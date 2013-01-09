/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.security.pacl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;
import com.liferay.portal.security.pacl.checker.Checker;
import com.liferay.portal.security.pacl.checker.JNDIChecker;
import com.liferay.portal.security.pacl.checker.PortalServiceChecker;
import com.liferay.portal.security.pacl.checker.SQLChecker;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.Method;

import java.security.Permission;

import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.locks.ReentrantLock;

import jodd.util.StringPool;

/**
 * @author Raymond Augé
 */
public class GeneratingPACLPolicy extends ActivePACLPolicy {

	public GeneratingPACLPolicy(
		String servletContextName, ClassLoader classLoader,
		Properties properties) {

		super(servletContextName, classLoader, properties);
	}

	@Override
	public void checkPermission(Permission permission) {
		Checker checker = getChecker(permission.getClass());

		try {
			checker.checkPermission(permission);
		}
		catch (SecurityException se) {
			String[] rule = checker.generateRuleFromCondition(permission);

			trackGeneratedRule(rule);
		}
	}

	@Override
	public boolean hasJNDI(String name) {
		JNDIChecker jndiChecker = getJndiChecker();

		if (!jndiChecker.hasJNDI(name)) {
			String[] rule = jndiChecker.generateRuleFromCondition(name);

			trackGeneratedRule(rule);
		}

		return true;
	}

	@Override
	public boolean hasPortalService(
		Object object, Method method, Object[] arguments) {

		PortalServiceChecker portalServiceChecker = getPortalServiceChecker();

		if (!portalServiceChecker.hasService(object, method, arguments)) {
			String[] rule = portalServiceChecker.generateRuleFromCondition(
				object, method, arguments);

			trackGeneratedRule(rule);
		}

		return true;
	}

	@Override
	public boolean hasSQL(String sql) {
		SQLChecker sqlChecker = getSqlChecker();

		if (!sqlChecker.hasSQL(sql)) {
			String[] rule = sqlChecker.generateRuleFromCondition(sql);

			trackGeneratedRule(rule);
		}

		return true;
	}

	private void trackGeneratedRule(String[] rule) {
		if ((rule == null) || (rule.length != 2) || (rule[0] == null)) {

			// This happens when a checker does not support configuration, which
			// is a valid scenario

			return;
		}

		String key = rule[0];
		String[] values = StringUtil.split(rule[1]);

		Set<String> trackedRules = _policyRules.get(key);
		Set<String> existingPolicyRules = getPropertySet(key);

		boolean changed = false;

		if (trackedRules == null) {
			trackedRules = new HashSet<String>();
		}

		if (!existingPolicyRules.isEmpty()) {
			trackedRules.addAll(existingPolicyRules);
		}

		for (String value : values) {
			if (!trackedRules.contains(value)) {
				trackedRules.add(value);

				changed = true;
			}
		}

		if (!changed) {
			return;
		}

		_reentrantLock.lock();

		try {
			System.out.println(
				"[PACL][" + getServletContextName() + "] adding rule {" +
					rule[0] + "=" + rule[1] + "}");

			// Only add new rules to the map is there was a change and we have a
			// lock

			_policyRules.put(key, trackedRules);

			writePropertiesFile();
		}
		finally {
			_reentrantLock.unlock();
		}
	}

	private void writePropertiesFile() {
		boolean enabled = PortalSecurityManagerThreadLocal.isEnabled();

		try {
			PortalSecurityManagerThreadLocal.setEnabled(false);

			String fileName = getServletContextName().concat(_fileExtension);

			String writePath = GetterUtil.getString(
				getProperty("security-manager-generator-dir"));

			if (Validator.isNull(writePath)) {
				writePath = PropsValues.LIFERAY_HOME.concat(
					File.separator).concat("pacl-policy");
			}

			String properties = generateProperties();

			FileUtil.write(writePath, fileName, properties);
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}
		finally {
			PortalSecurityManagerThreadLocal.setEnabled(enabled);
		}
	}

	private String generateProperties() {
		StringBundler sb = new StringBundler();

		for (Map.Entry<String, Set<String>> entry : _policyRules.entrySet()) {
			String key = entry.getKey();
			Set<String> valueSet = entry.getValue();

			sb.append(key);
			sb.append(StringPool.EQUALS);

			Set<String> sortedSet = new TreeSet<String>(valueSet);

			for (String value : sortedSet) {
				sb.append(StringPool.BACK_SLASH);
				sb.append(StringPool.NEWLINE);
				sb.append("    ");
				sb.append(value);
				sb.append(StringPool.COMMA);
			}

			sb.setIndex(sb.index() - 1);

			sb.append(StringPool.NEWLINE.concat(StringPool.NEWLINE));
		}

		if (sb.length() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private static Log _log = LogFactoryUtil.getLog(GeneratingPACLPolicy.class);

	private static final String _fileExtension = ".policy";

	private ConcurrentSkipListMap<String, Set<String>> _policyRules =
		new ConcurrentSkipListMap<String, Set<String>>();
	private ReentrantLock _reentrantLock = new ReentrantLock();

}
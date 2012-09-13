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

package com.liferay.portal.test;

import com.liferay.portal.apache.directory.LdapPartitions;
import com.liferay.portal.apache.directory.LiferayEmbeddedApacheDirectoryServer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.io.File;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Manuel de la Peña
 */
public class ApacheDirectoryServerExecutionTestListener
	extends AbstractExecutionTestListener {

	@Override
	public void runAfterClass(TestContext testContext) {
		try {
			_apacheDirectoryServer.stopServer();
		}

		catch(Exception e) {
			throw new RuntimeException(
				"The Apache directory could not be stopped");
		}
		finally {
			File workDir = new File(_WORK_DIR);

			FileUtil.deltree(workDir);
		}
	}

	@Override
	public void runBeforeClass(TestContext testContext) {
		Class<?> clazz = testContext.getClazz();

		Class<?> declaringClass = ReflectionUtil.getAnnotationDeclaringClass(
			LdapPartitions.class, clazz);

		List<String> partitions = new ArrayList<String>();

		while (declaringClass != null) {
			LdapPartitions ldapPartitions = declaringClass.getAnnotation(
				LdapPartitions.class);

			partitions.addAll(ListUtil.toList(ldapPartitions.partitions()));

			declaringClass = ReflectionUtil.getAnnotationDeclaringClass(
				LdapPartitions.class, declaringClass.getSuperclass());
		}

		_startServer(partitions);
	}

	@Override
	public void runBeforeTest(TestContext testContext) {
		Method method = testContext.getMethod();

		if (!ReflectionUtil.isAnnotationDeclared(
				LdapPartitions.class, method)) {

			return;
		}

		LdapPartitions ldapPartitions = method.getAnnotation(
			LdapPartitions.class);

		if  (ldapPartitions.removeAllPartitions()) {
			try {
				_apacheDirectoryServer.removeAllPartitions();
			}
			catch (Exception e) {
				_log.error(
					"An error has ocurred while removing existing partitions." +
						" Your test could fail because that");
			}
		}

		List<String> partitions = new ArrayList<String>();

		partitions.addAll(ListUtil.toList(ldapPartitions.partitions()));

		_addPartitions(partitions);
	}

	private void _addPartitions(List<String> partitions) {
		if (_apacheDirectoryServer == null) {
			_startServer(new ArrayList<String>());
		}

		try {
			_apacheDirectoryServer.addPartitions(partitions);
		}
		catch (Exception e) {
			throw new RuntimeException(
				"The Apache directory could not be initializated");
		}
	}

	private void _startServer(List<String> partitions) {
		if (_apacheDirectoryServer != null) {
			return;
		}

		try {
			_apacheDirectoryServer = new LiferayEmbeddedApacheDirectoryServer(
				_WORK_DIR, partitions);

			_apacheDirectoryServer.startServer();
		}

		catch(Exception e) {
			throw new RuntimeException(
				"The Apache directory could not be initializated");
		}
	}

	private static final String _WORK_DIR =
		System.getProperty("java.io.tmpdir") + "/apacheds";

	private static Log _log = LogFactoryUtil.getLog(
		ApacheDirectoryServerExecutionTestListener.class);

	private LiferayEmbeddedApacheDirectoryServer _apacheDirectoryServer;

}
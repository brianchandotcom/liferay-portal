/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Alan Huang
 */
public abstract class BaseJakartaTransformerCheck extends BaseFileCheck {

	protected boolean isModulesFile(String absolutePath) {
		String rootDirName = SourceUtil.getRootDirName(absolutePath);

		if (rootDirName.equals(StringPool.BLANK)) {
			return false;
		}

		String s = absolutePath.substring(rootDirName.length());

		if (!s.startsWith("/modules/")) {
			return false;
		}

		s = s.substring(9);

		for (String moduleName : _moduleNames) {
			if (s.startsWith(moduleName + "/")) {
				return true;
			}
		}

		return false;
	}

	protected boolean isTopLevelProjectsFile(String absolutePath) {
		String rootDirName = SourceUtil.getRootDirName(absolutePath);

		if (rootDirName.equals(StringPool.BLANK)) {
			return false;
		}

		int x = absolutePath.indexOf("/", rootDirName.length() + 1);

		if (x == -1) {
			return false;
		}

		String projectName = absolutePath.substring(
			rootDirName.length() + 1, x);

		if (_topLevelProjectNames.contains(projectName)) {
			return true;
		}

		return false;
	}

	protected String replace(String value) {
		for (Map.Entry<String, String> entry : _replacementDashMap.entrySet()) {
			value = StringUtil.replace(value, entry.getKey(), entry.getValue());
		}

		for (Map.Entry<String, String> entry : _replacementDotMap.entrySet()) {
			value = StringUtil.replace(value, entry.getKey(), entry.getValue());
		}

		for (Map.Entry<String, String> entry :
				_replacementSlashMap.entrySet()) {

			value = StringUtil.replace(value, entry.getKey(), entry.getValue());
		}

		return value;
	}

	protected String replaceTaglibURIs(String content) {
		return StringUtil.replace(
			content,
			new String[] {
				"http://java.sun.com/jsp/jstl/core",
				"http://java.sun.com/jsp/jstl/fmt",
				"http://java.sun.com/jsp/jstl/functions",
				"http://java.sun.com/jsp/jstl/sql",
				"http://java.sun.com/jsp/jstl/xml"
			},
			new String[] {
				"jakarta.tags.core", "jakarta.tags.fmt",
				"jakarta.tags.functions", "jakarta.tags.sql", "jakarta.tags.xml"
			});
	}

	private static final Set<String> _fixupSubpackageNames = new HashSet<>(
		Arrays.asList("annotation.processing", "transaction.xa"));
	private static final Map<String, String> _replacementDashMap =
		new LinkedHashMap<>();
	private static final Map<String, String> _replacementDotMap =
		new LinkedHashMap<>();
	private static final Map<String, String> _replacementSlashMap =
		new LinkedHashMap<>();
	private static final Set<String> _subpackageNames = new HashSet<>(
		Arrays.asList(
			"activation", "annotation", "batch", "decorator", "ejb", "el",
			"enterprise", "faces", "inject", "interceptor", "jms", "json",
			"jws", "mail", "mvc", "persistence", "portlet", "resource",
			"security.auth.message", "security.enterprise", "security.jacc",
			"servlet", "transaction", "validation", "websocket", "ws.rs",
			"xml.bind", "xml.soap", "xml.ws"));

	static {
		_subpackageNames.forEach(
			subpackageName -> {
				String javaxPackage = "javax." + subpackageName;
				String jakartaPackage = "jakarta." + subpackageName;

				_replacementDashMap.put(
					StringUtil.replace(javaxPackage, '.', '-'),
					StringUtil.replace(jakartaPackage, '.', '-'));
				_replacementDotMap.put(javaxPackage, jakartaPackage);
				_replacementSlashMap.put(
					StringUtil.replace(javaxPackage, '.', '/'),
					StringUtil.replace(jakartaPackage, '.', '/'));
			});

		// Order matters, fixups need to be put into replacement map later

		_fixupSubpackageNames.forEach(
			fixupSubpackageName -> {
				String fixupJavaxPackage = "javax." + fixupSubpackageName;
				String fixupJakartaPackage = "jakarta." + fixupSubpackageName;

				_replacementDashMap.put(
					StringUtil.replace(fixupJakartaPackage, '.', '-'),
					StringUtil.replace(fixupJavaxPackage, '.', '-'));
				_replacementDotMap.put(fixupJakartaPackage, fixupJavaxPackage);
				_replacementSlashMap.put(
					StringUtil.replace(fixupJakartaPackage, '.', '/'),
					StringUtil.replace(fixupJavaxPackage, '.', '/'));
			});

		_replacementDashMap.put(
			"X-JAVAX-PORTLET-NAMESPACED-RESPONSE",
			"X-JAKARTA-PORTLET-NAMESPACED-RESPONSE");
	}

	private final List<String> _moduleNames = Arrays.asList(
		"apps", "aspectj", "core", "dxp", "sdk/ant-bnd", "test", "util");
	private final List<String> _topLevelProjectNames = Arrays.asList(
		"portal-impl", "portal-kernel", "portal-test", "portal-web",
		"support-tomcat", "util-bridges", "util-java", "util-slf4j",
		"util-taglib");

}
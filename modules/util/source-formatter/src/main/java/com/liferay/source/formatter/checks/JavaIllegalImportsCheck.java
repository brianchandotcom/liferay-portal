/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Hugo Huijser
 */
public class JavaIllegalImportsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = StringUtil.replace(
			content,
			new String[] {
				"com.liferay.portal.PortalException",
				"com.liferay.portal.SystemException",
				"com.liferay.util.LocalizationUtil"
			},
			new String[] {
				"com.liferay.portal.kernel.exception.PortalException",
				"com.liferay.portal.kernel.exception.SystemException",
				"com.liferay.portal.kernel.util.LocalizationUtil"
			});

		if (!isExcludedPath(RUN_OUTSIDE_PORTAL_EXCLUDES, absolutePath) &&
			!isExcludedPath(_PROXY_EXCLUDES, absolutePath) &&
			content.contains("import java.lang.reflect.Proxy;")) {

			addMessage(
				fileName, "Use ProxyUtil instead of java.lang.reflect.Proxy");
		}

		if (content.contains("import edu.emory.mathcs.backport.java")) {
			addMessage(
				fileName, "Illegal import: edu.emory.mathcs.backport.java");
		}

		if (content.contains("import jodd.util.StringPool")) {
			addMessage(fileName, "Illegal import: jodd.util.StringPool");
		}

		// LPS-39508

		if (!isExcludedPath(RUN_OUTSIDE_PORTAL_EXCLUDES, absolutePath) &&
			!isExcludedPath(_SECURE_RANDOM_EXCLUDES, absolutePath) &&
			content.contains("java.security.SecureRandom") &&
			!content.contains("javax.crypto.KeyGenerator")) {

			addMessage(
				fileName,
				"Use SecureRandomUtil or com.liferay.portal.kernel.security." +
					"SecureRandom instead of java.security.SecureRandom, see " +
						"LPS-39058");
		}

		// LPS-45027

		if (content.contains(
				"com.liferay.portal.kernel.util.UnmodifiableList")) {

			addMessage(
				fileName,
				"Use java.util.Collections.unmodifiableList instead of " +
					"com.liferay.portal.kernel.util.UnmodifiableList, see " +
						"LPS-45027");
		}

		// LPS-47682

		if (isPortalSource() && absolutePath.contains("/portal-kernel/") &&
			content.contains("import javax.servlet.jsp.")) {

			addMessage(
				fileName,
				"Never import javax.servlet.jsp.* from portal-kernel, see " +
					"LPS-47682");
		}

		// LPS-55690

		if (content.contains("org.testng.Assert")) {
			addMessage(
				fileName,
				"Use org.junit.Assert instead of org.testng.Assert, see " +
					"LPS-55690");
		}

		// LPS-60473

		if (content.contains(".supportsBatchUpdates()") &&
			!fileName.endsWith("AutoBatchPreparedStatementUtil.java")) {

			addMessage(
				fileName,
				"Use AutoBatchPreparedStatementUtil instead of " +
					"DatabaseMetaData.supportsBatchUpdates, see LPS-60473");
		}

		// LPS-62786

		if (!fileName.endsWith("TypeConvertorUtil.java") &&
			content.contains("org.apache.commons.beanutils.PropertyUtils")) {

			addMessage(
				fileName,
				"Do not use org.apache.commons.beanutils.PropertyUtils, see " +
					"LPS-62786");
		}

		// LPS-64056

		if (content.contains("Configurable.createConfigurable(") &&
			!fileName.endsWith("ConfigurableUtil.java")) {

			addMessage(
				fileName,
				"Use ConfigurableUtil.createConfigurable instead of " +
					"Configurable.createConfigurable, see LPS-64056");
		}

		// LPS-65229

		if (fileName.endsWith("ResourceCommand.java") &&
			content.contains("ServletResponseUtil.sendFile(")) {

			addMessage(
				fileName,
				"Use PortletResponseUtil.sendFile instead of " +
					"ServletResponseUtil.sendFile, see LPS-65229");
		}

		// LPS-69494

		if (!fileName.endsWith("AbstractExtender.java") &&
			content.contains(
				"org.apache.felix.utils.extender.AbstractExtender")) {

			StringBundler sb = new StringBundler(4);

			sb.append("Use com.liferay.osgi.felix.util.AbstractExtender ");
			sb.append("instead of ");
			sb.append("org.apache.felix.utils.extender.AbstractExtender, see ");
			sb.append("LPS-69494");

			addMessage(fileName, sb.toString());
		}

		// LPS-70963

		if (content.contains("java.util.WeakHashMap")) {
			addMessage(
				fileName,
				"Do not use java.util.WeakHashMap because it is not " +
					"thread-safe, see LPS-70963");
		}

		if (!isExcludedPath(RUN_OUTSIDE_PORTAL_EXCLUDES, absolutePath) &&
			content.contains("org.slf4j.Logger")) {

			addMessage(
				fileName,
				"Use com.liferay.portal.kernel.log.Log instead of " +
					"org.slf4j.Logger");
		}

		return content;
	}

	private static final String _PROXY_EXCLUDES = "proxy.excludes";

	private static final String _SECURE_RANDOM_EXCLUDES =
		"secure.random.excludes";

}
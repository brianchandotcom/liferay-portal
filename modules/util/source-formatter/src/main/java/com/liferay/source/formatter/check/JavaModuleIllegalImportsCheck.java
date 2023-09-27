/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaModuleIllegalImportsCheck extends BaseFileCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		String packageName = JavaSourceUtil.getPackageName(content);

		if (!packageName.startsWith("com.liferay")) {
			return content;
		}

		if (!absolutePath.contains("/modules/core/jaxws-osgi-bridge") &&
			!absolutePath.contains("/modules/core/portal-bootstrap") &&
			!absolutePath.contains("/modules/core/registry-") &&
			!absolutePath.contains("/modules/core/slim-runtime") &&
			(isAttributeValue(
				_CHECK_REGISTRY_IN_TEST_CLASSES_KEY, absolutePath) ||
			 (!absolutePath.contains("/test/") &&
			  !absolutePath.contains("/testIntegration/")))) {

			Matcher matcher = _registryImportPattern.matcher(content);

			if (matcher.find()) {
				addMessage(
					fileName,
					"Do not use com.liferay.registry classes in modules, see " +
						"LPS-62989");
			}
		}

		if (isAttributeValue(_ENFORCE_PETRA_STRING_BUNDLER_KEY, absolutePath) &&
			content.contains("com.liferay.portal.kernel.util.StringBundler")) {

			addMessage(
				fileName,
				"Use com.liferay.petra.string.StringBundler instead of " +
					"com.liferay.portal.kernel.util.StringBundler");
		}

		if (content.contains("import com.liferay.util.dao.orm.CustomSQLUtil")) {
			addMessage(
				fileName,
				"Do not use com.liferay.util.dao.orm.CustomSQLUtil in " +
					"modules, see LPS-77361");
		}

		if (content.contains("import com.liferay.util.ContentUtil")) {
			addMessage(
				fileName,
				"Do not use com.liferay.util.ContentUtil in modules, see " +
					"LPS-64335");
		}

		int pos = fileName.lastIndexOf(StringPool.SLASH);

		String shortFileName = fileName.substring(pos + 1);

		if (absolutePath.matches(".+/internal/resource/v\\d*(_\\d+)+/.+") &&
			fileName.endsWith("ResourceImpl.java") &&
			!shortFileName.startsWith("Base") &&
			content.contains(
				"import com.liferay.petra.function.transform.TransformUtil")) {

			addMessage(
				fileName,
				"Do not use com.liferay.petra.function.transform." +
					"TransformUtil in *ResourceImpl.java, see LPS-167117");
		}

		return content;
	}

	private static final String _CHECK_REGISTRY_IN_TEST_CLASSES_KEY =
		"checkRegistryInTestClasses";

	private static final String _ENFORCE_PETRA_STRING_BUNDLER_KEY =
		"enforcePetraStringBundler";

	private static final Pattern _registryImportPattern = Pattern.compile(
		"\nimport (com\\.liferay\\.registry\\..+);");

}
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

package com.liferay.source.formatter.checkstyle.checks;

import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hugo Huijser
 */
public class UpgradeRemovedAPICheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		if ((parentDetailAST != null) ||
			AnnotationUtil.containsAnnotation(detailAST, "Deprecated")) {

			return;
		}

		String upgradeFromVersion = getAttributeValue(
			SourceFormatterUtil.UPGRADE_FROM_VERSION);
		String upgradeToVersion = getAttributeValue(
			SourceFormatterUtil.UPGRADE_TO_VERSION);

		try {
			JSONObject upgradeFromJavaClassesJSONObject =
				_getJavaClassesJSONObject(upgradeFromVersion);
			JSONObject upgradeToJavaClassesJSONObject =
				_getJavaClassesJSONObject(upgradeToVersion);

			_checkRemovedImportNames(
				detailAST, upgradeFromJavaClassesJSONObject,
				upgradeToJavaClassesJSONObject, upgradeToVersion);
		}
		catch (Exception exception) {
		}
	}

	private void _checkRemovedImportNames(
		DetailAST detailAST, JSONObject fromUpgradeJavaClassesJSONObject,
		JSONObject toUpgradeJavaClassesJSONObject, String upgradeToVersion) {

		List<String> importNames = getImportNames(detailAST);

		for (String importName : importNames) {
			JSONObject fromUpgradeClassJSONObject =
				fromUpgradeJavaClassesJSONObject.getJSONObject(importName);
			JSONObject toUpgradeClassJSONObject =
				toUpgradeJavaClassesJSONObject.getJSONObject(importName);

			if ((fromUpgradeClassJSONObject != null) &&
				(toUpgradeClassJSONObject == null)) {

				log(
					detailAST, _MSG_CLASS_NOT_FOUND, importName,
					upgradeToVersion);
			}
		}
	}

	private synchronized JSONObject _getJavaClassesJSONObject(String version)
		throws Exception {

		JSONObject javaClassesJSONObject = _javaClassesJSONObjectMap.get(
			version);

		if (javaClassesJSONObject != null) {
			return javaClassesJSONObject;
		}

		JSONObject portalJSONObject =
			SourceFormatterUtil.getPortalJSONObjectByVersion(version);

		if (portalJSONObject.has("javaClasses")) {
			javaClassesJSONObject = portalJSONObject.getJSONObject(
				"javaClasses");
		}
		else {
			javaClassesJSONObject = new JSONObjectImpl();
		}

		_javaClassesJSONObjectMap.put(version, javaClassesJSONObject);

		return javaClassesJSONObject;
	}

	private static final String _MSG_CLASS_NOT_FOUND = "class.not.found";

	private final Map<String, JSONObject> _javaClassesJSONObjectMap =
		new HashMap<>();

}
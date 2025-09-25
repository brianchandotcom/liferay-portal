/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Mikel Lorza
 */
public class FragmentLinkValueUtil {

	public static String getFieldKey(JSONObject jsonObject) {
		String fieldId = jsonObject.getString("fieldId");

		if (Validator.isNotNull(fieldId)) {
			return fieldId;
		}

		return null;
	}

	public static boolean isSaveFragmentMappedValue(JSONObject jsonObject) {
		if (jsonObject == null) {
			return false;
		}

		if ((jsonObject.has("classNameId") &&
			 jsonObject.has("externalReferenceCode") &&
			 jsonObject.has("fieldId")) ||
			jsonObject.has("layout")) {

			return true;
		}

		return false;
	}

	public static Object toItemReference(
		JSONObject jsonObject, long scopeGroupId) {

		String fieldId = jsonObject.getString("fieldId");
		JSONObject layoutJSONObject = jsonObject.getJSONObject("layout");

		if (Validator.isNull(fieldId) && (layoutJSONObject == null)) {
			return null;
		}

		if (layoutJSONObject != null) {
			return _toLayoutItemExternalReference(
				layoutJSONObject, scopeGroupId);
		}

		return new ItemExternalReference() {
			{
				setClassName(() -> _toItemClassName(jsonObject));
				setExternalReferenceCode(
					() -> jsonObject.getString("internalReferenceCode"));

				// TODO:add the scope here

			}
		};
	}

	private static String _toItemClassName(JSONObject jsonObject) {
		String classNameIdString = jsonObject.getString("classNameId");

		if (Validator.isNull(classNameIdString)) {
			return null;
		}

		long classNameId = 0;

		try {
			classNameId = Long.parseLong(classNameIdString);
		}
		catch (NumberFormatException numberFormatException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					String.format(
						"Item class name could not be set since class name " +
							"ID %s could not be parsed to a long",
						classNameIdString),
					numberFormatException);
			}

			return null;
		}

		String className = null;

		try {
			className = PortalUtil.getClassName(classNameId);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Item class name could not be set since no class name " +
						"could be obtained for class name ID " + classNameId,
					exception);
			}

			return null;
		}

		return className;
	}

	private static ItemExternalReference _toLayoutItemExternalReference(
		JSONObject layoutJSONObject, long scopeGroupId) {

		Layout layout;

		try {
			layout = LayoutLocalServiceUtil.getLayout(
				layoutJSONObject.getLong("groupId"),
				layoutJSONObject.getBoolean("privateLayout"),
				layoutJSONObject.getLong("layoutId"));
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Item reference could not be set since no layout could " +
						"be obtained",
					portalException);
			}

			return null;
		}

		return new ItemExternalReference() {
			{
				setClassName(Layout.class::getName);
				setExternalReferenceCode(layout::getExternalReferenceCode);
				setScope(
					() -> ScopeUtil.getScope(
						layout.getGroupId(), scopeGroupId));
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentLinkValueUtil.class);

}
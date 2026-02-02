/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.internal.util.LogUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.scope.Scope;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutUtil {

	public static Layout fetchLayoutByExternalReferenceCode(
		long companyId, String externalReferenceCode, Scope scope,
		long scopeGroupId) {

		Layout layout = null;

		Long groupId = ItemScopeUtil.getItemGroupId(
			companyId, scope, scopeGroupId);

		if (groupId != null) {
			layout = LayoutLocalServiceUtil.fetchLayoutByExternalReferenceCode(
				externalReferenceCode, groupId);
		}

		if (layout == null) {
			LogUtil.logOptionalReference(
				Layout.class.getName(), externalReferenceCode, scope,
				scopeGroupId);
		}

		return layout;
	}

	public static JSONObject getMappedLayoutJSONObject(
			long companyId, String externalReferenceCode, Scope scope,
			long scopeGroupId)
		throws PortalException {

		Layout layout = fetchLayoutByExternalReferenceCode(
			companyId, externalReferenceCode, scope, scopeGroupId);

		if (layout == null) {
			return JSONUtil.put(
				"externalReferenceCode", externalReferenceCode
			).put(
				"scopeExternalReferenceCode",
				ItemScopeUtil.getItemScopeExternalReferenceCode(
					scope, scopeGroupId)
			);
		}

		return JSONUtil.put(
			"externalReferenceCode", externalReferenceCode
		).put(
			"groupId", String.valueOf(layout.getGroupId())
		).put(
			"layoutId", String.valueOf(layout.getLayoutId())
		).put(
			"layoutUuid", layout.getUuid()
		).put(
			"privateLayout", layout.isPrivateLayout()
		).put(
			"scopeExternalReferenceCode",
			ItemScopeUtil.getItemScopeExternalReferenceCode(scope, scopeGroupId)
		).put(
			"title", layout.getName(LocaleUtil.getMostRelevantLocale())
		);
	}

	public static ItemExternalReference toLayoutItemExternalReference(
		long companyId, JSONObject layoutJSONObject, long scopeGroupId) {

		if (JSONUtil.isEmpty(layoutJSONObject)) {
			return null;
		}

		Layout layout = LayoutLocalServiceUtil.fetchLayout(
			layoutJSONObject.getLong("groupId"),
			layoutJSONObject.getBoolean("privateLayout"),
			layoutJSONObject.getLong("layoutId"));

		String externalReferenceCode;

		if (layout != null) {
			externalReferenceCode = layout.getExternalReferenceCode();
		}
		else {
			externalReferenceCode = layoutJSONObject.getString(
				"externalReferenceCode");
		}

		if (Validator.isNull(externalReferenceCode)) {
			return null;
		}

		ItemExternalReference itemExternalReference =
			new ItemExternalReference();

		itemExternalReference.setClassName(Layout.class::getName);
		itemExternalReference.setExternalReferenceCode(
			() -> externalReferenceCode);
		itemExternalReference.setScope(
			() -> {
				if (layout != null) {
					return ItemScopeUtil.getItemScope(
						layout.getGroupId(), scopeGroupId);
				}

				return ItemScopeUtil.getItemScope(
					companyId,
					layoutJSONObject.getString("scopeExternalReferenceCode"),
					scopeGroupId);
			});

		return itemExternalReference;
	}

}
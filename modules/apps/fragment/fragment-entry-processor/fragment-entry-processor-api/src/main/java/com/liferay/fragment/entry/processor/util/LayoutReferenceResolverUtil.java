/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.entry.processor.util;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Javier Moral
 */
public class LayoutReferenceResolverUtil {

	public static Layout resolve(
		long companyId, JSONObject layoutJSONObject, long scopeGroupId) {

		if (layoutJSONObject.has("layoutId")) {
			long groupId = layoutJSONObject.getLong("groupId");

			Group group = GroupLocalServiceUtil.fetchGroup(groupId);

			if (group == null) {
				return null;
			}

			return LayoutLocalServiceUtil.fetchLayout(
				groupId, layoutJSONObject.getBoolean("privateLayout"),
				layoutJSONObject.getLong("layoutId"));
		}

		if (layoutJSONObject.has("externalReferenceCode")) {
			long groupId = scopeGroupId;
			String scopeExternalReferenceCode = layoutJSONObject.getString(
				"scopeExternalReferenceCode");

			if (Validator.isNotNull(scopeExternalReferenceCode)) {
				Group group =
					GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
						scopeExternalReferenceCode, companyId);

				if (group != null) {
					groupId = group.getGroupId();
				}
			}

			return LayoutLocalServiceUtil.fetchLayoutByExternalReferenceCode(
				layoutJSONObject.getString("externalReferenceCode"), groupId);
		}

		return null;
	}

}
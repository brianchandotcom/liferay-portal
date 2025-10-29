/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.entry.processor.internal.helper;

import com.liferay.fragment.entry.processor.helper.LayoutReferenceResolver;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Moral
 */
@Component(service = LayoutReferenceResolver.class)
public class LayoutReferenceResolverImpl implements LayoutReferenceResolver {

	@Override
	public Layout resolve(
		long companyId, JSONObject jsonObject, long scopeGroupId) {

		if (jsonObject.has("layoutId")) {
			long groupId = jsonObject.getLong("groupId");

			Group group = _groupLocalService.fetchGroup(groupId);

			if (group == null) {
				return null;
			}

			return _layoutLocalService.fetchLayout(
				groupId, jsonObject.getBoolean("privateLayout"),
				jsonObject.getLong("layoutId"));
		}

		if (jsonObject.has("externalReferenceCode")) {
			long groupId = scopeGroupId;
			String scopeExternalReferenceCode = jsonObject.getString(
				"scopeExternalReferenceCode");

			if (Validator.isNotNull(scopeExternalReferenceCode)) {
				Group group =
					_groupLocalService.fetchGroupByExternalReferenceCode(
						scopeExternalReferenceCode, companyId);

				if (group == null) {
					return null;
				}

				groupId = group.getGroupId();
			}

			return _layoutLocalService.fetchLayoutByExternalReferenceCode(
				jsonObject.getString("externalReferenceCode"), groupId);
		}

		return null;
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

}
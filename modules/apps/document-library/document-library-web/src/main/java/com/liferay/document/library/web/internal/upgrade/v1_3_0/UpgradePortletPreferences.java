/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.upgrade.v1_3_0;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.BasePortletPreferencesUpgradeProcess;
import com.liferay.portal.kernel.util.Validator;

import jakarta.portlet.PortletPreferences;

/**
 * @author Jürgen Kappler
 */
public class UpgradePortletPreferences
	extends BasePortletPreferencesUpgradeProcess {

	public UpgradePortletPreferences(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Override
	protected String[] getPortletIds() {
		return new String[] {
			DLPortletKeys.DOCUMENT_LIBRARY + "_INSTANCE_%",
			DLPortletKeys.MEDIA_GALLERY_DISPLAY + "_INSTANCE_%"
		};
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		String selectedGroupExternalReferenceCode = portletPreferences.getValue(
			"selectedGroupExternalReferenceCode", null);

		if (Validator.isNull(selectedGroupExternalReferenceCode)) {
			return xml;
		}

		Group selectedGroup =
			_groupLocalService.fetchGroupByExternalReferenceCode(
				selectedGroupExternalReferenceCode, companyId);

		if (selectedGroup == null) {
			return xml;
		}

		Object[] layout = getLayout(plid);

		if (layout == null) {
			return xml;
		}

		long groupId = (long)layout[0];

		if (groupId == selectedGroup.getGroupId()) {
			portletPreferences.reset("selectedGroupExternalReferenceCode");
			portletPreferences.reset("selectedRepositoryExternalReferenceCode");
		}

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	private final GroupLocalService _groupLocalService;

}
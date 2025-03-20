/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.asset.library.internal.util;

import com.liferay.depot.model.DepotAppCustomization;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotAppCustomizationLocalServiceUtil;
import com.liferay.depot.service.DepotEntryServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Roberto Díaz
 */
public class DepotEntryManagerUtil {

	public static DepotEntry addOrUpdateDepotEntry(
			String externalReferenceCode, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap,
			UnicodeProperties unicodeProperties, ServiceContext serviceContext)
		throws Exception {

		DepotEntry depotEntry = null;

		Group group = GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
			externalReferenceCode, serviceContext.getCompanyId());

		if (group != null) {
			depotEntry = DepotEntryServiceUtil.getGroupDepotEntry(
				group.getGroupId());

			depotEntry = DepotEntryServiceUtil.updateDepotEntry(
				depotEntry.getDepotEntryId(), nameMap, descriptionMap,
				getDepotCustomizationMap(
					externalReferenceCode, depotEntry.getCompanyId()),
				unicodeProperties, serviceContext);
		}
		else {
			depotEntry = DepotEntryServiceUtil.addDepotEntry(
				nameMap, descriptionMap, serviceContext);
		}

		group = depotEntry.getGroup();

		group.setExternalReferenceCode(externalReferenceCode);

		GroupLocalServiceUtil.updateGroup(group);

		return depotEntry;
	}

	public static Map<String, Boolean> getDepotCustomizationMap(
			String externalReferenceCode, long companyId)
		throws Exception {

		Map<String, Boolean> depotAppCustomizationMap = new HashMap<>();

		Group group = GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
			externalReferenceCode, companyId);

		if (group != null) {
			DepotEntry depotEntry = DepotEntryServiceUtil.getGroupDepotEntry(
				group.getGroupId());

			for (DepotAppCustomization depotAppCustomization :
					DepotAppCustomizationLocalServiceUtil.
						getDepotAppCustomizations(
							depotEntry.getDepotEntryId())) {

				depotAppCustomizationMap.put(
					depotAppCustomization.getPortletId(),
					depotAppCustomization.isEnabled());
			}
		}

		return depotAppCustomizationMap;
	}

}
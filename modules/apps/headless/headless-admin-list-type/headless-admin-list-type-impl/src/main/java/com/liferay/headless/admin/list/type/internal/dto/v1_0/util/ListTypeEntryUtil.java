/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.list.type.internal.dto.v1_0.util;

import com.liferay.headless.admin.list.type.dto.v1_0.ListTypeEntry;
import com.liferay.headless.admin.list.type.dto.v1_0.Status;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.rest.dto.v1_0.util.CreatorUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import jakarta.ws.rs.core.UriInfo;

import java.util.Locale;
import java.util.Map;

/**
 * @author Gabriel Albuquerque
 */
public class ListTypeEntryUtil {

	public static com.liferay.list.type.model.ListTypeEntry toListTypeEntry(
		ListTypeEntry listTypeEntry,
		ListTypeEntryLocalService listTypeEntryLocalService) {

		com.liferay.list.type.model.ListTypeEntry serviceBuilderListTypeEntry =
			listTypeEntryLocalService.createListTypeEntry(0L);

		serviceBuilderListTypeEntry.setExternalReferenceCode(
			listTypeEntry.getExternalReferenceCode());
		serviceBuilderListTypeEntry.setKey(listTypeEntry.getKey());

		Map<Locale, String> nameMap = LocalizedMapUtil.getLocalizedMap(
			listTypeEntry.getName_i18n());

		nameMap.computeIfAbsent(
			LocaleUtil.getSiteDefault(), key -> listTypeEntry.getName());

		serviceBuilderListTypeEntry.setNameMap(nameMap);

		serviceBuilderListTypeEntry.setSystem(
			GetterUtil.getBoolean(listTypeEntry.getSystem()));

		return serviceBuilderListTypeEntry;
	}

	public static ListTypeEntry toListTypeEntry(
		Map<String, Map<String, String>> actions, Locale locale, Portal portal,
		com.liferay.list.type.model.ListTypeEntry serviceBuilderListTypeEntry,
		UriInfo uriInfo, User user) {

		ListTypeEntry listTypeEntry = new ListTypeEntry() {
			{
				setCreator(() -> CreatorUtil.toCreator(portal, uriInfo, user));
				setDateCreated(serviceBuilderListTypeEntry::getCreateDate);
				setDateModified(serviceBuilderListTypeEntry::getModifiedDate);
				setExternalReferenceCode(
					serviceBuilderListTypeEntry::getExternalReferenceCode);
				setId(serviceBuilderListTypeEntry::getListTypeEntryId);
				setKey(serviceBuilderListTypeEntry::getKey);
				setName(() -> serviceBuilderListTypeEntry.getName(locale));
				setName_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						serviceBuilderListTypeEntry.getNameMap()));
				setStatus(
					() -> {
						if (!FeatureFlagManagerUtil.isEnabled(
								serviceBuilderListTypeEntry.getCompanyId(),
								"LPD-35914")) {

							return null;
						}

						return new Status() {
							{
								setCode(serviceBuilderListTypeEntry::getStatus);
								setLabel(
									() -> WorkflowConstants.getStatusLabel(
										serviceBuilderListTypeEntry.
											getStatus()));
								setLabel_i18n(
									() -> LanguageUtil.get(
										LanguageResources.getResourceBundle(
											locale),
										WorkflowConstants.getStatusLabel(
											serviceBuilderListTypeEntry.
												getStatus())));
							}
						};
					});
				setSystem(serviceBuilderListTypeEntry::getSystem);
				setType(serviceBuilderListTypeEntry::getType);
			}
		};

		listTypeEntry.setActions(() -> actions);

		return listTypeEntry;
	}

}
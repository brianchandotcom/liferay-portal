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

package com.liferay.commerce.internal.helper;

import com.liferay.commerce.constants.CommerceSAPConstants;
import com.liferay.commerce.helper.CommerceSAPHelper;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(service = CommerceSAPHelper.class)
public class CommerceSAPHelperImpl implements CommerceSAPHelper {

	@Override
	public void addCommerceDefaultSAPEntries(long companyId, long userId)
		throws PortalException {

		for (String[] sapEntryObjectArray :
				CommerceSAPConstants.SAP_ENTRY_OBJECT_ARRAYS) {

			String sapEntryName = sapEntryObjectArray[0];

			SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
				companyId, sapEntryName);

			if (sapEntry != null) {
				continue;
			}

			Map<Locale, String> titleMap =
				ResourceBundleUtil.getLocalizationMap(
					LanguageResources.PORTAL_RESOURCE_BUNDLE_LOADER,
					"public-access-to-the-commerce-service-apis");

			_sapEntryLocalService.addSAPEntry(
				userId, sapEntryObjectArray[1], true, true, sapEntryName,
				titleMap, new ServiceContext());
		}
	}

	@Override
	public void removeCommerceDefaultSAPEntries(long companyId)
		throws PortalException {

		List<SAPEntry> companySAPEntries =
			_sapEntryLocalService.getCompanySAPEntries(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (SAPEntry sapEntry : companySAPEntries) {
			if (Objects.equals(
					sapEntry.getName(), CommerceSAPConstants.SAP_ENTRY_NAME)) {

				_sapEntryLocalService.deleteSAPEntry(sapEntry);
			}
		}
	}

	@Reference
	private SAPEntryLocalService _sapEntryLocalService;

}
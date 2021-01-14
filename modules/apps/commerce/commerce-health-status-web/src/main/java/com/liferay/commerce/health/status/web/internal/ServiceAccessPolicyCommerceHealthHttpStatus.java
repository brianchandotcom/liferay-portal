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

package com.liferay.commerce.health.status.web.internal;

import com.liferay.commerce.constants.CommerceHealthStatusConstants;
import com.liferay.commerce.constants.CommerceSAPConstants;
import com.liferay.commerce.health.status.CommerceHealthHttpStatus;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.resource.bundle.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ClassResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.health.status.display.order:Integer=50",
		"commerce.health.status.key=" + CommerceHealthStatusConstants.SAP_COMMERCE_HEALTH_STATUS_KEY
	},
	service = CommerceHealthHttpStatus.class
)
public class ServiceAccessPolicyCommerceHealthHttpStatus
	implements CommerceHealthHttpStatus {

	@Override
	public void fixIssue(HttpServletRequest httpServletRequest)
		throws PortalException {

		long companyId = _portal.getCompanyId(httpServletRequest);

		_removeCommerceDefaultSAPEntries(companyId);

		User user = _userLocalService.getDefaultUser(companyId);

		_addCommerceDefaultSAPEntries(companyId, user.getUserId());
	}

	@Override
	public String getDescription(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle,
			CommerceHealthStatusConstants.
				SAP_COMMERCE_HEALTH_STATUS_DESCRIPTION);
	}

	@Override
	public String getKey() {
		return CommerceHealthStatusConstants.SAP_COMMERCE_HEALTH_STATUS_KEY;
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle,
			CommerceHealthStatusConstants.SAP_COMMERCE_HEALTH_STATUS_KEY);
	}

	@Override
	public int getType() {
		return CommerceHealthStatusConstants.
			COMMERCE_HEALTH_STATUS_TYPE_VIRTUAL_INSTANCE;
	}

	@Override
	public boolean isFixed(long companyId, long commerceChannelId)
		throws PortalException {

		List<SAPEntry> companySAPEntries =
			_sapEntryLocalService.getCompanySAPEntries(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (SAPEntry sapEntry : companySAPEntries) {
			if (Objects.equals(
					sapEntry.getName(),
					CommerceSAPConstants.COMMERCE_SAP_ENTRY_NAME)) {

				String[] sapEntryObjectArray =
					CommerceSAPConstants.SAP_ENTRY_OBJECT_ARRAYS[0];

				String allowedServiceSignatures = sapEntryObjectArray[1];

				List<String> allowedServiceSignaturesList =
					sapEntry.getAllowedServiceSignaturesList();

				String[] allowedServiceSignaturesArray =
					allowedServiceSignatures.split("\n");

				for (String allowedServiceSignature :
						allowedServiceSignaturesArray) {

					if (!allowedServiceSignaturesList.contains(
							allowedServiceSignature)) {

						return false;
					}
				}

				return true;
			}
		}

		return false;
	}

	private void _addCommerceDefaultSAPEntries(long companyId, long userId)
		throws PortalException {

		Class<?> clazz = getClass();

		ResourceBundleLoader resourceBundleLoader =
			new AggregateResourceBundleLoader(
				new ClassResourceBundleLoader(
					"content.Language", clazz.getClassLoader()),
				LanguageResources.PORTAL_RESOURCE_BUNDLE_LOADER);

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
					resourceBundleLoader,
					"public-access-to-the-commerce-service-apis");

			_sapEntryLocalService.addSAPEntry(
				userId, sapEntryObjectArray[1], true, true, sapEntryName,
				titleMap, new ServiceContext());
		}
	}

	private void _removeCommerceDefaultSAPEntries(long companyId)
		throws PortalException {

		List<SAPEntry> companySAPEntries =
			_sapEntryLocalService.getCompanySAPEntries(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (SAPEntry sapEntry : companySAPEntries) {
			if (Objects.equals(
					sapEntry.getName(),
					CommerceSAPConstants.COMMERCE_SAP_ENTRY_NAME)) {

				_sapEntryLocalService.deleteSAPEntry(sapEntry);
			}
		}
	}

	@Reference
	private Portal _portal;

	@Reference
	private SAPEntryLocalService _sapEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
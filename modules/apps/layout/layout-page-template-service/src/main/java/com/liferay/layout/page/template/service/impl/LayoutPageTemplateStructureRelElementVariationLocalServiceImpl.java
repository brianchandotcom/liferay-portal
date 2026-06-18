/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.service.impl;

import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRelElementVariation;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateStructureRelElementVariationLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(
	property = "model.class.name=com.liferay.layout.page.template.model.LayoutPageTemplateStructureRelElementVariation",
	service = AopService.class
)
public class LayoutPageTemplateStructureRelElementVariationLocalServiceImpl
	extends LayoutPageTemplateStructureRelElementVariationLocalServiceBaseImpl {

	public LayoutPageTemplateStructureRelElementVariation
			addLayoutPageTemplateStructureRelElementVariation(
				String externalReferenceCode, long userId, long groupId,
				String audienceEntryERC, Map<Locale, String> hideMap,
				Map<Locale, String> htmlMap, Map<Locale, String> jsMap,
				String name, long plid, String segmentsExperienceERC,
				String targetElement, ServiceContext serviceContext)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		LayoutPageTemplateStructureRelElementVariation
			layoutPageTemplateStructureRelElementVariation =
				layoutPageTemplateStructureRelElementVariationPersistence.
					create(counterLocalService.increment());

		layoutPageTemplateStructureRelElementVariation.setUuid(
			serviceContext.getUuid());
		layoutPageTemplateStructureRelElementVariation.setExternalReferenceCode(
			externalReferenceCode);
		layoutPageTemplateStructureRelElementVariation.setGroupId(groupId);
		layoutPageTemplateStructureRelElementVariation.setCompanyId(
			user.getCompanyId());
		layoutPageTemplateStructureRelElementVariation.setUserId(
			user.getUserId());
		layoutPageTemplateStructureRelElementVariation.setUserName(
			user.getFullName());
		layoutPageTemplateStructureRelElementVariation.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		layoutPageTemplateStructureRelElementVariation.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		layoutPageTemplateStructureRelElementVariation.setAudienceEntryERC(
			audienceEntryERC);
		layoutPageTemplateStructureRelElementVariation.setHideMap(hideMap);
		layoutPageTemplateStructureRelElementVariation.setHtmlMap(htmlMap);
		layoutPageTemplateStructureRelElementVariation.setJsMap(jsMap);
		layoutPageTemplateStructureRelElementVariation.setName(name);
		layoutPageTemplateStructureRelElementVariation.setPlid(plid);
		layoutPageTemplateStructureRelElementVariation.setSegmentsExperienceERC(
			segmentsExperienceERC);
		layoutPageTemplateStructureRelElementVariation.setTargetElement(
			targetElement);

		return layoutPageTemplateStructureRelElementVariationPersistence.update(
			layoutPageTemplateStructureRelElementVariation);
	}

	public void deleteLayoutPageTemplateStructureRelElementVariations(
		long plid) {

		layoutPageTemplateStructureRelElementVariationPersistence.removeByPlid(
			plid);
	}

	@Reference
	private UserLocalService _userLocalService;

}
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

package com.liferay.commerce.product.internal.exportimport.staged.model.repository;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.commerce.product.model.CPDefinition",
	service = StagedModelRepository.class
)
public class CPDefinitionStagedModelRepository
	extends BaseStagedModelRepository<CPDefinition> {

	@Override
	public CPDefinition addStagedModel(
			PortletDataContext portletDataContext, CPDefinition cpDefinition)
		throws PortalException {

		long userId = portletDataContext.getUserId(cpDefinition.getUserUuid());

		User user = _userLocalService.getUser(userId);

		Date displayDate = cpDefinition.getDisplayDate();

		int displayDateMonth = 0;
		int displayDateDay = 0;
		int displayDateYear = 0;
		int displayDateHour = 0;
		int displayDateMinute = 0;

		if (displayDate != null) {
			Calendar displayCal = CalendarFactoryUtil.getCalendar(
				user.getTimeZone());

			displayCal.setTime(displayDate);

			displayDateMonth = displayCal.get(Calendar.MONTH);
			displayDateDay = displayCal.get(Calendar.DATE);
			displayDateYear = displayCal.get(Calendar.YEAR);
			displayDateHour = displayCal.get(Calendar.HOUR);
			displayDateMinute = displayCal.get(Calendar.MINUTE);

			if (displayCal.get(Calendar.AM_PM) == Calendar.PM) {
				displayDateHour += 12;
			}
		}

		Date expirationDate = cpDefinition.getExpirationDate();

		int expirationDateMonth = 0;
		int expirationDateDay = 0;
		int expirationDateYear = 0;
		int expirationDateHour = 0;
		int expirationDateMinute = 0;
		boolean neverExpire = true;

		if (expirationDate != null) {
			Calendar expirationCal = CalendarFactoryUtil.getCalendar(
				user.getTimeZone());

			expirationCal.setTime(expirationDate);

			expirationDateMonth = expirationCal.get(Calendar.MONTH);
			expirationDateDay = expirationCal.get(Calendar.DATE);
			expirationDateYear = expirationCal.get(Calendar.YEAR);
			expirationDateHour = expirationCal.get(Calendar.HOUR);
			expirationDateMinute = expirationCal.get(Calendar.MINUTE);

			neverExpire = false;

			if (expirationCal.get(Calendar.AM_PM) == Calendar.PM) {
				expirationDateHour += 12;
			}
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpDefinition);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(cpDefinition.getUuid());
		}

		return _cpDefinitionLocalService.addCPDefinition(
			cpDefinition.getTitleMap(), cpDefinition.getShortDescriptionMap(),
			cpDefinition.getDescriptionMap(), cpDefinition.getUrlTitleMap(),
			cpDefinition.getMetaTitleMap(), cpDefinition.getMetaKeywordsMap(),
			cpDefinition.getMetaDescriptionMap(),
			cpDefinition.getProductTypeName(),
			cpDefinition.isIgnoreSKUCombinations(), cpDefinition.isShippable(),
			cpDefinition.isFreeShipping(), cpDefinition.isShipSeparately(),
			cpDefinition.getShippingExtraPrice(), cpDefinition.getWidth(),
			cpDefinition.getHeight(), cpDefinition.getDepth(),
			cpDefinition.getWeight(), cpDefinition.getDDMStructureKey(),
			cpDefinition.getPublished(), displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, false,
			serviceContext);
	}

	@Override
	public void deleteStagedModel(CPDefinition cpDefinition)
		throws PortalException {

		_cpDefinitionLocalService.deleteCPDefinition(cpDefinition);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CPDefinition cpDefinition = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (cpDefinition != null) {
			deleteStagedModel(cpDefinition);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_cpDefinitionLocalService.deleteCPDefinitions(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public CPDefinition fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _cpDefinitionLocalService.fetchCPDefinitionByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<CPDefinition> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _cpDefinitionLocalService.getCPDefinitionsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<CPDefinition>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _cpDefinitionLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, CPDefinition cpDefinition)
		throws PortletDataException {

		long userId = portletDataContext.getUserId(cpDefinition.getUserUuid());

		CPDefinition existingCPDefinition = fetchStagedModelByUuidAndGroupId(
			cpDefinition.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingCPDefinition == null) ||
			!isStagedModelInTrash(existingCPDefinition)) {

			return;
		}

		TrashHandler trashHandler = existingCPDefinition.getTrashHandler();

		try {
			if (trashHandler.isRestorable(
					existingCPDefinition.getCPDefinitionId())) {

				trashHandler.restoreTrashEntry(
					userId, existingCPDefinition.getCPDefinitionId());
			}
		}
		catch (PortalException pe) {
			throw new PortletDataException(pe);
		}
	}

	@Override
	public CPDefinition saveStagedModel(CPDefinition cpDefinition)
		throws PortalException {

		return _cpDefinitionLocalService.updateCPDefinition(cpDefinition);
	}

	@Override
	public CPDefinition updateStagedModel(
			PortletDataContext portletDataContext, CPDefinition cpDefinition)
		throws PortalException {

		long userId = portletDataContext.getUserId(cpDefinition.getUserUuid());

		User user = _userLocalService.getUser(userId);

		Date displayDate = cpDefinition.getDisplayDate();

		int displayDateMonth = 0;
		int displayDateDay = 0;
		int displayDateYear = 0;
		int displayDateHour = 0;
		int displayDateMinute = 0;

		if (displayDate != null) {
			Calendar displayCal = CalendarFactoryUtil.getCalendar(
				user.getTimeZone());

			displayCal.setTime(displayDate);

			displayDateMonth = displayCal.get(Calendar.MONTH);
			displayDateDay = displayCal.get(Calendar.DATE);
			displayDateYear = displayCal.get(Calendar.YEAR);
			displayDateHour = displayCal.get(Calendar.HOUR);
			displayDateMinute = displayCal.get(Calendar.MINUTE);

			if (displayCal.get(Calendar.AM_PM) == Calendar.PM) {
				displayDateHour += 12;
			}
		}

		Date expirationDate = cpDefinition.getExpirationDate();

		int expirationDateMonth = 0;
		int expirationDateDay = 0;
		int expirationDateYear = 0;
		int expirationDateHour = 0;
		int expirationDateMinute = 0;
		boolean neverExpire = true;

		if (expirationDate != null) {
			Calendar expirationCal = CalendarFactoryUtil.getCalendar(
				user.getTimeZone());

			expirationCal.setTime(expirationDate);

			expirationDateMonth = expirationCal.get(Calendar.MONTH);
			expirationDateDay = expirationCal.get(Calendar.DATE);
			expirationDateYear = expirationCal.get(Calendar.YEAR);
			expirationDateHour = expirationCal.get(Calendar.HOUR);
			expirationDateMinute = expirationCal.get(Calendar.MINUTE);

			neverExpire = false;

			if (expirationCal.get(Calendar.AM_PM) == Calendar.PM) {
				expirationDateHour += 12;
			}
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpDefinition);

		return _cpDefinitionLocalService.updateCPDefinition(
			cpDefinition.getCPDefinitionId(), cpDefinition.getTitleMap(),
			cpDefinition.getShortDescriptionMap(),
			cpDefinition.getDescriptionMap(), cpDefinition.getUrlTitleMap(),
			cpDefinition.getMetaTitleMap(), cpDefinition.getMetaKeywordsMap(),
			cpDefinition.getMetaDescriptionMap(),
			cpDefinition.isIgnoreSKUCombinations(), cpDefinition.isShippable(),
			cpDefinition.isFreeShipping(), cpDefinition.isShipSeparately(),
			cpDefinition.getShippingExtraPrice(), cpDefinition.getWidth(),
			cpDefinition.getHeight(), cpDefinition.getDepth(),
			cpDefinition.getWeight(), cpDefinition.getDDMStructureKey(),
			cpDefinition.getPublished(), displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
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

package com.liferay.commerce.internal.exportimport.staged.model.repository;

import com.liferay.commerce.model.CommercePriceList;
import com.liferay.commerce.service.CommercePriceListLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.commerce.model.CommercePriceList",
	service = StagedModelRepository.class
)
public class CommercePriceListStagedModelRepository
	extends BaseStagedModelRepository<CommercePriceList> {

	@Override
	public CommercePriceList addStagedModel(
			PortletDataContext portletDataContext,
			CommercePriceList commercePriceList)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			commercePriceList.getUserUuid());

		User user = _userLocalService.getUser(userId);

		Date displayDate = commercePriceList.getDisplayDate();

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

		Date expirationDate = commercePriceList.getExpirationDate();

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
			commercePriceList);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(commercePriceList.getUuid());
		}

		return _commercePriceListLocalService.addCommercePriceList(
			commercePriceList.getCommerceCurrencyId(),
			commercePriceList.getName(), commercePriceList.getPriority(),
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public void deleteStagedModel(CommercePriceList commercePriceList)
		throws PortalException {

		_commercePriceListLocalService.deleteCommercePriceList(
			commercePriceList);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CommercePriceList commercePriceList = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (commercePriceList != null) {
			deleteStagedModel(commercePriceList);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public CommercePriceList fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _commercePriceListLocalService.
			fetchCommercePriceListByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<CommercePriceList> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _commercePriceListLocalService.
			getCommercePriceListsByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<CommercePriceList>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _commercePriceListLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public CommercePriceList saveStagedModel(
			CommercePriceList commercePriceList)
		throws PortalException {

		return _commercePriceListLocalService.updateCommercePriceList(
			commercePriceList);
	}

	@Override
	public CommercePriceList updateStagedModel(
			PortletDataContext portletDataContext,
			CommercePriceList commercePriceList)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			commercePriceList.getUserUuid());

		User user = _userLocalService.getUser(userId);

		Date displayDate = commercePriceList.getDisplayDate();

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

		Date expirationDate = commercePriceList.getExpirationDate();

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
			commercePriceList);

		return _commercePriceListLocalService.updateCommercePriceList(
			commercePriceList.getCommercePriceListId(),
			commercePriceList.getCommerceCurrencyId(),
			commercePriceList.getName(), commercePriceList.getPriority(),
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Reference
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
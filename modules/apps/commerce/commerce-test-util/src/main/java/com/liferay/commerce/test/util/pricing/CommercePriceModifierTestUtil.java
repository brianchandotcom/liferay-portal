/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.test.util.pricing;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalServiceUtil;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalServiceUtil;
import com.liferay.commerce.pricing.constants.CommercePriceModifierConstants;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.model.CommercePriceModifierRel;
import com.liferay.commerce.pricing.service.CommercePriceModifierLocalServiceUtil;
import com.liferay.commerce.pricing.service.CommercePriceModifierRelLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.math.BigDecimal;

import java.util.Calendar;

/**
 * @author Riccardo Alberti
 * @author Crescenzo Rega
 */
public class CommercePriceModifierTestUtil {

	public static CommercePriceList addCommercePriceList(
			long groupId, double priority)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		CommerceCurrency commerceCurrency =
			CommerceCurrencyTestUtil.addCommerceCurrency(
				serviceContext.getCompanyId());

		return addCommercePriceList(
			groupId, commerceCurrency.getCommerceCurrencyId(), priority);
	}

	public static CommercePriceList addCommercePriceList(
			long groupId, long commerceCurrencyId, double priority)
		throws Exception {

		CommerceCurrency commerceCurrency =
			CommerceCurrencyLocalServiceUtil.getCommerceCurrency(
				commerceCurrencyId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		User user = UserLocalServiceUtil.getGuestUser(
			serviceContext.getCompanyId());

		Calendar calendar = CalendarFactoryUtil.getCalendar(user.getTimeZone());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		return CommercePriceListLocalServiceUtil.addCommercePriceList(
			null, user.getUserId(), groupId, 0, false,
			commerceCurrency.getCode(), calendar.get(Calendar.DAY_OF_MONTH),
			calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
			calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR),
			calendar.get(Calendar.DAY_OF_MONTH),
			calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
			calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR),
			RandomTestUtil.randomString(), true, true, priority,
			CommercePriceListConstants.TYPE_PRICE_LIST, serviceContext);
	}

	public static CommercePriceModifier addCommercePriceModifier(
			long groupId, long commercePriceListId, BigDecimal amount,
			String type, boolean neverExpire)
		throws PortalException {

		return addCommercePriceModifier(
			groupId, commercePriceListId,
			CommercePriceModifierConstants.TARGET_CATALOG, amount, type,
			neverExpire);
	}

	public static CommercePriceModifier addCommercePriceModifier(
			long groupId, long commercePriceListId, String target,
			BigDecimal amount, String type, boolean neverExpire)
		throws PortalException {

		return addCommercePriceModifier(
			groupId, commercePriceListId, RandomTestUtil.randomString(), target,
			amount, type, neverExpire);
	}

	public static CommercePriceModifier addCommercePriceModifier(
			long groupId, long commercePriceListId, String title, String target,
			BigDecimal amount, String type, boolean neverExpire)
		throws PortalException {

		return addCommercePriceModifier(
			groupId, commercePriceListId, title, target, amount, type, 0.0,
			neverExpire);
	}

	public static CommercePriceModifier addCommercePriceModifier(
			long groupId, long commercePriceListId, String title, String target,
			BigDecimal amount, String type, double priority,
			boolean neverExpire)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		User user = UserLocalServiceUtil.getGuestUser(
			serviceContext.getCompanyId());

		Calendar calendar = CalendarFactoryUtil.getCalendar(user.getTimeZone());

		return CommercePriceModifierLocalServiceUtil.
			addOrUpdateCommercePriceModifier(
				null, 0L, groupId, commercePriceListId, title, target, amount,
				type, priority, true, calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), neverExpire, serviceContext);
	}

	public static CommercePriceModifierRel addCommercePriceModifierRel(
			long groupId, long commercePriceModifierId, String className,
			long classPK)
		throws PortalException {

		return CommercePriceModifierRelLocalServiceUtil.
			addCommercePriceModifierRel(
				commercePriceModifierId, className, classPK,
				ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static CommercePriceModifier updateCommercePriceModifier(
			long groupId, long commercePriceModifierId,
			long commercePriceListId)
		throws PortalException {

		CommercePriceModifier commercePriceModifier =
			CommercePriceModifierLocalServiceUtil.getCommercePriceModifier(
				commercePriceModifierId);

		return updateCommercePriceModifier(
			groupId, commercePriceModifierId, commercePriceListId,
			commercePriceModifier.getTarget());
	}

	public static CommercePriceModifier updateCommercePriceModifier(
			long groupId, long commercePriceModifierId,
			long commercePriceListId, String target)
		throws PortalException {

		CommercePriceModifier commercePriceModifier =
			CommercePriceModifierLocalServiceUtil.getCommercePriceModifier(
				commercePriceModifierId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		User user = UserLocalServiceUtil.getGuestUser(
			serviceContext.getCompanyId());

		Calendar calendar = CalendarFactoryUtil.getCalendar(user.getTimeZone());

		return CommercePriceModifierLocalServiceUtil.
			updateCommercePriceModifier(
				commercePriceModifier.getCommercePriceModifierId(),
				commercePriceModifier.getGroupId(), commercePriceListId,
				commercePriceModifier.getTitle(), target,
				commercePriceModifier.getModifierAmount(),
				commercePriceModifier.getModifierType(),
				commercePriceModifier.getPriority(),
				commercePriceModifier.isActive(), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), true, serviceContext);
	}

	public static CommercePriceModifier updateCommercePriceModifier(
			long groupId, long commercePriceModifierId, String target)
		throws PortalException {

		CommercePriceModifier commercePriceModifier =
			CommercePriceModifierLocalServiceUtil.getCommercePriceModifier(
				commercePriceModifierId);

		return updateCommercePriceModifier(
			groupId, commercePriceModifierId,
			commercePriceModifier.getCommercePriceListId(), target);
	}

}
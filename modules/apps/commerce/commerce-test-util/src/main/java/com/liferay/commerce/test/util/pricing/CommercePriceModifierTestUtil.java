/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.test.util.pricing;

import com.liferay.commerce.pricing.constants.CommercePriceModifierConstants;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.service.CommercePriceModifierLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;

import java.math.BigDecimal;

import java.util.Calendar;

/**
 * @author Riccardo Alberti
 * @author Crescenzo Rega
 */
public class CommercePriceModifierTestUtil {

	public static CommercePriceModifier addCommercePriceModifier(
			long groupId, User user, long commercePriceListId,
			BigDecimal amount, String type, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		return addCommercePriceModifier(
			groupId, user, commercePriceListId,
			CommercePriceModifierConstants.TARGET_CATALOG, amount, type,
			neverExpire, serviceContext);
	}

	public static CommercePriceModifier addCommercePriceModifier(
			long groupId, User user, long commercePriceListId, String target,
			BigDecimal amount, String type, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		return addCommercePriceModifier(
			groupId, user, commercePriceListId, RandomTestUtil.randomString(),
			target, amount, type, neverExpire, serviceContext);
	}

	public static CommercePriceModifier addCommercePriceModifier(
			long groupId, User user, long commercePriceListId, String title,
			String target, BigDecimal amount, String type, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		return addCommercePriceModifier(
			groupId, user, commercePriceListId, title, target, amount, type,
			0.0, neverExpire, serviceContext);
	}

	public static CommercePriceModifier addCommercePriceModifier(
			long groupId, User user, long commercePriceListId, String title,
			String target, BigDecimal amount, String type, double priority,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

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

	public static CommercePriceModifier updateCommercePriceModifier(
			User user, long commercePriceModifierId, String target,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePriceModifier commercePriceModifier =
			CommercePriceModifierLocalServiceUtil.getCommercePriceModifier(
				commercePriceModifierId);

		Calendar calendar = CalendarFactoryUtil.getCalendar(user.getTimeZone());

		return CommercePriceModifierLocalServiceUtil.
			updateCommercePriceModifier(
				commercePriceModifier.getCommercePriceModifierId(),
				commercePriceModifier.getGroupId(),
				commercePriceModifier.getCommercePriceListId(),
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

}
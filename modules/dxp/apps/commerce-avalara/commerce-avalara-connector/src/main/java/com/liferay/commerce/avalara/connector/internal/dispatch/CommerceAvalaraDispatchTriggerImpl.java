/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.avalara.connector.internal.dispatch;

import com.liferay.commerce.avalara.connector.dispatch.CommerceAvalaraDispatchTrigger;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.dispatch.constants.DispatchConstants;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchLogLocalService;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Katie Nesterovich
 */
@Component(service = CommerceAvalaraDispatchTrigger.class)
public class CommerceAvalaraDispatchTriggerImpl
	implements CommerceAvalaraDispatchTrigger {

	@Override
	public DispatchTrigger addDispatchTrigger(
		CommerceTaxMethod commerceTaxMethod) {

		DispatchTrigger dispatchTrigger = null;

		try {
			dispatchTrigger = _dispatchTriggerLocalService.addDispatchTrigger(
				null, commerceTaxMethod.getUserId(),
				commerceTaxMethod.getEngineKey(),
				UnicodePropertiesBuilder.create(
					true
				).setProperty(
					"groupId", String.valueOf(commerceTaxMethod.getGroupId())
				).build(),
				_getTriggerName(commerceTaxMethod), Boolean.FALSE);

			dispatchTrigger.setActive(Boolean.TRUE);
			dispatchTrigger.setCronExpression("0 0 0 1 * ?");

			dispatchTrigger =
				_dispatchTriggerLocalService.updateDispatchTrigger(
					dispatchTrigger);
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to add dispatch trigger for " + commerceTaxMethod,
				portalException);
		}

		return dispatchTrigger;
	}

	@Override
	public void deleteDispatchTrigger(CommerceTaxMethod commerceTaxMethod) {
		try {
			DispatchTrigger dispatchTrigger = _getDispatchTrigger(
				commerceTaxMethod);

			if (dispatchTrigger != null) {
				_dispatchTriggerLocalService.deleteDispatchTrigger(
					dispatchTrigger.getDispatchTriggerId());
			}
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable not delete dispatch trigger for " + commerceTaxMethod,
				portalException);
		}
	}

	@Override
	public DispatchLog getLatestDispatchLog(
		CommerceTaxMethod commerceTaxMethod) {

		DispatchTrigger dispatchTrigger = _getDispatchTrigger(
			commerceTaxMethod);

		if (dispatchTrigger == null) {
			return null;
		}

		DynamicQuery dynamicQuery = _getDispatchTriggerByIdQuery(
			dispatchTrigger);

		Property startDateProperty = PropertyFactoryUtil.forName("startDate");

		dynamicQuery.addOrder(startDateProperty.desc());

		dynamicQuery.setLimit(0, 1);

		List<DispatchLog> dispatchLogs = _dispatchLogLocalService.dynamicQuery(
			dynamicQuery);

		if (!dispatchLogs.isEmpty()) {
			return dispatchLogs.get(0);
		}

		return null;
	}

	@Override
	public boolean isJobPreviouslyRun(CommerceTaxMethod commerceTaxMethod) {
		DispatchTrigger dispatchTrigger = _getDispatchTrigger(
			commerceTaxMethod);

		if (dispatchTrigger == null) {
			return false;
		}

		DynamicQuery dynamicQuery = _getDispatchTriggerByIdQuery(
			dispatchTrigger);

		if (_dispatchLogLocalService.dynamicQueryCount(dynamicQuery) > 0) {
			return true;
		}

		return false;
	}

	@Override
	public void runJob(CommerceTaxMethod commerceTaxMethod) {
		DispatchTrigger dispatchTrigger = _getDispatchTrigger(
			commerceTaxMethod);

		if (dispatchTrigger == null) {
			dispatchTrigger = addDispatchTrigger(commerceTaxMethod);
		}

		_sendMessage(dispatchTrigger.getDispatchTriggerId());
	}

	@Override
	public DispatchTrigger updateDispatchTrigger(
		CommerceTaxMethod commerceTaxMethod) {

		DispatchTrigger dispatchTrigger = _getDispatchTrigger(
			commerceTaxMethod);

		if (dispatchTrigger != null) {
			dispatchTrigger.setActive(commerceTaxMethod.isActive());

			dispatchTrigger =
				_dispatchTriggerLocalService.updateDispatchTrigger(
					dispatchTrigger);
		}

		return dispatchTrigger;
	}

	private CommerceChannel _getAssociatedCommerceChannel(
		CommerceTaxMethod commerceTaxMethod) {

		CommerceChannel commerceChannel = null;

		try {
			commerceChannel =
				_commerceChannelLocalService.getCommerceChannelByGroupId(
					commerceTaxMethod.getGroupId());
		}
		catch (PortalException portalException) {
			_log.error("Could not get commerce channel", portalException);
		}

		return commerceChannel;
	}

	private DispatchTrigger _getDispatchTrigger(
		CommerceTaxMethod commerceTaxMethod) {

		return _dispatchTriggerLocalService.fetchDispatchTrigger(
			commerceTaxMethod.getCompanyId(),
			_getTriggerName(commerceTaxMethod));
	}

	private DynamicQuery _getDispatchTriggerByIdQuery(
		DispatchTrigger dispatchTrigger) {

		DynamicQuery dynamicQuery = _dispatchLogLocalService.dynamicQuery();

		Property dispatchTriggerIdProperty = PropertyFactoryUtil.forName(
			"dispatchTriggerId");

		dynamicQuery.add(
			dispatchTriggerIdProperty.eq(
				dispatchTrigger.getDispatchTriggerId()));

		return dynamicQuery;
	}

	private String _getTriggerName(CommerceTaxMethod commerceTaxMethod) {
		CommerceChannel commerceChannel = _getAssociatedCommerceChannel(
			commerceTaxMethod);

		return "avalara-" + commerceChannel.getCommerceChannelId();
	}

	private void _sendMessage(long dispatchTriggerId) {
		Message message = new Message();

		message.setPayload(
			JSONUtil.put(
				"dispatchTriggerId", dispatchTriggerId
			).toString());

		_destination.send(message);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceAvalaraDispatchTriggerImpl.class);

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference(
		target = "(destination.name=" + DispatchConstants.EXECUTOR_DESTINATION_NAME + ")"
	)
	private Destination _destination;

	@Reference
	private DispatchLogLocalService _dispatchLogLocalService;

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

}
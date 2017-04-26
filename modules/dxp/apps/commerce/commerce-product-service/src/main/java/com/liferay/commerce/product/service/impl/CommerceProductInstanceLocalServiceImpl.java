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

package com.liferay.commerce.product.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.exception.CommerceProductInstanceDisplayDateException;
import com.liferay.commerce.product.exception.CommerceProductInstanceExpirationDateException;
import com.liferay.commerce.product.model.CommerceProductInstance;
import com.liferay.commerce.product.service.base.CommerceProductInstanceLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marco Leo
 */
@ProviderType
public class CommerceProductInstanceLocalServiceImpl
	extends CommerceProductInstanceLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceProductInstance addCommerceProductInstance(
			long commerceProductDefinitionId, String sku, String ddmContent,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product instance

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		Date displayDate = null;
		Date expirationDate = null;
		Date now = new Date();

		displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			CommerceProductInstanceDisplayDateException.class);

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				CommerceProductInstanceExpirationDateException.class);
		}

		long commerceProductInstanceId = counterLocalService.increment();

		CommerceProductInstance commerceProductInstance =
			commerceProductInstancePersistence.create(
				commerceProductInstanceId);

		commerceProductInstance.setUuid(serviceContext.getUuid());
		commerceProductInstance.setGroupId(groupId);
		commerceProductInstance.setCompanyId(user.getCompanyId());
		commerceProductInstance.setUserId(user.getUserId());
		commerceProductInstance.setUserName(user.getFullName());
		commerceProductInstance.setCommerceProductDefinitionId(
			commerceProductDefinitionId);
		commerceProductInstance.setSku(sku);
		commerceProductInstance.setDDMContent(ddmContent);
		commerceProductInstance.setDisplayDate(displayDate);
		commerceProductInstance.setExpirationDate(expirationDate);

		if ((expirationDate == null) || expirationDate.after(now)) {
			commerceProductInstance.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			commerceProductInstance.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		commerceProductInstance.setStatusByUserId(user.getUserId());
		commerceProductInstance.setStatusDate(
			serviceContext.getModifiedDate(now));
		commerceProductInstance.setExpandoBridgeAttributes(serviceContext);

		commerceProductInstancePersistence.update(commerceProductInstance);

		// Workflow

		return startWorkflowInstance(
			user.getUserId(), commerceProductInstance, serviceContext);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceProductInstance deleteCommerceProductInstance(
			CommerceProductInstance commerceProductInstance)
		throws PortalException {

		// Commerce product instance

		commerceProductInstancePersistence.remove(commerceProductInstance);

		// Expando

		expandoRowLocalService.deleteRows(
			commerceProductInstance.getCommerceProductInstanceId());

		// Workflow

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			commerceProductInstance.getCompanyId(),
			commerceProductInstance.getGroupId(),
			CommerceProductInstance.class.getName(),
			commerceProductInstance.getCommerceProductInstanceId());

		return commerceProductInstance;
	}

	@Override
	public CommerceProductInstance deleteCommerceProductInstance(
			long commerceProductInstanceId)
		throws PortalException {

		CommerceProductInstance commerceProductInstance =
			commerceProductInstancePersistence.findByPrimaryKey(
				commerceProductInstanceId);

		return commerceProductInstanceLocalService.
			deleteCommerceProductInstance(commerceProductInstance);
	}

	@Override
	public void deleteCommerceProductInstances(long commerceProductDefinitionId)
		throws PortalException {

		List<CommerceProductInstance> commerceProductInstances =
			commerceProductInstanceLocalService.getCommerceProductInstances(
				commerceProductDefinitionId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (CommerceProductInstance commerceProductInstance
				: commerceProductInstances) {

			commerceProductInstanceLocalService.deleteCommerceProductInstance(
				commerceProductInstance);
		}
	}

	@Override
	public List<CommerceProductInstance> getCommerceProductInstances(
		long commerceProductDefinitionId, int start, int end) {

		return commerceProductInstancePersistence.
			findByCommerceProductDefinitionId(
				commerceProductDefinitionId, start, end);
	}

	@Override
	public List<CommerceProductInstance> getCommerceProductInstances(
		long commerceProductDefinitionId, int start, int end,
		OrderByComparator<CommerceProductInstance> orderByComparator) {

		return commerceProductInstancePersistence.
			findByCommerceProductDefinitionId(
				commerceProductDefinitionId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceProductInstancesCount(
		long commerceProductDefinitionId) {

		return commerceProductInstancePersistence.
			countByCommerceProductDefinitionId(commerceProductDefinitionId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceProductInstance updateCommerceProductInstance(
			long commerceProductInstanceId, String sku, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product instance

		User user = userLocalService.getUser(serviceContext.getUserId());
		CommerceProductInstance commerceProductInstance =
			commerceProductInstancePersistence.findByPrimaryKey(
				commerceProductInstanceId);

		Date displayDate = null;
		Date expirationDate = null;
		Date now = new Date();

		displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			CommerceProductInstanceDisplayDateException.class);

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				CommerceProductInstanceExpirationDateException.class);
		}

		commerceProductInstance.setSku(sku);
		commerceProductInstance.setDisplayDate(displayDate);
		commerceProductInstance.setExpirationDate(expirationDate);

		if ((expirationDate == null) || expirationDate.after(now)) {
			commerceProductInstance.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			commerceProductInstance.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		commerceProductInstance.setStatusByUserId(user.getUserId());
		commerceProductInstance.setStatusDate(
			serviceContext.getModifiedDate(now));
		commerceProductInstance.setExpandoBridgeAttributes(serviceContext);

		commerceProductInstancePersistence.update(commerceProductInstance);

		// Workflow

		return startWorkflowInstance(
			user.getUserId(), commerceProductInstance, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceProductInstance updateStatus(
			long userId, long commerceProductInstanceId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		// Commerce product instance

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		CommerceProductInstance commerceProductInstance =
			commerceProductInstancePersistence.findByPrimaryKey(
				commerceProductInstanceId);

		if ((status == WorkflowConstants.STATUS_APPROVED) &&
			(commerceProductInstance.getDisplayDate() != null) &&
			now.before(commerceProductInstance.getDisplayDate())) {

			status = WorkflowConstants.STATUS_SCHEDULED;
		}

		Date modifiedDate = serviceContext.getModifiedDate(now);

		commerceProductInstance.setModifiedDate(modifiedDate);

		if (status == WorkflowConstants.STATUS_APPROVED) {
			Date expirationDate = commerceProductInstance.getExpirationDate();

			if ((expirationDate != null) && expirationDate.before(now)) {
				commerceProductInstance.setExpirationDate(null);
			}
		}

		if (status == WorkflowConstants.STATUS_EXPIRED) {
			commerceProductInstance.setExpirationDate(now);
		}

		commerceProductInstance.setStatus(status);
		commerceProductInstance.setStatusByUserId(user.getUserId());
		commerceProductInstance.setStatusByUserName(user.getFullName());
		commerceProductInstance.setStatusDate(modifiedDate);

		commerceProductInstancePersistence.update(commerceProductInstance);

		return commerceProductInstance;
	}

	protected CommerceProductInstance startWorkflowInstance(
			long userId, CommerceProductInstance commerceProductInstance,
			ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext = new HashMap<>();

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			commerceProductInstance.getCompanyId(),
			commerceProductInstance.getGroupId(), userId,
			CommerceProductInstance.class.getName(),
			commerceProductInstance.getCommerceProductInstanceId(),
			commerceProductInstance, serviceContext, workflowContext);
	}

}
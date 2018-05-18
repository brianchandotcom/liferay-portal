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

package com.liferay.commerce.notification.service.impl;

import com.liferay.commerce.notification.model.CommerceNotificationQueue;
import com.liferay.commerce.notification.service.base.CommerceNotificationQueueLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceNotificationQueueLocalServiceImpl
	extends CommerceNotificationQueueLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceNotificationQueue addCommerceNotificationQueue(
			long commerceNotificationTemplateId, String from, String fromName,
			String to, String toName, String cc, String bcc,
			Map<Locale, String> subjectMap, Map<Locale, String> bodyMap,
			double priority, boolean sent, ServiceContext serviceContext)
		throws PortalException {

		// Commerce notification queue

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceNotificationQueueId = counterLocalService.increment();

		CommerceNotificationQueue commerceNotificationQueue =
			commerceNotificationQueuePersistence.create(
				commerceNotificationQueueId);

		commerceNotificationQueue.setUuid(serviceContext.getUuid());
		commerceNotificationQueue.setGroupId(groupId);
		commerceNotificationQueue.setCompanyId(user.getCompanyId());
		commerceNotificationQueue.setUserId(user.getUserId());
		commerceNotificationQueue.setUserName(user.getFullName());
		commerceNotificationQueue.setCommerceNotificationTemplateId(
			commerceNotificationTemplateId);
		commerceNotificationQueue.setFrom(from);
		commerceNotificationQueue.setFromName(fromName);
		commerceNotificationQueue.setTo(to);
		commerceNotificationQueue.setToName(toName);
		commerceNotificationQueue.setCc(cc);
		commerceNotificationQueue.setBcc(bcc);
		commerceNotificationQueue.setSubjectMap(subjectMap);
		commerceNotificationQueue.setBodyMap(bodyMap);
		commerceNotificationQueue.setPriority(priority);
		commerceNotificationQueue.setSent(sent);

		commerceNotificationQueuePersistence.update(commerceNotificationQueue);

		// Resources

		resourceLocalService.addModelResources(
			commerceNotificationQueue, serviceContext);

		return commerceNotificationQueue;
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceNotificationQueue deleteCommerceNotificationQueue(
			CommerceNotificationQueue commerceNotificationQueue)
		throws PortalException {

		// Commerce notification attachments

		commerceNotificationAttachmentLocalService.
			deleteCommerceNotificationAttachments(
				commerceNotificationQueue.getCommerceNotificationQueueId());

		// Commerce notification queue

		commerceNotificationQueuePersistence.remove(commerceNotificationQueue);

		// Resources

		resourceLocalService.deleteResource(
			commerceNotificationQueue.getCompanyId(),
			CommerceNotificationQueue.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			commerceNotificationQueue.getCommerceNotificationTemplateId());

		return commerceNotificationQueue;
	}

	@Override
	public CommerceNotificationQueue deleteCommerceNotificationQueue(
			long commerceNotificationQueueId)
		throws PortalException {

		CommerceNotificationQueue commerceNotificationQueue =
			commerceNotificationQueuePersistence.findByPrimaryKey(
				commerceNotificationQueueId);

		return commerceNotificationQueueLocalService.
			deleteCommerceNotificationQueue(commerceNotificationQueue);
	}

	@Override
	public void deleteCommerceNotificationQueues(long groupId)
		throws PortalException {

		List<CommerceNotificationQueue> commerceNotificationQueues =
			commerceNotificationQueuePersistence.findByGroupId(groupId);

		for (CommerceNotificationQueue commerceNotificationQueue :
				commerceNotificationQueues) {

			commerceNotificationQueueLocalService.
				deleteCommerceNotificationQueue(commerceNotificationQueue);
		}
	}

	@Override
	public List<CommerceNotificationQueue> getCommerceNotificationQueues(
		long groupId, int start, int end,
		OrderByComparator<CommerceNotificationQueue> orderByComparator) {

		return commerceNotificationQueuePersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceNotificationQueuesCount(long groupId) {
		return commerceNotificationQueuePersistence.countByGroupId(groupId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceNotificationQueue resendCommerceNotificationQueue(
			long commerceNotificationQueueId, boolean sent,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceNotificationQueue commerceNotificationQueue =
			commerceNotificationQueuePersistence.findByPrimaryKey(
				commerceNotificationQueueId);

		return
			commerceNotificationQueueLocalService.addCommerceNotificationQueue(
				commerceNotificationQueue.getCommerceNotificationTemplateId(),
				commerceNotificationQueue.getFrom(),
				commerceNotificationQueue.getFromName(),
				commerceNotificationQueue.getTo(),
				commerceNotificationQueue.getToName(),
				commerceNotificationQueue.getCc(),
				commerceNotificationQueue.getBcc(),
				commerceNotificationQueue.getSubjectMap(),
				commerceNotificationQueue.getBodyMap(),
				commerceNotificationQueue.getPriority(), sent, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceNotificationQueue setSent(
			long commerceNotificationQueueId, boolean sent)
		throws PortalException {

		CommerceNotificationQueue commerceNotificationQueue =
			commerceNotificationQueuePersistence.findByPrimaryKey(
				commerceNotificationQueueId);

		commerceNotificationQueue.setSent(sent);

		commerceNotificationQueuePersistence.update(commerceNotificationQueue);

		return commerceNotificationQueue;
	}

	@Override
	public void updateCommerceNotificationQueueTemplateIds(
		long commerceNotificationTemplateId) {

		List<CommerceNotificationQueue> commerceNotificationQueues =
			commerceNotificationQueuePersistence.
				findByCommerceNotificationTemplateId(
					commerceNotificationTemplateId);

		for (CommerceNotificationQueue commerceNotificationQueue :
				commerceNotificationQueues) {

			updateCommerceNotificationQueue(commerceNotificationQueue, 0);
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	protected CommerceNotificationQueue updateCommerceNotificationQueue(
		CommerceNotificationQueue commerceNotificationQueue,
		long commerceNotificationTemplateId) {

		commerceNotificationQueue.setCommerceNotificationTemplateId(
			commerceNotificationTemplateId);

		commerceNotificationQueuePersistence.update(commerceNotificationQueue);

		return commerceNotificationQueue;
	}

}
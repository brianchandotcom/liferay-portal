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

package com.liferay.portal.workflow.kaleo.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.workflow.kaleo.definition.Action;
import com.liferay.portal.workflow.kaleo.definition.Node;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.definition.Notification;
import com.liferay.portal.workflow.kaleo.definition.State;
import com.liferay.portal.workflow.kaleo.definition.Timer;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.service.KaleoActionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNotificationLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTimerLocalService;
import com.liferay.portal.workflow.kaleo.service.base.KaleoNodeLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.portal.workflow.kaleo.model.KaleoNode",
	service = AopService.class
)
public class KaleoNodeLocalServiceImpl extends KaleoNodeLocalServiceBaseImpl {

	@Override
	public KaleoNode addKaleoNode(
			long kaleoDefinitionVersionId, Node node,
			ServiceContext serviceContext)
		throws PortalException {

		// Kaleo node

		User user = userLocalService.getUser(serviceContext.getGuestOrUserId());
		Date now = new Date();

		long kaleoNodeId = counterLocalService.increment();

		KaleoNode kaleoNode = kaleoNodePersistence.create(kaleoNodeId);

		kaleoNode.setCompanyId(user.getCompanyId());
		kaleoNode.setUserId(user.getUserId());
		kaleoNode.setUserName(user.getFullName());
		kaleoNode.setCreateDate(now);
		kaleoNode.setModifiedDate(now);
		kaleoNode.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
		kaleoNode.setName(node.getName());
		kaleoNode.setMetadata(node.getMetadata());
		kaleoNode.setDescription(node.getDescription());

		NodeType nodeType = node.getNodeType();

		kaleoNode.setType(nodeType.name());

		boolean initial = false;
		boolean terminal = false;

		if (nodeType.equals(NodeType.STATE)) {
			State state = (State)node;

			initial = state.isInitial();
			terminal = state.isTerminal();
		}

		kaleoNode.setInitial(initial);
		kaleoNode.setTerminal(terminal);

		kaleoNodePersistence.update(kaleoNode);

		// Kaleo actions

		Set<Action> actions = node.getActions();

		for (Action action : actions) {
			_kaleoActionLocalService.addKaleoAction(
				KaleoNode.class.getName(), kaleoNodeId,
				kaleoDefinitionVersionId, node.getName(), action,
				serviceContext);
		}

		// Kaleo notifications

		Set<Notification> notifications = node.getNotifications();

		for (Notification notification : notifications) {
			_kaleoNotificationLocalService.addKaleoNotification(
				KaleoNode.class.getName(), kaleoNodeId,
				kaleoDefinitionVersionId, node.getName(), notification,
				serviceContext);
		}

		// Kaleo timers

		Set<Timer> timers = node.getTimers();

		for (Timer timer : timers) {
			_kaleoTimerLocalService.addKaleoTimer(
				KaleoNode.class.getName(), kaleoNodeId,
				kaleoDefinitionVersionId, timer, serviceContext);
		}

		return kaleoNode;
	}

	@Override
	public void deleteCompanyKaleoNodes(long companyId) {

		// Kaleo nodes

		kaleoNodePersistence.removeByCompanyId(companyId);

		// Kaleo actions

		_kaleoActionLocalService.deleteCompanyKaleoActions(companyId);

		// Kaleo notifications

		_kaleoNotificationLocalService.deleteCompanyKaleoNotifications(
			companyId);
	}

	@Override
	public void deleteKaleoDefinitionVersionKaleoNodes(
		long kaleoDefinitionVersionId) {

		// Kaleo nodes

		kaleoNodePersistence.removeByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);

		// Kaleo actions

		_kaleoActionLocalService.deleteKaleoDefinitionVersionKaleoActions(
			kaleoDefinitionVersionId);

		// Kaleo notifications

		_kaleoNotificationLocalService.
			deleteKaleoDefinitionVersionKaleoNotifications(
				kaleoDefinitionVersionId);
	}

	@Override
	public List<KaleoNode> getKaleoDefinitionVersionKaleoNodes(
		long kaleoDefinitionVersionId) {

		return kaleoNodePersistence.findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);
	}

	@Reference
	private KaleoActionLocalService _kaleoActionLocalService;

	@Reference
	private KaleoNotificationLocalService _kaleoNotificationLocalService;

	@Reference
	private KaleoTimerLocalService _kaleoTimerLocalService;

}
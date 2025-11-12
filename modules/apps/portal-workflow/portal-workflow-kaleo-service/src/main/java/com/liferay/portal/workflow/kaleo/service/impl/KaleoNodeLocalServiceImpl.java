/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.workflow.kaleo.definition.Action;
import com.liferay.portal.workflow.kaleo.definition.Node;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.definition.Notification;
import com.liferay.portal.workflow.kaleo.definition.Setting;
import com.liferay.portal.workflow.kaleo.definition.State;
import com.liferay.portal.workflow.kaleo.definition.Timer;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.service.KaleoActionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeSettingLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNotificationLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTimerLocalService;
import com.liferay.portal.workflow.kaleo.service.base.KaleoNodeLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

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
			long kaleoDefinitionId, long kaleoDefinitionVersionId, Node node,
			ServiceContext serviceContext)
		throws PortalException {

		User user = _userLocalService.getUser(
			serviceContext.getGuestOrUserId());
		Date date = new Date();

		long kaleoNodeId = counterLocalService.increment();

		KaleoNode kaleoNode = kaleoNodePersistence.create(kaleoNodeId);

		kaleoNode.setCompanyId(user.getCompanyId());
		kaleoNode.setUserId(user.getUserId());
		kaleoNode.setUserName(user.getFullName());
		kaleoNode.setCreateDate(date);
		kaleoNode.setModifiedDate(date);
		kaleoNode.setKaleoDefinitionId(kaleoDefinitionId);
		kaleoNode.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
		kaleoNode.setName(node.getName());
		kaleoNode.setLabelMap(node.getLabelMap());
		kaleoNode.setMetadata(node.getMetadata());
		kaleoNode.setDescription(node.getDescription());

		NodeType nodeType = node.getNodeType();

		kaleoNode.setType(nodeType.name());

		if (node instanceof State state) {
			kaleoNode.setInitial(state.isInitial());
			kaleoNode.setTerminal(state.isTerminal());
		}

		kaleoNode = kaleoNodePersistence.update(kaleoNode);

		for (Action action : node.getActions()) {
			_kaleoActionLocalService.addKaleoAction(
				KaleoNode.class.getName(), kaleoNodeId, kaleoDefinitionId,
				kaleoDefinitionVersionId, node.getName(), action,
				serviceContext);
		}

		for (Notification notification : node.getNotifications()) {
			_kaleoNotificationLocalService.addKaleoNotification(
				KaleoNode.class.getName(), kaleoNodeId, kaleoDefinitionId,
				kaleoDefinitionVersionId, node.getName(), notification,
				serviceContext);
		}

		for (Setting setting : node.getSettings()) {
			_kaleoNodeSettingLocalService.addKaleoNodeSetting(
				user.getUserId(), kaleoNodeId, setting.getName(),
				setting.getValue());
		}

		for (Timer timer : node.getTimers()) {
			_kaleoTimerLocalService.addKaleoTimer(
				KaleoNode.class.getName(), kaleoNodeId, kaleoDefinitionId,
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
	private KaleoNodeSettingLocalService _kaleoNodeSettingLocalService;

	@Reference
	private KaleoNotificationLocalService _kaleoNotificationLocalService;

	@Reference
	private KaleoTimerLocalService _kaleoTimerLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
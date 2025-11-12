/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.workflow.kaleo.model.KaleoNodeSetting;
import com.liferay.portal.workflow.kaleo.service.base.KaleoNodeSettingLocalServiceBaseImpl;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoNodePersistence;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = "model.class.name=com.liferay.portal.workflow.kaleo.model.KaleoNodeSetting",
	service = AopService.class
)
public class KaleoNodeSettingLocalServiceImpl
	extends KaleoNodeSettingLocalServiceBaseImpl {

	@Override
	public KaleoNodeSetting addKaleoNodeSetting(
			long userId, long kaleoNodeId, String name, String value)
		throws PortalException {

		_kaleoNodePersistence.findByPrimaryKey(kaleoNodeId);

		KaleoNodeSetting kaleoNodeSetting = kaleoNodeSettingPersistence.create(
			counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		kaleoNodeSetting.setCompanyId(user.getCompanyId());
		kaleoNodeSetting.setUserId(user.getUserId());
		kaleoNodeSetting.setUserName(user.getFullName());

		kaleoNodeSetting.setKaleoNodeId(kaleoNodeId);
		kaleoNodeSetting.setName(name);
		kaleoNodeSetting.setValue(value);

		return kaleoNodeSettingPersistence.update(kaleoNodeSetting);
	}

	@Override
	public List<KaleoNodeSetting> getKaleoNodeSettings(long kaleoNodeId) {
		return kaleoNodeSettingPersistence.findByKaleoNodeId(kaleoNodeId);
	}

	@Reference
	private KaleoNodePersistence _kaleoNodePersistence;

	@Reference
	private UserLocalService _userLocalService;

}
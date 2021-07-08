/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.service.impl;

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting;
import com.liferay.commerce.shop.by.diagram.service.base.CPDefinitionDiagramSettingLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import org.osgi.service.component.annotations.Component;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting",
	service = AopService.class
)
public class CPDefinitionDiagramSettingLocalServiceImpl
	extends CPDefinitionDiagramSettingLocalServiceBaseImpl {

	@Override
	public CPDefinitionDiagramSetting addCPDefinitionDiagramSetting(
			long userId, long cpDefinitionId, long cpAttachmentFileEntryId,
			String color, double radius, String type)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long cpDefinitionDiagramSettingId = counterLocalService.increment();

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			cpDefinitionDiagramSettingPersistence.create(
				cpDefinitionDiagramSettingId);

		cpDefinitionDiagramSetting.setCompanyId(user.getCompanyId());
		cpDefinitionDiagramSetting.setUserId(user.getUserId());
		cpDefinitionDiagramSetting.setUserName(user.getFullName());
		cpDefinitionDiagramSetting.setCPDefinitionId(cpDefinitionId);
		cpDefinitionDiagramSetting.setCPAttachmentFileEntryId(
			cpAttachmentFileEntryId);
		cpDefinitionDiagramSetting.setColor(color);
		cpDefinitionDiagramSetting.setRadius(radius);
		cpDefinitionDiagramSetting.setType(type);

		return cpDefinitionDiagramSettingPersistence.update(
			cpDefinitionDiagramSetting);
	}

	@Override
	public CPDefinitionDiagramSetting deleteCPDefinitionDiagramSetting(
			long cpDefinitionDiagramSettingId)
		throws PortalException {

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			cpDefinitionDiagramSettingPersistence.findByPrimaryKey(
				cpDefinitionDiagramSettingId);

		return cpDefinitionDiagramSettingLocalService.
			deleteCPDefinitionDiagramSetting(cpDefinitionDiagramSetting);
	}

	@Override
	public CPDefinitionDiagramSetting
			deleteCPDefinitionDiagramSettingByCPDefinitionId(
				long cpDefinitionId)
		throws PortalException {

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			cpDefinitionDiagramSettingPersistence.findByCPDefinitionId(
				cpDefinitionId);

		return cpDefinitionDiagramSettingLocalService.
			deleteCPDefinitionDiagramSetting(cpDefinitionDiagramSetting);
	}

	@Override
	public CPDefinitionDiagramSetting
		fetchCPDefinitionDiagramSettingByCPDefinitionId(long cpDefinitionId) {

		return cpDefinitionDiagramSettingPersistence.fetchByCPDefinitionId(
			cpDefinitionId);
	}

	@Override
	public CPDefinitionDiagramSetting
			getCPDefinitionDiagramSettingByCPDefinitionId(long cpDefinitionId)
		throws PortalException {

		return cpDefinitionDiagramSettingPersistence.findByCPDefinitionId(
			cpDefinitionId);
	}

	@Override
	public CPDefinitionDiagramSetting updateCPDefinitionDiagramSetting(
			long cpDefinitionDiagramSettingId, long cpAttachmentFileEntryId,
			String color, double radius, String type)
		throws PortalException {

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			cpDefinitionDiagramSettingLocalService.
				getCPDefinitionDiagramSetting(cpDefinitionDiagramSettingId);

		cpDefinitionDiagramSetting.setCPAttachmentFileEntryId(
			cpAttachmentFileEntryId);
		cpDefinitionDiagramSetting.setColor(color);
		cpDefinitionDiagramSetting.setRadius(radius);
		cpDefinitionDiagramSetting.setType(type);

		return cpDefinitionDiagramSettingPersistence.update(
			cpDefinitionDiagramSetting);
	}

}
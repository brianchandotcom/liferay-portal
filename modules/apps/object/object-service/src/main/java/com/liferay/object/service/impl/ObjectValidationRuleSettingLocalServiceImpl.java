/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.impl;

import com.liferay.object.model.ObjectValidationRuleSetting;
import com.liferay.object.service.base.ObjectValidationRuleSettingLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectValidationRulePersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectValidationRuleSetting",
	service = AopService.class
)
public class ObjectValidationRuleSettingLocalServiceImpl
	extends ObjectValidationRuleSettingLocalServiceBaseImpl {

	@Override
	public ObjectValidationRuleSetting addObjectValidationRuleSetting(
			long userId, long objectValidationRuleId, String name, String value)
		throws PortalException {

		_objectValidationRulePersistence.findByPrimaryKey(
			objectValidationRuleId);

		ObjectValidationRuleSetting objectValidationRuleSetting =
			objectValidationRuleSettingPersistence.create(
				counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		objectValidationRuleSetting.setCompanyId(user.getCompanyId());
		objectValidationRuleSetting.setUserId(user.getUserId());
		objectValidationRuleSetting.setUserName(user.getFullName());

		objectValidationRuleSetting.setObjectValidationRuleId(
			objectValidationRuleId);
		objectValidationRuleSetting.setName(name);
		objectValidationRuleSetting.setValue(value);

		return objectValidationRuleSettingPersistence.update(
			objectValidationRuleSetting);
	}

	@Reference
	private ObjectValidationRulePersistence _objectValidationRulePersistence;

	@Reference
	private UserLocalService _userLocalService;

}
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.configuration.persistence.listener;

import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.constants.ObjectValidationRuleConstants;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectValidationRule;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectValidationRuleLocalService;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Dictionary;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Leite
 */
@Component(
	property = "model.class.name=com.liferay.object.configuration.ObjectConfiguration",
	service = ConfigurationModelListener.class
)
public class ObjectConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onAfterSave(String pid, Dictionary<String, Object> properties) {
		boolean allowAdministratorsExecuteScript = GetterUtil.getBoolean(
			properties.get("allowAdministratorsExecuteScript"));

		if (!allowAdministratorsExecuteScript) {
			long defaultCompanyId = _portal.getDefaultCompanyId();

			for (ObjectAction objectAction :
					_objectActionLocalService.getObjectActions(
						QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

				if ((objectAction.getCompanyId() != defaultCompanyId) &&
					objectAction.isActive() &&
					StringUtil.equals(
						objectAction.getObjectActionExecutorKey(),
						ObjectActionExecutorConstants.KEY_GROOVY)) {

					objectAction.setActive(false);

					_objectActionLocalService.updateObjectAction(objectAction);
				}
			}

			for (ObjectValidationRule objectValidationRule :
					_objectValidationRuleLocalService.getObjectValidationRules(
						QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

				if ((objectValidationRule.getCompanyId() != defaultCompanyId) &&
					objectValidationRule.isActive() &&
					StringUtil.equals(
						objectValidationRule.getEngine(),
						ObjectValidationRuleConstants.ENGINE_TYPE_GROOVY)) {

					objectValidationRule.setActive(false);

					_objectValidationRuleLocalService.
						updateObjectValidationRule(objectValidationRule);
				}
			}
		}
	}

	@Reference
	private ObjectActionLocalService _objectActionLocalService;

	@Reference
	private ObjectValidationRuleLocalService _objectValidationRuleLocalService;

	@Reference
	private Portal _portal;

}
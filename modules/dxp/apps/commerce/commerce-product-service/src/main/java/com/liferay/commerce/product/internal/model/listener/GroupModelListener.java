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

package com.liferay.commerce.product.internal.model.listener;

import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPMeasurementUnitLocalService;
import com.liferay.commerce.product.service.CPRuleLocalService;
import com.liferay.commerce.product.service.CPTaxCategoryLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = ModelListener.class)
public class GroupModelListener extends BaseModelListener<Group> {

	@Override
	public void onAfterCreate(Group group) throws ModelListenerException {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setLanguageId(group.getDefaultLanguageId());
		serviceContext.setScopeGroupId(group.getGroupId());
		serviceContext.setUserId(group.getCreatorUserId());

		try {
			_cpMeasurementUnitLocalService.importDefaultValues(serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void onBeforeRemove(Group group) throws ModelListenerException {
		try {
			_cpDefinitionLocalService.deleteCPDefinitions(group.getGroupId());
			_cpRuleLocalService.deleteCPRules(group.getGroupId());
			_cpTaxCategoryLocalService.deleteCPTaxCategories(
				group.getGroupId());
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroupModelListener.class);

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPMeasurementUnitLocalService _cpMeasurementUnitLocalService;

	@Reference
	private CPRuleLocalService _cpRuleLocalService;

	@Reference
	private CPTaxCategoryLocalService _cpTaxCategoryLocalService;

}
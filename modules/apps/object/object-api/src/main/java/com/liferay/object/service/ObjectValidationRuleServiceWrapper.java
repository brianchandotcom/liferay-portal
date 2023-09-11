/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ObjectValidationRuleService}.
 *
 * @author Marco Leo
 * @see ObjectValidationRuleService
 * @generated
 */
public class ObjectValidationRuleServiceWrapper
	implements ObjectValidationRuleService,
			   ServiceWrapper<ObjectValidationRuleService> {

	public ObjectValidationRuleServiceWrapper() {
		this(null);
	}

	public ObjectValidationRuleServiceWrapper(
		ObjectValidationRuleService objectValidationRuleService) {

		_objectValidationRuleService = objectValidationRuleService;
	}

	@Override
	public com.liferay.object.model.ObjectValidationRule
			addObjectValidationRule(
				long objectDefinitionId, boolean active, String engine,
				java.util.Map<java.util.Locale, String> errorLabelMap,
				java.util.Map<java.util.Locale, String> nameMap,
				String outputType, String script, boolean system,
				java.util.List
					<com.liferay.object.model.ObjectValidationRuleSetting>
						objectValidationRuleSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectValidationRuleService.addObjectValidationRule(
			objectDefinitionId, active, engine, errorLabelMap, nameMap,
			outputType, script, system, objectValidationRuleSettings);
	}

	@Override
	public com.liferay.object.model.ObjectValidationRule
			deleteObjectValidationRule(long objectValidationRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectValidationRuleService.deleteObjectValidationRule(
			objectValidationRuleId);
	}

	@Override
	public com.liferay.object.model.ObjectValidationRule
			getObjectValidationRule(long objectValidationRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectValidationRuleService.getObjectValidationRule(
			objectValidationRuleId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectValidationRuleService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.object.model.ObjectValidationRule
			updateObjectValidationRule(
				long objectValidationRuleId, boolean active, String engine,
				java.util.Map<java.util.Locale, String> errorLabelMap,
				java.util.Map<java.util.Locale, String> nameMap,
				String outputType, String script,
				java.util.List
					<com.liferay.object.model.ObjectValidationRuleSetting>
						objectValidationRuleSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectValidationRuleService.updateObjectValidationRule(
			objectValidationRuleId, active, engine, errorLabelMap, nameMap,
			outputType, script, objectValidationRuleSettings);
	}

	@Override
	public ObjectValidationRuleService getWrappedService() {
		return _objectValidationRuleService;
	}

	@Override
	public void setWrappedService(
		ObjectValidationRuleService objectValidationRuleService) {

		_objectValidationRuleService = objectValidationRuleService;
	}

	private ObjectValidationRuleService _objectValidationRuleService;

}
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
				java.util.Map<java.util.Locale, String> nameMap, String script)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectValidationRuleService.addObjectValidationRule(
			objectDefinitionId, active, engine, errorLabelMap, nameMap, script);
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
				java.util.Map<java.util.Locale, String> nameMap, String script)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectValidationRuleService.updateObjectValidationRule(
			objectValidationRuleId, active, engine, errorLabelMap, nameMap,
			script);
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
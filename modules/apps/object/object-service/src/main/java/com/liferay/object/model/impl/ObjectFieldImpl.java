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

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Objects;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectFieldImpl extends ObjectFieldBaseImpl {

	@Override
	public boolean compareBusinessType(String businessType) {
		if (Objects.equals(getBusinessType(), businessType)) {
			return true;
		}

		return false;
	}

	@Override
	public String getI18nObjectFieldName() {
		return getName() + "_i18n";
	}

	@Override
	public ObjectDefinition getObjectDefinition() throws PortalException {
		return ObjectDefinitionLocalServiceUtil.getObjectDefinition(
			getObjectDefinitionId());
	}

	@Override
	public List<ObjectFieldSetting> getObjectFieldSettings() {
		return _objectFieldSettings;
	}

	@Override
	public boolean isDeletionAllowed() throws PortalException {
		if (isSystem() || Validator.isNotNull(getRelationshipType())) {
			return false;
		}

		ObjectDefinition objectDefinition = getObjectDefinition();

		if (objectDefinition.isUnmodifiableSystemObject() &&
			!Objects.equals(
				objectDefinition.getExtensionDBTableName(), getDBTableName())) {

			return false;
		}

		if (objectDefinition.isApproved() && objectDefinition.isModifiable() &&
			objectDefinition.isSystem()) {

			return false;
		}

		return true;
	}

	@Override
	public void setObjectFieldSettings(
		List<ObjectFieldSetting> objectFieldSettings) {

		_objectFieldSettings = objectFieldSettings;
	}

	private List<ObjectFieldSetting> _objectFieldSettings;

}
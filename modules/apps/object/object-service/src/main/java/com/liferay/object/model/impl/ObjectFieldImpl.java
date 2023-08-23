/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.field.util.ObjectFieldUtil;
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
		if (Validator.isNotNull(getRelationshipType())) {
			return false;
		}

		ObjectDefinition objectDefinition = getObjectDefinition();

		if (objectDefinition.isUnmodifiableSystemObject() &&
			!Objects.equals(
				objectDefinition.getExtensionDBTableName(), getDBTableName())) {

			return false;
		}

		return true;
	}

	public boolean isMetadata() {
		return ObjectFieldUtil.isMetadata(getName());
	}

	@Override
	public void setObjectFieldSettings(
		List<ObjectFieldSetting> objectFieldSettings) {

		_objectFieldSettings = objectFieldSettings;
	}

	private List<ObjectFieldSetting> _objectFieldSettings;

}
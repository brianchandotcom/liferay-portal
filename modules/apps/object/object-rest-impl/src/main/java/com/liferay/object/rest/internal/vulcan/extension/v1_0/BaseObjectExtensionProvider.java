/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.vulcan.extension.v1_0;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.vulcan.dto.converter.DTOMapper;
import com.liferay.portal.vulcan.extension.ExtensionProvider;

import java.util.Collection;
import java.util.Collections;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
public abstract class BaseObjectExtensionProvider implements ExtensionProvider {

	@Override
	public Collection<String> getFilteredPropertyNames(
		long companyId, Object entity) {

		return Collections.emptyList();
	}

	@Override
	public boolean isApplicableExtension(long companyId, String className) {
		ObjectDefinition objectDefinition = fetchObjectDefinition(
			companyId, className);

		if ((objectDefinition != null) &&
			objectDefinition.isUnmodifiableSystemObject()) {

			return true;
		}

		return false;
	}

	protected ObjectDefinition fetchObjectDefinition(
		long companyId, String className) {

		if (className == null) {
			return null;
		}

		String internalDTOClassName = dtoMapper.toInternalDTOClassName(
			className);

		if (internalDTOClassName == null) {
			return null;
		}

		return objectDefinitionLocalService.fetchObjectDefinitionByClassName(
			companyId, internalDTOClassName);
	}

	protected long getPrimaryKey(Object entity) throws PortalException {
		JSONObject jsonObject = jsonFactory.createJSONObject(
			jsonFactory.looseSerializeDeep(entity));

		return jsonObject.getLong("id");
	}

	@Reference
	protected DTOMapper dtoMapper;

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected ObjectDefinitionLocalService objectDefinitionLocalService;

}
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

package com.liferay.object.dynamic.data.mapping.internal.data.provider;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderException;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	immediate = true, property = "ddm.data.provider.instance.id=objects",
	service = DDMDataProvider.class
)
public class ObjectsDataProvider implements DDMDataProvider {

	@Override
	public DDMDataProviderResponse getData(
			DDMDataProviderRequest ddmDataProviderRequest)
		throws DDMDataProviderException {

		try {
			DDMDataProviderResponse.Builder builder =
				DDMDataProviderResponse.Builder.newBuilder();

			List<KeyValuePair> keyValuePairs = new ArrayList<>();

			List<ObjectDefinition> objectDefinitions =
				_objectDefinitionLocalService.getObjectDefinitions(
					ddmDataProviderRequest.getCompanyId(), true, false,
					WorkflowConstants.STATUS_APPROVED);

			for (ObjectDefinition objectDefinition : objectDefinitions) {
				keyValuePairs.add(
					new KeyValuePair(
						String.valueOf(
							objectDefinition.getObjectDefinitionId()),
						objectDefinition.getLabel(
							ddmDataProviderRequest.getLocale())));
			}

			builder.withOutput("Default-Output", keyValuePairs);

			return builder.build();
		}
		catch (SystemException systemException) {
			throw new DDMDataProviderException(systemException);
		}
	}

	@Override
	public Class<?> getSettings() {
		throw new UnsupportedOperationException();
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}
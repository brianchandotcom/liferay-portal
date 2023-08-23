/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	property = "ddm.data.provider.instance.id=objects",
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

			for (ObjectDefinition objectDefinition :
					_objectDefinitionLocalService.getObjectDefinitions(
						ddmDataProviderRequest.getCompanyId(), true, false,
						WorkflowConstants.STATUS_APPROVED)) {

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
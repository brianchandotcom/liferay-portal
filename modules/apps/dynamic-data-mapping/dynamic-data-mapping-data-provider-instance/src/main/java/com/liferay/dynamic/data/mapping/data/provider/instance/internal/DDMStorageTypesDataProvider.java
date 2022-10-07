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

package com.liferay.dynamic.data.mapping.data.provider.instance.internal;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterTracker;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.KeyValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	property = "ddm.data.provider.instance.id=ddm-storage-types",
	service = DDMDataProvider.class
)
public class DDMStorageTypesDataProvider implements DDMDataProvider {

	@Override
	public DDMDataProviderResponse getData(
		DDMDataProviderRequest ddmDataProviderRequest) {

		List<KeyValuePair> keyValuePairs = new ArrayList<>();

		Set<String> storageTypes =
			ddmStorageAdapterTracker.getDDMStorageAdapterTypes();

		Optional<HttpServletRequest> httpServletRequestOptional =
			ddmDataProviderRequest.getParameterOptional(
				"httpServletRequest", HttpServletRequest.class);

		HttpServletRequest httpServletRequest =
			httpServletRequestOptional.orElse(null);

		for (String storageType : storageTypes) {
			if (storageType.equals(StorageType.JSON.getValue())) {
				continue;
			}

			if (httpServletRequest == null) {
				keyValuePairs.add(new KeyValuePair(storageType, storageType));

				continue;
			}

			keyValuePairs.add(
				new KeyValuePair(
					storageType,
					_language.get(
						httpServletRequest, storageType + "[stands-for]",
						_language.get(httpServletRequest, storageType))));
		}

		DDMDataProviderResponse.Builder builder =
			DDMDataProviderResponse.Builder.newBuilder();

		builder.withOutput("Default-Output", keyValuePairs);

		return builder.build();
	}

	@Override
	public Class<?> getSettings() {
		throw new UnsupportedOperationException();
	}

	@Reference
	protected DDMStorageAdapterTracker ddmStorageAdapterTracker;

	@Reference
	private Language _language;

}
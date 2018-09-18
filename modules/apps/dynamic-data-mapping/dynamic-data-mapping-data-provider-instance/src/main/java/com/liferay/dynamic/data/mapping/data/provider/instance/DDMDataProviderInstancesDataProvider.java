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

package com.liferay.dynamic.data.mapping.data.provider.instance;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = "ddm.data.provider.instance.id=getDataProviderInstances",
	service = DDMDataProvider.class
)
public class DDMDataProviderInstancesDataProvider implements DDMDataProvider {

	@Override
	public DDMDataProviderResponse getData(
		DDMDataProviderRequest ddmDataProviderRequest) {

		List<KeyValuePair> keyValuePairs = new ArrayList<>();

		try {
			long[] groupIds = portal.getCurrentAndAncestorSiteGroupIds(
				ddmDataProviderRequest.getGroupId());

			List<DDMDataProviderInstance> ddmDataProviderInstances =
				ddmDataProviderInstanceLocalService.getDataProviderInstances(
					groupIds);

			for (DDMDataProviderInstance ddmDataProviderInstance :
					ddmDataProviderInstances) {

				long value =
					ddmDataProviderInstance.getDataProviderInstanceId();
				String label = ddmDataProviderInstance.getName(
					LocaleThreadLocal.getThemeDisplayLocale());

				keyValuePairs.add(
					new KeyValuePair(String.valueOf(value), label));
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		DDMDataProviderResponse.Builder builder =
			DDMDataProviderResponse.Builder.newBuilder();

		return builder.withOutput(
			"Default-Output", keyValuePairs
		).build();
	}

	@Override
	public Class<?> getSettings() {
		throw new UnsupportedOperationException();
	}

	@Reference
	protected DDMDataProviderInstanceLocalService
		ddmDataProviderInstanceLocalService;

	@Reference
	protected Portal portal;

	private static final Log _log = LogFactoryUtil.getLog(
		DDMDataProviderInstancesDataProvider.class);

}
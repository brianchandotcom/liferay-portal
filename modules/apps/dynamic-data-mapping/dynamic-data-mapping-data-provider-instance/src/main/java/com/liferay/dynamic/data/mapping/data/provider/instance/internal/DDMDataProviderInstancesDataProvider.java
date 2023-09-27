/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider.instance.internal;

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
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
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
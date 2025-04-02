/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.internal.provider;

import com.liferay.friendly.url.configuration.manager.FriendlyURLSeparatorConfigurationManager;
import com.liferay.friendly.url.provider.FriendlyURLSeparatorProvider;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(service = FriendlyURLSeparatorProvider.class)
public class FriendlyURLSeparatorProviderImpl
	implements FriendlyURLSeparatorProvider {

	@Override
	public String getFriendlyURLSeparator(long companyId, String key) {
		try {
			JSONObject jsonObject = _portalCache.get(companyId);

			if (jsonObject != null) {
				return jsonObject.getString(key);
			}

			JSONObject friendlyURLSeparatorsJSONObject =
				_friendlyURLSeparatorConfigurationManager.
					getFriendlyURLSeparatorsJSONObject(companyId);

			_portalCache.put(companyId, friendlyURLSeparatorsJSONObject);

			return friendlyURLSeparatorsJSONObject.getString(key);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return null;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_portalCache =
			(PortalCache<Long, JSONObject>)_multiVMPool.getPortalCache(
				FriendlyURLSeparatorProvider.class.getName());
	}

	@Deactivate
	protected void deactivate() {
		_multiVMPool.removePortalCache(
			FriendlyURLSeparatorProvider.class.getName());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FriendlyURLSeparatorProviderImpl.class.getName());

	@Reference
	private FriendlyURLSeparatorConfigurationManager
		_friendlyURLSeparatorConfigurationManager;

	@Reference
	private MultiVMPool _multiVMPool;

	private PortalCache<Long, JSONObject> _portalCache;

}
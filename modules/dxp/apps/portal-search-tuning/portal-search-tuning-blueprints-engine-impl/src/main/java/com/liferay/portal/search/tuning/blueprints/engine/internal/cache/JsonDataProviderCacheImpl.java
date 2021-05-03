/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.engine.internal.cache;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.tuning.blueprints.engine.cache.JSONDataProviderCache;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = JSONDataProviderCache.class)
public class JsonDataProviderCacheImpl implements JSONDataProviderCache {

	@Override
	public JSONObject getJSONObject(String cacheKey) {
		return _portalCache.get(cacheKey);
	}

	@Override
	public void put(String cacheKey, JSONObject jsonObject) {
		_portalCache.put(cacheKey, jsonObject);
	}

	@Override
	public void put(String cacheKey, JSONObject jsonObject, int timeToLive) {
		_portalCache.put(cacheKey, jsonObject, timeToLive);
	}

	@Reference(unbind = "-")
	protected void setSingleVMPool(SingleVMPool singleVMPool) {
		_portalCache =
			(PortalCache<String, JSONObject>)singleVMPool.getPortalCache(
				JsonDataProviderCacheImpl.class.getName());
	}

	private PortalCache<String, JSONObject> _portalCache;

}
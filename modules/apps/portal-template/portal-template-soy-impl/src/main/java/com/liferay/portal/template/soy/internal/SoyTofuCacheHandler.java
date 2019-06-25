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

package com.liferay.portal.template.soy.internal;

import com.google.template.soy.SoyFileSet;
import com.google.template.soy.tofu.SoyTofu;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.template.TemplateResource;

import java.util.List;

/**
 * @author Bruno Basto
 */
public class SoyTofuCacheHandler {

	public SoyTofuCacheHandler(
		PortalCache<String, SoyTofuCacheBag> portalCache) {

		_portalCache = portalCache;
	}

	public SoyTofuCacheBag add(
		String templateId, SoyFileSet soyFileSet, SoyTofu soyTofu) {

		SoyTofuCacheBag soyTofuCacheBag = new SoyTofuCacheBag(
			soyFileSet, soyTofu);

		_portalCache.put(templateId, soyTofuCacheBag);

		return soyTofuCacheBag;
	}

	public SoyTofuCacheBag get(String templateId) {
		return _portalCache.get(templateId);
	}

	public void removeIfAny(List<TemplateResource> templateResources) {
		for (TemplateResource templateResource : templateResources) {
			String templateId = templateResource.getTemplateId();

			for (String key : _portalCache.getKeys()) {
				if (key.equals(templateId) ||
					key.startsWith(templateId + StringPool.COMMA) ||
					key.endsWith(StringPool.COMMA + templateId) ||
					key.contains(
						StringPool.COMMA + templateId + StringPool.COMMA)) {

					_portalCache.remove(key);
				}
			}
		}
	}

	private final PortalCache<String, SoyTofuCacheBag> _portalCache;

}
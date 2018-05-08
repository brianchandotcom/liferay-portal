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

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.template.TemplateResource;

import java.util.HashSet;
import java.util.List;

/**
 * @author Bruno Basto
 */
public class SoyTofuCacheHandler {

	public SoyTofuCacheHandler(
		PortalCache<HashSet<TemplateResource>, SoyTofuCacheBag> portalCache) {

		_portalCache = portalCache;
	}

	public SoyTofuCacheBag add(
		List<TemplateResource> templateResources, SoyFileSet soyFileSet,
		SoyTofu soyTofu) {

		HashSet<TemplateResource> key = getKeySet(templateResources);

		SoyTofuCacheBag soyTofuCacheBag = new SoyTofuCacheBag(
			soyFileSet, soyTofu);

		_portalCache.put(key, soyTofuCacheBag);

		return soyTofuCacheBag;
	}

	public SoyTofuCacheBag get(List<TemplateResource> templateResources) {
		HashSet<TemplateResource> key = getKeySet(templateResources);

		return _portalCache.get(key);
	}

	public SoyTofu getSoyTofu(List<TemplateResource> templateResources) {
		SoyTofuCacheBag soyTofuCacheBag = get(templateResources);

		return soyTofuCacheBag.getSoyTofu();
	}

	public void removeIfAny(List<TemplateResource> templateResources) {
		for (TemplateResource templateResource : templateResources) {
			for (HashSet<TemplateResource> key : _portalCache.getKeys()) {
				if (key.contains(templateResource)) {
					_portalCache.remove(key);
				}
			}
		}
	}

	protected HashSet<TemplateResource> getKeySet(
		List<TemplateResource> templateResources) {

		return new HashSet<>(templateResources);
	}

	private final PortalCache<HashSet<TemplateResource>, SoyTofuCacheBag>
		_portalCache;

}
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

package com.liferay.journal.internal.exportimport.content.processor;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true, service = JournalArticleExportImportProcessorCache.class
)
public class JournalArticleExportImportProcessorCache {

	public void clear() {
		_portalCache.removeAll();
	}

	public String get(String key) {
		return _portalCache.get(key);
	}

	public void put(String key, String content) {
		_portalCache.put(key, content);
	}

	@Activate
	protected void activate() {
		_portalCache =
			(PortalCache<String, String>)_singleVMPool.getPortalCache(
				JournalArticleExportImportProcessorCache.class.getName());
	}

	private static PortalCache<String, String> _portalCache;

	@Reference
	private SingleVMPool _singleVMPool;

}
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

package com.liferay.portal.search.internal;

import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.search.BaseSearchEngineConfigurator;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(service = {})
public class DefaultSearchEngineConfigurator
	extends BaseSearchEngineConfigurator {

	@Activate
	protected void activate() {
		setDestinationFactory(_destinationFactory);
		setMessageBus(_messageBus);
		setSearchEngines(
			HashMapBuilder.put(
				SearchEngineHelper.GENERIC_ENGINE_ID, _searchEngine
			).build());

		afterPropertiesSet();
	}

	@Deactivate
	protected void deactivate() {
		destroy();
	}

	@Override
	protected String getDefaultSearchEngineId() {
		return SearchEngineHelper.SYSTEM_ENGINE_ID;
	}

	@Override
	protected IndexSearcher getIndexSearcher() {
		return _indexSearcher;
	}

	@Override
	protected IndexWriter getIndexWriter() {
		return _indexWriter;
	}

	@Override
	protected ClassLoader getOperatingClassLoader() {
		return PortalClassLoaderUtil.getClassLoader();
	}

	@Override
	protected SearchEngineHelper getSearchEngineHelper() {
		return _searchEngineHelper;
	}

	@Reference
	private DestinationFactory _destinationFactory;

	@Reference
	private IndexSearcher _indexSearcher;

	@Reference
	private IndexWriter _indexWriter;

	@Reference
	private MessageBus _messageBus;

	@Reference
	private SearchEngine _searchEngine;

	@Reference
	private SearchEngineHelper _searchEngineHelper;

}
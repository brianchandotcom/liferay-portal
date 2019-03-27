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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;

/**
 * @author Michael C. Han
 */
public class PluginSearchEngineConfigurator
	extends BaseSearchEngineConfigurator {

	public void setDefaultSearchEngineId(String defaultSearchEngineId) {
		_defaultSearchEngineId = defaultSearchEngineId;
	}

	@Override
	protected String getDefaultSearchEngineId() {
		return _defaultSearchEngineId;
	}

	@Override
	protected IndexSearcher getIndexSearcher() {
		BeanLocator beanLocator = PortalBeanLocatorUtil.getBeanLocator();

		return (IndexSearcher)beanLocator.locate(
			IndexSearcherProxyBean.class.getName());
	}

	@Override
	protected IndexWriter getIndexWriter() {
		BeanLocator beanLocator = PortalBeanLocatorUtil.getBeanLocator();

		return (IndexWriter)beanLocator.locate(
			IndexWriterProxyBean.class.getName());
	}

	@Override
	protected ClassLoader getOperatingClassloader() {
		return PortletClassLoaderUtil.getClassLoader();
	}

	@Override
	protected SearchEngineHelper getSearchEngineHelper() {
		return SearchEngineHelperUtil.getSearchEngineHelper();
	}

	private String _defaultSearchEngineId;

}
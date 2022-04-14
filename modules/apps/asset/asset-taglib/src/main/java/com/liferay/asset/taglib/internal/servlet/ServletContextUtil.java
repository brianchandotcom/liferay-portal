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

package com.liferay.asset.taglib.internal.servlet;

import com.liferay.asset.util.AssetHelper;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.renderer.FragmentRendererTracker;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael Bradford
 */
@Component(immediate = true, service = {})
public class ServletContextUtil {

	public static AssetHelper getAssetHelper() {
		return _assetHelper;
	}

	public static FragmentCollectionContributorTracker
		getFragmentCollectionContributorTracker() {

		return _fragmentCollectionContributorTracker;
	}

	public static FragmentRendererTracker getFragmentRendererTracker() {
		return _fragmentRendererTracker;
	}

	public static ServletContext getServletContext() {
		return _servletContext;
	}

	@Reference(unbind = "-")
	protected void setAssetHelper(AssetHelper assetHelper) {
		_assetHelper = assetHelper;
	}

	@Reference(unbind = "-")
	protected void setFragmentCollectionContributorTracker(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker) {

		_fragmentCollectionContributorTracker =
			fragmentCollectionContributorTracker;
	}

	@Reference(unbind = "-")
	protected void setFragmentRendererTracker(
		FragmentRendererTracker fragmentRendererTracker) {

		_fragmentRendererTracker = fragmentRendererTracker;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.asset.taglib)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static AssetHelper _assetHelper;
	private static FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;
	private static FragmentRendererTracker _fragmentRendererTracker;
	private static ServletContext _servletContext;

}
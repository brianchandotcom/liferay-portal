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

package com.liferay.fragment.collection.contributor.cookie.banner;

import com.liferay.fragment.contributor.BaseFragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;

import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = "fragment.collection.key=COOKIE_BANNER",
	service = FragmentCollectionContributor.class
)
public class CookieBannerFragmentCollectionContributor
	extends BaseFragmentCollectionContributor {

	@Override
	public String getFragmentCollectionKey() {
		return "COOKIE_BANNER";
	}

	@Override
	public List<FragmentEntry> getFragmentEntries() {
		if (FeatureFlagManagerUtil.isEnabled("LPS-165346")) {
			return super.getFragmentEntries();
		}

		return Collections.emptyList();
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(int type) {
		if (FeatureFlagManagerUtil.isEnabled("LPS-165346")) {
			return super.getFragmentEntries();
		}

		return Collections.emptyList();
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.fragment.collection.contributor.cookie.banner)"
	)
	private ServletContext _servletContext;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
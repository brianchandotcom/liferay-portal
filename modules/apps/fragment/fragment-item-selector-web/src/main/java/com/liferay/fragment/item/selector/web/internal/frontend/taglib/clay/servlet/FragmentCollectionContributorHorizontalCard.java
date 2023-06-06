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

package com.liferay.fragment.item.selector.web.internal.frontend.taglib.clay.servlet;

import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.frontend.taglib.clay.servlet.taglib.HorizontalCard;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;

import javax.portlet.PortletURL;

/**
 * @author Víctor Galán
 */
public class FragmentCollectionContributorHorizontalCard
	implements HorizontalCard {

	public FragmentCollectionContributorHorizontalCard(
		FragmentCollectionContributor fragmentCollectionContributor,
		PortletURL portletURL) {

		_fragmentCollectionContributor = fragmentCollectionContributor;
		_portletURL = portletURL;
	}

	@Override
	public String getHref() {
		return PortletURLBuilder.create(
			_portletURL
		).setParameter(
			"fragmentCollectionKey",
			_fragmentCollectionContributor.getFragmentCollectionKey()
		).buildString();
	}

	@Override
	public String getIcon() {
		return "box-container";
	}

	@Override
	public String getTitle() {
		return _fragmentCollectionContributor.getName();
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private final FragmentCollectionContributor _fragmentCollectionContributor;
	private final PortletURL _portletURL;

}
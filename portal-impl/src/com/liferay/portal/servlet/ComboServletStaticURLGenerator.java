/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.comparator.PortletNameComparator;
import com.liferay.portlet.PortletResourceAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Carlos Sierra Andrés
 */
public class ComboServletStaticURLGenerator {

	public List<String> generate(List<Portlet> portlets) {
		List<String> urls = new ArrayList<String>();

		StringBundler sb = new StringBundler();

		long timestamp = _timestamp;

		portlets = ListUtil.sort(portlets, _portletNameComparator);

		for (Portlet portlet : portlets) {
			for (PortletResourceAccessor portletResourcesAccessor :
					_portletResourcesAccessors) {

				List<String> portletResources = ListUtil.sort(
					portletResourcesAccessor.get(portlet));

				for (String portletResource : portletResources) {
					if (!_predicateFilter.filter(portletResource)) {
						continue;
					}

					if (_visitedURLs.contains(portletResource)) {
						continue;
					}

					if (HttpUtil.hasProtocol(portletResource)) {
						urls.add(portletResource);
					}
					else {
						sb.append(StringPool.AMPERSAND);

						String contextPath = portlet.getContextPath();

						if (!portletResourcesAccessor.isPortalResource() &&
							(contextPath != null) &&
							!contextPath.equals(PortalUtil.getPathContext())) {

							sb.append(contextPath);
							sb.append(StringPool.COLON);
						}

						sb.append(HtmlUtil.escape(portletResource));

						timestamp = Math.max(timestamp, portlet.getTimestamp());
					}

					_visitedURLs.add(portletResource);
				}
			}
		}

		if (sb.length() > 0) {
			String url = _urlPrefix + sb.toString();

			url = HttpUtil.addParameter(url, "t", timestamp);

			urls.add(url);
		}

		return urls;
	}

	public void setPortletResourceAccessors(
		PortletResourceAccessor... portletResourcesAccessors) {

		_portletResourcesAccessors = portletResourcesAccessors;
	}

	public void setPredicateFilter(PredicateFilter<String> predicateFilter) {
		_predicateFilter = predicateFilter;
	}

	public void setTimestamp(long timestamp) {
		_timestamp = timestamp;
	}

	public void setURLPrefix(String urlPrefix) {
		_urlPrefix = urlPrefix;
	}

	public void setVisitedURLs(Set<String> visitedURLs) {
		_visitedURLs = visitedURLs;
	}

	private static PortletNameComparator _portletNameComparator =
		new PortletNameComparator();

	private PortletResourceAccessor[] _portletResourcesAccessors;
	private PredicateFilter<String> _predicateFilter = PredicateFilter.ALL;
	private long _timestamp;
	private String _urlPrefix;
	private Set<String> _visitedURLs;

}
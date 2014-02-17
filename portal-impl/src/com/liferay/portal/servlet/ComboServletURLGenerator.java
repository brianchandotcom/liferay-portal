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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author Carlos Sierra Andrés
 */
public class ComboServletURLGenerator {

	public Collection<String> generateURLsFor(List<Portlet> portlets) {
		List<String> urls = new ArrayList<String>();

		portlets = ListUtil.sort(portlets, _portletNameComparator);

		StringBundler comboURLSB = new StringBundler(_comboURLPrefix);

		boolean hasComboURL = false;

		long timestamp = _minTimestamp;

		for (Portlet portlet : portlets) {
			for (
				PortletResourcesAccessor portletResourcesAccessor :
					_portletResourcesAccessors) {

				List<String> resources = ListUtil.sort(
					portletResourcesAccessor.get(portlet));

				for (String resource : resources) {
					if (!_predicateFilter.filter(resource)) {
						continue;
					}

					if (_visited.contains(resource)) {
						continue;
					}

					if (HttpUtil.hasProtocol(resource)) {
						urls.add(resource);
					}
					else {
						comboURLSB.append(StringPool.AMPERSAND);

						String curPortletContextPath = portlet.getContextPath();

						if (!portletResourcesAccessor.isPortalResource() &&
							(curPortletContextPath != null) &&
							!curPortletContextPath.equals(
								PortalUtil.getPathContext())) {

							comboURLSB.append(curPortletContextPath);
							comboURLSB.append(StringPool.COLON);
						}

						comboURLSB.append(HtmlUtil.escape(resource));

						timestamp = Math.max(timestamp, portlet.getTimestamp());

						hasComboURL = true;
					}

					_visited.add(resource);
				}
			}
		}

		if (hasComboURL) {
			String comboURL = comboURLSB.toString();
			comboURL = HttpUtil.addParameter(comboURL, "t", timestamp);
			urls.add(comboURL);
		}

		return urls;
	}

	public void setComboURLPrefix(String comboURLPrefix) {
		_comboURLPrefix = comboURLPrefix;
	}

	public void setMinTimestamp(long minTimestamp) {
		_minTimestamp = minTimestamp;
	}

	public void setPortletResourcesAccessors(
		PortletResourcesAccessor ... portletResourcesAccessor) {

		_portletResourcesAccessors = portletResourcesAccessor;
	}

	public void setPredicateFilter(PredicateFilter<String> predicateFilter) {
		_predicateFilter = predicateFilter;
	}

	public void setVisited(Set<String> visited) {
		_visited = visited;
	}

	private String _comboURLPrefix;
	private long _minTimestamp;
	private final PortletNameComparator _portletNameComparator =
		new PortletNameComparator();
	private PortletResourcesAccessor[] _portletResourcesAccessors;
	private PredicateFilter<String> _predicateFilter = PredicateFilter.ALL;
	private Set<String> _visited;

}
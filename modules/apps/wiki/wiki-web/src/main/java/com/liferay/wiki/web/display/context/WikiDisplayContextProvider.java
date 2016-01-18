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

package com.liferay.wiki.web.display.context;

import com.liferay.wiki.display.context.WikiDisplayContextFactory;
import com.liferay.wiki.display.context.WikiEditPageDisplayContext;
import com.liferay.wiki.display.context.WikiInfoPanelDisplayContext;
import com.liferay.wiki.display.context.WikiListPagesDisplayContext;
import com.liferay.wiki.display.context.WikiViewPageDisplayContext;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Iván Zaera
 */
@Component(service = WikiDisplayContextProvider.class)
public class WikiDisplayContextProvider {

	public WikiEditPageDisplayContext getWikiEditPageDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		WikiPage wikiPage) {

		Collection<WikiDisplayContextFactory> wikiDisplayContextFactories =
			_wikiDisplayContextFactories.values();

		WikiEditPageDisplayContext wikiEditPageDisplayContext =
			new DefaultWikiEditPageDisplayContext(request, response, wikiPage);

		for (WikiDisplayContextFactory wikiDisplayContextFactory :
				wikiDisplayContextFactories) {

			wikiEditPageDisplayContext =
				wikiDisplayContextFactory.getWikiEditPageDisplayContext(
					wikiEditPageDisplayContext, request, response, wikiPage);
		}

		return wikiEditPageDisplayContext;
	}

	public WikiInfoPanelDisplayContext getWikiInfoPanelDisplayContext(
		HttpServletRequest request, HttpServletResponse response) {

		Collection<WikiDisplayContextFactory> wikiDisplayContextFactories =
			_wikiDisplayContextFactories.values();

		WikiInfoPanelDisplayContext wikiInfoPanelDisplayContext =
			new DefaultWikiInfoPanelDisplayContext(request, response);

		for (WikiDisplayContextFactory wikiDisplayContextFactory :
				wikiDisplayContextFactories) {

			wikiInfoPanelDisplayContext =
				wikiDisplayContextFactory.getWikiInfoPanelDisplayContext(
					wikiInfoPanelDisplayContext, request, response);
		}

		return wikiInfoPanelDisplayContext;
	}

	public WikiListPagesDisplayContext getWikiListPagesDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		WikiNode wikiNode) {

		Collection<WikiDisplayContextFactory> wikiDisplayContextFactories =
			_wikiDisplayContextFactories.values();

		WikiListPagesDisplayContext wikiListPagesDisplayContext =
			new DefaultWikiListPagesDisplayContext(request, response, wikiNode);

		for (WikiDisplayContextFactory wikiDisplayContextFactory :
				wikiDisplayContextFactories) {

			wikiListPagesDisplayContext =
				wikiDisplayContextFactory.getWikiListPagesDisplayContext(
					wikiListPagesDisplayContext, request, response, wikiNode);
		}

		return wikiListPagesDisplayContext;
	}

	public WikiViewPageDisplayContext getWikiViewPageDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		WikiPage wikiPage) {

		Collection<WikiDisplayContextFactory> wikiDisplayContextFactories =
			_wikiDisplayContextFactories.values();

		WikiViewPageDisplayContext wikiViewPageDisplayContext =
			new DefaultWikiViewPageDisplayContext(request, response, wikiPage);

		for (WikiDisplayContextFactory wikiDisplayContextFactory :
				wikiDisplayContextFactories) {

			wikiViewPageDisplayContext =
				wikiDisplayContextFactory.getWikiViewPageDisplayContext(
					wikiViewPageDisplayContext, request, response, wikiPage);
		}

		return wikiViewPageDisplayContext;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		for (Map.Entry
				<ServiceReference<WikiDisplayContextFactory>,
					WikiDisplayContextFactory> entry :
						_wikiDisplayContextFactories.entrySet()) {

			if (entry.getValue() != null) {
				continue;
			}

			ServiceReference<WikiDisplayContextFactory> serviceReference =
				entry.getKey();

			WikiDisplayContextFactory wikiDisplayContextFactory =
				_bundleContext.getService(serviceReference);

			_wikiDisplayContextFactories.put(
				serviceReference, wikiDisplayContextFactory);
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.RELUCTANT,
		service = WikiDisplayContextFactory.class
	)
	protected void setWikiDisplayContextFactory(
		ServiceReference<WikiDisplayContextFactory> serviceReference) {

		WikiDisplayContextFactory wikiDisplayContextFactory = null;

		if (_bundleContext != null) {
			wikiDisplayContextFactory = _bundleContext.getService(
				serviceReference);
		}

		_wikiDisplayContextFactories.put(
			serviceReference, wikiDisplayContextFactory);
	}

	protected void unsetWikiDisplayContextFactory(
		ServiceReference<WikiDisplayContextFactory> serviceReference) {

		_wikiDisplayContextFactories.remove(serviceReference);
	}

	private BundleContext _bundleContext;
	private final Map
		<ServiceReference<WikiDisplayContextFactory>, WikiDisplayContextFactory>
			_wikiDisplayContextFactories = new ConcurrentSkipListMap<>();

}
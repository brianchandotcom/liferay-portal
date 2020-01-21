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

package com.liferay.bean.portlet.cdi.extension.internal.mvc;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.BeanManager;

import javax.inject.Inject;
import javax.inject.Named;

import javax.mvc.MvcContext;
import javax.mvc.locale.LocaleResolver;
import javax.mvc.security.Encoders;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.annotations.PortletRequestScoped;

import javax.ws.rs.core.Configuration;

/**
 * @author Neil Griffin
 */
@ApplicationScoped
public class MVCContextProducer {

	@Named("mvc")
	@PortletRequestScoped
	@Produces
	public MvcContext getMvcContext(
		Configuration configuration, Encoders encoders,
		PortletContext portletContext, PortletRequest portletRequest) {

		return new MVCContextImpl(
			configuration, encoders, _localeResolvers, portletContext,
			portletRequest);
	}

	@PostConstruct
	public void postConstruct() {
		_localeResolvers = BeanUtil.getBeanInstances(
			_beanManager, LocaleResolver.class);

		_localeResolvers.add(new LocaleResolverImpl());

		Collections.sort(
			_localeResolvers, new LocaleResolverPriorityComparator());
	}

	@Inject
	private BeanManager _beanManager;

	private List<LocaleResolver> _localeResolvers;

	private static class LocaleResolverPriorityComparator
		extends DescendingPriorityComparator<LocaleResolver> {

		private LocaleResolverPriorityComparator() {

			// The Javadoc for javax.mvc.locale.LocaleResolver states "If no
			// priority is explicitly defined, the priority is assumed to be
			// 1000."

			super(1000);
		}

	}

}
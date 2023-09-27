/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		extends BaseDescendingPriorityComparator<LocaleResolver> {

		private LocaleResolverPriorityComparator() {

			// The Javadoc for javax.mvc.locale.LocaleResolver states "If no
			// priority is explicitly defined, the priority is assumed to be
			// 1000."

			super(1000);
		}

	}

}
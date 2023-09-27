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

import javax.ws.rs.ext.ParamConverterProvider;

/**
 * @author Neil Griffin
 */
@ApplicationScoped
public class ParamConverterProvidersProducer {

	@ApplicationScoped
	@ParamConverterProviders
	@Produces
	public List<ParamConverterProvider> getParamConverterProviders() {
		return _paramConverterProviders;
	}

	@PostConstruct
	public void postConstruct() {
		_paramConverterProviders = BeanUtil.getBeanInstances(
			_beanManager, ParamConverterProvider.class);

		_paramConverterProviders.add(new ParamConverterProviderImpl());

		Collections.sort(
			_paramConverterProviders,
			new ParamConverterProviderPriorityComparator());
	}

	@Inject
	private BeanManager _beanManager;

	private List<ParamConverterProvider> _paramConverterProviders;

	private static class ParamConverterProviderPriorityComparator
		extends BaseDescendingPriorityComparator<ParamConverterProvider> {

		private ParamConverterProviderPriorityComparator() {
			super(0);
		}

	}

}
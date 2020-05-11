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

import javax.ws.rs.ext.ParamConverterProvider;

/**
 * @author  Neil Griffin
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
		extends DescendingPriorityComparator<ParamConverterProvider> {

		private ParamConverterProviderPriorityComparator() {
			super(0);
		}

	}

}
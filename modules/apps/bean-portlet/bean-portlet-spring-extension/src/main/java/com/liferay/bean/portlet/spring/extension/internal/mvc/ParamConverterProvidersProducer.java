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

package com.liferay.bean.portlet.spring.extension.internal.mvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.ws.rs.ext.ParamConverterProvider;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author  Neil Griffin
 */
@Configuration
public class ParamConverterProvidersProducer
	implements ApplicationContextAware {

	@Bean
	@ParamConverterProviders
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public List<ParamConverterProvider> getParamConverterProviders() {
		return _paramConverterProviders;
	}

	@PostConstruct
	public void postConstruct() {
		Map<String, ParamConverterProvider> paramConverterProviders =
			_applicationContext.getBeansOfType(ParamConverterProvider.class);

		_paramConverterProviders = new ArrayList<>(
			paramConverterProviders.values());

		Collections.sort(
			_paramConverterProviders,
			new ParamConverterProviderPriorityComparator());
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		_applicationContext = applicationContext;
	}

	private ApplicationContext _applicationContext;
	private List<ParamConverterProvider> _paramConverterProviders;

	private static class ParamConverterProviderPriorityComparator
		extends DescendingPriorityComparator<ParamConverterProvider> {

		private ParamConverterProviderPriorityComparator() {
			super(0);
		}

	}

}
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.application;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import com.liferay.osb.faro.web.internal.exception.FaroEngineClientExceptionMapper;
import com.liferay.osb.faro.web.internal.exception.NoSuchModelExceptionMapper;
import com.liferay.osb.faro.web.internal.exception.OAuthExceptionMapper;
import com.liferay.osb.faro.web.internal.param.converter.FaroParamConverterProvider;
import com.liferay.osb.faro.web.internal.request.filter.FaroContainerRequestFilter;
import com.liferay.osb.faro.web.internal.request.filter.FaroContainerResponseFilter;
import com.liferay.osb.faro.web.internal.request.filter.SecurityFilter;
import com.liferay.osb.faro.web.internal.request.filter.TokenAuthenticationFilter;
import com.liferay.osb.faro.web.internal.util.JSONUtil;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

/**
 * @author Matthew Kong
 */
public abstract class BaseApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();

		classes.add(FaroContainerRequestFilter.class);
		classes.add(FaroContainerResponseFilter.class);
		classes.add(FaroParamConverterProvider.class);

		return classes;
	}

	public abstract Set<Object> getControllers();

	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<>();

		singletons.add(new JacksonJsonProvider(JSONUtil.getObjectMapper()));
		singletons.add(new FaroEngineClientExceptionMapper());
		singletons.add(new NoSuchModelExceptionMapper());
		singletons.add(new OAuthExceptionMapper());
		singletons.add(new SecurityFilter());
		singletons.add(new TokenAuthenticationFilter());
		singletons.addAll(getControllers());

		return singletons;
	}

}
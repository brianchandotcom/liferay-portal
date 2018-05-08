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

package com.liferay.frontend.js.top.head.extender.internal;

import com.liferay.osgi.felix.util.AbstractExtender;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.Dictionary;

import org.apache.felix.utils.extender.Extension;
import org.apache.felix.utils.log.Logger;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true)
public class TopHeadExtender extends AbstractExtender {

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		_logger = new Logger(bundleContext);

		start(bundleContext);
	}

	@Deactivate
	protected void deactivate(BundleContext bundleContext) throws Exception {
		stop(bundleContext);
	}

	@Override
	protected void debug(Bundle bundle, String s) {
		_logger.log(
			Logger.LOG_DEBUG,
			StringBundler.concat("[", String.valueOf(bundle), "] ", s));
	}

	@Override
	protected Extension doCreateExtension(Bundle bundle) throws Exception {
		Dictionary<String, String> headers = bundle.getHeaders();

		String liferayTopHeadJsResources = headers.get(
			"Liferay-Top-Head-JS-Resources");

		String liferayTopHeadAuthenticatedJsResources = headers.get(
			"Liferay-Top-Head-Authenticated-JS-Resources");

		if (Validator.isBlank(liferayTopHeadJsResources) &&
			Validator.isBlank(liferayTopHeadAuthenticatedJsResources)) {

			return null;
		}

		if (Validator.isNull(liferayTopHeadJsResources)) {
			liferayTopHeadJsResources = StringPool.BLANK;
		}

		if (Validator.isNull(liferayTopHeadAuthenticatedJsResources)) {
			liferayTopHeadAuthenticatedJsResources = StringPool.BLANK;
		}

		TopHeadResourcesImpl topHeadResourcesImpl = new TopHeadResourcesImpl(
			Arrays.asList(liferayTopHeadJsResources.split(StringPool.COMMA)),
			Arrays.asList(
				liferayTopHeadAuthenticatedJsResources.split(
					StringPool.COMMA)));

		int liferayTopHeadWeight = GetterUtil.getInteger(
			headers.get("Liferay-Top-Head-Weight"));

		return new TopHeadExtension(
			bundle, topHeadResourcesImpl, liferayTopHeadWeight);
	}

	@Override
	protected void error(String s, Throwable t) {
		_logger.log(Logger.LOG_ERROR, s, t);
	}

	@Override
	protected void warn(Bundle bundle, String s, Throwable t) {
		_logger.log(
			Logger.LOG_WARNING,
			StringBundler.concat("[", String.valueOf(bundle), "] ", s), t);
	}

	private Logger _logger;

}
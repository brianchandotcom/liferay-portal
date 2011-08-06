/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.adaptor;

import com.liferay.portal.AdaptorException;

import java.util.Iterator;
import java.util.ServiceLoader;

import javax.servlet.ServletContext;

/**
 * @author Raymond Augé
 */
public class AdaptorUtil {

	public static Adaptor getAdaptor() {
		if (_adaptor == null) {
			Iterator<AdaptorFactory> iterator = ServiceLoader.load(
				AdaptorFactory.class).iterator();

			if (iterator.hasNext()) {
				_adaptor = iterator.next().newAdaptor();
			}
		}

		return _adaptor;
	}

	public static void init(ServletContext servletContext, Object beanContext)
		throws AdaptorException {

		if (hasAdaptor()) {
			getAdaptor().init(servletContext, beanContext);
		}
	}

	public static void start() throws AdaptorException {
		if (hasAdaptor()) {
			getAdaptor().start();
		}
	}

	public static void stop() throws AdaptorException {
		if (hasAdaptor()) {
			getAdaptor().stop();
		}
	}

	public void setAdaptor(Adaptor adaptor) {
		_adaptor = adaptor;
	}

	protected static boolean hasAdaptor() {
		if (getAdaptor() != null) {
			return true;
		}

		return false;
	}

	private static Adaptor _adaptor;

}
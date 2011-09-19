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

import com.liferay.portal.kernel.util.ServiceLoader;

import java.util.List;

/**
 * @author Raymond Augé
 */
public class AdaptorUtil {

	public static Adaptor getAdaptor() throws AdaptorException {
		if (_adaptor != null) {
			return _adaptor;
		}

		try {
			List<AdaptorFactory> services = ServiceLoader.load(
				AdaptorFactory.class);

			for (AdaptorFactory adaptorFactory : services) {
				_adaptor = adaptorFactory.newAdaptor();
			}
		}
		catch (Exception e) {
			throw new AdaptorException(e);
		}

		return _adaptor;
	}

	public static void init(Object applicationContext) throws AdaptorException {
		if (hasAdaptor()) {
			Adaptor adaptor = getAdaptor();

			adaptor.init(applicationContext);
		}
	}

	public static void start() throws AdaptorException {
		if (hasAdaptor()) {
			Adaptor adaptor = getAdaptor();

			adaptor.start();
		}
	}

	public static void stop() throws AdaptorException {
		if (hasAdaptor()) {
			Adaptor adaptor = getAdaptor();

			adaptor.stop();
		}
	}

	public void setAdaptor(Adaptor adaptor) {
		_adaptor = adaptor;
	}

	protected static boolean hasAdaptor() throws AdaptorException {
		Adaptor adaptor = getAdaptor();

		if (adaptor != null) {
			return true;
		}

		return false;
	}

	private static Adaptor _adaptor;

}
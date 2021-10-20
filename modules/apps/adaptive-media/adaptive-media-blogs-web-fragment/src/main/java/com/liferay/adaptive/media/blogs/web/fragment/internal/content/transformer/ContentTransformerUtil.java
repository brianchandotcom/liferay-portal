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

package com.liferay.adaptive.media.blogs.web.fragment.internal.content.transformer;

import com.liferay.adaptive.media.content.transformer.ContentTransformerHandler;

import java.util.function.Supplier;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Alejandro Tardín
 */
public class ContentTransformerUtil {

	public static ContentTransformerHandler getContentTransformerHandler() {
		return _supplier.get();
	}

	private static final Supplier<ContentTransformerHandler> _supplier;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ContentTransformerUtil.class);

		ServiceTracker<ContentTransformerHandler, ContentTransformerHandler>
			serviceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), ContentTransformerHandler.class,
				null);

		serviceTracker.open();

		_supplier = serviceTracker::getService;
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
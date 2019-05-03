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

package com.liferay.journal.internal.transformer;

import com.liferay.journal.util.JournalTransformerListenerRegistry;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.templateparser.TransformerListener;

import java.util.List;

import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Pavel Savinov
 */
public class JournalTransformerListenerRegistryUtil {

	public static TransformerListener getTransformerListener(String className) {
		JournalTransformerListenerRegistry journalTransformerListenerRegistry =
			_serviceTracker.getService();

		return journalTransformerListenerRegistry.getTransformerListener(
			className);
	}

	public static List<TransformerListener> getTransformerListeners() {
		JournalTransformerListenerRegistry journalTransformerListenerRegistry =
			_serviceTracker.getService();

		return journalTransformerListenerRegistry.getTransformerListeners();
	}

	private static final ServiceTracker<?, JournalTransformerListenerRegistry>
		_serviceTracker = ServiceTrackerFactory.open(
			FrameworkUtil.getBundle(
				JournalTransformerListenerRegistryUtil.class),
			JournalTransformerListenerRegistry.class);

}
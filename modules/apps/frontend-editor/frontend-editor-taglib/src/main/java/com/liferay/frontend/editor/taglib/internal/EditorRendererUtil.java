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

package com.liferay.frontend.editor.taglib.internal;

import com.liferay.frontend.editor.EditorRenderer;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
public class EditorRendererUtil {

	public static EditorRenderer getEditorRenderer(String name) {
		if (Validator.isNull(name)) {
			return null;
		}

		return _editorRendererMap.get(name);
	}

	public static void start(final BundleContext bundleContext) {
		_serviceTracker = new ServiceTracker<EditorRenderer, EditorRenderer>(
			bundleContext, EditorRenderer.class,
			new ServiceTrackerCustomizer<EditorRenderer, EditorRenderer>() {

				@Override
				public EditorRenderer addingService(
					ServiceReference<EditorRenderer> serviceReference) {

					String name = (String)serviceReference.getProperty("name");

					EditorRenderer editorRenderer = bundleContext.getService(
						serviceReference);

					_editorRendererMap.put(name, editorRenderer);

					return editorRenderer;
				}

				@Override
				public void modifiedService(
					ServiceReference<EditorRenderer> serviceReference,
					EditorRenderer editorRenderer) {

					String name = (String)serviceReference.getProperty("name");

					if (_editorRendererMap.get(name) != editorRenderer) {
						Collection<EditorRenderer> editorRenderers =
							_editorRendererMap.values();

						editorRenderers.remove(editorRenderer);

						_editorRendererMap.put(name, editorRenderer);
					}
				}

				@Override
				public void removedService(
					ServiceReference<EditorRenderer> serviceReference,
					EditorRenderer editorRenderer) {

					String name = (String)serviceReference.getProperty("name");

					_editorRendererMap.remove(name);

					bundleContext.ungetService(serviceReference);
				}

			});

		_serviceTracker.open();
	}

	public static void stop() {
		_serviceTracker.close();

		_serviceTracker = null;
	}

	private static final Map<String, EditorRenderer> _editorRendererMap =
		new ConcurrentHashMap<>();
	private static ServiceTracker<EditorRenderer, EditorRenderer>
		_serviceTracker;

}
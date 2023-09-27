/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.editor.taglib.internal;

import com.liferay.frontend.editor.EditorRenderer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.List;
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

					EditorRenderer editorRenderer = bundleContext.getService(
						serviceReference);

					List<String> names = StringUtil.asList(
						serviceReference.getProperty("name"));

					for (String name : names) {
						_editorRendererMap.put(name, editorRenderer);
					}

					return editorRenderer;
				}

				@Override
				public void modifiedService(
					ServiceReference<EditorRenderer> serviceReference,
					EditorRenderer editorRenderer) {

					List<String> names = StringUtil.asList(
						serviceReference.getProperty("name"));

					for (String name : names) {
						if (_editorRendererMap.get(name) != editorRenderer) {
							Collection<EditorRenderer> editorRenderers =
								_editorRendererMap.values();

							editorRenderers.remove(editorRenderer);

							_editorRendererMap.put(name, editorRenderer);
						}
					}
				}

				@Override
				public void removedService(
					ServiceReference<EditorRenderer> serviceReference,
					EditorRenderer editorRenderer) {

					List<String> names = StringUtil.asList(
						serviceReference.getProperty("name"));

					for (String name : names) {
						_editorRendererMap.remove(name);
					}

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
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.editor.taglib.internal;

import com.liferay.frontend.editor.EditorRenderer;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Iván Zaera Avellón
 */
public class EditorRendererUtil {

	public static EditorRenderer getEditorRenderer(String name) {
		if (Validator.isNull(name)) {
			return null;
		}

		return _serviceTrackerMap.getService(name);
	}

	private static final ServiceTrackerMap<String, EditorRenderer>
		_serviceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(EditorRendererUtil.class);

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundle.getBundleContext(), EditorRenderer.class, "name");
	}

}
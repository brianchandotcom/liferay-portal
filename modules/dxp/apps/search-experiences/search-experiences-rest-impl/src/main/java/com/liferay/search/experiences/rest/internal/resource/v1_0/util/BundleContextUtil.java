/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.internal.resource.v1_0.util;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.search.experiences.rest.internal.resource.v1_0.OpenAPIResourceImpl;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class BundleContextUtil {

	public static String[] getComponentNames(Class<?> clazz) throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(OpenAPIResourceImpl.class);

		BundleContext bundleContext = bundle.getBundleContext();

		return TransformUtil.transform(
			bundleContext.getAllServiceReferences(clazz.getName(), null),
			serviceReference -> (String)serviceReference.getProperty(
				"component.name"),
			String.class);
	}

}
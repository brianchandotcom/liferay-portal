/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
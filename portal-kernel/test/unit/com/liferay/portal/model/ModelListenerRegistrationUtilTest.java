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

package com.liferay.portal.model;

import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.ModelListenerRegistrationUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import org.junit.Assert;
import org.junit.Test;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Leon Chi
 */
public class ModelListenerRegistrationUtilTest {

	@Test
	public void testGetModelListeners() {
		BaseModelListener<Contact> baseModelListener =
			new BaseModelListener<Contact>() {
			};

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		ServiceRegistration<ModelListener<?>> serviceRegistration =
			bundleContext.registerService(
				(Class<ModelListener<?>>)(Class<?>)ModelListener.class,
				baseModelListener, null);

		try {
			Assert.assertArrayEquals(
				new ModelListener[] {baseModelListener},
				ModelListenerRegistrationUtil.getModelListeners(Contact.class));
		}
		finally {
			serviceRegistration.unregister();
		}
	}

}
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
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leon Chi
 */
public class ModelListenerRegistrationUtilTest {

	@Test
	public void testGetModelListeners() {
		BaseModelListener<Contact> baseModelListener =
			new BaseModelListener<Contact>() {
			};

		RegistryUtil.setRegistry(new BasicRegistryImpl());

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<ModelListener> serviceRegistration =
			registry.registerService(ModelListener.class, baseModelListener);

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
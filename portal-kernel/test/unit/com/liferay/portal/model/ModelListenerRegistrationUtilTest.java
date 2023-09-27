/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
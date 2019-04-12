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

import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.ModelListenerRegistrationUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.model.bundle.modellistenerregistrationutil.TestModelListener;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleClassTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class ModelListenerRegistrationUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleClassTestRule(
				"bundle.modellistenerregistrationutil"));

	@Test
	public void testGetModelListeners() {
		List<ModelListener<Contact>> modelListeners = new ArrayList<>(
			Arrays.asList(
				ModelListenerRegistrationUtil.getModelListeners(
					Contact.class)));

		String testClassName = TestModelListener.class.getName();

		Assert.assertTrue(
			testClassName + " not found in " + modelListeners,
			modelListeners.removeIf(
				modelListener -> {
					Class<?> clazz = modelListener.getClass();

					return testClassName.equals(clazz.getName());
				}));
	}

}
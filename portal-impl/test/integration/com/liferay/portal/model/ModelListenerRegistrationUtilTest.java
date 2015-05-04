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

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.model.bundle.modellistenerregistrationutil.TestModelListener;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;

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
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.modellistenerregistrationutil"));

	@Test
	public void testGetModelListeners() {
		String testClassName = TestModelListener.class.getName();

		ModelListener<Contact>[] modelListeners =
			ModelListenerRegistrationUtil.getModelListeners(Contact.class);

		for (ModelListener<Contact> modelListener : modelListeners) {
			Class<?> clazz = modelListener.getClass();

			if (testClassName.equals(clazz.getName())) {
				return;
			}
		}

		Assert.fail();
	}

}
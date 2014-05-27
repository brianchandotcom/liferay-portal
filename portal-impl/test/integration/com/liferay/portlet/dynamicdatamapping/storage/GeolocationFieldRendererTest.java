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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testng.Assert;

import java.util.Locale;

/**
 * @author Adolfo Pérez
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class GeolocationFieldRendererTest {

	@Test
	public void testRenderedValuesShouldHave3DecimalPlaces() {
		FieldRenderer fieldRenderer = new GeolocationFieldRenderer();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("latitude", 9.8765);
		jsonObject.put("longitude", 1.2345);

		Field field = new Field("field", jsonObject.toString());

		Assert.assertEquals(
			"Latitude: 9.876, Longitude: 1.234",
			fieldRenderer.render(field, Locale.US));
	}

}
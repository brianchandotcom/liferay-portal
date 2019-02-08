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

package com.liferay.portal.vulcan.internal.context.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.internal.context.provider.test.util.MockMessage;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.jaxrs.ext.ContextProvider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class FilterContextProviderTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Registry registry = RegistryUtil.getRegistry();

		EntityModel entityModel = new EntityModel() {

			@Override
			public Map<String, EntityField> getEntityFieldsMap() {
				return Collections.singletonMap(
					"title",
					new StringEntityField("title", locale -> "internalTitle"));
			}

			@Override
			public String getName() {
				return ExampleResource.ODATA_ENTITY_MODEL_NAME;
			}

		};

		registry.registerService(
			EntityModel.class, entityModel,
			new HashMap<String, Object>() {
				{
					put(
						"entity.model.name",
						ExampleResource.ODATA_ENTITY_MODEL_NAME);
				}
			});
	}

	@Test
	public void testCreateContext() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest() {
				{
					addParameter("filter", "title eq 'example'");
				}
			};

		Filter filter = _contextProvider.createContext(
			new MockMessage(
				ExampleResource.class.getMethod(
					"exampleJaxRSMethod", String.class),
				mockHttpServletRequest));

		Assert.assertTrue(filter instanceof TermFilter);

		TermFilter termFilter = (TermFilter)filter;

		Assert.assertEquals("internalTitle", termFilter.getField());
		Assert.assertEquals("example", termFilter.getValue());
	}

	public class ExampleResource {

		public static final String ODATA_ENTITY_MODEL_NAME =
			"ExampleEntityModel";

		public String exampleJaxRSMethod(String param) {
			return "";
		}

	}

	@Inject(
		filter = "component.name=com.liferay.portal.vulcan.internal.context.provider.FilterContextProvider"
	)
	private ContextProvider<Filter> _contextProvider;

}
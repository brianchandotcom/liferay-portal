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

package com.liferay.portal.vulcan.internal.jaxrs.param.converter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.content.space.ContentSpace;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.test.util.MockFeature;

import java.lang.annotation.Annotation;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Feature;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class ContentSpaceParamConverterTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		MockFeature mockFeature = new MockFeature(_feature);

		ParamConverterProvider paramConverterProvider =
			(ParamConverterProvider)mockFeature.getObject(
				"com.liferay.portal.vulcan.internal.jaxrs.context.provider." +
					"VulcanParamConverterProvider");

		_contentSpaceParamConverter = paramConverterProvider.getConverter(
			ContentSpace.class, ContentSpace.class, new Annotation[0]);
	}

	@Test(expected = NotFoundException.class)
	public void testConvertInvalidGroup() {
		_contentSpaceParamConverter.fromString("0");
	}

	@Test
	public void testConvertValidGroup() throws Exception {
		Group group = GroupTestUtil.addGroup();

		ContentSpace contentSpace = _contentSpaceParamConverter.fromString(
			String.valueOf(group.getGroupId()));

		Assert.assertEquals(group.getGroupId(), contentSpace.getId());
	}

	private ParamConverter<ContentSpace> _contentSpaceParamConverter;

	@Inject(
		filter = "component.name=com.liferay.portal.vulcan.internal.jaxrs.feature.VulcanFeature"
	)
	private Feature _feature;

	@Inject
	private GroupLocalService _groupLocalService;

}
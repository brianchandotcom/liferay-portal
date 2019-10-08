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

package com.liferay.website.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.WebsiteURLException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.WebsiteLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael C. Han
 */
@RunWith(Arquillian.class)
public class WebsiteLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test(expected = WebsiteURLException.class)
	public void testAddWebsiteInvalidURL() throws Exception {
		User user = TestPropsValues.getUser();

		List<ListType> listTypes = _listTypeLocalService.getListTypes(
			"com.liferay.portal.kernel.model.Contact.website");

		ListType listType = listTypes.get(0);

		_websiteLocalService.addWebsite(
			user.getUserId(), Contact.class.getName(), user.getContactId(),
			"http://www,invalid.com", listType.getListTypeId(), true,
			ServiceContextTestUtil.getServiceContext());
	}

	@Inject
	private ListTypeLocalService _listTypeLocalService;

	@Inject
	private WebsiteLocalService _websiteLocalService;

}
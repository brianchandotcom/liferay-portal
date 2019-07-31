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

package com.liferay.headless.admin.user.graphql.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.client.dto.v1_0.WebUrl;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.service.ListTypeServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WebsiteLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;

import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class WebUrlGraphQLTest extends BaseWebUrlGraphQLTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addGroupAdminUser(testGroup);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"url"};
	}

	@Override
	protected WebUrl randomWebUrl() {
		return new WebUrl() {
			{
				url = "http://" + RandomTestUtil.randomString();
			}
		};
	}

	@Override
	protected WebUrl testWebUrl_addWebUrl() throws Exception {
		WebUrl webUrl = randomWebUrl();

		List<ListType> listTypes = ListTypeServiceUtil.getListTypes(
			ListTypeConstants.CONTACT_WEBSITE);

		ListType listType = listTypes.get(0);

		return _toWebUrl(
			WebsiteLocalServiceUtil.addWebsite(
				_user.getUserId(), Contact.class.getName(),
				_user.getContactId(), webUrl.getUrl(), listType.getListTypeId(),
				false, new ServiceContext()));
	}

	private WebUrl _toWebUrl(Website website) {
		return new WebUrl() {
			{
				id = website.getWebsiteId();
				url = website.getUrl();
			}
		};
	}

	@DeleteAfterTestRun
	private User _user;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.website.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.WebsiteURLException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.WebsiteLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class WebsiteLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user = TestPropsValues.getUser();

		List<ListType> listTypes = _listTypeLocalService.getListTypes(
			"com.liferay.portal.kernel.model.Contact.website");

		_listType = listTypes.get(0);
	}

	@Test
	public void testAddWebsite() throws Exception {
		_website = _websiteLocalService.addWebsite(
			_user.getUserId(), Contact.class.getName(), _user.getContactId(),
			_VALID_URL, _listType.getListTypeId(), true,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertNotNull(_website);
		Assert.assertNotNull(
			_websiteLocalService.fetchWebsite(_website.getWebsiteId()));
	}

	@Test(expected = WebsiteURLException.class)
	public void testAddWebsiteInvalidURL() throws Exception {
		_website = _websiteLocalService.addWebsite(
			_user.getUserId(), Contact.class.getName(), _user.getContactId(),
			_INVALID_URL, _listType.getListTypeId(), true,
			ServiceContextTestUtil.getServiceContext());
	}

	@Test
	public void testUpdateWebsite() throws Exception {
		_website = _websiteLocalService.addWebsite(
			_user.getUserId(), Contact.class.getName(), _user.getContactId(),
			_VALID_URL, _listType.getListTypeId(), true,
			ServiceContextTestUtil.getServiceContext());

		_website = _websiteLocalService.updateWebsite(
			_website.getWebsiteId(), _website.getUrl(),
			_website.getListTypeId(), false);

		Assert.assertFalse(_website.isPrimary());
	}

	@Test(expected = WebsiteURLException.class)
	public void testUpdateWebsiteInvalidURL() throws Exception {
		_website = _websiteLocalService.addWebsite(
			_user.getUserId(), Contact.class.getName(), _user.getContactId(),
			_VALID_URL, _listType.getListTypeId(), true,
			ServiceContextTestUtil.getServiceContext());

		_websiteLocalService.updateWebsite(
			_website.getWebsiteId(), _INVALID_URL, _website.getListTypeId(),
			_website.isPrimary());
	}

	private static final String _INVALID_URL = "http://www,invalid.com";

	private static final String _VALID_URL = "http://www.valid.com";

	private ListType _listType;

	@Inject
	private ListTypeLocalService _listTypeLocalService;

	private User _user;

	@DeleteAfterTestRun
	private Website _website;

	@Inject
	private WebsiteLocalService _websiteLocalService;

}
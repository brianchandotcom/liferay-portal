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

package com.liferay.portal.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogWrapper;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.verify.VerifyLayout;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.test.util.BaseVerifyProcessTestCase;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class VerifyLayoutTest extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_verifyLayoutLog = ReflectionTestUtil.getFieldValue(
			VerifyLayout.class, "_log");

		ReflectionTestUtil.setFieldValue(
			VerifyLayout.class, "_log",
			new VerifyLayoutLogWrapper(_verifyLayoutLog));

		_connection = DataAccess.getConnection();

		for (String keyword : PropsValues.LAYOUT_FRIENDLY_URL_KEYWORDS) {
			if (!keyword.contains(StringPool.STAR)) {
				_keyword = keyword;

				break;
			}
		}

		_layout = _layoutLocalService.addLayout(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(), false, 0,
			"name", "title", "description", LayoutConstants.TYPE_PORTLET, false,
			"/friendlyURL",
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId()));
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		ReflectionTestUtil.setFieldValue(
			VerifyLayout.class, "_log", _verifyLayoutLog);

		_layoutLocalService.deleteLayout(_layout);

		DataAccess.cleanUp(_connection);
	}

	@After
	public void tearDown() throws Exception {
		_errorMessages = new ArrayList<>();

		_updateFriendlyURL(_layout.getPlid(), _layout.getFriendlyURL());
	}

	@Test
	public void testVerifyLayoutsWithoutReservedLayoutFriendlyURL()
		throws Exception {

		super.testVerify();

		Assert.assertEquals(
			_errorMessages.toString(), 0, _errorMessages.size());
	}

	@Test
	public void testVerifyLayoutsWithReservedLayoutFriendlyURL()
		throws Exception {

		_updateFriendlyURL(
			_layout.getPlid(), StringPool.FORWARD_SLASH + _keyword);

		super.testVerify();

		Assert.assertEquals(
			_errorMessages.toString(), 1, _errorMessages.size());

		String errorMessage = _errorMessages.get(0);

		Assert.assertTrue(errorMessage.contains(_keyword));
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new VerifyLayout();
	}

	private void _updateFriendlyURL(long plid, String friendlyURL)
		throws Exception {

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"update Layout set friendlyURL = ? where plid = ?")) {

			preparedStatement.setString(1, friendlyURL);
			preparedStatement.setLong(2, plid);

			preparedStatement.executeUpdate();
		}
	}

	private static Connection _connection;
	private static List<String> _errorMessages;
	private static String _keyword;
	private static Layout _layout;

	@Inject
	private static LayoutLocalService _layoutLocalService;

	private static Log _verifyLayoutLog;

	private static class VerifyLayoutLogWrapper extends LogWrapper {

		public VerifyLayoutLogWrapper(Log log) {
			super(log);
		}

		@Override
		public void error(Object msg) {
			super.error(msg);

			_errorMessages.add((String)msg);
		}

	}

}
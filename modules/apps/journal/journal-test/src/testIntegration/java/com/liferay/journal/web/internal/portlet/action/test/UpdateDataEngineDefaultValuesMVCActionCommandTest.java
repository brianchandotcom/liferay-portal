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

package com.liferay.journal.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upload.UploadPortletRequestImpl;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockActionRequest;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

import javax.portlet.ActionRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class UpdateDataEngineDefaultValuesMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddArticleDefaultValuesWithoutDisplayDate()
		throws Exception {

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		MockActionRequest mockActionRequest = new MockActionRequest();

		mockActionRequest.setAttribute(
			PortletServlet.PORTLET_SERVLET_REQUEST,
			new MockHttpServletRequest());

		mockActionRequest.addParameter(
			ActionRequest.ACTION_NAME,
			"/journal/add_data_engine_default_values");
		mockActionRequest.addParameter(
			"groupId", String.valueOf(ddmStructure.getGroupId()));

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.HOUR_OF_DAY, 1);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		calendar.add(Calendar.MONTH, 1);

		Calendar reviewCalendar = calendar;

		calendar.add(Calendar.MONTH, 1);

		Calendar expireCalendar = calendar;

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					new MockHttpServletRequest(), new HashMap<>(),
					HashMapBuilder.put(
						ActionRequest.ACTION_NAME,
						Collections.singletonList(
							"/journal/add_data_engine_default_values")
					).put(
						"classNameId",
						Collections.singletonList(
							String.valueOf(
								_portal.getClassNameId(
									DDMStructure.class.getName())))
					).put(
						"classPK",
						Collections.singletonList(
							String.valueOf(ddmStructure.getStructureId()))
					).put(
						"ddmStructureKey",
						Collections.singletonList(
							ddmStructure.getStructureKey())
					).put(
						"expirationDateDay",
						Collections.singletonList(
							String.valueOf(
								expireCalendar.get(Calendar.DAY_OF_MONTH)))
					).put(
						"expirationDateHour",
						Collections.singletonList(
							String.valueOf(expireCalendar.get(Calendar.HOUR)))
					).put(
						"expirationDateMinute",
						Collections.singletonList(
							String.valueOf(expireCalendar.get(Calendar.MINUTE)))
					).put(
						"expirationDateMonth",
						Collections.singletonList(
							String.valueOf(expireCalendar.get(Calendar.MONTH)))
					).put(
						"expirationDateYear",
						Collections.singletonList(
							String.valueOf(expireCalendar.get(Calendar.YEAR)))
					).put(
						"groupId",
						Collections.singletonList(
							String.valueOf(ddmStructure.getGroupId()))
					).put(
						"reviewDateDay",
						Collections.singletonList(
							String.valueOf(
								reviewCalendar.get(Calendar.DAY_OF_MONTH)))
					).put(
						"reviewDateHour",
						Collections.singletonList(
							String.valueOf(reviewCalendar.get(Calendar.HOUR)))
					).put(
						"reviewDateMinute",
						Collections.singletonList(
							String.valueOf(reviewCalendar.get(Calendar.MINUTE)))
					).put(
						"reviewDateMonth",
						Collections.singletonList(
							String.valueOf(reviewCalendar.get(Calendar.MONTH)))
					).put(
						"reviewDateYear",
						Collections.singletonList(
							String.valueOf(reviewCalendar.get(Calendar.YEAR)))
					).build()),
				null, RandomTestUtil.randomString());

		uploadPortletRequest.setAttribute(
			WebKeys.CURRENT_URL, "http://localhost:8080");

		JournalArticle article = ReflectionTestUtil.invoke(
			_mvcActionCommand, "_addOrUpdateArticleDefaultValues",
			new Class<?>[] {ActionRequest.class, UploadPortletRequest.class},
			mockActionRequest, uploadPortletRequest);

		Assert.assertNotNull(article);

		Assert.assertEquals(
			expireCalendar.getTime(), article.getExpirationDate());
		Assert.assertEquals(reviewCalendar.getTime(), article.getReviewDate());
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject(filter = "mvc.command.name=/journal/add_data_engine_default_values")
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private Portal _portal;

}
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

package com.liferay.fragment.internal.struts.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.sharepoint.methods.Method;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Pavel Savinov
 */
@RunWith(Arquillian.class)
public class RenderFragmentEntryStrutsActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_bundle = FrameworkUtil.getBundle(getClass());

		_group = GroupTestUtil.addGroup();

		_groupUser = UserTestUtil.addOmniAdminUser();

		_guestUser = UserTestUtil.addGroupUser(_group, RoleConstants.GUEST);

		ServiceTestUtil.setUser(_groupUser);
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(_group);
	}

	@Test
	public void testRenderFragment() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(
				Method.POST, "/portal/fragment/render_fragment_entry");

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			mockHttpServletResponse, unsyncStringWriter);

		mockHttpServletRequest.setParameter(
			"groupId", String.valueOf(_group.getGroupId()));

		URL htmlUrl = _bundle.getEntry(
			_RESOURCES_PATH + "fragments/card/index.html");

		mockHttpServletRequest.setParameter(
			"html", StringUtil.read(htmlUrl.openStream()));

		URL cssUrl = _bundle.getEntry(
			_RESOURCES_PATH + "fragments/card/index.css");

		mockHttpServletRequest.setParameter(
			"css", StringUtil.read(cssUrl.openStream()));

		URL jsUrl = _bundle.getEntry(
			_RESOURCES_PATH + "fragments/card/index.js");

		mockHttpServletRequest.setParameter(
			"js", StringUtil.read(jsUrl.openStream()));

		_setUpEnvironment(
			mockHttpServletRequest, mockHttpServletResponse, _groupUser);

		_renderFragmentEntryStrutsAction.execute(
			mockHttpServletRequest, pipingServletResponse);

		URL renderedUrl = _bundle.getEntry(
			_RESOURCES_PATH + "render/simple.html");

		String actualHTML = _getHTML(unsyncStringWriter.toString());

		String expectedHTML = _getHTML(
			StringUtil.read(renderedUrl.openStream()));

		Assert.assertEquals(expectedHTML, actualHTML);
	}

	@Test(expected = PrincipalException.class)
	public void testRenderFragmentWithoutPermissions() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(
				Method.POST, "/portal/fragment/render_fragment_entry");

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_setUpEnvironment(
			mockHttpServletRequest, mockHttpServletResponse, _guestUser);

		mockHttpServletRequest.setParameter(
			"groupId", String.valueOf(_group.getGroupId()));

		_renderFragmentEntryStrutsAction.execute(
			mockHttpServletRequest, mockHttpServletResponse);
	}

	private String _getHTML(String html) {
		Document document = Jsoup.parseBodyFragment(html);

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.indentAmount(0);
		outputSettings.prettyPrint(false);

		document.outputSettings(outputSettings);

		Element bodyElement = document.body();

		Elements elements = bodyElement.getElementsByTag("title");

		elements.remove();

		elements = bodyElement.getElementsByTag("link");

		elements.remove();

		elements = bodyElement.getElementsByTag("script");

		elements.remove();

		return _removeSpacingCharactersBetweenTags(bodyElement);
	}

	private String _removeSpacingCharactersBetweenTags(Element bodyElement) {
		String htmlString = bodyElement.html();

		htmlString = htmlString.replaceAll(">\\s+", ">");

		return htmlString.replaceAll("\\s+<", "<");
	}

	private void _setUpEnvironment(
			MockHttpServletRequest mockHttpServletRequest,
			MockHttpServletResponse mockHttpServletResponse, User user)
		throws Exception {

		mockHttpServletRequest.setAttribute(
			WebKeys.CURRENT_URL, "/portal/fragment/render_fragment_entry");

		mockHttpServletRequest.setAttribute(WebKeys.USER, user);

		EventsProcessorUtil.process(
			PropsKeys.SERVLET_SERVICE_EVENTS_PRE,
			PropsValues.SERVLET_SERVICE_EVENTS_PRE, mockHttpServletRequest,
			mockHttpServletResponse);
	}

	private static final String _RESOURCES_PATH =
		"com/liferay/fragment/dependencies/fragments/";

	private Bundle _bundle;
	private Group _group;
	private User _groupUser;

	@DeleteAfterTestRun
	private User _guestUser;

	@Inject(filter = "component.name=*.RenderFragmentEntryStrutsAction")
	private StrutsAction _renderFragmentEntryStrutsAction;

}
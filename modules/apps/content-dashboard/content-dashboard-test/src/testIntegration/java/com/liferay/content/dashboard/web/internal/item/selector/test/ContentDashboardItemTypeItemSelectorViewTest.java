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

package com.liferay.content.dashboard.web.internal.item.selector.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
@Sync
public class ContentDashboardItemTypeItemSelectorViewTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetSearchContainer() throws Exception {
		DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm(
			"content", "string", "text", true, "textarea",
			new Locale[] {LocaleUtil.US}, LocaleUtil.US);

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName(), 0, ddmForm,
			LocaleUtil.US, ServiceContextTestUtil.getServiceContext());

		SearchContainer<Object> searchContainer = _getSearchContainer(
			ddmStructure.getName(LocaleUtil.US));

		List<Object> results = searchContainer.getResults();

		Assert.assertEquals(results.toString(), 1, results.size());

		Assert.assertEquals(
			ddmStructure.getName(LocaleUtil.US),
			ReflectionTestUtil.invoke(
				results.get(0), "getLabel", new Class<?>[] {Locale.class},
				LocaleUtil.US));
	}

	@Test
	public void testGetSearchContainerWithoutKeywords() throws Exception {
		SearchContainer<Object> searchContainer = _getSearchContainer(null);

		List<Object> results = searchContainer.getResults();

		int initialCount = results.size();

		DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm(
			"content", "string", "text", true, "textarea",
			new Locale[] {LocaleUtil.getSiteDefault()},
			LocaleUtil.getSiteDefault());

		DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName(), 0, ddmForm,
			LocaleUtil.getSiteDefault(),
			ServiceContextTestUtil.getServiceContext());

		searchContainer = _getSearchContainer(null);

		results = searchContainer.getResults();

		Assert.assertEquals(
			results.toString(), initialCount + 1, results.size());
	}

	private SearchContainer<Object> _getSearchContainer(String keywords)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		mockLiferayPortletRenderRequest.setAttribute(
			"null" + StringPool.DASH + WebKeys.CURRENT_PORTLET_URL,
			new MockLiferayPortletURL());

		mockLiferayPortletRenderRequest.setParameter("keywords", keywords);

		mockHttpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST,
			mockLiferayPortletRenderRequest);

		mockHttpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE,
			new MockLiferayPortletRenderResponse());

		_contentDashboardItemTypeItemSelectorView.renderHTML(
			mockHttpServletRequest, new MockHttpServletResponse(), null,
			new MockLiferayPortletURL(), RandomTestUtil.randomString(), true);

		Object contentDashboardItemTypeItemSelectorViewDisplayContext =
			mockHttpServletRequest.getAttribute(
				"com.liferay.content.dashboard.web.internal.display.context." +
					"ContentDashboardItemTypeItemSelectorViewDisplayContext");

		return ReflectionTestUtil.invoke(
			contentDashboardItemTypeItemSelectorViewDisplayContext,
			"getSearchContainer", new Class<?>[0], null);
	}

	@Inject(
		filter = "component.name=*.ContentDashboardItemTypeItemSelectorView",
		type = ItemSelectorView.class
	)
	private ItemSelectorView<ItemSelectorCriterion>
		_contentDashboardItemTypeItemSelectorView;

	@DeleteAfterTestRun
	private Group _group;

}
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

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class UpdateSegmentsExperienceMVCActionCommandTest {

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
	public void testProcessAction() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_group);

		SegmentsExperience initialSegmentsExperience =
			_segmentsExperienceLocalService.addSegmentsExperience(
				SegmentsExperienceConstants.ID_DEFAULT,
				_classNameLocalService.getClassNameId(Layout.class),
				layout.getPlid(), RandomTestUtil.randomLocaleStringMap(), 0,
				true,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, new ThemeDisplay());

		mockLiferayPortletActionRequest.addParameter(
			"languageIds",
			StringUtil.merge(
				LocaleUtil.toLanguageIds(
					new Locale[] {LocaleUtil.US, LocaleUtil.BRAZIL}),
				StringPool.COMMA));
		mockLiferayPortletActionRequest.addParameter("name", "newName");

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		mockLiferayPortletActionRequest.addParameter(
			"segmentsEntryId",
			String.valueOf(segmentsEntry.getSegmentsEntryId()));

		mockLiferayPortletActionRequest.addParameter(
			"segmentsExperienceId",
			String.valueOf(
				initialSegmentsExperience.getSegmentsExperienceId()));

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		SegmentsExperience actualSegmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				initialSegmentsExperience.getSegmentsExperienceId());

		Assert.assertEquals(
			"newName",
			actualSegmentsExperience.getName(LocaleUtil.getSiteDefault()));

		UnicodeProperties typeSettingsUnicodeProperties =
			actualSegmentsExperience.getTypeSettingsUnicodeProperties();

		Assert.assertEquals(
			StringUtil.merge(
				LocaleUtil.toLanguageIds(
					new Locale[] {LocaleUtil.US, LocaleUtil.BRAZIL}),
				StringPool.COMMA),
			typeSettingsUnicodeProperties.get(PropsKeys.LOCALES));

		Assert.assertEquals(
			segmentsEntry.getSegmentsEntryId(),
			actualSegmentsExperience.getSegmentsEntryId());
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/update_segments_experience"
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}
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

package com.liferay.portlet.messageboards.lar;

import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.lar.test.BasePortletDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Zsolt Berentey
 */
public class MBPortletDataHandlerTest extends BasePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Test
	public void testDeleteAllFolders() throws Exception {
		Group group = GroupTestUtil.addGroup();

		MBCategory parentCategory = MBTestUtil.addCategory(group.getGroupId());

		MBCategory childCategory = MBTestUtil.addCategory(
			group.getGroupId(), parentCategory.getCategoryId());

		MBCategoryLocalServiceUtil.moveCategoryToTrash(
			TestPropsValues.getUserId(), childCategory.getCategoryId());

		MBCategoryLocalServiceUtil.moveCategoryToTrash(
			TestPropsValues.getUserId(), parentCategory.getCategoryId());

		MBCategoryLocalServiceUtil.deleteCategory(parentCategory, false);

		GroupLocalServiceUtil.deleteGroup(group);

		List<MBCategory> categories = MBCategoryLocalServiceUtil.getCategories(
			group.getGroupId());

		Assert.assertEquals(0, categories.size());
	}

	@Override
	protected void addParameters(Map<String, String[]> parameterMap) {
		addBooleanParameter(
			parameterMap, MBPortletDataHandler.NAMESPACE, "messages", true);
		addBooleanParameter(
			parameterMap, MBPortletDataHandler.NAMESPACE, "thread-flags", true);
		addBooleanParameter(
			parameterMap, MBPortletDataHandler.NAMESPACE, "user-bans", true);
	}

	@Override
	protected void addStagedModels() throws Exception {
		MBCategory category = MBTestUtil.addCategory(stagingGroup.getGroupId());

		MBMessage message = MBTestUtil.addMessageWithWorkflow(
			stagingGroup.getGroupId(), category.getCategoryId(), true);

		MBTestUtil.addThreadFlag(
			stagingGroup.getGroupId(), message.getThread());

		MBTestUtil.addBan(stagingGroup.getGroupId());
	}

	@Override
	protected void checkManifestSummary(
		Map<String, LongWrapper> expectedModelAdditionCounters) {

		String manifestSummaryKey = ManifestSummary.getManifestSummaryKey(
			MBThread.class.getName(), null);

		expectedModelAdditionCounters.remove(manifestSummaryKey);

		super.checkManifestSummary(expectedModelAdditionCounters);
	}

	@Override
	protected PortletDataHandler createPortletDataHandler() {
		return new MBPortletDataHandler();
	}

	@Override
	protected String getPortletId() {
		return PortletKeys.MESSAGE_BOARDS;
	}

}
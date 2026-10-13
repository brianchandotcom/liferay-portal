/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.workflow.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotAppCustomizationLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandlerVisibleFilter;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class WorkflowHandlerVisibleFilterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_depotEntry = _depotEntryLocalService.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			DepotConstants.TYPE_ASSET_LIBRARY,
			ServiceContextTestUtil.getServiceContext());
	}

	@Test
	public void testIsVisible() throws Exception {
		_testIsVisible();
		_testIsVisibleWithDisabledDepotApplication();
		_testIsVisibleWithUnsupportedWorkflowHandler();
	}

	private void _testIsVisible() throws Exception {
		Assert.assertTrue(
			_workflowHandlerVisibleFilter.isVisible(
				WorkflowHandlerRegistryUtil.getWorkflowHandler(
					JournalArticle.class.getName()),
				_depotEntry.getGroup()));
	}

	private void _testIsVisibleWithDisabledDepotApplication() throws Exception {
		_depotAppCustomizationLocalService.updateDepotAppCustomization(
			_depotEntry.getDepotEntryId(), false, JournalPortletKeys.JOURNAL);

		Assert.assertFalse(
			_workflowHandlerVisibleFilter.isVisible(
				WorkflowHandlerRegistryUtil.getWorkflowHandler(
					JournalArticle.class.getName()),
				_depotEntry.getGroup()));
	}

	private void _testIsVisibleWithUnsupportedWorkflowHandler()
		throws Exception {

		Assert.assertFalse(
			_workflowHandlerVisibleFilter.isVisible(
				WorkflowHandlerRegistryUtil.getWorkflowHandler(
					"com.liferay.blogs.model.BlogsEntry"),
				_depotEntry.getGroup()));
	}

	@Inject
	private DepotAppCustomizationLocalService
		_depotAppCustomizationLocalService;

	@DeleteAfterTestRun
	private DepotEntry _depotEntry;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private WorkflowHandlerVisibleFilter _workflowHandlerVisibleFilter;

}
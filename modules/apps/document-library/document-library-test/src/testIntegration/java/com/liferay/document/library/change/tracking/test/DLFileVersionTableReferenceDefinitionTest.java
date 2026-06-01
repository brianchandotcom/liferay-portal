/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseTableReferenceDefinitionTestCase;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class DLFileVersionTableReferenceDefinitionTest
	extends BaseTableReferenceDefinitionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(
			group.getCreatorUserId(), group.getCompanyId(), group.getGroupId(),
			DLFolder.class.getName(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL, "Single Approver",
			1);
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			FileEntry fileEntry = _dlAppLocalService.addFileEntry(
				null, group.getCreatorUserId(), group.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN,
				RandomTestUtil.randomString(), StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK,
				TestDataConstants.TEST_BYTE_ARRAY, null, null, null,
				serviceContext);

			return (DLFileEntry)fileEntry.getModel();
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.test.util;

import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBComment;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
import com.liferay.knowledge.base.service.KBCommentLocalServiceUtil;
import com.liferay.knowledge.base.service.KBFolderLocalServiceUtil;
import com.liferay.knowledge.base.service.KBTemplateLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author Vy Bui
 */
public class KBTestUtil {

	public static KBArticle addKBArticle(long groupId) throws PortalException {
		return KBArticleLocalServiceUtil.addKBArticle(
			null, TestPropsValues.getUserId(),
			PortalUtil.getClassNameId(KBFolder.class.getName()), 0,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			null, RandomTestUtil.nextDate(), null, null, null,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static File addKBArticleAttachment(
			String attachmentFileName, Class<?> clazz, long resourcePrimKey,
			String testFileName, long userId)
		throws Exception {

		byte[] bytes = FileUtil.getBytes(clazz, "dependencies/" + testFileName);

		if (ArrayUtil.isEmpty(bytes)) {
			throw new RuntimeException("File not found: " + testFileName);
		}

		File file = FileUtil.createTempFile(bytes);

		try (FileInputStream fileInputStream = new FileInputStream(file)) {
			KBArticleLocalServiceUtil.addAttachment(
				userId, resourcePrimKey, attachmentFileName, fileInputStream,
				MimeTypesUtil.getContentType(file));
		}

		return file;
	}

	public static KBArticle addKBArticleWithWorkflow(
			long userId, boolean approved, long parentResourcePrimKey,
			String title, ServiceContext serviceContext)
		throws PortalException {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			serviceContext = (ServiceContext)serviceContext.clone();

			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			KBArticle kbArticle = KBArticleLocalServiceUtil.addKBArticle(
				null, userId,
				PortalUtil.getClassNameId(KBFolder.class.getName()),
				parentResourcePrimKey, title, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				null, null, RandomTestUtil.nextDate(), null, null, null,
				serviceContext);

			if (approved) {
				return KBArticleLocalServiceUtil.updateStatus(
					kbArticle.getUserId(), kbArticle.getResourcePrimKey(),
					WorkflowConstants.STATUS_APPROVED, serviceContext);
			}

			return kbArticle;
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	public static KBComment addKBComment(long kbArticleId)
		throws PortalException {

		KBArticle kbArticle = KBArticleLocalServiceUtil.getKBArticle(
			kbArticleId);

		return KBCommentLocalServiceUtil.addKBComment(
			kbArticle.getUserId(), kbArticle.getClassNameId(),
			kbArticle.getClassPK(), StringUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(kbArticle.getGroupId()));
	}

	public static KBFolder addKBFolder(long groupId) throws PortalException {
		return addKBFolder(
			groupId, KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static KBFolder addKBFolder(
			long groupId, long parentResourcePrimKey,
			ServiceContext serviceContext)
		throws PortalException {

		return KBFolderLocalServiceUtil.addKBFolder(
			null, TestPropsValues.getUserId(), groupId,
			PortalUtil.getClassNameId(KBFolder.class.getName()),
			parentResourcePrimKey, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);
	}

	public static KBTemplate addKBTemplate(long groupId)
		throws PortalException {

		return KBTemplateLocalServiceUtil.addKBTemplate(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(groupId));
	}

}
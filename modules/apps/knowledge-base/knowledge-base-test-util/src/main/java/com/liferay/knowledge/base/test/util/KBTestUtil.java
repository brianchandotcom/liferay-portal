/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.test.util;

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
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Date;

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
			null, new Date(), null, null, null,
			ServiceContextTestUtil.getServiceContext(groupId));
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
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return KBFolderLocalServiceUtil.addKBFolder(
			null, TestPropsValues.getUserId(), groupId,
			PortalUtil.getClassNameId(KBFolder.class.getName()), 0,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);
	}

	public static KBTemplate addKBTemplate(long groupId)
		throws PortalException {

		return KBTemplateLocalServiceUtil.addKBTemplate(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(groupId));
	}

}
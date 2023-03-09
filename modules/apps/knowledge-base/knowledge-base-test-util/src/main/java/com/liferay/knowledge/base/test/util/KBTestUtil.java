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
			null, null, null, null,
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
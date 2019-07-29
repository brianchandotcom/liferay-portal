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

package com.liferay.knowledge.base.internal.util;

import com.liferay.knowledge.base.model.KBComment;
import com.liferay.knowledge.base.service.KBCommentLocalServiceUtil;
import com.liferay.knowledge.base.service.persistence.KBCommentPersistence;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import java.util.List;

/**
 * @author Hai Yu
 */
public class KBCommentUtil {

	public static void deleteKBComments(
			String className, ClassNameLocalService classNameLocalService,
			long classPK, KBCommentPersistence kbCommentPersistence)
		throws PortalException {

		List<KBComment> kbComments = kbCommentPersistence.findByC_C(
			classNameLocalService.getClassNameId(className), classPK);

		for (KBComment kbComment : kbComments) {
			KBCommentLocalServiceUtil.deleteKBComment(kbComment);
		}
	}

}
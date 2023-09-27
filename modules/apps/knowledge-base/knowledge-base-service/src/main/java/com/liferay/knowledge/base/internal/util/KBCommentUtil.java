/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
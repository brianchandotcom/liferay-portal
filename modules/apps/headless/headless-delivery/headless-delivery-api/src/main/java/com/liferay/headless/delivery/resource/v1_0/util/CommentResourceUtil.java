/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.resource.v1_0.util;

import com.liferay.portal.kernel.comment.Comment;

/**
 * @author Jhosseph Gonzalez
 */
public class CommentResourceUtil {

	public static boolean isAssociated(
		String className, long classPK, Comment comment) {

		if (className.equals(comment.getClassName()) &&
			(classPK == comment.getClassPK())) {

			return true;
		}

		return false;
	}

}
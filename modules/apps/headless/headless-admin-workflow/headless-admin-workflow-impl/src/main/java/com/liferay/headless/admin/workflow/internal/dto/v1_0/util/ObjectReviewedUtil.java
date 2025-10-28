/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.workflow.internal.dto.v1_0.util;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.headless.admin.workflow.dto.v1_0.ObjectReviewed;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Rafael Praxedes
 */
public class ObjectReviewedUtil {

	public static ObjectReviewed toObjectReviewed(
		Locale locale, Map<String, Serializable> optionalAttributes) {

		String entryClassName = GetterUtil.getString(
			optionalAttributes.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME));

		WorkflowHandler<?> workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(entryClassName);

		if (workflowHandler == null) {
			return null;
		}

		long entryClassPK = GetterUtil.getLong(
			optionalAttributes.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		return new ObjectReviewed() {
			{
				setAssetTitle(
					() -> workflowHandler.getTitle(entryClassPK, locale));
				setAssetType(() -> workflowHandler.getType(locale));
				setId(() -> entryClassPK);
				setResourceType(
					() -> {
						if (Objects.equals(
								entryClassName, BlogsEntry.class.getName())) {

							return "BlogPosting";
						}
						else if (Objects.equals(
									entryClassName,
									MBDiscussion.class.getName())) {

							return "Comment";
						}

						return null;
					});
			}
		};
	}

}
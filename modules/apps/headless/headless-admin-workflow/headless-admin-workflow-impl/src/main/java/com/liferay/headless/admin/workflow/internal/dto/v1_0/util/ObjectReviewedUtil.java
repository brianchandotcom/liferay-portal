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

		return new ObjectReviewed() {
			{
				assetTitle = _getAssetTitle(
					GetterUtil.getLong(
						optionalAttributes.get(
							WorkflowConstants.CONTEXT_ENTRY_CLASS_PK)),
					GetterUtil.getString(
						optionalAttributes.get(
							WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME)),
					locale);
				assetType = _getAssetType(
					GetterUtil.getString(
						optionalAttributes.get(
							WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME)),
					locale);
				id = GetterUtil.getLong(
					optionalAttributes.get(
						WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
				resourceType = _toResourceType(
					GetterUtil.getString(
						optionalAttributes.get(
							WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME)));
			}
		};
	}

	private static String _getAssetTitle(
		long classPK, String entryClassName, Locale locale) {

		WorkflowHandler<?> workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(entryClassName);

		return workflowHandler.getTitle(classPK, locale);
	}

	private static String _getAssetType(String entryClassName, Locale locale) {
		WorkflowHandler<?> workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(entryClassName);

		return workflowHandler.getType(locale);
	}

	private static String _toResourceType(String entryClassName) {
		if (Objects.equals(entryClassName, BlogsEntry.class.getName())) {
			return "BlogPosting";
		}

		if (Objects.equals(entryClassName, MBDiscussion.class.getName())) {
			return "Comment";
		}

		return null;
	}

}
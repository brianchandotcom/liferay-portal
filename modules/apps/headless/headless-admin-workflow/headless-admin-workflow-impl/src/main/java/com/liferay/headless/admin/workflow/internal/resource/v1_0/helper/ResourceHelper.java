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

package com.liferay.headless.admin.workflow.internal.resource.v1_0.helper;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.headless.admin.workflow.dto.v1_0.ObjectReviewed;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = ResourceHelper.class)
public class ResourceHelper {

	public ObjectReviewed toObjectReviewed(
		Map<String, Serializable> optionalAttributes) {

		return new ObjectReviewed() {
			{
				id = GetterUtil.getLong(
					optionalAttributes.get(
						WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));
				resourceType = _getResourceType(optionalAttributes);
			}
		};
	}

	private String _getResourceType(
		Map<String, Serializable> optionalAttributes) {

		String className = GetterUtil.getString(
			optionalAttributes.get("entryClassName"));

		if (className.equals(BlogsEntry.class.getName())) {
			return "BlogPosting";
		}

		if (className.equals(MBDiscussion.class.getName())) {
			return "Comment";
		}

		return null;
	}

}
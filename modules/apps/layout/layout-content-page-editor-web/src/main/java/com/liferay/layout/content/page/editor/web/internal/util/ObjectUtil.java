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

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class ObjectUtil {

	public static Boolean hideInputFragments(long companyId) {
		List<ObjectDefinition> objectDefinitions =
			ObjectDefinitionLocalServiceUtil.getObjectDefinitions(
				companyId, true, false, WorkflowConstants.STATUS_APPROVED);

		if (objectDefinitions.isEmpty()) {
			return true;
		}

		return false;
	}

}
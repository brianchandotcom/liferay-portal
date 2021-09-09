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

package com.liferay.object.model.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectDefinitionImpl extends ObjectDefinitionBaseImpl {

	public static String getShortName(String name) {
		String shortName = name;

		if (shortName.startsWith("C_")) {
			shortName = shortName.substring(2);
		}

		return shortName;
	}

	@Override
	public String getClassName() {
		if (isSystem()) {
			throw new UnsupportedOperationException();
		}

		return "com.liferay.object.model.ObjectDefinition#" +
			getObjectDefinitionId();
	}

	@Override
	public String getDestinationName() {
		return StringBundler.concat(
			"liferay/object/", getCompanyId(), StringPool.SLASH,
			getShortName());
	}

	@Override
	public String getExtensionDBTableName() {
		if (isSystem()) {
			return StringBundler.concat(
				getDBTableName(), "_x_", getCompanyId());
		}

		return getDBTableName() + "_x";
	}

	@Override
	public String getPortletId() {
		if (isSystem()) {
			throw new UnsupportedOperationException();
		}

		return "com_liferay_object_web_internal_object_definitions_portlet_" +
			"ObjectDefinitionsPortlet_" + getObjectDefinitionId();
	}

	@Override
	public String getResourceName() {
		if (isSystem()) {
			throw new UnsupportedOperationException();
		}

		return "com.liferay.object#" + getObjectDefinitionId();
	}

	@Override
	public String getRESTContextPath() {
		if (isSystem()) {
			throw new UnsupportedOperationException();
		}

		return "/c/" +
			TextFormatter.formatPlural(StringUtil.toLowerCase(getShortName()));
	}

	@Override
	public String getShortName() {
		return getShortName(getName());
	}

	@Override
	public boolean isApproved() {
		if (getStatus() == WorkflowConstants.STATUS_APPROVED) {
			return true;
		}

		return false;
	}

}
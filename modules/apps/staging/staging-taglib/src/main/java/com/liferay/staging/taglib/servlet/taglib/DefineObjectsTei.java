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

package com.liferay.staging.taglib.servlet.taglib;

import com.liferay.portal.kernel.model.Group;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * @author Levente Hudák
 */
public class DefineObjectsTei extends TagExtraInfo {

	@Override
	public VariableInfo[] getVariableInfo(TagData tagData) {
		return Concealer._variableInfo;
	}

	private static class Concealer {

		private static final VariableInfo[] _variableInfo = {
			new VariableInfo(
				"group", Group.class.getName(), true, VariableInfo.AT_END),
			new VariableInfo(
				"groupId", Long.class.getName(), true, VariableInfo.AT_END),
			new VariableInfo(
				"liveGroup", Group.class.getName(), true, VariableInfo.AT_END),
			new VariableInfo(
				"liveGroupId", Long.class.getName(), true, VariableInfo.AT_END),
			new VariableInfo(
				"privateLayout", Boolean.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"scopeGroup", Group.class.getName(), true, VariableInfo.AT_END),
			new VariableInfo(
				"scopeGroupId", Long.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"stagingGroup", Group.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"stagingGroupId", Long.class.getName(), true,
				VariableInfo.AT_END)
		};

	}

}
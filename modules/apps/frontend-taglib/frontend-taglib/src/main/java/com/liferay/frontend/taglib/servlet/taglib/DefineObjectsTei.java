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

package com.liferay.frontend.taglib.servlet.taglib;

import java.util.ResourceBundle;

import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * @author Adolfo Pérez
 */
public class DefineObjectsTei extends TagExtraInfo {

	@Override
	public VariableInfo[] getVariableInfo(TagData data) {
		return Concealer._variableInfo;
	}

	private static class Concealer {

		private static final VariableInfo[] _variableInfo = {
			new VariableInfo(
				"currentURL", String.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"currentURLObj", PortletURL.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"npmResolvedPackageName", String.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"resourceBundle", ResourceBundle.class.getName(), true,
				VariableInfo.AT_END),
			new VariableInfo(
				"windowState", WindowState.class.getName(), true,
				VariableInfo.AT_END)
		};

	}

}
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

package com.liferay.source.formatter.checkstyle.checks;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public class PortletURLBuilderCheck extends BaseBuilderCheck {

	@Override
	protected boolean allowNullValues() {
		return true;
	}

	@Override
	protected List<BaseBuilderCheck.BuilderInformation>
		doGetBuilderInformationList() {

		return ListUtil.fromArray(
			new BaseBuilderCheck.BuilderInformation(
				"PortletURL", "PortletURLBuilder", "setActionName",
				"setBackURL", "setCMD", "setKeywords", "setMVCPath",
				"setMVCRenderCommandName", "setNavigation", "setParameter",
				"setParameters", "setPortletMode", "setPortletResource",
				"setProperty", "setRedirect", "setSecure", "setTabs1",
				"setTabs2", "setWindowState"));
	}

	@Override
	protected String getAssignClassName(DetailAST assignDetailAST) {
		List<DetailAST> identDetailASTList = getAllChildTokens(
			assignDetailAST, true, TokenTypes.IDENT);

		for (BaseBuilderCheck.BuilderInformation builderInformation :
				getBuilderInformationList()) {

			for (DetailAST identDetailAST : identDetailASTList) {
				if (Objects.equals(
						builderInformation.getBuilderClassName(),
						identDetailAST.getText())) {

					return null;
				}
			}
		}

		DetailAST parentDetailAST = assignDetailAST.getParent();

		if (parentDetailAST.getType() == TokenTypes.VARIABLE_DEF) {
			return getTypeName(parentDetailAST, false);
		}

		DetailAST identDetailAST = assignDetailAST.findFirstToken(
			TokenTypes.IDENT);

		if (identDetailAST != null) {
			return getVariableTypeName(
				assignDetailAST, identDetailAST.getText(), false);
		}

		return null;
	}

	@Override
	protected List<String> getAvoidCastStringMethodNames() {
		return ListUtil.fromArray("setParameter", "setRedirect");
	}

	@Override
	protected Map<String, String[][]> getReservedKeywordsMap() {
		return HashMapBuilder.put(
			"setParameter", _RESERVED_KEYWORDS
		).build();
	}

	@Override
	protected List<String> getSupportsFunctionMethodNames() {
		return ListUtil.fromArray(
			"setActionName", "setCMD", "setKeywords", "setMVCPath",
			"setMVCRenderCommandName", "setNavigation", "setParameter",
			"setPortletResource", "setRedirect", "setTabs1", "setTabs2");
	}

	@Override
	protected boolean isSupportsNestedMethodCalls() {
		return true;
	}

	private static final String[][] _RESERVED_KEYWORDS = {
		{"ActionRequest.ACTION_NAME", "setActionName"},
		{"Constants.CMD", "setCMD"}, {"backURL", "setBackURL"},
		{"cmd", "setCMD"}, {"javax.portlet.action", "setActionName"},
		{"keywords", "setKeywords"}, {"mvcPath", "setMVCPath"},
		{"mvcRenderCommandName", "setMVCRenderCommandName"},
		{"navigation", "setNavigation"}, {"p_p_mode", "setPortletMode"},
		{"p_p_state", "setWindowState"},
		{"portletResource", "setPortletResource"}, {"redirect", "setRedirect"},
		{"tabs1", "setTabs1"}, {"tabs2", "setTabs2"}
	};

}
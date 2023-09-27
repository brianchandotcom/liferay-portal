<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	redirect = PortletURLBuilder.createRenderURL(
		renderResponse
	).setMVCRenderCommandName(
		"/sxp_blueprint_admin/view_sxp_blueprints"
	).buildString();
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.get(request, "edit-blueprint"));
%>

<div>
	<span aria-hidden="true" class="loading-animation"></span>

	<react:component
		module="sxp_blueprint_admin/js/edit_sxp_blueprint/index"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"contextPath", application.getContextPath()
			).put(
				"defaultLocale", LocaleUtil.toLanguageId(LocaleUtil.getDefault())
			).put(
				"featureFlagLps153813", FeatureFlagManagerUtil.isEnabled("LPS-153813")
			).put(
				"isCompanyAdmin", permissionChecker.isCompanyAdmin()
			).put(
				"learnMessages", LearnMessageUtil.getJSONObject("search-experiences-web")
			).put(
				"locale", themeDisplay.getLanguageId()
			).put(
				"namespace", liferayPortletResponse.getNamespace()
			).put(
				"redirectURL", redirect
			).put(
				"sxpBlueprintId", ParamUtil.getLong(renderRequest, "sxpBlueprintId")
			).build()
		%>'
	/>
</div>
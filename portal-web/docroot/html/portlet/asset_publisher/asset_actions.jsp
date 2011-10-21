<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
boolean showIconLabel = ((Boolean)request.getAttribute("view.jsp-showIconLabel")).booleanValue();

AssetRenderer assetRenderer = (AssetRenderer)request.getAttribute("view.jsp-assetRenderer");

boolean showEditURL = ParamUtil.getBoolean(request, "showEditURL", true);

PortletURL editPortletURL = assetRenderer.getURLEdit(liferayPortletRequest, liferayPortletResponse);

String editPortletURLString = StringPool.BLANK;

if (showEditURL && (editPortletURL != null)) {
	editPortletURL.setWindowState(LiferayWindowState.POP_UP);
	editPortletURL.setPortletMode(PortletMode.VIEW);

	if (Validator.isNotNull(portletResource)) {
		editPortletURL.setParameter("referringPortletResource", portletResource);
	}
	else {
		editPortletURL.setParameter("referringPortletResource", portletDisplay.getId());
	}

	PortletURL redirectURL = renderResponse.createRenderURL();

	redirectURL.setWindowState(LiferayWindowState.POP_UP);

	redirectURL.setParameter("struts_action", "/asset_publisher/add_asset_redirect");

	editPortletURL.setParameter("redirect", HtmlUtil.escapeURL(redirectURL.toString()));

	editPortletURLString = editPortletURL.toString();

	editPortletURLString = HttpUtil.addParameter(editPortletURLString, "doAsGroupId", assetRenderer.getGroupId());
	editPortletURLString = HttpUtil.addParameter(editPortletURLString, "refererPlid", plid);
}

Group stageableGroup = themeDisplay.getScopeGroup();

if (themeDisplay.getScopeGroup().isLayout()) {
	stageableGroup = layout.getGroup();
}
%>

<c:if test="<%= assetRenderer.hasEditPermission(permissionChecker) && Validator.isNotNull(editPortletURLString) && !stageableGroup.hasStagingGroup() %>">
	<div class="lfr-meta-actions asset-actions">

		<%
		String taglibEditURL = "javascript:Liferay.Util.openWindow({dialog: {width: 960}, id: '" + renderResponse.getNamespace() + "', title: '" + LanguageUtil.format(pageContext, "edit-x", HtmlUtil.escape(assetRenderer.getTitle(locale))) + "', uri:'" + editPortletURLString + "'});";
		%>

		<liferay-ui:icon
			image="edit"
			label="<%= showIconLabel %>"
			message='<%= showIconLabel ? LanguageUtil.format(pageContext, "edit-x-x", new Object[] {"aui-helper-hidden-accessible", HtmlUtil.escape(assetRenderer.getTitle(locale))}) : LanguageUtil.format(pageContext, "edit-x", HtmlUtil.escape(assetRenderer.getTitle(locale))) %>'
			url="<%= taglibEditURL %>"
		/>
	</div>
</c:if>
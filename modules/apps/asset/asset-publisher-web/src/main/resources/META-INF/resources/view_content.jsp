<%--
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
--%>

<%@ include file="/init.jsp" %>

<liferay-util:dynamic-include key="com.liferay.asset.publisher.web#/view_content.jsp#pre" />

<%
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

if (Validator.isNotNull(returnToFullPageURL)) {
	portletDisplay.setURLBack(returnToFullPageURL);
}

long assetEntryId = ParamUtil.getLong(request, "assetEntryId");
String type = ParamUtil.getString(request, "type");
long groupId = ParamUtil.getLong(request, "groupId", scopeGroupId);
String urlTitle = ParamUtil.getString(request, "urlTitle");

boolean print = false;

String viewMode = ParamUtil.getString(request, "viewMode");

if (Objects.equals(viewMode, Constants.PRINT)) {
	print = true;
}

AssetEntry assetEntry = null;

AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByType(type);
AssetRenderer<?> assetRenderer = null;

if (Validator.isNotNull(urlTitle)) {
	assetRenderer = assetRendererFactory.getAssetRenderer(groupId, urlTitle);

	assetEntry = assetRendererFactory.getAssetEntry(assetRendererFactory.getClassName(), assetRenderer.getClassPK());
}
else {
	assetEntry = assetRendererFactory.getAssetEntry(assetEntryId);

	assetRenderer = assetRendererFactory.getAssetRenderer(assetEntry.getClassPK());
}

if ((assetEntry.isVisible() && !assetPublisherDisplayContext.isEnablePermissions()) || (assetRenderer.hasViewPermission(permissionChecker) && assetRenderer.isDisplayable())) {
	request.setAttribute("view.jsp-assetEntry", assetEntry);
	request.setAttribute("view.jsp-assetRenderer", assetRenderer);
	request.setAttribute("view.jsp-assetRendererFactory", assetRendererFactory);
	request.setAttribute("view.jsp-print", print);
	request.setAttribute("view.jsp-showBackURL", !print);

	PortalUtil.addPortletBreadcrumbEntry(request, assetRenderer.getTitle(locale), currentURL);
%>

	<liferay-util:include page="/view_asset_entry_full_content.jsp" servletContext="<%= application %>" />

<%
	String summary = StringUtil.shorten(assetRenderer.getSummary(liferayPortletRequest, liferayPortletResponse), assetPublisherDisplayContext.getAbstractLength());

	PortalUtil.setPageDescription(summary, request);

	PortalUtil.setPageKeywords(assetHelper.getAssetKeywords(assetEntry.getClassName(), assetEntry.getClassPK()), request);
	PortalUtil.setPageTitle(assetRenderer.getTitle(locale), request);
}
else {
	if (!assetEntry.isVisible()) {
		SessionErrors.add(renderRequest, NoSuchModelException.class.getName());
	}
	else {
		SessionErrors.add(renderRequest, PrincipalException.MustHavePermission.class.getName());
	}
%>

	<liferay-util:include page="/error.jsp" servletContext="<%= application %>" />

<%
}
%>

<aui:script use="aui-base">
	Liferay.once(
		'allPortletsReady',
		function() {
			A.one('#p_p_id_<%= portletDisplay.getId() %>_').scrollIntoView();
		}
	);
</aui:script>

<liferay-util:dynamic-include key="com.liferay.asset.publisher.web#/view_content.jsp#post" />
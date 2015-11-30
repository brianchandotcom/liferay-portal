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

<%
String category = ParamUtil.getString(request, "category");
String state = ParamUtil.getString(request, "state");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

AppDisplay appDisplay = (AppDisplay)row.getObject();

String bundleIds = _getBundleIds(appDisplay);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("category", category);
portletURL.setParameter("state", state);
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<c:choose>
		<c:when test="<%= appDisplay.getState() == BundleStateConstants.ACTIVE %>">
			<portlet:actionURL name="deactivateBundles" var="deactivateBundlesURL">
				<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
				<portlet:param name="bundleIds" value="<%= bundleIds %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				message="deactivate"
				url="<%= deactivateBundlesURL %>"
			/>
		</c:when>
		<c:otherwise>
			<portlet:actionURL name="activateBundles" var="activateBundlesURL">
				<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
				<portlet:param name="bundleIds" value="<%= bundleIds %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				message="activate"
				url="<%= activateBundlesURL %>"
			/>
		</c:otherwise>
	</c:choose>

	<portlet:actionURL name="uninstallBundles" var="uninstallBundlesURL">
		<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
		<portlet:param name="bundleIds" value="<%= bundleIds %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete url="<%= uninstallBundlesURL %>" />
</liferay-ui:icon-menu>

<%!
private String _getBundleIds(AppDisplay appDisplay) {
	List<Bundle> bundles = appDisplay.getBundles();

	long[] bundleIds = new long[bundles.size()];

	for (int i = 0; i < bundles.size(); i++) {
		Bundle bundle = bundles.get(i);

		bundleIds[i] = bundle.getBundleId();
	}

	return StringUtil.merge(bundleIds);
}
%>
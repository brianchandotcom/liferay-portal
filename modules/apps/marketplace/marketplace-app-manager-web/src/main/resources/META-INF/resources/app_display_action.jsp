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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

AppDisplay appDisplay = (AppDisplay)row.getObject();

String bundleIds = _getBundleIds(appDisplay);
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>

	<%
	String storeURL = appDisplay.getStoreURL(request);
	%>

	<c:if test="<%= Validator.isNotNull(storeURL) %>">
		<liferay-ui:icon
			message="go-to-marketplace"
			url="<%= storeURL %>"
		/>
	</c:if>

	<c:choose>
		<c:when test="<%= appDisplay.getState() == BundleStateConstants.ACTIVE %>">
			<c:if test="<%= !appDisplay.isRequired() %>">
				<portlet:actionURL name="deactivateBundles" var="deactivateBundlesURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="bundleIds" value="<%= bundleIds %>" />
				</portlet:actionURL>

				<%
				String taglibDeactivateBundlesURL = "javascript:if (confirm(\'" + UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-deactivate-this") + "\')) {submitForm(document.hrefFm, \'" + HtmlUtil.unescape(deactivateBundlesURL.toString()) + "\');};";
				%>

				<liferay-ui:icon
					message="deactivate"
					url="<%= taglibDeactivateBundlesURL %>"
				/>
			</c:if>
		</c:when>
		<c:otherwise>
			<portlet:actionURL name="activateBundles" var="activateBundlesURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="bundleIds" value="<%= bundleIds %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				message="activate"
				url="<%= activateBundlesURL %>"
			/>
		</c:otherwise>
	</c:choose>

	<c:if test="<%= !appDisplay.isRequired() %>">
		<portlet:actionURL name="uninstallBundles" var="uninstallBundlesURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="bundleIds" value="<%= bundleIds %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			message="uninstall"
			url="<%= uninstallBundlesURL %>"
		/>
	</c:if>
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
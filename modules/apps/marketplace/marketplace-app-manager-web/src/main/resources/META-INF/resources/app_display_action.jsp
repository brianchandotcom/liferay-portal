<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				String taglibDeactivateBundlesURL = "javascript:Liferay.Util.openConfirmModal({message: '" + UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-deactivate-this") + "', onConfirm: function (isConfirmed) {if (isConfirmed) {submitForm(document.hrefFm, '" + HtmlUtil.unescape(deactivateBundlesURL.toString()) + "');}}});";
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
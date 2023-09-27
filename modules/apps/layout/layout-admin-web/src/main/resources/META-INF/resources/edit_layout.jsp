<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

long selPlid = ParamUtil.getLong(request, "selPlid");

Layout selLayout = LayoutLocalServiceUtil.fetchLayout(selPlid);

if (Validator.isNull(backURL)) {
	backURL = PortalUtil.getLayoutFullURL(selLayout, themeDisplay);
}

String portletResource = ParamUtil.getString(request, "portletResource");

LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(selLayout);

String layoutSetBranchName = StringPool.BLANK;

boolean incomplete = false;

if (layoutRevision != null) {
	long layoutSetBranchId = layoutRevision.getLayoutSetBranchId();

	incomplete = StagingUtil.isIncomplete(selLayout, layoutSetBranchId);

	if (incomplete) {
		LayoutSetBranch layoutSetBranch = LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(layoutSetBranchId);

		layoutSetBranchName = layoutSetBranch.getName();

		if (LayoutSetBranchConstants.MASTER_BRANCH_NAME.equals(layoutSetBranchName)) {
			layoutSetBranchName = LanguageUtil.get(request, layoutSetBranchName);
		}

		portletDisplay.setShowStagingIcon(false);
	}
}

if ((layoutRevision != null) && StagingUtil.isIncomplete(selLayout, layoutRevision.getLayoutSetBranchId())) {
	portletDisplay.setShowStagingIcon(false);
}

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
}

renderResponse.setTitle(layoutsAdminDisplayContext.getConfigurationTitle(selLayout, locale));
%>

<c:choose>
	<c:when test="<%= incomplete %>">
		<clay:container-fluid>
			<clay:sheet>
				<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(selLayout.getName(locale)), HtmlUtil.escape(layoutSetBranchName)} %>" key="the-page-x-is-not-enabled-in-x,-but-is-available-in-other-pages-variations" translateArguments="<%= false %>" />

				<aui:button-row>
					<clay:button
						displayType="secondary"
						id='<%= liferayPortletResponse.getNamespace() + "enableLayoutButton" %>'
						label='<%= LanguageUtil.format(request, "enable-in-x", HtmlUtil.escape(layoutSetBranchName), false) %>'
					/>

					<clay:button
						displayType="secondary"
						id='<%= liferayPortletResponse.getNamespace() + "deleteLayoutButton" %>'
						label="delete-in-all-pages-variations"
					/>

					<portlet:actionURL name="/layout_admin/delete_layout" var="deleteLayoutURL">
						<portlet:param name="redirect" value="<%= String.valueOf(layoutsAdminDisplayContext.getLayoutScreenNavigationPortletURL(selPlid)) %>" />
						<portlet:param name="selPlid" value="<%= String.valueOf(selPlid) %>" />
						<portlet:param name="layoutSetBranchId" value="0" />
					</portlet:actionURL>

					<portlet:actionURL name="/layout_admin/enable_layout" var="enableLayoutURL">
						<portlet:param name="redirect" value="<%= String.valueOf(layoutsAdminDisplayContext.getLayoutScreenNavigationPortletURL(selPlid)) %>" />
						<portlet:param name="incompleteLayoutRevisionId" value="<%= String.valueOf(layoutRevision.getLayoutRevisionId()) %>" />
					</portlet:actionURL>

					<liferay-frontend:component
						context='<%=
							HashMapBuilder.<String, Object>put(
								"deleteLayoutURL", deleteLayoutURL
							).put(
								"enableLayoutURL", enableLayoutURL
							).build()
						%>'
						module="js/layout/IncompleteLayoutEventListener"
					/>
				</aui:button-row>
			</clay:sheet>
		</clay:container-fluid>
	</c:when>
	<c:otherwise>
		<liferay-ui:success key='<%= portletResource + "layoutUpdated" %>' message='<%= LanguageUtil.get(resourceBundle, "the-page-was-updated-successfully") %>' />

		<liferay-frontend:screen-navigation
			containerCssClass="col-lg-8"
			containerWrapperCssClass="container-fluid container-fluid-max-xl container-form-lg"
			context="<%= selLayout %>"
			inverted="<%= true %>"
			key="<%= LayoutScreenNavigationEntryConstants.SCREEN_NAVIGATION_KEY_LAYOUT %>"
			menubarCssClass="menubar menubar-transparent menubar-vertical-expand-lg"
			navCssClass="col-lg-3"
			portletURL="<%= layoutsAdminDisplayContext.getLayoutScreenNavigationPortletURL(selPlid) %>"
		/>

		<%@ include file="/friendly_url_warning_message.jspf" %>
	</c:otherwise>
</c:choose>
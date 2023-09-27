<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String portletTitle = (String)request.getAttribute(ProductNavigationControlMenuWebKeys.PORTLET_TITLE);

Group group = layout.getGroup();

Group liveGroup = group;

boolean inStaging = false;

if (group.isControlPanel()) {
	long doAsGroupId = ParamUtil.getLong(request, "doAsGroupId");

	if (doAsGroupId > 0) {
		try {
			liveGroup = GroupLocalServiceUtil.getGroup(doAsGroupId);

			if (liveGroup.isStagingGroup()) {
				liveGroup = liveGroup.getLiveGroup();

				inStaging = true;
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
	}
}
else if (group.isStagingGroup()) {
	liveGroup = group.getLiveGroup();

	inStaging = true;
}
%>

<div class="control-menu-nav-item control-menu-nav-item-content">
	<h1 class="control-menu-level-1-heading mb-0 text-truncate" data-qa-id="headerTitle"><%= HtmlUtil.escape(portletTitle) %></h1>

	<c:if test="<%= liveGroup.isStaged() && !liveGroup.isStagedPortlet(portletDisplay.getRootPortletId()) %>">
		<c:choose>
			<c:when test="<%= !liveGroup.isStagedRemotely() && inStaging %>">
				<span class="align-items-center lfr-portal-tooltip" title="<%= HtmlUtil.stripHtml(LanguageUtil.get(request, "this-portlet-is-not-staged-local-alert")) %>">
					<clay:icon
						aria-label='<%= HtmlUtil.stripHtml(LanguageUtil.get(request, "this-portlet-is-not-staged-local-alert")) %>'
						cssClass="ml-3 mt-0"
						symbol="warning-full"
					/>
				</span>
			</c:when>
			<c:when test="<%= liveGroup.isStagedRemotely() && themeDisplay.isSignedIn() %>">
				<span class="align-items-center lfr-portal-tooltip" title="<%= HtmlUtil.stripHtml(LanguageUtil.get(request, "this-portlet-is-not-staged-remote-alert")) %>">
					<clay:icon
						aria-label='<%= HtmlUtil.stripHtml(LanguageUtil.get(request, "this-portlet-is-not-staged-remote-alert")) %>'
						cssClass="ml-3 mt-0"
						symbol="warning-full"
					/>
				</span>
			</c:when>
		</c:choose>
	</c:if>
</div>

<%!
private static final Log _log = LogFactoryUtil.getLog("com_liferay_product_navigation_control_menu_web.entries.portlet_header_jsp");
%>
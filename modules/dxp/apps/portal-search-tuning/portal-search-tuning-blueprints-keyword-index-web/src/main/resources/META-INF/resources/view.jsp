<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-ui:error key="<%= KeywordIndexWebKeys.ERROR %>">
	<liferay-ui:message arguments="<%= SessionErrors.get(liferayPortletRequest, KeywordIndexWebKeys.ERROR) %>" key="error-x" />
</liferay-ui:error>

<%
final String tabs = ParamUtil.getString(request, "tabs", KeywordEntryStatus.REPORTED.name());

PortletURL renderURL = renderResponse.createRenderURL();
%>

<clay:navigation-bar
	inverted="<%= false %>"
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(tabs.equals(KeywordEntryStatus.REPORTED.name()));
						navigationItem.setHref(renderURL, "tabs", KeywordEntryStatus.REPORTED.name());
						navigationItem.setLabel(LanguageUtil.get(request, "reported-entries"));
					});
				add(
					navigationItem -> {
						navigationItem.setActive(tabs.equals(KeywordEntryStatus.ACTIVE.name()));
						navigationItem.setHref(renderURL, "tabs", KeywordEntryStatus.ACTIVE.name());
						navigationItem.setLabel(LanguageUtil.get(request, "active-entries"));
					});
				add(
					navigationItem -> {
						navigationItem.setActive(tabs.equals(KeywordEntryStatus.BLACKLISTED.name()));
						navigationItem.setHref(renderURL, "tabs", KeywordEntryStatus.BLACKLISTED.name());
						navigationItem.setLabel(LanguageUtil.get(request, "blacklisted-entries"));
					});
			}
		}
	%>'
/>

<c:choose>
	<c:when test="<%= tabs.equals(KeywordEntryStatus.ACTIVE.name()) %>">
		<liferay-util:include page="/view_active_entries.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test="<%= tabs.equals(KeywordEntryStatus.BLACKLISTED.name()) %>">
		<liferay-util:include page="/view_blacklisted_entries.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/view_reported_entries.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>
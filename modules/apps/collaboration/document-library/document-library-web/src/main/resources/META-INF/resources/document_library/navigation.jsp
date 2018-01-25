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

<%@ include file="/document_library/init.jsp" %>

<%
DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(dlRequestHelper);
%>

<c:if test="<%= dlPortletInstanceSettingsHelper.isShowTabs() || dlPortletInstanceSettingsHelper.isShowSearch() %>">
	<aui:nav-bar cssClass='<%= dlPortletInstanceSettingsHelper.isShowSearch() ? "collapse-basic-search" : StringPool.BLANK %>' markupView="lexicon">
		<c:if test="<%= dlPortletInstanceSettingsHelper.isShowTabs() %>">
			<liferay-util:include page="/document_library/navigation_tabs.jsp" servletContext="<%= application %>" />
		</c:if>
	</aui:nav-bar>
</c:if>
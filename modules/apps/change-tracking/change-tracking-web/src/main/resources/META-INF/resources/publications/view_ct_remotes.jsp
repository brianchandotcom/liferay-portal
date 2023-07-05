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

<%@ include file="/publications/init.jsp" %>

<liferay-portlet:renderURL var="backURL" />

<%
ViewCTRemotesDisplayContext viewCTRemotesDisplayContext = (ViewCTRemotesDisplayContext)request.getAttribute(CTWebKeys.VIEW_CT_REMOTES_DISPLAY_CONTEXT);

renderResponse.setTitle(LanguageUtil.get(request, "remote-servers"));

portletDisplay.setURLBack(backURL);
portletDisplay.setShowBackIcon(true);
%>

<frontend-data-set:headless-display
	apiURL="<%= viewCTRemotesDisplayContext.getAPIURL() %>"
	creationMenu="<%= viewCTRemotesDisplayContext.getCreationMenu() %>"
	fdsActionDropdownItems="<%= viewCTRemotesDisplayContext.getFDSActionDropdownItems() %>"
	id="<%= PublicationsFDSNames.PUBLICATIONS_REMOTES %>"
	style="fluid"
/>
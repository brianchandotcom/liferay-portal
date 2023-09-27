<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<liferay-portlet:renderURL var="backURL" />

<%
ViewCTRemotesDisplayContext viewCTRemotesDisplayContext = (ViewCTRemotesDisplayContext)request.getAttribute(CTWebKeys.VIEW_CT_REMOTES_DISPLAY_CONTEXT);

renderResponse.setTitle(LanguageUtil.get(request, "remote-servers"));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
%>

<frontend-data-set:headless-display
	apiURL="<%= viewCTRemotesDisplayContext.getAPIURL() %>"
	creationMenu="<%= viewCTRemotesDisplayContext.getCreationMenu() %>"
	fdsActionDropdownItems="<%= viewCTRemotesDisplayContext.getFDSActionDropdownItems() %>"
	id="<%= PublicationsFDSNames.PUBLICATIONS_REMOTES %>"
	style="fluid"
/>
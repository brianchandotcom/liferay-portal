<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long countryId = ParamUtil.getLong(request, "countryId");

Country country = CountryLocalServiceUtil.fetchCountry(countryId);

String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle((country == null) ? LanguageUtil.get(request, "add-country") : LanguageUtil.format(request, "edit-x", country.getTitle(locale), false));
%>

<liferay-frontend:screen-navigation
	context="<%= CountryServiceUtil.fetchCountry(countryId) %>"
	key="<%= CountryScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COUNTRY %>"
	portletURL='<%=
		PortletURLBuilder.createRenderURL(
			renderResponse
		).setMVCRenderCommandName(
			"/address/edit_country"
		).setParameter(
			"countryId", countryId
		).buildPortletURL()
	%>'
/>
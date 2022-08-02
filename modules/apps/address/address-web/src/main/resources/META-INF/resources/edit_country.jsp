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
long countryId = ParamUtil.getLong(request, "countryId");

Country country = CountryLocalServiceUtil.fetchCountry(countryId);

String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle((country == null) ? LanguageUtil.get(request, "add-country") : LanguageUtil.format(request, "edit-x", country.getName(locale), false));
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
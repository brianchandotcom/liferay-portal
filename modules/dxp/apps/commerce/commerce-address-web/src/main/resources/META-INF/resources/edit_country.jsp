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
CommerceCountriesDisplayContext commerceCountryDisplayContext = (CommerceCountriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceCountry commerceCountry = commerceCountryDisplayContext.getCommerceCountry();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(settingsURL);

renderResponse.setTitle((commerceCountry == null) ? LanguageUtil.get(request, "add-country") : LanguageUtil.format(request, "edit-x", commerceCountry.getName(locale), false));
%>

<liferay-frontend:screen-navigation
	key="<%= CommerceCountryScreenNavigationConstants.CATEGORY_KEY_COMMERCE_COUNTRY_DETAILS %>"
	modelBean="<%= commerceCountry %>"
	portletURL="<%= currentURLObj %>"
/>
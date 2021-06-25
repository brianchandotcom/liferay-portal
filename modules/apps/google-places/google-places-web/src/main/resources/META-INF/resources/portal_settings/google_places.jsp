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

<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

<aui:field-wrapper cssClass="form-group">
	<aui:input helpMessage='<%= LanguageUtil.get(request, "set-the-google-places-api-key-that-is-used-for-this-set-of-pages") %>' label='<%= LanguageUtil.get(request, "google-places-api-key") %>' name='<%= "settings--" + GooglePlacesWebKeys.GOOGLE_PLACES_API_KEY + "--" %>' value="<%= (String)request.getAttribute(GooglePlacesWebKeys.GOOGLE_PLACES_API_KEY) %>" />

	<span class="small text-secondary"><liferay-ui:message key="ensure-that-both-googles-places-api-and-maps-javascript-api-are-enabled-for-that-key" /></span>
</aui:field-wrapper>
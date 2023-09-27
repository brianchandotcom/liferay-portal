<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

<aui:field-wrapper cssClass="form-group">
	<aui:input helpMessage='<%= LanguageUtil.get(request, "set-the-google-places-api-key-that-is-used-for-this-set-of-pages") %>' label='<%= LanguageUtil.get(request, "google-places-api-key") %>' name='<%= "settings--" + GooglePlacesWebKeys.GOOGLE_PLACES_API_KEY + "--" %>' value="<%= (String)request.getAttribute(GooglePlacesWebKeys.GOOGLE_PLACES_API_KEY) %>" />

	<span class="small text-secondary"><liferay-ui:message key="ensure-that-both-googles-places-api-and-maps-javascript-api-are-enabled-for-that-key" /></span>
</aui:field-wrapper>
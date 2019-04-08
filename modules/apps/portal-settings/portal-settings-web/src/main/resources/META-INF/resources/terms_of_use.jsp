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
boolean termsOfUseRequired = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.TERMS_OF_USE_REQUIRED, PropsValues.TERMS_OF_USE_REQUIRED);

TermsOfUseContentProvider termsOfUseContentProvider = (TermsOfUseContentProvider)request.getAttribute(PortalSettingsWebKeys.TERMS_OF_USE_CONTENT_PROVIDER);
%>

<aui:fieldset>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input label="require-terms-of-use" name='<%= "settings--" + PropsKeys.TERMS_OF_USE_REQUIRED + "--" %>' type="checkbox" value="<%= termsOfUseRequired %>" />

	<c:if test="<%= termsOfUseContentProvider != null %>">

		<%
		termsOfUseContentProvider.includeConfig(request, response);
		%>

	</c:if>
</aui:fieldset>
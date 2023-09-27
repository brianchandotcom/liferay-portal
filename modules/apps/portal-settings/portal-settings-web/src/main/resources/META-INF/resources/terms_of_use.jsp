<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
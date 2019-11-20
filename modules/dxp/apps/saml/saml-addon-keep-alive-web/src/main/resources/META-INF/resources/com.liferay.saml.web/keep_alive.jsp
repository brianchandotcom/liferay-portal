<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
String expandoAttribute = SamlKeepAliveConstants.EXPANDO_COLUMN_NAME_KEEP_ALIVE_URL;

String expandoAttributeName = "ExpandoAttributeName--" + expandoAttribute + "--";
String expandoAttributeValue = "ExpandoAttribute--" + expandoAttribute + "--";

String keepAliveURL = GetterUtil.getString(request.getAttribute(SamlWebKeys.SAML_KEEP_ALIVE_URL));
%>

<aui:fieldset label="keep-alive">
	<aui:input name="<%= expandoAttributeName %>" type="hidden" value="<%= expandoAttribute %>" />

	<aui:input label="keep-alive-url" name="<%= expandoAttributeValue %>" value="<%= keepAliveURL %>" />
</aui:fieldset>
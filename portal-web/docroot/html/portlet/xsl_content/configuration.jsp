<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/xsl_content/init.jsp" %>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="true" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-ui:error key="xmlUrl" message="please-enter-a-valid-xml-url" />
	<liferay-ui:error key="xslUrl" message="please-enter-a-valid-xsl-url" />
	<liferay-ui:error key="transformation" message="an-error-occurred-while-processing-your-xml-and-xsl" />

	<%
	String validUrlPrefixes = PropsUtil.get(PropsKeys.XSL_CONTENT_VALID_URL_PREFIXES);
	%>

	<c:if test="<%= Validator.isNotNull(validUrlPrefixes) %>">
		<div class="alert alert-info">
			<liferay-ui:message arguments="<%= validUrlPrefixes %>" key="urls-must-begin-with-one-of-the-following" />
		</div>
	</c:if>

	<aui:fieldset>
		<aui:input cssClass="lfr-input-text-container" name="preferences--xmlUrl--" type="text" value="<%= xmlUrl %>" />

		<aui:input cssClass="lfr-input-text-container" name="preferences--xslUrl--" type="text" value="<%= xslUrl %>" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>
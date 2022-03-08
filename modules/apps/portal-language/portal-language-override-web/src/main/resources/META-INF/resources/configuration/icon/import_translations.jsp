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
String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

String redirect = ParamUtil.getString(request, "redirect", backURL);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.get(request, "import-translations"));
%>

<portlet:actionURL name="importTranslations" var="importTranslationsURL">
	<portlet:param name="redirect" value="<%= redirect %>" />
</portlet:actionURL>

<clay:container-fluid
	cssClass="container-view"
>
	<aui:form action="<%= importTranslationsURL %>" cssClass="sheet sheet-lg" enctype="multipart/form-data" method="post" name="fm">
		<h5><liferay-ui:message key="import-file" /></h5>

		<div class="sheet-text">
			<liferay-ui:message arguments='<%= ".properties" %>' key="support-file-format" />
		</div>

		<aui:input id="file" label="file-upload" name="file" type="file">
			<aui:validator name="required" />

			<aui:validator name="acceptFiles">
				'properties'
			</aui:validator>
		</aui:input>

		<aui:button-row>
			<aui:button name="submit" type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</clay:container-fluid>
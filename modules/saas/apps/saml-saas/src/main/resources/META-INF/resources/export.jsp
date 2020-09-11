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

<portlet:actionURL name="/admin/saas/saml/export" var="exportSamlUrl">
	<portlet:param name="mvcRenderCommandName" value="/admin" />
</portlet:actionURL>

<div class="container-fluid container-fluid-max-xl sheet">
	<liferay-ui:error key="exportError" message="an-error-has-occurred-during-the-export-process" />

	<div class="button-holder">
		<h3 class="text-default">
			<liferay-ui:message key="export-the-saml-configuration-from-this-instance-to-your-production-instance" />
		</h3>

		<div class="alert alert-warning">
			<liferay-ui:message key="the-saml-configuration-of-your-production-instance-will-be-completely-overwritten" />
		</div>

		<aui:form action="<%= exportSamlUrl %>" method="post" name="fm">
			<aui:button type="submit" value="export-saml-configuration" />
		</aui:form>
	</div>
</div>
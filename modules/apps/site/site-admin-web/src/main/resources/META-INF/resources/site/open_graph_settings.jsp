<%@ page import="com.liferay.layout.seo.model.SiteSEOEntry" %><%@
page import="com.liferay.site.admin.web.internal.display.context.SiteAdminDisplayContext" %>

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

<aui:fieldset>

	<h4 class="sheet-tertiary-title">
		<liferay-ui:message key="enable-open-graph" />
	</h4>

	<p class="text-muted">
		<liferay-ui:message key="enable-open-graph-description" />
	</p>

	<h4 class="sheet-tertiary-title">
		<liferay-ui:message key="open-graph-image" />
	</h4>

	<p class="text-muted">
		<liferay-ui:message key="open-graph-image-description" />
	</p>

	<div>
		<label class="control-label"><liferay-ui:message key="image" /></label>
	</div>

	<div class="form-group">
		<label><liferay-ui:message key="preview" /></label>
	</div>

</aui:fieldset>
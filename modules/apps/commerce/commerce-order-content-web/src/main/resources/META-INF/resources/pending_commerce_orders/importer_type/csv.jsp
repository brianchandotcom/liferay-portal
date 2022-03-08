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

<div class="container-fluid container-xl mt-3">
	<div class="alert alert-info center container-fluid">
		<div class="mb-2 row-fluid">
			<clay:icon symbol="info-circle" /> <strong class="lead"><liferay-ui:message key="info" /></strong>: <liferay-ui:message key="download-csv-template-help" />
		</div>

		<div class="row-fluid">
			<aui:button href="<%= commerceOrderContentDisplayContext.getCSVTemplateDownloadURL() %>" name="downloadCSVTemplateButton" primary="<%= true %>" value="download-template" />
		</div>
	</div>

	<portlet:actionURL name="/commerce_open_order_content/import_csv" var="importCSVActionURL" />

	<aui:form action="<%= importCSVActionURL %>" enctype="multipart/form-data" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.IMPORT %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceOrderId" type="hidden" value="<%= String.valueOf(commerceOrderContentDisplayContext.getCommerceOrderId()) %>" />
		<aui:input name="commerceOrderImporterTypeKey" type="hidden" value="<%= CSVCommerceOrderImporterTypeImpl.KEY %>" />

		<liferay-ui:error embed="<%= false %>" exception="<%= CommerceOrderImporterTypeException.class %>">

			<%
			String commerceOrderImporterTypeKey = (String)SessionErrors.get(renderRequest, CommerceOrderImporterTypeException.class);
			%>

			<c:choose>
				<c:when test="<%= Validator.isNull(commerceOrderImporterTypeKey) %>">
					<liferay-ui:message key="the-import-process-failed" />
				</c:when>
				<c:otherwise>
					<liferay-ui:message arguments="<%= commerceOrderImporterTypeKey %>" key="the-x-could-not-be-imported" />
				</c:otherwise>
			</c:choose>
		</liferay-ui:error>

		<aui:input label="select-file" name="csvFileName" type="file" />

		<aui:button-row>
			<aui:button cssClass="btn-lg" name="importButton" primary="<%= true %>" type="submit" value='<%= LanguageUtil.get(request, "import") %>' />

			<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>
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

<portlet:actionURL name="<%= MVCActionCommandNames.IMPORT %>" var="importActionURL" />

<div class="container-md">
	<h1><liferay-ui:message key="bulk-loader-title" /></h1>

	<aui:form action="<%= importActionURL %>" name="importform">
		<aui:fieldset>
			<aui:select label="select-data-type" name="type">
				<aui:option label="federated-content" value="<%= ImportTypeKeys.FEDERATED_CONTENT %>" />
			</aui:select>
		</aui:fieldset>

		<aui:button-row>
			<aui:button cssClass="btn btn-primary" type="submit" value="import" />
		</aui:button-row>
	</aui:form>
</div>
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

<%@ include file="/admin/init.jsp" %>

<%
KaleoProcess kaleoProcess = (KaleoProcess)request.getAttribute(KaleoFormsWebKeys.KALEO_PROCESS);
%>

<h3 class="kaleo-process-header"><liferay-ui:message key="details" /></h3>

<liferay-ui:error exception="<%= RecordSetNameException.class %>" message="please-enter-a-valid-name" />

<p class="kaleo-process-message"><liferay-ui:message key="please-type-a-name-for-your-process-and-a-description-of-what-it-does" /></p>

<aui:fieldset-group markupView="lexicon">
	<aui:fieldset>
		<aui:input cssClass="lfr-input-text" localized="<%= true %>" name="name" type="text" value="<%= KaleoFormsUtil.getKaleoProcessName(kaleoProcess, portletSession) %>">
			<aui:validator name="required" />
		</aui:input>

		<aui:input cssClass="lfr-editor-textarea" localized="<%= true %>" name="description" type="textarea" value="<%= KaleoFormsUtil.getKaleoProcessDescription(kaleoProcess, portletSession) %>" wrapperCssClass="lfr-textarea-container" />
	</aui:fieldset>
</aui:fieldset-group>
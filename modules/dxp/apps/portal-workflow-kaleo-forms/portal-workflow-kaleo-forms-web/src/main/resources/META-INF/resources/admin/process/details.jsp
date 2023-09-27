<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
KaleoProcess kaleoProcess = (KaleoProcess)request.getAttribute(KaleoFormsWebKeys.KALEO_PROCESS);
%>

<h3 class="kaleo-process-header"><liferay-ui:message key="details" /></h3>

<liferay-ui:error exception="<%= RecordSetNameException.class %>" message="please-enter-a-valid-name" />

<p class="kaleo-process-message"><liferay-ui:message key="please-type-a-name-for-your-process-and-a-description-of-what-it-does" /></p>

<div class="sheet">
	<div class="panel-group panel-group-flush">
		<aui:fieldset>
			<aui:input cssClass="lfr-input-text" defaultLanguageId="<%= LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale()) %>" localized="<%= true %>" name="name" required="<%= true %>" type="text" value="<%= KaleoFormsUtil.getKaleoProcessName(kaleoProcess, portletSession) %>" />

			<aui:input cssClass="lfr-editor-textarea" defaultLanguageId="<%= LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale()) %>" localized="<%= true %>" name="description" type="textarea" value="<%= KaleoFormsUtil.getKaleoProcessDescription(kaleoProcess, portletSession) %>" wrapperCssClass="lfr-textarea-container" />
		</aui:fieldset>
	</div>
</div>
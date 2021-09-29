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

<%@ include file="/admin/init.jsp" %>

<%
KaleoProcess kaleoProcess = (KaleoProcess)request.getAttribute(KaleoFormsWebKeys.KALEO_PROCESS);
%>

<h3 class="kaleo-process-header"><liferay-ui:message key="details" /></h3>

<liferay-ui:error exception="<%= RecordSetNameException.class %>" message="please-enter-a-valid-name" />

<p class="kaleo-process-message"><liferay-ui:message key="please-type-a-name-for-your-process-and-a-description-of-what-it-does" /></p>

<aui:fieldset-group markupView="lexicon">
	<aui:fieldset>
		<aui:input cssClass="lfr-input-text" defaultLanguageId="<%= LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale()) %>" localized="<%= true %>" name="name" type="text" value="<%= KaleoFormsUtil.getKaleoProcessName(kaleoProcess, portletSession) %>">
			<aui:validator name="required" />
		</aui:input>

		<aui:input cssClass="lfr-editor-textarea" defaultLanguageId="<%= LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale()) %>" localized="<%= true %>" name="description" type="textarea" value="<%= KaleoFormsUtil.getKaleoProcessDescription(kaleoProcess, portletSession) %>" wrapperCssClass="lfr-textarea-container" />
	</aui:fieldset>
</aui:fieldset-group>
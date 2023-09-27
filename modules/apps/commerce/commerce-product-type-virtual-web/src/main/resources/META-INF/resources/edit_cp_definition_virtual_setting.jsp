<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPDefinitionVirtualSettingDisplayContext cpDefinitionVirtualSettingDisplayContext = (CPDefinitionVirtualSettingDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinitionVirtualSetting cpDefinitionVirtualSetting = cpDefinitionVirtualSettingDisplayContext.getCPDefinitionVirtualSetting();
%>

<portlet:actionURL name="/cp_definitions/edit_cp_definition_virtual_setting" var="editProductDefinitionVirtualSettingActionURL" />

<aui:form action="<%= editProductDefinitionVirtualSettingActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm1">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpDefinitionVirtualSetting == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="className" type="hidden" value="<%= CPDefinition.class.getName() %>" />
	<aui:input name="classPK" type="hidden" value="<%= cpDefinitionVirtualSettingDisplayContext.getCPDefinitionId() %>" />
	<aui:input name="cpDefinitionVirtualSettingId" type="hidden" value="<%= (cpDefinitionVirtualSetting == null) ? StringPool.BLANK : cpDefinitionVirtualSetting.getCPDefinitionVirtualSettingId() %>" />
	<aui:input name="fileEntryId" type="hidden" value="<%= (cpDefinitionVirtualSetting == null) ? StringPool.BLANK : cpDefinitionVirtualSetting.getFileEntryId() %>" />
	<aui:input name="sampleFileEntryId" type="hidden" value="<%= (cpDefinitionVirtualSetting == null) ? StringPool.BLANK : cpDefinitionVirtualSetting.getSampleFileEntryId() %>" />
	<aui:input name="termsOfUseJournalArticleResourcePrimKey" type="hidden" value="<%= (cpDefinitionVirtualSetting == null) ? StringPool.BLANK : cpDefinitionVirtualSetting.getTermsOfUseJournalArticleResourcePrimKey() %>" />

	<div class="lfr-form-content" id="<portlet:namespace />fileEntryContainer">
		<liferay-frontend:form-navigator
			backURL="<%= catalogURL %>"
			formModelBean="<%= cpDefinitionVirtualSetting %>"
			id="<%= CPDefinitionVirtualSettingFormNavigatorConstants.FORM_NAVIGATOR_ID_CP_DEFINITION_VIRTUAL_SETTING %>"
		/>
	</div>
</aui:form>
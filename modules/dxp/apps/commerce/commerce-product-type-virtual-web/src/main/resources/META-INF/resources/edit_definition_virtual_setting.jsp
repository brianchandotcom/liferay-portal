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
CPDefinitionVirtualSettingDisplayContext cpDefinitionVirtualSettingDisplayContext = (CPDefinitionVirtualSettingDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinitionVirtualSetting cpDefinitionVirtualSetting = cpDefinitionVirtualSettingDisplayContext.getCPDefinitionVirtualSetting();

CPDefinition cpDefinition = cpDefinitionVirtualSettingDisplayContext.getCPDefinition();

long cpDefinitionId = cpDefinitionVirtualSettingDisplayContext.getCPDefinitionId();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(catalogURL);

renderResponse.setTitle(cpDefinition.getName(languageId));
%>

<portlet:actionURL name="editProductDefinitionVirtualSetting" var="editProductDefinitionVirtualSettingActionURL" />

<aui:form action="<%= editProductDefinitionVirtualSettingActionURL %>" cssClass="container-fluid-1280" method="post" name="fm1">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpDefinitionVirtualSetting == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionId %>" />
	<aui:input name="cpDefinitionVirtualSettingId" type="hidden" value="<%= (cpDefinitionVirtualSetting == null) ? StringPool.BLANK : cpDefinitionVirtualSetting.getCPDefinitionVirtualSettingId() %>" />
	<aui:input name="fileEntryId" type="hidden" value="<%= (cpDefinitionVirtualSetting == null) ? StringPool.BLANK : cpDefinitionVirtualSetting.getFileEntryId() %>" />
	<aui:input name="sampleFileEntryId" type="hidden" value="<%= (cpDefinitionVirtualSetting == null) ? StringPool.BLANK : cpDefinitionVirtualSetting.getSampleFileEntryId() %>" />
	<aui:input name="termsOfUseJournalArticleResourcePrimKey" type="hidden" value="<%= (cpDefinitionVirtualSetting == null) ? StringPool.BLANK : cpDefinitionVirtualSetting.getTermsOfUseJournalArticleResourcePrimKey() %>" />

	<div class="lfr-form-content" id="<portlet:namespace />fileEntryContainer">
		<liferay-ui:form-navigator
			backURL="<%= catalogURL %>"
			formModelBean="<%= cpDefinitionVirtualSetting %>"
			id="<%= CPDefinitionVirtualSettingFormNavigatorConstants.FORM_NAVIGATOR_ID_CP_DEFINITION_VIRTUAL_SETTING %>"
			markupView="lexicon"
		/>
	</div>
</aui:form>
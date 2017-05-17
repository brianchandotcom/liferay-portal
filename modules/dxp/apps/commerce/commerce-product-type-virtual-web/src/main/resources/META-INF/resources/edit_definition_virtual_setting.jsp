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
CPDefinitionVirtualSetting cpDefinitionVirtualSetting = (CPDefinitionVirtualSetting)request.getAttribute(CPDefinitionVirtualSettingWebKeys.COMMERCE_PRODUCT_DEFINITION_VIRTUAL_SETTING);

String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);
%>

<portlet:actionURL name="editProductDefinitionVirtualSetting" var="editProductDefinitionVirtualSettingActionURL" />

<aui:form action="<%= editProductDefinitionVirtualSettingActionURL %>" method="post" name="fm1">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="cpDefinitionId" type="hidden" />
	<aui:input name="fileEntryId" type="hidden" />
	<aui:input name="sampleFileEntryId" type="hidden" />
	<aui:input name="termsOfUseJournalArticleId" type="hidden" />

	<div class="lfr-form-content" id="<portlet:namespace />fileEntryContainer">
		<liferay-ui:form-navigator
			backURL="<%= backURL %>"
			formModelBean="<%= cpDefinitionVirtualSetting %>"
			id="<%= CPDefinitionVirtualSettingFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRODUCT_DEFINITION_VIRTUAL_SETTING %>"
			markupView="lexicon"
		/>
	</div>
</aui:form>
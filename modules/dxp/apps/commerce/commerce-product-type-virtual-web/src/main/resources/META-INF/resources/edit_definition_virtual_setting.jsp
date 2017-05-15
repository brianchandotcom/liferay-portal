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
CPDefinitionVirtualSetting cpDefinitionVirtualSetting = (CPDefinitionVirtualSetting)request.getAttribute(CPWebKeys.COMMERCE_PRODUCT_DEFINITION_VIRTUAL_SETTING);

long fileEntryId = BeanParamUtil.getLong(cpDefinitionVirtualSetting, request, "fileEntryId");

boolean fileEntry = ParamUtil.getBoolean(request, "fileEntry", false);
%>

<portlet:actionURL name="editProductDefinitionVirtualSetting" var="editProductDefinitionVirtualSettingActionURL" />

<aui:form action="<%= editProductDefinitionVirtualSettingActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="cpDefinitionId" type="hidden" />

	<div class="lfr-form-content">
		<aui:model-context bean="<%= cpDefinitionVirtualSetting %>" model="<%= CPDefinitionVirtualSetting.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<portlet:actionURL name="uploadCPDefinitionVirtualSetting" var="uploadCPDefinitionVirtualSettingURL" />

				<div class="lfr-definition-virtual-setting-image-selector">

					<%
					String itemEventName = liferayPortletResponse.getNamespace() + "selectedItem";
					%>

					<liferay-item-selector:repository-entry-browser draggableImage="vertical" fileEntryId="<%= fileEntryId %>" itemSelectorEventName="<%= itemEventName %>" itemSelectorURL="" maxFileSize="<%= PropsValues.DL_FILE_MAX_SIZE %>" paramName="CPDefinitionVirtualSettingFileEntry" uploadURL="<%= uploadCPDefinitionVirtualSettingURL %>" %>' />
				</div>

				<aui:input dateTogglerCheckboxLabel="use-url" disabled="<%= !fileEntry %>" formName="fm" name="url" />

				<aui:input name="" />

				<aui:input name="" />

				<aui:input name="" />

				<aui:input name="" />

				<aui:input name="" />

				<aui:input name="" />

				<aui:input name="" />

				<aui:input name="" />

				<aui:input name="" />
			</aui:fieldset>
		</aui:fieldset-group>
	</div>
</aui:form>
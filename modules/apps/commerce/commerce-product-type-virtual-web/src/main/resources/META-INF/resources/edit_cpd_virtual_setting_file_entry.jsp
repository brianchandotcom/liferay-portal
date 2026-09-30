<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPDefinitionVirtualSettingDisplayContext cpDefinitionVirtualSettingDisplayContext = (CPDefinitionVirtualSettingDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry = cpDefinitionVirtualSettingDisplayContext.getCPDVirtualSettingFileEntry();

String className = CPDefinition.class.getName();
long classPK = cpDefinitionVirtualSettingDisplayContext.getCPDefinitionId();

long cpInstanceId = cpDefinitionVirtualSettingDisplayContext.getCPInstanceId();

if (cpInstanceId > 0) {
	className = CPInstance.class.getName();
	classPK = cpInstanceId;
}

long fileEntryId = 0;
long cpdVirtualSettingFileEntryId = 0;

if (cpdVirtualSettingFileEntry != null) {
	fileEntryId = cpdVirtualSettingFileEntry.getFileEntryId();
	cpdVirtualSettingFileEntryId = cpdVirtualSettingFileEntry.getCPDefinitionVirtualSettingFileEntryId();
}

FileEntry fileEntry = cpDefinitionVirtualSettingDisplayContext.getFileEntry(fileEntryId);
%>

<portlet:actionURL name="/cp_definitions/edit_cpd_virtual_setting_file_entry" var="editCPDVirtualSettingFileEntryActionURL" />

<liferay-frontend:side-panel-content
	title='<%= LanguageUtil.get(request, "insert-the-url-or-select-a-file-of-your-virtual-product") %>'
>
	<aui:form action="<%= editCPDVirtualSettingFileEntryActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpdVirtualSettingFileEntry == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="className" type="hidden" value="<%= className %>" />
		<aui:input name="classPK" type="hidden" value="<%= classPK %>" />
		<aui:input name="cpdVirtualSettingFileEntryId" type="hidden" value="<%= cpdVirtualSettingFileEntryId %>" />
		<aui:input name="fileEntryId" type="hidden" value="<%= fileEntryId %>" />
		<aui:input name="propagate" type="hidden" value="<%= false %>" />

		<aui:model-context bean="<%= cpdVirtualSettingFileEntry %>" model="<%= CPDVirtualSettingFileEntry.class %>" />

		<liferay-ui:error exception="<%= CommerceVirtualOrderItemException.class %>" message="unable-to-add-the-file-to-the-orders-of-an-unpublished-product" />
		<liferay-ui:error exception="<%= CPDefinitionVirtualSettingException.class %>" message="please-enter-a-valid-url-or-select-an-existing-file" />
		<liferay-ui:error exception="<%= CPDefinitionVirtualSettingFileEntryIdException.class %>" message="please-select-an-existing-file" />
		<liferay-ui:error exception="<%= CPDefinitionVirtualSettingURLException.class %>" message="please-enter-a-valid-url" />

		<commerce-ui:panel
			title='<%= LanguageUtil.get(request, "details") %>'
		>
			<aui:button name="selectFile" value="select" />

			<p class="text-default">
				<span class="<%= (fileEntry != null) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />fileEntryRemove" role="button">
					<clay:button
						aria-label='<%= LanguageUtil.format(locale, "remove-x", "file") %>'
						cssClass="lfr-portal-tooltip"
						displayType="unstyled"
						icon="times"
						title="remove"
					/>
				</span>
				<span id="<portlet:namespace />fileEntryNameInput">
					<c:choose>
						<c:when test="<%= fileEntry != null %>">
							<a href="<%= cpDefinitionVirtualSettingDisplayContext.getDownloadFileEntryURL(fileEntry.getFileEntryId()) %>">
								<%= HtmlUtil.escape(fileEntry.getFileName()) %>
							</a>
						</c:when>
						<c:otherwise>
							<span class="text-muted"><liferay-ui:message key="none" /></span>
						</c:otherwise>
					</c:choose>
				</span>
			</p>

			<aui:input name="url" />
			<aui:input name="version" />
		</commerce-ui:panel>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" value="save" />

			<c:if test="<%= cpDefinitionVirtualSettingDisplayContext.isShowSaveAndPropagateButton() %>">
				<aui:button cssClass="btn-lg" name="saveAndPropagate" value="save-and-propagate" />
			</c:if>

			<aui:button cssClass="btn-lg" type="cancel" />
		</aui:button-row>
	</aui:form>
</liferay-frontend:side-panel-content>

<liferay-frontend:component
	componentId="cpdVirtualSettingFileEntryUtil"
	context='<%=
		HashMapBuilder.<String, Object>put(
			"fileEntryItemSelectorURL", cpDefinitionVirtualSettingDisplayContext.getFileEntryItemSelectorURL()
		).put(
			"portletNamespace", portletDisplay.getNamespace()
		).build()
	%>'
	module="{CPDVirtualSettingFileEntryUtil} from commerce-product-type-virtual-web"
/>
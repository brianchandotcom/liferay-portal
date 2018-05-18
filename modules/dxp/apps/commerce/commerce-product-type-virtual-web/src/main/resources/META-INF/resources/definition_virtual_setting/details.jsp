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

<%
CPDefinitionVirtualSettingDisplayContext cpDefinitionVirtualSettingDisplayContext = (CPDefinitionVirtualSettingDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinitionVirtualSetting cpDefinitionVirtualSetting = cpDefinitionVirtualSettingDisplayContext.getCPDefinitionVirtualSetting();

FileEntry fileEntry = cpDefinitionVirtualSettingDisplayContext.getFileEntry();

long fileEntryId = BeanParamUtil.getLong(cpDefinitionVirtualSetting, request, "fileEntryId");

String textCssClass = "text-default ";

boolean useFileEntry = false;

if (fileEntryId > 0) {
	textCssClass += "hide";

	useFileEntry = true;
}
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="details"
/>

<aui:model-context bean="<%= cpDefinitionVirtualSetting %>" model="<%= CPDefinitionVirtualSetting.class %>" />

<liferay-ui:error exception="<%= CPDefinitionVirtualSettingException.class %>" message="please-enter-a-valid-url-or-select-an-existing-file" />
<liferay-ui:error exception="<%= CPDefinitionVirtualSettingFileEntryIdException.class %>" message="please-select-an-existing-file" />
<liferay-ui:error exception="<%= CPDefinitionVirtualSettingUrlException.class %>" message="please-enter-a-valid-url" />

<div class="col-md-3">
	<h4 class="text-default"><liferay-ui:message key="insert-the-url-or-select-a-file-of-your-virtual-product" /></h4>
</div>

<div class="col-md-9">
	<aui:fieldset>
		<aui:input disabled="<%= useFileEntry %>" name="url" />

		<h4 class="<%= textCssClass %>" id="lfr-definition-virtual-button-row-message"><liferay-ui:message key="or" /></h4>

		<p class="text-default">
			<span class="<%= (fileEntryId > 0) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />fileEntryRemove" role="button">
				<aui:icon cssClass="icon-monospaced" image="times" markupView="lexicon" />
			</span>
			<span id="<portlet:namespace />fileEntryNameInput">
				<c:choose>
					<c:when test="<%= (fileEntry != null) %>">
						<a href="<%= cpDefinitionVirtualSettingDisplayContext.getDownloadFileEntryURL() %>">
							<%= fileEntry.getFileName() %>
						</a>
					</c:when>
					<c:otherwise>
						<span class="text-muted"><liferay-ui:message key="none" /></span>
					</c:otherwise>
				</c:choose>
			</span>
		</p>
	</aui:fieldset>

	<aui:button name="selectFile" value="select" />
</div>

<aui:script use="liferay-item-selector-dialog">
	var fileEntryRemove = $('#<portlet:namespace />fileEntryRemove');
	var fileEntryNameInput = $('#<portlet:namespace />fileEntryNameInput');

	$('#<portlet:namespace />selectFile').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'uploadCPDefinitionVirtualSetting',
					on: {
						selectedItemChange: function(event) {
							var selectedItem = event.newVal;

							if (selectedItem) {
								var value = JSON.parse(selectedItem.value);

								$('#<portlet:namespace />fileEntryId').val(value.fileEntryId);

								$('#<portlet:namespace />url').attr('disabled', true);

								$('#lfr-definition-virtual-button-row-message').addClass('hide');

								fileEntryRemove.removeClass('hide');

								fileEntryNameInput.html('<a>' + value.title + '</a>');
							}
						}
					},
					title: '<liferay-ui:message key="select-file" />',
					url: '<%= cpDefinitionVirtualSettingDisplayContext.getFileEntryItemSelectorURL() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);

	$('#<portlet:namespace />fileEntryRemove').on(
		'click',
		function(event) {
			event.preventDefault();

			$('#<portlet:namespace />fileEntryId').val(0);

			$('#<portlet:namespace />url').attr('disabled', false);

			$('#lfr-definition-virtual-button-row-message').removeClass('hide');

			fileEntryNameInput.html('<liferay-ui:message key="none" />');

			fileEntryRemove.addClass('hide');
		}
	);
</aui:script>
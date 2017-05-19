<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %><%--
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

PortletURL assetBrowserURL = cpDefinitionVirtualSettingDisplayContext.getAssetBrowserURL();

boolean termsOfUseRequired = BeanParamUtil.getBoolean(cpDefinitionVirtualSetting, request, "termsOfUseRequired");
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="terms-of-use" />

<aui:model-context bean="<%= cpDefinitionVirtualSetting %>" model="<%= CPDefinitionVirtualSetting.class %>" />

<div class="lfr-definition-virtual-setting-terms-of-use-header">
	<aui:fieldset>
		<aui:input name="termsOfUseRequired" />
	</aui:fieldset>
</div>

<div class="lfr-definition-virtual-setting-terms-of-use-content toggler-content-collapsed">
	<aui:fieldset>
		<aui:input checked="<%= true %>" cssClass="lfr-definition-virtual-setting-terms-of-use-type" label="use-content" name="useContent" type="radio" />

		<aui:field-wrapper cssClass="lfr-definition-virtual-setting-terms-of-use-value">
			<div class="entry-content form-group">
				<liferay-ui:input-localized
					cssClass="form-control"
					editorName="alloyeditor"
					name="termsOfUseContent"
					type="editor"
					xml='<%= BeanPropertiesUtil.getString(cpDefinitionVirtualSetting, "termsOfUseContent") %>'
				/>
			</div>
		</aui:field-wrapper>
	</aui:fieldset>

	<aui:fieldset>
		<aui:input cssClass="lfr-definition-virtual-setting-terms-of-use-type" label="use-article" name="useArticle" type="radio" />

		<aui:button cssClass="lfr-definition-virtual-setting-terms-of-use-value hidden" name="selectArticle" value="select-article" />
	</aui:fieldset>
</div>

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />selectArticle').on(
		'click',
		function(event) {
			event.preventDefault();

			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						modal: true
					},
					eventName: 'selectJournalArticle',
					id: '',
					title: '<liferay-ui:message key="select-article" />'
					uri: '<%= cpDefinitionVirtualSettingDisplayContext.getAssetBrowserURL() %>'
				},
				function(event) {
					//selectAsset(event.entityid, event.assetclassname, event.assettype, event.assettitle, event.groupdescriptivename);
					console.log(event);
				}
			);
		}
	);
</aui:script>

<aui:script use="aui-toggler">
	var container = A.one('#<portlet:namespace />fileEntryContainer');

	var termsOfUseTypes = container.all('.lfr-definition-virtual-setting-terms-of-use-type');
	var termsOfUseValues = container.all('.lfr-definition-virtual-setting-terms-of-use-value');

	var selectTermsOfUseType = function(index) {
		termsOfUseTypes.attr('checked', false);

		termsOfUseTypes.item(index).attr('checked', true);

		termsOfUseValues.addClass('hidden');

		termsOfUseValues.item(index).removeClass('hidden');
	};

	container.delegate(
		'change',
		function(event) {
			var index = termsOfUseTypes.indexOf(event.currentTarget);

			selectTermsOfUseType(index);
		},
		'.lfr-definition-virtual-setting-terms-of-use-type'
	);

	new A.Toggler(
		{
			animated: true,
			content: '#<portlet:namespace />fileEntryContainer .lfr-definition-virtual-setting-terms-of-use-content',
			expanded: <%= termsOfUseRequired %>,
			header: '#<portlet:namespace />fileEntryContainer .lfr-definition-virtual-setting-terms-of-use-header',
			on: {
				animatingChange: function(event) {
					var instance = this;

					var expanded = !instance.get('expanded');

					A.one('#<portlet:namespace />termsOfUseRequired').attr('checked', expanded);

					if (expanded) {
						termsOfUseTypes.each(
							function(item, index) {
								if (item.get('checked')) {
									termsOfUseValues.item(index).removeClass('hidden');
								}
							}
						);
					}
					else {
						termsOfUseValues.addClass('hidden');
					}
				}
			}
		}
	);
</aui:script>
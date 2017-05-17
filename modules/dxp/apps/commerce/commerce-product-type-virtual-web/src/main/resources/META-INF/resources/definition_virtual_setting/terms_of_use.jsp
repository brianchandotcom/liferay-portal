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

boolean termsOfUseRequired = BeanParamUtil.getBoolean(cpDefinitionVirtualSetting, request, "termsOfUseRequired");
%>

<aui:model-context bean="<%= cpDefinitionVirtualSetting %>" model="<%= CPDefinitionVirtualSetting.class %>" />

<div class="lfr-definition-virtual-setting-terms-of-use-header">
	<aui:fieldset>
		<aui:input name="termsOfUseRequired" />
	</aui:fieldset>
</div>

<div class="lfr-definition-virtual-setting-terms-of-use-content toggler-content-collapsed">
	<aui:fieldset>
		<aui:input checked="<%= true %>" cssClass="lfr-definition-virtual-setting-terms-of-use-type" label="use-content" name="useContent" type="radio" />

		<aui:input cssClass="lfr-definition-virtual-setting-terms-of-use-value" name="termsOfUseContent" />
	</aui:fieldset>

	<aui:fieldset>
		<aui:input cssClass="lfr-definition-virtual-setting-terms-of-use-type" label="use-article" name="useArticle" type="radio" />

		<aui:button cssClass="lfr-definition-virtual-setting-terms-of-use-value" disabled="<%= true %>" name="selectArticle" value="select-article" />
	</aui:fieldset>
</div>

<aui:script use="aui-toggler">
	var container = A.one('#<portlet:namespace />fileEntryContainer');

	var termsTypes = container.all('.lfr-definition-virtual-setting-terms-of-use-type');
	var termsValues = container.all('.lfr-definition-virtual-setting-terms-of-use-value');

	var selectTermsType = function(index) {
		termsTypes.attr('checked', false);

		termsTypes.item(index).attr('checked', true);

		termsValues.attr('disabled', true);

		termsValues.item(index).attr('disabled', false);
	};

	container.delegate(
		'change',
		function(event) {
			var index = termsTypes.indexOf(event.currentTarget);

			selectTermsType(index);
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
						termsTypes.each(
							function(item, index) {
								if (item.get('checked')) {
									termsValues.item(index).attr('disabled', false);
								}
							}
						);
					}
					else {
						termsValues.attr('disabled', true);
					}
				}
			}
		}
	);
</aui:script>
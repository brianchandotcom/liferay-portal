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
JournalEditDDMTemplateDisplayContext journalEditDDMTemplateDisplayContext = new JournalEditDDMTemplateDisplayContext(request);

DDMTemplate ddmTemplate = journalEditDDMTemplateDisplayContext.getDDMTemplate();
%>

<aui:model-context bean="<%= ddmTemplate %>" model="<%= DDMTemplate.class %>" />

<div id="<portlet:namespace />smallImageContainer">
	<div class="lfr-ddm-small-image-header">
		<aui:input name="smallImage" />
	</div>

	<div class="lfr-ddm-small-image-content p-3 toggler-content-collapsed">
		<aui:row>
			<c:if test="<%= journalEditDDMTemplateDisplayContext.isSmallImage() && (ddmTemplate != null) %>">
				<aui:col width="<%= 50 %>">
					<div class="aspect-ratio aspect-ratio-16-to-9">
						<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="preview" />" class="aspect-ratio-item-fluid" src="<%= HtmlUtil.escapeAttribute(ddmTemplate.getTemplateImageURL(themeDisplay)) %>" />
					</div>
				</aui:col>
			</c:if>

			<aui:col width="<%= (journalEditDDMTemplateDisplayContext.isSmallImage() && (ddmTemplate != null)) ? 50 : 100 %>">
				<aui:fieldset>
					<aui:input cssClass="lfr-ddm-small-image-type" label="small-image-url" name="type" type="radio" />

					<aui:input cssClass="lfr-ddm-small-image-value" label="" name="smallImageURL" title="small-image-url" />
				</aui:fieldset>

				<aui:fieldset>
					<aui:input cssClass="lfr-ddm-small-image-type" label="small-image" name="type" type="radio" />

					<aui:input cssClass="lfr-ddm-small-image-value" label="" name="smallImageFile" type="file" />
				</aui:fieldset>
			</aui:col>
		</aui:row>
	</div>
</div>

<aui:script use="aui-toggler">
	var container = A.one('#<portlet:namespace />smallImageContainer');

	var types = container.all('.lfr-ddm-small-image-type');
	var values = container.all('.lfr-ddm-small-image-value');

	var selectSmallImageType = function(index) {
		types.attr('checked', false);

		types.item(index).attr('checked', true);

		values.attr('disabled', true);

		values.item(index).attr('disabled', false);
	};

	container.delegate(
		'change',
		function(event) {
			var index = types.indexOf(event.currentTarget);

			selectSmallImageType(index);
		},
		'.lfr-ddm-small-image-type'
	);

	new A.Toggler(
		{
			animated: true,
			content: '#<portlet:namespace />smallImageContainer .lfr-ddm-small-image-content',
			expanded: <%= journalEditDDMTemplateDisplayContext.isSmallImage() %>,
			header: '#<portlet:namespace />smallImageContainer .lfr-ddm-small-image-header',
			on: {
				animatingChange: function(event) {
					var instance = this;

					var expanded = !instance.get('expanded');

					A.one('#<portlet:namespace />smallImage').attr('checked', expanded);

					if (expanded) {
						types.each(
							function(item, index) {
								if (item.get('checked')) {
									values.item(index).attr('disabled', false);
								}
							}
						);
					}
					else {
						values.attr('disabled', true);
					}
				}
			}
		}
	);

	selectSmallImageType('<%= ((ddmTemplate != null) && Validator.isNotNull(ddmTemplate.getSmallImageURL())) ? 0 : 1 %>');
</aui:script>
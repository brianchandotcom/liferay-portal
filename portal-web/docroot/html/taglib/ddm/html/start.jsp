<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/ddm/html/init.jsp" %>

<div class="lfr-ddm-container" id="<%= containerId %>">

	<c:if test="<%= Validator.isNotNull(xsd) %>">
		<%= DDMXSDUtil.getHTML(pageContext, xsd, fields, fieldsNamespace, readOnly, locale) %>

		<c:if test="<%= repeatable %>">

			<liferay-portlet:resourceURL portletName="<%= PortletKeys.DYNAMIC_DATA_MAPPING %>" var="renderFieldURL">
				<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_structure" />
				<portlet:param name="<%= Constants.CMD %>" value="fieldHTML" />
				<portlet:param name="className" value="<%= className %>" />
				<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
				<portlet:param name="readOnly" value="<%= String.valueOf(readOnly) %>" />
				<portlet:param name="fieldName" value="{fieldName}" />
				<portlet:param name="repeatableIndex" value="{repeatableIndex}" />
			</liferay-portlet:resourceURL>

			<aui:script use="liferay-portlet-url">
			var TPL_ADD_REPEATABLE = '<a class="lfr-ddm-repeatable-add-button" href="javascript:;"></a>';
			var TPL_DELETE_REPEATABLE = '<a class="lfr-ddm-repeatable-delete-button" href="javascript:;"></a>';

			var container = A.one('#<%= containerId %>');

			var createRepeatableField = function(fieldWrapper, deletable) {
				if (deletable) {
					fieldWrapper.append(TPL_DELETE_REPEATABLE);
				}

				fieldWrapper.append(TPL_ADD_REPEATABLE);
				fieldWrapper.plug(A.Plugin.ParseContent);
			}

			container.all('.aui-field-wrapper[data-repeatable="true"]').each(
				function(item, index, collection) {
					createRepeatableField(item, false);
				}
			);

			container.delegate(
				'click',
				function(event) {
					var currentTarget = event.currentTarget;
					var fieldWrapper = currentTarget.ancestor('.aui-field-wrapper');

					if (currentTarget.test('.lfr-ddm-repeatable-add-button')) {
						var fieldName = fieldWrapper.attr('data-fieldName');
						var siblings = container.all('.aui-field-wrapper[data-fieldName="' + fieldName + '"]');

						var renderFieldURL = A.Lang.sub(
							decodeURIComponent('<%= renderFieldURL %>'),
							{
								fieldName: fieldName,
								repeatableIndex: siblings.size()
							}
						);

						A.io.request(
							renderFieldURL,
							{
								on: {
									success: function(event, id, xhr) {
										siblings.pop().insert(xhr.responseText, 'after');

										createRepeatableField(siblings.refresh().pop(), true);
									}
								}
							}
						);
					}
					else if (currentTarget.test('.lfr-ddm-repeatable-delete-button')) {
						fieldWrapper.remove();
					}
				},
				'.lfr-ddm-repeatable-add-button, .lfr-ddm-repeatable-delete-button'
			);

			var hoverHandler = function(event) {
				var fieldWrapper = event.currentTarget.ancestor('.aui-field-wrapper');

				if (fieldWrapper) {
					fieldWrapper.toggleClass('lfr-ddm-repeatable-active', (event.phase === 'over'));
				}
			}

			container.delegate('hover', hoverHandler, hoverHandler, '.lfr-ddm-repeatable-add-button, .lfr-ddm-repeatable-delete-button');
			</aui:script>

		</c:if>
	</c:if>
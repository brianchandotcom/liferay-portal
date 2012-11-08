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

			<aui:input name="__repeatabaleFieldsMap" type="hidden" />

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
			var fieldsMapInput = A.one('#<portlet:namespace />__repeatabaleFieldsMap');

			var RepeatableUtil = {

				fieldsCountMap: {},

				fieldsMap: {},

				addField: function(fieldName, repeatableIndex) {
					var instance = this;

					var fieldsList = instance.getFieldsList(fieldName);
					var fieldWrapper = fieldsList.item(repeatableIndex);

					instance.getField(
						fieldName,
						++instance.fieldsCountMap[fieldName],
						function(fieldHTML) {
							fieldWrapper.insert(fieldHTML, 'after');

							fieldsList.refresh();

							instance.makeFieldRepeatable(fieldsList.item(repeatableIndex + 1));

							instance.syncFieldsMap(fieldName);
						}
					);
				},

				deleteField: function(fieldName, repeatableIndex) {
					var instance = this;

					var fieldsList = RepeatableUtil.getFieldsList(fieldName);

					fieldsList.item(repeatableIndex).remove();

					instance.syncFieldsMap(fieldName);
				},

				getFieldsList: function(fieldName) {
					var instance = this;

					var query = '.aui-field-wrapper';

					if (fieldName) {
						query += '[data-fieldName="' + fieldName + '"]';
					}

					query += '[data-repeatable="true"]';

					return container.all(query);
				},

				getField: function(fieldName, repeatableIndex, callback) {
					var instance = this;

					var renderFieldURL = A.Lang.sub(
						decodeURIComponent('<%= renderFieldURL %>'),
						{
							fieldName: fieldName,
							repeatableIndex: repeatableIndex
						}
					);

					A.io.request(
						renderFieldURL,
						{
							on: {
								success: function(event, id, xhr) {
									if (callback) {
										callback.call(instance, xhr.responseText);
									}
								}
							}
						}
					);
				},

				makeFieldRepeatable: function(fieldWrapper) {
					var instance = this;

					var fieldName = fieldWrapper.attr('data-fieldName');
					var fieldsList = RepeatableUtil.getFieldsList(fieldName);

					fieldWrapper.append(TPL_ADD_REPEATABLE);

					if (fieldsList.indexOf(fieldWrapper) > 0) {
						fieldWrapper.append(TPL_DELETE_REPEATABLE);
					}

					fieldWrapper.plug(A.Plugin.ParseContent);
				},

				syncFieldsMap: function(fieldName) {
					var instance = this;

					var fieldsList = RepeatableUtil.getFieldsList(fieldName);

					instance.fieldsMap[fieldName] = [];

					fieldsList.each(
						function(item, index, collection) {
							var repeatableIndex = item.attr('data-repeatableIndex');

							if (repeatableIndex) {
								instance.fieldsMap[fieldName].push(repeatableIndex);
							}
						}
					);

					fieldsMapInput.val(JSON.stringify(instance.fieldsMap));
				}

			};

			RepeatableUtil.getFieldsList().each(
				function(item, index, collection) {
					var fieldName = item.attr('data-fieldName');

					if (!RepeatableUtil.fieldsCountMap[fieldName]) {
						RepeatableUtil.fieldsCountMap[fieldName] = 0;
					}
					else {
						RepeatableUtil.fieldsCountMap[fieldName]++;
					}

					RepeatableUtil.makeFieldRepeatable(item);

					RepeatableUtil.syncFieldsMap(fieldName);
				}
			);

			container.delegate(
				'click',
				function(event) {
					var currentTarget = event.currentTarget;
					var fieldWrapper = currentTarget.ancestor('.aui-field-wrapper');
					var fieldName = fieldWrapper.attr('data-fieldName');
					var repeatableIndex = RepeatableUtil.getFieldsList(fieldName).indexOf(fieldWrapper);

					if (currentTarget.test('.lfr-ddm-repeatable-add-button')) {
						RepeatableUtil.addField(fieldName, repeatableIndex);
					}
					else if (currentTarget.test('.lfr-ddm-repeatable-delete-button')) {
						RepeatableUtil.deleteField(fieldName, repeatableIndex);
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
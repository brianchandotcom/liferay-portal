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
String redirect = ParamUtil.getString(request, "redirect");

long sapEntryId = ParamUtil.getLong(request, "sapEntryId");

SAPEntry sapEntry = null;

if (sapEntryId > 0) {
	sapEntry = SAPEntryServiceUtil.getSAPEntry(sapEntryId);
}

String[] allowedServiceSignaturesArray = {};

if (sapEntry != null) {
	allowedServiceSignaturesArray = StringUtil.splitLines(sapEntry.getAllowedServiceSignatures());
}

if (allowedServiceSignaturesArray.length == 0) {
	allowedServiceSignaturesArray = new String[] {StringPool.BLANK};
}

boolean systemSAPEntry = false;

if (sapEntry != null) {
	systemSAPEntry = sapEntry.isSystem();
}
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	title='<%= (sapEntry != null) ? sapEntry.getTitle(locale) : "new-service-access-policy" %>'
/>

<portlet:actionURL name="updateSAPEntry" var="updateSAPEntryURL">
	<portlet:param name="mvcPath" value="/edit_entry.jsp" />
</portlet:actionURL>

<aui:form action="<%= updateSAPEntryURL %>">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="sapEntryId" type="hidden" value="<%= sapEntryId %>" />

	<liferay-ui:error exception="<%= DuplicateSAPEntryNameException.class %>" message="please-enter-a-unique-service-access-policy-name" />
	<liferay-ui:error exception="<%= SAPEntryNameException.class %>" message="service-access-policy-name-is-required" />
	<liferay-ui:error exception="<%= SAPEntryTitleException.class %>" message="service-access-policy-title-is-required" />

	<aui:model-context bean="<%= sapEntry %>" model="<%= SAPEntry.class %>" />

	<aui:input disabled="<%= systemSAPEntry %>" name="name" required="<%= true %>">
		<aui:validator errorMessage="this-field-is-required-and-must-contain-only-following-characters" name="custom">
			function(val, fieldNode, ruleValue) {
				var allowedCharacters = '<%= HtmlUtil.escapeJS(SAPEntryConstants.NAME_ALLOWED_CHARACTERS) %>';

				val = val.trim();

				var regex = new RegExp('[^' + allowedCharacters + ']');

				return !regex.test(val);
			}
		</aui:validator>
	</aui:input>

	<aui:input name="enabled" />

	<aui:input disabled="<%= systemSAPEntry %>" helpMessage="default-sap-entry-help" label="default" name="defaultSAPEntry" />

	<aui:input name="title" required="<%= true %>" />

	<aui:input cssClass="hide" helpMessage="allowed-service-signatures-help" name="allowedServiceSignatures" />

	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="getMethodNamesURL">
		<portlet:param name="<%= ActionRequest.ACTION_NAME %>" value="getMethodNames" />
	</liferay-portlet:resourceURL>

	<div id="<portlet:namespace />allowedServiceSignaturesFriendlyContentBox">

		<%
		for (int i = 0; i < allowedServiceSignaturesArray.length; i++) {
			String serviceClassName = StringPool.BLANK;
			String methodName = StringPool.BLANK;

			String[] allowedServiceSignatureArray = StringUtil.split(allowedServiceSignaturesArray[i], CharPool.POUND);

			if (allowedServiceSignatureArray.length > 0) {
				serviceClassName = GetterUtil.getString(allowedServiceSignatureArray[0], StringPool.BLANK);

				if (allowedServiceSignatureArray.length > 1) {
					methodName = GetterUtil.getString(allowedServiceSignatureArray[1], StringPool.BLANK);
				}
			}
		%>

			<div class="lfr-form-row">
				<div class="row-fields">
					<aui:col md="6">
						<aui:input cssClass="service-class-name" data-service-class-name="<%= serviceClassName %>" id='<%= "serviceClassName" + i %>' label="service-class" name="serviceClassName" type="text" value="<%= serviceClassName %>" />
					</aui:col>
					<aui:col md="6">
						<aui:input cssClass="method-name" id='<%= "methodName" + i %>' label="method-name" name="methodName" type="text" value="<%= methodName %>" />
					</aui:col>
				</div>
			</div>

		<%
		}
		%>

	</div>

	<aui:script>
		function <portlet:namespace />toggleAdvancedMode(argument) {
			AUI.$('#<portlet:namespace />advancedMode, #<portlet:namespace />friendlyMode, #<portlet:namespace />allowedServiceSignatures, #<portlet:namespace />allowedServiceSignaturesFriendlyContentBox').toggleClass('hide');
		}
	</aui:script>

	<aui:script use="autocomplete,autocomplete-filters,io-base,liferay-auto-fields,liferay-portlet-url">
		var REGEX_DOT = /\./g;

		var getMethodNamesURL = Liferay.PortletURL.createURL('<%= getMethodNamesURL %>');

		var serviceClassNames = <%= JSONFactoryUtil.looseSerialize(request.getAttribute(SAPWebKeys.REMOTE_SERVICES_CLASS_NAMES)) %>;

		var serviceMethods = {};

		var getServiceContext = function(serviceClassName) {
			var service = A.Array.find(
				serviceClassNames,
				function(item, index) {
					return item.serviceClassName === serviceClassName;
				}
			);

			return service && service.contextName || 'portal';
		};

		var getServiceMethods = function(contextName, serviceClassName, callback) {
			if (contextName && serviceClassName && callback) {
				var namespace = contextName.replace(REGEX_DOT, '_') + '.' + serviceClassName.replace(REGEX_DOT, '_');

				var methodObj = A.namespace.call(serviceMethods, namespace);

				var methods = methodObj.methods;

				if (!methods) {
					if (contextName == 'portal') {
						contextName = '';
					}

					getMethodNamesURL.setParameter('contextName', contextName);
					getMethodNamesURL.setParameter('serviceClassName', serviceClassName);

					A.io.request(
						getMethodNamesURL.toString(),
						{
							dataType: 'JSON',
							method: 'GET',
							on: {
								success: function(event, id, xhr) {
									methods = this.get('responseData');

									methodObj.methods = methods;

									callback(methods);
								}
							}
						}
					);
				}
				else {
					callback(methods);
				}
			}
		};

		var initAutoCompleteRow = function(rowNode) {
			var methodInput = rowNode.one('.method-name');
			var serviceInput = rowNode.one('.service-class-name');

			new A.AutoComplete(
				{
					inputNode: serviceInput,
					on: {
						select: function(event) {
							var result = event.result.raw;

							serviceInput.attr('data-service-class-name', result.serviceClassName);
							serviceInput.attr('data-context-name', result.contextName);

							methodInput.attr('disabled', false);
						}
					},
					resultFilters: 'phraseMatch',
					resultTextLocator: 'serviceClassName',
					source: serviceClassNames
				}
			).render();

			new A.AutoComplete(
				{
					inputNode: methodInput,
					resultFilters: 'phraseMatch',
					resultTextLocator: 'methodName',
					source: function(query, callback) {
						var contextName = serviceInput.attr('data-context-name');
						var serviceClassName = serviceInput.attr('data-service-class-name');

						if (!contextName) {
							contextName = getServiceContext(serviceClassName);

							serviceInput.attr('data-context-name', contextName);
						}

						getServiceMethods(contextName, serviceClassName, callback);
					}
				}
			).render();
		};

		var updateAdvancedModeTextarea = function() {
			var updatedInput = '';

			A.all('#<portlet:namespace />allowedServiceSignaturesFriendlyContentBox .lfr-form-row:not(.hide)').each(
				function(item, index) {
					var methodName = item.one('.method-name').val();
					var serviceClassName = item.one('.service-class-name').val();

					updatedInput += serviceClassName;

					if (methodName) {
						updatedInput += '#' + methodName;
					}

					updatedInput += '\n';
				}
			);

			A.one('#<portlet:namespace />allowedServiceSignatures').val(updatedInput);
		};

		var updateFriendlyModeInputs = function() {
			var contentBox = A.one('#<portlet:namespace />allowedServiceSignaturesFriendlyContentBox');

			contentBox.all('.lfr-form-row:not(.hide)').remove();

			var advancedInput = A.one('#<portlet:namespace />allowedServiceSignatures').val();

			var entries = advancedInput.split('\n');

			entries = A.Array.dedupe(entries);

			entries.forEach(
				function(item, index) {
					var row = rowTemplate.clone();

					if (item) {
						var methodInput = row.one('.method-name');
						var serviceInput = row.one('.service-class-name');

						item = item.split('#');

						var serviceClassName = item[0];

						serviceInput.val(serviceClassName);

						serviceInput.attr('data-service-class-name', serviceClassName);

						var method = item[1];

						if (method) {
							methodInput.val(method);
						}

						initAutoCompleteRow(row);

						contentBox.append(row);
					}
				}
			);
		};

		new Liferay.AutoFields(
			{
				contentBox: '#<portlet:namespace />allowedServiceSignaturesFriendlyContentBox',
				namespace: '<portlet:namespace />',
				on: {
					clone: function(event) {
						var rowNode = event.row;

						var methodInput = rowNode.one('.method-name');
						var serviceInput = rowNode.one('.service-class-name');

						methodInput.attr('disabled', true);

						serviceInput.attr(
							{
								'data-context-name': '',
								'data-service-class-name': ''
							}
						);

						initAutoCompleteRow(rowNode);
					},
					delete: updateAdvancedModeTextarea
				}
			}
		).render();

		var rows = A.all('#<portlet:namespace />allowedServiceSignaturesFriendlyContentBox .lfr-form-row');

		var rowTemplate = rows.first().clone();

		rowTemplate.all('input').val('');

		A.each(rows, initAutoCompleteRow);

		A.one('#<portlet:namespace />allowedServiceSignaturesFriendlyContentBox').delegate('blur', updateAdvancedModeTextarea, '.service-class-name, .method-name');
		A.one('#<portlet:namespace />allowedServiceSignatures').on('blur', updateFriendlyModeInputs);
	</aui:script>

	<aui:button-row>
		<aui:button type="submit" value="save" />

		<aui:button id="advancedMode" onClick='<%= renderResponse.getNamespace() + "toggleAdvancedMode();" %>' value="switch-to-advanced-mode" />

		<aui:button cssClass="hide" id="friendlyMode" onClick='<%= renderResponse.getNamespace() + "toggleAdvancedMode();" %>' value="switch-to-friendly-mode" />
	</aui:button-row>
</aui:form>
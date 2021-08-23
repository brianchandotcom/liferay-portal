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
String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

EditObjectDefinitionDisplayContext editObjectDefinitionDisplayContext = (EditObjectDefinitionDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ObjectDefinition objectDefinition = editObjectDefinitionDisplayContext.getObjectDefinition();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.format(request, "edit-x", objectDefinition.getShortName(), false));
%>

<portlet:actionURL name="/object_definitions/edit_object_definition" var="editObjectDefinitionURL" />

<liferay-frontend:edit-form
	action="<%= editObjectDefinitionURL %>"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<liferay-frontend:edit-form-body>
		<aui:model-context bean="<%= objectDefinition %>" model="<%= ObjectDefinition.class %>" />

		<liferay-ui:error exception="<%= DuplicateObjectDefinitionException.class %>" />
		<liferay-ui:error exception="<%= ObjectDefinitionLabelException.class %>" />
		<liferay-ui:error exception="<%= ObjectDefinitionNameException.class %>" />
		<liferay-ui:error exception="<%= ObjectDefinitionPluralLabelException.class %>" />
		<liferay-ui:error exception="<%= ObjectDefinitionScopeException.class %>" />
		<liferay-ui:error exception="<%= ObjectDefinitionStatusException.class %>" />

		<h2 class="sheet-title">
			<%= LanguageUtil.get(request, "information") %>
		</h2>

		<liferay-frontend:fieldset-group>
			<clay:sheet-section>
				<h3 class="sheet-subtitle">
					<%= LanguageUtil.get(request, "object-definition-data") %>
				</h3>

				<clay:row>
					<clay:col
						md="11"
					>
						<aui:input cssClass="disabled" label="object-definition-id" name="objectDefinitionId" readonly="true" type="text" />
						<aui:input disabled="<%= objectDefinition.getStatus() == WorkflowConstants.STATUS_APPROVED %>" label="name" name="shortName" required="<%= true %>" type="text" value="<%= objectDefinition.getShortName() %>" />
						<aui:input name="label" />
						<aui:input name="pluralLabel" />
						<aui:input cssClass="disabled" label="object-definition-table-name" name="DBTableName" readonly="true" type="text" />
					</clay:col>
				</clay:row>

				<aui:field-wrapper cssClass="form-group lfr-input-text-container">
					<aui:input label="" labelOff="inactive" labelOn="active" name="active" type="toggle-switch" value="<%= objectDefinition.isSystem() %>" />
				</aui:field-wrapper>
			</clay:sheet-section>

			<clay:sheet-section>
				<h3 class="sheet-subtitle">
					<%= LanguageUtil.get(request, "scope") %>
				</h3>

				<clay:row>
					<clay:col
						md="11"
					>
						<aui:select disabled="<%= objectDefinition.getStatus() == WorkflowConstants.STATUS_APPROVED %>" name="scope" onChange='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "selectScope();" %>' showEmptyOption="<%= false %>">

							<%
							for (ObjectScopeProvider objectScopeProvider : editObjectDefinitionDisplayContext.getObjectScopeProviders()) {
								String scope = editObjectDefinitionDisplayContext.getScope();
							%>

								<aui:option label="<%= objectScopeProvider.getLabel(locale) %>" selected="<%= scope.equals(objectScopeProvider.getKey()) %>" value="<%= objectScopeProvider.getKey() %>" />
							<%} %>
						</aui:select>
					</clay:col>
				</clay:row>

				<clay:row>
					<clay:col
						md="11"
					>
						<aui:select name="panelCategoryKey" showEmptyOption="<%= true %>">
							<% for(KeyValuePair keyValuePair : editObjectDefinitionDisplayContext.getPanelCategories()) {
									String panelCategoryKey = objectDefinition.getPanelCategoryKey();
							%>

								<aui:option label="<%= keyValuePair.getValue() %>" selected="<%= panelCategoryKey.equals(keyValuePair.getKey()) %>" value="<%= keyValuePair.getKey() %>" />
							<%} %>
						</aui:select>
					</clay:col>
				</clay:row>
			</clay:sheet-section>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button name="save" onClick='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "submitObjectDefinition(true);" %>' primary="<%= false %>" value='<%= LanguageUtil.get(request, "save") %>' />

		<aui:button disabled="<%= objectDefinition.getStatus() == WorkflowConstants.STATUS_APPROVED %>" name="publish" onClick='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "submitObjectDefinition(false);" %>' type="submit" value='<%= LanguageUtil.get(request, "publish") %>' />

		<aui:button href="<%= backURL %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<script>
	function <portlet:namespace />submitObjectDefinition(draft) {
		var form = document.getElementById('<portlet:namespace />fm');

		var cmd = form.querySelector('#<portlet:namespace /><%= Constants.CMD %>');

		if (!draft) {
			cmd.setAttribute('value', '<%= Constants.PUBLISH %>');
		}

		submitForm(form);
	}

	function <portlet:namespace />selectScope() {
		const scope = document.getElementById('<portlet:namespace />scope');

		let url = new URL(window.location.href);

		url.searchParams.set('<portlet:namespace />scope', scope.value);

		if (Liferay.SPA) {
			Liferay.SPA.app.navigate(url);
		}
		else {
			window.location.href = url;
		}
	}
</script>
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
ObjectDefinition objectDefinition = (ObjectDefinition)request.getAttribute(ObjectWebKeys.OBJECT_DEFINITION);

String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.format(request, "edit-x", objectDefinition.getName(), false));
%>

<portlet:actionURL name="/object_definitions/edit_object_definition" var="editObjectURL" />

<liferay-frontend:edit-form
	action="<%= editObjectURL %>"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<liferay-frontend:edit-form-body>
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
						<aui:input cssClass="disabled" label="object-definition-id" name="objectDefinitionId" readonly="true" type="text" value="<%= String.valueOf(objectDefinition.getObjectDefinitionId()) %>" />

						<aui:input cssClass="disabled" label="name" name="name" readonly="true" type="text" value="<%= String.valueOf(objectDefinition.getName()) %>" />

						<aui:input cssClass="disabled" label="object-definition-table-name" name="dbTableName" readonly="true" type="text" value="<%= String.valueOf(objectDefinition.getDBTableName()) %>" />
					</clay:col>
				</clay:row>

				<aui:field-wrapper cssClass="form-group lfr-input-text-container">
					<aui:input label="" labelOff="inactive" labelOn="active" name="active" type="toggle-switch" value="<%= objectDefinition.isSystem() %>" />
				</aui:field-wrapper>
			</clay:sheet-section>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href="<%= backURL %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>
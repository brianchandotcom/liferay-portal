<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/data_layout_builder/init.jsp" %>

<portlet:renderURL var="basePortletURL" />

<div class="data-engine-form-builder-messages">
	<liferay-ui:error exception="<%= DataDefinitionValidationException.class %>" message="please-enter-a-valid-form-definition" />

	<liferay-ui:error exception="<%= DataDefinitionValidationException.MustNotDuplicateFieldName.class %>">

		<%
		DataDefinitionValidationException.MustNotDuplicateFieldName mndfn = (DataDefinitionValidationException.MustNotDuplicateFieldName)errorException;
		%>

		<liferay-ui:message arguments="<%= HtmlUtil.escape(StringUtil.merge(mndfn.getDuplicatedFieldNames(), StringPool.COMMA_AND_SPACE)) %>" key="the-definition-field-name-x-was-defined-more-than-once" translateArguments="<%= false %>" />
	</liferay-ui:error>

	<liferay-ui:error exception="<%= DataDefinitionValidationException.MustSetFields.class %>" message="at-least-one-field-must-be-added" />

	<liferay-ui:error exception="<%= DataDefinitionValidationException.MustSetOptionsForField.class %>">

		<%
		DataDefinitionValidationException.MustSetOptionsForField msoff = (DataDefinitionValidationException.MustSetOptionsForField)errorException;
		%>

		<liferay-ui:message arguments="<%= HtmlUtil.escape(msoff.getFieldLabel()) %>" key="at-least-one-option-should-be-set-for-field-x" translateArguments="<%= false %>" />
	</liferay-ui:error>

	<liferay-ui:error exception="<%= DataDefinitionValidationException.MustSetValidCharactersForFieldName.class %>">

		<%
		DataDefinitionValidationException.MustSetValidCharactersForFieldName msvcffn = (DataDefinitionValidationException.MustSetValidCharactersForFieldName)errorException;
		%>

		<liferay-ui:message arguments="<%= HtmlUtil.escape(msvcffn.getFieldName()) %>" key="invalid-characters-were-defined-for-field-name-x" translateArguments="<%= false %>" />
	</liferay-ui:error>

	<liferay-ui:error exception="<%= DataDefinitionValidationException.MustSetValidName.class %>" message="please-enter-a-valid-name" />

	<liferay-ui:error exception="<%= DataLayoutValidationException.class %>" message="please-enter-a-valid-form-layout" />

	<liferay-ui:error exception="<%= DataLayoutValidationException.MustNotDuplicateFieldName.class %>">

		<%
		DataLayoutValidationException.MustNotDuplicateFieldName mndfn = (DataLayoutValidationException.MustNotDuplicateFieldName)errorException;
		%>

		<liferay-ui:message arguments="<%= HtmlUtil.escape(StringUtil.merge(mndfn.getDuplicatedFieldNames(), StringPool.COMMA_AND_SPACE)) %>" key="the-definition-field-name-x-was-defined-more-than-once" translateArguments="<%= false %>" />
	</liferay-ui:error>
</div>

<div id="<%= componentId %>container">
	<react:component
		module="<%= module %>"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"availableLanguageIds", availableLanguageIds
			).put(
				"config", configJSONObject
			).put(
				"contentType", contentType
			).put(
				"contentTypeConfig", contentTypeConfigJSONObject
			).put(
				"context", dataLayoutJSONObject
			).put(
				"dataDefinitionId", dataDefinitionId
			).put(
				"dataLayoutBuilderElementId", liferayPortletResponse.getNamespace() + "-data-layout-builder"
			).put(
				"dataLayoutBuilderId", componentId
			).put(
				"dataLayoutId", dataLayoutId
			).put(
				"defaultLanguageId", defaultLanguageId
			).put(
				"fieldSetContentType", fieldSetContentType
			).put(
				"fieldTypes", fieldTypesJSONArray
			).put(
				"fieldTypesModules", fieldTypesModules
			).put(
				"groupId", groupId
			).put(
				"localizable", localizable
			).put(
				"sidebarPanels", sidebarPanels
			).put(
				"spritemap", themeDisplay.getPathThemeSpritemap()
			).put(
				"submitButtonId", submitButtonId
			).build()
		%>'
		servletContext="<%= moduleServletContext %>"
	/>
</div>
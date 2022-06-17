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

<liferay-ui:icon
	id="importObjectDefinitionIcon"
	message="import-object"
	onClick='<%= liferayPortletResponse.getNamespace() + "openImportObjectDefinitionModal();" %>'
	url="javascript:void(0);"
/>

<div>
	<liferay-ui:error embed="<%= false %>" exception="<%= ObjectDefinitionNameException.MustBeginWithUpperCaseLetter.class %>" message="the-first-character-of-a-name-must-be-an-upper-case-letter" />
	<liferay-ui:error embed="<%= false %>" exception="<%= ObjectDefinitionNameException.MustNotBeDuplicate.class %>" message="this-name-is-already-in-use-try-another-one" />
	<liferay-ui:error embed="<%= false %>" exception="<%= ObjectDefinitionNameException.MustOnlyContainLettersAndDigits.class %>" message="name-must-only-contain-letters-and-digits" />
	<liferay-ui:error embed="<%= false %>" key="importObjectDefinitionErrorMessage" message="the-structure-was-not-successfully-imported" />

	<react:component
		module="js/components/ModalImportObjectDefinition"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"importObjectDefinitionURL",
				PortletURLBuilder.createActionURL(
					renderResponse
				).setActionName(
					"/object_definitions/import_object_definition"
				).setRedirect(
					currentURL
				).buildString()
			).put(
				"nameMaxLength", ModelHintsConstants.TEXT_MAX_LENGTH
			).build()
		%>'
	/>
</div>

<aui:script>
	function <portlet:namespace />openImportObjectDefinitionModal() {
		Liferay.componentReady(
			'<portlet:namespace />importObjectDefinitionModal'
		).then((importObjectDefinitionModal) => {
			importObjectDefinitionModal.open();
		});
	}
</aui:script>
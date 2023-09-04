<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewListTypeDefinitionsDisplayContext viewListTypeDefinitionsDisplayContext = (ViewListTypeDefinitionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<frontend-data-set:headless-display
	apiURL="<%= viewListTypeDefinitionsDisplayContext.getAPIURL() %>"
	creationMenu="<%= viewListTypeDefinitionsDisplayContext.getCreationMenu() %>"
	fdsActionDropdownItems="<%= viewListTypeDefinitionsDisplayContext.getFDSActionDropdownItems() %>"
	formName="fm"
	id="<%= ListTypeFDSNames.LIST_TYPE_DEFINITIONS %>"
	propsTransformer="js/components/FDSPropsTransformer/MultiselectPicklistFDSPropsTransformer"
	style="fluid"
/>

<div id="<portlet:namespace />addListTypeDefinition">
	<react:component
		module="js/components/ModalAddListTypeDefinition"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"apiURL", viewListTypeDefinitionsDisplayContext.getAPIURL()
			).build()
		%>'
	/>
</div>

<div>
	<react:component
		module="js/components/ListTypeDefinition/ListTypeEntriesModal"
	/>
</div>
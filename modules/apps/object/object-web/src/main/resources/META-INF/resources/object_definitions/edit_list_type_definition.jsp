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
ListTypeDefinition listTypeDefinition = (ListTypeDefinition)request.getAttribute("LIST_TYPE_DEFINITION");

ViewListTypeEntriesDisplayContext viewListTypeEntriesDisplayContext = (ViewListTypeEntriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-frontend:side-panel-content
	title='<%= LanguageUtil.get(request, "picklist") %>'
>
	<form action="javascript:;" onSubmit="<%= liferayPortletResponse.getNamespace() + "saveListTypeDefinition();" %>">
		<div class="side-panel-content">
			<div class="side-panel-content__body">
				<div class="sheet">
					<h2 class="sheet-title">
						<liferay-ui:message key="basic-info" />
					</h2>

					<aui:input name="name" required="<%= true %>" value="<%= listTypeDefinition.getName(themeDisplay.getLocale()) %>" />
				</div>

				<div class="sheet">
					<h2 class="sheet-title">
						<liferay-ui:message key="items" />
					</h2>

					<clay:headless-data-set-display
						apiURL="<%= viewListTypeEntriesDisplayContext.getAPIURL() %>"
						creationMenu="<%= viewListTypeEntriesDisplayContext.getCreationMenu() %>"
						formId="fm"
						id="<%= ObjectDefinitionsClayDataSetDisplayNames.LIST_TYPE_DEFINITION_ITEMS %>"
						itemsPerPage="<%= 20 %>"
						namespace="<%= liferayPortletResponse.getNamespace() %>"
						pageNumber="<%= 1 %>"
						portletURL="<%= liferayPortletResponse.createRenderURL() %>"
						style="fluid"
					/>
				</div>
			</div>

			<div class="side-panel-content__footer">
				<aui:button cssClass="btn-cancel mr-1" name="cancel" value='<%= LanguageUtil.get(request, "cancel") %>' />

				<aui:button name="save" type="submit" value='<%= LanguageUtil.get(request, "save") %>' />
			</div>
		</div>
	</form>
</liferay-frontend:side-panel-content>

<div id="<portlet:namespace />addListTypeEntry">
	<react:component
		module="js/components/ModalAddListTypeEntry"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"apiURL", viewListTypeEntriesDisplayContext.getAPIURL()
			).put(
				"spritemap", themeDisplay.getPathThemeImages() + "/clay/icons.svg"
			).build()
		%>'
	/>
</div>

<script>
	function handleDestroyPortlet() {
		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</script>
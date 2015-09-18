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
StructureDisplayTerms displayTerms = new StructureDisplayTerms(renderRequest);
%>

<liferay-ui:search-toggle
	buttonLabel="search"
	displayTerms="<%= displayTerms %>"
	id="toggle_id_ddm_structure_search"
	markupView="lexicon"
>
	<aui:fieldset cssClass="lfr-ddm-search-form">
		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" inlineField="<%= true %>" name="<%= StructureDisplayTerms.NAME %>" size="20" value="<%= displayTerms.getName() %>" />

		<aui:input inlineField="<%= true %>" name="<%= StructureDisplayTerms.DESCRIPTION %>" size="20" value="<%= displayTerms.getDescription() %>" />

		<aui:input name="<%= StructureDisplayTerms.CLASS_NAME_ID %>" type="hidden" value="<%= scopeClassNameId %>" />

		<c:choose>
			<c:when test="<%= Validator.isNull(storageTypeValue) %>">
				<aui:select inlineField="<%= true %>" name="storageType">

					<%
					for (StorageType storageType : StorageType.values()) {
					%>

						<aui:option label="<%= storageType %>" selected="<%= storageType.equals(displayTerms.getStorageType()) %>" value="<%= storageType %>" />

					<%
					}
					%>

				</aui:select>
			</c:when>
			<c:otherwise>
				<aui:input name="storageType" type="hidden" value="<%= storageTypeValue %>" />
			</c:otherwise>
		</c:choose>
	</aui:fieldset>
</liferay-ui:search-toggle>
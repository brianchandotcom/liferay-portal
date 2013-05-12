<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/dynamic_data_mapping/init.jsp" %>

<%
StructureDisplayTerms displayTerms = new StructureDisplayTerms(renderRequest);
%>

<liferay-ui:search-toggle
	buttonLabel="search"
	displayTerms="<%= displayTerms %>"
	id="toggle_id_ddm_structure_search"
>
	<aui:fieldset cssClass="lfr-ddm-search-form">
		<aui:input name="<%= displayTerms.NAME %>" size="20" value="<%= displayTerms.getName() %>" />

		<aui:input name="<%= displayTerms.DESCRIPTION %>" size="20" value="<%= displayTerms.getDescription() %>" />

		<c:choose>
			<c:when test="<%= scopeClassNameId == 0 %>">
				<aui:select label="type" name="<%= displayTerms.CLASS_NAME_ID %>">
					<aui:option label="<%= ResourceActionsUtil.getModelResource(locale, DDLRecordSet.class.getName()) %>" selected='<%= "datalist".equals(displayTerms.getStorageType()) %>' value="<%= PortalUtil.getClassNameId(DDLRecordSet.class.getName()) %>" />
					<aui:option label="<%= ResourceActionsUtil.getModelResource(locale, DLFileEntryMetadata.class.getName()) %>" selected='<%= "datalist".equals(displayTerms.getStorageType()) %>' value="<%= PortalUtil.getClassNameId(DLFileEntryMetadata.class.getName()) %>" />
				</aui:select>
			</c:when>
			<c:otherwise>
				<aui:input name="<%= displayTerms.CLASS_NAME_ID %>" type="hidden" value="<%= scopeClassNameId %>" />
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="<%= Validator.isNull(storageTypeValue) %>">
				<aui:select name="storageType">

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

<%
boolean showAddStructureButton = ddmDisplay.isShowAddStructureButton(permissionChecker, scopeGroupId);

String buttonLabel = "add-structure";

if (Validator.isNotNull(scopeStructureName)) {
	buttonLabel = LanguageUtil.format(pageContext, "add-x", scopeStructureName);
}
%>

<c:if test="<%= showAddStructureButton %>">
	<aui:button-row>
		<aui:button onClick='<%= renderResponse.getNamespace() + "addStructure();" %>' value="<%= buttonLabel %>" />
	</aui:button-row>
</c:if>

<aui:script>
	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.NAME %>);
	</c:if>
</aui:script>

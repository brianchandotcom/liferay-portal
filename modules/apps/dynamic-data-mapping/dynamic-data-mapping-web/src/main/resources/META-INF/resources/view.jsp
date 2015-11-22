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
String tabs1 = ParamUtil.getString(request, "tabs1", "structures");

long groupId = ParamUtil.getLong(request, "groupId", themeDisplay.getSiteGroupId());

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view.jsp");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("groupId", String.valueOf(groupId));

String searchContainerId = "ddmStructures";
%>

<liferay-ui:error exception="<%= RequiredStructureException.MustNotDeleteStructureReferencedByStructureLinks.class %>" message="the-structure-cannot-be-deleted-because-it-is-required-by-one-or-more-structure-links" />
<liferay-ui:error exception="<%= RequiredStructureException.MustNotDeleteStructureReferencedByTemplates.class %>" message="the-structure-cannot-be-deleted-because-it-is-required-by-one-or-more-templates" />
<liferay-ui:error exception="<%= RequiredStructureException.MustNotDeleteStructureThatHasChild.class %>" message="the-structure-cannot-be-deleted-because-it-has-one-or-more-substructures" />

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
	<aui:input name="deleteStructureIds" type="hidden" />

	<%
	String orderByCol = ParamUtil.getString(request, "orderByCol");
	String orderByType = ParamUtil.getString(request, "orderByType");

	if (Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
		portalPreferences.setValue(DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-col", orderByCol);
		portalPreferences.setValue(DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-type", orderByType);
	}
	else {
		orderByCol = portalPreferences.getValue(DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-col", "id");
		orderByType = portalPreferences.getValue(DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-type", "asc");
	}

	OrderByComparator<DDMStructure> orderByComparator = DDMUtil.getStructureOrderByComparator(orderByCol, orderByType);
	%>

	<c:if test="<%= showToolbar %>">
		<liferay-util:include page="/search_bar.jsp" servletContext="<%= application %>">
			<liferay-util:param name="groupId" value="<%= String.valueOf(groupId) %>" />
		</liferay-util:include>

		<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>">
			<liferay-util:param name="searchContainerId" value="<%= searchContainerId %>" />
		</liferay-util:include>
	</c:if>

	<div class="container-fluid-1280" id="<portlet:namespace />entriesContainer">
		<liferay-ui:search-container
			id="<%= searchContainerId %>"
			orderByCol="<%= orderByCol %>"
			orderByComparator="<%= orderByComparator %>"
			orderByType="<%= orderByType %>"
			rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
			searchContainer="<%= new StructureSearch(renderRequest, portletURL) %>"
		>

			<liferay-ui:search-container-results>
				<%@ include file="/structure_search_results.jspf" %>
			</liferay-ui:search-container-results>

			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.mapping.model.DDMStructure"
				keyProperty="structureId"
				modelVar="structure"
			>

				<%
				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcPath", "/edit_structure.jsp");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("classNameId", String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)));
				rowURL.setParameter("classPK", String.valueOf(structure.getStructureId()));

				String rowHREF = rowURL.toString();
				%>

				<liferay-ui:search-container-column-text
					href="<%= rowHREF %>"
					name="id"
					orderable="<%= true %>"
					orderableProperty="id"
					property="structureId"
				/>

				<liferay-ui:search-container-column-text
					href="<%= rowHREF %>"
					name="name"
					value="<%= HtmlUtil.escape(structure.getName(locale)) %>"
				/>

				<liferay-ui:search-container-column-text
					href="<%= rowHREF %>"
					name="description"
					value="<%= HtmlUtil.escape(structure.getDescription(locale)) %>"
				/>

				<c:if test="<%= Validator.isNull(storageTypeValue) %>">
					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="storage-type"
						value="<%= LanguageUtil.get(request, structure.getStorageType()) %>"
					/>
				</c:if>

				<c:if test="<%= scopeClassNameId == 0 %>">
					<liferay-ui:search-container-column-text
						href="<%= rowHREF %>"
						name="type"
						value="<%= ResourceActionsUtil.getModelResource(locale, structure.getClassName()) %>"
					/>
				</c:if>

				<%
				Group group = GroupLocalServiceUtil.getGroup(structure.getGroupId());
				%>

				<liferay-ui:search-container-column-text
					name="scope"
					value="<%= LanguageUtil.get(request, group.getScopeLabel(themeDisplay)) %>"
				/>

				<liferay-ui:search-container-column-date
					href="<%= rowHREF %>"
					name="modified-date"
					orderable="<%= true %>"
					orderableProperty="modified-date"
					value="<%= structure.getModifiedDate() %>"
				/>

				<liferay-ui:search-container-column-jsp
					align="right"
					cssClass="entry-action"
					path="/structure_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator markupView="lexicon" />
		</liferay-ui:search-container>
	</div>

	<c:if test="<%= ddmDisplay.isShowAddStructureButton() && DDMStructurePermission.containsAddStruturePermission(permissionChecker, groupId, scopeClassNameId) %>">
		<liferay-portlet:renderURL var="viewStructuresURL">
			<portlet:param name="mvcPath" value="/view.jsp" />
			<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
		</liferay-portlet:renderURL>

		<liferay-portlet:renderURL var="addStructureURL">
			<portlet:param name="mvcPath" value="/edit_structure.jsp" />
			<portlet:param name="redirect" value="<%= viewStructuresURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
		</liferay-portlet:renderURL>

		<liferay-frontend:add-menu>
			<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add") %>' url="<%= addStructureURL %>" />
		</liferay-frontend:add-menu>
	</c:if>
</aui:form>

<aui:script>
	function <portlet:namespace />copyStructure(uri) {
		Liferay.Util.openWindow(
			{
				id: '<portlet:namespace />copyStructure',
				refreshWindow: window,
				title: '<%= UnicodeLanguageUtil.format(request, "copy-x", ddmDisplay.getStructureName(locale), false) %>',
				uri: uri
			}
		);
	}
</aui:script>
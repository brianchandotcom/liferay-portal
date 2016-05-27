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

String redirect = ParamUtil.getString(request, "redirect");

long groupId = ParamUtil.getLong(request, "groupId", themeDisplay.getSiteGroupId());

boolean showBackURL = ParamUtil.getBoolean(request, "showBackURL", true);

PortletURL iteratorURL = renderResponse.createRenderURL();

StructureSearch structureSearch = new StructureSearch(renderRequest, iteratorURL);

OrderByComparator<DDMStructure> orderByComparator = DDMUtil.getStructureOrderByComparator(ddmDisplayContext.getOrderByCol(), ddmDisplayContext.getOrderByType());

structureSearch.setOrderByCol(ddmDisplayContext.getOrderByCol());
structureSearch.setOrderByComparator(orderByComparator);
structureSearch.setOrderByType(ddmDisplayContext.getOrderByType());
%>

<c:if test="<%= showBackURL && ddmDisplay.isShowBackURLInTitleBar() %>">

	<%
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(ddmDisplay.getViewStructuresBackURL(liferayPortletRequest, liferayPortletResponse));

	renderResponse.setTitle(ddmDisplay.getTitle(locale));
	%>

</c:if>

<liferay-ui:error exception="<%= RequiredStructureException.MustNotDeleteStructureReferencedByStructureLinks.class %>" message="the-structure-cannot-be-deleted-because-it-is-required-by-one-or-more-structure-links" />
<liferay-ui:error exception="<%= RequiredStructureException.MustNotDeleteStructureReferencedByTemplates.class %>" message="the-structure-cannot-be-deleted-because-it-is-required-by-one-or-more-templates" />
<liferay-ui:error exception="<%= RequiredStructureException.MustNotDeleteStructureThatHasChild.class %>" message="the-structure-cannot-be-deleted-because-it-has-one-or-more-substructures" />

<liferay-ui:success key='<%= DDMPortletKeys.DYNAMIC_DATA_MAPPING + "requestProcessed" %>' message="your-request-completed-successfully" />

<portlet:renderURL var="portletURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
	<portlet:param name="tabs1" value="<%= tabs1 %>" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
</portlet:renderURL>

<liferay-util:include page="/search_bar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="groupId" value="<%= String.valueOf(groupId) %>" />
</liferay-util:include>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>">
		<liferay-util:param name="orderByCol" value="<%= ddmDisplayContext.getOrderByCol() %>" />
		<liferay-util:param name="orderByType" value="<%= ddmDisplayContext.getOrderByType() %>" />
		<liferay-util:param name="searchContainerId" value="ddmStructures" />
	</liferay-util:include>

	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
	<aui:input name="deleteStructureIds" type="hidden" />

	<div class="container-fluid-1280" id="<portlet:namespace />entriesContainer">
		<liferay-ui:search-container
			id="ddmStructures"
			rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
			searchContainer="<%= structureSearch %>"
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
				String rowHREF = StringPool.BLANK;

				if (DDMStructurePermission.contains(permissionChecker, structure, refererPortletName, ActionKeys.UPDATE)) {
					PortletURL rowURL = renderResponse.createRenderURL();

					rowURL.setParameter("mvcPath", "/edit_structure.jsp");
					rowURL.setParameter("redirect", currentURL);
					rowURL.setParameter("classNameId", String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)));
					rowURL.setParameter("classPK", String.valueOf(structure.getStructureId()));

					rowHREF = rowURL.toString();
				}
				%>

				<liferay-ui:search-container-column-text
					cssClass="text-strong"
					href="<%= rowHREF %>"
					name="id"
					orderable="<%= true %>"
					orderableProperty="id"
					property="structureId"
				/>

				<liferay-ui:search-container-column-text
					cssClass="text-strong"
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
</aui:form>

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
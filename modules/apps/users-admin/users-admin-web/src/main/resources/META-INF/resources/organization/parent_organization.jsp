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
long organizationId = ParamUtil.getLong(request, "organizationId");

Organization organization = OrganizationServiceUtil.fetchOrganization(organizationId);

long parentOrganizationId = ParamUtil.getLong(request, "parentOrganizationSearchContainerPrimaryKeys", (organization != null) ? organization.getParentOrganizationId() : OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);

if (parentOrganizationId <= 0) {
	parentOrganizationId = OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID;

	if (organization != null) {
		parentOrganizationId = organization.getParentOrganizationId();
	}
}

User selUser = (User)request.getAttribute("user.selUser");
%>

<%
Organization parentOrganization = null;

if ((organization == null) && (parentOrganizationId == OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) && !permissionChecker.isCompanyAdmin()) {
	List<Organization> manageableOrganizations = new ArrayList<Organization>();

	for (Organization curOrganization : selUser.getOrganizations()) {
		if (OrganizationPermissionUtil.contains(permissionChecker, curOrganization, ActionKeys.MANAGE_SUBORGANIZATIONS)) {
			manageableOrganizations.add(curOrganization);
		}
	}

	if (manageableOrganizations.size() == 1) {
		parentOrganizationId = manageableOrganizations.get(0).getOrganizationId();
	}
}

if (parentOrganizationId != OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {
	try {
		parentOrganization = OrganizationLocalServiceUtil.getOrganization(parentOrganizationId);
	}
	catch (NoSuchOrganizationException nsoe) {
	}
}

List<Organization> parentOrganizations = new ArrayList<Organization>();

if (parentOrganization != null) {
	parentOrganizations.add(parentOrganization);
}
%>

<h3 class="autofit-row sheet-subtitle">
	<span class="autofit-col autofit-col-expand">
		<span class="heading-text"><liferay-ui:message key="parent-organization" /></span>
	</span>
	<span class="autofit-col">
		<span class="heading-end">
			<liferay-ui:icon
				cssClass="modify-link"
				id="selectOrganizationLink"
				label="<%= true %>"
				linkCssClass="btn btn-secondary btn-sm"
				message='<%= (parentOrganizations.size() > 0) ? "change" : "select" %>'
				url="javascript:;"
			/>
		</span>
	</span>
</h3>

<liferay-util:buffer
	var="removeOrganizationIcon"
>
	<liferay-ui:icon
		icon="times-circle"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<liferay-ui:error exception="<%= OrganizationParentException.class %>" message="please-enter-a-valid-parent-organization" />

<liferay-ui:error exception="<%= OrganizationParentException.MustBeRootable.class %>">

	<%
	OrganizationParentException.MustBeRootable mbr = (OrganizationParentException.MustBeRootable)errorException;
	%>

	<liferay-ui:message arguments="<%= mbr.getType() %>" key="an-organization-of-type-x-cannot-be-a-root-organization" />
</liferay-ui:error>

<liferay-ui:error exception="<%= OrganizationParentException.MustHaveValidChildType.class %>">

	<%
	OrganizationParentException.MustHaveValidChildType mhvct = (OrganizationParentException.MustHaveValidChildType)errorException;
	%>

	<liferay-ui:message arguments="<%= new String[] {mhvct.getChildOrganizationType(), mhvct.getParentOrganizationType()} %>" key="an-organization-of-type-x-is-not-allowed-as-a-child-of-type-x" />
</liferay-ui:error>

<liferay-ui:error exception="<%= OrganizationParentException.MustNotHaveChildren.class %>">

	<%
	OrganizationParentException.MustNotHaveChildren mnhc = (OrganizationParentException.MustNotHaveChildren)errorException;
	%>

	<liferay-ui:message arguments="<%= mnhc.getType() %>" key="an-organization-of-type-x-cannot-have-children" />
</liferay-ui:error>

<liferay-ui:search-container
	compactEmptyResultsMessage="<%= true %>"
	emptyResultsMessage="this-organization-does-not-have-a-parent-organization"
	headerNames="name,type,null"
	id="parentOrganizationSearchContainer"
	total="<%= parentOrganizations.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= parentOrganizations %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.Organization"
		escapedModel="<%= true %>"
		keyProperty="organizationId"
		modelVar="curOrganization"
	>
		<portlet:renderURL var="rowURL">
			<portlet:param name="mvcRenderCommandName" value="/users_admin/edit_organization" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="organizationId" value="<%= String.valueOf(curOrganization.getOrganizationId()) %>" />
		</portlet:renderURL>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand table-cell-minw-200 table-title"
			href="<%= rowURL %>"
			name="name"
			property="name"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand table-cell-minw-150"
			href="<%= rowURL %>"
			name="type"
			value="<%= LanguageUtil.get(request, curOrganization.getType()) %>"
		/>

		<liferay-ui:search-container-column-text>
			<a class="modify-link" data-rowId="<%= curOrganization.getOrganizationId() %>" href="javascript:;"><%= removeOrganizationIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
		paginate="<%= false %>"
	/>
</liferay-ui:search-container>

<portlet:renderURL var="selectOrganizationRenderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/select_organization.jsp" />
	<portlet:param name="organizationId" value="<%= String.valueOf(organizationId) %>" />
	<portlet:param name="p_u_i_d" value='<%= (selUser == null) ? "0" : String.valueOf(selUser.getUserId()) %>' />
</portlet:renderURL>

<aui:script use="liferay-search-container">
	function <portlet:namespace />createURL(href, value, onclick) {
		return (
			'<a href="' +
			href +
			'"' +
			(onclick ? ' onclick="' + onclick + '" ' : '') +
			'>' +
			value +
			'</a>'
		);
	}

	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />parentOrganizationSearchContainer'
	);

	var selectOrganizationLink = A.one(
		'#<portlet:namespace />selectOrganizationLink'
	);

	var selectOrganizationLinkText = selectOrganizationLink.one('.taglib-text');

	if (selectOrganizationLink) {
		searchContainer.get('contentBox').delegate(
			'click',
			function(event) {
				var link = event.currentTarget;
				var tr = link.ancestor('tr');

				searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));

				selectOrganizationLinkText.text(
					'<liferay-ui:message key="select" />'
				);
			},
			'.modify-link'
		);

		selectOrganizationLink.on('click', function(event) {
			var searchContainerData = searchContainer.getData();

			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						modal: true
					},
					id: '<portlet:namespace />selectOrganization',
					selectedData: [searchContainerData],
					title:
						'<liferay-ui:message arguments="parent-organization" key="select-x" />',
					uri: '<%= selectOrganizationRenderURL %>'
				},
				function(event) {
					var rowColumns = [];

					var href =
						'<portlet:renderURL><portlet:param name="mvcRenderCommandName" value="/users_admin/edit_organization" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>&<portlet:namespace />organizationId=' +
						event.entityid;

					rowColumns.push(
						<portlet:namespace />createURL(href, event.entityname)
					);
					rowColumns.push(
						<portlet:namespace />createURL(href, event.type)
					);
					rowColumns.push(
						'<a class="modify-link" data-rowId="' +
							event.entityid +
							'" href="javascript:;"><%= UnicodeFormatter.toString(removeOrganizationIcon) %></a>'
					);

					searchContainer.deleteRow(1, searchContainer.getData());
					searchContainer.addRow(rowColumns, event.entityid);
					searchContainer.updateDataStore(event.entityid);

					selectOrganizationLinkText.text(
						'<liferay-ui:message key="change" />'
					);
				}
			);
		});
	}
</aui:script>
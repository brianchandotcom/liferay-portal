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

String[] organizationsTypes = OrganizationLocalServiceUtil.getTypes();

String type = BeanParamUtil.getString(organization, request, "type", organizationsTypes[0]);

long regionId = BeanParamUtil.getLong(organization, request, "regionId");
long countryId = BeanParamUtil.getLong(organization, request, "countryId");

long groupId = 0;

if (organization != null) {
	groupId = organization.getGroupId();
}
%>

<aui:model-context bean="<%= organization %>" model="<%= Organization.class %>" />

<div class="row">
	<div class="col-md-7">
		<liferay-ui:error exception="<%= DuplicateOrganizationException.class %>" message="the-organization-name-is-already-taken" />

		<liferay-ui:error exception="<%= OrganizationNameException.class %>">
			<liferay-ui:message arguments="<%= new String[] {OrganizationConstants.NAME_LABEL, OrganizationConstants.NAME_GENERAL_RESTRICTIONS, OrganizationConstants.NAME_RESERVED_WORDS} %>" key="the-x-cannot-be-x-or-a-reserved-word-such-as-x" />
		</liferay-ui:error>

		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="name" />

		<c:choose>
			<c:when test="<%= PropsValues.FIELD_ENABLE_COM_LIFERAY_PORTAL_KERNEL_MODEL_ORGANIZATION_STATUS %>">
				<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + Organization.class.getName() + ListTypeConstants.ORGANIZATION_STATUS %>" message="please-select-a-type" />

				<aui:select label="status" listType="<%= ListTypeConstants.ORGANIZATION_STATUS %>" listTypeFieldName="statusId" name="statusId" showEmptyOption="<%= true %>" />
			</c:when>
			<c:otherwise>
				<aui:input name="statusId" type="hidden" value="<%= (organization != null) ? organization.getStatusId() : ListTypeConstants.ORGANIZATION_STATUS_DEFAULT %>" />
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="<%= (organization == null) && (organizationsTypes.length > 1) %>">
				<aui:select name="type">

					<%
					for (String curType : organizationsTypes) {
					%>

						<aui:option label="<%= curType %>" selected="<%= type.equals(curType) %>" />

					<%
					}
					%>

				</aui:select>
			</c:when>
			<c:when test="<%= organization == null %>">
				<aui:input name="type" type="hidden" value="<%= organizationsTypes[0] %>" />
			</c:when>
			<c:otherwise>
				<aui:input name="typeLabel" type="resource" value="<%= LanguageUtil.get(request, organization.getType()) %>" />

				<aui:input name="type" type="hidden" value="<%= organization.getType() %>" />
			</c:otherwise>
		</c:choose>

		<liferay-ui:error exception="<%= NoSuchCountryException.class %>" message="please-select-a-country" />

		<div class="<%= OrganizationLocalServiceUtil.isCountryEnabled(type) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />countryDiv">
			<aui:select label="country" name="countryId" />

			<aui:select label="region" name="regionId" />
		</div>
	</div>

	<div class="col-md-5">
		<div align="middle">
			<c:if test="<%= organization != null %>">

				<%
				long logoId = organization.getLogoId();

				UserFileUploadsConfiguration userFileUploadsConfiguration = (UserFileUploadsConfiguration)request.getAttribute(UserFileUploadsConfiguration.class.getName());
				%>

				<label class="control-label"></label>

				<liferay-ui:logo-selector
					currentLogoURL='<%= themeDisplay.getPathImage() + "/organization_logo?img_id=" + logoId + "&t=" + WebServerServletTokenUtil.getToken(logoId) %>'
					defaultLogo="<%= logoId == 0 %>"
					defaultLogoURL='<%= themeDisplay.getPathImage() + "/organization_logo?img_id=0" %>'
					logoDisplaySelector=".organization-logo"
					maxFileSize="<%= userFileUploadsConfiguration.imageMaxSize() %>"
					tempImageFileName="<%= String.valueOf(groupId) %>"
				/>
			</c:if>
		</div>
	</div>
</div>

<aui:script use="liferay-dynamic-select">
	new Liferay.DynamicSelect([
		{
			select: '<portlet:namespace />countryId',
			selectData: Liferay.Address.getCountries,
			selectDesc: 'nameCurrentValue',
			selectId: 'countryId',
			selectSort: '<%= true %>',
			selectVal: '<%= countryId %>'
		},
		{
			select: '<portlet:namespace />regionId',
			selectData: Liferay.Address.getRegions,
			selectDesc: 'name',
			selectDisableOnEmpty: true,
			selectId: 'regionId',
			selectVal: '<%= regionId %>'
		}
	]);
</aui:script>

<c:if test="<%= organization == null %>">
	<aui:script sandbox="<%= true %>">
		var typeSelect = document.getElementById('<portlet:namespace />type');

		if (typeSelect) {
			typeSelect.addEventListener('change', function(event) {
				var countryDiv = document.getElementById(
					'<portlet:namespace />countryDiv'
				);

				if (countryDiv) {

					<%
					for (String curType : organizationsTypes) {
					%>

						if (event.currentTarget.value === '<%= curType %>') {
							if (
								!<%= OrganizationLocalServiceUtil.isCountryEnabled(curType) %>
							) {
								countryDiv.classList.add('hide');
							} else {
								countryDiv.classList.remove('hide');
							}
						}

					<%
					}
					%>

				}
			});
		}
	</aui:script>
</c:if>
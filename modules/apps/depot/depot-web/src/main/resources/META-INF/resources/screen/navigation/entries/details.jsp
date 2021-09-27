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
DepotAdminDetailsDisplayContext depotAdminDetailsDisplayContext = (DepotAdminDetailsDisplayContext)request.getAttribute(DepotAdminWebKeys.DEPOT_ADMIN_DETAILS_DISPLAY_CONTEXT);
%>

<liferay-ui:error exception="<%= DuplicateGroupException.class %>" message="please-enter-a-unique-name" />

<liferay-ui:error exception="<%= GroupKeyException.class %>">
	<p>
		<liferay-ui:message arguments="<%= new String[] {DepotEntryConstants.NAME_LABEL, DepotEntryConstants.getNameGeneralRestrictions(locale), DepotEntryConstants.NAME_RESERVED_WORDS} %>" key="the-x-cannot-be-x-or-a-reserved-word-such-as-x" />
	</p>

	<p>
		<liferay-ui:message arguments="<%= new String[] {DepotEntryConstants.NAME_LABEL, DepotEntryConstants.NAME_INVALID_CHARACTERS} %>" key="the-x-cannot-contain-the-following-invalid-characters-x" />
	</p>
</liferay-ui:error>

<liferay-frontend:fieldset-group>
	<liferay-frontend:fieldset
		collapsible="<%= false %>"
		label='<%= LanguageUtil.get(request, "details") %>'
	>
		<aui:model-context bean="<%= depotAdminDetailsDisplayContext.getGroup() %>" model="<%= Group.class %>" />

		<aui:input name="assetLibraryId" type="resource" value="<%= String.valueOf(depotAdminDetailsDisplayContext.getDepotEntryId()) %>" />

		<aui:input name="groupId" type="resource" value="<%= String.valueOf(depotAdminDetailsDisplayContext.getGroupId()) %>" />

		<aui:input name="name" placeholder="name" required="<%= true %>" value="<%= depotAdminDetailsDisplayContext.getDepotName(locale) %>" />

		<aui:input name="description" placeholder="description" />
	</liferay-frontend:fieldset>

	<liferay-frontend:fieldset
		collapsible="<%= true %>"
		cssClass="panel-group-flush"
		label='<%= LanguageUtil.get(request, "applications") %>'
	>
		<p class="text-muted">
			<liferay-ui:message key="asset-library-applications-description" />
		</p>

		<clay:row>

			<%
			for (DepotApplication depotApplication : depotAdminDetailsDisplayContext.getDepotApplications()) {
			%>

				<clay:col
					md="6"
				>
					<aui:input label="<%= depotApplication.getLabel(locale) %>" name='<%= "DepotAppCustomization--" + depotApplication.getPortletId() + "--" %>' type="checkbox" value="<%= depotAdminDetailsDisplayContext.isEnabled(depotApplication.getPortletId()) %>" />
				</clay:col>

			<%
			}
			%>

		</clay:row>
	</liferay-frontend:fieldset>

	<liferay-util:include page="/screen/navigation/entries/sharing.jsp" servletContext="<%= application %>" />

	<liferay-util:include page="/screen/navigation/entries/asset_auto_tagger.jsp" servletContext="<%= application %>" />

	<liferay-util:include page="/screen/navigation/entries/documents_and_media.jsp" servletContext="<%= application %>" />

	<liferay-util:include page="/screen/navigation/entries/recycle_bin.jsp" servletContext="<%= application %>" />
</liferay-frontend:fieldset-group>
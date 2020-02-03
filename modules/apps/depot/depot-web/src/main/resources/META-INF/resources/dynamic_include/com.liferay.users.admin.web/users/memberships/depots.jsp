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
DepotAdminMembershipsDisplayContext depotAdminMembershipsDisplayContext = new DepotAdminMembershipsDisplayContext(liferayPortletRequest);

currentURLObj.setParameter("historyKey", renderResponse.getNamespace() + "repositories");
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="repositories"
/>

<liferay-ui:membership-policy-error />

<h3 class="autofit-row sheet-subtitle">
	<span class="autofit-col autofit-col-expand">
		<span class="heading-text"><liferay-ui:message key="repositories" /></span>
	</span>
</h3>

<liferay-util:buffer
	var="removeGroupIcon"
>
	<liferay-ui:icon
		icon="times-circle"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<aui:input name="addGroupIds" type="hidden" />
<aui:input name="deleteGroupIds" type="hidden" />

<liferay-ui:search-container
	compactEmptyResultsMessage="<%= true %>"
	cssClass="lfr-search-container-sites"
	curParam="depotsCur"
	emptyResultsMessage="this-user-does-not-belong-to-a-repository"
	headerNames="name,roles,null"
	iteratorURL="<%= currentURLObj %>"
	total="<%= depotAdminMembershipsDisplayContext.getDepotsCount() %>"
>
	<liferay-ui:search-container-results
		results="<%= depotAdminMembershipsDisplayContext.getDepots(searchContainer.getStart(), searchContainer.getEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.Group"
		escapedModel="<%= true %>"
		keyProperty="groupId"
		modelVar="group"
		rowIdProperty="friendlyURL"
	>
		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			name="name"
		/>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			name="roles"
			value="<%= HtmlUtil.escape(depotAdminMembershipsDisplayContext.getRoles(group)) %>"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>
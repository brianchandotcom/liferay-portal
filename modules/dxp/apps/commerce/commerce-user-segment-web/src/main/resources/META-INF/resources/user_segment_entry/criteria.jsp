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
CommerceUserSegmentDisplayContext commerceUserSegmentDisplayContext = (CommerceUserSegmentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long commerceUserSegmentEntryId = commerceUserSegmentDisplayContext.getCommerceUserSegmentEntryId();
SearchContainer<CommerceUserSegmentCriterion> commerceUserSegmentCriteriaSearchContainer = commerceUserSegmentDisplayContext.getCommerceUserSegmentCriteriaSearchContainer();
PortletURL portletURL = commerceUserSegmentDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceUserSegmentCriteria"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-portlet:renderURL var="addCommerceUserSegmentCriterionURL">
			<portlet:param name="mvcRenderCommandName" value="editCommerceUserSegmentCriterion" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceUserSegmentEntryId" value="<%= String.valueOf(commerceUserSegmentEntryId) %>" />
		</liferay-portlet:renderURL>

		<liferay-frontend:add-menu
			inline="<%= true %>"
		>
			<liferay-frontend:add-menu-item
				title='<%= LanguageUtil.get(request, "add-criterion") %>'
				url="<%= addCommerceUserSegmentCriterionURL.toString() %>"
			/>
		</liferay-frontend:add-menu>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceUserSegmentCriteriaSearchContainer.getOrderByCol() %>"
			orderByType="<%= commerceUserSegmentCriteriaSearchContainer.getOrderByType() %>"
			orderColumns='<%= new String[] {"priority"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceUserSegmentCriteria();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<portlet:actionURL name="editCommerceUserSegmentCriterion" var="editCommerceUserSegmentCriterionActionURL" />

<aui:form action="<%= editCommerceUserSegmentCriterionActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="deleteCommerceUserSegmentCriterionIds" type="hidden" />

	<liferay-ui:search-container
		searchContainer="<%= commerceUserSegmentCriteriaSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion"
			keyProperty="commerceUserSegmentCriterionId"
			modelVar="commerceUserSegmentCriterion"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="criterionType"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="condition"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="value"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="priority"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/user_segment_criterion_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	function <portlet:namespace />deleteCommerceUserSegmentCriteria() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-criteria" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('deleteCommerceUserSegmentCriterionIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	}
</aui:script>
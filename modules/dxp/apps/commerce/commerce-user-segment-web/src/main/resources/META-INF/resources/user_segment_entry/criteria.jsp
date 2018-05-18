<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceUserSegmentDisplayContext commerceUserSegmentDisplayContext = (CommerceUserSegmentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long commerceUserSegmentEntryId = commerceUserSegmentDisplayContext.getCommerceUserSegmentEntryId();
SearchContainer<CommerceUserSegmentCriterion> commerceUserSegmentCriteriaSearchContainer = commerceUserSegmentDisplayContext.getCommerceUserSegmentCriteriaSearchContainer();
PortletURL portletURL = commerceUserSegmentDisplayContext.getPortletURL();

portletURL.setParameter("mvcRenderCommandName", "editCommerceUserSegmentEntry");
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
		id="commerceUserSegmentCriteria"
		searchContainer="<%= commerceUserSegmentCriteriaSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion"
			keyProperty="commerceUserSegmentCriterionId"
			modelVar="commerceUserSegmentCriterion"
		>

			<%
			CommerceUserSegmentCriterionType commerceUserSegmentCriterionType = commerceUserSegmentDisplayContext.getCommerceUserSegmentCriterionType(commerceUserSegmentCriterion.getType());

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("mvcRenderCommandName", "editCommerceUserSegmentCriterion");
			rowURL.setParameter("redirect", currentURL);
			rowURL.setParameter("commerceUserSegmentCriterionId", String.valueOf(commerceUserSegmentCriterion.getCommerceUserSegmentCriterionId()));
			rowURL.setParameter("commerceUserSegmentEntryId", String.valueOf(commerceUserSegmentCriterion.getCommerceUserSegmentEntryId()));
			%>

			<liferay-ui:search-container-column-text
				cssClass="important table-cell-content"
				href="<%= rowURL %>"
				name="type"
				value="<%= HtmlUtil.escape(commerceUserSegmentCriterionType.getLabel(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="preview"
				value="<%= HtmlUtil.escape(commerceUserSegmentCriterionType.getPreview(commerceUserSegmentCriterion, 40)) %>"
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
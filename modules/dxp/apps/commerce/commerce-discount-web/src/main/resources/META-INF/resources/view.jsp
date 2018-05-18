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
CommerceDiscountDisplayContext commerceDiscountDisplayContext = (CommerceDiscountDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceDiscount> commerceDiscountSearchContainer = commerceDiscountDisplayContext.getSearchContainer();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceDiscounts"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			disabled="<%= true %>"
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceDiscountDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>

		<portlet:renderURL var="addCommerceDiscountURL">
			<portlet:param name="mvcRenderCommandName" value="editCommerceDiscount" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:renderURL>

		<liferay-frontend:add-menu
			inline="<%= true %>"
		>
			<liferay-frontend:add-menu-item
				title='<%= LanguageUtil.get(request, "add-discount") %>'
				url="<%= addCommerceDiscountURL.toString() %>"
			/>
		</liferay-frontend:add-menu>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceDiscountSearchContainer.getOrderByCol() %>"
			orderByType="<%= commerceDiscountSearchContainer.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date"} %>'
			portletURL="<%= commerceDiscountDisplayContext.getPortletURL() %>"
		/>

		<li>
			<aui:form action="<%= String.valueOf(commerceDiscountDisplayContext.getPortletURL()) %>" name="searchFm">
				<liferay-ui:input-search
					markupView="lexicon"
				/>
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceDiscounts();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<portlet:actionURL name="editCommerceDiscount" var="editCommerceDiscountActionURL" />

	<aui:form action="<%= editCommerceDiscountActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deleteCommerceDiscountIds" type="hidden" />

		<liferay-ui:search-container
			id="commerceDiscounts"
			searchContainer="<%= commerceDiscountSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.discount.model.CommerceDiscount"
				keyProperty="commerceDiscountId"
				modelVar="commerceDiscount"
			>
				<portlet:renderURL var="rowURL">
					<portlet:param name="mvcRenderCommandName" value="editCommerceDiscount" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="commerceDiscountId" value="<%= String.valueOf(commerceDiscount.getCommerceDiscountId()) %>" />
				</portlet:renderURL>

				<liferay-ui:search-container-column-text
					cssClass="important table-cell-content"
					href="<%= rowURL %>"
					property="title"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="active"
					value='<%= LanguageUtil.get(request, commerceDiscount.isActive() ? "yes" : "no") %>'
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/discount_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />deleteCommerceDiscounts() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-discounts" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('deleteCommerceDiscountIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	}
</aui:script>
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
UserPriceListQualificationTypeDisplayContext userPriceListQualificationTypeDisplayContext = (UserPriceListQualificationTypeDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePriceList commercePriceList = userPriceListQualificationTypeDisplayContext.getCommercePriceList();

CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel = userPriceListQualificationTypeDisplayContext.getCommercePriceListQualificationTypeRel();

long commercePriceListId = userPriceListQualificationTypeDisplayContext.getCommercePriceListId();
long commercePriceListQualificationTypeRelId = userPriceListQualificationTypeDisplayContext.getCommercePriceListQualificationTypeRelId();

SearchContainer<User> userSearchContainer = userPriceListQualificationTypeDisplayContext.getSearchContainer();

PortletURL portletURL = userPriceListQualificationTypeDisplayContext.getPortletURL();

PortletURL priceListQualificationTypesURL = priceListsURLObj;

priceListQualificationTypesURL.setParameter("mvcRenderCommandName", "editCommercePriceList");
priceListQualificationTypesURL.setParameter("commercePriceListId", String.valueOf(commercePriceListId));
priceListQualificationTypesURL.setParameter("screenNavigationCategoryKey", userPriceListQualificationTypeDisplayContext.getScreenNavigationCategoryKey());

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(priceListQualificationTypesURL.toString());

renderResponse.setTitle(commercePriceList.getName() + " - " + commercePriceListQualificationTypeRel.getCommercePriceListQualificationType());
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commercePriceListQualificationTypeUsers"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= userPriceListQualificationTypeDisplayContext.getOrderByCol() %>"
			orderByType="<%= userPriceListQualificationTypeDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommercePriceListUserRels();" %>' icon="times" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />priceListUsersContainer">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="className" type="hidden" value="<%= User.class.getName() %>" />
		<aui:input name="commercePriceListQualificationTypeRelId" type="hidden" value="<%= commercePriceListQualificationTypeRelId %>" />
		<aui:input name="deleteClassPKs" type="hidden" />

		<div class="price-list-users-container" id="<portlet:namespace />entriesContainer">
			<liferay-ui:search-container
				id="commercePriceListQualificationTypeUsers"
				iteratorURL="<%= portletURL %>"
				searchContainer="<%= userSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.portal.kernel.model.User"
					cssClass="entry-display-style"
					keyProperty="userId"
					modelVar="user"
				>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="name"
						value="<%= HtmlUtil.escape(user.getFullName()) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="screen-name"
						property="screenName"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
					>
						<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
							<portlet:actionURL name="editCommercePriceListUserRel" var="deleteURL">
								<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="className" value="<%= User.class.getName() %>" />
								<portlet:param name="classPK" value="<%= String.valueOf(user.getUserId()) %>" />
								<portlet:param name="commercePriceListQualificationTypeRelId" value="<%= String.valueOf(commercePriceListQualificationTypeRelId) %>" />
							</portlet:actionURL>

							<liferay-ui:icon
								message="delete"
								url="<%= deleteURL %>"
							/>
						</liferay-ui:icon-menu>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator markupView="lexicon" searchContainer="<%= userSearchContainer %>" />
			</liferay-ui:search-container>
		</div>
	</aui:form>
</div>

<portlet:actionURL name="editCommercePriceListUserRel" var="addCommercePriceListUserRelURL" />

<aui:form action="<%= addCommercePriceListUserRelURL %>" cssClass="hide" name="addCommercePriceListUserRelFm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD_MULTIPLE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="addClassPKs" type="hidden" value="" />
	<aui:input name="className" type="hidden" value="<%= User.class.getName() %>" />
	<aui:input name="commercePriceListQualificationTypeRelId" type="hidden" value="<%= commercePriceListQualificationTypeRelId %>" />
</aui:form>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item id="addCommercePriceListUserRel" title='<%= LanguageUtil.get(request, "add-user") %>' url="javascript:;" />
</liferay-frontend:add-menu>

<aui:script>
	function <portlet:namespace />deleteCommercePriceListUserRels() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-users" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('deleteClassPKs').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="editCommercePriceListUserRel" />');
		}
	}
</aui:script>

<aui:script use="liferay-item-selector-dialog">
	$('#<portlet:namespace />addCommercePriceListUserRel').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'usersSelectItem',
					on: {
						selectedItemChange: function(event) {
							var selectedItems = event.newVal;

							if (selectedItems) {
								$('#<portlet:namespace />addClassPKs').val(selectedItems);

								var addCommercePriceListUserRelFm = $('#<portlet:namespace />addCommercePriceListUserRelFm');

								submitForm(addCommercePriceListUserRelFm);
							}
						}
					},
					title: '<liferay-ui:message arguments="<%= commercePriceList.getName() %>" key="add-user-to-x" />',
					url: '<%= userPriceListQualificationTypeDisplayContext.getItemSelectorUrl() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>
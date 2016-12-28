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

<liferay-ui:icon
	id="copyApplications"
	message="copy-applications"
	url="javascript:;"
/>

<%
Group selGroup = (Group)request.getAttribute(WebKeys.GROUP);

PortletURL redirectURL = layoutsAdminDisplayContext.getRedirectURL();
%>

<portlet:actionURL name="copyApplications" var="copyApplicationsURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
</portlet:actionURL>

<aui:form action='<%= HttpUtil.addParameter(copyApplicationsURL, "refererPlid", plid) %>' name="copyApplicationsFm">
	<aui:input name="redirect" type="hidden" value='<%= HttpUtil.addParameter(redirectURL.toString(), liferayPortletResponse.getNamespace() + "selPlid", layoutsAdminDisplayContext.getSelPlid()) %>' />
	<aui:input name="groupId" type="hidden" value="<%= selGroup.getGroupId() %>" />
	<aui:input name="selPlid" type="hidden" value="<%= layoutsAdminDisplayContext.getSelPlid() %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= layoutsAdminDisplayContext.isPrivateLayout() %>" />
	<aui:input name="layoutId" type="hidden" value="<%= layoutsAdminDisplayContext.getLayoutId() %>" />
</aui:form>

<aui:script use="liferay-item-selector-dialog">
	<portlet:renderURL var="selectPageURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcPath" value="/configuration/icon/select_page.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="plid" value="<%= String.valueOf(layoutsAdminDisplayContext.getSelPlid()) %>" />
	</portlet:renderURL>

	$('#<portlet:namespace />copyApplications').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: '<portlet:namespace />selectPage',
					on: {
						selectedItemChange: function(event) {
							var selectedItem = event.newVal;

							if (selectedItem) {
								var form = A.one('#<portlet:namespace />copyApplicationsFm');

								form.append(selectedItem);

								submitForm(form);
							}
						}
					},
					strings: {
						add: '<liferay-ui:message key="done" />',
						cancel: '<liferay-ui:message key="cancel" />'
					},
					title: '<liferay-ui:message key="copy-applications" />',
					url: '<%= selectPageURL %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>
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
CommerceDataIntegrationProcessDisplayContext commerceDataIntegrationProcessDisplayContext = (CommerceDataIntegrationProcessDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId='<%= ParamUtil.getString(request, "searchContainerId", "commerceDataIntegrationProcesses") %>'
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceDataIntegrationProcessDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>

		<liferay-portlet:renderURL var="addProcessURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
			<portlet:param name="mvcRenderCommandName" value="editCommerceDataIntegrationProcess" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:renderURL>

		<liferay-frontend:add-menu
			inline="<%= true %>"
		>

			<%
			for (ProcessType processType : commerceDataIntegrationProcessDisplayContext.getProcessTypes()) {
			%>

				<liferay-portlet:renderURL var="addProcessURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
					<portlet:param name="mvcRenderCommandName" value="editCommerceDataIntegrationProcess" />
					<portlet:param name="backURL" value="<%= currentURL %>" />
					<portlet:param name="processType" value="<%= processType.getKey() %>" />
				</liferay-portlet:renderURL>

				<liferay-frontend:add-menu-item
					title="<%= processType.getLabel(locale) %>"
					url="<%= addProcessURL %>"
				/>

			<%
			}
			%>

		</liferay-frontend:add-menu>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= commerceDataIntegrationProcessDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + liferayPortletResponse.getNamespace() + "deleteCommerceDataIntegrationProcesses();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<aui:script>
	function <portlet:namespace />deleteCommerceDataIntegrationProcesses() {
		if (
			confirm(
				'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-processes" />'
			)
		) {
			var form = window.document['<portlet:namespace />fm'];

			form.setAttribute('method', 'post');
			form['<portlet:namespace /><%= Constants.CMD %>'].value =
				'<%= Constants.DELETE %>';
			form[
				'<portlet:namespace />deleteCDataIntegrationProcessIds'
			].value = Liferay.Util.listCheckedExcept(
				form,
				'<portlet:namespace />allRowIds'
			);

			submitForm(
				form,
				'<portlet:actionURL name="editCommerceDataIntegrationProcess" />'
			);
		}
	}
</aui:script>
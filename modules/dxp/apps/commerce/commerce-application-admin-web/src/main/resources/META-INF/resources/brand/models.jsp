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
CommerceApplicationAdminDisplayContext commerceApplicationAdminDisplayContext = (CommerceApplicationAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceApplicationModels"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= commerceApplicationAdminDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceApplicationAdminDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceApplicationAdminDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"name", "year"} %>'
			portletURL="<%= commerceApplicationAdminDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceApplicationAdminDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>

		<c:if test="<%= commerceApplicationAdminDisplayContext.hasModelPermissions(CommerceApplicationActionKeys.ADD_COMMERCE_MODEL) %>">
			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<portlet:renderURL var="addCommerceApplicationModelURL">
					<portlet:param name="mvcRenderCommandName" value="/commerce_application_admin/edit_commerce_application_model" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="commerceApplicationBrandId" value="<%= String.valueOf(commerceApplicationAdminDisplayContext.getCommerceApplicationBrandId()) %>" />
				</portlet:renderURL>

				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(request, "add-model") %>'
					url="<%= addCommerceApplicationModelURL.toString() %>"
				/>
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + liferayPortletResponse.getNamespace() + "deleteCommerceApplicationModels();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<portlet:actionURL name="/commerce_application_admin/edit_commerce_application_model" var="editCommerceApplicationModelActionURL" />

<div class="container-fluid container-fluid-max-xl" id="<portlet:namespace />commerceApplicationModelContainer">
	<aui:form action="<%= editCommerceApplicationModelActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceApplicationBrandId" type="hidden" value="<%= commerceApplicationAdminDisplayContext.getCommerceApplicationBrandId() %>" />
		<aui:input name="deleteCommerceApplicationModelIds" type="hidden" />

		<liferay-ui:search-container
			id="commerceApplicationModels"
			searchContainer="<%= commerceApplicationAdminDisplayContext.getCommerceApplicationModelSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.application.model.CommerceApplicationModel"
				keyProperty="commerceApplicationModelId"
				modelVar="commerceApplicationModel"
			>

				<%
				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcRenderCommandName", "/commerce_application_admin/edit_commerce_application_model");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("commerceApplicationBrandId", String.valueOf(commerceApplicationModel.getCommerceApplicationBrandId()));
				rowURL.setParameter("commerceApplicationModelId", String.valueOf(commerceApplicationModel.getCommerceApplicationModelId()));
				%>

				<liferay-ui:search-container-column-text
					cssClass="important table-cell-expand"
					href="<%= rowURL %>"
					property="name"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					property="year"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/application_model_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="list"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />deleteCommerceApplicationModels() {
		if (
			confirm(
				'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-models" />'
			)
		) {
			var form = window.document['<portlet:namespace />fm'];

			form[
				'<portlet:namespace />deleteCommerceApplicationModelIds'
			].value = Liferay.Util.listCheckedExcept(
				form,
				'<portlet:namespace />allRowIds'
			);

			submitForm(form);
		}
	}
</aui:script>
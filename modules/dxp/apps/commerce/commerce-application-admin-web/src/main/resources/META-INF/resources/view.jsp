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

renderResponse.setTitle(LanguageUtil.get(request, "applications"));
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceApplicationBrands"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= commerceApplicationAdminDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceApplicationAdminDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceApplicationAdminDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= commerceApplicationAdminDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceApplicationAdminDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>

		<c:if test="<%= commerceApplicationAdminDisplayContext.hasBrandPermissions(CommerceApplicationActionKeys.ADD_COMMERCE_BRAND) %>">
			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					id="addApplicationBrandButton"
					title='<%= LanguageUtil.get(request, "add-brand") %>'
					url="javascript:;"
				/>
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + liferayPortletResponse.getNamespace() + "deleteCommerceApplicationBrands();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<portlet:actionURL name="/commerce_application_admin/edit_commerce_application_brand" var="editCommerceApplicationBrandActionURL" />

<div class="container-fluid container-fluid-max-xl" id="<portlet:namespace />commerceApplicationBrandContainer">
	<aui:form action="<%= editCommerceApplicationBrandActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deleteCommerceApplicationBrandIds" type="hidden" />

		<liferay-ui:search-container
			id="commerceApplicationBrands"
			searchContainer="<%= commerceApplicationAdminDisplayContext.getCommerceApplicationBrandSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.application.model.CommerceApplicationBrand"
				keyProperty="commerceApplicationBrandId"
				modelVar="commerceApplicationBrand"
			>

				<%
				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcRenderCommandName", "/commerce_application_admin/edit_commerce_application_brand");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("commerceApplicationBrandId", String.valueOf(commerceApplicationBrand.getCommerceApplicationBrandId()));
				%>

				<liferay-ui:search-container-column-text
					cssClass="important table-cell-expand"
					href="<%= rowURL %>"
					property="name"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/application_brand_action.jsp"
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
	function <portlet:namespace />deleteCommerceApplicationBrands() {
		if (
			confirm(
				'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-brands" />'
			)
		) {
			var form = window.document['<portlet:namespace />fm'];

			form[
				'<portlet:namespace />deleteCommerceApplicationBrandIds'
			].value = Liferay.Util.listCheckedExcept(
				form,
				'<portlet:namespace />allRowIds'
			);

			submitForm(form);
		}
	}
</aui:script>

<c:if test="<%= commerceApplicationAdminDisplayContext.hasBrandPermissions(CommerceApplicationActionKeys.ADD_COMMERCE_BRAND) %>">
	<portlet:actionURL name="/commerce_application_admin/edit_commerce_application_brand" var="editCommerceApplicationBrandActionURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:actionURL>

	<aui:script require="frontend-js-web/liferay/delegate/delegate.es as delegateModule,frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es as modalCommands">
		var delegate = delegateModule.default;

		var handleAddApplicationBrandButtonClick = delegate(
			document.body,
			'click',
			'#<portlet:namespace />addApplicationBrandButton',
			function (event) {
				modalCommands.openSimpleInputModal({
					dialogTitle: '<liferay-ui:message key="add-brand" />',
					formSubmitURL: '<%= editCommerceApplicationBrandActionURL %>',
					mainFieldLabel: '<liferay-ui:message key="name" />',
					mainFieldName: 'name',
					mainFieldPlaceholder: '<liferay-ui:message key="name" />',
					namespace: '<portlet:namespace />',
					spritemap:
						'<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg',
				});
			}
		);

		function handleDestroyPortlet() {
			handleAddApplicationBrandButtonClick.dispose();

			Liferay.detach('destroyPortlet', handleDestroyPortlet);
		}

		Liferay.on('destroyPortlet', handleDestroyPortlet);
	</aui:script>
</c:if>
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

<%@ include file="/admin/init.jsp" %>

<%
String displayStyle = ddmFormAdminDisplayContext.getDisplayStyle();
PortletURL portletURL = ddmFormAdminDisplayContext.getPortletURL();

FormInstancePermissionCheckerHelper formInstancePermissionCheckerHelper = ddmFormAdminDisplayContext.getPermissionCheckerHelper();
%>

<div class="container-fluid-1280" id="<portlet:namespace />formContainer">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="searchContainerForm">
		<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
		<aui:input name="deleteFormInstanceIds" type="hidden" />

		<liferay-ui:search-container
			id="<%= ddmFormAdminDisplayContext.getSearchContainerId() %>"
			searchContainer="<%= ddmFormAdminDisplayContext.getSearch() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.mapping.model.DDMFormInstance"
				cssClass="entry-display-style"
				keyProperty="formInstanceId"
				modelVar="formInstance"
			>
				<portlet:renderURL var="rowURL">
					<portlet:param name="mvcRenderCommandName" value="/admin/edit_form_instance" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="formInstanceId" value="<%= String.valueOf(formInstance.getFormInstanceId()) %>" />
					<portlet:param name="displayStyle" value="<%= displayStyle %>" />
				</portlet:renderURL>

				<%
				if (!formInstancePermissionCheckerHelper.isShowEditIcon(formInstance)) {
					rowURL = null;
				}
				%>

				<c:choose>
					<c:when test='<%= displayStyle.equals("descriptive") %>'>
						<liferay-ui:search-container-column-icon
							cssClass="asset-icon"
							icon="forms"
						/>

						<liferay-ui:search-container-column-jsp
							colspan="<%= 2 %>"
							href="<%= rowURL %>"
							path="/admin/view_form_instance_descriptive.jsp"
						/>

						<liferay-ui:search-container-column-jsp
							path="/admin/form_instance_action.jsp"
						/>
					</c:when>
					<c:otherwise>
						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand table-title"
							href="<%= rowURL %>"
							name="name"
							value="<%= HtmlUtil.escape(formInstance.getName(locale)) %>"
						/>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand"
							name="description"
							value="<%= HtmlUtil.escape(formInstance.getDescription(locale)) %>"
						/>

						<liferay-ui:search-container-column-date
							cssClass="table-cell-expand-smaller"
							name="modified-date"
							value="<%= formInstance.getModifiedDate() %>"
						/>

						<liferay-ui:search-container-column-jsp
							path="/admin/form_instance_action.jsp"
						/>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="<%= displayStyle %>"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script require='<%= mainRequire + "/admin/js/components/ShareFormPopover/ShareFormPopover.es as ShareFormPopover" %>'>
	var spritemap = themeDisplay.getPathThemeImages() + '/lexicon/icons.svg';

	Liferay.after('<portlet:namespace />copyFormURL', function(data) {
		var url = data.url;
		var trigger = Liferay.Menu._INSTANCE._activeTrigger;

		var popover = new ShareFormPopover.default({
			alignElement: trigger.getDOM(),
			events: {
				popoverClosed: function() {
					popover.dispose();
				}
			},
			spritemap: spritemap,
			url: url,
			visible: true
		});
	});
</aui:script>

<%@ include file="/admin/copy_form_publish_url.jspf" %>

<%@ include file="/admin/export_form_instance.jspf" %>
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
String displayStyle = ddlFormAdminDisplayContext.getDisplayStyle();

PortletURL portletURL = ddlFormAdminDisplayContext.getPortletURL();

portletURL.setParameter("displayStyle", displayStyle);
%>

<liferay-util:include page="/admin/search_bar.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/admin/toolbar.jsp" servletContext="<%= application %>" />

<div class="container-fluid-1280" id="<portlet:namespace />formContainer">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="searchContainerForm">
		<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
		<aui:input name="deleteRecordSetIds" type="hidden" />

		<liferay-ui:search-container
			id="ddlRecordSet"
			rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
			searchContainer="<%= ddlFormAdminDisplayContext.getRecordSetSearch() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.lists.model.DDLRecordSet"
				cssClass="entry-display-style"
				keyProperty="recordSetId"
				modelVar="recordSet"
			>
				<portlet:renderURL var="rowURL">
					<portlet:param name="mvcPath" value="/admin/edit_record_set.jsp" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
					<portlet:param name="displayStyle" value="<%= displayStyle %>" />
				</portlet:renderURL>

				<c:choose>
					<c:when test='<%= displayStyle.equals("descriptive") %>'>
						<liferay-ui:search-container-column-icon
							cssClass="asset-icon"
							icon="forms"
						/>

						<liferay-ui:search-container-column-jsp
							colspan="<%= 2 %>"
							href="<%= rowURL %>"
							path="/admin/view_record_set_descriptive.jsp"
						/>

						<liferay-ui:search-container-column-jsp
							path="/admin/record_set_action.jsp"
						/>
					</c:when>
					<c:otherwise>
						<liferay-ui:search-container-column-text
							cssClass="table-cell-content"
							href="<%= rowURL %>"
							name="name"
							value="<%= HtmlUtil.escape(recordSet.getName(locale)) %>"
						/>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-content"
							name="description"
							value="<%= HtmlUtil.escape(recordSet.getDescription(locale)) %>"
						/>

						<liferay-ui:search-container-column-date
							name="modified-date"
							value="<%= recordSet.getModifiedDate() %>"
						/>

						<liferay-ui:search-container-column-jsp
							path="/admin/record_set_action.jsp"
						/>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
		</liferay-ui:search-container>
	</aui:form>
</div>

<c:if test="<%= ddlFormAdminDisplayContext.isShowAddRecordSetButton() %>">
	<portlet:renderURL var="addRecordSetURL">
		<portlet:param name="mvcPath" value="/admin/edit_record_set.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "new-form") %>' url="<%= addRecordSetURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>

<%@ include file="/admin/export_record_set.jspf" %>

<%@ include file="/admin/copy_form_publish_url.jspf" %>

<aui:script use="liferay-ddl-form-builder-copy-publish-form-url-popover">
	var A = AUI();

	var copyPublishFormURLPopover = new Liferay.DDL.FormBuilderCopyPublishFormURLPopover(
		{
			portletNamespace: '<portlet:namespace />'
		}
	);

	Liferay.on(
		'<portlet:namespace />copyFormURL',
		function(event) {

			if (copyPublishFormURLPopover.isVisible()) {
				copyPublishFormURLPopover.hide();
			}

			copyPublishFormURLPopover.set('publishURL', event.url);

			copyPublishFormURLPopover.setAlign(
				{
					node: Liferay.Menu._INSTANCE._activeTrigger,
					points: [A.WidgetPositionAlign.TR, A.WidgetPositionAlign.TR]
				}
			);

			copyPublishFormURLPopover.show();
		}
	);

	A.all('.searchcontainer-content .dropdown-toggle.icon-monospaced').before(
		'click',
		function() {
			copyPublishFormURLPopover.hide();
		}
	);

	Liferay.on(
		'destroyPortlet',
		function() {
			copyPublishFormURLPopover.destroy();
		}
	);
</aui:script>
<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/dynamic_data_lists/init.jsp" %>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/dynamic_data_lists/view");
%>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />

	<liferay-ui:search-container
		searchContainer="<%= new RecordSetSearch(renderRequest, portletURL) %>"
	>

		<%
		RecordSetDisplayTerms displayTerms = (RecordSetDisplayTerms)searchContainer.getDisplayTerms();
		RecordSetSearchTerms searchTerms = (RecordSetSearchTerms)searchContainer.getSearchTerms();
		%>

		<liferay-util:include page="/html/portlet/dynamic_data_lists/toolbar.jsp">
			<liferay-util:param name="toolbarItem" value="view-all" />
		</liferay-util:include>

		<liferay-ui:search-container-results>
			<%@ include file="/html/portlet/dynamic_data_lists/record_set_search_results.jspf" %>
		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.dynamicdatalists.model.DDLRecordSet"
			escapedModel="<%= true %>"
			keyProperty="recordSetId"
			modelVar="recordSet"
		>
			<liferay-portlet:renderURL varImpl="rowURL">
				<portlet:param name="struts_action" value="/dynamic_data_lists/view_record_set" />
				<portlet:param name="redirect" value="<%= searchContainer.getIteratorURL().toString() %>" />
				<portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" />
			</liferay-portlet:renderURL>

			<%
			if (!DDLRecordSetPermission.contains(permissionChecker, recordSet, ActionKeys.VIEW)) {
				rowURL = null;
			}
			%>

			<%@ include file="/html/portlet/dynamic_data_lists/search_columns.jspf" %>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/html/portlet/dynamic_data_lists/record_set_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<div class="separator"><!-- --></div>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<div class="hide" id="<portlet:namespace />export-list">
	<aui:select label="file-extension" name="fileExtension">
		<aui:option value="csv">CSV</aui:option>
		<aui:option value="xml">XML</aui:option>
	</aui:select>
</div>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />exportRecordSet',
		function(url) {
			var A = AUI();

			var form = A.Node.create('<form />');

			form.setAttribute('method', 'POST');

			var content = A.one('#<portlet:namespace />export-list');

			if (content) {
				form.append(content);
				content.show();
			}

			var dialog = Liferay.Util.Window.getWindow(
				{
					dialog: {
						bodyContent: form,
						toolbars: {
							footer: [
								{
									label: '<%= UnicodeLanguageUtil.get(pageContext, "ok") %>',
									on: {
										click: function() {
											submitForm(form, url, false);
										}
									}
								},
								{
									label: '<%= UnicodeLanguageUtil.get(pageContext, "cancel") %>',
									on: {
										click: function() {
											dialog.hide();
										}
									}
								}
							]
						}
					},
					title: '<%= UnicodeLanguageUtil.get(pageContext, "export") %>'
				}
			);

		},
		['liferay-util-window']
	);
</aui:script>
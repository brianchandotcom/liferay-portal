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

<portlet:renderURL var="searchSourcesURL">
	<portlet:param name="mvcPath" value="/admin/view.jsp" />
	<portlet:param name="tabs1" value="sources" />
</portlet:renderURL>

<%
String backURL = ParamUtil.getString(request, "backURL", searchSourcesURL);

Source source = (Source)request.getAttribute(ReportsEngineWebKeys.SOURCE);

String name = BeanParamUtil.getString(source, request, "name");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle((source != null) ? LanguageUtil.format(request, "edit-x", source.getName(locale), false) : LanguageUtil.get(request, "new-data-source"));
%>

<portlet:actionURL name="editDataSource" var="actionURL">
	<portlet:param name="mvcPath" value="/admin/data_source/edit_data_source.jsp" />
	<portlet:param name="redirect" value="<%= searchSourcesURL %>" />
</portlet:actionURL>

<aui:form action="<%= actionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<liferay-ui:error exception="<%= SourceDriverClassNameException.class %>" message="please-enter-a-valid-data-source-driver" />
	<liferay-ui:error exception="<%= SourceJDBCConnectionException.class %>" message="could-not-connect-to-the-database.-please-verify-that-the-settings-are-correct" />
	<liferay-ui:error exception="<%= SourceTypeException.class %>" message="please-enter-a-valid-data-source-type" />

	<aui:model-context bean="<%= source %>" model="<%= Source.class %>" />

	<aui:input name="sourceId" type="hidden" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<div class="form-group">
				<aui:input name="name" required="<%= true %>" />

				<aui:input label="jdbc-driver-class-name" name="driverClassName" required="<%= true %>" />

				<aui:input label="jdbc-url" name="driverUrl" required="<%= true %>" />

				<aui:input autocomplete="off" label="jdbc-user-name" name="driverUserName" required="<%= true %>" />

				<aui:input autocomplete="off" label="jdbc-password" name="driverPassword" type="password" />
			</div>
		</aui:fieldset>

		<c:if test="<%= source == null %>">
			<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= Source.class.getName() %>"
				/>
			</aui:fieldset>
		</c:if>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" name="testDatabaseConnectionButton" onClick='<%= renderResponse.getNamespace() + "testDatabaseConnection();" %>' value="test-database-connection" />

		<aui:button cssClass="btn-lg" href="<%= searchSourcesURL %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />testDatabaseConnection',
		function() {
			var A = AUI();

			var url = '<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcPath" value="/admin/data_source/test_database_connection.jsp" /></portlet:renderURL>';

			var data = {};

			<c:if test="<%= source != null %>">
				data.<portlet:namespace />sourceId = document.<portlet:namespace />fm['<portlet:namespace />sourceId'].value;
			</c:if>

			data.<portlet:namespace />driverClassName = document.<portlet:namespace />fm['<portlet:namespace />driverClassName'].value;
			data.<portlet:namespace />driverUrl = document.<portlet:namespace />fm['<portlet:namespace />driverUrl'].value;
			data.<portlet:namespace />driverUserName = document.<portlet:namespace />fm['<portlet:namespace />driverUserName'].value;
			data.<portlet:namespace />driverPassword = document.<portlet:namespace />fm['<portlet:namespace />driverPassword'].value;

			if (url != null) {
				var databaseConnectionModal = Liferay.Util.Window.getWindow(
					{
						dialog: {
							centered: true,
							destroyOnHide: true,
							height: 300,
							hideOn: [],
							modal: true,
							resizable: false,
							toolbars: {
								footer: [
									{
										cssClass: 'btn-lg btn-primary',
										label: '<liferay-ui:message key="close" />',
										on: {
											click: function() {
												databaseConnectionModal.hide();
											}
										}
									}
								],
								header: [
									{
										cssClass: 'close',
										discardDefaultButtonCssClasses: true,
										labelHTML: Liferay.Util.getLexiconIconTpl('times'),
										on: {
											click: function() {
												databaseConnectionModal.hide();
											}
										}
									}
								]
							},
							width: 600
						},
						title: '<liferay-ui:message key="source" />'
					}
				);

				databaseConnectionModal.render();

				A.io.request(
					url,
					{
						after: {
							success: function() {
								var response = this.get('responseData');

								databaseConnectionModal.bodyNode.append(response);
								databaseConnectionModal.show();
							}
						},
						data: data
					}
				);
			}
		},
		['aui-dialog', 'aui-io']
	);
</aui:script>
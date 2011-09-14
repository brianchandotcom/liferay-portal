<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
<%@ include file="/html/common/init.jsp" %>

<%@ page import="com.liferay.taglib.aui.ScriptTag" %>

<aui:form action="/c/portal/setup_wizard" name="fm" method="post">
	<aui:input type="hidden" name="<%= Constants.CMD %>" value="<%= Constants.EDIT %>" />

	<aui:fieldset cssClass="fieldset-header" label="portal">
		<aui:input label="portal-name" name='<%= "properties--" + PropsKeys.COMPANY_DEFAULT_WEB_ID + "--" %>' value="<%= PropsValues.COMPANY_DEFAULT_WEB_ID %>" />

		<%
		String domain = " liferay.com";
		%>
		<span class="tooltip"><liferay-ui:message key="for-example-x" arguments="<%= domain %>" /></span>
	</aui:fieldset>

	<aui:fieldset cssClass="fieldset-header" label="administrator-user">
		<aui:input label="first-name" name='<%= "properties--" + PropsKeys.DEFAULT_ADMIN_FIRST_NAME + "--" %>' value="<%= PropsValues.DEFAULT_ADMIN_FIRST_NAME %>" />

		<aui:input label="last-name" name='<%= "properties--" + PropsKeys.DEFAULT_ADMIN_LAST_NAME + "--" %>' value="<%= PropsValues.DEFAULT_ADMIN_LAST_NAME %>" />

		<aui:input label="email" name='<%= "properties--" + PropsKeys.ADMIN_EMAIL_FROM_ADDRESS + "--" %>' value="<%= PropsUtil.get(PropsKeys.ADMIN_EMAIL_FROM_ADDRESS) %>" />
	</aui:fieldset>

	<aui:fieldset cssClass="fieldset-header" label="database">

		<%
		String[] drivers = PropsUtil.getArray(PropsKeys.SETUP_DATABASE_DRIVERS_LIST);

		String jdbcDefaultUrl = PropsUtil.get(PropsKeys.JDBC_DEFAULT_URL);
		%>

		<aui:input name="defaultDatabase" type="hidden" value='<%= jdbcDefaultUrl.indexOf("hsqldb") != -1 %>' />
		<div id="defaultDatabaseOptions">
			<strong><liferay-ui:message key="default-database-hypersonic" /></strong>. <liferay-ui:message key="this-database-is-useful-for-development" />

			<a href="javascript;" id="changeDataBaseLink">(<liferay-ui:message key="change" />)</a>
		  </div>

		<div class="aui-helper-hidden" id="customDatabaseOptions">
			<a href="javascript;" id="defaultDatabaseOptionsLink">&laquo; <liferay-ui:message key="use-default-database" /></a>

			<aui:select name="databaseType">
			<%
			for (String driver : drivers) {
			%>
				<aui:option selected="<%= jdbcDefaultUrl.indexOf(driver) != -1 %>" label="<%= driver %>" />
			<%
			}
			%>
			</aui:select>

			<span id="tooltip" class="aui-helper-hidden tooltip"><liferay-ui:message key="in-order-to-use-this-database" /></span>

			<aui:input name="databaseName" value="lportal" />

			<aui:input label="user-name" name='<%= "properties--" + PropsKeys.JDBC_DEFAULT_USERNAME + "--" %>' value="<%= PropsUtil.get(PropsKeys.JDBC_DEFAULT_USERNAME) %>" />

			<aui:input label="password" name='<%= "properties--" + PropsKeys.JDBC_DEFAULT_PASSWORD + "--" %>' value="<%= PropsUtil.get(PropsKeys.JDBC_DEFAULT_PASSWORD) %>" />
		</div>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" value="finish-configuration" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base">
	var databaseSelector = A.one('#databaseType');
	var defaultDatabaseOptions = A.one('#defaultDatabaseOptions');
	var customDatabaseOptions = A.one('#customDatabaseOptions');
	var tooltip = A.one('#tooltip');
	var changeDataBaseLink = A.one('#changeDataBaseLink');
	var defaultDatabaseOptionsLink = A.one('#defaultDatabaseOptionsLink');
	var defaultDatabase = A.one('#defaultDatabase');

	if (databaseSelector.get('value') != 'hsqldb') {
		defaultDatabaseOptions.hide();

		customDatabaseOptions.show();
	}

	changeDataBaseLink.on(
		'click',
	        function(event) {
				event.preventDefault();

				defaultDatabaseOptions.hide();

				customDatabaseOptions.show();

				defaultDatabase.val('false');
	        }
	);

	defaultDatabaseOptionsLink.on(
		'click',
			function(event) {
				event.preventDefault();

				defaultDatabaseOptions.show();

				customDatabaseOptions.hide();

				defaultDatabase.val('true');
	        }
	);

	var onChangeDatabaseSelector = function() {
		var value = databaseSelector.get('value');

	    	if ((value == 'hsqldb') || (value == 'mysql') || (value == 'postgresql')) {
				tooltip.hide();
	        }
	        else {
				tooltip.show();
	        }
	}

	onChangeDatabaseSelector();

	databaseSelector.on('change', onChangeDatabaseSelector);
</aui:script>

<%
ScriptTag.flushScriptData(pageContext);
%>
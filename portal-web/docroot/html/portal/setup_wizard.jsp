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

<%@ include file="/html/portal/init.jsp" %>

<%@ page import="com.liferay.portal.setup.SetupWizardUtil" %>

<%
UnicodeProperties unicodeProperties = (UnicodeProperties)session.getAttribute(WebKeys.SETUP_WIZARD_PROPERTIES);

boolean propertiesFileUpdated = GetterUtil.getBoolean((Boolean)session.getAttribute(WebKeys.SETUP_WIZARD_PROPERTIES_UPDATED));

boolean passwordUpdated = GetterUtil.getBoolean((Boolean)session.getAttribute(WebKeys.SETUP_WIZARD_PASSWORD_UPDATED));
%>

<style>
	<%@ include file="/html/portal/setup_wizard_css.jspf" %>
</style>

<div id="wrapper">
	<header id="banner" role="banner">
		<hgroup id="heading">
			<h1 class="company-title">
				<span class="logo" title="<liferay-ui:message key="welcome-to-liferay" />">
					<liferay-ui:message key="welcome-to-liferay" />
				</span>
			</h1>

			<h2 class="site-title">
				<span><liferay-ui:message key="basic-configuration" /></span>
			</h2>
		</hgroup>
	</header>

	<div id="content">
		<div id="main-content">
			<c:choose>
				<c:when test="<%= !propertiesFileUpdated && !SetupWizardUtil.isSetupFinished(request) %>">
					<aui:form action='<%= themeDisplay.getPathMain() + "/portal/setup_wizard" %>' method="post" name="fm">
						<aui:input type="hidden" name="<%= Constants.CMD %>" value="<%= Constants.UPDATE %>" />

						<aui:fieldset column="<%= true %>" cssClass="aui-w45" label="portal">
							<aui:input label="portal-name" name='<%= "properties--" + PropsKeys.COMPANY_DEFAULT_WEB_ID + "--" %>' value="<%= PropsValues.COMPANY_DEFAULT_WEB_ID %>" />

							<span class="aui-field-hint">
								<liferay-ui:message arguments='<%= "liferay.com" %>' key="for-example-x" />
							</span>
						</aui:fieldset>

						<aui:fieldset column="<%= true %>" cssClass="aui-column-last aui-w50" label="administrator-user">
							<aui:input label="first-name" name='<%= "properties--" + PropsKeys.DEFAULT_ADMIN_FIRST_NAME + "--" %>' value="<%= PropsValues.DEFAULT_ADMIN_FIRST_NAME %>" />

							<aui:input label="last-name" name='<%= "properties--" + PropsKeys.DEFAULT_ADMIN_LAST_NAME + "--" %>' value="<%= PropsValues.DEFAULT_ADMIN_LAST_NAME %>" />

							<aui:input label="email" name='<%= "properties--" + PropsKeys.ADMIN_EMAIL_FROM_ADDRESS + "--" %>' value="<%= PropsValues.ADMIN_EMAIL_FROM_ADDRESS %>" />
						</aui:fieldset>

						<aui:fieldset column="<%= true %>" cssClass="aui-w100" label="database">
							<aui:input name="defaultDatabase" type="hidden" value='<%= PropsValues.JDBC_DEFAULT_URL.contains("hypersonic") %>' />

							<div id="defaultDatabaseOptions">
								<strong><liferay-ui:message key="default-database" /> (<liferay-ui:message key="database.hypersonic" />)</strong>
								<liferay-ui:message key="hypersonic-is-an-embedded-database-useful-for-development-and-demo'ing-purposes" />

								<a href="javascript;" id="customDatabaseOptionsLink">
									(<liferay-ui:message key="change" />)
								</a>
							</div>

							<div class="aui-helper-hidden" id="customDatabaseOptions">
								<a class="database-options" href="javascript;" id="defaultDatabaseOptionsLink">
									&laquo; <liferay-ui:message key="use-default-database" />
								</a>

								<aui:select name="databaseType">

									<%
									for (int i = 0; i < PropsValues.SETUP_DATABASE_TYPES.length; i++) {
										String databaseType = PropsValues.SETUP_DATABASE_TYPES[i];
										String driverClassName = PropsUtil.get(PropsKeys.SETUP_DATABASE_DRIVER_CLASS_NAME, new com.liferay.portal.kernel.configuration.Filter(databaseType));
										String url = PropsUtil.get(PropsKeys.SETUP_DATABASE_URL, new com.liferay.portal.kernel.configuration.Filter(databaseType));
									%>

										<aui:option label='<%= "database." + databaseType %>' data-url="<%=url%>" data-driverClassName="<%=driverClassName%>" selected="<%= PropsValues.JDBC_DEFAULT_URL.contains(databaseType) %>" value="<%= databaseType %>" />

									<%
									}
									%>

								</aui:select>

								<span class="aui-field-hint aui-helper-hidden" id="databaseMessage">
									<liferay-ui:message key="in-order-to-use-this-database" />
								</span>

								<aui:input id="jdbcDefaultURL" label="jdbc-default-url" name="<%= "properties--" + PropsKeys.JDBC_DEFAULT_URL + "--" %>" value="<%= PropsValues.JDBC_DEFAULT_URL %>" />

								<aui:input id="jdbcDefaultDriverName" label="jdbc-driver-classname" name="<%= "properties--" + PropsKeys.JDBC_DEFAULT_DRIVER_CLASS_NAME + "--" %>" value="<%= PropsValues.JDBC_DEFAULT_DRIVER_CLASS_NAME %>" />

								<aui:input label="user-name" name='<%= "properties--" + PropsKeys.JDBC_DEFAULT_USERNAME + "--" %>' value="<%= PropsValues.JDBC_DEFAULT_USERNAME %>" />

								<aui:input label="password" name='<%= "properties--" + PropsKeys.JDBC_DEFAULT_PASSWORD + "--" %>' type="password" value="<%= PropsValues.JDBC_DEFAULT_PASSWORD %>" />
							</div>
						</aui:fieldset>

						<aui:button-row>
							<aui:button name="finishButton" type="submit" value="finish-configuration" />
						</aui:button-row>
					</aui:form>

					<aui:script use="aui-base,aui-loading-mask">
						var customDatabaseOptions = A.one('#customDatabaseOptions');
						var customDatabaseOptionsLink = A.one('#customDatabaseOptionsLink');
						var databaseMessage = A.one('#databaseMessage');
						var databaseSelector = A.one('#databaseType');
						var defaultDatabase = A.one('#defaultDatabase');
						var defaultDatabaseOptions = A.one('#defaultDatabaseOptions');
						var defaultDatabaseOptionsLink = A.one('#defaultDatabaseOptionsLink');

						var jdbcDefaultURL = A.one('#<portlet:namespace />jdbcDefaultURL');
						var jdbcDefaultDriverClassName = A.one('#<portlet:namespace />jdbcDefaultDriverName');

						if (databaseSelector.val() != 'hypersonic') {
							defaultDatabaseOptions.hide();

							customDatabaseOptions.show();
						}

						var toggleDatabaseOptions = function(showDefault, event) {
							if (event) {
								event.preventDefault();
							}

							defaultDatabaseOptions.toggle(showDefault);

							customDatabaseOptions.toggle(!showDefault);

							defaultDatabase.val(showDefault);
						};

						customDatabaseOptionsLink.on('click', A.bind(toggleDatabaseOptions, null, false));

						defaultDatabaseOptionsLink.on('click', A.bind(toggleDatabaseOptions, null, true));

						var onChangeDatabaseSelector = function() {
							var value = databaseSelector.val();
							var index = databaseSelector.get('selectedIndex');
							var selectedOption = databaseSelector.get("options").item(index);

							var driverClassName = selectedOption.getAttribute('data-driverClassName');
						    var databaseURL = selectedOption.getAttribute('data-url');

							jdbcDefaultURL.val(databaseURL);
							jdbcDefaultDriverClassName.val(driverClassName);

							var displayMessage = !(/^hypersonic|mysql|postgresql$/.test(value));

							databaseMessage.toggle(displayMessage);
						}

						onChangeDatabaseSelector();

						databaseSelector.on('change', onChangeDatabaseSelector);

						A.one('#<portlet:namespace />finishButton').on(
							'click',
							function(event) {
								var loadingMask = new A.LoadingMask(
									{
										'strings.loading': '<liferay-ui:message key="liferay-is-being-installed" />',
										target: A.getBody(),
										visible: true
									}
								);
							}
						);
					</aui:script>
				</c:when>
				<c:otherwise>

					<%
					application.setAttribute(WebKeys.SETUP_WIZARD_FINISHED, true);
					%>

					<c:choose>
						<c:when test="<%= propertiesFileUpdated %>">

							<%
							PortletURL loginURL = new PortletURLImpl(request, PortletKeys.LOGIN, plid, PortletRequest.ACTION_PHASE);

							loginURL.setWindowState(WindowState.NORMAL);
							loginURL.setPortletMode(PortletMode.VIEW);

							loginURL.setParameter("struts_action", "/login/login");
							loginURL.setParameter("saveLastPath", "0");
							%>

							<aui:form action="<%= loginURL %>" method="post" name="fm">
								<aui:input name="login" type="hidden" value="<%= PropsValues.DEFAULT_ADMIN_EMAIL_ADDRESS %>" />
								<aui:input name="password" type="hidden" value='<%= PropsValues.DEFAULT_ADMIN_PASSWORD %>' />

								<p>
									<span class="portlet-msg-success">
										<liferay-ui:message key="your-configuration-was-saved-sucessfully" />
									</span>

									<span class="aui-field-hint">

										<%
										String taglibArguments = "<span class=\"lfr-inline-code\">" + PropsValues.LIFERAY_HOME + StringPool.SLASH + SetupWizardUtil.PROPERTIES_FILE_NAME + "</span>";
										%>

										<liferay-ui:message arguments="<%= taglibArguments %>" key="the-configuration-was-saved-in" />
									</span>
								</p>

								<c:if test="<%= !passwordUpdated %>">
									<p>
										<span class="aui-field-hint">
											<liferay-ui:message arguments="<%= PropsValues.DEFAULT_ADMIN_PASSWORD %>" key="your-password-is-x.-don't-forget-to-change-it-in-my-account" />
										</span>
									</p>
								</c:if>

							   <aui:button type="submit" value="go-to-my-portal" />
							</aui:form>
						</c:when>
						<c:otherwise>
							<p>
								<span class="portlet-msg-alert">

									<%
									String taglibArguments = "<span class=\"lfr-inline-code\">" + PropsValues.LIFERAY_HOME + "</span>";
									%>

									<liferay-ui:message arguments="<%= taglibArguments %>" key="sorry,-we-were-not-able-to-save-the-configuration-file-in-x" />
								</span>
							</p>

							<aui:input inputCssClass="properties-text" name="portal-ext" label="" type="textarea" value="<%= unicodeProperties.toSortedString() %>" wrap="soft" />
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<footer id="footer" role="contentinfo">
		<p class="powered-by">
			<liferay-ui:message key="powered-by" /> <a href="http://www.liferay.com" rel="external">Liferay</a>
		</p>
	</footer>
</div>
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
UnicodeProperties properties = (UnicodeProperties)request.getSession().getAttribute(WebKeys.SETUP_WIZARD_PROPERTIES);

boolean propertiesFileCreated = GetterUtil.getBoolean((Boolean)request.getSession().getAttribute(WebKeys.SETUP_WIZARD_PROPERTIES_UPDATED));

boolean passwordUpdated = GetterUtil.getBoolean((Boolean)request.getSession().getAttribute(WebKeys.SETUP_WIZARD_PASSWORD_UPDATED));

String themeStaticResourcePath = theme.getStaticResourcePath();

String cdnHost = PortalUtil.getCDNHost(request.isSecure());

String themeImagesPath = cdnHost + themeStaticResourcePath + theme.getImagesPath();
%>

<style>
	<%@include file="/html/portal/css/portal/setup_wizard.jspf" %>
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
				<c:when test="<%= !propertiesFileCreated && !SetupWizardUtil.isSetupFinished(request) %>">
					<aui:form action='<%= themeDisplay.getPathMain() + "/portal/setup_wizard" %>' method="post" name="fm">
						<aui:input type="hidden" name="<%= Constants.CMD %>" value="<%= Constants.UPDATE %>" />

						<aui:fieldset column="<%= true %>" cssClass="aui-w45" label="portal">
							<aui:input label="portal-name" name='<%= "properties--" + PropsKeys.COMPANY_DEFAULT_WEB_ID + "--" %>' value="<%= PropsValues.COMPANY_DEFAULT_WEB_ID %>" />

							<span class="aui-field-hint">
								<liferay-ui:message key="for-example-x" arguments='<%= "liferay.com" %>' />
							</span>
						</aui:fieldset>

						<aui:fieldset column="<%= true %>" cssClass="aui-column-last aui-w50" label="administrator-user">
							<aui:input label="first-name" name='<%= "properties--" + PropsKeys.DEFAULT_ADMIN_FIRST_NAME + "--" %>' value="<%= PropsValues.DEFAULT_ADMIN_FIRST_NAME %>" />

							<aui:input label="last-name" name='<%= "properties--" + PropsKeys.DEFAULT_ADMIN_LAST_NAME + "--" %>' value="<%= PropsValues.DEFAULT_ADMIN_LAST_NAME %>" />

							<aui:input label="email" name='<%= "properties--" + PropsKeys.ADMIN_EMAIL_FROM_ADDRESS + "--" %>' value="<%= PropsUtil.get(PropsKeys.ADMIN_EMAIL_FROM_ADDRESS) %>" />
						</aui:fieldset>

						<aui:fieldset column="<%= true %>" cssClass="aui-w100" label="database">

							<%
							String[] drivers = PropsUtil.getArray(PropsKeys.SETUP_DATABASE_DRIVERS_LIST);

							String jdbcDefaultUrl = PropsUtil.get(PropsKeys.JDBC_DEFAULT_URL);
							%>

							<aui:input name="defaultDatabase" type="hidden" value='<%= jdbcDefaultUrl.indexOf("hsqldb") != -1 %>' />

							<div id="defaultDatabaseOptions">
								<strong><liferay-ui:message key="default-database-hypersonic" /></strong>. <liferay-ui:message key="this-database-is-useful-for-development" />

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
									for (String driver : drivers) {
									%>
										<aui:option selected="<%= jdbcDefaultUrl.indexOf(driver) != -1 %>" label="<%= driver %>" />
									<%
									}
									%>

								</aui:select>

								<span class="aui-field-hint aui-helper-hidden" id="databaseMessage">
									<liferay-ui:message key="in-order-to-use-this-database" />
								</span>

								<aui:input name="databaseName" value="lportal" />

								<aui:input label="user-name" name='<%= "properties--" + PropsKeys.JDBC_DEFAULT_USERNAME + "--" %>' value="<%= PropsUtil.get(PropsKeys.JDBC_DEFAULT_USERNAME) %>" />

								<aui:input label="password" name='<%= "properties--" + PropsKeys.JDBC_DEFAULT_PASSWORD + "--" %>' type="password" value="<%= PropsUtil.get(PropsKeys.JDBC_DEFAULT_PASSWORD) %>" />
							</div>
						</aui:fieldset>

						<aui:button-row>
							<aui:button name="finishButton" type="submit" value="finish-configuration" />
						</aui:button-row>
					</aui:form>

					<aui:script use="aui-base,aui-loading-mask">
						var customDatabaseOptionsLink = A.one('#customDatabaseOptionsLink');
						var customDatabaseOptions = A.one('#customDatabaseOptions');
						var databaseMessage = A.one('#databaseMessage');
						var databaseSelector = A.one('#databaseType');
						var defaultDatabase = A.one('#defaultDatabase');
						var defaultDatabaseOptions = A.one('#defaultDatabaseOptions');
						var defaultDatabaseOptionsLink = A.one('#defaultDatabaseOptionsLink');

						if (databaseSelector.val() != 'hsqldb') {
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

							var displayMessage = !(/^hsqldb|mysql|postgresql$/.test(value));

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
						<c:when test="<%= propertiesFileCreated %>">
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

								<aui:fieldset label="congratulations">
									<p>
										<span class="portlet-msg-success">
											<liferay-ui:message key="your-configuration-has-finished-sucessfully" />
										</span>

										<span class="aui-field-hint">
											<liferay-ui:message key="this-configuration-has-been-saved-in" arguments='<%= "<span class=\\"lfr-inline-code\\">" + PropsValues.LIFERAY_HOME + StringPool.SLASH + SetupWizardUtil.PROPERTIES_FILE_NAME + "</span>" %>'/>
										</span>
									</p>
								</aui:fieldset>

							   <aui:button type="submit" value="go-to-my-portal" />

								<c:if test="<%= !passwordUpdated %>">
									<span class="aui-field-hint">
										<liferay-ui:message key="your-passwrod-is-x" arguments="<%= PropsValues.DEFAULT_ADMIN_PASSWORD %>" />
									</span>
								</c:if>
							</aui:form>
						</c:when>
						<c:otherwise>
							<aui:fieldset label="create-a-configuration-file">
								<p>
									<span class="portlet-msg-alert">
										<liferay-ui:message key="sorry-we-were-not-able-to-write-configuration-file" arguments='<%= "<span class=\\"lfr-inline-code\\">" + PropsValues.LIFERAY_HOME + "</span>" %>'/>
									</span>
								</p>
							</aui:fieldset>

							<aui:fieldset label="your-portal-ext-properties">
								<aui:input inputCssClass="properties-text" name="portal-ext" label="" type="textarea" value="<%= properties.toString() %>" wrap="soft" />
							</aui:fieldset>
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

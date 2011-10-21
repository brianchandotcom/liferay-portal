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

			<%
			boolean propertiesFileUpdated = GetterUtil.getBoolean((Boolean)session.getAttribute(WebKeys.SETUP_WIZARD_PROPERTIES_UPDATED));
			%>

			<c:choose>
				<c:when test="<%= !propertiesFileUpdated && !SetupWizardUtil.isSetupFinished(request) %>">

					<%
					boolean defaultDatabase = ParamUtil.getBoolean(request,"defaultDatabase", PropsValues.JDBC_DEFAULT_URL.contains("hsqldb"));
					%>

					<aui:form action='<%= themeDisplay.getPathMain() + "/portal/setup_wizard" %>' method="post" onSubmit="event.preventDefault();" name="fm">
						<aui:input type="hidden" name="<%= Constants.CMD %>" value="<%= Constants.UPDATE %>" />

						<aui:fieldset column="<%= true %>" cssClass="aui-w45" label="portal">
							<aui:input label="portal-name" name='<%= "properties--" + PropsKeys.COMPANY_DEFAULT_WEB_ID + "--" %>' suffix='<%= LanguageUtil.format(pageContext, "for-example-x", "liferay.com") %>' value="<%= PropsValues.COMPANY_DEFAULT_WEB_ID %>" />

							<aui:select inlineField="<%= true %>" label="default-language" name='<%= "properties--" + PropsKeys.COMPANY_DEFAULT_LOCALE + "--" %>'>

								<%
								String languageId = GetterUtil.getString((String)session.getAttribute(WebKeys.SETUP_WIZARD_DEFAULT_LOCALE), PropsValues.COMPANY_DEFAULT_LOCALE);

								Locale[] locales = LanguageUtil.getAvailableLocales();

								for (Locale curLocale : locales) {
								%>

									<aui:option label="<%= curLocale.getDisplayName(curLocale) %>" selected="<%= languageId.equals(LocaleUtil.toLanguageId(curLocale)) %>" value="<%= LocaleUtil.toLanguageId(curLocale) %>" />

								<%
								}
								%>

							</aui:select>

							<aui:button cssClass="change-language" name="changeLanguageButton" value="change" />
						</aui:fieldset>

						<aui:fieldset column="<%= true %>" cssClass="aui-column-last aui-w50" label="administrator-user">
							<aui:input label="first-name" name='<%= "properties--" + PropsKeys.DEFAULT_ADMIN_FIRST_NAME + "--" %>' value="<%= PropsValues.DEFAULT_ADMIN_FIRST_NAME %>" />

							<aui:input label="last-name" name='<%= "properties--" + PropsKeys.DEFAULT_ADMIN_LAST_NAME + "--" %>' value="<%= PropsValues.DEFAULT_ADMIN_LAST_NAME %>" />

							<aui:input label="email" name='<%= "properties--" + PropsKeys.ADMIN_EMAIL_FROM_ADDRESS + "--" %>' value="<%= PropsValues.ADMIN_EMAIL_FROM_ADDRESS %>">
								<aui:validator name="email" />
								<aui:validator name="required" />
							</aui:input>
						</aui:fieldset>

						<aui:fieldset column="<%= true %>" cssClass="aui-w100" label="database">
							<aui:input name="defaultDatabase" type="hidden" value="<%= defaultDatabase %>" />

							<div class='<%= defaultDatabase ? StringPool.BLANK : "aui-helper-hidden" %>' id="defaultDatabaseOptions">
								<p>
									<strong><liferay-ui:message key="default-database" /> (<liferay-ui:message key="database.hypersonic" />)</strong>
								</p>

								<liferay-ui:message key="this-database-is-useful-for-development-and-demo'ing-purposes" />

								<a href="<%= HttpUtil.addParameter(themeDisplay.getPathMain() + "/portal/setup_wizard", "defaultDatabase", false) %>" id="customDatabaseOptionsLink">
									(<liferay-ui:message key="change" />)
								</a>
							</div>

							<div class="<%= defaultDatabase ? "aui-helper-hidden" : StringPool.BLANK %>" id="customDatabaseOptions">
								<div class="connection-messages" id="connectionMessages"></div>

								<a class="database-options" href="<%= HttpUtil.addParameter(themeDisplay.getPathMain() + "/portal/setup_wizard", "defaultDatabase", true) %>" id="defaultDatabaseOptionsLink">
									&laquo; <liferay-ui:message key="use-default-database" />
								</a>

								<aui:select cssClass="database-type" name="databaseType">

									<%
									for (int i = 0; i < PropsValues.SETUP_DATABASE_TYPES.length; i++) {
										String databaseType = PropsValues.SETUP_DATABASE_TYPES[i];

										Map<String, Object> data = new HashMap<String, Object>();

										String driverClassName = PropsUtil.get(PropsKeys.SETUP_DATABASE_DRIVER_CLASS_NAME, new Filter(databaseType));

										data.put("driverClassName", driverClassName);

										String url = PropsUtil.get(PropsKeys.SETUP_DATABASE_URL, new Filter(databaseType));

										data.put("url", url);
									%>

										<aui:option data="<%= data %>" label='<%= "database." + databaseType %>' selected="<%= PropsValues.JDBC_DEFAULT_URL.contains(databaseType) %>" value="<%= databaseType %>" />

									<%
									}
									%>

								</aui:select>

								<aui:input id="jdbcDefaultURL" label="jdbc-url" name='<%= "properties--" + PropsKeys.JDBC_DEFAULT_URL + "--" %>' value="<%= PropsValues.JDBC_DEFAULT_URL %>">
									<aui:validator name="required" />
								</aui:input>

								<aui:input id="jdbcDefaultDriverName" label="jdbc-driver-class-name" name='<%= "properties--" + PropsKeys.JDBC_DEFAULT_DRIVER_CLASS_NAME + "--" %>' value="<%= PropsValues.JDBC_DEFAULT_DRIVER_CLASS_NAME %>">
									<aui:validator name="required" />
								</aui:input>

								<aui:input id="jdbcDefaultUserName" label="user-name" name='<%= "properties--" + PropsKeys.JDBC_DEFAULT_USERNAME + "--" %>' value="<%= PropsValues.JDBC_DEFAULT_USERNAME %>" />

								<aui:input id="jdbcDefaultPassword" label="password" name='<%= "properties--" + PropsKeys.JDBC_DEFAULT_PASSWORD + "--" %>' type="password" value="<%= PropsValues.JDBC_DEFAULT_PASSWORD %>" />
							</div>
						</aui:fieldset>

						<aui:button-row>
							<aui:button name="finishButton" type="submit" value="finish-configuration" />
						</aui:button-row>
					</aui:form>

					<aui:script use="aui-base,aui-io-request,aui-loading-mask">
						var customDatabaseOptions = A.one('#customDatabaseOptions');
						var customDatabaseOptionsLink = A.one('#customDatabaseOptionsLink');
						var databaseSelector = A.one('#databaseType');
						var defaultDatabase = A.one('#defaultDatabase');
						var defaultDatabaseOptions = A.one('#defaultDatabaseOptions');
						var defaultDatabaseOptionsLink = A.one('#defaultDatabaseOptionsLink');

						var jdbcDefaultURL = A.one('#jdbcDefaultURL');
						var jdbcDefaultDriverClassName = A.one('#jdbcDefaultDriverName');
						var jdbcDefaultUserName = A.one('#jdbcDefaultUserName');
						var jdbcDefaultPassword = A.one('#jdbcDefaultPassword');

						var setupForm = A.one('#fm');
						var command = A.one('#<%= Constants.CMD %>');

						var connectionMessages = A.one('#connectionMessages');

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

							var selectedOption = databaseSelector.get('options').item(index);

							var driverClassName = selectedOption.attr('data-driverClassName');
							var databaseURL = selectedOption.attr('data-url');

							jdbcDefaultURL.val(databaseURL);
							jdbcDefaultDriverClassName.val(driverClassName);
						}

						onChangeDatabaseSelector();

						databaseSelector.on('change', onChangeDatabaseSelector);

						A.one('#changeLanguageButton').on(
							'click',
							function(event) {
								command.val('<%= Constants.TRANSLATE %>');

								setupForm.submit();
							}
						);

						var loadingMask = new A.LoadingMask(
							{
								'strings.loading': '<liferay-ui:message key="liferay-is-being-installed" />',
								target: A.getBody()
							}
						);

						var updateMessage = function(message, type) {
							connectionMessages.html('<span class="portlet-msg-' + type + '">' + message + '</span>');
						};

						var startInstall = function() {
							connectionMessages.empty();

							loadingMask.show();
						};

						A.one('#fm').on(
							'submit',
							function(event) {
								if (defaultDatabase.val() == 'true') {
									startInstall();

									command.val('<%= Constants.UPDATE %>');

									submitForm(document.fm);
								}
								else {
									command.val('<%= Constants.TEST %>');

									A.io.request(
										setupForm.get('action'),
										{
											form: {
												id: document.fm
											},
											dataType: 'json',
											after: {
												success: function(event, id, obj) {
													command.val('<%= Constants.UPDATE %>');

													var responseData = this.get('responseData');

													if (!responseData.success) {
														updateMessage(responseData.message, 'error');

														loadingMask.hide();
													}
													else {
														submitForm(document.fm);
													}

												},
												failure: function(event, id, obj) {
													loadingMask.hide();

													updateMessage('<%= LanguageUtil.get(pageContext, "an-unexpected-error-occurred-while-connecting-to-the-database") %>', 'error');
												}
											},
											on: {
												start: startInstall
											}
										}
									);
								}
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

								<%
								boolean passwordUpdated = GetterUtil.getBoolean((Boolean)session.getAttribute(WebKeys.SETUP_WIZARD_PASSWORD_UPDATED));
								%>

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

							<%
							UnicodeProperties unicodeProperties = (UnicodeProperties)session.getAttribute(WebKeys.SETUP_WIZARD_PROPERTIES);
							%>

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
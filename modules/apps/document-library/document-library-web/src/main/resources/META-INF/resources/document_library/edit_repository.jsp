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

<%@ include file="/document_library/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

Repository repository = (Repository)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_REPOSITORY);

long repositoryId = BeanParamUtil.getLong(repository, request, "repositoryId");

long folderId = ParamUtil.getLong(request, "folderId");

String headerTitle = (repository == null) ? LanguageUtil.get(request, "new-repository") : repository.getName();

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	renderResponse.setTitle(headerTitle);
}
%>

<div <%= portletTitleBasedNavigation ? "class=\"container-fluid-1280\"" : StringPool.BLANK %>>
	<portlet:actionURL name="/document_library/edit_repository" var="editRepositoryURL">
		<portlet:param name="mvcRenderCommandName" value="/document_library/edit_repository" />
	</portlet:actionURL>

	<aui:form action="<%= editRepositoryURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (repository == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="repositoryId" type="hidden" value="<%= repositoryId %>" />
		<aui:input name="folderId" type="hidden" value="<%= folderId %>" />

		<c:if test="<%= !portletTitleBasedNavigation %>">
			<liferay-ui:header
				backURL="<%= redirect %>"
				localizeTitle="<%= false %>"
				title="<%= headerTitle %>"
			/>
		</c:if>

		<liferay-ui:error exception="<%= DuplicateFolderNameException.class %>" message="please-enter-a-unique-repository-name" />
		<liferay-ui:error exception="<%= DuplicateRepositoryNameException.class %>" message="please-enter-a-unique-repository-name" />
		<liferay-ui:error exception="<%= FolderNameException.class %>" message="please-enter-a-valid-folder-name" />
		<liferay-ui:error exception="<%= InvalidRepositoryException.class %>" message="please-verify-your-repository-configuration-parameters" />
		<liferay-ui:error exception="<%= RepositoryNameException.class %>" message="please-enter-a-valid-name" />

		<aui:model-context bean="<%= repository %>" model="<%= Repository.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="name" />

				<aui:input name="description" />
			</aui:fieldset>

			<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="repository-configuration">
				<c:choose>
					<c:when test="<%= repository == null %>">
						<aui:select id="repositoryTypes" label="repository-type" name="className">

							<%
							for (RepositoryClassDefinition repositoryClassDefinition : RepositoryClassDefinitionCatalogUtil.getExternalRepositoryClassDefinitions()) {
							%>

								<aui:option label="<%= HtmlUtil.escape(repositoryClassDefinition.getRepositoryTypeLabel(locale)) %>" value="<%= HtmlUtil.escapeAttribute(repositoryClassDefinition.getClassName()) %>" />

							<%
							}
							%>

						</aui:select>

						<div id="<portlet:namespace />settingsParameters"></div>
					</c:when>
					<c:otherwise>

						<%
						RepositoryClassDefinition repositoryClassDefinition = RepositoryClassDefinitionCatalogUtil.getRepositoryClassDefinition(repository.getClassName());
						%>

						<div class="repository-settings-display">
							<dt>
								<liferay-ui:message key="repository-type" />
							</dt>
							<dd>
								<%= repositoryClassDefinition.getRepositoryTypeLabel(locale) %>
							</dd>

							<%
							UnicodeProperties typeSettingsProperties = repository.getTypeSettingsProperties();

							RepositoryConfiguration repositoryConfiguration = repositoryClassDefinition.getRepositoryConfiguration();

							for (RepositoryConfiguration.Parameter repositoryConfigurationParameter : repositoryConfiguration.getParameters()) {
								String parameterValue = typeSettingsProperties.getProperty(repositoryConfigurationParameter.getName());

								if (Validator.isNotNull(parameterValue)) {
							%>

									<dt>
										<%= HtmlUtil.escape(repositoryConfigurationParameter.getLabel(locale)) %>
									</dt>
									<dd>
										<%= HtmlUtil.escape(parameterValue) %>
									</dd>

							<%
								}
							}
							%>

						</div>
					</c:otherwise>
				</c:choose>
			</aui:fieldset>

			<c:if test="<%= repository == null %>">
				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions">
					<liferay-ui:input-permissions
						modelName="<%= DLFolderConstants.getClassName() %>"
					/>
				</aui:fieldset>
			</c:if>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>

	<div class="hide" id="<portlet:namespace />settingsSupported">

		<%
		for (RepositoryClassDefinition repositoryClassDefinition : RepositoryClassDefinitionCatalogUtil.getExternalRepositoryClassDefinitions()) {
			try {
				String repositoryClassDefinitionId = RepositoryClassDefinitionUtil.getRepositoryClassDefinitionId(repositoryClassDefinition);
		%>

				<div class="settings-parameters" id="<portlet:namespace />repository-<%= repositoryClassDefinitionId %>-configuration">

					<%
					RepositoryConfiguration repositoryConfiguration = repositoryClassDefinition.getRepositoryConfiguration();

					for (RepositoryConfiguration.Parameter repositoryConfigurationParameter : repositoryConfiguration.getParameters()) {
					%>

						<aui:input label="<%= HtmlUtil.escape(repositoryConfigurationParameter.getLabel(locale)) %>" name='<%= "settings--" + HtmlUtil.escapeAttribute(repositoryConfigurationParameter.getName()) + "--" %>' type="text" value="" />

					<%
					}
					%>

				</div>

		<%
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
		%>

	</div>
</div>

<aui:script require="metal-dom/src/dom as dom">
	var settingsParametersContainer = document.getElementById(
		'<portlet:namespace />settingsParameters'
	);
	var settingsSupported = document.getElementById(
		'<portlet:namespace />settingsSupported'
	);

	function showConfiguration(select) {
		if (settingsParametersContainer && settingsSupported) {
			var settingsParametersElement = settingsParametersContainer.querySelector(
				'.settings-parameters'
			);

			if (settingsParametersElement) {
				dom.append(settingsSupported, settingsParametersElement);
			}

			var className = select.value;

			var repositoryClassDefinitionId = className.replace(/\W/g, '-');

			var repositoryParameters = document.getElementById(
				'<portlet:namespace />repository-' +
					repositoryClassDefinitionId +
					'-configuration'
			);

			if (repositoryParameters) {
				dom.append(settingsParametersContainer, repositoryParameters);
			}
		}
	}

	var repositoryTypesSelect = document.getElementById(
		'<portlet:namespace />repositoryTypes'
	);

	if (repositoryTypesSelect) {
		repositoryTypesSelect.addEventListener('change', function(event) {
			showConfiguration(repositoryTypesSelect);
		});

		showConfiguration(repositoryTypesSelect);
	}
</aui:script>

<%
if (repository != null) {
	DLBreadcrumbUtil.addPortletBreadcrumbEntries(folderId, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
}
%>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_document_library_web.document_library.edit_repository_jsp");
%>
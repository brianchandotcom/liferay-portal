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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

Repository repository = (Repository)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_REPOSITORY);

long repositoryId = BeanParamUtil.getLong(repository, request, "repositoryId");

long folderId = ParamUtil.getLong(request, "folderId");
%>

<liferay-util:include page="/html/portlet/document_library/top_links.jsp" />

<portlet:actionURL var="editRepositoryURL">
	<portlet:param name="struts_action" value="/document_library/edit_repository" />
</portlet:actionURL>

<aui:form action="<%= editRepositoryURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (repository == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="repositoryId" type="hidden" value="<%= repositoryId %>" />
	<aui:input name="folderId" type="hidden" value="<%= folderId %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		localizeTitle="<%= (repository == null) %>"
		title='<%= (repository == null) ? "new-repository" : repository.getName() %>'
	/>

	<liferay-ui:error exception="<%= DuplicateFolderNameException.class %>" message="please-enter-a-unique-repository-name" />
	<liferay-ui:error exception="<%= DuplicateRepositoryNameException.class %>" message="please-enter-a-unique-repository-name" />
	<liferay-ui:error exception="<%= FolderNameException.class %>" message="please-enter-a-valid-folder-name" />
	<liferay-ui:error exception="<%= InvalidRepositoryException.class %>" message="please-verify-your-repository-configuration-parameters" />
	<liferay-ui:error exception="<%= RepositoryNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= repository %>" model="<%= Repository.class %>" />

	<aui:fieldset>
		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="name" />

		<aui:input name="description" />

		<c:choose>
			<c:when test="<%= repository == null %>">
				<aui:select id="repositoryTypes" label="repository-type" name="className">

					<%
					for (String dlRepositoryImpl : RepositoryFactoryUtil.getRepositoryClassNames()) {
					%>

						<aui:option label="<%= HtmlUtil.escape(ResourceActionsUtil.getModelResource(locale, dlRepositoryImpl)) %>" value="<%= HtmlUtil.escapeAttribute(dlRepositoryImpl) %>" />

					<%
					}
					%>

				</aui:select>

				<div id="<portlet:namespace />settingsConfiguration"></div>

				<div id="<portlet:namespace />settingsParameters"></div>
			</c:when>
			<c:otherwise>
				<div class="repository-settings-display">
					<dt>
						<liferay-ui:message key="repository-type" />
					</dt>
					<dd>
						<%= ResourceActionsUtil.getModelResource(locale, repository.getClassName()) %>
					</dd>

					<%
					UnicodeProperties typeSettingsProperties = repository.getTypeSettingsProperties();

					String configuration = typeSettingsProperties.get("configuration-type");

					String[] supportedParameters = RepositoryServiceUtil.getSupportedParameters(repository.getClassNameId(), configuration);

					for (String supportedParameter : supportedParameters) {
						String supportedParameterValue = typeSettingsProperties.getProperty(supportedParameter);

						if (Validator.isNotNull(supportedParameterValue)) {
					%>

							<dt>
								<%= LanguageUtil.get(pageContext, HtmlUtil.escape(StringUtil.replace(StringUtil.toLowerCase(supportedParameter), CharPool.UNDERLINE, CharPool.DASH))) %>
							</dt>
							<dd>
								<%= HtmlUtil.escape(supportedParameterValue) %>
							</dd>

					<%
						}
					}
					%>

				</div>
			</c:otherwise>
		</c:choose>
		<c:if test="<%= repository == null %>">
			<aui:field-wrapper label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= DLFolderConstants.getClassName() %>"
				/>
			</aui:field-wrapper>
		</c:if>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<div class="hide" id="<portlet:namespace />settingsSupported">

	<%
	for (String dlRepositoryImpl : RepositoryFactoryUtil.getRepositoryClassNames()) {
		String className = HtmlUtil.escapeAttribute(dlRepositoryImpl.substring(dlRepositoryImpl.lastIndexOf(StringPool.PERIOD) + 1));

		long classNameId = PortalUtil.getClassNameId(dlRepositoryImpl);

		try {
			String[] supportedConfigurations = RepositoryServiceUtil.getSupportedConfigurations(classNameId);

			for (String supportedConfiguration : supportedConfigurations) {
		%>

			<div class="settings-configuration <%= ((supportedConfigurations.length == 1) ? "hide" : "") %>" id="<portlet:namespace />repository-<%= className %>-wrapper">
				<aui:select cssClass="repository-configuration" id='<%= "repository-" + className %>' label="repository-configuration" name="settings--configuration-type--">
					<aui:option label="<%= LanguageUtil.get(pageContext, HtmlUtil.escape(StringUtil.replace(StringUtil.toLowerCase(supportedConfiguration), CharPool.UNDERLINE, CharPool.DASH))) %>" selected="<%= supportedConfiguration.equals(supportedConfigurations[0]) %>" value="<%= HtmlUtil.escapeAttribute(supportedConfiguration) %>" />
				</aui:select>
			</div>
			<div class="settings-parameters" id="<portlet:namespace />repository-<%= className %>-configuration-<%= HtmlUtil.escapeAttribute(supportedConfiguration) %>">

				<%
				String[] supportedParameters = RepositoryServiceUtil.getSupportedParameters(classNameId, supportedConfiguration);

				for (String supportedParameter : supportedParameters) {
				%>

					<aui:input label="<%= LanguageUtil.get(pageContext, HtmlUtil.escape(StringUtil.replace(StringUtil.toLowerCase(supportedParameter), CharPool.UNDERLINE, CharPool.DASH))) %>" name='<%= "settings--" + HtmlUtil.escapeAttribute(supportedParameter) + "--" %>' type="text" value="" />

				<%
				}
				%>

			</div>

	<%
			}
		}
		catch (Exception e) {
			_log.error(e);
		}
	}
	%>

</div>

<aui:script use="aui-base">
	var settingsSupported = A.one('#<portlet:namespace />settingsSupported');
	var settingsConfiguration = A.one('#<portlet:namespace />settingsConfiguration');
	var settingsParameters = A.one('#<portlet:namespace />settingsParameters');

	var showConfiguration = function(select) {
		if (settingsConfiguration) {
			settingsSupported.append(settingsConfiguration.all('.settings-configuration'));
		}

		if (settingsParameters) {
			settingsSupported.append(settingsParameters.all('.settings-parameters'));
		}

		var value = select.val();
		var className = value.split('.').pop();

		var repositoryConfiguration = A.one('#<portlet:namespace />repository-' + className + '-wrapper');
		var selectRepositoryConfiguration = A.one('#<portlet:namespace />repository-' + className);

		if (selectRepositoryConfiguration) {
			var repositoryParameters = A.one('#<portlet:namespace />repository-' + className + '-configuration-' + selectRepositoryConfiguration.val());

			if (settingsConfiguration) {
				settingsConfiguration.append(repositoryConfiguration);
			}

			if (settingsParameters) {
				settingsParameters.append(repositoryParameters);
			}
		}
	};

	var showParameters = function(event) {
		var select = event.currentTarget;

		var repositoryParameters = A.one('#' + select.attr('id') + '-configuration-' + select.val());

		var settingsParametersChildren = settingsParameters.all('.settings-parameters');

		settingsSupported.append(settingsParametersChildren);
		settingsParameters.append(repositoryParameters);
	}

	var selectRepositoryTypes = A.one('#<portlet:namespace />repositoryTypes');

	if (selectRepositoryTypes) {
		selectRepositoryTypes.on(
			'change',
			function(event) {
				showConfiguration(event.currentTarget);
			}
		);

		showConfiguration(selectRepositoryTypes);
	}

	var selectConfiguration = A.all('.repository-configuration');

	selectConfiguration.on('change', showParameters);
</aui:script>

<%
if (repository != null) {
	DLUtil.addPortletBreadcrumbEntries(folderId, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
%>

<%!
	private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.document_library.edit_repository_jsp");
%>
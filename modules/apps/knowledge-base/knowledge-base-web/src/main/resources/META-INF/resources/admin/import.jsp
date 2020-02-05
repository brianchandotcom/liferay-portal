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
long parentKBFolderId = ParamUtil.getLong(request, "parentKBFolderId");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.get(resourceBundle, "import"));
%>

<portlet:actionURL name="importFile" var="importFileURL">
	<portlet:param name="redirect" value="<%= redirect %>" />
</portlet:actionURL>

<div class="container-fluid-1280">
	<aui:form action="<%= importFileURL %>" class="lfr-dynamic-form" enctype="multipart/form-data" method="post" name="fm">
		<aui:input name="mvcPath" type="hidden" value="/admin/import.jsp" />
		<aui:input name="parentKBFolderId" type="hidden" value="<%= String.valueOf(parentKBFolderId) %>" />

		<liferay-ui:error exception="<%= KBArticleImportException.MustHaveACategory.class %>">
			<%= LanguageUtil.format(request, "an-unexpected-error-occurred-while-importing-articles-x", LanguageUtil.get(request, "there-are-one-or-more-mandatory-vocabularies-assigned-to-the-knowledge-base-article")) %>
		</liferay-ui:error>

		<liferay-ui:error exception="<%= KBArticleImportException.class %>">

			<%
			KBArticleImportException kbaie = (KBArticleImportException)errorException;
			%>

			<%= LanguageUtil.format(request, "an-unexpected-error-occurred-while-importing-articles-x", HtmlUtil.escape(kbaie.getLocalizedMessage())) %>
		</liferay-ui:error>

		<liferay-ui:error exception="<%= UploadRequestSizeException.class %>">
			<liferay-ui:message arguments="<%= LanguageUtil.formatStorageSize(UploadServletRequestConfigurationHelperUtil.getMaxSize(), locale) %>" key="request-is-larger-than-x-and-could-not-be-processed" translateArguments="<%= false %>" />
		</liferay-ui:error>

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:field-wrapper>
					<div class="alert alert-info">
						<liferay-ui:message arguments="<%= HtmlUtil.escape(StringUtil.merge(kbGroupServiceConfiguration.markdownImporterArticleExtensions(), StringPool.COMMA_AND_SPACE)) %>" key="upload-your-zip-file-help" />
					</div>
				</aui:field-wrapper>

				<aui:input id="file" label="upload-your-zip-file" name="file" type="file" />

				<aui:input helpMessage="apply-numerical-prefixes-of-article-files-as-priorities-help" label="apply-numerical-prefixes-of-article-files-as-priorities" name="prioritizeByNumericalPrefix" type="checkbox" value="true" />
			</aui:fieldset>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button name="submit" type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>
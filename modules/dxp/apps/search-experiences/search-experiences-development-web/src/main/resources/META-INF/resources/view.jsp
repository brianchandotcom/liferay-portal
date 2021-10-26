<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.search.experiences.development.web.internal.constants.ImportTypeKeys" %><%@
page import="com.liferay.search.experiences.development.web.internal.constants.MVCActionCommandNames" %>

<liferay-theme:defineObjects />

<portlet:actionURL name="<%= MVCActionCommandNames.IMPORT_GOOGLE_PLACES %>" var="importGooglePlacesActionURL" />
<portlet:actionURL name="<%= MVCActionCommandNames.IMPORT_WIKIPEDIA_ARTICLES %>" var="importWikipediaArticlesActionURL" />

<liferay-ui:success embed="<%= true %>" key="success"><%= request.getAttribute("success") %></liferay-ui:success>
<liferay-ui:error embed="<%= true %>" key="error"><%= request.getAttribute("error") %></liferay-ui:error>

<div class="container-md">
	<h1><liferay-ui:message key="google-places-importer" /></h1>

	<aui:form action="<%= importGooglePlacesActionURL %>" name="importGooglePlacesForm">
		<aui:fieldset>
			<aui:select label="select-data-type" name="type">
				<aui:option label="all" value="<%= ImportTypeKeys.ALL %>" />
				<aui:option label="restaurants" value="<%= ImportTypeKeys.RESTAURANTS %>" />
				<aui:option label="tourist-attractions" value="<%= ImportTypeKeys.TOURIST_ATTRACTIONS %>" />
			</aui:select>
		</aui:fieldset>

		<aui:fieldset>
			<aui:input label="list-of-target-article-creators" name="userIds" value="<%= themeDisplay.getUserId() %>">
				<aui:validator name="required" />
			</aui:input>

			<aui:input label="list-of-target-groups" name="groupIds" required="<%= true %>" value="<%= themeDisplay.getScopeGroupId() %>">
				<aui:validator name="required" />
			</aui:input>

			<aui:input label="target-language-id" name="languageId" required="<%= true %>" value="<%= themeDisplay.getLanguageId() %>">
				<aui:validator name="required" />
			</aui:input>
		</aui:fieldset>

		<aui:button-row>
			<aui:button cssClass="btn btn-primary" type="submit" value="import" />
		</aui:button-row>
	</aui:form>

	<hr />

	<h1><liferay-ui:message key="wikipedia-articles-importer" /></h1>

	<aui:form action="<%= importWikipediaArticlesActionURL %>" name="importWikipediaArticlesForm">
		<aui:fieldset>
			<aui:input label="wiki-language" name="wikiLanguage" value="en" />
			<aui:input label="wiki-articles" name="wikiArticles" />
			<aui:input label="count-of-articles" name="count" value="100" />

			<clay:alert
				message="long-contents-will-be-truncated"
				style="info"
				title="info"
			/>
		</aui:fieldset>

		<aui:fieldset>
			<aui:input label="list-of-target-article-creators" name="userIds" value="<%= themeDisplay.getUserId() %>">
				<aui:validator name="required" />
			</aui:input>

			<aui:input label="list-of-target-groups" name="groupIds" required="<%= true %>" value="<%= themeDisplay.getScopeGroupId() %>">
				<aui:validator name="required" />
			</aui:input>

			<aui:input label="target-language-id" name="languageId" required="<%= true %>" value="<%= themeDisplay.getLanguageId() %>">
				<aui:validator name="required" />
			</aui:input>
		</aui:fieldset>

		<aui:button-row>
			<aui:button cssClass="btn btn-primary" type="submit" value="import" />
		</aui:button-row>
	</aui:form>
</div>
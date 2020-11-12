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

<%
String redirect = ParamUtil.getString(request, "redirect");

long groupId = ParamUtil.getLong(request, "groupId");
String oldArticleId = ParamUtil.getString(request, "oldArticleId");
String newArticleId = ParamUtil.getString(request, "newArticleId");
double version = ParamUtil.getDouble(request, "version");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.get(request, "web-content"));
%>

<portlet:actionURL name="copyArticle" var="copyArticleURL">
	<portlet:param name="mvcPath" value="/copy_article.jsp" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= copyArticleURL %>"
	method="post"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="oldArticleId" type="hidden" value="<%= oldArticleId %>" />
	<aui:input name="version" type="hidden" value="<%= version %>" />

	<liferay-frontend:edit-form-body>
		<liferay-ui:error exception="<%= ArticleIdException.class %>" message="please-enter-a-valid-id" />
		<liferay-ui:error exception="<%= DuplicateArticleIdException.class %>" message="please-enter-a-unique-id" />

		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset>
				<aui:input name="id" type="resource" value="<%= oldArticleId %>" />

				<c:choose>
					<c:when test="<%= journalWebConfiguration.journalFeedForceAutogenerateId() %>">
						<aui:input name="newId" type="resource" value='<%= LanguageUtil.get(request, "autogenerate-id") %>' />

						<aui:input name="newArticleId" type="hidden" />
						<aui:input name="autoArticleId" type="hidden" value="<%= true %>" />
					</c:when>
					<c:otherwise>
						<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" bean="<%= null %>" cssClass="lfr-input-text-container" field="articleId" fieldParam="newArticleId" label="" model="<%= JournalArticle.class %>" name="newArticleId" value="<%= newArticleId %>" />
					</c:otherwise>
				</c:choose>
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" value="copy" />

		<aui:button href="<%= redirect %>" value="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>
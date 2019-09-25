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

<%@ include file="/dynamic_include/init.jsp" %>

<%
JournalArticle article = journalContentDisplayContext.getArticle();
%>

<liferay-ui:success key='<%= JournalContentPortletKeys.JOURNAL_CONTENT + "requestProcessed" %>' message="your-request-completed-successfully" />

<div class="visible-interaction">
	<liferay-ui:icon-menu
		cssClass="btn btn-monospaced btn-sm"
		direction="left-side"
		icon="<%= StringPool.BLANK %>"
		markupView="lexicon"
		message="web-content-options"
		showWhenSingleIcon="<%= true %>"
	>
		<c:if test="<%= journalContentDisplayContext.isShowEditArticleIcon() %>">

			<%
			JournalArticle latestArticle = journalContentDisplayContext.getLatestArticle();

			Map<String, Object> data = new HashMap<String, Object>();

			data.put("destroyOnHide", true);
			data.put("id", HtmlUtil.escape(portletDisplay.getNamespace()) + "editAsset");
			data.put("title", HtmlUtil.escape(latestArticle.getTitle(locale)));
			%>

			<liferay-ui:icon
				data="<%= data %>"
				id="editWebContentIcon"
				message="edit-web-content"
				url="<%= journalContentDisplayContext.getURLEdit() %>"
			/>
		</c:if>

		<c:if test="<%= journalContentDisplayContext.isShowEditTemplateIcon() %>">
			<liferay-ui:icon
				id="editTemplateIcon"
				message="edit-template"
				url="<%= journalContentDisplayContext.getURLEditTemplate() %>"
			/>
		</c:if>

		<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.PERMISSIONS) %>">
			<liferay-security:permissionsURL
				modelResource="<%= JournalArticle.class.getName() %>"
				modelResourceDescription="<%= HtmlUtil.escape(article.getTitle(locale)) %>"
				resourcePrimKey="<%= String.valueOf(article.getResourcePrimKey()) %>"
				var="permissionsURL"
				windowState="<%= LiferayWindowState.POP_UP.toString() %>"
			/>

			<liferay-ui:icon
				message="permissions"
				method="get"
				url="<%= permissionsURL %>"
				useDialog="<%= true %>"
			/>
		</c:if>

		<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.UPDATE) %>">

			<%
			JournalArticle latestArticle = journalContentDisplayContext.getLatestArticle();

			Map<String, Object> data = new HashMap<String, Object>();

			data.put("destroyOnHide", true);
			data.put("id", HtmlUtil.escape(portletDisplay.getNamespace()) + "editAsset");
			data.put("title", HtmlUtil.escape(latestArticle.getTitle(locale)));
			%>

			<liferay-ui:icon
				data="<%= data %>"
				id="basicViewHistoryIcon"
				message="view-history"
				url="<%= journalContentDisplayContext.getURLViewHistory() %>"
				useDialog="<%= true %>"
			/>
		</c:if>
	</liferay-ui:icon-menu>
</div>
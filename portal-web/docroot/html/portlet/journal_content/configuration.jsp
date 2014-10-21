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

<%@ include file="/html/portlet/journal_content/init.jsp" %>

<%
int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

JournalArticle article = journalContentDisplayContext.getArticle();

String type = ParamUtil.getString(request, "type");

if (article != null) {
	type = article.getType();
}

String ddmTemplateKey = journalContentDisplayContext.getDDMTemplateKey();
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="true" varImpl="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm1">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value='<%= configurationRenderURL + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur %>' />

	<liferay-ui:error exception="<%= NoSuchArticleException.class %>" message="the-web-content-could-not-be-found" />

	<div class="alert alert-info">
		<span class="displaying-help-message-holder <%= article == null ? StringPool.BLANK : "hide" %>">
			<liferay-ui:message key="please-select-a-web-content-from-the-list-below" />
		</span>

		<span class="displaying-article-id-holder <%= article == null ? "hide" : StringPool.BLANK %>">
			<liferay-ui:message key="displaying-content" />: <span class="displaying-article-id"><%= article != null ? HtmlUtil.escape(article.getTitle(locale)) : StringPool.BLANK %></span>
		</span>
	</div>

	<c:if test="<%= article != null %>">

		<%
		List<DDMTemplate> ddmTemplates = journalContentDisplayContext.getDDMTemplates();
		%>

		<c:if test="<%= !ddmTemplates.isEmpty() %>">
			<aui:fieldset>
				<liferay-ui:message key="override-default-template" />

				<liferay-ui:table-iterator
					list="<%= ddmTemplates %>"
					listType="com.liferay.portlet.dynamicdatamapping.model.DDMTemplate"
					rowLength="3"
					rowPadding="30"
				>

					<%
					boolean templateChecked = false;

					if (ddmTemplateKey.equals(tableIteratorObj.getTemplateKey())) {
						templateChecked = true;
					}

					if ((tableIteratorPos.intValue() == 0) && Validator.isNull(ddmTemplateKey)) {
						templateChecked = true;
					}
					%>

					<liferay-portlet:renderURL portletName="<%= PortletKeys.DYNAMIC_DATA_MAPPING %>" var="editTemplateURL">
						<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="refererPortletName" value="<%= PortletKeys.JOURNAL_CONTENT %>" />
						<portlet:param name="groupId" value="<%= String.valueOf(tableIteratorObj.getGroupId()) %>" />
						<portlet:param name="templateId" value="<%= String.valueOf(tableIteratorObj.getTemplateId()) %>" />
					</liferay-portlet:renderURL>

					<liferay-util:buffer var="linkContent">
						<aui:a href="<%= editTemplateURL %>" id="tableIteratorObjName"><%= HtmlUtil.escape(tableIteratorObj.getName(locale)) %></aui:a>
					</liferay-util:buffer>

					<aui:input checked="<%= templateChecked %>" label="<%= linkContent %>" name="overideTemplateId" onChange='<%= "if (this.checked) {document." + renderResponse.getNamespace() + "fm." + renderResponse.getNamespace() + "ddmTemplateKey.value = this.value;}" %>' type="radio" value="<%= tableIteratorObj.getTemplateKey() %>" />

					<c:if test="<%= tableIteratorObj.isSmallImage() %>">
						<br />

						<img alt="" hspace="0" src="<%= HtmlUtil.escapeAttribute(tableIteratorObj.getTemplateImageURL(themeDisplay)) %>" vspace="0" />
					</c:if>
				</liferay-ui:table-iterator>

				<br />
			</aui:fieldset>
		</c:if>
	</c:if>

	<%
	long groupId = ParamUtil.getLong(request, "groupId", themeDisplay.getScopeGroupId());

	DynamicRenderRequest dynamicRenderRequest = new DynamicRenderRequest(renderRequest);

	dynamicRenderRequest.setParameter("type", type);
	dynamicRenderRequest.setParameter("groupId", String.valueOf(groupId));

	ArticleSearch searchContainer = new ArticleSearch(dynamicRenderRequest, configurationRenderURL);

	searchContainer.setEmptyResultsMessage("no-web-content-was-found-that-matched-the-specified-filters");

	List<String> headerNames = searchContainer.getHeaderNames();

	headerNames.clear();

	headerNames.add("id");
	headerNames.add("title");
	headerNames.add("modified-date");
	headerNames.add("display-date");
	headerNames.add("author");
	%>

	<liferay-ui:search-form
		page="/html/portlet/journal/article_search.jsp"
		searchContainer="<%= searchContainer %>"
	>
		<liferay-ui:param name="groupId" value="<%= String.valueOf(groupId) %>" />
		<liferay-ui:param name="type" value="<%= HtmlUtil.escape(type) %>" />
	</liferay-ui:search-form>

	<br />

	<%
	ArticleSearchTerms searchTerms = (ArticleSearchTerms)searchContainer.getSearchTerms();

	searchTerms.setFolderIds(new ArrayList<Long>());
	searchTerms.setVersion(-1);

	List<JournalArticle> results = null;
	int total = 0;
	%>

	<%@ include file="/html/portlet/journal/article_search_results.jspf" %>

	<%
	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		JournalArticle curArticle = results.get(i);

		ResultRow row = new ResultRow(null, HtmlUtil.escapeAttribute(curArticle.getArticleId()) + EditArticleAction.VERSION_SEPARATOR + curArticle.getVersion(), i);

		StringBundler sb = new StringBundler(9);

		sb.append("javascript:");
		sb.append(renderResponse.getNamespace());
		sb.append("selectArticle('");
		sb.append(String.valueOf(curArticle.getGroupId()));
		sb.append("','");
		sb.append(HtmlUtil.escapeJS(curArticle.getArticleId()));
		sb.append("','");
		sb.append(HtmlUtil.escapeJS(curArticle.getTitle(locale)));
		sb.append("');");

		String rowHREF = sb.toString();

		// Article id

		row.addText(HtmlUtil.escape(curArticle.getArticleId()), rowHREF);

		// Title

		row.addText(HtmlUtil.escape(curArticle.getTitle(locale)), rowHREF);

		// Modified date

		row.addDate(curArticle.getModifiedDate(), rowHREF);

		// Display date

		row.addDate(curArticle.getDisplayDate(), rowHREF);

		// Author

		row.addText(HtmlUtil.escape(PortalUtil.getUserName(curArticle)), rowHREF);

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</aui:form>

<aui:form action="<%= configurationActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value='<%= configurationRenderURL + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur" + cur %>' />
	<aui:input name="preferences--groupId--" type="hidden" value="<%= journalContentDisplayContext.getArticleGroupId() %>" />
	<aui:input name="preferences--articleId--" type="hidden" value="<%= journalContentDisplayContext.getArticleId() %>" />
	<aui:input name="preferences--ddmTemplateKey--" type="hidden" value="<%= ddmTemplateKey %>" />
	<aui:input name="preferences--extensions--" type="hidden" value="<%= journalContentDisplayContext.getExtensions() %>" />

	<aui:fieldset>
		<aui:input name="portletId" type="resource" value="<%= journalContentDisplayContext.getPortletResource() %>" />
	</aui:fieldset>

	<aui:fieldset>
		<aui:field-wrapper>
			<aui:input name="preferences--showAvailableLocales--" type="checkbox" value="<%= journalContentDisplayContext.isShowAvailableLocales() %>" />
		</aui:field-wrapper>

		<aui:field-wrapper helpMessage='<%= !journalContentDisplayContext.isOpenOfficeServerEnabled() ? "enabling-openoffice-integration-provides-document-conversion-functionality" : StringPool.BLANK %>' label="enable-conversion-to">
			<liferay-ui:input-move-boxes
				leftBoxName="currentExtensions"
				leftList="<%= journalContentDisplayContext.getCurrentExtensions() %>"
				leftReorder="true"
				leftTitle="current"
				rightBoxName="availableExtensions"
				rightList="<%= journalContentDisplayContext.getAvailableExtensions() %>"
				rightTitle="available"
			/>
		</aui:field-wrapper>

		<aui:field-wrapper>
			<aui:input name="preferences--enablePrint--" type="checkbox" value="<%= journalContentDisplayContext.isEnablePrint() %>" />

			<aui:input name="preferences--enableRelatedAssets--" type="checkbox" value="<%= journalContentDisplayContext.isEnableRelatedAssets() %>" />

			<aui:input name="preferences--enableRatings--" type="checkbox" value="<%= journalContentDisplayContext.isEnableRatings() %>" />

			<c:if test="<%= PropsValues.JOURNAL_ARTICLE_COMMENTS_ENABLED %>">
				<aui:input name="preferences--enableComments--" type="checkbox" value="<%= journalContentDisplayContext.isEnableComments() %>" />

				<aui:input name="preferences--enableCommentRatings--" type="checkbox" value="<%= journalContentDisplayContext.isEnableCommentRatings() %>" />
			</c:if>

			<aui:input name="preferences--enableViewCountIncrement--" type="checkbox" value="<%= journalContentDisplayContext.isEnableViewCountIncrement() %>" />
		</aui:field-wrapper>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />selectArticle',
		function(articleGroupId, articleId, articleTitle) {
			var A = AUI();

			document.<portlet:namespace />fm.<portlet:namespace />groupId.value = articleGroupId;
			document.<portlet:namespace />fm.<portlet:namespace />articleId.value = articleId;
			document.<portlet:namespace />fm.<portlet:namespace />ddmTemplateKey.value = '';

			A.one('.displaying-article-id-holder').show();
			A.one('.displaying-help-message-holder').hide();

			var displayArticleId = A.one('.displaying-article-id');

			displayArticleId.html(A.Lang.String.escapeHTML(articleTitle) + ' (<liferay-ui:message key="modified" />)');

			displayArticleId.addClass('modified');
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />saveConfiguration',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace />extensions.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentExtensions);

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);
</aui:script>
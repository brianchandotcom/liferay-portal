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

<%@ include file="/html/portlet/journal_content/init.jsp" %>

<%
int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

String redirect = ParamUtil.getString(request, "redirect");

JournalArticle article = null;

String type = StringPool.BLANK;

try {
	if (Validator.isNotNull(articleId)) {
		article = JournalArticleLocalServiceUtil.getLatestArticle(groupId, articleId);

		article = article.toEscapedModel();

		groupId = article.getGroupId();
		type = article.getType();
	}
}
catch (NoSuchArticleException nsae) {
}

groupId = ParamUtil.getLong(request, "groupId", themeDisplay.getScopeGroupId());
type = ParamUtil.getString(request, "type", type);
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />
<liferay-portlet:renderURL portletConfiguration="true" varImpl="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm1">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value='<%= configurationRenderURL + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur %>' />

	<liferay-ui:error exception="<%= NoSuchArticleException.class %>" message="the-web-content-could-not-be-found" />

	<div class="portlet-msg-info">
		<span class="displaying-help-message-holder <%= article == null ? StringPool.BLANK : "aui-helper-hidden" %>">
			<liferay-ui:message key="please-select-a-web-content-from-the-list-below" />
		</span>

		<span class="displaying-article-id-holder <%= article == null ? "aui-helper-hidden" : StringPool.BLANK %>">
			<liferay-ui:message key="displaying-content" />: <span class="displaying-article-id"><%= article != null ? article.getTitle(locale) : StringPool.BLANK %></span>
		</span>
	</div>

	<c:if test="<%= article != null %>">

		<%
		String structureId = article.getStructureId();

		DDMStructure ddmStructure = null;

		long ddmStructureGroupId = groupId;

		if (Validator.isNotNull(structureId)) {
			ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(groupId, PortalUtil.getClassNameId(JournalArticle.class), structureId);

			List<DDMTemplate> ddmTemplates = new ArrayList<DDMTemplate>();

			if (ddmStructure != null) {
				ddmStructureGroupId = ddmStructure.getGroupId();

				ddmTemplates.addAll(DDMTemplateLocalServiceUtil.getTemplates(ddmStructureGroupId, PortalUtil.getClassNameId(DDMStructure.class), ddmStructure.getStructureId()));

				if (groupId != ddmStructureGroupId) {
					ddmTemplates.addAll(DDMTemplateLocalServiceUtil.getTemplates(groupId, PortalUtil.getClassNameId(DDMStructure.class), ddmStructure.getStructureId()));
				}
			}

			if (!ddmTemplates.isEmpty()) {
				if (Validator.isNull(ddmTemplateKey)) {
					ddmTemplateKey = article.getTemplateId();
				}
		%>

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
							<portlet:param name="groupId" value="<%= String.valueOf(tableIteratorObj.getGroupId()) %>" />
							<portlet:param name="templateId" value="<%= String.valueOf(tableIteratorObj.getTemplateId()) %>" />
						</liferay-portlet:renderURL>

						<liferay-util:buffer var="linkContent">
							<aui:a href="<%= editTemplateURL %>" id="tableIteratorObjName"><%= tableIteratorObj.getName() %></aui:a>
						</liferay-util:buffer>

						<aui:input checked="<%= templateChecked %>" label="<%= linkContent %>" name="overideTemplateId" onChange='<%= "if (this.checked) {document." + renderResponse.getNamespace() + "fm." + renderResponse.getNamespace() + "ddmTemplateKey.value = this.value;}" %>' type="radio" value="<%= tableIteratorObj.getTemplateKey() %>" />

						<c:if test="<%= tableIteratorObj.isSmallImage() %>">
							<br />

							<img border="0" hspace="0" src="<%= Validator.isNotNull(tableIteratorObj.getSmallImageURL()) ? tableIteratorObj.getSmallImageURL() : themeDisplay.getPathImage() + "/journal/template?img_id=" + tableIteratorObj.getSmallImageId() + "&t=" + WebServerServletTokenUtil.getToken(tableIteratorObj.getSmallImageId()) %>" vspace="0" />
						</c:if>
					</liferay-ui:table-iterator>

					<br />
				</aui:fieldset>

		<%
			}
		}
		%>

	</c:if>

	<%
	DynamicRenderRequest dynamicRenderRequest = new DynamicRenderRequest(renderRequest);

	dynamicRenderRequest.setParameter("type", type);
	dynamicRenderRequest.setParameter("groupId", String.valueOf(groupId));

	ArticleSearch searchContainer = new ArticleSearch(dynamicRenderRequest, configurationRenderURL);

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
		<liferay-ui:param name="type" value="<%= type %>" />
	</liferay-ui:search-form>

	<br />

	<%
	OrderByComparator orderByComparator = JournalUtil.getArticleOrderByComparator(searchContainer.getOrderByCol(), searchContainer.getOrderByType());

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

		curArticle = curArticle.toEscapedModel();

		ResultRow row = new ResultRow(null, curArticle.getArticleId() + EditArticleAction.VERSION_SEPARATOR + curArticle.getVersion(), i);

		StringBundler sb = new StringBundler(7);

		sb.append("javascript:");
		sb.append(renderResponse.getNamespace());
		sb.append("selectArticle('");
		sb.append(curArticle.getArticleId());
		sb.append("','");
		sb.append(curArticle.getTitle(locale));
		sb.append("');");

		String rowHREF = sb.toString();

		// Article id

		row.addText(curArticle.getArticleId(), rowHREF);

		// Title

		row.addText(curArticle.getTitle(locale), rowHREF);

		// Modified date

		row.addText(dateFormatDate.format(curArticle.getModifiedDate()), rowHREF);

		// Display date

		row.addText(dateFormatDate.format(curArticle.getDisplayDate()), rowHREF);

		// Author

		row.addText(PortalUtil.getUserName(curArticle), rowHREF);

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</aui:form>

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value='<%= configurationRenderURL + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur" + cur %>' />
	<aui:input name="preferences--groupId--" type="hidden" value="<%= groupId %>" />
	<aui:input name="preferences--articleId--" type="hidden" value="<%= articleId %>" />
	<aui:input name="preferences--ddmTemplateKey--" type="hidden" value="<%= ddmTemplateKey %>" />

	<aui:fieldset cssClass="aui-helper-hidden">
		<aui:field-wrapper label="portlet-id">
			<%= portletResource %>
		</aui:field-wrapper>
	</aui:fieldset>

	<br />

	<aui:fieldset>
		<aui:input name="preferences--showAvailableLocales--" type="checkbox" value="<%= showAvailableLocales %>" />

		<aui:field-wrapper helpMessage='<%= !openOfficeServerEnabled ? "enabling-openoffice-integration-provides-document-conversion-functionality" : StringPool.BLANK %>' label="enable-conversion-to">

			<%
			for (String conversion : conversions) {
			%>

				<aui:field-wrapper inlineField="<%= true %>" inlineLabel="left" label="<%= conversion.toUpperCase() %>">
					<input <%= ArrayUtil.contains(extensions, conversion) ? "checked": "" %> <%= !openOfficeServerEnabled ? "disabled" : "" %> name="<portlet:namespace />extensions" type="checkbox" value="<%= conversion %>" />
				</aui:field-wrapper>

			<%
			}
			%>

		</aui:field-wrapper>

		<aui:input name="preferences--enablePrint--" type="checkbox" value="<%= enablePrint %>" />

		<aui:input name="preferences--enableRelatedAssets--" type="checkbox" value="<%= enableRelatedAssets %>" />

		<aui:input name="preferences--enableRatings--" type="checkbox" value="<%= enableRatings %>" />

		<c:if test="<%= PropsValues.JOURNAL_ARTICLE_COMMENTS_ENABLED %>">
			<aui:input name="preferences--enableComments--" type="checkbox" value="<%= enableComments %>" />

			<aui:input name="preferences--enableCommentRatings--" type="checkbox" value="<%= enableCommentRatings %>" />
		</c:if>

		<aui:input name="preferences--enableViewCountIncrement--" type="checkbox" value="<%= enableViewCountIncrement %>" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />selectArticle',
		function(articleId, articletTitle) {
			var A = AUI();

			document.<portlet:namespace />fm.<portlet:namespace />articleId.value = articleId;
			document.<portlet:namespace />fm.<portlet:namespace />ddmTemplateKey.value = "";

			A.one('.displaying-article-id-holder').show();
			A.one('.displaying-help-message-holder').hide();

			var displayArticleId = A.one('.displaying-article-id');

			displayArticleId.set('innerHTML', articletTitle + ' (<%= UnicodeLanguageUtil.get(pageContext, "modified") %>)');
			displayArticleId.addClass('modified');
		},
		['aui-base']
	);
</aui:script>
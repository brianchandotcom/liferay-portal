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

<%@ include file="/html/portlet/search/init.jsp" %>

<%
String primarySearch = ParamUtil.getString(request, "primarySearch");

if (Validator.isNotNull(primarySearch)) {
	portalPreferences.setValue(PortletKeys.SEARCH, "primary-search", primarySearch);
}
else {
	primarySearch = portalPreferences.getValue(PortletKeys.SEARCH, "primary-search", StringPool.BLANK);
}

long groupId = ParamUtil.getLong(request, "groupId");

Group group = themeDisplay.getScopeGroup();

String keywords = ParamUtil.getString(request, "keywords");

String format = ParamUtil.getString(request, "format");

List<Portlet> portlets = PortletLocalServiceUtil.getPortlets(company.getCompanyId(), includeSystemPortlets, false);

portlets = ListUtil.sort(portlets, new PortletTitleComparator(application, locale));

Iterator itr = portlets.iterator();

List<String> portletTitles = new ArrayList<String>();

while (itr.hasNext()) {
	Portlet portlet = (Portlet)itr.next();

	if (Validator.isNull(portlet.getOpenSearchClass())) {
		itr.remove();

		continue;
	}

	OpenSearch openSearch = portlet.getOpenSearchInstance();

	if (!openSearch.isEnabled()) {
		itr.remove();

		continue;
	}

	if (groupId != 0) {
		long curPlid = PortalUtil.getPlidFromPortletId(groupId, portlet.getPortletId());

		if (!PortletPermissionUtil.contains(permissionChecker, curPlid, portlet, ActionKeys.VIEW)) {
			itr.remove();

			continue;
		}
	}

	portletTitles.add(PortalUtil.getPortletTitle(portlet, application, locale));
}

if (Validator.isNotNull(primarySearch)) {
	for (int i = 0; i < portlets.size(); i++) {
		Portlet portlet = (Portlet)portlets.get(i);

		if (portlet.getOpenSearchClass().equals(primarySearch)) {
			if (i != 0) {
				portlets.remove(i);
				portlets.add(0, portlet);
			}

			break;
		}
	}
}

LinkedHashMap groupParams = new LinkedHashMap();

groupParams.put("active", Boolean.FALSE);

int inactiveGroupsCount = GroupLocalServiceUtil.searchCount(themeDisplay.getCompanyId(), null, null, groupParams);
%>

<liferay-portlet:renderURL varImpl="searchURL">
	<portlet:param name="struts_action" value="/search/search" />
</liferay-portlet:renderURL>

<aui:form action="<%= searchURL %>" method="get" name="fm" onSubmit='<%= renderResponse.getNamespace() + "search();" %>'>
	<liferay-portlet:renderURLParams varImpl="searchURL" />
	<aui:input name="format" type="hidden" value="<%= format %>" />

	<aui:fieldset>
		<aui:input inlineField="<%= true %>" label="" name="keywords" size="30" value="<%= keywords %>" />

		<aui:select inlineField="<%= true %>" label="" name="groupId">
			<aui:option label="everything" selected="<%= groupId == 0 %>" value="0" />
			<aui:option label='<%= "this-" + (group.isOrganization() ? "organization" : "site") %>' selected="<%= groupId != 0 %>" value="<%= group.getGroupId() %>" />
		</aui:select>

		<aui:input align="absmiddle" border="0" inlineField="<%= true %>" label="" name="search" src='<%= themeDisplay.getPathThemeImages() + "/common/search.png" %>' title="search" type="image" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button onClick='<%= renderResponse.getNamespace() + "addSearchProvider();" %>' value='<%= LanguageUtil.format(pageContext, "add-x-as-a-search-provider", company.getName(), false) %>' />
	</aui:button-row>

	<div class="search-msg">
		<c:choose>
			<c:when test="<%= portletTitles.isEmpty() %>">
				<liferay-ui:message key="no-portlets-were-searched" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="searched" /> <%= StringUtil.merge(portletTitles, StringPool.COMMA_AND_SPACE) %>
			</c:otherwise>
		</c:choose>
	</div>

	<%
	int totalResults = 0;

	for (int i = 0; i < portlets.size(); i++) {
		Portlet portlet = (Portlet)portlets.get(i);

		OpenSearch openSearch = portlet.getOpenSearchInstance();

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("struts_action", "/search/search");
		portletURL.setParameter("keywords", keywords);
		portletURL.setParameter("format", format);

		//List<String> headerNames = new ArrayList<String>();

		//headerNames.add("#");
		//headerNames.add("summary");
		//headerNames.add("tags");
		//headerNames.add("score");

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM + i, SearchContainer.DEFAULT_DELTA, portletURL, null, LanguageUtil.format(pageContext, "no-results-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>"));

		if (Validator.isNotNull(primarySearch) && primarySearch.equals(portlet.getOpenSearchClass())) {
			int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM + i);

			if (delta > 0) {
				searchContainer.setDelta(delta);
			}
			else {
				searchContainer.setDelta(SearchContainer.DEFAULT_DELTA);
			}
		}

		String portletTitle = PortalUtil.getPortletTitle(portlet, application, locale);

		List resultRows = new ArrayList();

		try {
			String xml = openSearch.search(request, groupId, themeDisplay.getUserId(), keywords, searchContainer.getCur(), searchContainer.getDelta(), format);

			xml = XMLFormatter.stripInvalidChars(xml);

			Document doc = SAXReaderUtil.read(xml);

			Element root = doc.getRootElement();

			//portletTitle = root.elementText("title");

			String[] queryTerms = StringUtil.split(root.elementText("queryTerms"), StringPool.COMMA_AND_SPACE);

			List<Element> entries = root.elements("entry");

			int total = GetterUtil.getInteger(root.elementText(OpenSearchUtil.getQName("totalResults", OpenSearchUtil.OS_NAMESPACE)));

			resultRows = searchContainer.getResultRows();

			for (int j = 0; j < entries.size(); j++) {
				try {
					Element el = entries.get(j);

					ResultRow row = new ResultRow(doc, String.valueOf(j), j);

					// Position

					//row.addText(SearchEntry.DEFAULT_ALIGN, "top", searchContainer.getStart() + j + 1 + StringPool.PERIOD);

					// Summary

					String entryClassName = el.elementText("entryClassName");
					String entryTitle = el.elementText("title");
					String entryHref = el.element("link").attributeValue("href");
					String summary = el.elementText("summary");

					// Group id

					long entryScopeGroupId = GetterUtil.getLong(el.elementText(OpenSearchUtil.getQName("scopeGroupId", OpenSearchUtil.LIFERAY_NAMESPACE)));

					if (Validator.isNotNull(entryScopeGroupId) && (inactiveGroupsCount > 0)) {
						Group entryGroup = GroupServiceUtil.getGroup(entryScopeGroupId);

						if (entryGroup.isLayout()) {
							entryGroup = GroupLocalServiceUtil.getGroup(entryGroup.getParentGroupId());
						}

						if (!entryGroup.isActive()) {
							total--;

							continue;
						}
					}

					String portletId = portlet.getPortletId();

					if (portletId.equals(PortletKeys.DOCUMENT_LIBRARY) || (portletId.equals(PortletKeys.SEARCH) && entryClassName.equals(DLFileEntryConstants.getClassName()))) {
						long fileEntryId = GetterUtil.getLong(HttpUtil.getParameter(entryHref, "_20_fileEntryId", false));

						FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(fileEntryId);

						entryTitle = fileEntry.getTitle();

						if (portletId.equals(PortletKeys.SEARCH)) {
							entryTitle = PortalUtil.getPortletTitle(PortletKeys.DOCUMENT_LIBRARY, locale) + " " + CharPool.RAQUO + " " + entryTitle;
						}

						if (dlLinkToViewURL) {
							long dlPlid = PortalUtil.getPlidFromPortletId(fileEntry.getRepositoryId(), PortletKeys.DOCUMENT_LIBRARY);

							PortletURL viewURL = new PortletURLImpl(request, PortletKeys.DOCUMENT_LIBRARY, dlPlid, PortletRequest.RENDER_PHASE);

							viewURL.setParameter("struts_action", "/document_library/view_file_entry");
							viewURL.setParameter("redirect", currentURL);
							viewURL.setParameter("fileEntryId", String.valueOf(fileEntry.getFileEntryId()));

							entryHref = viewURL.toString();
						}
					}

					StringBundler rowSB = new StringBundler();

					rowSB.append("<a class=\"entry-title\" href=\"");
					rowSB.append(entryHref);
					rowSB.append("\">");
					rowSB.append(StringUtil.highlight(HtmlUtil.escape(entryTitle), queryTerms));
					rowSB.append("</a>");

					if (Validator.isNotNull(summary)) {
						rowSB.append("<br />");
						rowSB.append(StringUtil.highlight(HtmlUtil.escape(summary), queryTerms));
					}

					rowSB.append("<br />");

					// Tags

					String tagsString = el.elementText("tags");

					tagsString = tagsString.replaceAll("[\\[\\]]","");

					String[] tags = StringUtil.split(tagsString);

					String[] tagsQueryTerms = queryTerms;

					if (StringUtil.startsWith(keywords, Field.ASSET_TAG_NAMES + StringPool.COLON)) {
						tagsQueryTerms = new String[] {StringUtil.replace(keywords, Field.ASSET_TAG_NAMES + StringPool.COLON, StringPool.BLANK)};
					}

					for (int k = 0; k < tags.length; k++) {
						String tag = tags[k];

						String newKeywords = tag.trim();

						if (newKeywords.matches(".+\\s.+")) {
							newKeywords = StringPool.QUOTE + tag + StringPool.QUOTE;
						}

						PortletURL tagURL = PortletURLUtil.clone(portletURL, renderResponse);

						tagURL.setParameter("keywords", Field.ASSET_TAG_NAMES + StringPool.COLON + newKeywords);
						tagURL.setParameter("format", format);

						if (k == 0) {
							rowSB.append("<div class=\"entry-tags\">");
							rowSB.append("<div class=\"taglib-asset-tags-summary\">");
						}

						rowSB.append("<a class=\"tag\" href=\"");
						rowSB.append(tagURL.toString());
						rowSB.append("\">");
						rowSB.append(StringUtil.highlight(tag, tagsQueryTerms));
						rowSB.append("</a>");

						if ((k + 1) == tags.length) {
							rowSB.append("</div>");
							rowSB.append("</div>");
						}
					}

					row.addText(rowSB.toString());

					// Ratings

					//String ratings = el.elementText("ratings");

					//row.addText(ratings);

					// Score

					//String score = el.elementText(OpenSearchUtil.getQName("score", OpenSearchUtil.RELEVANCE_NAMESPACE));

					//row.addText(score);

					// Add result row

					resultRows.add(row);
				}
				catch (Exception e) {
					_log.error("Error retrieving individual search result of type " + portlet.getOpenSearchClass(), e);

					total--;
				}
			}

			searchContainer.setTotal(total);
		}
		catch (Exception e) {
			_log.error("Error displaying content of type " + portlet.getOpenSearchClass(), e);
		}
	%>

		<c:if test="<%= !resultRows.isEmpty() %>">

			<%
			totalResults = totalResults + searchContainer.getTotal();
			%>

			<div class="section-title">
				<%= portletTitle %> (<%= searchContainer.getTotal() %>)
			</div>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />

			<c:choose>
				<c:when test="<%= (searchContainer.getTotal() == resultRows.size()) || (Validator.isNotNull(primarySearch) && portlet.getOpenSearchClass().equals(primarySearch)) %>">
					<div class="search-paginator-container">
						<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" type="more" />
					</div>
				</c:when>
				<c:otherwise>
					<div class="more-results">
						<portlet:renderURL var="moreResultsURL">
							<portlet:param name="struts_action" value="/search/search" />
							<portlet:param name="primarySearch" value="<%= portlet.getOpenSearchClass() %>" />
							<portlet:param name="keywords" value="<%= HtmlUtil.escapeHREF(keywords) %>" />
							<portlet:param name="format" value="<%= format %>" />
						</portlet:renderURL>

						<aui:a href="<%= moreResultsURL %>"><%= LanguageUtil.format(pageContext, "more-x-results", portletTitle) %> &raquo;</aui:a>
					</div>
				</c:otherwise>
			</c:choose>
		</c:if>

	<%
	}
	%>

	<c:if test="<%= totalResults == 0 %>">
		<div class="no-results">
			<%= LanguageUtil.format(pageContext, "no-results-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>") %>
		</div>
	</c:if>
</aui:form>

<aui:script>
	function <portlet:namespace />addSearchProvider() {
		window.external.AddSearchProvider("<%= themeDisplay.getPortalURL() %><%= PortalUtil.getPathMain() %>/search/open_search_description.xml?p_l_id=<%= themeDisplay.getPlid() %>&groupId=<%= groupId %>");
	}

	function <portlet:namespace />search() {
		var keywords = document.<portlet:namespace />fm.<portlet:namespace />keywords.value;

		keywords = keywords.replace(/^\s+|\s+$/, '');

		if (keywords != '') {
			document.<portlet:namespace />fm.submit();
		}
	}

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />keywords);
	</c:if>
</aui:script>

<%
String pageSubtitle = LanguageUtil.get(pageContext, "search-results");
String pageDescription = LanguageUtil.get(pageContext, "search-results");
String pageKeywords = LanguageUtil.get(pageContext, "search");

if (!portletTitles.isEmpty()) {
	pageDescription = LanguageUtil.get(pageContext, "searched") + StringPool.SPACE + StringUtil.merge(portletTitles, StringPool.COMMA_AND_SPACE);
}

if (Validator.isNotNull(keywords)) {
	pageKeywords = keywords;

	if (StringUtil.startsWith(pageKeywords, Field.ASSET_TAG_NAMES + StringPool.COLON)) {
		pageKeywords = StringUtil.replace(pageKeywords, Field.ASSET_TAG_NAMES + StringPool.COLON, StringPool.BLANK);
	}
}

PortalUtil.setPageSubtitle(pageSubtitle, request);
PortalUtil.setPageDescription(pageDescription, request);
PortalUtil.setPageKeywords(pageKeywords, request);
%>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.search.search_jsp");
%>
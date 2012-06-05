<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
JournalFolder folder = (JournalFolder)request.getAttribute("view.jsp-folder");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/journal/view");

ArticleSearch searchContainer = new ArticleSearch(renderRequest, portletURL);

ArticleDisplayTerms displayTerms = (ArticleDisplayTerms)searchContainer.getDisplayTerms();

boolean advancedSearch = ParamUtil.getBoolean(request, displayTerms.ADVANCED_SEARCH, false);
%>

<div class='taglib-search-toggle taglib-search-toggle-advanced <%= advancedSearch ? "" : "aui-helper-hidden" %>' id="<portlet:namespace />advancedSearch">
	<aui:input name="<%= displayTerms.ADVANCED_SEARCH %>" type="hidden" value="<%= true %>" />

	<liferay-util:buffer var="andOperator">
		<aui:select cssClass="inline-control" inlineField="<%= true %>" label="" name="<%= displayTerms.AND_OPERATOR %>">
			<aui:option label="all" selected="<%= displayTerms.isAndOperator() %>" value="1" />
			<aui:option label="any" selected="<%= !displayTerms.isAndOperator() %>" value="0" />
		</aui:select>
	</liferay-util:buffer>

	<liferay-ui:message arguments="<%= andOperator %>" key="match-x-of-the-following-fields" />

	<aui:fieldset>
		<aui:input label="id" name="<%= displayTerms.ARTICLE_ID %>" size="20" value="<%= displayTerms.getArticleId() %>" />

		<aui:input name="<%= displayTerms.TITLE %>" size="20" type="text" value="<%= displayTerms.getTitle() %>" />

		<aui:input name="<%= displayTerms.DESCRIPTION %>" size="20" type="text" value="<%= displayTerms.getDescription() %>" />

		<aui:input name="<%= displayTerms.CONTENT %>" size="20" type="text" value="<%= displayTerms.getContent() %>" />

		<aui:select name="<%= displayTerms.TYPE %>">
			<aui:option value=""></aui:option>

			<%
			for (int i = 0; i < JournalArticleConstants.TYPES.length; i++) {
			%>

				<aui:option label="<%= JournalArticleConstants.TYPES[i] %>" selected="<%= displayTerms.getType().equals(JournalArticleConstants.TYPES[i]) %>" />

			<%
			}
			%>

		</aui:select>

		<c:if test="<%= !portletName.equals(PortletKeys.JOURNAL) || ((themeDisplay.getScopeGroupId() == themeDisplay.getCompanyGroupId()) && (Validator.isNotNull(displayTerms.getStructureId()) || Validator.isNotNull(displayTerms.getTemplateId()))) %>">

			<%
			List<Group> mySites = user.getMySites();

			List<Layout> scopeLayouts = new ArrayList<Layout>();

			scopeLayouts.addAll(LayoutLocalServiceUtil.getScopeGroupLayouts(themeDisplay.getParentGroupId(), false));
			scopeLayouts.addAll(LayoutLocalServiceUtil.getScopeGroupLayouts(themeDisplay.getParentGroupId(), true));
			%>

			<aui:select label="my-sites" name="<%= displayTerms.GROUP_ID %>" showEmptyOption="<%= (themeDisplay.getScopeGroupId() == themeDisplay.getCompanyGroupId()) && (Validator.isNotNull(displayTerms.getStructureId()) || Validator.isNotNull(displayTerms.getTemplateId())) %>">
				<aui:option label="global" selected="<%= displayTerms.getGroupId() == themeDisplay.getCompanyGroupId() %>" value="<%= themeDisplay.getCompanyGroupId() %>" />

				<%
				for (Group mySite : mySites) {
					if (mySite.hasStagingGroup() && !mySite.isStagedRemotely() && mySite.isStagedPortlet(PortletKeys.JOURNAL)) {
						mySite = mySite.getStagingGroup();
					}
				%>

					<aui:option label='<%= mySite.isUser() ? "my-site" : HtmlUtil.escape(mySite.getDescriptiveName(locale)) %>' selected="<%= displayTerms.getGroupId() == mySite.getGroupId() %>" value="<%= mySite.getGroupId() %>" />

				<%
				}
				%>

				<c:if test="<%= !scopeLayouts.isEmpty() %>">

					<%
					for (Layout curScopeLayout : scopeLayouts) {
					%>

						<%
						Group scopeGroup = curScopeLayout.getScopeGroup();

						String label = HtmlUtil.escape(curScopeLayout.getName(locale));

						if (curScopeLayout.equals(layout)) {
							label = LanguageUtil.get(pageContext, "current-page") + " (" + label + ")";
						}
						%>

						<aui:option label='<%= label %>' selected="<%= displayTerms.getGroupId() == scopeGroup.getGroupId() %>" value="<%= scopeGroup.getGroupId() %>" />

					<%
					}
					%>

				</c:if>
			</aui:select>
		</c:if>

		<c:if test="<%= portletName.equals(PortletKeys.JOURNAL) %>">
			<aui:select name="<%= displayTerms.STATUS %>">
				<aui:option value=""></aui:option>
				<aui:option label="draft" selected='<%= displayTerms.getStatus().equals("draft") %>' />
				<aui:option label="pending" selected='<%= displayTerms.getStatus().equals("pending") %>' />
				<aui:option label="approved" selected='<%= displayTerms.getStatus().equals("approved") %>' />
				<aui:option label="expired" selected='<%= displayTerms.getStatus().equals("expired") %>' />
			</aui:select>
		</c:if>
	</aui:fieldset>

	<aui:button type="submit" value="search" />
</div>

<%
String keywords = ParamUtil.getString(request, "keywords");
%>

<c:if test="<%= (Validator.isNotNull(keywords) || advancedSearch) %>">
	<div id="<portlet:namespace />searchInfo">
		<div class="search-info">

			<%
			String message = LanguageUtil.get(pageContext, "advanced-search");

			if (advancedSearch) {
				if (folder != null) {
					message = LanguageUtil.format(pageContext, "searched-for-x-in-x", new Object[] {HtmlUtil.escape(keywords), folder.getName()});
				}
				else {
					message = LanguageUtil.format(pageContext, "searched-for-x-everywhere", HtmlUtil.escape(keywords));
				}
			}
			%>

			<span class="keywords">
				<%= message %>
				<c:if test="<%= folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID %>">

					<%
					String taglibOnClick = renderResponse.getNamespace() + "changeSearchFolder()";
					%>

					<span class="change-search-folder">
						(<aui:a href="javascript:;" label="search-everywhere" onClick="<%= taglibOnClick %>" title="search-everywhere" />)
					</span>
				</c:if>
			</span>


			<liferay-portlet:renderURL varImpl="closeSearchURL">
				<portlet:param name="struts_action" value="/journal/view" />
				<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:icon cssClass="close-search" id="closeSearch" image="../aui/closethick" url="<%= closeSearchURL.toString() %>" />
		</div>

		<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
			<aui:script>
				Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace />keywords);
			</aui:script>
		</c:if>
	</div>

	<aui:script>
		function <portlet:namespace />changeSearchFolder() {
			<liferay-portlet:renderURL varImpl="changeSearchFolderURL">
				<portlet:param name="struts_action" value="/journal/view" />
				<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
			</liferay-portlet:renderURL>

			<c:choose>
				<c:when test="<%= advancedSearch %>">
					submitForm(document.<portlet:namespace />fm, '<%= changeSearchFolderURL.toString() %>');
				</c:when>
				<c:otherwise>
					submitForm(document.<portlet:namespace />fm1, '<%= changeSearchFolderURL.toString() %>');
				</c:otherwise>
			</c:choose>
		}
	</aui:script>
</c:if>

<aui:script>
	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) || windowState.equals(LiferayWindowState.POP_UP) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.ARTICLE_ID %>);
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.KEYWORDS %>);
	</c:if>
</aui:script>
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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
long folderId = GetterUtil.getLong((String)liferayPortletRequest.getAttribute("view.jsp-folderId"));

PortletURL portletURL = liferayPortletResponse.createRenderURL();

portletURL.setParameter("struts_action", "/journal/view");
portletURL.setParameter("folderId", String.valueOf(folderId));

ArticleSearch searchContainer = new ArticleSearch(liferayPortletRequest, portletURL);

ArticleDisplayTerms displayTerms = (ArticleDisplayTerms)searchContainer.getDisplayTerms();
%>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm1" onSubmit="event.preventDefault();">
	<liferay-ui:search-toggle
		buttonLabel="search"
		displayTerms="<%= displayTerms %>"
		id="<%= renderResponse.getNamespace() %>"
	>

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

				scopeLayouts.addAll(LayoutLocalServiceUtil.getScopeGroupLayouts(themeDisplay.getSiteGroupId(), false));
				scopeLayouts.addAll(LayoutLocalServiceUtil.getScopeGroupLayouts(themeDisplay.getSiteGroupId(), true));
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

					if (!scopeLayouts.isEmpty()) {
						for (Layout curScopeLayout : scopeLayouts) {
							Group scopeGroup = curScopeLayout.getScopeGroup();

							String label = HtmlUtil.escape(curScopeLayout.getName(locale));

							if (curScopeLayout.equals(layout)) {
								label = LanguageUtil.get(pageContext, "current-page") + " (" + label + ")";
							}
							%>

							<aui:option label="<%= label %>" selected="<%= displayTerms.getGroupId() == scopeGroup.getGroupId() %>" value="<%= scopeGroup.getGroupId() %>" />

					<%
						}
					}
					%>

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
	</liferay-ui:search-toggle>
</aui:form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace /><%= displayTerms.KEYWORDS %>);
	</aui:script>
</c:if>
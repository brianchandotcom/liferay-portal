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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WebKeys.WIKI_PAGE);

String title = wikiPage.getTitle();
String newTitle = ParamUtil.get(request, "newTitle", StringPool.BLANK);
%>

<liferay-util:include page="/html/portlet/wiki/top_links.jsp" />

<liferay-ui:error exception="<%= DuplicatePageException.class %>" message="there-is-already-a-page-with-the-specified-title" />
<liferay-ui:error exception="<%= PageTitleException.class %>" message="please-enter-a-valid-title" />

<%@ include file="/html/portlet/wiki/page_name.jspf" %>

<portlet:actionURL var="movePageURL">
	<portlet:param name="struts_action" value="/wiki/move_page" />
</portlet:actionURL>

<aui:form action="<%= movePageURL %>" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "changeParent();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="nodeId" type="hidden" value="<%= node.getNodeId() %>" />
	<aui:input name="title" type="hidden" value="<%= title %>" />

	<liferay-ui:tabs
		names="rename,change-parent"
		refresh="<%= false %>"
	>
		<liferay-ui:section>
			<div class="alert alert-info">
				<liferay-ui:message key="use-the-form-below-to-rename-a-page,-moving-all-of-its-history-to-the-new-name" />
			</div>

			<aui:fieldset>
				<aui:field-wrapper label="current-title">
					<%= wikiPage.getTitle() %>
				</aui:field-wrapper>

				<aui:input name="newTitle" value="<%= newTitle %>" />

				<aui:button-row>
					<aui:button onClick='<%= renderResponse.getNamespace() + "renamePage();" %>' value="rename" />

					<aui:button href="<%= redirect %>" type="cancel" />
				</aui:button-row>
			</aui:fieldset>
		</liferay-ui:section>
		<liferay-ui:section>
			<div class="alert alert-info">
				<liferay-ui:message key="use-the-form-below-to-move-a-page-and-all-of-its-history-to-be-the-child-of-a-new-parent-page" />
			</div>

			<%
			String parentText = StringPool.BLANK;

			WikiPage parentPage = wikiPage.getViewableParentPage();

			if (parentPage == null) {
				parentText = StringPool.OPEN_PARENTHESIS + LanguageUtil.get(pageContext, "none") + StringPool.CLOSE_PARENTHESIS;
			}
			else {
				parentText = parentPage.getTitle();

				parentPage = parentPage.getViewableParentPage();

				while (parentPage != null) {
					parentText = parentPage.getTitle() + " &raquo; " + parentText;

					parentPage = parentPage.getViewableParentPage();
				}
			}

			List<WikiPage> childPages = WikiPageLocalServiceUtil.getChildren(node.getNodeId(), true, StringPool.BLANK);

			childPages = ListUtil.sort(childPages);

			childPages.remove(wikiPage);
			%>

			<aui:fieldset>
				<aui:field-wrapper label="current-parent">
					<%= parentText %>
				</aui:field-wrapper>

				<%
				boolean newParentAvailable = true;

				if (childPages.isEmpty()) {
					newParentAvailable = false;
				%>

					<aui:select disabled="<%= true %>" label="new-parent" name="newParentTitle">
						<aui:option label="not-available" value="" />
					</aui:select>

				<%
				}
				else {
				%>

					<aui:select label="new-parent" name="newParentTitle">
						<aui:option label="none" selected="<%= Validator.isNull(wikiPage.getParentTitle()) %>" value="" />

						<%
						for (WikiPage childPage : childPages) {
							if (Validator.isNull(childPage.getRedirectTitle())) {
								request.setAttribute(WebKeys.WIKI_TREE_WALKER_PARENT, childPage);
								request.setAttribute(WebKeys.WIKI_TREE_WALKER_PAGE, wikiPage);
								request.setAttribute(WebKeys.WIKI_TREE_WALKER_DEPTH, 1);
						%>

								<liferay-util:include page="/html/portlet/wiki/page_tree.jsp" />

						<%
							}
						}
						%>

					</aui:select>

				<%
				}
				%>

				<aui:button-row>
					<aui:button disabled="<%= !newParentAvailable %>" type="submit" value="change-parent" />

					<aui:button href="<%= redirect %>" type="cancel" />
				</aui:button-row>
			</aui:fieldset>
		</liferay-ui:section>
	</liferay-ui:tabs>
</aui:form>

<aui:script>
	function <portlet:namespace />changeParent() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "changeParent";

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />renamePage() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "rename";

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>
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
ArticleSearch searchContainer = (ArticleSearch)request.getAttribute("liferay-ui:search:searchContainer");

ArticleDisplayTerms displayTerms = (ArticleDisplayTerms)searchContainer.getDisplayTerms();
%>

<liferay-ui:search-toggle
	id="toggle_id_journal_article_search"
	displayTerms="<%= displayTerms %>"
	buttonLabel="search"
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
</liferay-ui:search-toggle>

<%
boolean showAddArticleButtonButton = false;
boolean showPermissionsButton = false;
boolean showSubscribeLink = false;

if (portletName.equals(PortletKeys.JOURNAL)) {
	showAddArticleButtonButton = JournalPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_ARTICLE);
	showPermissionsButton = JournalPermission.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS);
	showSubscribeLink = JournalPermission.contains(permissionChecker, scopeGroupId, ActionKeys.SUBSCRIBE);
}
%>

<c:if test="<%= showAddArticleButtonButton || showPermissionsButton %>">
	<aui:button-row cssClass="add-permission-button-row">
		<c:if test="<%= showAddArticleButtonButton %>">
			<div class="add-article-selector">
				<%@ include file="/html/portlet/journal/add_article.jspf" %>
			</div>
		</c:if>

		<c:if test="<%= showPermissionsButton %>">
			<liferay-security:permissionsURL
				modelResource="com.liferay.portlet.journal"
				modelResourceDescription="<%= HtmlUtil.escape(themeDisplay.getScopeGroupName()) %>"
				resourcePrimKey="<%= String.valueOf(scopeGroupId) %>"
				var="permissionsURL"
			/>

			<aui:button href="<%= permissionsURL %>" value="permissions" />
		</c:if>
	</aui:button-row>
</c:if>

<c:if test="<%= showSubscribeLink %>">
	<c:choose>
		<c:when test="<%= SubscriptionLocalServiceUtil.isSubscribed(company.getCompanyId(), user.getUserId(), JournalArticle.class.getName(), scopeGroupId) %>">
			<portlet:actionURL var="unsubscribeURL">
				<portlet:param name="struts_action" value="/journal/edit_article" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:actionURL>

			<liferay-ui:icon cssClass="subscribe-link" image="unsubscribe" label="<%= true %>" url="<%= unsubscribeURL %>" />
		</c:when>
		<c:otherwise>
			<portlet:actionURL var="subscribeURL">
				<portlet:param name="struts_action" value="/journal/edit_article" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:actionURL>

			<liferay-ui:icon cssClass="subscribe-link" image="subscribe" label="<%= true %>" url="<%= subscribeURL %>" />
		</c:otherwise>
	</c:choose>
</c:if>

<c:if test="<%= Validator.isNotNull(displayTerms.getStructureId()) %>">
	<aui:input name="<%= displayTerms.STRUCTURE_ID %>" type="hidden" value="<%= displayTerms.getStructureId() %>" />

	<c:if test="<%= showAddArticleButtonButton %>">
		<div class="portlet-msg-info">

			<%
			JournalStructure structure = JournalStructureLocalServiceUtil.getStructure(scopeGroupId, displayTerms.getStructureId());
			%>

			<liferay-ui:message arguments="<%= structure.getName(locale) %>" key="showing-content-filtered-by-structure-x" /> (<a href="javascript:<portlet:namespace />addArticle();"><liferay-ui:message key="add-new-web-content" /></a>)
		</div>
	</c:if>
</c:if>

<c:if test="<%= Validator.isNotNull(displayTerms.getTemplateId()) %>">
	<aui:input name="<%= displayTerms.TEMPLATE_ID %>" type="hidden" value="<%= displayTerms.getTemplateId() %>" />

	<c:if test="<%= showAddArticleButtonButton %>">
		<div class="portlet-msg-info">

			<%
			JournalTemplate template = JournalTemplateLocalServiceUtil.getTemplate(scopeGroupId, displayTerms.getTemplateId());
			%>

			<liferay-ui:message arguments="<%= template.getName(locale) %>" key="showing-content-filtered-by-template-x" /> (<a href="javascript:<portlet:namespace />addArticle();"><liferay-ui:message key="add-new-web-content" /></a>)
		</div>
	</c:if>
</c:if>

<aui:script>
	function <portlet:namespace />addArticle() {
		var url = '<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" portletName="<%= PortletKeys.JOURNAL %>"><portlet:param name="struts_action" value="/journal/edit_article" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="backURL" value="<%= currentURL %>" /><portlet:param name="structureId" value="<%= displayTerms.getStructureId() %>" /><portlet:param name="templateId" value="<%= displayTerms.getTemplateId() %>" /></liferay-portlet:renderURL>';

		if (toggle_id_journal_article_searchcurClickValue == 'basic') {
			url += '&<portlet:namespace /><%= displayTerms.TITLE %>=' + document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.KEYWORDS %>.value;

			submitForm(document.hrefFm, url);
		}
		else {
			document.<portlet:namespace />fm.method = 'post';

			submitForm(document.<portlet:namespace />fm, url);
		}
	}

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) || windowState.equals(LiferayWindowState.POP_UP) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.ARTICLE_ID %>);
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.KEYWORDS %>);
	</c:if>
</aui:script>
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
String backURL = ParamUtil.getString(request, "backURL");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

String displayStyle = ParamUtil.getString(request, "displayStyle", "list");

JournalArticle article = journalDisplayContext.getArticle();
%>

<c:choose>
	<c:when test="<%= article == null %>">
		<div class="alert alert-danger">
			<liferay-ui:message key="the-selected-web-content-no-longer-exists" />
		</div>
	</c:when>
	<c:otherwise>

		<%
		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(backURL);

		renderResponse.setTitle(article.getTitle(locale));

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view_article_history.jsp");
		portletURL.setParameter("redirect", redirect);
		portletURL.setParameter("referringPortletResource", referringPortletResource);
		portletURL.setParameter("groupId", String.valueOf(article.getGroupId()));
		portletURL.setParameter("articleId", article.getArticleId());
		%>

		<aui:nav-bar markupView="lexicon">
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label='<%= LanguageUtil.get(resourceBundle, "versions") %>' selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<liferay-frontend:management-bar
			includeCheckBox="<%= true %>"
			searchContainerId="articleVersions"
		>
			<liferay-frontend:management-bar-buttons>
				<liferay-frontend:management-bar-display-buttons
					displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
					portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
					selectedDisplayStyle="<%= displayStyle %>"
				/>
			</liferay-frontend:management-bar-buttons>

			<liferay-frontend:management-bar-filters>
				<liferay-frontend:management-bar-navigation
					navigationKeys='<%= new String[] {"all"} %>'
					portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
				/>

				<liferay-frontend:management-bar-sort
					orderByCol="<%= journalDisplayContext.getOrderByCol() %>"
					orderByType="<%= journalDisplayContext.getOrderByType() %>"
					orderColumns='<%= new String[] {"version", "display-date", "modified-date"} %>'
					portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
				/>
			</liferay-frontend:management-bar-filters>

			<liferay-frontend:management-bar-action-buttons>
				<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.DELETE) %>">
					<liferay-frontend:management-bar-button href="javascript:;" icon="trash" id="deleteArticles" label="delete" />
				</c:if>

				<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.EXPIRE) %>">
					<liferay-frontend:management-bar-button href="javascript:;" icon="time" id="expireArticles" label="expire" />
				</c:if>
			</liferay-frontend:management-bar-action-buttons>
		</liferay-frontend:management-bar>

		<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
			<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
			<aui:input name="groupId" type="hidden" value="<%= String.valueOf(article.getGroupId()) %>" />

			<liferay-ui:search-container
				id="articleVersions"
				rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
				searchContainer="<%= new ArticleSearch(renderRequest, portletURL) %>"
				total="<%= JournalArticleServiceUtil.getArticlesCountByArticleId(article.getGroupId(), article.getArticleId()) %>"
			>
				<liferay-ui:search-container-results
					results="<%= JournalArticleServiceUtil.getArticlesByArticleId(article.getGroupId(), article.getArticleId(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.journal.model.JournalArticle"
					modelVar="articleVersion"
				>

					<%
					row.setPrimaryKey(articleVersion.getArticleId() + JournalPortlet.VERSION_SEPARATOR + articleVersion.getVersion());
					%>

					<c:choose>
						<c:when test='<%= displayStyle.equals("descriptive") %>'>
							<liferay-ui:search-container-column-text>
								<liferay-ui:user-portrait
									cssClass="user-icon-lg"
									userId="<%= articleVersion.getUserId() %>"
								/>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-text
								colspan="<%= 2 %>"
							>

								<%
								Date createDate = articleVersion.getModifiedDate();

								String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
								%>

								<h6 class="text-default">
									<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(articleVersion.getUserName()), modifiedDateDescription} %>" key="x-modified-x-ago" />
								</h6>

								<h5>
									<%= HtmlUtil.escape(articleVersion.getTitle(locale)) %>
								</h5>

								<h6 class="text-default">
									<aui:workflow-status markupView="lexicon" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= articleVersion.getStatus() %>" version="<%= String.valueOf(articleVersion.getVersion()) %>" />
								</h6>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-jsp
								path="/article_version_action.jsp"
							/>
						</c:when>
						<c:when test='<%= displayStyle.equals("icon") %>'>

							<%
							row.setCssClass("entry-card lfr-asset-item");
							%>

							<liferay-ui:search-container-column-text>

								<%
								String articleImageURL = articleVersion.getArticleImageURL(themeDisplay);
								%>

								<c:choose>
									<c:when test="<%= Validator.isNotNull(articleImageURL) %>">
										<liferay-frontend:vertical-card
											actionJsp="/article_version_action.jsp"
											actionJspServletContext="<%= application %>"
											imageUrl="<%= articleImageURL %>"
											resultRow="<%= row %>"
											rowChecker="<%= searchContainer.getRowChecker() %>"
											title="<%= articleVersion.getTitle(locale) %>"
										>
											<%@ include file="/article_version_vertical_card.jspf" %>
										</liferay-frontend:vertical-card>
									</c:when>
									<c:otherwise>
										<liferay-frontend:icon-vertical-card
											actionJsp="/article_version_action.jsp"
											actionJspServletContext="<%= application %>"
											icon="web-content"
											resultRow="<%= row %>"
											rowChecker="<%= searchContainer.getRowChecker() %>"
											title="<%= articleVersion.getTitle(locale) %>"
										>
											<%@ include file="/article_version_vertical_card.jspf" %>
										</liferay-frontend:icon-vertical-card>
									</c:otherwise>
								</c:choose>
							</liferay-ui:search-container-column-text>
						</c:when>
						<c:when test='<%= displayStyle.equals("list") %>'>
							<liferay-ui:search-container-column-text
								cssClass="id-column text-column"
								name="id"
								value="<%= HtmlUtil.escape(articleVersion.getArticleId()) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="content-column title-column"
								name="title"
								truncate="<%= true %>"
								value="<%= HtmlUtil.escape(articleVersion.getTitle(locale)) %>"
							/>

							<liferay-ui:search-container-column-text
								cssClass="text-column version-column"
								name="version"
								orderable="<%= true %>"
							/>

							<liferay-ui:search-container-column-status
								cssClass="text-column status-column"
								name="status"
							/>

							<liferay-ui:search-container-column-date
								cssClass="modified-date-column text-column"
								name="modified-date"
								orderable="<%= true %>"
								property="modifiedDate"
							/>

							<c:if test="<%= article.getDisplayDate() != null %>">
								<liferay-ui:search-container-column-date
									cssClass="display-date-column text-column"
									name="display-date"
									orderable="<%= true %>"
									property="displayDate"
								/>
							</c:if>

							<liferay-ui:search-container-column-text
								cssClass="author-column text-column"
								name="author"
								value="<%= HtmlUtil.escape(PortalUtil.getUserName(articleVersion)) %>"
							/>

							<liferay-ui:search-container-column-jsp
								cssClass="entry-action-column"
								path="/article_version_action.jsp"
							/>
						</c:when>
					</c:choose>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
			</liferay-ui:search-container>
		</aui:form>

		<aui:script>
			AUI.$('body').on(
				'click',
				'.compare-to-link a',
				function(event) {
					var currentTarget = AUI.$(event.currentTarget);

					Liferay.Util.selectEntity(
						{
							dialog: {
								constrain: true,
								destroyOnHide: true,
								modal: true
							},
							eventName: '<portlet:namespace />selectVersionFm',
							id: '<portlet:namespace />compareVersions' + currentTarget.attr('id'),
							title: '<liferay-ui:message key="compare-versions" />',
							uri: currentTarget.data('uri')
						},
						function(event) {
							<portlet:renderURL var="compareVersionURL">
								<portlet:param name="mvcPath" value="/compare_versions.jsp" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
								<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
							</portlet:renderURL>

							var uri = '<%= compareVersionURL %>';

							uri = Liferay.Util.addParams('<portlet:namespace />sourceVersion=' + event.sourceversion, uri);
							uri = Liferay.Util.addParams('<portlet:namespace />targetVersion=' + event.targetversion, uri);

							location.href = uri;
						}
					);
				}
			);

			<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.DELETE) %>">
				$('#<portlet:namespace />deleteArticles').on(
					'click',
					function() {
						if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-version") %>')) {
							var form = AUI.$(document.<portlet:namespace />fm);

							submitForm(form, '<portlet:actionURL name="deleteArticles"><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
						}
					}
				);
			</c:if>

			<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.EXPIRE) %>">
				$('#<portlet:namespace />expireArticles').on(
					'click',
					function() {
						if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-expire-the-selected-version") %>')) {
							var form = AUI.$(document.<portlet:namespace />fm);

							submitForm(form, '<portlet:actionURL name="expireArticles"><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
						}
					}
				);
			</c:if>
		</aui:script>
	</c:otherwise>
</c:choose>
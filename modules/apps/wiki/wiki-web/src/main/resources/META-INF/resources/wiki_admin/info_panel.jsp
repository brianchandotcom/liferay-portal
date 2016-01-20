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

<%@ include file="/wiki/init.jsp" %>

<%
WikiInfoPanelDisplayContext wikiInfoPanelDisplayContext = wikiDisplayContextProvider.getWikiInfoPanelDisplayContext(request, response);
%>

<div class="sidebar-header">
	<c:choose>
		<c:when test="<%= wikiInfoPanelDisplayContext.isSingleNodeSelection() %>">
			<ul class="list-inline list-unstyled sidebar-header-actions">

				<%
				request.setAttribute("info_panel.jsp-wikiNode", wikiInfoPanelDisplayContext.getFirstNode());
				%>

				<li>
					<liferay-util:include page="/wiki/subscribe.jsp" servletContext="<%= application %>" />
				</li>

				<li>
					<liferay-util:include page="/wiki/node_action.jsp" servletContext="<%= application %>" />
				</li>
			</ul>

			<%
			WikiNode node = wikiInfoPanelDisplayContext.getFirstNode();
			%>

			<h4>
				<%= HtmlUtil.escape(node.getName()) %>
			</h4>

			<p>
				<liferay-ui:message key="wiki" />
			</p>
		</c:when>
		<c:when test="<%= wikiInfoPanelDisplayContext.isSinglePageSelection() %>">
			<ul class="list-inline list-unstyled sidebar-header-actions">

				<%
				request.setAttribute("info_panel.jsp-wikiPage", wikiInfoPanelDisplayContext.getFirstPage());
				%>

				<li>
					<liferay-util:include page="/wiki/subscribe.jsp" servletContext="<%= application %>" />
				</li>

				<li>
					<liferay-util:include page="/wiki/page_action.jsp" servletContext="<%= application %>" />
				</li>
			</ul>

			<%
			WikiPage wikiPage = wikiInfoPanelDisplayContext.getFirstPage();
			%>

			<h4>
				<%= HtmlUtil.escape(wikiPage.getTitle()) %>
			</h4>

			<p>
				<liferay-ui:message key="page" />
			</p>
		</c:when>
		<c:when test="<%= wikiInfoPanelDisplayContext.isMultipleItemSelection() %>">
			<h4><liferay-ui:message arguments="<%= wikiInfoPanelDisplayContext.getSelectedItemsCount() %>" key="x-items-are-selected" /></h4>
		</c:when>
		<c:otherwise>
			<h4><liferay-ui:message key="<%= wikiInfoPanelDisplayContext.getItemNameLabel() %>" /></h4>
		</c:otherwise>
	</c:choose>
</div>

<%
String sections = "details";

if (wikiInfoPanelDisplayContext.isSinglePageSelection()) {
	sections += ",versions,activity";
}
%>

<liferay-ui:tabs names="<%= sections %>" refresh="<%= false %>" type="dropdown">
	<liferay-ui:section>
		<div class="sidebar-body">
			<c:choose>
				<c:when test="<%= wikiInfoPanelDisplayContext.isSingleNodeSelection() %>">

					<%
					WikiNode node = wikiInfoPanelDisplayContext.getFirstNode();
					%>

					<c:if test="<%= Validator.isNotNull(node.getDescription()) %>">
						<h5><strong><liferay-ui:message key="description" /></strong></h5>

						<p>
							<%= HtmlUtil.escape(node.getDescription()) %>
						</p>
					</c:if>

					<h5><strong><liferay-ui:message key="total-pages" /></strong></h5>

					<p>
						<%= WikiPageServiceUtil.getPagesCount(scopeGroupId, node.getNodeId(), true) %>
					</p>

					<h5><strong><liferay-ui:message key="orphan-pages" /></strong></h5>

					<p>

						<%
						List<WikiPage> orphanPages = WikiPageServiceUtil.getOrphans(scopeGroupId, node.getNodeId());
						%>

						<%= orphanPages.size() %>
					</p>

					<h5><strong><liferay-ui:message key="last-modified" /></strong></h5>

					<p>
						<%= dateFormatDateTime.format(node.getModifiedDate()) %>
					</p>

					<h5><strong><liferay-ui:message key="create-date" /></strong></h5>

					<p>
						<%= dateFormatDateTime.format(node.getModifiedDate()) %>
					</p>
				</c:when>
				<c:when test="<%= wikiInfoPanelDisplayContext.isSinglePageSelection() %>">

					<%
					WikiPage wikiPage = wikiInfoPanelDisplayContext.getFirstPage();
					%>

					<c:if test="<%= Validator.isNotNull(wikiPage.getSummary()) %>">
						<h5><strong><liferay-ui:message key="summary" /></strong></h5>

						<p>
							<%= HtmlUtil.escape(wikiPage.getSummary()) %>
						</p>
					</c:if>

					<h5><strong><liferay-ui:message key="format" /></strong></h5>

					<p>
						<liferay-ui:message key="<%= WikiUtil.getFormatLabel(wikiPage.getFormat(), locale) %>" />
					</p>

					<h5><strong><liferay-ui:message key="latest-version" /></strong></h5>

					<p>
						<%= wikiPage.getVersion() %>

						<c:if test="<%= wikiPage.isMinorEdit() %>">
							(<liferay-ui:message key="minor-edit" />)
						</c:if>
					</p>

					<h5><strong><liferay-ui:message key="create-date" /></strong></h5>

					<p>
						<%= dateFormatDateTime.format(wikiPage.getCreateDate()) %>
					</p>

					<h5><strong><liferay-ui:message key="last-modified" /></strong></h5>

					<p>
						<%= dateFormatDateTime.format(wikiPage.getModifiedDate()) %>
					</p>

					<h5><strong><liferay-ui:message key="attachments" /></strong></h5>

					<p>
						<%= wikiPage.getAttachmentsFileEntriesCount() %>
					</p>

					<h5><strong><liferay-ui:message key="rss" /></strong></h5>

					<p>
						<aui:a href="<%= wikiInfoPanelDisplayContext.getPageRSSURL(wikiPage) %>" target="_blank">
							<liferay-ui:message key="feed" />
						</aui:a>
					</p>

					<div class="lfr-asset-categories">
						<liferay-ui:asset-categories-summary
							className="<%= WikiPage.class.getName() %>"
							classPK="<%= wikiPage.getResourcePrimKey() %>"
							message="categories"
						/>
					</div>

					<div class="lfr-asset-tags">
						<liferay-ui:asset-tags-summary
							className="<%= WikiPage.class.getName() %>"
							classPK="<%= wikiPage.getResourcePrimKey() %>"
							message="tags"
						/>
					</div>

					<c:if test="<%= wikiPortletInstanceSettingsHelper.isEnablePageRatings() %>">
						<liferay-ui:ratings
							className="<%= WikiPage.class.getName() %>"
							classPK="<%= wikiPage.getResourcePrimKey() %>"
						/>
					</c:if>

					<liferay-ui:custom-attributes-available className="<%= WikiPage.class.getName() %>">
						<liferay-ui:custom-attribute-list
							className="<%= WikiPage.class.getName() %>"
							classPK="<%= wikiPage.getResourcePrimKey() %>"
							editable="<%= false %>"
							label="<%= true %>"
						/>
					</liferay-ui:custom-attributes-available>

					<%
					AssetEntry wikiPageAssetEntry = AssetEntryLocalServiceUtil.fetchEntry(WikiPage.class.getName(), wikiPage.getPrimaryKey());
					%>

					<c:if test="<%= (wikiPageAssetEntry != null) && wikiPortletInstanceSettingsHelper.isEnableRelatedAssets() %>">
						<div class="entry-links">
							<liferay-ui:asset-links
								assetEntryId="<%= wikiPageAssetEntry.getEntryId() %>"
							/>
						</div>
					</c:if>
				</c:when>
				<c:when test="<%= wikiInfoPanelDisplayContext.isMultipleItemSelection() %>">
					<h5><liferay-ui:message arguments="<%= wikiInfoPanelDisplayContext.getSelectedItemsCount() %>" key="x-items-are-selected" /></h5>
				</c:when>
				<c:otherwise>
					<h5><liferay-ui:message key="num-of-items" /></h5>

					<p>
						<%= wikiInfoPanelDisplayContext.getItemsCount() %>
					</p>
				</c:otherwise>
			</c:choose>
		</div>
	</liferay-ui:section>

	<c:if test="<%= wikiInfoPanelDisplayContext.isSinglePageSelection() %>">
		<liferay-ui:section>
			<div class="sidebar-body">

				<%
				WikiPage wikiPage = wikiInfoPanelDisplayContext.getFirstPage();

				List<WikiPage> pages = WikiPageLocalServiceUtil.getPages(wikiPage.getNodeId(), wikiPage.getTitle(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, new PageVersionComparator());

				for (WikiPage curPage : pages) {
				%>

					<div>
						<ul class="list-inline list-unstyled sidebar-header-actions">
							<li>

								<%
								request.setAttribute("info_panel.jsp-wikiPage", curPage);
								%>

								<liferay-util:include page="/wiki/page_history_action.jsp" servletContext="<%= application %>" />
							</li>
						</ul>

						<h4><liferay-ui:message arguments="<%= curPage.getVersion() %>" key="version-x" /></h4>

						<small class="text-muted">
							<liferay-ui:message arguments="<%= new Object[] {curPage.getUserName(), dateFormatDateTime.format(curPage.getCreateDate())} %>" key="by-x-on-x" />
						</small>
					</div>

				<%
				}
				%>

			</div>
		</liferay-ui:section>

		<liferay-ui:section>
			<div class="sidebar-body">

				<%
				WikiSocialActivityHelper wikiSocialActivityHelper = new WikiSocialActivityHelper(wikiRequestHelper);

				WikiPage wikiPage = wikiInfoPanelDisplayContext.getFirstPage();

				List<SocialActivity> socialActivities = SocialActivityLocalServiceUtil.getActivities(0, WikiPage.class.getName(), wikiPage.getResourcePrimKey(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				for (SocialActivity socialActivity : socialActivities) {
					JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject(socialActivity.getExtraData());

					String path = wikiSocialActivityHelper.getSocialActivityActionJSP(socialActivity, extraDataJSONObject);
				%>

					<c:if test="<%= Validator.isNotNull(path) %>">
						<div>
							<ul class="list-inline list-unstyled sidebar-header-actions">
								<li>

									<%
									request.setAttribute("info_panel.jsp-socialActivity", socialActivity);
									request.setAttribute(WikiWebKeys.WIKI_PAGE, wikiPage);
									%>

									<liferay-util:include page="<%= path %>" servletContext="<%= application %>" />
								</li>
							</ul>
						</div>
					</c:if>

					<h4><%= wikiSocialActivityHelper.getSocialActivityDescription(wikiPage, socialActivity, extraDataJSONObject, resourceBundle) %></h4>

					<small type="text-muted">
						<%= dateFormatDateTime.format(socialActivity.getCreateDate()) %>
					</small>

				<%
				}
				%>

			</div>
		</liferay-ui:section>
	</c:if>
</liferay-ui:tabs>
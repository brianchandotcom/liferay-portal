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

<%@ include file="/blogs/init.jsp" %>

<%
SearchContainer<BaseModel<?>> searchContainer = (SearchContainer)request.getAttribute("view_entry_content.jsp-searchContainer");

BlogsEntry entry = (BlogsEntry)request.getAttribute("view_entry_content.jsp-entry");

BlogsPortletInstanceConfiguration blogsPortletInstanceConfiguration = BlogsPortletInstanceConfigurationUtil.getBlogsPortletInstanceConfiguration(themeDisplay);
%>

<liferay-ui:success key='<%= portletDisplay.getId() + "requestProcessed" %>' message="your-request-completed-successfully" />

<c:choose>
	<c:when test="<%= entry.isVisible() || (entry.getUserId() == user.getUserId()) || BlogsEntryPermission.contains(permissionChecker, entry, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="viewEntryURL">
			<portlet:param name="mvcRenderCommandName" value="/blogs/view_entry" />
			<portlet:param name="redirect" value="<%= currentURL %>" />

			<c:choose>
				<c:when test="<%= Validator.isNotNull(entry.getUrlTitle()) %>">
					<portlet:param name="urlTitle" value="<%= entry.getUrlTitle() %>" />
				</c:when>
				<c:otherwise>
					<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
				</c:otherwise>
			</c:choose>
		</portlet:renderURL>

		<clay:container-fluid
			cssClass="widget-mode-detail-header"
		>
			<liferay-asset:asset-categories-available
				className="<%= BlogsEntry.class.getName() %>"
				classPK="<%= entry.getEntryId() %>"
			>
				<clay:row>
					<clay:col
						cssClass="categories mx-auto widget-metadata"
						md="8"
					>
						<liferay-asset:asset-categories-summary
							className="<%= BlogsEntry.class.getName() %>"
							classPK="<%= entry.getEntryId() %>"
							displayStyle="simple-category"
							portletURL="<%= renderResponse.createRenderURL() %>"
						/>
					</clay:col>
				</clay:row>
			</liferay-asset:asset-categories-available>

			<clay:row>
				<clay:col
					cssClass="mx-auto"
					md="8"
				>
					<clay:content-row>
						<clay:content-col
							expand="<%= true %>"
						>
							<h3 class="title"><%= HtmlUtil.escape(BlogsEntryUtil.getDisplayTitle(resourceBundle, entry)) %></h3>

							<%
							String subtitle = entry.getSubtitle();
							%>

							<c:if test="<%= Validator.isNotNull(subtitle) %>">
								<h4 class="sub-title"><%= HtmlUtil.escape(subtitle) %></h4>
							</c:if>
						</clay:content-col>

						<clay:content-col
							cssClass="visible-interaction"
						>
							<div class="dropdown dropdown-action">
								<c:if test="<%= BlogsEntryPermission.contains(permissionChecker, entry, ActionKeys.UPDATE) %>">
									<a
										href="<%=
											PortletURLBuilder.create(
												PortalUtil.getControlPanelPortletURL(request, themeDisplay.getScopeGroup(), BlogsPortletKeys.BLOGS_ADMIN, 0, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE)
											).setMVCRenderCommandName(
												"/blogs/edit_entry"
											).setRedirect(
												currentURL
											).setPortletResource(
												portletDisplay.getId()
											).setParameter(
												"entryId", entry.getEntryId()
											).buildString()
										%>"
									>
										<span class="hide-accessible"><liferay-ui:message key="edit-entry" /></span>

										<clay:icon
											symbol="pencil"
										/>
									</a>
								</c:if>
							</div>
						</clay:content-col>
					</clay:content-row>

					<clay:content-row
						cssClass="widget-metadata"
					>

						<%
						User entryUser = UserLocalServiceUtil.fetchUser(entry.getUserId());

						String entryUserURL = StringPool.BLANK;

						if ((entryUser != null) && !entryUser.isDefaultUser() && !user.isDefaultUser()) {
							entryUserURL = entryUser.getDisplayURL(themeDisplay);
						}
						%>

						<clay:content-col
							cssClass="inline-item-before"
						>
							<liferay-ui:user-portrait
								cssClass="sticker-lg"
								user="<%= entryUser %>"
							/>
						</clay:content-col>

						<clay:content-col
							expand="<%= true %>"
						>
							<clay:content-row>
								<clay:content-col
									expand="<%= true %>"
								>
									<div class="text-truncate-inline">
										<a class="text-truncate username" href="<%= entryUserURL %>"><%= HtmlUtil.escape(entry.getUserName()) %></a>
									</div>

									<div>
										<span class="hide-accessible"><liferay-ui:message key="published-date" /></span><liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - entry.getStatusDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />

										<c:if test="<%= blogsPortletInstanceConfiguration.enableReadingTime() %>">
											- <liferay-reading-time:reading-time displayStyle="descriptive" model="<%= entry %>" />
										</c:if>

										<c:if test="<%= blogsPortletInstanceConfiguration.enableViewCount() %>">

											<%
											AssetEntry assetEntry = BlogsEntryAssetEntryUtil.getAssetEntry(request, entry);
											%>

											- <liferay-ui:message arguments="<%= assetEntry.getViewCount() %>" key='<%= (assetEntry.getViewCount() == 1) ? "x-view" : "x-views" %>' />
										</c:if>
									</div>
								</clay:content-col>
							</clay:content-row>
						</clay:content-col>
					</clay:content-row>
				</clay:col>
			</clay:row>
		</clay:container-fluid>

		<%
		String coverImageURL = entry.getCoverImageURL(themeDisplay);
		%>

		<liferay-util:include page="/blogs/entry_cover_image_caption.jsp" servletContext="<%= application %>">
			<liferay-util:param name="coverImageCaption" value="<%= entry.getCoverImageCaption() %>" />
			<liferay-util:param name="coverImageURL" value="<%= coverImageURL %>" />
		</liferay-util:include>

		<!-- text resume -->

		<clay:container-fluid
			cssClass="widget-mode-detail-header"
			id="<%= liferayPortletResponse.getNamespace() + entry.getEntryId() %>"
		>
			<c:if test="<%= Validator.isNotNull(coverImageURL) %>">

				<%
				String coverImageCaption = entry.getCoverImageCaption();
				%>

				<c:if test="<%= Validator.isNotNull(coverImageCaption) %>">
					<clay:row>
						<clay:col
							cssClass="mx-auto"
							md="8"
						>
							<div class="cover-image-caption">
								<small><%= entry.getCoverImageCaption() %></small>
							</div>
						</clay:col>
					</clay:row>
				</c:if>
			</c:if>

			<clay:row>
				<clay:col
					cssClass="mx-auto widget-mode-detail-text"
					md="8"
				>
					<%= entry.getContent() %>
				</clay:col>
			</clay:row>

			<liferay-expando:custom-attributes-available
				className="<%= BlogsEntry.class.getName() %>"
			>
				<clay:row>
					<clay:col
						cssClass="mx-auto widget-mode-detail-text"
						md="8"
					>
						<liferay-expando:custom-attribute-list
							className="<%= BlogsEntry.class.getName() %>"
							classPK="<%= entry.getEntryId() %>"
							editable="<%= false %>"
							label="<%= true %>"
						/>
					</clay:col>
				</clay:row>
			</liferay-expando:custom-attributes-available>

			<liferay-asset:asset-tags-available
				className="<%= BlogsEntry.class.getName() %>"
				classPK="<%= entry.getEntryId() %>"
			>
				<clay:row>
					<clay:col
						cssClass="mx-auto widget-mode-detail-text"
						md="8"
					>
						<div class="entry-tags">
							<liferay-asset:asset-tags-summary
								className="<%= BlogsEntry.class.getName() %>"
								classPK="<%= entry.getEntryId() %>"
								portletURL="<%= renderResponse.createRenderURL() %>"
							/>
						</div>
					</clay:col>
				</clay:row>
			</liferay-asset:asset-tags-available>
		</clay:container-fluid>

		<clay:container-fluid>
			<clay:row>
				<clay:col
					cssClass="mx-auto widget-mode-detail-text"
					md="8"
				>

					<%
					request.setAttribute("entry_toolbar.jsp-entry", entry);
					%>

					<liferay-util:include page="/blogs/entry_toolbar.jsp" servletContext="<%= application %>">
						<liferay-util:param name="showFlags" value="<%= Boolean.TRUE.toString() %>" />
					</liferay-util:include>
				</clay:col>
			</clay:row>

			<c:if test="<%= blogsPortletInstanceConfiguration.enableRelatedAssets() %>">
				<clay:row>
					<clay:col
						cssClass="mx-auto widget-mode-detail-text"
						md="8"
					>

						<%
						AssetEntry assetEntry = BlogsEntryAssetEntryUtil.getAssetEntry(request, entry);
						%>

						<div class="entry-links">
							<liferay-asset:asset-links
								assetEntryId="<%= (assetEntry != null) ? assetEntry.getEntryId() : 0 %>"
								className="<%= BlogsEntry.class.getName() %>"
								classPK="<%= entry.getEntryId() %>"
							/>
						</div>
					</clay:col>
				</clay:row>
			</c:if>
		</clay:container-fluid>
	</c:when>
	<c:otherwise>

		<%
		if (searchContainer != null) {
			searchContainer.setResultsAndTotal(searchContainer::getResults, searchContainer.getTotal() - 1);
		}
		%>

	</c:otherwise>
</c:choose>
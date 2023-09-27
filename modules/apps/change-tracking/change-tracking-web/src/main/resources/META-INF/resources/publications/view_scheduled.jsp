<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<%
ViewScheduledDisplayContext viewScheduledDisplayContext = (ViewScheduledDisplayContext)request.getAttribute(CTWebKeys.VIEW_SCHEDULED_DISPLAY_CONTEXT);
%>

<clay:navigation-bar
	navigationItems="<%= viewScheduledDisplayContext.getViewNavigationItems() %>"
/>

<c:choose>
	<c:when test='<%= FeatureFlagManagerUtil.isEnabled("LPS-180155") %>'>
		<clay:container-fluid>
			<frontend-data-set:headless-display
				apiURL="<%= viewScheduledDisplayContext.getAPIURL() %>"
				fdsActionDropdownItems="<%= viewScheduledDisplayContext.getFDSActionDropdownItems() %>"
				id="<%= PublicationsFDSNames.PUBLICATIONS_SCHEDULED %>"
				style="stacked"
			/>
		</clay:container-fluid>
	</c:when>
	<c:otherwise>

		<%
		SearchContainer<CTCollection> searchContainer = viewScheduledDisplayContext.getSearchContainer();

		ViewScheduledManagementToolbarDisplayContext viewScheduledManagementToolbarDisplayContext = new ViewScheduledManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, searchContainer, viewScheduledDisplayContext);

		Format format = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
		%>

		<clay:management-toolbar
			managementToolbarDisplayContext="<%= viewScheduledManagementToolbarDisplayContext %>"
		/>

		<clay:container-fluid>
			<liferay-ui:search-container
				cssClass="publications-table"
				searchContainer="<%= searchContainer %>"
				var="publicationsSearchContainer"
			>
				<liferay-ui:search-container-row
					className="com.liferay.change.tracking.model.CTCollection"
					escapedModel="<%= true %>"
					keyProperty="ctCollectionId"
					modelVar="ctCollection"
				>
					<c:choose>
						<c:when test='<%= Objects.equals(publicationsDisplayContext.getDisplayStyle(), "descriptive") %>'>
							<liferay-ui:search-container-column-text>
								<span class="lfr-portal-tooltip" title="<%= HtmlUtil.escape(ctCollection.getUserName()) %>">
									<liferay-user:user-portrait
										userId="<%= ctCollection.getUserId() %>"
									/>
								</span>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-text
								cssClass="autofit-col-expand"
								href="<%= publicationsDisplayContext.getReviewChangesURL(ctCollection.getCtCollectionId()) %>"
							>
								<div class="publication-name <%= (publicationsDisplayContext.getCtCollectionId() == ctCollection.getCtCollectionId()) ? "font-italic" : StringPool.BLANK %>">
									<%= ctCollection.getName() %>
								</div>

								<div class="publication-description <%= (publicationsDisplayContext.getCtCollectionId() == ctCollection.getCtCollectionId()) ? "font-italic" : StringPool.BLANK %>">
									<%= ctCollection.getDescription() %>
								</div>
							</liferay-ui:search-container-column-text>
						</c:when>
						<c:otherwise>
							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand"
								href="<%= publicationsDisplayContext.getReviewChangesURL(ctCollection.getCtCollectionId()) %>"
								name="publication"
							>
								<div class="publication-name <%= (publicationsDisplayContext.getCtCollectionId() == ctCollection.getCtCollectionId()) ? "font-italic" : StringPool.BLANK %>">
									<%= ctCollection.getName() %>
								</div>

								<div class="publication-description <%= (publicationsDisplayContext.getCtCollectionId() == ctCollection.getCtCollectionId()) ? "font-italic" : StringPool.BLANK %>">
									<%= ctCollection.getDescription() %>
								</div>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand-smaller"
								name="publishing"
							>
								<%= format.format(viewScheduledDisplayContext.getPublishingDate(ctCollection.getCtCollectionId())) %>
							</liferay-ui:search-container-column-text>

							<%
							Date modifiedDate = ctCollection.getModifiedDate();
							%>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand-smaller"
								name="last-modified"
							>
								<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
							</liferay-ui:search-container-column-text>

							<%
							Date createDate = ctCollection.getCreateDate();
							%>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand-smaller"
								name="created"
							>
								<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand-smallest text-center"
								name="owner"
							>
								<span class="lfr-portal-tooltip" title="<%= HtmlUtil.escape(ctCollection.getUserName()) %>">
									<liferay-user:user-portrait
										userId="<%= ctCollection.getUserId() %>"
									/>
								</span>
							</liferay-ui:search-container-column-text>
						</c:otherwise>
					</c:choose>

					<liferay-ui:search-container-column-jsp
						path="/publications/scheduled_publication_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					displayStyle="<%= publicationsDisplayContext.getDisplayStyle() %>"
					markupView="lexicon"
					searchContainer="<%= searchContainer %>"
				/>
			</liferay-ui:search-container>
		</clay:container-fluid>
	</c:otherwise>
</c:choose>
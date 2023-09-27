<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<clay:navigation-bar
	navigationItems="<%= publicationsDisplayContext.getViewNavigationItems() %>"
/>

<c:choose>
	<c:when test='<%= FeatureFlagManagerUtil.isEnabled("LPS-180155") %>'>
		<clay:container-fluid>
			<frontend-data-set:headless-display
				apiURL="<%= publicationsDisplayContext.getAPIURL() %>"
				creationMenu="<%= publicationsDisplayContext.getCreationMenu() %>"
				fdsActionDropdownItems="<%= publicationsDisplayContext.getFDSActionDropdownItems() %>"
				id="<%= PublicationsFDSNames.PUBLICATIONS_ONGOING %>"
				style="stacked"
			/>
		</clay:container-fluid>
	</c:when>
	<c:otherwise>

		<%
		SearchContainer<CTCollection> searchContainer = publicationsDisplayContext.getSearchContainer();
		%>

		<clay:management-toolbar
			managementToolbarDisplayContext="<%= new PublicationsManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, publicationsDisplayContext, searchContainer) %>"
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

								<c:if test="<%= Validator.isNotNull(ctCollection.getDescription()) %>">
									<div class="publication-description <%= (publicationsDisplayContext.getCtCollectionId() == ctCollection.getCtCollectionId()) ? "font-italic" : StringPool.BLANK %>">
										<%= ctCollection.getDescription() %>
									</div>
								</c:if>

								<clay:label
									displayType="<%= publicationsDisplayContext.getStatusStyle(ctCollection.getStatus()) %>"
									label="<%= publicationsDisplayContext.getStatusLabel(ctCollection.getStatus()) %>"
								/>
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
								cssClass="table-cell-expand-smaller"
								name="status"
							>
								<clay:label
									displayType="<%= publicationsDisplayContext.getStatusStyle(ctCollection.getStatus()) %>"
									label="<%= publicationsDisplayContext.getStatusLabel(ctCollection.getStatus()) %>"
								/>
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

					<liferay-ui:search-container-column-text>
						<div>
							<div class="dropdown">
								<button class="btn btn-monospaced btn-sm btn-unstyled dropdown-toggle hidden" type="button">
									<svg class="lexicon-icon lexicon-icon-ellipsis-v publications-hidden" role="presentation">
										<use xlink:href="<%= themeDisplay.getPathThemeSpritemap() %>#ellipsis-v" />
									</svg>
								</button>
							</div>

							<react:component
								module="publications/js/components/ViewPublicationsDropdownMenu"
								props="<%= publicationsDisplayContext.getDropdownReactData(ctCollection, permissionChecker) %>"
							/>
						</div>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					displayStyle="<%= publicationsDisplayContext.getDisplayStyle() %>"
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</clay:container-fluid>
	</c:otherwise>
</c:choose>

<%
CTLocalizedException ctLocalizedException = null;

if (MultiSessionErrors.contains(liferayPortletRequest, CTLocalizedException.class.getName())) {
	ctLocalizedException = (CTLocalizedException)MultiSessionErrors.get(liferayPortletRequest, CTLocalizedException.class.getName());
}
%>

<c:if test="<%= ctLocalizedException != null %>">
	<aui:script>
		Liferay.Util.openToast({
			autoClose: 10000,
			message:
				'<%= HtmlUtil.escapeJS(ctLocalizedException.formatMessage(resourceBundle)) %>',
			title: '<liferay-ui:message key="error" />:',
			type: 'danger',
		});
	</aui:script>
</c:if>
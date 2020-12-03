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

<%@ include file="/publications/init.jsp" %>

<%
SearchContainer<CTCollection> searchContainer = publicationsDisplayContext.getSearchContainer();

searchContainer.setId("selectPublication");
%>

<clay:management-toolbar-v2
	displayContext="<%= new SelectPublicationManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, searchContainer) %>"
/>

<c:if test="<%= !searchContainer.hasResults() %>">
	<div class="contact-information-empty-results-message-wrapper">
		<liferay-ui:empty-result-message
			message="<%= searchContainer.getEmptyResultsMessage() %>"
		/>
	</div>
</c:if>

<clay:container-fluid
	id='<%= liferayPortletResponse.getNamespace() + "selectPublicationContainer" %>'
>
	<div class="table-responsive">
		<table class="publications-table select-publication-table table table-autofit">
			<tbody>

				<%
				for (CTCollection ctCollection : searchContainer.getResults()) {
				%>

					<tr>
						<td>
							<span class="lfr-portal-tooltip" title="<%= HtmlUtil.escape(ctCollection.getUserName()) %>">
								<liferay-ui:user-portrait
									userId="<%= ctCollection.getUserId() %>"
								/>
							</span>
						</td>
						<td class="table-cell-expand">
							<c:choose>
								<c:when test="<%= ctCollection.getCtCollectionId() == publicationsDisplayContext.getCtCollectionId() %>">
									<div class="font-italic publication-name">
										<%= HtmlUtil.escape(ctCollection.getName()) %>
									</div>

									<c:if test="<%= Validator.isNotNull(ctCollection.getDescription()) %>">
										<div class="font-italic publication-description">
											<%= HtmlUtil.escape(ctCollection.getDescription()) %>
										</div>
									</c:if>

									<clay:label
										displayType="<%= publicationsDisplayContext.getStatusStyle(ctCollection.getStatus()) %>"
										label="<%= publicationsDisplayContext.getStatusLabel(ctCollection.getStatus()) %>"
									/>
								</c:when>
								<c:otherwise>
									<div>
										<aui:a
											cssClass='<%= (ctCollection.getStatus() == WorkflowConstants.STATUS_EXPIRED) ? "btn btn-link btn-unstyled disabled" : "selector-button" %>'
											data='<%=
												HashMapBuilder.<String, Object>put(
													"ctcollectionid", ctCollection.getCtCollectionId()
												).build()
											%>'
											href="javascript:;"
										>
											<div class="publication-name">
												<%= HtmlUtil.escape(ctCollection.getName()) %>
											</div>

											<c:if test="<%= Validator.isNotNull(ctCollection.getDescription()) %>">
												<div class="publication-description">
													<%= HtmlUtil.escape(ctCollection.getDescription()) %>
												</div>
											</c:if>
										</aui:a>
									</div>

									<div>
										<clay:label
											displayType="<%= publicationsDisplayContext.getStatusStyle(ctCollection.getStatus()) %>"
											label="<%= publicationsDisplayContext.getStatusLabel(ctCollection.getStatus()) %>"
										/>
									</div>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>

				<%
				}
				%>

			</tbody>
		</table>
	</div>

	<liferay-ui:search-paginator
		markupView="lexicon"
		searchContainer="<%= searchContainer %>"
	/>
</clay:container-fluid>

<aui:script>
	Liferay.Util.selectEntityHandler(
		'#<portlet:namespace />selectPublicationContainer',
		'<portlet:namespace />selectPublication'
	);
</aui:script>
<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
List<FriendlyURLPublicMappingConflict> friendlyURLPublicMappingConflicts = (List<FriendlyURLPublicMappingConflict>)request.getAttribute(ServerAdminWebKeys.FRIENDLY_URL_PUBLIC_MAPPING_CONFLICTS);
%>

<div class="sheet">
	<div class="panel-group panel-group-flush">
		<c:choose>
			<c:when test="<%= friendlyURLPublicMappingConflicts == null %>">
				<p>
					<liferay-ui:message key="check-whether-the-public-friendly-url-mapping-can-be-safely-disabled" />
				</p>
			</c:when>
			<c:when test="<%= friendlyURLPublicMappingConflicts.isEmpty() %>">
				<clay:alert
					displayType="success"
					message="no-conflicts-were-found-the-public-friendly-url-mapping-can-be-safely-disabled"
				/>
			</c:when>
			<c:otherwise>
				<clay:alert
					displayType="warning"
					message='<%= LanguageUtil.format(request, "x-conflicts-were-found-resolve-each-before-disabling-the-public-friendly-url-mapping", friendlyURLPublicMappingConflicts.size()) %>'
				/>

				<%
				SearchContainer<FriendlyURLPublicMappingConflict> searchContainer = new SearchContainer<FriendlyURLPublicMappingConflict>(liferayPortletRequest, renderResponse.createRenderURL(), null, null);

				searchContainer.setResultsAndTotal(friendlyURLPublicMappingConflicts);
				%>

				<liferay-ui:search-container
					searchContainer="<%= searchContainer %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.friendly.url.checker.FriendlyURLPublicMappingConflict"
						modelVar="friendlyURLPublicMappingConflict"
					>
						<liferay-ui:search-container-column-text
							name="path"
							value="<%= HtmlUtil.escape(friendlyURLPublicMappingConflict.getFriendlyURL()) %>"
						/>

						<liferay-ui:search-container-column-text
							name="subject"
						>
							<%= HtmlUtil.escape(ResourceActionsUtil.getModelResource(locale, friendlyURLPublicMappingConflict.getClassName())) %> &mdash; <%= HtmlUtil.escape(friendlyURLPublicMappingConflict.getTitle()) %> (<%= friendlyURLPublicMappingConflict.getClassPK() %>)
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text
							name="type"
						>
							<liferay-ui:message key="<%= friendlyURLPublicMappingConflict.getType() %>" />
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text
							name="conflicts-with"
						>
							<c:choose>
								<c:when test="<%= friendlyURLPublicMappingConflict.getConflictingGroupId() != null %>">
									<%= HtmlUtil.escape(friendlyURLPublicMappingConflict.getConflictingGroupName()) %> (<%= friendlyURLPublicMappingConflict.getConflictingGroupId() %>)
								</c:when>
								<c:otherwise>
									&mdash;
								</c:otherwise>
							</c:choose>
						</liferay-ui:search-container-column-text>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						markupView="lexicon"
					/>
				</liferay-ui:search-container>
			</c:otherwise>
		</c:choose>

		<aui:form action='<%= PortletURLBuilder.createActionURL(renderResponse).setActionName("/server_admin/check_friendly_urls").buildString() %>' method="post">
			<aui:button primary="<%= friendlyURLPublicMappingConflicts == null %>" type="submit" value='<%= (friendlyURLPublicMappingConflicts == null) ? "run-check" : "run-check-again" %>' />
		</aui:form>
	</div>
</div>
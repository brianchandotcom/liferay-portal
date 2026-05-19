<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
List<FriendlyURLPublicMappingConflict> conflicts = (List<FriendlyURLPublicMappingConflict>)request.getAttribute(ServerAdminWebKeys.FRIENDLY_URL_PUBLIC_MAPPING_CONFLICTS);
%>

<div class="sheet">
	<div class="panel-group panel-group-flush">
		<c:choose>
			<c:when test="<%= conflicts == null %>">
				<p>
					<liferay-ui:message key="check-whether-the-public-friendly-url-mapping-can-be-safely-disabled" />
				</p>
			</c:when>
			<c:when test="<%= conflicts.isEmpty() %>">
				<clay:alert
					displayType="success"
					message="no-conflicts-were-found-the-public-friendly-url-mapping-can-be-safely-disabled"
				/>
			</c:when>
			<c:otherwise>
				<clay:alert
					displayType="warning"
					message='<%= LanguageUtil.format(request, "x-conflicts-were-found-resolve-each-before-disabling-the-public-friendly-url-mapping", conflicts.size()) %>'
				/>

				<%
				SearchContainer<FriendlyURLPublicMappingConflict> conflictSearchContainer = new SearchContainer<FriendlyURLPublicMappingConflict>(liferayPortletRequest, renderResponse.createRenderURL(), null, null);

				conflictSearchContainer.setResultsAndTotal(conflicts);
				%>

				<liferay-ui:search-container
					searchContainer="<%= conflictSearchContainer %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.friendly.url.checker.FriendlyURLPublicMappingConflict"
						modelVar="conflict"
					>
						<liferay-ui:search-container-column-text
							name="path"
							value="<%= HtmlUtil.escape(conflict.getFriendlyURL()) %>"
						/>

						<liferay-ui:search-container-column-text
							name="subject"
						>
							<%= HtmlUtil.escape(ResourceActionsUtil.getModelResource(locale, conflict.getClassName())) %> &mdash; <%= HtmlUtil.escape(conflict.getTitle()) %> (<%= conflict.getClassPK() %>)
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text
							name="type"
						>
							<liferay-ui:message key="<%= StringUtil.replace(StringUtil.toLowerCase(conflict.getType().name()), CharPool.UNDERLINE, CharPool.DASH) %>" />
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text
							name="conflicts-with"
						>
							<c:choose>
								<c:when test="<%= conflict.getConflictingGroupId() != null %>">
									<%= HtmlUtil.escape(conflict.getConflictingGroupName()) %> (<%= conflict.getConflictingGroupId() %>)
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
			<aui:button primary="<%= conflicts == null %>" type="submit" value='<%= (conflicts == null) ? "run-check" : "run-check-again" %>' />
		</aui:form>
	</div>
</div>
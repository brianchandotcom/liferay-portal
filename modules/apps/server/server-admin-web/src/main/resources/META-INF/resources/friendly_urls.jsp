<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ServerDisplayContext serverDisplayContext = new ServerDisplayContext(renderRequest, renderResponse);

List<LayoutFriendlyURLPublicMappingConflict> conflicts = serverDisplayContext.getLayoutFriendlyURLPublicMappingConflicts();

PortletURL runVerificationURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCRenderCommandName(
	"/server_admin/view"
).setTabs1(
	"friendly-urls"
).setParameter(
	ServerDisplayContext.VERIFY_RAN_PARAM, true
).buildPortletURL();
%>

<div class="sheet">
	<div class="panel-group panel-group-flush">
		<c:choose>
			<c:when test="<%= conflicts == null %>">
				<p>
					<liferay-ui:message key="check-whether-the-public-friendly-url-mapping-can-be-safely-disabled-conflicts-are-reported-per-page" />
				</p>

				<aui:button href="<%= runVerificationURL.toString() %>" primary="<%= true %>" value="run-verification" />
			</c:when>
			<c:when test="<%= (conflicts != null) && conflicts.isEmpty() %>">
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
				SearchContainer<LayoutFriendlyURLPublicMappingConflict> conflictSearchContainer = new SearchContainer<LayoutFriendlyURLPublicMappingConflict>(liferayPortletRequest, runVerificationURL, null, null);

				conflictSearchContainer.setResultsAndTotal(conflicts);
				%>

				<liferay-ui:search-container
					searchContainer="<%= conflictSearchContainer %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.layout.friendly.url.verifier.LayoutFriendlyURLPublicMappingConflict"
						modelVar="conflict"
					>
						<liferay-ui:search-container-column-text
							name="path"
							value="<%= HtmlUtil.escape(conflict.getPageURL()) %>"
						/>

						<liferay-ui:search-container-column-text
							name="page"
						>
							<%= HtmlUtil.escape(conflict.getLayoutName()) %> (plid <%= conflict.getLayoutPlid() %>)
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

		<c:if test="<%= conflicts != null %>">
			<p class="text-secondary">
				<%= LanguageUtil.format(request, "last-run-x-by-x", new Object[] {new Date(), HtmlUtil.escape(themeDisplay.getUser().getFullName())}, false) %>
			</p>

			<aui:button href="<%= runVerificationURL.toString() %>" value="run-verification-again" />
		</c:if>
	</div>
</div>
<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/ui/quick_access/init.jsp" %>

<%
String linkClass = "d-block p-2 sr-only sr-only-focusable text-reset";
String randomNamespace = StringUtil.randomId() + StringPool.UNDERLINE;
%>

<liferay-util:buffer
	var="skipToMainContentLink"
>
	<a class="<%= linkClass %>" href="<%= contentId %>">
		<liferay-ui:message key="skip-to-main-content" />
	</a>
</liferay-util:buffer>

<c:if test="<%= ((quickAccessEntries != null) && !quickAccessEntries.isEmpty()) || Validator.isNotNull(contentId) %>">
	<nav aria-label="<liferay-ui:message key="quick-links" />" class="bg-dark cadmin quick-access-nav text-center text-white" id="<%= randomNamespace %>quickAccessNav">
		<c:choose>
			<c:when test="<%= Validator.isNotNull(contentId) && ((quickAccessEntries == null) || quickAccessEntries.isEmpty()) %>">
				<%= skipToMainContentLink %>
			</c:when>
			<c:otherwise>
				<ul class="list-unstyled mb-0">
					<c:if test="<%= Validator.isNotNull(contentId) %>">
						<li>
							<%= skipToMainContentLink %>
						</li>
					</c:if>

					<c:if test="<%= (quickAccessEntries != null) && !quickAccessEntries.isEmpty() %>">

						<%
						for (QuickAccessEntry quickAccessEntry : quickAccessEntries) {
						%>

							<li>
								<c:choose>
									<c:when test="<%= Validator.isNull(quickAccessEntry.getURL()) %>">
										<button class="<%= linkClass %> btn btn-link btn-unstyled text-nowrap" id="<%= randomNamespace + quickAccessEntry.getId() %>" onclick="<%= quickAccessEntry.getOnClick() %>">
											<%= quickAccessEntry.getContent() %>
										</button>
									</c:when>
									<c:otherwise>
										<a class="<%= linkClass %>" href="<%= quickAccessEntry.getURL() %>" id="<%= randomNamespace + quickAccessEntry.getId() %>" onclick="<%= quickAccessEntry.getOnClick() %>">
											<%= quickAccessEntry.getContent() %>
										</a>
									</c:otherwise>
								</c:choose>
							</li>

						<%
						}
						%>

					</c:if>
				</ul>
			</c:otherwise>
		</c:choose>
	</nav>
</c:if>
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
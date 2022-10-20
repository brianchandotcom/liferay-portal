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

<%@ include file="/init.jsp" %>

<%
String className = (String)request.getAttribute("change-tracking:timeline:className");
long classPK = GetterUtil.getLong(request.getAttribute("change-tracking:timeline:classPK"));

TimelineDisplayContext timelineDisplayContext = new TimelineDisplayContext(renderRequest, renderResponse, className, classPK);
%>

<c:choose>
	<c:when test="<%= !timelineDisplayContext.isPublicationsEnabled() %>">
		<div>
			<liferay-ui:message key="publications-must-be-enabled" />
		</div>
	</c:when>
	<c:otherwise>
		<clay:content-row
			cssClass="sheet-subtitle sidebar-section"
		>
			<clay:content-col
				expand="<%= true %>"
			>
				<strong><liferay-ui:message key="timeline" /></strong>
			</clay:content-col>
		</clay:content-row>

		<%
		CTCollection currentCTCollection = timelineDisplayContext.getCurrentCTCollection();
		%>

		<clay:content-row
			cssClass="sheet-subtitle sidebar-section"
			style="text-transform: none;"
		>
			<clay:content-col>
				<clay:icon
					style="color:#6B6C7E; margin-right:1em; margin-top:0.25em;"
					symbol="sheets"
				/>
			</clay:content-col>

			<clay:content-col
				expand="<%= true %>"
			>
				<div>
					<strong><%= (currentCTCollection != null) ? currentCTCollection.getName() : LanguageUtil.get(request, "production") %> (<liferay-ui:message key="current-draft" />)</strong>
				</div>

				<div class="text-secondary">
					<%= (currentCTCollection != null) ? currentCTCollection.getDescription() : "" %>
				</div>
			</clay:content-col>
		</clay:content-row>

		<%
		for (CTCollection ctCollection : timelineDisplayContext.getCTCollections()) {
		%>

			<clay:content-row
				cssClass="sheet-subtitle sidebar-section"
				style="text-transform: none;"
			>
				<clay:content-col>
					<clay:icon
						style="color:#6B6C7E; margin-right:1em; margin-top:0.25em;"
						symbol="change-list"
					/>
				</clay:content-col>

				<clay:content-col
					expand="<%= true %>"
				>
					<div>
						<strong><%= ctCollection.getName() %></strong>
					</div>

					<div class="text-secondary">
						<%= ctCollection.getDescription() %>
					</div>

					<%
					Date modifiedDate = ctCollection.getModifiedDate();
					%>

					<div class="text-secondary">
						<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(ctCollection.getUserName()), LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true)} %>" key="x-published-x-ago" translateArguments="<%= false %>" />
					</div>
				</clay:content-col>

				<clay:content-col>
					<div class="dropdown dropdown-action">
						<button class="btn btn-monospaced btn-sm btn-unstyled dropdown-toggle hidden" type="button">
							<svg class="lexicon-icon lexicon-icon-ellipsis-v publications-hidden" role="presentation" style="color: #6B6C7E;">
								<use xlink:href="<%= FrontendIconsUtil.getSpritemap(themeDisplay) %>#ellipsis-v" />
							</svg>
						</button>
					</div>

					<react:component
						module="timeline/js/TimelineDropdownMenu"
						props="<%= timelineDisplayContext.getDropdownReactData(ctCollection) %>"
					/>
				</clay:content-col>
			</clay:content-row>

		<%
		}
		%>

	</c:otherwise>
</c:choose>
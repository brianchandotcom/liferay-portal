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

<%@ include file="/group_selector/init.jsp" %>

<%
GroupSelectorDisplayContext groupSelectorDisplayContext = new GroupSelectorDisplayContext(liferayPortletRequest);

Set<String> groupTypes = groupSelectorDisplayContext.getGroupTypes();
%>

<c:if test="<%= groupTypes.size() > 1 %>">
	<clay:container>
		<div class="btn-group btn-group-sm my-3" role="group">

			<%
			for (String curGroupType : groupTypes) {
			%>

				<a class="btn btn-secondary <%= groupSelectorDisplayContext.isGroupTypeActive(curGroupType) ? "active" : StringPool.BLANK %>" href="<%= groupSelectorDisplayContext.getGroupItemSelectorURL(curGroupType) %>"><%= groupSelectorDisplayContext.getGroupItemSelectorLabel(curGroupType) %></a>

			<%
			}
			%>

		</div>
	</clay:container>
</c:if>

<clay:container
	className="lfr-item-viewer"
>
	<liferay-ui:search-container
		searchContainer="<%= groupSelectorDisplayContext.getSearchContainer() %>"
		var="listSearchContainer"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Group"
			cssClass="entry-card lfr-asset-item"
			modelVar="curGroup"
		>
			<liferay-ui:search-container-column-text
				colspan="<%= 3 %>"
			>
				<liferay-frontend:horizontal-card
					text="<%= curGroup.getDescriptiveName(locale) %>"
					url="<%= groupSelectorDisplayContext.getViewGroupURL(curGroup) %>"
				>
					<liferay-frontend:horizontal-card-col>
						<c:choose>
							<c:when test="<%= Validator.isNotNull(curGroup.getLogoURL(themeDisplay, false)) %>">
								<span class="sticker sticker-rounded">
									<span class="sticker-overlay">
										<img alt="" class="sticker-img" src="<%= curGroup.getLogoURL(themeDisplay, false) %>" />
									</span>
								</span>
							</c:when>
							<c:otherwise>
								<liferay-frontend:horizontal-card-icon
									icon="<%= groupSelectorDisplayContext.getGroupItemSelectorIcon() %>"
								/>
							</c:otherwise>
						</c:choose>
					</liferay-frontend:horizontal-card-col>
				</liferay-frontend:horizontal-card>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container>
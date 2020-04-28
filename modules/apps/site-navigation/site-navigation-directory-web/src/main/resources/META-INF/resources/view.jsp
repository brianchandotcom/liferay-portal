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

<div class="nav-menu sites-directory-taglib">
	<c:choose>
		<c:when test="<%= sitesDirectoryDisplayContext.isHidden() %>">
			<div class="alert alert-info">
				<liferay-ui:message key="no-sites-were-found" />
			</div>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test='<%= Objects.equals(sitesDirectoryDisplayContext.getDisplayStyle(), "descriptive") || Objects.equals(sitesDirectoryDisplayContext.getDisplayStyle(), "icon") %>'>
					<c:choose>
						<c:when test="<%= Validator.isNull(portletDisplay.getId()) %>">
							<div class="alert alert-info">
								<liferay-ui:message arguments="<%= sitesDirectoryDisplayContext.getDisplayStyle() %>" key="the-display-style-x-cannot-be-used-in-this-context" />
							</div>
						</c:when>
						<c:otherwise>
							<liferay-ui:search-container
								searchContainer="<%= sitesDirectoryDisplayContext.getSearchContainer() %>"
							>
								<liferay-ui:search-container-row
									className="com.liferay.portal.kernel.model.Group"
									keyProperty="groupId"
									modelVar="childGroup"
								>
									<c:choose>
										<c:when test='<%= Objects.equals(sitesDirectoryDisplayContext.getDisplayStyle(), "icon") %>'>

											<%
											row.setCssClass("entry-card lfr-asset-item");
											%>

											<liferay-ui:search-container-column-text>
												<clay:vertical-card
													verticalCard="<%= new GroupVerticalCard(childGroup, renderRequest) %>"
												/>
											</liferay-ui:search-container-column-text>
										</c:when>
										<c:otherwise>
											<liferay-ui:search-container-column-image
												src="<%= childGroup.getLogoURL(themeDisplay, true) %>"
											/>

											<liferay-ui:search-container-column-text
												colspan="<%= 2 %>"
											>
												<h5>
													<aui:a href="<%= (childGroup.getGroupId() != scopeGroupId) ? childGroup.getDisplayURL(themeDisplay) : null %>">
														<%= childGroup.getDescriptiveName(locale) %>
													</aui:a>
												</h5>

												<h6 class="text-default">
													<%= HtmlUtil.escape(childGroup.getDescription(locale)) %>
												</h6>

												<h6 class="text-default">
													<liferay-ui:asset-tags-summary
														className="<%= Group.class.getName() %>"
														classPK="<%= childGroup.getGroupId() %>"
													/>
												</h6>

												<h6 class="text-default">
													<liferay-ui:asset-categories-summary
														className="<%= Group.class.getName() %>"
														classPK="<%= childGroup.getGroupId() %>"
													/>
												</h6>
											</liferay-ui:search-container-column-text>
										</c:otherwise>
									</c:choose>
								</liferay-ui:search-container-row>

								<liferay-ui:search-iterator
									displayStyle="<%= sitesDirectoryDisplayContext.getDisplayStyle() %>"
									markupView="lexicon"
								/>
							</liferay-ui:search-container>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>

					<%
					StringBundler sb = new StringBundler();

					_buildSitesList(sitesDirectoryDisplayContext.getRootGroup(), themeDisplay.getScopeGroup(), themeDisplay, 1, Objects.equals(sitesDirectoryDisplayContext.getDisplayStyle(), "list-hierarchy"), true, sb);

					String content = sb.toString();
					%>

					<%= content %>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
</div>

<%!
private void _buildSitesList(Group rootGroup, Group curGroup, ThemeDisplay themeDisplay, int groupLevel, boolean showHierarchy, boolean nestedChildren, StringBundler sb) throws Exception {
	List<Group> childGroups = null;

	if (rootGroup != null) {
		childGroups = rootGroup.getChildrenWithLayouts(true, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new GroupNameComparator(true));
	}
	else {
		childGroups = GroupLocalServiceUtil.getLayoutsGroups(curGroup.getCompanyId(), GroupConstants.DEFAULT_LIVE_GROUP_ID, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new GroupNameComparator(true));
	}

	if (childGroups.isEmpty()) {
		if (sb.length() == 0) {
			sb.append("<div class=\"alert alert-info\">");
			sb.append(LanguageUtil.get(themeDisplay.getLocale(), "no-sites-were-found"));
			sb.append("</div>");
		}

		return;
	}

	StringBundler tailSB = null;

	if (!nestedChildren) {
		tailSB = new StringBundler();
	}

	sb.append("<ul class=\"sites level-");
	sb.append(groupLevel);
	sb.append("\">");

	for (Group childGroup : childGroups) {
		boolean open = false;

		if (showHierarchy) {
			open = true;
		}

		String className = StringPool.BLANK;

		if (open) {
			className += "open ";
		}

		if (curGroup.getGroupId() == childGroup.getGroupId()) {
			className += "selected ";
		}

		sb.append("<li ");

		if (Validator.isNotNull(className)) {
			sb.append("class=\"");
			sb.append(className);
			sb.append("\" ");
		}

		sb.append(">");

		if (childGroup.getGroupId() != themeDisplay.getScopeGroupId()) {
			sb.append("<a ");
		}
		else {
			sb.append("<span ");
		}

		if (Validator.isNotNull(className)) {
			sb.append("class=\"");
			sb.append(className);
			sb.append("\" ");
		}

		if (childGroup.getGroupId() != themeDisplay.getScopeGroupId()) {
			sb.append("href=\"");
			sb.append(HtmlUtil.escapeHREF(childGroup.getDisplayURL(themeDisplay, !childGroup.hasPublicLayouts())));
			sb.append("\"");
		}

		sb.append("> ");

		sb.append(HtmlUtil.escape(childGroup.getDescriptiveName(themeDisplay.getLocale())));

		if (childGroup.getGroupId() != themeDisplay.getScopeGroupId()) {
			sb.append("</a>");
		}
		else {
			sb.append("</span>");
		}

		if (open) {
			StringBundler childGroupSB = null;

			if (nestedChildren) {
				childGroupSB = sb;
			}
			else {
				childGroupSB = tailSB;
			}

			_buildSitesList(childGroup, curGroup, themeDisplay, groupLevel + 1, showHierarchy, nestedChildren, childGroupSB);
		}

		sb.append("</li>");
	}

	sb.append("</ul>");

	if (!nestedChildren) {
		sb.append(tailSB);
	}
}
%>
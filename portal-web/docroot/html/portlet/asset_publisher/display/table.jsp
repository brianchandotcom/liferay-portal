<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
List results = (List)request.getAttribute("view.jsp-results");

int assetEntryIndex = ((Integer)request.getAttribute("view.jsp-assetEntryIndex")).intValue();

AssetEntry assetEntry = (AssetEntry)request.getAttribute("view.jsp-assetEntry");
AssetRendererFactory assetRendererFactory = (AssetRendererFactory)request.getAttribute("view.jsp-assetRendererFactory");
AssetRenderer assetRenderer = (AssetRenderer)request.getAttribute("view.jsp-assetRenderer");

Group stageableGroup = themeDisplay.getScopeGroup();

if (stageableGroup.isLayout()) {
	stageableGroup = layout.getGroup();
}

String title = (String)request.getAttribute("view.jsp-title");

if (Validator.isNull(title)) {
	title = assetRenderer.getTitle(locale);
}

boolean show = ((Boolean)request.getAttribute("view.jsp-show")).booleanValue();

PortletURL editPortletURL = assetRenderer.getURLEdit(liferayPortletRequest, liferayPortletResponse);

PortletURL viewFullContentURL = renderResponse.createRenderURL();

viewFullContentURL.setParameter("struts_action", "/asset_publisher/view_content");
viewFullContentURL.setParameter("assetEntryId", String.valueOf(assetEntry.getEntryId()));
viewFullContentURL.setParameter("type", assetRendererFactory.getType());

if (Validator.isNotNull(assetRenderer.getUrlTitle())) {
	if (assetRenderer.getGroupId() != scopeGroupId) {
		viewFullContentURL.setParameter("groupId", String.valueOf(assetRenderer.getGroupId()));
	}

	viewFullContentURL.setParameter("urlTitle", assetRenderer.getUrlTitle());
}

String viewFullContentURLString = viewFullContentURL.toString();

viewFullContentURLString = HttpUtil.setParameter(viewFullContentURLString, "redirect", currentURL);

String viewURL = viewInContext ? assetRenderer.getURLViewInContext(liferayPortletRequest, liferayPortletResponse, viewFullContentURLString) : viewFullContentURL.toString();

viewURL = _checkViewURL(assetEntry, viewInContext, viewURL, currentURL, themeDisplay);

request.setAttribute("view.jsp-showIconLabel", false);
%>

<c:if test="<%= assetEntryIndex == 0 %>">
	<table class="table table-bordered table-hover table-striped">
	<thead class="table-columns">
	<tr>
		<th class="table-header table-sortable-column">
			<liferay-ui:message key="title" />
		</th>

		<%
		for (int m = 0; m < metadataFields.length; m++) {
		%>

			<th class="table-header">
				<liferay-ui:message key="<%= metadataFields[m] %>" />
			</th>

		<%
		}
		%>

		<c:if test="<%= assetRenderer.hasEditPermission(permissionChecker) && (editPortletURL != null) && !stageableGroup.hasStagingGroup() %>">
			<th class="table-header"></th>
		</c:if>
	</tr>
	</thead>

	<tbody class="table-data">
</c:if>


<tr>
	<td class="table-cell">
		<c:choose>
			<c:when test="<%= Validator.isNotNull(viewURL) %>">
				<a href="<%= viewURL %>"><%= HtmlUtil.escape(title) %></a>
			</c:when>
			<c:otherwise>
				<%= HtmlUtil.escape(title) %>
			</c:otherwise>
		</c:choose>
	</td>

	<%
	for (int m = 0; m < metadataFields.length; m++) {
		String value = null;

		if (metadataFields[m].equals("create-date")) {
			value = dateFormatDate.format(assetEntry.getCreateDate());
		}
		else if (metadataFields[m].equals("modified-date")) {
			value = dateFormatDate.format(assetEntry.getModifiedDate());
		}
		else if (metadataFields[m].equals("publish-date")) {
			if (assetEntry.getPublishDate() == null) {
				value = StringPool.BLANK;
			}
			else {
				value = dateFormatDate.format(assetEntry.getPublishDate());
			}
		}
		else if (metadataFields[m].equals("expiration-date")) {
			if (assetEntry.getExpirationDate() == null) {
				value = StringPool.BLANK;
			}
			else {
				value = dateFormatDate.format(assetEntry.getExpirationDate());
			}
		}
		else if (metadataFields[m].equals("priority")) {
			value = String.valueOf(assetEntry.getPriority());
		}
		else if (metadataFields[m].equals("author")) {
			String userName = PortalUtil.getUserName(assetRenderer.getUserId(), assetRenderer.getUserName());

			value = HtmlUtil.escape(userName);
		}
		else if (metadataFields[m].equals("view-count")) {
			value = String.valueOf(assetEntry.getViewCount());
		}
		else if (metadataFields[m].equals("categories")) {
		%>

			<td class="table-cell">
				<liferay-ui:asset-categories-summary
					className="<%= assetEntry.getClassName() %>"
					classPK="<%= assetEntry.getClassPK() %>"
					portletURL="<%= renderResponse.createRenderURL() %>"
				/>
			</td>

		<%
		}
		else if (metadataFields[m].equals("tags")) {
		%>

			<td class="table-cell">
				<liferay-ui:asset-tags-summary
					className="<%= assetEntry.getClassName() %>"
					classPK="<%= assetEntry.getClassPK() %>"
					portletURL="<%= renderResponse.createRenderURL() %>"
				/>
			</td>

		<%
		}

		if (value != null) {
	%>

			<td class="table-cell">
				<liferay-ui:message key="<%= value %>" />
			</td>

	<%
		}
	}
	%>

	<c:if test="<%= assetRenderer.hasEditPermission(permissionChecker) && (editPortletURL != null) && !stageableGroup.hasStagingGroup() %>">
		<td class="table-cell">
			<liferay-util:include page="/html/portlet/asset_publisher/asset_actions.jsp" />
		</td>
	</c:if>
</tr>

<c:if test="<%= (assetEntryIndex + 1) == results.size() %>">
	</tbody>
	</table>
</c:if>
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

<%
for (int j = 0; j < metadataFields.length; j++) {
	String iconCssClass = StringPool.BLANK;
	String value = null;

	if (metadataFields[j].equals("create-date")) {
		iconCssClass = "icon-calendar";
		value = dateFormatDate.format(assetEntry.getCreateDate());
	}
	else if (metadataFields[j].equals("modified-date")) {
		iconCssClass = "icon-calendar";
		value = dateFormatDate.format(assetEntry.getModifiedDate());
	}
	else if (metadataFields[j].equals("publish-date")) {
		iconCssClass = "icon-calendar";

		if (assetEntry.getPublishDate() == null) {
			value = StringPool.BLANK;
		}
		else {
			value = dateFormatDate.format(assetEntry.getPublishDate());
		}
	}
	else if (metadataFields[j].equals("expiration-date")) {
		iconCssClass = "icon-calendar";

		if (assetEntry.getExpirationDate() == null) {
			value = StringPool.BLANK;
		}
		else {
			value = dateFormatDate.format(assetEntry.getExpirationDate());
		}
	}
	else if (metadataFields[j].equals("priority")) {
		iconCssClass = "icon-long-arrow-up";

		value = LanguageUtil.get(request, "priority") + StringPool.COLON + StringPool.SPACE + assetEntry.getPriority();
	}
	else if (metadataFields[j].equals("author")) {
		iconCssClass = "icon-user";

		String userName = PortalUtil.getUserName(assetRenderer.getUserId(), assetRenderer.getUserName());

		value = LanguageUtil.get(request, "by") + StringPool.SPACE + HtmlUtil.escape(userName);
	}
	else if (metadataFields[j].equals("view-count")) {
		int viewCount = assetEntry.getViewCount();

		value = viewCount + StringPool.SPACE + LanguageUtil.get(request, (viewCount == 1) ? "view" : "views");
	}
	else if (metadataFields[j].equals("categories")) {
		value = "categories";
	}
	else if (metadataFields[j].equals("tags")) {
		value = "tags";
	}

	if (Validator.isNotNull(value)) {
%>

		<span class="metadata-entry <%= "metadata-" + metadataFields[j] %> <%= iconCssClass %>">
			<c:choose>
				<c:when test='<%= value.equals("categories") %>' >
					<liferay-ui:asset-categories-summary
						className="<%= assetEntry.getClassName() %>"
						classPK="<%= assetEntry.getClassPK () %>"
						portletURL="<%= filterByMetadata ? renderResponse.createRenderURL() : null %>"
					/>
				</c:when>
				<c:when test='<%= value.equals("tags") %>' >
					<liferay-ui:asset-tags-summary
						className="<%= assetEntry.getClassName() %>"
						classPK="<%= assetEntry.getClassPK () %>"
						portletURL="<%= filterByMetadata ? renderResponse.createRenderURL() : null %>"
					/>
				</c:when>
				<c:otherwise>
					<%= value %>
				</c:otherwise>
			</c:choose>
		</span>

<%
	}
}
%>
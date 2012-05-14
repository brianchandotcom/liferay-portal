<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/trash/init.jsp" %>

<div class="asset-content">

	<%
	String redirect = ParamUtil.getString(request, "redirect");
	long entryId = ParamUtil.getLong(request, "entryId");

	TrashEntry trashEntry = TrashEntryLocalServiceUtil.getEntry(entryId);

	TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(trashEntry.getClassName());

	TrashRenderer trashRenderer = trashHandler.getTrashRenderer(trashEntry.getClassPK());

	String path = trashRenderer.render(renderRequest, renderResponse, AssetRenderer.TEMPLATE_FULL_CONTENT);
	%>

	<liferay-ui:header
		backURL="<%= redirect %>"
		localizeTitle="<%= false %>"
		title="<%= trashRenderer.getTitle(locale) %>"
	/>

	<c:choose>
		<c:when test="<%= Validator.isNotNull(path) %>">
			<liferay-util:include page="<%= path %>" portletId="<%= trashRenderer.getPortletId() %>">
				<liferay-util:param name="showHeader" value="<%= Boolean.FALSE.toString() %>" />
			</liferay-util:include>
		</c:when>
		<c:otherwise>
			<%= trashRenderer.getSummary(locale) %>
		</c:otherwise>
	</c:choose>

	<c:if test="<%= trashRenderer instanceof AssetRenderer %>">

		<%
		AssetRenderer assetRenderer = (AssetRenderer)trashRenderer;
		%>

		<c:if test="<%= !assetRenderer.getAssetRendererFactoryClassName().equals(DLFileEntryAssetRendererFactory.CLASS_NAME) %>">
			<div class="asset-ratings">
				<liferay-ui:ratings
					className="<%= trashEntry.getClassName() %>"
					classPK="<%= trashEntry.getClassPK() %>"
				/>
			</div>

			<%
			AssetEntry entry = AssetEntryLocalServiceUtil.getEntry(trashEntry.getClassName(), trashEntry.getClassPK());
			%>

			<div class="asset-related-assets">
				<liferay-ui:asset-links
					assetEntryId="<%= entry.getEntryId() %>"
				/>
			</div>

			<c:if test="<%= Validator.isNotNull(assetRenderer.getDiscussionPath()) %>">
				<portlet:actionURL var="discussionURL">
					<portlet:param name="struts_action" value="/trash/edit_discussion" />
				</portlet:actionURL>

				<div class="asset-discussion">
					<liferay-ui:discussion
						className="<%= trashEntry.getClassName() %>"
						classPK="<%= trashEntry.getClassPK() %>"
						formAction="<%= discussionURL %>"
						formName='<%= "fm" + trashEntry.getClassPK() %>'
						redirect="<%= currentURL %>"
						subject="<%= trashRenderer.getTitle(locale) %>"
						userId="<%= trashEntry.getUserId() %>"
					/>
				</div>
			</c:if>
		</c:if>
	</c:if>
</div>
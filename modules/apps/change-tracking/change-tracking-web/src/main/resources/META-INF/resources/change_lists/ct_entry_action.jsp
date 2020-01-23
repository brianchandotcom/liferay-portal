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

<%@ include file="/change_lists/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CTEntry ctEntry = (CTEntry)row.getObject();

ViewChangesDisplayContext viewChangesDisplayContext = (ViewChangesDisplayContext)request.getAttribute(CTWebKeys.VIEW_CHANGES_DISPLAY_CONTEXT);

CTCollection ctCollection = viewChangesDisplayContext.getCtCollection();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:choose>
		<c:when test="<%= viewChangesDisplayContext.isPublished() %>">
			<liferay-ui:icon
				message="view"
				url="<%= ctDisplayRendererRegistry.getViewURL(liferayPortletRequest, liferayPortletResponse, ctEntry, false) %>"
			/>
		</c:when>
		<c:otherwise>

			<%
			String editURL = ctDisplayRendererRegistry.getEditURL(request, ctEntry);
			%>

			<c:if test="<%= Validator.isNotNull(editURL) && (ctCollection.getCtCollectionId() == changeListsDisplayContext.getCtCollectionId()) %>">
				<liferay-ui:icon
					message="edit"
					url="<%= editURL %>"
				/>
			</c:if>

			<%
			PortletURL diffURL = renderResponse.createRenderURL();

			diffURL.setParameter("mvcRenderCommandName", "/change_lists/view_diff");
			diffURL.setParameter("ctEntryId", String.valueOf(ctEntry.getCtEntryId()));

			diffURL.setWindowState(LiferayWindowState.POP_UP);
			%>

			<liferay-ui:icon
				message="view-diff"
				url='<%= StringBundler.concat("javascript:Liferay.Util.openWindow({dialog: {destroyOnHide: true}, title: '", ctDisplayRendererRegistry.getEntryTitle(ctEntry, request), "', uri: '", diffURL.toString(), "'});") %>'
			/>
		</c:otherwise>
	</c:choose>
</liferay-ui:icon-menu>
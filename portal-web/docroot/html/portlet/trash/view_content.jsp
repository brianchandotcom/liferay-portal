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

	long trashEntryId = ParamUtil.getLong(request, "trashEntryId");

	String className = ParamUtil.getString(request, "className");
	long classPK = ParamUtil.getLong(request, "classPK");

	TrashEntry entry = null;

	if (trashEntryId > 0) {
		entry = TrashEntryLocalServiceUtil.getEntry(trashEntryId);
	}
	else if (Validator.isNotNull(className) && (classPK > 0)) {
		entry = TrashEntryLocalServiceUtil.fetchEntry(className, classPK);
	}

	if (entry != null) {
		className = entry.getClassName();
		classPK = entry.getClassPK();
	}

	TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(className);

	TrashRenderer trashRenderer = trashHandler.getTrashRenderer(classPK);

	String path = trashRenderer.render(renderRequest, renderResponse, AssetRenderer.TEMPLATE_FULL_CONTENT);
	%>

	<liferay-ui:header
		backURL="<%= redirect %>"
		localizeTitle="<%= false %>"
		title="<%= trashRenderer.getTitle(locale) %>"
	/>

	<c:if test="<%= ((entry != null) && (entry.getRootEntry() == null)) || Validator.isNotNull(trashRenderer.renderActions(renderRequest, renderResponse)) %>">

		<%
		request.setAttribute(WebKeys.TRASH_ENTRY, entry);
		%>

		<liferay-util:include page='<%= (entry != null) && (entry.getRootEntry() == null) ? "/html/portlet/trash/entry_action.jsp" : trashRenderer.renderActions(renderRequest, renderResponse) %>' />
	</c:if>

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
					className="<%= entry.getClassName() %>"
					classPK="<%= entry.getClassPK() %>"
				/>
			</div>

			<%
			AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(entry.getClassName(), entry.getClassPK());
			%>

			<div class="asset-related-assets">
				<liferay-ui:asset-links
					assetEntryId="<%= assetEntry.getEntryId() %>"
				/>
			</div>

			<c:if test="<%= Validator.isNotNull(assetRenderer.getDiscussionPath()) %>">
				<portlet:actionURL var="discussionURL">
					<portlet:param name="struts_action" value="/trash/edit_discussion" />
				</portlet:actionURL>

				<div class="asset-discussion">
					<liferay-ui:discussion
						className="<%= entry.getClassName() %>"
						classPK="<%= entry.getClassPK() %>"
						formAction="<%= discussionURL %>"
						formName='<%= "fm" + entry.getClassPK() %>'
						redirect="<%= currentURL %>"
						subject="<%= trashRenderer.getTitle(locale) %>"
						userId="<%= entry.getUserId() %>"
					/>
				</div>
			</c:if>
		</c:if>
	</c:if>
</div>

<aui:script use="liferay-restore-entry">
	new Liferay.RestoreEntry(
		{
			checkEntryURL: '<portlet:actionURL><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.CHECK %>" /><portlet:param name="struts_action" value="/trash/edit_entry" /></portlet:actionURL>',
			namespace: '<portlet:namespace />',
			restoreEntryURL: '<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/trash/restore_entry" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>'
		}
	);
</aui:script>
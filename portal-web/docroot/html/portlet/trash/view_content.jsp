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

<liferay-util:include page="/html/portlet/trash/restore_path.jsp" />

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
		<c:when test="<%= trashHandler.isContainerModel() %>">

			<%
			PortletURL iteratorURL = renderResponse.createRenderURL();

			iteratorURL.setParameter("struts_action", "/trash/view_content");
			iteratorURL.setParameter("redirect", redirect);
			iteratorURL.setParameter("className", className);
			iteratorURL.setParameter("classPK", String.valueOf(classPK));

			int containerModelsCount = trashHandler.getTrashContainerModelsCount(classPK);
			int baseModelsCount = trashHandler.getTrashContainedModelsCount(classPK);
			%>

			<liferay-ui:panel-container extended="<%= false %>" id="containerDisplayInfoPanelContainer" persistState="<%= true %>">
				<c:if test="<%= containerModelsCount > 0 %>">
					<liferay-ui:panel collapsible="<%= true %>" cssClass="view-folders" extended="<%= true %>" id="containerModelsListingPanel" persistState="<%= true %>" title="<%= trashHandler.getTrashContainerModelName() %>">
						<liferay-ui:search-container
							curParam="cur1"
							deltaConfigurable="<%= false %>"
							iteratorURL="<%= iteratorURL %>"
						>
							<liferay-ui:search-container-results
								results="<%= trashHandler.getTrashContainerModelTrashRenderers(classPK, searchContainer.getStart(), searchContainer.getEnd()) %>"
								total="<%= containerModelsCount %>"
							/>

							<liferay-ui:search-container-row
								className="com.liferay.portal.kernel.trash.TrashRenderer"
								modelVar="curTrashRenderer"
							>

								<%
								PortletURL rowURL = renderResponse.createRenderURL();

								rowURL.setParameter("struts_action", "/trash/view_content");
								rowURL.setParameter("redirect", currentURL);
								rowURL.setParameter("className", (curTrashRenderer.getClassName()));
								rowURL.setParameter("classPK", String.valueOf(curTrashRenderer.getClassPK()));
								%>

								<liferay-ui:search-container-column-text
									name="name"
								>
									<liferay-ui:icon
										label="<%= true %>"
										message="<%= HtmlUtil.escape(curTrashRenderer.getTitle(locale)) %>"
										src="<%= curTrashRenderer.getIconPath(renderRequest) %>"
										url="<%= rowURL.toString() %>"
									/>
								</liferay-ui:search-container-column-text>

								<liferay-ui:search-container-column-text
									name='<%= LanguageUtil.format(pageContext, "num-of-x", trashHandler.getTrashContainedModelName(), true) %>'
									value="<%= String.valueOf(baseModelsCount) %>"
								/>

								<liferay-ui:search-container-column-text
									name='<%= LanguageUtil.format(pageContext, "num-of-x", trashHandler.getTrashContainerModelName(), true) %>'
									value="<%= String.valueOf(containerModelsCount) %>"
								/>

								<liferay-ui:search-container-column-jsp
									align="right"
									path="/html/portlet/trash/view_content_action.jsp"
								/>
							</liferay-ui:search-container-row>

							<liferay-ui:search-iterator />
						</liferay-ui:search-container>
					</liferay-ui:panel>
				</c:if>

				<c:if test="<%= baseModelsCount > 0 %>">
					<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="baseModelsListingPanel" persistState="<%= true %>" title="<%= trashHandler.getTrashContainedModelName() %>">
						<liferay-ui:search-container
							curParam="cur2"
							deltaConfigurable="<%= false %>"
							iteratorURL="<%= iteratorURL %>"
						>
							<liferay-ui:search-container-results
								results="<%= trashHandler.getTrashContainedModelTrashRenderers(classPK, searchContainer.getStart(), searchContainer.getEnd()) %>"
								total="<%= baseModelsCount %>"
							/>

							<liferay-ui:search-container-row
								className="com.liferay.portal.kernel.trash.TrashRenderer"
								modelVar="curTrashRenderer"
							>

								<%
								PortletURL rowURL = renderResponse.createRenderURL();

								rowURL.setParameter("struts_action", "/trash/view_content");
								rowURL.setParameter("redirect", currentURL);
								rowURL.setParameter("className", curTrashRenderer.getClassName());
								rowURL.setParameter("classPK", String.valueOf(curTrashRenderer.getClassPK()));
								%>

								<liferay-ui:search-container-column-text
									name="name"
								>
									<liferay-ui:icon
										label="<%= true %>"
										message="<%= HtmlUtil.escape(curTrashRenderer.getTitle(locale)) %>"
										src="<%= curTrashRenderer.getIconPath(renderRequest) %>"
										url="<%= rowURL.toString() %>"
									/>
								</liferay-ui:search-container-column-text>

								<liferay-ui:search-container-column-jsp
									align="right"
									path="/html/portlet/trash/view_content_action.jsp"
								/>
							</liferay-ui:search-container-row>

							<liferay-ui:search-iterator />
						</liferay-ui:search-container>
					</liferay-ui:panel>
				</c:if>
			</liferay-ui:panel-container>
		</c:when>
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
					className="<%= className %>"
					classPK="<%= classPK %>"
				/>
			</div>

			<%
			AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(className, classPK);
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
						className="<%= className %>"
						classPK="<%= classPK %>"
						formAction="<%= discussionURL %>"
						formName='<%= "fm" + classPK %>'
						redirect="<%= currentURL %>"
						subject="<%= trashRenderer.getTitle(locale) %>"
						userId="<%= assetEntry.getUserId() %>"
					/>
				</div>
			</c:if>
		</c:if>
	</c:if>
</div>
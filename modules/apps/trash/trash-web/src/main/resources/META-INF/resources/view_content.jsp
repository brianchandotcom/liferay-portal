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
String displayStyle = ParamUtil.getString(request, "displayStyle", "list");

String redirect = ParamUtil.getString(request, "redirect");

long trashEntryId = ParamUtil.getLong(request, "trashEntryId");

long classNameId = ParamUtil.getLong(request, "classNameId");

String className = StringPool.BLANK;

if (classNameId != 0) {
	className = PortalUtil.getClassName(classNameId);
}

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

PortletURL portletURL = renderResponse.createRenderURL();

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "recycle-bin"), portletURL.toString());

PortletURL containerModelURL = renderResponse.createRenderURL();

String trashHandlerContainerModelClassName = trashHandler.getContainerModelClassName(classPK);

containerModelURL.setParameter("mvcPath", "/view_content.jsp");
containerModelURL.setParameter("classNameId", String.valueOf(PortalUtil.getClassNameId(trashHandlerContainerModelClassName)));

TrashUtil.addBaseModelBreadcrumbEntries(request, liferayPortletResponse, className, classPK, containerModelURL);

if (Validator.isNull(redirect)) {
	ContainerModel parentContainerModel = trashHandler.getParentContainerModel(classPK);

	PortletURL redirectURL = renderResponse.createRenderURL();

	if ((parentContainerModel != null) && (classNameId > 0)) {
		String parentContainerModelClassName = parentContainerModel.getModelClassName();

		redirectURL.setParameter("mvcPath", "/view_content.jsp");
		redirectURL.setParameter("classNameId", String.valueOf(PortalUtil.getClassNameId(parentContainerModelClassName)));
		redirectURL.setParameter("classPK", String.valueOf(parentContainerModel.getContainerModelId()));
	}

	redirect = redirectURL.toString();
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(trashRenderer.getTitle(locale));
%>

<liferay-util:include page="/navigation.jsp" servletContext="<%= application %>" />

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= PortletURLUtil.clone(containerModelURL, renderResponse) %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= PortletURLUtil.clone(containerModelURL, renderResponse) %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<liferay-util:include page="/restore_path.jsp" servletContext="<%= application %>" />

<div class="asset-content container-fluid-1280" id="<portlet:namespace />trashContainer">
	<liferay-ui:breadcrumb
		showCurrentGroup="<%= false %>"
		showGuestGroup="<%= false %>"
		showLayout="<%= false %>"
		showParentGroups="<%= false %>"
	/>

	<liferay-util:include page="/info_panel_content.jsp" servletContext="<%= application %>" />

	<%
	PortletURL iteratorURL = renderResponse.createRenderURL();

	iteratorURL.setParameter("mvcPath", "/view_content.jsp");
	iteratorURL.setParameter("classNameId", String.valueOf(classNameId));
	iteratorURL.setParameter("classPK", String.valueOf(classPK));

	String emptyResultsMessage = LanguageUtil.format(request, "this-x-does-not-contain-an-entry", ResourceActionsUtil.getModelResource(locale, className), false);
	%>

	<liferay-ui:search-container
		deltaConfigurable="<%= false %>"
		emptyResultsMessage="<%= emptyResultsMessage %>"
		iteratorURL="<%= iteratorURL %>"
		total="<%= trashHandler.getTrashModelsCount(classPK) %>"
	>
		<liferay-ui:search-container-results
			results="<%= trashHandler.getTrashModelTrashRenderers(classPK, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.trash.TrashRenderer"
			modelVar="curTrashRenderer"
		>

			<%
			TrashHandler curTrashHandler = TrashHandlerRegistryUtil.getTrashHandler(curTrashRenderer.getClassName());
			%>

			<liferay-ui:search-container-column-text
				cssClass="text-strong"
				name="name"
			>
				<c:choose>
					<c:when test="<%= curTrashHandler.isContainerModel() %>">

						<%
						PortletURL rowURL = renderResponse.createRenderURL();

						rowURL.setParameter("mvcPath", "/view_content.jsp");
						rowURL.setParameter("classNameId", String.valueOf(PortalUtil.getClassNameId(curTrashRenderer.getClassName())));
						rowURL.setParameter("classPK", String.valueOf(curTrashRenderer.getClassPK()));
						%>

						<aui:a href="<%= rowURL.toString() %>">
							<%= HtmlUtil.escape(curTrashRenderer.getTitle(locale)) %>
						</aui:a>
					</c:when>
					<c:otherwise>

						<%
						PortletURL rowURL = renderResponse.createRenderURL();

						rowURL.setParameter("mvcPath", "/preview.jsp");
						rowURL.setParameter("classNameId", String.valueOf(PortalUtil.getClassNameId(curTrashRenderer.getClassName())));
						rowURL.setParameter("classPK", String.valueOf(curTrashRenderer.getClassPK()));

						rowURL.setWindowState(LiferayWindowState.POP_UP);

						Map<String, Object> data = new HashMap<String, Object>();

						data.put("title", HtmlUtil.escape(curTrashRenderer.getTitle(locale)));
						data.put("url", rowURL.toString());
						%>

						<aui:a cssClass="preview" data="<%= data %>" href="javascript:;">
							<%= HtmlUtil.escape(curTrashRenderer.getTitle(locale)) %>
						</aui:a>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="type"
				value="<%= ResourceActionsUtil.getModelResource(locale, curTrashRenderer.getClassName()) %>"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="list-group-item-field"
				path="/view_content_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-url-preview">
	A.one('#<portlet:namespace />trashContainer').delegate(
		'click',
		function(event) {
			var currentTarget = event.currentTarget;

			var urlPreview = new Liferay.UrlPreview(
				{
					title: currentTarget.attr('data-title'),
					url: currentTarget.attr('data-url')
				}
			);

			urlPreview.open();
		},
		'.preview'
	);
</aui:script>
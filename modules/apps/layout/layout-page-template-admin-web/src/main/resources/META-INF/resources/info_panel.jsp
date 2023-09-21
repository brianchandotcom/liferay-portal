<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DisplayPageTemplateInfoPanelDisplayContext displayPageTemplateInfoPanelDisplayContext = new DisplayPageTemplateInfoPanelDisplayContext(request, renderRequest, renderResponse);

List<LayoutPageTemplateCollection> layoutPageTemplateCollections = displayPageTemplateInfoPanelDisplayContext.getLayoutPageTemplateCollections();
List<LayoutPageTemplateEntry> layoutPageTemplateEntries = displayPageTemplateInfoPanelDisplayContext.getLayoutPageTemplateEntries();

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<c:choose>
	<c:when test="<%= ListUtil.isEmpty(layoutPageTemplateCollections) && ListUtil.isNotEmpty(layoutPageTemplateEntries) && (layoutPageTemplateEntries.size() == 1) %>">

		<%
		LayoutPageTemplateEntry layoutPageTemplateEntry = layoutPageTemplateEntries.get(0);
		%>

		<div class="sidebar-header">
			<clay:content-row
				cssClass="sidebar-section"
			>
				<clay:content-col
					expand="<%= true %>"
				>
					<h1 class="component-title">
						<%= HtmlUtil.escape(layoutPageTemplateEntry.getName()) %>
					</h1>

					<h2 class="component-subtitle">
						<liferay-ui:message key="display-page-template" />
					</h2>
				</clay:content-col>
			</clay:content-row>
		</div>

		<div class="sidebar-body">
			<p class="sidebar-dt">
				<liferay-ui:message key="location" />
			</p>

			<p class="sidebar-dd text-secondary">
				<clay:icon
					symbol="folder"
				/>
				<%= StringUtil.merge(displayPageTemplateInfoPanelDisplayContext.getLayoutPageTemplateCollectionPath(ParamUtil.getLong(request, "layoutPageTemplateCollectionId")), " > ") %>
			</p>

			<p class="sidebar-dt">
				<liferay-ui:message key="content-type" />
			</p>

			<p class="sidebar-dd text-secondary">
				<%= displayPageTemplateInfoPanelDisplayContext.getTypeLabel(layoutPageTemplateEntry) %>
			</p>

			<p class="sidebar-dt">
				<liferay-ui:message key="subtype" />
			</p>

			<p class="sidebar-dd text-secondary">
				<%= displayPageTemplateInfoPanelDisplayContext.getSubtypeLabel(layoutPageTemplateEntry) %>
			</p>

			<p class="sidebar-dt">
				<liferay-ui:message key="created" />
			</p>

			<p class="sidebar-dd text-secondary">
				<%= dateFormatDateTime.format(layoutPageTemplateEntry.getCreateDate()) %>
			</p>

			<p class="sidebar-dt">
				<liferay-ui:message key="modified" />
			</p>

			<p class="sidebar-dd text-secondary">
				<%= dateFormatDateTime.format(layoutPageTemplateEntry.getModifiedDate()) %>
			</p>
		</div>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<clay:content-row
				cssClass="sidebar-section"
			>
				<clay:content-col
					expand="<%= true %>"
				>
					<h1 class="component-title"><liferay-ui:message arguments="<%= layoutPageTemplateCollections.size() + layoutPageTemplateEntries.size() %>" key="x-items-are-selected" /></h1>
				</clay:content-col>
			</clay:content-row>
		</div>
	</c:otherwise>
</c:choose>
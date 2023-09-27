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
	<c:when test="<%= ListUtil.isNotEmpty(layoutPageTemplateCollections) && ListUtil.isEmpty(layoutPageTemplateEntries) && (layoutPageTemplateCollections.size() == 1) %>">

		<%
		LayoutPageTemplateCollection layoutPageTemplateCollection = layoutPageTemplateCollections.get(0);
		%>

		<div class="sidebar-header">
			<clay:content-row
				cssClass="sidebar-section"
			>
				<clay:content-col
					expand="<%= true %>"
				>
					<h1 class="component-title">
						<%= (layoutPageTemplateCollection != null) ? HtmlUtil.escape(layoutPageTemplateCollection.getName()) : LanguageUtil.get(request, "home") %>
					</h1>

					<h2 class="component-subtitle">
						<liferay-ui:message key="folder" />
					</h2>
				</clay:content-col>
			</clay:content-row>
		</div>

		<div class="sidebar-body">
			<p class="sidebar-dt">
				<liferay-ui:message key="num-of-items" />
			</p>

			<c:if test="<%= layoutPageTemplateCollection == null %>">
				<p class="sidebar-dd text-secondary">
					<%= displayPageTemplateInfoPanelDisplayContext.getHomeItemsCount(scopeGroupId) %>
				</p>
			</c:if>

			<c:if test="<%= layoutPageTemplateCollection != null %>">
				<p class="sidebar-dd text-secondary">
					<%= displayPageTemplateInfoPanelDisplayContext.getLayoutPageTemplateCollectionItemsCount(layoutPageTemplateCollection) %>
				</p>

				<p class="sidebar-dt">
					<liferay-ui:message key="location" />
				</p>

				<p class="sidebar-dd text-secondary">
					<clay:icon
						symbol="folder"
					/>

					<%= StringUtil.merge(displayPageTemplateInfoPanelDisplayContext.getLayoutPageTemplateCollectionPath(layoutPageTemplateCollection.getParentLayoutPageTemplateCollectionId()), " > ") %>
				</p>

				<p class="sidebar-dt">
					<liferay-ui:message key="created" />
				</p>

				<p class="sidebar-dd text-secondary">
					<%= dateFormatDateTime.format(layoutPageTemplateCollection.getCreateDate()) %>
				</p>

				<p class="sidebar-dt">
					<liferay-ui:message key="modified" />
				</p>

				<p class="sidebar-dd text-secondary">
					<%= dateFormatDateTime.format(layoutPageTemplateCollection.getModifiedDate()) %>
				</p>

				<p class="sidebar-dt">
					<liferay-ui:message key="description" />
				</p>

				<p class="sidebar-dd text-secondary">
					<%= HtmlUtil.escape(layoutPageTemplateCollection.getDescription()) %>
				</p>
			</c:if>
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
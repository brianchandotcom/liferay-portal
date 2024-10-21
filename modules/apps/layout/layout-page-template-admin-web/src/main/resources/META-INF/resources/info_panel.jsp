<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DisplayPageTemplateInfoPanelDisplayContext displayPageTemplateInfoPanelDisplayContext = new DisplayPageTemplateInfoPanelDisplayContext(request, liferayPortletRequest, liferayPortletResponse);

List<LayoutPageTemplateCollection> layoutPageTemplateCollections = displayPageTemplateInfoPanelDisplayContext.getLayoutPageTemplateCollections();
List<LayoutPageTemplateEntry> layoutPageTemplateEntries = displayPageTemplateInfoPanelDisplayContext.getLayoutPageTemplateEntries();

Format dateTimeFormat = FastDateFormatFactoryUtil.getDateTime(DateFormat.MEDIUM, DateFormat.MEDIUM, locale, timeZone);
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
					<h1 class="c-mb-1 component-title">
						<%= HtmlUtil.escape(layoutPageTemplateEntry.getName()) %>
					</h1>

					<h2 class="c-mb-1 component-subtitle font-weight-normal">
						<liferay-ui:message key="display-page-template" />
					</h2>
				</clay:content-col>
			</clay:content-row>

			<clay:content-row
				cssClass="sidebar-section"
			>
				<c:if test="<%= layoutPageTemplateEntry.isDefaultTemplate() %>">
					<div>
						<clay:label
							displayType="info"
							label='<%= LanguageUtil.get(request, "default") %>'
						/>
					</div>
				</c:if>

				<liferay-portal-workflow:status
					showStatusLabel="<%= false %>"
					status="<%= layoutPageTemplateEntry.getStatus() %>"
					statusMessage="<%= WorkflowConstants.getStatusLabel(layoutPageTemplateEntry.getStatus()) %>"
				/>
			</clay:content-row>
		</div>

		<div class="sidebar-body">
			<clay:button
				additionalProps='<%=
					HashMapBuilder.<String, Object>put(
						"permissionsURL", displayPageTemplateInfoPanelDisplayContext.getPermissionsLayoutPageTemplateEntryURL(layoutPageTemplateEntry)
					).build()
				%>'
				cssClass="c-mb-4"
				displayType="secondary"
				label="manage-permissions"
				propsTransformer="{ManagePermissionsPropsTransformer} from layout-page-template-admin-web"
				small="<%= true %>"
			/>

			<div class="c-mb-4">
				<p class="c-mb-1 font-weight-semi-bold text-3">
					<liferay-ui:message key="location" />
				</p>

				<p class="sidebar-dd text-secondary">
					<clay:icon
						symbol="folder"
					/>
					<%= StringUtil.merge(displayPageTemplateInfoPanelDisplayContext.getLayoutPageTemplateCollectionPath(), " > ") %>
				</p>
			</div>

			<div class="c-mb-4">
				<p class="c-mb-0 font-weight-semi-bold text-3">
					<liferay-ui:message key="content-type" />
				</p>

				<clay:label
					displayType="secondary"
					label="<%= displayPageTemplateInfoPanelDisplayContext.getTypeLabel(layoutPageTemplateEntry) %>"
				/>
			</div>

			<c:if test="<%= !displayPageTemplateInfoPanelDisplayContext.getSubtypeLabel(layoutPageTemplateEntry).isEmpty() %>">
				<div class="c-mb-4">
					<p class="font-weight-semi-bold mb-0 text-3">
						<liferay-ui:message key="subtype" />
					</p>

					<clay:label
						displayType="secondary"
						label="<%= displayPageTemplateInfoPanelDisplayContext.getSubtypeLabel(layoutPageTemplateEntry) %>"
					/>
				</div>
			</c:if>

			<div class="c-mb-4">
				<p class="c-mb-1 font-weight-semi-bold text-3">
					<liferay-ui:message key="created" />
				</p>

				<p class="sidebar-dd text-secondary">
					<liferay-ui:message arguments="<%= new Object[] {dateTimeFormat.format(layoutPageTemplateEntry.getCreateDate()), HtmlUtil.escape(layoutPageTemplateEntry.getUserName())} %>" key="x-by-x" translateArguments="<%= false %>" />
				</p>
			</div>

			<div class="c-mb-4">
				<p class="c-mb-1 font-weight-semi-bold text-3">
					<liferay-ui:message key="modified" />
				</p>

				<p class="sidebar-dd text-secondary">
					<liferay-ui:message arguments="<%= new Object[] {dateTimeFormat.format(layoutPageTemplateEntry.getModifiedDate()), displayPageTemplateInfoPanelDisplayContext.getUserName(layoutPageTemplateEntry.getStatusByUserId())} %>" key="x-by-x" translateArguments="<%= false %>" />
				</p>
			</div>
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
					<h1 class="c-mb-1 component-title">
						<%= (layoutPageTemplateCollection != null) ? HtmlUtil.escape(layoutPageTemplateCollection.getName()) : LanguageUtil.get(request, "home") %>
					</h1>

					<h2 class="c-mb-1 component-subtitle font-weight-normal">
						<liferay-ui:message key="folder" />
					</h2>
				</clay:content-col>
			</clay:content-row>
		</div>

		<div class="sidebar-body">
			<c:if test="<%= layoutPageTemplateCollection != null %>">
				<clay:button
					additionalProps='<%=
						HashMapBuilder.<String, Object>put(
							"permissionsURL", displayPageTemplateInfoPanelDisplayContext.getPermissionsLayoutPageTemplateEntryCollectionURL(layoutPageTemplateCollection)
						).build()
					%>'
					cssClass="c-mb-4"
					displayType="secondary"
					label="manage-permissions"
					propsTransformer="{ManagePermissionsPropsTransformer} from layout-page-template-admin-web"
					small="<%= true %>"
				/>
			</c:if>

			<c:if test="<%= layoutPageTemplateCollection != null %>">
				<div class="c-mb-4">
					<p class="c-mb-0 font-weight-semi-bold text-3">
						<liferay-ui:message key="location" />
					</p>

					<p class="sidebar-dd text-secondary">
						<clay:icon
							symbol="folder"
						/>

						<%= StringUtil.merge(displayPageTemplateInfoPanelDisplayContext.getLayoutPageTemplateCollectionPath(), " > ") %>
					</p>
				</div>
			</c:if>

			<div class="c-mb-4">
				<p class="c-mb-0 font-weight-semi-bold text-3">
					<liferay-ui:message key="number-of-items" />
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
				</c:if>
			</div>

			<c:if test="<%= layoutPageTemplateCollection != null %>">
				<div class="c-mb-4">
					<p class="c-mb-0 font-weight-semi-bold text-3">
						<liferay-ui:message key="created" />
					</p>

					<p class="sidebar-dd text-secondary">
						<liferay-ui:message arguments="<%= new Object[] {dateTimeFormat.format(layoutPageTemplateCollection.getCreateDate()), HtmlUtil.escape(layoutPageTemplateCollection.getUserName())} %>" key="x-by-x" translateArguments="<%= false %>" />
					</p>
				</div>

				<div class="c-mb-4">
					<p class="c-mb-0 font-weight-semi-bold text-3">
						<liferay-ui:message key="modified" />
					</p>

					<p class="sidebar-dd text-secondary">
						<liferay-ui:message arguments="<%= new Object[] {dateTimeFormat.format(layoutPageTemplateCollection.getModifiedDate()), HtmlUtil.escape(layoutPageTemplateCollection.getUserName())} %>" key="x-by-x" translateArguments="<%= false %>" />
					</p>
				</div>

				<div class="c-mb-4">
					<c:if test="<%= !layoutPageTemplateCollection.getDescription().isEmpty() %>">
						<p class="c-mb-0 font-weight-semi-bold text-3">
							<liferay-ui:message key="description" />
						</p>

						<p class="sidebar-dd text-secondary">
							<%= HtmlUtil.escape(layoutPageTemplateCollection.getDescription()) %>
						</p>
					</c:if>
				</div>
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
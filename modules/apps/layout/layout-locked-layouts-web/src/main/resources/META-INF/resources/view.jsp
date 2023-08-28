<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
LockedLayoutsDisplayContext lockedLayoutsDisplayContext = (LockedLayoutsDisplayContext)request.getAttribute(LockedLayoutsDisplayContext.class.getName());
%>

<clay:sheet>
	<clay:sheet-header>
		<clay:content-row
			containerElement="h2"
			cssClass="sheet-subtitle"
		>
			<clay:content-col
				expand="<%= true %>"
			>
				<span class="heading-text"><liferay-ui:message key="locked-pages" /></span>
			</clay:content-col>
		</clay:content-row>
	</clay:sheet-header>

	<clay:sheet-section>
		<clay:content-row>
			<clay:content-col
				expand="<%= true %>"
			>
				<p class="text-secondary"><liferay-ui:message key="admins-can-manually-unlock-pages-that-are-being-used-by-other-users.-please-note-that-the-current-user-may-lose-control-over-the-page-edition-if-you-unlock-it" /></p>
			</clay:content-col>
		</clay:content-row>

		<clay:content-row>
			<clay:content-col
				expand="<%= true %>"
			>
				<c:if test="<%= lockedLayoutsDisplayContext.existLockedLayouts() %>">
					<clay:management-toolbar
						managementToolbarDisplayContext="<%= new LockedLayoutsSearchContainerManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, lockedLayoutsDisplayContext) %>"
					/>
				</c:if>
				<liferay-ui:search-container
					id="lockedLayoutsSearchContainer"
					searchContainer="<%= lockedLayoutsDisplayContext.getSearchContainer() %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.layout.model.LockedLayout"
						keyProperty="plid"
						modelVar="lockedLayout"
					>

						<liferay-ui:search-container-column-text
							cssClass="modify-text"
							name="name"
							value="<%= HtmlUtil.escape(lockedLayoutsDisplayContext.getName(lockedLayout)) %>"
						/>

						<liferay-ui:search-container-column-text
							cssClass="modify-text"
							name="type"
							value="<%= lockedLayoutsDisplayContext.getLayoutType(lockedLayout) %>"
						/>

						<liferay-ui:search-container-column-text
							cssClass="modify-text"
							name="current-user"
							value="<%= HtmlUtil.escape(lockedLayout.getUserName()) %>"
						/>

						<liferay-ui:search-container-column-text
							cssClass="modify-text"
							name="last-autosave"
							value="<%= lockedLayoutsDisplayContext.getLastAutoSave(lockedLayout) %>"
						/>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						markupView="lexicon"
						paginate="<%= false %>"
					/>
				</liferay-ui:search-container>
			</clay:content-col>
		</clay:content-row>
	</clay:sheet-section>
</clay:sheet>
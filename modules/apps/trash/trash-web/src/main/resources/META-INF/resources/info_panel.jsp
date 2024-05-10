<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
List<TrashEntry> trashEntries = (List<TrashEntry>)request.getAttribute(TrashWebKeys.TRASH_ENTRIES);
%>

<c:choose>
	<c:when test="<%= ListUtil.isNotEmpty(trashEntries) %>">
		<c:choose>
			<c:when test="<%= trashEntries.size() == 1 %>">

				<%
				TrashEntry trashEntry = trashEntries.get(0);

				TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(trashEntry.getClassName());

				TrashRenderer trashRenderer = trashHandler.getTrashRenderer(trashEntry.getClassPK());
				%>

				<div class="sidebar-header">
					<clay:content-row
						cssClass="sidebar-section"
					>
						<clay:content-col
							expand="<%= true %>"
						>
							<div class="component-title"><%= HtmlUtil.escape(trashRenderer.getTitle(locale)) %></div>

							<p class="component-subtitle">
								<%= ResourceActionsUtil.getModelResource(locale, trashEntry.getClassName()) %>
							</p>
						</clay:content-col>

						<clay:content-col>
							<ul class="autofit-padded-no-gutters autofit-row">
								<li class="autofit-col">
									<c:choose>
										<c:when test="<%= trashEntry.getRootEntry() == null %>">
											<clay:dropdown-actions
												additionalProps='<%=
													HashMapBuilder.<String, Object>put(
														"portletNamespace", liferayPortletResponse.getNamespace()
													).build()
												%>'
												aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
												dropdownItems="<%= trashDisplayContext.getTrashEntryActionDropdownItems(trashEntry) %>"
												propsTransformer="{EntriesPropsTransformer} from trash-web"
											/>
										</c:when>
										<c:otherwise>
											<clay:dropdown-actions
												additionalProps='<%=
													HashMapBuilder.<String, Object>put(
														"portletNamespace", liferayPortletResponse.getNamespace()
													).build()
												%>'
												aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
												dropdownItems="<%= trashDisplayContext.getTrashViewContentActionDropdownItems(trashRenderer.getClassName(), trashRenderer.getClassPK()) %>"
												propsTransformer="{EntriesPropsTransformer} from trash-web"
											/>
										</c:otherwise>
									</c:choose>
								</li>
							</ul>
						</clay:content-col>
					</clay:content-row>
				</div>

				<clay:tabs
					tabsItems="<%= trashDisplayContext.getTabsItems() %>"
				>
					<clay:tabs-panel>
						<dl class="sidebar-dl sidebar-section">
							<dt class="sidebar-dt"><liferay-ui:message key="removed-date" /></dt>

							<dd class="sidebar-dd">
								<%= dateTimeFormat.format(trashEntry.getCreateDate()) %>
							</dd>
							<dt class="sidebar-dt"><liferay-ui:message key="removed-by" /></dt>

							<dd class="sidebar-dd">
								<%= HtmlUtil.escape(trashEntry.getUserName()) %>
							</dd>
						</dl>
					</clay:tabs-panel>
				</clay:tabs>
			</c:when>
			<c:otherwise>
				<div class="sidebar-header">
					<clay:content-row
						cssClass="sidebar-section"
					>
						<clay:content-col
							expand="<%= true %>"
						>
							<div class="component-title"><liferay-ui:message arguments="<%= trashEntries.size() %>" key="x-items-are-selected" /></div>
						</clay:content-col>

						<clay:content-col>
							<ul class="autofit-padded-no-gutters autofit-row">
								<li class="autofit-col">
								</li>
							</ul>
						</clay:content-col>
					</clay:content-row>
				</div>

				<clay:tabs
					tabsItems="<%= trashDisplayContext.getTabsItems() %>"
				>
					<clay:tabs-panel>
						<dl class="sidebar-dl sidebar-section">
							<dt class="sidebar-dt"><liferay-ui:message key="num-of-items" /></dt>

							<dd class="sidebar-dd">
								<%= trashEntries.size() %>
							</dd>
						</dl>
					</clay:tabs-panel>
				</clay:tabs>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<clay:content-row
				cssClass="sidebar-section"
			>
				<clay:content-col
					expand="<%= true %>"
				>
					<span class="component-title"><liferay-ui:message key="home" /></span>
				</clay:content-col>
			</clay:content-row>
		</div>

		<clay:tabs
			tabsItems="<%= trashDisplayContext.getTabsItems() %>"
		>
			<clay:tabs-panel>
				<dl class="sidebar-dl sidebar-section">
					<dt class="sidebar-dt"><liferay-ui:message key="num-of-items" /></dt>

					<dd class="sidebar-dd">
						<%= TrashEntryLocalServiceUtil.getEntriesCount(themeDisplay.getScopeGroupId()) %>
					</dd>
				</dl>
			</clay:tabs-panel>
		</clay:tabs>
	</c:otherwise>
</c:choose>
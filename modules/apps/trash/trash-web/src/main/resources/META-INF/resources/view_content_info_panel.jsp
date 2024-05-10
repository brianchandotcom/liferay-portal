<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long classPK = trashDisplayContext.getClassPK();

TrashRenderer trashRenderer = trashDisplayContext.getTrashRenderer();

TrashHandler trashHandler = trashDisplayContext.getTrashHandler();
%>

<c:if test="<%= trashRenderer != null %>">
	<div class="sidebar-header">
		<clay:content-row
			cssClass="sidebar-section"
		>
			<clay:content-col
				expand="<%= true %>"
			>
				<div class="component-title"><%= HtmlUtil.escape(trashRenderer.getTitle(locale)) %></div>
			</clay:content-col>

			<clay:content-col>
				<ul class="autofit-padded-no-gutters autofit-row">
					<li class="autofit-col">

						<%
						TrashContainerActionDropdownItemsProvider trashContainerActionDropdownItemsProvider = new TrashContainerActionDropdownItemsProvider(liferayPortletRequest, liferayPortletResponse, trashDisplayContext);
						%>

						<clay:dropdown-actions
							additionalProps='<%=
								HashMapBuilder.<String, Object>put(
									"portletNamespace", liferayPortletResponse.getNamespace()
								).build()
							%>'
							aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
							dropdownItems="<%= trashContainerActionDropdownItemsProvider.getActionDropdownItems() %>"
							propsTransformer="{EntriesPropsTransformer} from trash-web"
						/>
					</li>
				</ul>
			</clay:content-col>
		</clay:content-row>
	</div>

	<div class="sheet-body">
		<clay:tabs
			tabsItems="<%= trashDisplayContext.getTabsItems() %>"
		>
			<clay:tabs-panel>
				<dl class="sidebar-dl sidebar-section">
					<dt class="sidebar-dt"><liferay-ui:message key="num-of-items" /></dt>

					<dd class="sidebar-dd">
						<%= trashHandler.getTrashModelsCount(classPK) %>
					</dd>
				</dl>
			</clay:tabs-panel>
		</clay:tabs>
	</div>
</c:if>
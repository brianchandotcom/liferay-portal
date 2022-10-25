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
				<h4 class="component-title"><%= HtmlUtil.escape(trashRenderer.getTitle(locale)) %></h4>
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
							propsTransformer="js/EntriesPropsTransformer"
						/>
					</li>
				</ul>
			</clay:content-col>
		</clay:content-row>
	</div>

	<clay:navigation-bar
		navigationItems="<%= trashDisplayContext.getInfoPanelNavigationItems() %>"
	/>

	<div class="sidebar-body">
		<dl class="sidebar-dl sidebar-section">
			<dt class="sidebar-dt"><liferay-ui:message key="num-of-items" /></dt>

			<dd class="sidebar-dd">
				<%= trashHandler.getTrashModelsCount(classPK) %>
			</dd>
		</dl>
	</div>
</c:if>
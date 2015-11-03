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

<%@ include file="/management_bar/init.jsp" %>

<div class="management-bar-container" id="<%= namespace %>managementBarContainerId">
	<div class="management-bar management-bar-default">
		<div class="container-fluid-1280">
			<div class="management-bar-header">
				<c:if test="<%= includeCheckBox %>">
					<label class="checkbox">
						<aui:input cssClass="select-all-checkboxes" inline="<%= true %>" label="" name="<%= RowChecker.ALL_ROW_IDS %>" title="select-all" type="checkbox" />
					</label>
				</c:if>
			</div>

			<c:if test="<%= Validator.isNotNull(buttons) %>">
				<div class="management-bar-header-right">
					<ul class="management-bar-nav nav">
						<%= buttons %>
					</ul>
				</div>
			</c:if>

			<c:if test="<%= Validator.isNotNull(filters) %>">
				<div class="collapse management-bar-collapse">
					<ul class="management-bar-nav nav">
						<%= filters %>
					</ul>
				</div>
			</c:if>
		</div>
	</div>

	<c:if test="<%= Validator.isNotNull(actionButtons) || includeCheckBox %>">
		<div class="management-bar management-bar-default management-bar-no-collapse" id="<%= namespace %>actionButtons">
			<div class="container-fluid-1280">
				<div class="management-bar-header">
					<c:if test="<%= includeCheckBox %>">
						<label class="checkbox">
							<aui:input cssClass="select-all-checkboxes" inline="<%= true %>" label="" name="actionsCheckBox" title="select-all" type="checkbox" />
						</label>
					</c:if>
				</div>

				<div class="management-bar-header-right">
					<c:if test="<%= Validator.isNotNull(actionButtons) %>">
						<ul class="management-bar-nav nav">
							<%= actionButtons %>
						</ul>
					</c:if>
				</div>

				<div class="collapse management-bar-collapse">
					<ul class="management-bar-nav nav">
						<li>
							<span class="management-bar-text">
								<span class="selected-items-count"></span> <liferay-ui:message key="items-selected" />
							</span>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</c:if>
</div>

<c:if test="<%= Validator.isNotNull(actionButtons) || includeCheckBox %>">
	<aui:script use="liferay-management-bar">
		var managementBar = new Liferay.ManagementBar(
			{
				checkBoxContainer: '#<%= checkBoxContainerId %>',
				namespace: '<%= namespace %>',
				searchContainerId: '<%= namespace + searchContainerId %>',
				secondaryBar: '#actionButtons'
			}
		);

		var clearManagementBarHandles = function(event) {
			if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
				managementBar.destroy();

				Liferay.detach('destroyPortlet', clearManagementBarHandles);
			}
		};

		Liferay.on('destroyPortlet', clearManagementBarHandles);
	</aui:script>
</c:if>
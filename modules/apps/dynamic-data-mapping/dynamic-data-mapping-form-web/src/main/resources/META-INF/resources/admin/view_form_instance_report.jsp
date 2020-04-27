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

<%@ include file="/admin/init.jsp" %>

<%
DDMFormViewFormInstanceRecordsDisplayContext ddmFormViewFormInstanceRecordsDisplayContext = ddmFormAdminDisplayContext.getFormViewRecordsDisplayContext();

int totalItems = ddmFormViewFormInstanceRecordsDisplayContext.getTotalItems();
%>

<div class="ddm-form-report hide">
	<div class="ddm-form-report-header">
		<div class="container-fluid container-fluid-max-xl">
			<div class="align-items-center autofit-row">
				<span class="ddm-form-report-header-title text-truncate">
					<liferay-ui:message arguments="<%= totalItems %>" key="x-entries" />
				</span>
			</div>

			<div class="align-items-center autofit-row">
				<span class="ddm-form-report-header-subtitle text-truncate">
					<c:choose>
						<c:when test="<%= totalItems > 0 %>">
							<%= ddmFormViewFormInstanceRecordsDisplayContext.getReportLastModifiedDate() %>
						</c:when>
						<c:otherwise>
							<liferay-ui:message key="there-are-no-entries" />
						</c:otherwise>
					</c:choose>
				</span>
			</div>
		</div>
	</div>

	<clay:navigation-bar
		elementClasses="ddm-form-report-tabs"
		navigationItems='<%=
			new JSPNavigationItemList(pageContext) {
				{
					add(
						navigationItem -> {
							navigationItem.setActive(true);
							navigationItem.setHref(StringPool.BLANK);
							navigationItem.setLabel(LanguageUtil.get(request, "entries"));
						});
				}
			}
		%>'
	/>

	<hr class="m-0" />

	<div id="<portlet:namespace />entriesTabContent">
		<liferay-util:include page="/admin/form_instance_records_search_container.jsp" servletContext="<%= application %>" />
	</div>
</div>

<aui:script require="metal-dom/src/dom as dom">
	dom.delegate(
		document.querySelector('.ddm-form-report-tabs'),
		'click',
		'li',
		function (event) {
			var navItem = dom.closest(event.delegateTarget, '.nav-item');
			var navItemIndex = Number(navItem.dataset.navItemIndex);
			var navLink = navItem.querySelector('.nav-link');

			document
				.querySelector('.ddm-form-report-tabs li > a.active')
				.classList.remove('active');
			navLink.classList.add('active');

			var entriesTabContent = document.querySelector(
				'#<portlet:namespace />entriesTabContent'
			);

			if (navItemIndex === 0) {
				entriesTabContent.classList.remove('hide');
			}
			else {
				entriesTabContent.classList.add('hide');
			}
		}
	);
</aui:script>
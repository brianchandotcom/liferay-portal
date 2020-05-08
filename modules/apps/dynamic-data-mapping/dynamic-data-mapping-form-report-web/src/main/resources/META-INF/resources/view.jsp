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
int totalItems = ddmFormReportDisplayContext.getTotalItems();
%>

<div class="ddm-form-report hide">
	<div class="ddm-form-report-header">
		<clay:container>
			<div class="align-items-center autofit-row">
				<span class="ddm-form-report-header-title text-truncate">
					<liferay-ui:message arguments="<%= totalItems %>" key="x-entries" />
				</span>
			</div>

			<div class="align-items-center autofit-row">
				<span class="ddm-form-report-header-subtitle text-truncate">
					<c:choose>
						<c:when test="<%= totalItems > 0 %>">
							<%= ddmFormReportDisplayContext.getLastModifiedDate() %>
						</c:when>
						<c:otherwise>
							<liferay-ui:message key="there-are-no-entries" />
						</c:otherwise>
					</c:choose>
				</span>
			</div>
		</clay:container>
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
							navigationItem.setLabel(LanguageUtil.get(request, "summary"));
						}
					);
				}
			}
		%>'
	/>

	<hr class="m-0" />

	<div id="<portlet:namespace />summaryTabContent">
		<liferay-util:include page="/view_summary.jsp" servletContext="<%= application %>" />
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

			var summaryTabContent = document.querySelector(
				'#<portlet:namespace />summaryTabContent'
			);

			if (navItemIndex === 0) {
				summaryTabContent.classList.remove('hide');
			}
			else {
				summaryTabContent.classList.add('hide');
			}
		}
	);
</aui:script>
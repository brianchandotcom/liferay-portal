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

<div class="hide portlet-ddm-form-report" id="container-portlet-ddm-form-report">
	<div class="portlet-ddm-form-report__header">
		<clay:container-fluid>
			<clay:content-row
				cssClass="align-items-center"
			>
				<h2 class="portlet-ddm-form-report__title text-truncate">
					<c:choose>
						<c:when test="<%= totalItems == 1 %>">
							<liferay-ui:message arguments="<%= totalItems %>" key="x-entry" />
						</c:when>
						<c:otherwise>
							<liferay-ui:message arguments="<%= totalItems %>" key="x-entries" />
						</c:otherwise>
					</c:choose>
				</h2>
			</clay:content-row>

			<clay:content-row
				cssClass="align-items-center"
			>
				<span class="portlet-ddm-form-report__subtitle text-truncate">
					<c:choose>
						<c:when test="<%= totalItems > 0 %>">
							<%= ddmFormReportDisplayContext.getLastModifiedDate() %>
						</c:when>
						<c:otherwise>
							<liferay-ui:message key="there-are-no-entries" />
						</c:otherwise>
					</c:choose>
				</span>
			</clay:content-row>
		</clay:container-fluid>
	</div>

	<clay:navigation-bar
		cssClass="portlet-ddm-form-report__tabs"
		navigationItems='<%=
			new JSPNavigationItemList(pageContext) {
				{
					add(
						navigationItem -> {
							navigationItem.setActive(true);
							navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "summary"));
						});
				}
			}
		%>'
	/>

	<hr class="m-0" />

	<div class="container-fluid container-fluid-max-xl" id="<portlet:namespace />summaryTabContent">
		<react:component
			module="js/index"
			props='<%=
				HashMapBuilder.<String, Object>put(
					"data", ddmFormInstanceReportData
				).put(
					"fields", ddmFormReportDisplayContext.getFieldsJSONArray()
				).put(
					"formReportRecordsFieldValuesURL", ddmFormReportDisplayContext.getFormReportRecordsFieldValuesURL()
				).put(
					"portletNamespace", PortalUtil.getPortletNamespace(DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_REPORT)
				).build()
			%>'
		/>
	</div>
</div>

<aui:script require="frontend-js-web/liferay/delegate/delegate.es as delegateModule">
	var delegate = delegateModule.default;

	delegate(
		document.querySelector('.portlet-ddm-form-report__tabs'),
		'click',
		'li',
		(event) => {
			var navItem = event.delegateTarget.closest('.nav-item');
			var navItemIndex = Number(navItem.dataset.navItemIndex);
			var navLink = navItem.querySelector('.nav-link');

			document
				.querySelector('.portlet-ddm-form-report__tabs li > .active')
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
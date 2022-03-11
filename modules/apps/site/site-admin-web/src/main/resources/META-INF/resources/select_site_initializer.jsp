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
SelectSiteInitializerDisplayContext selectSiteInitializerDisplayContext = new SelectSiteInitializerDisplayContext(request, renderRequest, renderResponse);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(selectSiteInitializerDisplayContext.getBackURL());

renderResponse.setTitle(LanguageUtil.get(request, "select-site-template"));
%>

<aui:form cssClass="container-fluid container-fluid-max-xl" name="fm">
	<liferay-ui:search-container
		searchContainer="<%= selectSiteInitializerDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.site.admin.web.internal.util.SiteInitializerItem"
			keyProperty="key"
			modelVar="siteInitializerItem"
		>
			<liferay-ui:search-container-column-text>
				<button class="add-site-action-button align-items-stretch btn btn-unstyled form-check-card mb-4 w-100" type="button">
					<clay:vertical-card
						verticalCard="<%= new SelectSiteInitializerVerticalCard(siteInitializerItem, renderRequest, renderResponse) %>"
					/>
				</button>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>

	<portlet:actionURL name="/site_admin/add_group" var="addSiteURL">
		<portlet:param name="mvcPath" value="/select_layout_set_prototype_entry.jsp" />
		<portlet:param name="parentGroupId" value="<%= String.valueOf(selectSiteInitializerDisplayContext.getParentGroupId()) %>" />
	</portlet:actionURL>

	<aui:script require="frontend-js-web/liferay/delegate/delegate.es as delegateModule,frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es as openSimpleInputModal">
		var delegate = delegateModule.default;

		var addSiteActionOptionQueryClickHandler = delegate(
			document.body,
			'click',
			'.add-site-action-button',
			(event) => {
				var data = event.delegateTarget.querySelector('.add-site-action-card')
					.dataset;

				Liferay.Util.openModal({
					iframeBodyCssClass: '',
					disableAutoClose: true,
					height: '60vh',
					id: '<portlet:namespace />addSiteDialog',
					size: 'md',
					title: '<liferay-ui:message key="add-site" />',
					url: data.addSiteUrl,
				});
			}
		);

		function handleDestroyPortlet() {
			addSiteActionOptionQueryClickHandler.dispose();

			Liferay.detach('destroyPortlet', handleDestroyPortlet);
		}

		Liferay.on('destroyPortlet', handleDestroyPortlet);
	</aui:script>
</aui:form>
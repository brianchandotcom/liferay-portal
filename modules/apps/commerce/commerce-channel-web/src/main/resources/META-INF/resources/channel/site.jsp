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
SiteCommerceChannelTypeDisplayContext siteCommerceChannelTypeDisplayContext = (SiteCommerceChannelTypeDisplayContext)request.getAttribute("site.jsp-portletDisplayContext");

Group site = siteCommerceChannelTypeDisplayContext.getChannelSite();

List<Group> siteAsList = new ArrayList<>();

if (site != null) {
	siteAsList = Arrays.asList(site);
}

CommerceChannel commerceChannel = siteCommerceChannelTypeDisplayContext.getCommerceChannel();
long commerceChannelId = siteCommerceChannelTypeDisplayContext.getCommerceChannelId();

boolean isViewOnly = false;

if (commerceChannel != null) {
	isViewOnly = !siteCommerceChannelTypeDisplayContext.hasPermission(commerceChannel.getCommerceChannelId(), ActionKeys.UPDATE);
}
%>

<liferay-util:buffer
	var="removeCommerceChannelSiteIcon"
>
	<liferay-ui:icon
		icon="times"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<portlet:actionURL name="editCommerceChannel" var="editCommerceChannelActionURL" />

<aui:form action="<%= editCommerceChannelActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="selectSite" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceChannelId" type="hidden" value="<%= commerceChannelId %>" />
	<aui:input name="siteGroupId" type="hidden" value="<%= (commerceChannel == null) ? 0 : commerceChannel.getSiteGroupId() %>" />

	<div class="row">
		<div class="col-12">
			<commerce-ui:panel
				bodyClasses="flex-fill"
				title='<%= LanguageUtil.get(request, "details") %>'
			>
				<commerce-ui:info-box
					title='<%= LanguageUtil.get(request, "type") %>'
				>
					<div class="item mb-4 pl-4">
						<liferay-ui:message key="site" />
					</div>
				</commerce-ui:info-box>

				<liferay-ui:search-container
					curParam="commerceChannelSiteCur"
					headerNames="null,null"
					id="CommerceChannelSitesSearchContainer"
					iteratorURL="<%= currentURLObj %>"
					total="<%= siteAsList.size() %>"
				>
					<liferay-ui:search-container-results
						results="<%= siteAsList %>"
					/>

					<liferay-ui:search-container-row
						className="com.liferay.portal.kernel.model.Group"
						keyProperty="groupId"
						modelVar="group"
					>
						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand"
							value="<%= HtmlUtil.escape(group.getName(locale)) %>"
						/>

						<c:if test="<%= !isViewOnly %>">
							<liferay-ui:search-container-column-text>
								<a class="float-right modify-link" data-rowId="<%= group.getGroupId() %>" href="javascript:;"><%= removeCommerceChannelSiteIcon %></a>
							</liferay-ui:search-container-column-text>
						</c:if>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						markupView="lexicon"
					/>
				</liferay-ui:search-container>

				<c:if test="<%= !isViewOnly %>">
					<aui:button cssClass="mb-4" name="selectSite" value='<%= LanguageUtil.format(locale, "select-x", "site") %>' />
				</c:if>
			</commerce-ui:panel>
		</div>
	</div>
</aui:form>

<aui:script use="aui-base,liferay-item-selector-dialog">
	var selectSiteButton = window.document.querySelector(
		'#<portlet:namespace />selectSite'
	);

	if (selectSiteButton) {
		selectSiteButton.addEventListener('click', function (event) {
			event.preventDefault();

			Liferay.Util.selectEntity({
				dialog: {
					constrain: true,
					modal: true,
				},
				eventName: 'sitesSelectItem',
				title: '<liferay-ui:message arguments="site" key="select-x" />',
				uri:
					'<%= siteCommerceChannelTypeDisplayContext.getItemSelectorUrl() %>',
			});
		});
	}
</aui:script>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />CommerceChannelSitesSearchContainer'
	);

	var searchContainerContentBox = searchContainer.get('contentBox');

	searchContainerContentBox.delegate(
		'click',
		function (event) {
			var link = event.currentTarget;

			var rowId = link.attr('data-rowId');

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));

			A.one('#<portlet:namespace />siteGroupId').val(0);
		},
		'.modify-link'
	);

	var sitesSelectItemHandle = Liferay.on('sitesSelectItem', function (event) {
		var item = event.data;

		if (item) {
			var searchContainer = Liferay.SearchContainer.get(
				'<portlet:namespace />CommerceChannelSitesSearchContainer'
			);

			var link = A.one('[data-rowid=' + searchContainer.getData() + ']');

			if (link !== null) {
				var tr = link.ancestor('tr');

				searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));
			}

			if (!searchContainer.getData().includes(item.id)) {
				var rowColumns = [];

				rowColumns.push(item.name);
				rowColumns.push(
					'<a class="float-right modify-link" data-rowId="' +
						item.id +
						'" href="javascript:;"><%= UnicodeFormatter.toString(removeCommerceChannelSiteIcon) %></a>'
				);

				A.one('#<portlet:namespace />siteGroupId').val(item.id);

				searchContainer.addRow(rowColumns, item.id);

				searchContainer.updateDataStore();
			}
		}
	});

	Liferay.on('beforeNavigate', function (event) {
		sitesSelectItemHandle.detach();
	});
</aui:script>
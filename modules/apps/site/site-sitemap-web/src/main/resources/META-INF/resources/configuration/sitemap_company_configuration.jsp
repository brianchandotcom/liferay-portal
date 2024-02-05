<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SitemapCompanyConfigurationDisplayContext sitemapCompanyConfigurationDisplayContext = (SitemapCompanyConfigurationDisplayContext)request.getAttribute(SitemapCompanyConfigurationDisplayContext.class.getName());
%>

<clay:content-row
	cssClass="c-mb-3"
>
	<clay:content-col>
		<span>
			<liferay-ui:message key="the-sitemap-protocol-notifies-search-engines-of-the-structure-of-the-website" />
		</span>
		<span>
			<clay:link
				href="http://www.sitemaps.org"
				label='<%= LanguageUtil.format(request, "for-more-information,-visit-x", "www.sitemaps.org") %>'
				target="_blank"
			/>
		</span>
	</clay:content-col>
</clay:content-row>

<liferay-util:buffer
	var="removeButtonSites"
>
	<clay:button
		aria-label='TOKEN_ARIA_LABEL'
		borderless="<%= true %>"
		cssClass="lfr-portal-tooltip remove-button"
		data-rowId="TOKEN_DATA_ROW_ID"
		displayType="secondary"
		icon="times-circle"
		monospaced="<%= true %>"
		small="<%= true %>"
		title="TOKEN_TITLE"
		type="button"
	/>
</liferay-util:buffer>

<liferay-util:buffer
	var="sitesIcon"
>
	<div class="sticker sticker-secondary sticker-static">
		<clay:icon
			symbol="sites"
		/>
	</div>
</liferay-util:buffer>

<clay:sheet-section role="group" aria-labelledby='<%= liferayPortletResponse.getNamespace() + "sitesIncludedTitle" %>'>
	<clay:content-row
		containerElement="h3"
		cssClass="c-mb-3 sheet-subtitle"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<span class="heading-text text-secondary" id="<portlet:namespace />sitesIncludedTitle"><liferay-ui:message key="sites-included-in-the-xml-sitemap" /></span>
		</clay:content-col>

		<clay:content-col>
			<clay:button
				aria-label='<%= LanguageUtil.format(request, "select-x", "sites-included-in-the-xml-sitemap") %>'
				displayType="secondary"
				id='<%= liferayPortletResponse.getNamespace() + "selectSiteLink" %>'
				label="select"
				small="<%= true %>"
				title="select"
			/>
		</clay:content-col>
	</clay:content-row>

	<clay:content-row>
		<clay:content-col
			expand="<%= true %>"
		>
			<liferay-ui:search-container
				compactEmptyResultsMessage="<%= true %>"
				headerNames="null,site-name,null"
				id="groupsSearchContainer"
				searchContainer="<%= sitemapCompanyConfigurationDisplayContext.getSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.portal.kernel.model.Group"
					escapedModel="<%= true %>"
					keyProperty="groupId"
					modelVar="group"
					rowIdProperty="groupId"
				>
					<liferay-ui:search-container-column-icon
						icon="sites"
					/>

					<liferay-ui:search-container-column-text
						name="site-name"
						truncate="<%= true %>"
						value="<%= HtmlUtil.escape(group.getDescriptiveName()) %>"
					/>

					<liferay-ui:search-container-column-text>
						<c:if test="<%= !group.isGuest() %>">
							<clay:button
								aria-label='<%= LanguageUtil.format(request, "remove-x", HtmlUtil.escape(group.getDescriptiveName())) %>'
								borderless="<%= true %>"
								cssClass="lfr-portal-tooltip remove-button"
								data-rowId="<%= group.getGroupId() %>"
								displayType="secondary"
								icon="times-circle"
								monospaced="<%= true %>"
								small="<%= true %>"
								title='<%= LanguageUtil.format(request, "remove-x", HtmlUtil.escape(group.getDescriptiveName())) %>'
								type="button"
							/>
						</c:if>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
					paginate="<%= false %>"
				/>
			</liferay-ui:search-container>
		</clay:content-col>
	</clay:content-row>
</clay:sheet-section>

<clay:sheet-section role="group" aria-labelledby='<%= liferayPortletResponse.getNamespace() + "pagesTitle" %>'>
	<clay:content-row
		containerElement="h3"
		cssClass="c-mb-3 sheet-subtitle"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<span class="heading-text text-secondary" id="<portlet:namespace />pagesTitle"><liferay-ui:message key="pages" /></span>
		</clay:content-col>
	</clay:content-row>

	<clay:content-row
		cssClass="c-mt-2"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<clay:checkbox
				checked="<%= sitemapCompanyConfigurationDisplayContext.includePages() %>"
				id='<%= liferayPortletResponse.getNamespace() + "includePages" %>'
				label='<%= LanguageUtil.get(request, "include-page-urls-in-the-xml-sitemap") %>'
				name='<%= liferayPortletResponse.getNamespace() + "includePages" %>'
			/>

			<p class="c-mb-0 c-mt-2 small text-secondary"><liferay-ui:message key="when-this-configuration-is-enabled,-search-engines-will-be-notified-that-page-URLs-are-available-for-crawling" /></p>
		</clay:content-col>
	</clay:content-row>
</clay:sheet-section>

<clay:sheet-section role="group" aria-labelledby='<%= liferayPortletResponse.getNamespace() + "webContentTitle" %>'>
	<clay:content-row
		containerElement="h3"
		cssClass="c-mb-3 sheet-subtitle"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<span class="heading-text text-secondary" id="<portlet:namespace />webContentTitle"><liferay-ui:message key="web-content" /></span>
		</clay:content-col>
	</clay:content-row>

	<clay:content-row
		cssClass="c-mt-2"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<clay:checkbox
				checked="<%= sitemapCompanyConfigurationDisplayContext.includeWebContent() %>"
				id='<%= liferayPortletResponse.getNamespace() + "includeWebContent" %>'
				label='<%= LanguageUtil.get(request, "include-web-content-urls-in-the-xml-sitemap") %>'
				name='<%= liferayPortletResponse.getNamespace() + "includeWebContent" %>'
			/>

			<p class="c-mb-0 c-mt-2 small text-secondary"><liferay-ui:message key="when-this-configuration-is-enabled,-search-engines-will-be-notified-that-web-content-URLs-are-available-for-crawling" /></p>
		</clay:content-col>
	</clay:content-row>
</clay:sheet-section>

<clay:sheet-section role="group" aria-labelledby='<%= liferayPortletResponse.getNamespace() + "categoriesTitle" %>'>
	<clay:content-row
		containerElement="h3"
		cssClass="c-mb-3 sheet-subtitle"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<span class="heading-text text-secondary" id="<portlet:namespace />categoriesTitle"><liferay-ui:message key="categories" /></span>
		</clay:content-col>
	</clay:content-row>

	<clay:content-row
		cssClass="c-mt-2"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<clay:checkbox
				checked="<%= sitemapCompanyConfigurationDisplayContext.includeCategories() %>"
				id='<%= liferayPortletResponse.getNamespace() + "includeCategories" %>'
				label='<%= LanguageUtil.get(request, "include-category-urls-in-the-xml-sitemap") %>'
				name='<%= liferayPortletResponse.getNamespace() + "includeCategories" %>'
			/>

			<p class="c-mb-0 c-mt-2 small text-secondary"><liferay-ui:message key="when-this-configuration-is-enabled,-search-engines-will-be-notified-that-category-URLs-are-available-for-crawling" /></p>
		</clay:content-col>
	</clay:content-row>
</clay:sheet-section>

<aui:script use="liferay-search-container">
	var groupIds = [];

	const groupIdsInput = document.getElementById(
		'<portlet:namespace />groupsSearchContainerPrimaryKeys'
	);

	var groupIds = groupIdsInput.value.split(',');

	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />groupsSearchContainer'
	);

	var searchContainerContentBox = searchContainer.get('contentBox');

	const selectSiteButton = document.getElementById(
		'<portlet:namespace />selectSiteLink'
	);

	const handleOnSelect = selectSiteButton.addEventListener('click', (event) => {
		var searchContainerData = searchContainer.getData();

		if (!searchContainerData.length) {
			searchContainerData = [];
		}
		else {
			searchContainerData = searchContainerData.split(',');
		}

		Liferay.Util.openSelectionModal({
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const entityId = selectedItem.groupid;
					const entityName = selectedItem.groupdescriptivename;
					const label = Liferay.Util.sub(
						'<liferay-ui:message key="remove-x" />',
						entityName
					);

					const rowColumns = [];

					let sitesIcon = '<%= UnicodeFormatter.toString(sitesIcon) %>';

					let removeButton =
						'<%= UnicodeFormatter.toString(removeButtonSites) %>';

					removeButton = removeButton
						.replace('TOKEN_ARIA_LABEL', label)
						.replace('TOKEN_DATA_ROW_ID', entityId)
						.replace('TOKEN_TITLE', label);

					rowColumns.push(sitesIcon);
					rowColumns.push(entityName);
					rowColumns.push(removeButton);

					searchContainer.addRow(rowColumns, entityId);

					searchContainer.updateDataStore();

					groupIds.push(entityId);

					groupIdsInput.value = groupIds.join(',');
				}
			},
			selectEventName:
				'<%= sitemapCompanyConfigurationDisplayContext.getEventName() %>',
			selectedData: [searchContainerData],
			title: '<liferay-ui:message arguments="site" key="select-x" />',
			url:
				'<%= sitemapCompanyConfigurationDisplayContext.getGroupSelectorURL() %>',
		});
	});

	var handleOnRemoveButton = searchContainerContentBox.delegate(
		'click',
		(event) => {
			var link = event.currentTarget;

			var rowId = link.attr('data-rowId');
			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, rowId);

			var searchContainerData = searchContainer.getData();

			if (!searchContainerData.length) {
				groupIds = [];
			}
			else {
				groupIds = searchContainerData.split(',');
			}

			groupIdsInput.value = groupIds.join(',');
		},
		'.remove-button'
	);

	var handleEnableRemoveSite = Liferay.on(
		'<portlet:namespace />enableRemovedSites',
		(event) => {
			event.selectors.each((item, index, collection) => {
				var groupId = item.attr('data-entityid');

				if (groupIds.indexOf(groupId) != -1) {
					Liferay.Util.toggleDisabled(item, false);
				}
			});
		}
	);

	var onDestroyPortlet = function (event) {
		if (event.portletId === '<%= portletDisplay.getId() %>') {
			removeEventListener('click', handleOnSelect);
			Liferay.detach(handleOnRemoveButton);
			Liferay.detach(handleEnableRemoveSite);

			Liferay.detach('destroyPortlet', onDestroyPortlet);
		}
	};

	Liferay.on('destroyPortlet', onDestroyPortlet);
</aui:script>
/* eslint-disable no-undef */
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

const accountKey = window.location.search.replace('?kor_id=', '');
const cardStructure = fragmentElement.querySelector('#card-structure');
const containerId = configuration.containerId;
const elementName = `.dynamic-web-content-${containerId}`;
const fragmentContainer = fragmentElement.querySelector('.cp-tip-container');
const headlessBaseURL = `${window.location.origin}/o/headless-delivery/v1.0`;
const siteGroupId = Liferay.ThemeDisplay.getSiteGroupId();
function setDynamicWebContent(htmlBody, customData = {}) {
	const keys = Object.keys(customData);
	const sanitizeHTMLRegex = /<[^>]*>?/gm;
	const sanitizeEmptyKeysRegex = /{{[^\s]*}}/g;
	let html = htmlBody.replace('[accountKey]', accountKey);
	keys.forEach((key) => {
		html = html.replace(
			`{{${key}}}`,
			customData[key].replace(sanitizeHTMLRegex, '')
		);
	});
	html = html.replace(sanitizeEmptyKeysRegex, '');
	const htmlElement = (elementName, html) => {
		return `<div class="card-container bg-white card-body cp-link-body mb-3 rounded-lg ${elementName}">${html}</div>`;
	};
	fragmentContainer.innerHTML += htmlElement(elementName, html);
}
async function fetchHeadless(url, resolveAsJson = true) {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(`${headlessBaseURL}${url}`, {
		headers: {
			'Cache-Control': 'max-age=30, stale-while-revalidate=30',
			'x-csrf-token': Liferay.authToken,
		},
	});
	if (resolveAsJson) {
		return response.json();
	}

	return response;
}
function fetchWebContent(structuredContentId, contentTemplateId, customData) {
	fetchHeadless(
		`/structured-contents/${structuredContentId}/rendered-content/${contentTemplateId}`,
		false
	)
		.then((response) => response.text())
		.then((response) => setDynamicWebContent(response, customData));
}
function CPFragmentInteractiveListener(templateId, structuredContents) {
	const subscriptionGroupsItems = JSON.parse(
		sessionStorage.getItem('cp-tip-container-primary')
	);
	function getStructuredContentIdByName(groupName) {
		return structuredContents.find(
			({friendlyUrlPath, key}) =>
				friendlyUrlPath === groupName ||
				key === groupName?.toUpperCase()
		)?.id;
	}
	if (
		typeof subscriptionGroupsItems === 'object' &&
		subscriptionGroupsItems.every((item) =>
			getStructuredContentIdByName(item)
		)
	) {
		subscriptionGroupsItems.forEach((item) =>
			fetchWebContent(getStructuredContentIdByName(item), templateId)
		);
	}
	else {
		console.warn(`Structure ${subscriptionGroupsItems} not found`);
	}
}
async function workflow() {
	const structuredContentFolders = await fetchHeadless(
		`/sites/${siteGroupId}/structured-content-folders`
	);
	const {id: cpFolderId} =
		structuredContentFolders.items.find(({name}) => name === 'actions') ||
		{};
	if (!cpFolderId) {
		return console.warn('CP Actions Folder not found');
	}
	const structuredContents = await fetchHeadless(
		`/structured-content-folders/${cpFolderId}/structured-contents`
	);
	const contentTemplates = await fetchHeadless(
		`/sites/${siteGroupId}/content-templates`
	);
	const contentTemplate = contentTemplates.items.find(
		(template) => template.name === 'Action Card'
	);
	CPFragmentInteractiveListener(
		contentTemplate?.id,
		structuredContents.items
	);
}
workflow();
fragmentElement.addEventListener('click', (event) => {
	const hideLinkId = fragmentElement.querySelector('#hide-link');
	let currentButton = event.target;
	if (currentButton.tagName === 'U' || currentButton.tagName === 'svg') {
		currentButton = currentButton.parentElement;
	}
	if (currentButton.tagName === 'use') {
		currentButton = currentButton.parentElement.parentElement;
	}
	if (currentButton.id === 'hide-link') {
		const iconLink = hideLinkId.innerHTML;
		fragmentContainer.classList.toggle('hide');
		cardStructure.classList.toggle('position-absolute');
		hideLinkId.innerHTML = iconLink.includes('#hr')
			? iconLink.replace('#hr', '#order-arrow-left')
			: iconLink.replace('#order-arrow-left', '#hr');
		hideLinkId.children[1].innerHTML = iconLink.includes('#hr')
			? 'Show'
			: 'Hide';
	}
});

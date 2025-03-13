/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {contentSecurityPolicyPagesTest} from '../../fixtures/contentSecurityPolicyPagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import {performLoginViaApi} from '../../utils/performLogin';
import getFragmentDefinition from '../layout-content-page-editor-web/utils/getFragmentDefinition';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';

export const test = mergeTests(
	apiHelpersTest,
	contentSecurityPolicyPagesTest,
	featureFlagsTest({
		'LPS-134060': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

test.afterEach(
	'Reset CSP configuration',
	async ({contentSecurityPolicyPage, page}) => {
		await page.goto('/');

		if (await page.getByRole('button', {name: 'Sign In'}).isVisible()) {
			await performLoginViaApi(page, 'test');
		}

		await contentSecurityPolicyPage.goto();

		await contentSecurityPolicyPage.resetCSPConfiguration();
	}
);

test("CSP connect-src allows connections to 'self'", async ({
	apiHelpers,
	contentSecurityPolicyPage,
	page,
	pageEditorPage,
	site,
}) => {
	await contentSecurityPolicyPage.gotoAndConfigurePolicy(
		`connect-src 'self'`
	);

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getFragmentDefinition({
				id: getRandomString(),
				key: 'BASIC_COMPONENT-html',
			}),
		]),
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	const portalURL = liferayConfig.environment.baseUrl;

	await pageEditorPage.editHTMLEditable({
		editableId: 'element-html',
		fragmentId: await pageEditorPage.getFragmentId('HTML'),
		value: `<a  ping="${portalURL}" href="${portalURL}" target="_blank">
		 			Test CSP
					<script>
						const response = fetch("${portalURL}");

						const xmlHttpRequest = new XMLHttpRequest();
						xmlHttpRequest.open("GET", "${portalURL}");
						xmlHttpRequest.send();

						const webSocket = new WebSocket("${portalURL}");

						const eventSource = new EventSource("${portalURL}");

						navigator.sendBeacon("${portalURL}", {
						/* … */
						});
					</script>
				</a>`,
	});

	await pageEditorPage.publishPage();

	const cspErrors = [];

	page.on('console', (msg) => {
		if (
			msg.type() === 'error' &&
			msg
				.text()
				.includes('Content Security Policy directive: "connect-src')
		) {
			cspErrors.push({text: msg.text(), type: msg.type()});
		}
	});

	page.on('requestfailed', (request) => {
		if (
			request.url().includes('localhost') &&
			request.failure().errorText.includes('csp')
		) {
			cspErrors.push({
				failure: request.failure(),
				url: request.url(),
			});
		}
	});

	await page.goto(`/web/${site.name}/${layout.friendlyUrlPath}`);

	await page.getByRole('link', {name: 'Test CSP'}).click({
		button: 'middle',
	});

	expect(cspErrors.length).toBeLessThanOrEqual(0);
});

test('CSP connect-src allows connections to specific domain', async ({
	apiHelpers,
	contentSecurityPolicyPage,
	page,
	pageEditorPage,
	site,
}) => {
	await contentSecurityPolicyPage.gotoAndConfigurePolicy(
		`connect-src 'self' http://www.able.com:8080/ wss://www.able.com:8080/;`
	);

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getFragmentDefinition({
				id: getRandomString(),
				key: 'BASIC_COMPONENT-html',
			}),
		]),
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	await pageEditorPage.editHTMLEditable({
		editableId: 'element-html',
		fragmentId: await pageEditorPage.getFragmentId('HTML'),
		value: `<a  ping="http://www.able.com:8080" href="http://localhost:8080" target="_blank">
		 			Test CSP
					<script>
						const response = fetch("http://www.able.com:8080");

						const xmlHttpRequest = new XMLHttpRequest();
						xmlHttpRequest.open("GET", "http://www.able.com:8080");
						xmlHttpRequest.send();

						const webSocket = new WebSocket("wss://www.able.com:8080/");

						const eventSource = new EventSource("http://www.able.com:8080");

						navigator.sendBeacon("http://www.able.com:8080", {
						/* … */
						});
					</script>
				</a>`,
	});

	await pageEditorPage.publishPage();

	const cspErrors = [];

	page.on('console', (msg) => {
		if (
			msg.type() === 'error' &&
			msg
				.text()
				.includes('Content Security Policy directive: "connect-src')
		) {
			cspErrors.push({text: msg.text(), type: msg.type()});
		}
	});

	page.on('requestfailed', (request) => {
		if (
			request.url().includes('www.able.com') &&
			request.failure().errorText.includes('csp')
		) {
			cspErrors.push({
				failure: request.failure(),
				url: request.url(),
			});
		}
	});

	await page.goto(`/web/${site.name}/${layout.friendlyUrlPath}`);

	await page.getByRole('link', {name: 'Test CSP'}).click({
		button: 'middle',
	});

	expect(cspErrors.length).toBeLessThanOrEqual(0);
});

test('CSP connect-src blocks connections', async ({
	apiHelpers,
	contentSecurityPolicyPage,
	page,
	pageEditorPage,
	site,
}) => {
	await contentSecurityPolicyPage.gotoAndConfigurePolicy(
		`connect-src 'self';`
	);

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getFragmentDefinition({
				id: getRandomString(),
				key: 'BASIC_COMPONENT-html',
			}),
		]),
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	await pageEditorPage.editHTMLEditable({
		editableId: 'element-html',
		fragmentId: await pageEditorPage.getFragmentId('HTML'),
		value: `<a  ping="http://www.able.com:8080" href="http://localhost:8080" target="_blank">
		 			Test CSP
					<script>
						const response = fetch("http://www.able.com:8080");

						const xmlHttpRequest = new XMLHttpRequest();
						xmlHttpRequest.open("GET", "http://www.able.com:8080");
						xmlHttpRequest.send();

						const webSocket = new WebSocket("wss://www.able.com:8080/");

						const eventSource = new EventSource("http://www.able.com:8080");

						navigator.sendBeacon("http://www.able.com:8080", {
						/* … */
						});
					</script>
				</a>`,
	});

	await pageEditorPage.publishPage();

	const cspErrors = [];

	page.on('console', (msg) => {
		if (
			msg.type() === 'error' &&
			msg
				.text()
				.includes('Content Security Policy directive: "connect-src')
		) {
			cspErrors.push({text: msg.text(), type: msg.type()});
		}
	});

	page.on('requestfailed', (request) => {
		if (
			request.url().includes('www.able.com') &&
			request.failure().errorText.includes('csp')
		) {
			cspErrors.push({
				failure: request.failure(),
				url: request.url(),
			});
		}
	});

	await page.goto(`/web/${site.name}/${layout.friendlyUrlPath}`);

	await page.getByRole('link', {name: 'Test CSP'}).click({
		button: 'middle',
	});

	expect(cspErrors.length).toBeGreaterThanOrEqual(9);
});

test("CSP img-src allow images from 'self'", async ({
	apiHelpers,
	contentSecurityPolicyPage,
	page,
	pageEditorPage,
	site,
}) => {
	await contentSecurityPolicyPage.gotoAndConfigurePolicy("img-src 'self';");

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getFragmentDefinition({
				id: getRandomString(),
				key: 'BASIC_COMPONENT-html',
			}),
		]),
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	const portalURL = liferayConfig.environment.baseUrl;

	await pageEditorPage.editHTMLEditable({
		editableId: 'element-html',
		fragmentId: await pageEditorPage.getFragmentId('HTML'),
		value: `<img src="${portalURL}/html/icons/default.png" alt="example picture" />`,
	});

	await pageEditorPage.publishPage();

	const errors = [];

	page.on('console', (message) => {
		if (message.type() === 'error') {
			const text = message.text();
			if (
				text.includes(
					`Refused to load the image '${portalURL}/html/icons/default.png'`
				)
			) {
				errors.push(text);
			}
		}
	});

	await page.goto(`/web/${site.name}/${layout.friendlyUrlPath}`);

	expect(errors).toEqual([]);
});

test('CSP img-src allows images from specific domains', async ({
	apiHelpers,
	contentSecurityPolicyPage,
	page,
	pageEditorPage,
	site,
}) => {
	await contentSecurityPolicyPage.gotoAndConfigurePolicy(
		"img-src 'self' http://www.able.com:8080;"
	);

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getFragmentDefinition({
				id: getRandomString(),
				key: 'BASIC_COMPONENT-html',
			}),
		]),
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	await pageEditorPage.editHTMLEditable({
		editableId: 'element-html',
		fragmentId: await pageEditorPage.getFragmentId('HTML'),
		value: `<img src="http://www.able.com:8080/html/icons/default.png" alt="example picture" />`,
	});

	await pageEditorPage.publishPage();

	const errors = [];

	page.on('console', (message) => {
		if (message.type() === 'error') {
			const text = message.text();
			if (
				text.includes(
					"Refused to load the image 'http://www.able.com:8080/html/icons/default.png'"
				)
			) {
				errors.push(text);
			}
		}
	});

	await page.goto(`/web/${site.name}/${layout.friendlyUrlPath}`);

	expect(errors).toEqual([]);
});

test('CSP img-src blocks images', async ({
	apiHelpers,
	contentSecurityPolicyPage,
	page,
	pageEditorPage,
	site,
}) => {
	await contentSecurityPolicyPage.gotoAndConfigurePolicy("img-src 'self';");

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getFragmentDefinition({
				id: getRandomString(),
				key: 'BASIC_COMPONENT-html',
			}),
		]),
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	await pageEditorPage.editHTMLEditable({
		editableId: 'element-html',
		fragmentId: await pageEditorPage.getFragmentId('HTML'),
		value: `<img src="http://www.able.com:8080/html/icons/default.png" alt="example picture" />`,
	});

	await pageEditorPage.publishPage();

	const errors = [];

	page.on('console', (message) => {
		if (message.type() === 'error') {
			const text = message.text();
			if (
				text.includes('http://www.able.com:8080/html/icons/default.png')
			) {
				errors.push(text);
			}
		}
	});

	await page.goto(`/web/${site.name}/${layout.friendlyUrlPath}`);

	expect(errors.length).toEqual(1);
});

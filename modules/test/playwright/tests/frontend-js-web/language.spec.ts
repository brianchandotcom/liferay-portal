/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, Response, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';

export const test = mergeTests(apiHelpersTest, loginTest());

test(`labels are downloaded in a separate .js file`, async ({page}) => {
	const response = await getLabelsResponse(page, 'frontend-js-web');

	expect(response.status()).toBe(200);

	const body = await response.body();
	const js = body.toString('utf-8');

	expect(js).toContain('const labels = {');
	expect(js).toContain('Liferay.Language._cache = {');
	expect(js).toContain('export default labels;');
});

test(`labels .js file is cacheable forever`, async ({page}) => {
	const response = await getLabelsResponse(page, 'frontend-js-web');

	expect(response.status()).toBe(200);

	const headers = await response.allHeaders();

	expect(headers['cache-control']).toContain('immutable');
	expect(headers['cache-control']).toContain('max-age=315360000');
	expect(headers['cache-control']).toContain('public');
});

test(`obsolete labels .js file is NOT cacheable forever`, async ({page}) => {
	const url = `/o/js/language/INVALID_HASH/en_US/frontend-js-web/all.js`;

	const response = await page.goto(url);

	expect(response.status()).toBe(200);

	const headers = await response.allHeaders();

	expect(headers['cache-control']).toContain('must-revalidate');
	expect(headers['cache-control']).toContain('no-cache');
	expect(headers['cache-control']).toContain('no-store');
	expect(headers['cache-control']).toContain('private');
});

test(`different languages return different labels`, async ({page}) => {
	const englishResponse = await getLabelsResponse(page, 'frontend-js-web', {
		languageId: 'en_US',
	});

	expect(englishResponse.status()).toBe(200);

	const englishBody = await englishResponse.body();
	const englishJs = englishBody.toString('utf-8');

	expect(englishJs).toContain("'ok':'OK'");

	await page.goto('/');

	const spanishResponse = await getLabelsResponse(page, 'frontend-js-web', {
		languageId: 'es_ES',
	});

	expect(spanishResponse.status()).toBe(200);

	const spanishBody = await spanishResponse.body();
	const spanishJs = spanishBody.toString('utf-8');

	expect(spanishJs).toContain("'ok':'Aceptar'");

	expect(englishJs).not.toEqual(spanishJs);
});

test(`changing an override changes the hash`, async ({apiHelpers, page}) => {
	await apiHelpers.language.deleteMessage('this-field-is-required', 'en_US');

	const firstHash = await getLabelsHash(page);

	await apiHelpers.language.putMessage({
		key: 'this-field-is-required',
		languageId: 'en_US',
		value: 'This field WAS required',
	});

	const secondHash = await getLabelsHash(page);

	await apiHelpers.language.deleteMessage('this-field-is-required', 'en_US');

	const thirdHash = await getLabelsHash(page);

	expect(secondHash).not.toEqual(firstHash);
	expect(thirdHash).toEqual(firstHash);
});

async function getLabelsHash(page: Page): Promise<string> {
	await page.goto('/');

	const json = await page.locator('script[type=importmap]').innerText();
	const importmap = JSON.parse(json);
	const prefix = importmap.imports['@liferay/language/'];
	const parts = prefix.split('/');

	return parts[4];
}

async function getLabelsResponse(
	page: Page,
	webContextPath: string,
	options: {
		hash?: string;
		languageId?: string;
	} = {}
): Promise<Response> {
	const json = await page.locator('script[type=importmap]').innerText();
	const importmap = JSON.parse(json);

	let prefix = importmap.imports['@liferay/language/'];

	if (options.hash) {
		const parts = prefix.split('/');

		parts[4] = options.hash;

		prefix = parts.join('/');
	}

	if (!options.languageId) {
		options.languageId = 'en_US';
	}

	const url = `${prefix}${options.languageId}/${webContextPath}/all.js`;

	const response = await page.goto(url);

	return response;
}

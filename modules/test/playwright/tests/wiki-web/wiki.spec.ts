/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {wikiPagesTest} from '../../fixtures/wikiPagesTest';

export const test = mergeTests(isolatedSiteTest, loginTest(), wikiPagesTest);

test('LPD-26435 Icon menu should close when another icon menu is open', async ({
	page,
	wikiPage,
}) => {
	await wikiPage.goto();

	await wikiPage.createNewWikiNode('Wiki Node Title');

	await page.getByLabel('Order').waitFor();

	const wikiNodeMenu = await page.locator(
		'[id="_com_liferay_wiki_web_portlet_WikiAdminPortlet_wikiNodes_1_menu"]'
	);

	await test.step('Check menu gets closed', async () => {
		await wikiNodeMenu.click();

		const menuOne = await page.locator(
			'[aria-labelledby="_com_liferay_wiki_web_portlet_WikiAdminPortlet_wikiNodes_1_menu"]'
		);

		await expect(menuOne).toBeVisible();

		await page
			.locator(
				'[id="_com_liferay_wiki_web_portlet_WikiAdminPortlet_wikiNodes_2_menu"]'
			)
			.click();

		const menuTwo = await page.locator(
			'[aria-labelledby="_com_liferay_wiki_web_portlet_WikiAdminPortlet_wikiNodes_2_menu"]'
		);

		await expect(menuTwo).toBeVisible();

		await expect(menuOne).toBeHidden();
	});

	await test.step('Wiki node is deleted', async () => {
		await wikiNodeMenu.click();

		await page.getByRole('link', {name: 'Delete'}).click();
	});
});

test('LPD-28898 Image added via rich text editor on Wiki tool losing reference', async ({
	page,
	wikiPage,
}) => {
	await wikiPage.goto();

	await wikiPage.goToWikiNode('Main');

	await wikiPage.goToEditFirstWikiPage();

	const image =
		'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOAAAADgCAMAAAAt85rTAAAAeFBMVEX///8LY84AXM+frr4AYM0AWcwAXMwAUspRgta9zO2NquIAYc0AXs35+ffL0tqotsS5zOkRadH///sAV8u0xefv8/sueNOyye2HpN5vldjA0efu8fR5ntnn7PJjktiowuVfjNfR3ezX4OqOqtpIhdcob9CNseMYbtMc8dVDAAADbUlEQVR4nO2da3OqMBBAQwv4CGoFi4+qtbW2//8fFmE6t9mAo3cwpMw5H3e2jqcwCYlLVikAAAAAAAAAAAAAAAAAAAAAAAAAAACAP8hkOnt0xGw6ca73vAyyB2dkyfLZrd9TnoWBQ3SWP7n1S1zqnUlcGj679zsburtLl5l7vyDIlq78JonuQjAMXI2l004uYHEJp44EZw/dCD7MHAk+diX4iCCCCCKIIII+CuokNqnWUlpEq0c8K/naB7/uBHVwWI1/sxqW4dyMjvfzwkXP12Z0lV9p2J1gctiJlLe8uIbZJjWjL9u4SN6+mNF0c+WzbXeC8djKGRaCAyu6iIIgGlnhQW8ERwgi6AIEFYImCCLYLggqBE0QRLBdbMGVTElLwVSGy9XEwkr2XjAcvomvvDkVi9joVRju9kmxHtyLxWP6GvkuGASnocnpHNRfIppXybkIf3m/oi+uoaSM6tqolez/nowjELyfoM4GJlF528UieqkyQ0cy2bpzO9w2zDdmRvp6HjjiTzGKvn80Guq5HHI31m5il/OgnPGqedD6TX3ROCGEQ+sjVrE/grc8yTQJWsljBO8FggpBBBWCdwRBhSCCCsE7gqDqm2CyFoUTaldWWbyL6OQov/M/wVwWarysZeV7hwve+XZksNiXX/pjYYaPl/bP9mbyYjv3Z8Eb6CQyqf75oYherGmSH2Ens+mE4H8L6rj2Fq3HSq52oqy73KNbtBhkxAixv+D3dRQjUrXXJgaZkU+DTLKvnSbqiY9ys+09832aaJjo66n/fdDviR5BBBFEsE0QVAiaIIhguyCoEDTxX7C+CKFB0CrGmwx8L0JoKCOpJ/wQu4npZ3xeBvtcRtJUCNRgKJPLK+V1IZAjEHQueFM5ZX1FpteC1xbEVttntTW1fgveUtKsT/J92DdrpvFO8JaidHseVB7Ng02C1l82v1bg95MMgj8g+BsE7waCCAoQRLBdEERQ0DNBazXxZwVrjjwq1oNx7ZFHYS7eklW7gz9VFg1Yh1atLx1aNRTnXh0Cj7YNmwxvOXYsrE32W7BlEEQQQQQRRNAHwd4fJN77o+AngdNmGj/oxFljlL63Y+h/Q43et0Qpm9o4baoROm5qc25LlLhsSxS4bkuket9YCgAAAAAAAAAAAAAAAAAAAAAAAAAAAKAFvgGY6WrR7U77yAAAAABJRU5ErkJggg==';

	await wikiPage.addSourceContentToWikiPage(`{{${image}|alternate text}}`);

	const ckeditorIframeLocator = page.frameLocator(
		'iframe[title="Editor, _com_liferay_wiki_web_portlet_WikiAdminPortlet_contentEditor"]'
	);

	await wikiPage.assertAddedImage(
		ckeditorIframeLocator.getByAltText('alternate text'),
		image
	);

	await wikiPage.publishPage();

	await wikiPage.goto();

	await wikiPage.goToWikiNode('Main');

	await wikiPage.goToFirstWikiPage();

	await wikiPage.assertAddedImage(page.getByAltText('alternate text'), image);
});

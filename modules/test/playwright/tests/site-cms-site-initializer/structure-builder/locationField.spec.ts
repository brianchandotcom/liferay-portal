/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {
	GEOCODED_ADDRESS,
	dragMapPin,
	interceptMapRequests,
} from '../../object-web/utils/location';
import {cmsPagesTest} from '../main/fixtures/cmsPagesTest';
import {structureBuilderPagesTest} from './fixtures/structureBuilderPagesTest';

const test = mergeTests(
	cmsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-70668': {enabled: true},
	}),
	loginTest(),
	structureBuilderPagesTest
);

test(
	'Location field keeps the picked location after the content is saved',
	{tag: '@LPD-70671'},
	async ({contentsPage, page, structureBuilderPage}) => {
		await interceptMapRequests(page);

		const structureLabel = `Structure${getRandomInt()}`;
		const contentTitle = getRandomString();

		// Create a structure with a Location field

		await structureBuilderPage.createStructureFromData({
			label: structureLabel,
			name: structureLabel,
			page: structureBuilderPage,
		});

		await structureBuilderPage.addField('Location');

		await structureBuilderPage.changeFieldSettings({label: 'Office'});

		await structureBuilderPage.publishStructure();

		// Create content and pick a location on the map

		await contentsPage.goto();

		await contentsPage.createContent(structureLabel);

		await contentsPage.fillData([{label: 'Title', value: contentTitle}]);

		const mapContainer = page.locator('[id$="-location-input-container"]');

		await dragMapPin(
			mapContainer.locator('.object-field__location-map'),
			page
		);

		const valueInput = page.locator('input[id$="-location-input-value"]');

		await expect(valueInput).toHaveValue(new RegExp(GEOCODED_ADDRESS));

		await contentsPage.saveContent();

		// Edit the content and check the saved location comes back

		await contentsPage.editContent(contentTitle);

		await expect(valueInput).toHaveValue(/"coordinates"/);
	}
);

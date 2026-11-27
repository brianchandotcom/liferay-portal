/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {DataApiHelpers} from '../../../helpers/ApiHelpers';
import {expandSection} from '../../../utils/expandSection';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {
	GEOCODED_ADDRESS,
	dragMapPin,
	interceptMapRequests,
} from '../../object-web/utils/location';
import getFormContainerDefinition from '../main/utils/getFormContainerDefinition';
import getFragmentDefinition from '../main/utils/getFragmentDefinition';
import getPageDefinition from '../main/utils/getPageDefinition';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-70668': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

async function addLocationFormPage({
	apiHelpers,
	site,
}: {
	apiHelpers: DataApiHelpers;
	site: Site;
}) {

	// Create an object with a required location field

	const objectDefinition =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			objectDefinitionExternalReferenceCode: `Office${getRandomInt()}`,
			objectFields: [
				{
					DBType: 'Clob',
					businessType: 'Location',
					externalReferenceCode: getRandomString(),
					label: {en_US: 'Office Location'},
					name: 'location',
					required: true,
				},
			],
			status: {code: 0},
		});

	apiHelpers.data.push({
		id: objectDefinition.id,
		type: 'objectDefinition',
	});

	// Create a page with a form container mapped to the object

	return apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getFormContainerDefinition({
				id: getRandomString(),
				objectDefinitionClassName: objectDefinition.className,
				pageElements: [
					getFragmentDefinition({
						fragmentConfig: {
							inputFieldId: 'ObjectField_location',
						},
						id: getRandomString(),
						key: 'INPUTS-location-input',
					}),
					getFragmentDefinition({
						id: getRandomString(),
						key: 'INPUTS-submit-button',
					}),
				],
			}),
		]),
		siteId: site.id,
		title: getRandomString(),
	});
}

test(
	'Can pick a location on the map of a required location input',
	{tag: '@LPD-70670'},
	async ({apiHelpers, page, site}) => {
		await interceptMapRequests(page);

		const layout = await addLocationFormPage({apiHelpers, site});

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

		// Submit without a location and check the required error

		await page.getByText('Submit', {exact: true}).click();

		await expect(
			page.locator('[id$="-location-input-error-message"]')
		).toHaveText('This field is required.');

		const formGroup = page.locator('[id$="-location-input-form-group"]');

		await expect(formGroup).toHaveClass(/has-error/);

		// Drag the pin and check the input holds the geocoded address

		await dragMapPin(
			page.locator(
				'[id$="-location-input-container"] .object-field__location-map'
			),
			page
		);

		await expect(
			page.locator('input[id$="-location-input-value"]')
		).toHaveValue(new RegExp(GEOCODED_ADDRESS));

		await expect(formGroup).not.toHaveClass(/has-error/);
	}
);

test(
	'Locks the map of the location input in the page editor',
	{tag: '@LPD-70670'},
	async ({apiHelpers, page, pageEditorPage, site}) => {
		await interceptMapRequests(page);

		const layout = await addLocationFormPage({apiHelpers, site});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// The fragment is offered in the fragments panel

		await pageEditorPage.goToSidebarTab('Components');

		await page.getByRole('tab', {exact: true, name: 'Fragments'}).click();

		await expandSection(
			page.getByRole('menuitem', {exact: true, name: 'Form Components'})
		);

		await expect(page.getByLabel('Add Location Selector')).toBeVisible();

		// The map is shown, but it cannot be used while editing the page

		const container = page.locator('[id$="-location-input-container"]');

		await expect(
			container.locator('.object-field__location-map')
		).toBeVisible();

		const content = container.locator('.location-input-content');

		await expect(content).toHaveClass(/location-input--inert/);
		await expect(content).toHaveClass(/location-input--locked/);
	}
);

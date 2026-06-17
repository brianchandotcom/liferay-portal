/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {changeTrackingPagesTest} from '../../../fixtures/changeTrackingPagesTest';
import getRandomString from '../../../utils/getRandomString';
import getBasicWebContentStructureId from '../../../utils/structured-content/getBasicWebContentStructureId';

const test = mergeTests(apiHelpersTest, changeTrackingPagesTest);

test(
	'View publication history for a web content edited on production after publish',
	{tag: '@LPD-23181'},
	async ({apiHelpers, changeTrackingPage, ctCollection, page}) => {

		// Add a web content in the publication

		await changeTrackingPage.workOnPublication(ctCollection);

		const site =
			await apiHelpers.headlessAdminUser.getSiteByFriendlyUrlPath(
				'guest'
			);

		const basicWebContentStructureId =
			await getBasicWebContentStructureId(apiHelpers);

		const title = getRandomString();

		const webContent =
			await apiHelpers.jsonWebServicesJournal.addWebContent({
				content: 'original',
				ddmStructureId: basicWebContentStructureId,
				groupId: site.id,
				titleMap: {en_US: title},
			});

		// Publish the publication and wait for the background task to finish

		await apiHelpers.headlessChangeTracking.publishCTCollection(
			ctCollection.body.id
		);

		await changeTrackingPage.assertStatus(
			'Published',
			ctCollection.body.name
		);

		// Edit the same web content directly on production to create v1.1

		await changeTrackingPage.workOnProduction();

		await apiHelpers.jsonWebServicesJournal.editWebContent(
			{content: 'edited-on-production'},
			site.id,
			webContent
		);

		// Open the published publication in the History tab and click the
		// web content entry

		await changeTrackingPage.goToPublicationHistory();

		await page.getByRole('link', {name: ctCollection.body.name}).click();

		await changeTrackingPage.reviewChange(title);

		// The publication's contribution (v1.0) and the current production
		// state (v1.1) must both be selectable. Before the fix the request
		// failed with NoSuchEntryException because the left model was queried
		// in the published publication's CT context instead of production.

		await expect(
			page.getByText(
				'Unable to display content due to an unexpected error'
			)
		).toBeHidden();

		await changeTrackingPage.selectTab('Data');

		await changeTrackingPage.selectRenderView('Production');

		await expect(
			page.locator('.publications-render-view-content')
		).toContainText('edited-on-production');

		await changeTrackingPage.selectRenderView(ctCollection.body.name);

		await expect(
			page.locator('.publications-render-view-content')
		).toContainText('original');
	}
);

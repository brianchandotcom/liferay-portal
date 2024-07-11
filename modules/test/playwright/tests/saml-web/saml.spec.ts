/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {samlAdminPagesTest} from '../../fixtures/samlAdminPagesTest';
import {virtualInstancesPagesTest} from '../../fixtures/virtualInstancesPagesTest';
import getRandomString from '../../utils/getRandomString';
import {connectSpAndIdp} from './utils/samlProviderConnectionUtil';
import {
	createIdentityProviderVirtualInstance,
	createServiceProviderVirtualInstance,
} from './utils/samlVirtualInstanceUtil';

export const test = mergeTests(
	loginTest(),
	samlAdminPagesTest,
	virtualInstancesPagesTest
);

test('Create, edit, and delete a new virtual instance', async ({
	editVirtualInstancePage,
	virtualInstancesPage,
}) => {
	const name = getRandomString();

	await virtualInstancesPage.addNewVirtualInstance(
		undefined,
		undefined,
		name,
		undefined
	);

	const newName = getRandomString();

	await editVirtualInstancePage.editVirtualInstance(
		false,
		name,
		newName + '.com',
		'100',
		newName
	);

	await expect(
		await virtualInstancesPage.page
			.getByRole('row')
			.getByText(name + ' ' + newName + ' ' + newName + '.com 0 100 No')
	).toBeVisible();

	await virtualInstancesPage.deleteVirtualInstance(name);
});

test('Create a new virtual instance, configure it for SAML IdP', async ({
	identityProviderConnectionsPage,
	page,
	samlAdminPage,
	serviceProviderConnectionsPage,
	virtualInstancesPage,
}) => {

	// Create new idp virtual instance

	const idpVirtualInstanceName = 'idp';

	await createIdentityProviderVirtualInstance(
	    idpVirtualInstanceName, idpVirtualInstanceName,
		{page, samlAdminPage, virtualInstancesPage});

	// Create new sp virtual instance

	const spVirtualInstanceName = 'sp';

	await createServiceProviderVirtualInstance(
	    spVirtualInstanceName, spVirtualInstanceName,
		{page, samlAdminPage, virtualInstancesPage});

	// Add a new connection for each provider, of the opposite provider

	await connectSpAndIdp(
		idpVirtualInstanceName,
		spVirtualInstanceName,
		undefined,
		undefined,
		page
	);

	// Next, attempt auth from SP, to IdP, then redirected back to SP


	//Lastly, delete both virtual instances
	await virtualInstancesPage.deleteVirtualInstance(idpVirtualInstanceName);

	await virtualInstancesPage.deleteVirtualInstance(spVirtualInstanceName);
});

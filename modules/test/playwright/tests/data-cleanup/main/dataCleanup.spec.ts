/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {globalMenuPagesTest} from '../../../fixtures/globalMenuPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {serverAdministrationPageTest} from '../../../fixtures/serverAdministrationPageTest';

const SERVLET_CONTEXT_NAMES = [
	'com.liferay.amazon.rankings.web',
	'com.liferay.chat.service',
	'com.liferay.document.library.file.rank.service',
];

export const test = mergeTests(
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	loginTest(),
	globalMenuPagesTest,
	serverAdministrationPageTest
);

test('execute all system cleanup actions', async ({globalMenuPage, page}) => {
	test.setTimeout(150000);

	// Go to Server Admin Page

	await globalMenuPage.goToControlPanel('Server Administration');

	// Execute System Cleanup Actions

	await executeCleanupActions(page, 'System Cleanup Actions');
});

test('execute all module cleanup actions', async ({
	globalMenuPage,
	page,
	serverAdministrationPage,
}) => {
	test.setTimeout(180000);

	// Go to Server Admin Page

	await globalMenuPage.goToControlPanel('Server Administration');

	// Add releases for Module Cleanup Actions

	const addReleasesScript = `
       import com.liferay.portal.kernel.service.ReleaseLocalServiceUtil;
       import com.liferay.portal.kernel.model.Release;

       def servletContextNames = ${JSON.stringify(SERVLET_CONTEXT_NAMES)}

       for (String servletContextName : servletContextNames) {
		   Release release = ReleaseLocalServiceUtil.fetchRelease(servletContextName);

		   if (release == null) {
			   ReleaseLocalServiceUtil.addRelease(servletContextName,"1.0.0");
			   }
		   }
	   `;

	try {
		await serverAdministrationPage.executeScript(addReleasesScript);

		// Reset Data Cleanup Registrator Component

		const resetDataCleanupRegistratorScript = `
			import com.liferay.portal.kernel.module.util.BundleUtil;
			import com.liferay.portal.kernel.module.util.SystemBundleUtil;
			import org.osgi.framework.FrameworkUtil;
			import org.osgi.service.component.runtime.ServiceComponentRuntime;
			import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;
			import org.osgi.util.promise.Promise;

			def bundle = FrameworkUtil.getBundle(ServiceComponentRuntime.class);

			def bundleContext = bundle.getBundleContext();

			def serviceReference = bundleContext.getServiceReference(
				ServiceComponentRuntime.class);

			def serviceComponentRuntime = bundleContext.getService(serviceReference);

			try {
				ComponentDescriptionDTO componentDescriptionDTO =
						serviceComponentRuntime.getComponentDescriptionDTO(
								BundleUtil.getBundle(
										SystemBundleUtil.getBundleContext(),
										"com.liferay.data.cleanup.impl"),
								"com.liferay.data.cleanup.internal.DataCleanupRegistrator");

				Promise<Void> promise = serviceComponentRuntime.disableComponent(
						componentDescriptionDTO);

				promise.getValue();

				promise = serviceComponentRuntime.enableComponent(
						componentDescriptionDTO);

				promise.getValue();
				}
				finally {
					if (serviceReference != null) {
						bundleContext.ungetService(serviceReference);
					}
				}
		`;

		await serverAdministrationPage.executeScript(
			resetDataCleanupRegistratorScript
		);

		await globalMenuPage.goToControlPanel('Server Administration');

		// Execute Module Cleanup Actions

		await executeCleanupActions(page, 'Module Cleanup Actions');
	}
	finally {

		// Remove module releases

		const deleteReleasesScript = `
				import com.liferay.portal.kernel.service.ReleaseLocalServiceUtil;
				import com.liferay.portal.kernel.model.Release;

				def servletContextNames = ${JSON.stringify(SERVLET_CONTEXT_NAMES)};

				for (String servletContextName : servletContextNames) {

					Release release = ReleaseLocalServiceUtil.fetchRelease(servletContextName);

					if (release != null) {
						ReleaseLocalServiceUtil.deleteRelease(release);
					}
				}
			`;
		await serverAdministrationPage.executeScript(deleteReleasesScript);
	}
});

async function executeCleanupActions(page, panelName: string) {

	// Find Cleanup Actions Panel

	const cleanupPanel = page
		.locator('.card, .panel', {has: page.getByText(panelName)})
		.last();

	const panelHeader = cleanupPanel.getByRole('button', {
		name: new RegExp(panelName, 'i'),
	});

	// Expand Cleanup Actions Menu

	if ((await panelHeader.getAttribute('aria-expanded')) === 'false') {
		await panelHeader.click();

		await expect(panelHeader).toHaveAttribute('aria-expanded', 'true');
	}

	// Collect all execute buttons in Cleanup Actions Menu

	const executeButtons = cleanupPanel.getByRole('button', {name: 'Execute'});

	// Execute all data cleanup actions for System Cleanup Section

	const successMessage = page.locator('.alert-success', {
		hasText: 'Your request completed successfully.',
	});

	if (panelName === 'System Cleanup Actions') {
		for (const button of await executeButtons.all()) {
			await button.click();

			await expect(successMessage).toBeVisible({timeout: 120000});
		}
	}

	// Execute Clean Up All Module Data data cleanup action for Module Section

	if (panelName === 'Module Cleanup Actions') {
		const cleanupAllModuleDataRow = cleanupPanel
			.locator('.list-group-item, tr, .row', {
				hasText: 'Clean Up All Module Data',
			})
			.first();

		const executeButton = cleanupAllModuleDataRow.getByRole('button', {
			name: 'Execute',
		});

		await executeButton.click();

		await expect(successMessage).toBeVisible({timeout: 120000});

		const disabledButtons = cleanupPanel.getByRole('button', {
			disabled: true,
			name: 'Execute',
		});

		await expect(disabledButtons).toHaveCount(SERVLET_CONTEXT_NAMES.length);
	}
}

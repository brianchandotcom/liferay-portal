/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {waitForInputLocalized} from '../../../../utils/waitFor';
import {waitForAlert} from '../../../../utils/waitForAlert';
import {ClientExtensionsPage} from './ClientExtensionsPage';

const EDIT_CLIENT_EXTENSION_BASE_URL =
	'/group/control_panel/manage?p_p_id=com_liferay_client_extension_web_internal_portlet_ClientExtensionAdminPortlet&_com_liferay_client_extension_web_internal_portlet_ClientExtensionAdminPortlet_mvcRenderCommandName=/client_extension_admin/edit_client_extension_entry&_com_liferay_client_extension_web_internal_portlet_ClientExtensionAdminPortlet_type=';

const PORTLET_ID =
	'_com_liferay_client_extension_web_internal_portlet_ClientExtensionAdminPortlet';

export enum WaitAction {
	ERROR,
	NONE,
	SUCCESS,
}

export class EditClientExtensionsPage {
	readonly clientExtensionsPage: ClientExtensionsPage;
	readonly clientExtensionType: string;
	readonly descriptionContentEditable: Locator;
	readonly descriptionCKEditor: Locator;
	readonly nameInput: Locator;
	readonly page: Page;
	readonly portletId: string;
	readonly publishButton: Locator;
	readonly sourceCodeURLInput: Locator;

	private readonly _cancelButton: Locator;
	private readonly _nameLanguageInput: Locator;

	constructor(page: Page, clientExtensionType: string) {
		this.clientExtensionsPage = new ClientExtensionsPage(page);
		this.clientExtensionType = clientExtensionType;
		this.nameInput = page.locator(`#${PORTLET_ID}_name`);
		this.page = page;
		this.portletId = PORTLET_ID;
		this.publishButton = page.getByRole('button', {
			name: 'Publish',
		});
		this.sourceCodeURLInput = page.locator(`#${PORTLET_ID}_sourceCodeURL`);

		this.descriptionCKEditor = page.locator(
			'#cke__com_liferay_client_extension_web_internal_portlet_ClientExtensionAdminPortlet_description'
		);

		const descriptionIframe = page.frameLocator(
			`#cke_${PORTLET_ID}_description iframe`
		);

		this.descriptionContentEditable =
			descriptionIframe.locator('.cke_editable');

		this._cancelButton = page.getByRole('button', {name: 'Cancel'});
		this._nameLanguageInput = page.locator(
			`#${PORTLET_ID}_${PORTLET_ID}_nameMenu`
		);
	}

	async cancel() {
		await this._cancelButton.click();
	}

	async changeNameLanguage(languageId: string) {
		await this._nameLanguageInput.click();

		await this.page
			.locator(
				`#${PORTLET_ID}_namePaletteContentBox a[data-languageid=${languageId}]`
			)
			.click();

		await this.page
			.locator(`#${PORTLET_ID}_namePaletteContentBox`)
			.waitFor({state: 'hidden'});
	}

	async goto() {
		await this.page.goto(
			`${EDIT_CLIENT_EXTENSION_BASE_URL}${this.clientExtensionType}`
		);

		await this.waitFor();
	}

	async publish(waitAction: WaitAction) {
		await this.publishButton.click();

		switch (waitAction) {
			case WaitAction.ERROR:
				await waitForAlert(
					this.page,
					'Error:Your request failed to complete.',
					{
						timeout: 5000,
						type: 'danger',
					}
				);
				break;

			case WaitAction.NONE:
				break;

			case WaitAction.SUCCESS:
				await waitForAlert(
					this.page,
					'Success:Your request completed successfully.',
					{
						timeout: 5000,
					}
				);
				break;

			default:
				throw new Error(`Unknown wait action: ${waitAction}`);
		}
	}

	async waitFor() {
		await this._cancelButton.waitFor({state: 'visible'});
		await waitForInputLocalized(this.page, `${PORTLET_ID}_name`);

		// TODO: wait for CK editor to be ready

	}
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import POM from '../../../../utils/POM';
import {waitForInputLocalized} from '../../../../utils/waitFor';
import {waitForAlert} from '../../../../utils/waitForAlert';
import {ClientExtensionsPage} from './ClientExtensionsPage';

const PORTLET_NAME =
	'com_liferay_client_extension_web_internal_portlet_ClientExtensionAdminPortlet';

const PORTLET_BASE_URL =
	'/group/control_panel/manage' +
	`?p_p_id=${PORTLET_NAME}` +
	`&_${PORTLET_NAME}_mvcRenderCommandName=/client_extension_admin/edit_client_extension_entry`;

export enum WaitAction {
	ERROR,
	NONE,
	SUCCESS,
}

export class EditClientExtensionsPage extends POM {
	readonly clientExtensionsPage: ClientExtensionsPage;
	readonly clientExtensionType: string;
	readonly descriptionContentEditable: Locator;
	readonly descriptionCKEditor: Locator;
	readonly nameHeader: Locator;
	readonly nameInput: Locator;
	readonly portletName = PORTLET_NAME;
	readonly publishButton: Locator;
	readonly sourceCodeURLInput: Locator;

	private readonly _cancelButton: Locator;
	private readonly _nameLanguageInput: Locator;

	constructor(page: Page, clientExtensionType: string) {
		super(
			page,
			`${PORTLET_BASE_URL}` +
				`&_${PORTLET_NAME}_type=${clientExtensionType}`
		);

		this.clientExtensionsPage = new ClientExtensionsPage(page);
		this.clientExtensionType = clientExtensionType;
		this.nameHeader = page.locator('h3');
		this.nameInput = page.locator(`#_${this.portletName}_name`);
		this.publishButton = page.getByRole('button', {
			name: 'Publish',
		});
		this.sourceCodeURLInput = page.locator(
			`#_${this.portletName}_sourceCodeURL`
		);

		this.descriptionCKEditor = page.locator(
			'#cke__com_liferay_client_extension_web_internal_portlet_ClientExtensionAdminPortlet_description'
		);

		const descriptionIframe = page.frameLocator(
			`#cke__${this.portletName}_description iframe`
		);

		this.descriptionContentEditable =
			descriptionIframe.locator('.cke_editable');

		this._cancelButton = page.getByRole('button', {name: 'Cancel'});
		this._nameLanguageInput = page.locator(
			`#_${this.portletName}__${this.portletName}_nameMenu`
		);
	}

	async cancel() {
		await this._cancelButton.click();
	}

	async changeNameLanguage(languageId: string) {
		await this._nameLanguageInput.click();

		await this.page
			.locator(
				`#_${this.portletName}_namePaletteContentBox a[data-languageid=${languageId}]`
			)
			.click();

		await this.page
			.locator(`#_${this.portletName}_namePaletteContentBox`)
			.waitFor({state: 'hidden'});
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

	override async waitFor() {
		await this._cancelButton.waitFor({state: 'visible'});
		await waitForInputLocalized(this.page, `_${this.portletName}_name`);

		// TODO: wait for CK editor to be ready

	}
}

import { Locator, Page } from "@playwright/test";

export class EditObjectDefinitionPage {
    readonly actionsTab: Locator;

    constructor(page: Page) {
        this.actionsTab = page.getByRole('link', { name: 'Actions' });
    }

    async openActionsTab() {
		await this.actionsTab.click();
	}
}
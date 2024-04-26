import { Locator, Page } from "@playwright/test";

export class ViewObjectActionsPage {
    readonly addObjectActionButton: Locator;

    constructor(page: Page) {
        this.addObjectActionButton = page.getByLabel('Add Object Action');
    }

    async openObjectActionSidePanel() {
		await this.addObjectActionButton.click();
	}
}
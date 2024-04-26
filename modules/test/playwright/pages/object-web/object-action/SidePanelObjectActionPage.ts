import { Locator, Page } from "@playwright/test";

export class SidePanelObjectActionPage {
    readonly actionBuilderTab: Locator;

    constructor(page: Page) {
        this.actionBuilderTab = page.frameLocator('iframe').getByRole('tab', { name: 'Action Builder' });
    }

    async openActionBuilderTab() {
		await this.actionBuilderTab.click();
	}
}
import { Locator, Page } from "@playwright/test";

export class ActionBuilderPage {
    readonly inputNotificationsCombo: Locator;
    readonly inputThenCombo: Locator;
    readonly optionNotification: Locator;

    constructor(page: Page) {
        this.inputNotificationsCombo = page.frameLocator('iframe').getByRole('combobox').getByText('Select an Option')
        this.inputThenCombo = page.frameLocator('iframe').getByRole('combobox').getByText('Choose an Action');
        this.optionNotification = page.frameLocator('iframe').getByRole('option', { name: 'Notification' });
    }

    async chooseNotificationOption() {
		await this.inputThenCombo.click();
        await this.optionNotification.click();
	}
    
    async clickInputNotificationsCombo() {
		await this.inputNotificationsCombo.click();
	}
}
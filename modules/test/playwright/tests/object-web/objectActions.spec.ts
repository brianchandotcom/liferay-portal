import { expect, mergeTests } from "@playwright/test";
import { apiHelpersTest } from "../../fixtures/apiHelpersTest";
import { loginTest } from "../../fixtures/loginTest";
import { objectPagesTest } from "../../fixtures/objectPagesTest";
import { getRandomInt } from "../../utils/getRandomInt";
import { editObjectDefinitionPagesTest } from "../../fixtures/editObjectDefinitionPagesTest";

export const test = mergeTests(apiHelpersTest, editObjectDefinitionPagesTest,loginTest(), objectPagesTest);

test.describe('manage object actions through object actions tab', () => {
	test('notification action section must display all persisted notifications', async ({
		actionBuilderPage,
        apiHelpers,
        editObjectDefinitionPage,
        page,
        sidePanelObjectActionPage,
        viewObjectActionsPage,
		viewObjectDefinitionsPage,
	}) => {
        const ids: number[] = [];
        const names: string[] = [];
        
        for(let index=1;index <= 21;index++){
            const notificationTemplate = await apiHelpers.notification.postRandomNotificationTemplate(
                "notification template test " + getRandomInt()
            )
            ids.push(notificationTemplate.id)
            names.push(notificationTemplate.name + " " + notificationTemplate.type)
        }
        
        const objectDefinition =
            await apiHelpers.objectAdmin.postRandomObjectDefinition(
                'default'
            );

        await viewObjectDefinitionsPage.goto()

        await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(objectDefinition.name);
        
        await editObjectDefinitionPage.openActionsTab();
            
        await viewObjectActionsPage.openObjectActionSidePanel();

        await sidePanelObjectActionPage.openActionBuilderTab();

        await actionBuilderPage.chooseNotificationOption();

        await actionBuilderPage.clickInputNotificationsCombo();

        for (let index = 0; index < names.length; index++) {
            await expect(
                page.frameLocator('iframe').getByRole('option', { name: names[index] })
            ).toBeVisible();
        }

        // Clean up

        await apiHelpers.objectAdmin.deleteObjectDefinition(objectDefinition.id);

        for (let index = 0; index < ids.length; index++) {
            await apiHelpers.notification.deleteNotificationTemplate(ids[index]);
        }
    });
});
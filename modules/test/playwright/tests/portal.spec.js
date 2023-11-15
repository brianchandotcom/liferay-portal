const { test, expect } = require('@playwright/test');

test('has title', async ({ page }) => {
  await page.goto(process.env.PORTAL_URL);
  await expect(page).toHaveTitle("Home - Liferay DXP");
  await expect(page.locator('#main-content img')).toBeVisible();
});
import { t as testController } from 'testcafe';

import config from '../../config';
import { retry } from '../helpers/utils';

/**
 * Navigate to a console page via its friendly URL.
 *
 * @param {string} friendlyUrl FriendURL for console page.
 */
async function navigateToConsolePage(friendlyUrl) {
  await retry(3, 3000, async () => {
    await testController.navigateTo(config.consoleUrl + friendlyUrl);
  });
}

/**
 * Navigate to a project page.
 *
 * @param {string} projectId Project id.
 * @param {string} page      Project page.
 */
async function navigateToProjectPage(projectId, page) {
  await navigateToConsolePage(`/projects/${projectId}/${page.toLowerCase()}`);
}

/**
 * Navigate to a service page.
 *
 * @param {string} projectId Project id.
 * @param {string} serviceId Service id.
 * @param {string} page      Service page.
 */
async function navigateToProjectServicePage(projectId, serviceId, page) {
  await navigateToConsolePage(
    `/projects/${projectId}/services/${serviceId}/${page}`
  );
}

module.exports = {
  navigateToConsolePage,
  navigateToProjectPage,
  navigateToProjectServicePage,
};

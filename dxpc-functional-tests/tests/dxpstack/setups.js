import fs from 'fs';

import config from '../../config';
import { waitForBackupService } from '../helpers/api/backup';
import { waitUntilReady } from '../helpers/api/service';
import * as git from '../helpers/git';

/**
 * Setup for dxpstack assorted tests.
 *
 * @param {string} projectId Project id of root environment.
 */
async function setupAssorted(projectId) {
  await checkStackReady(`${projectId}-prd`);
  await checkStackReady(`${projectId}-dev`);
}

/**
 * Setup for dxpstack backup tests.
 *
 * @param {string} projectId Project id of root environment.
 */
async function setupBackup(projectId) {
  await checkStackReady(`${projectId}-prd`);
  await checkStackReady(`${projectId}-dev`);
  await waitForBackupService(`${projectId}-prd`);
  await waitForBackupService(`${projectId}-dev`);
}

/**
 * Wait for all stack services have ready status.
 *
 * @param {string} projectId Project id.
 */
async function checkStackReady(projectId) {
  const services = ['search', 'database', 'liferay', 'backup', 'webserver'];

  for (const service of services) {
    await waitUntilReady(projectId, service, { timeoutSeconds: 1200 });
  }
}

/**
 * Sets up local copy of provisioned project from github; will clone, fetch, and
 * checkout the 'develop' branch.
 *
 * @param {string} projectId Project ID.
 */
function setupLocalRepo(projectId) {
  if (fs.existsSync(`${config.projectRoot}/temp/${projectId}`)) return;

  const releaseProjectUrl =
    'https://WeDeployQA1:' +
    process.env.GITHUB_TOKEN +
    '@github.com/dxpcloud-dev/' +
    projectId +
    '.git';

  git.cloneRepo(releaseProjectUrl);

  git.fetchBranch(projectId, 'develop');

  git.checkoutBranch(projectId, 'develop');

  git.setupUser(projectId, config.githubUserEmail, 'WeDeploy QA1');
}

module.exports = {
  checkStackReady,
  setupAssorted,
  setupBackup,
  setupLocalRepo,
};

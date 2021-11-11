import { ClientFunction, t as testController } from 'testcafe';

import basePage from '../pages/base-page';

const getCurrentUrl = ClientFunction(() => window.location.href);

/**
 * Checks if current url includes url string.
 *
 * @param {string} url String to be checked.
 */
async function assertCurrentUrl(url, { timeout = 10000 } = {}) {
  await basePage.waitForLoadingBar();

  await testController.expect(getCurrentUrl()).contains(url, { timeout });
  return;
}

/**
 * Generate random project id.
 *
 * @returns {string} Project Id.
 */
function randomProjectId() {
  return `qatc${Math.random().toString(36).substring(2)}`;
}

/**
 * Generate random user email.
 *
 * @returns {string} User email.
 */
function randomUserEmail() {
  return `${randomProjectId()}@test.com`;
}

/**
 * Retry an asynchronous function, pausing between each attempt.
 *
 * @param {number}   tries   Number of tries.
 * @param {number}   timeout Time in ms to wait between tries.
 * @param {function} retryFn Async function to retry.
 */
async function retry(tries, timeout, retryFn) {
  for (let i = 0; i < tries; i++) {
    try {
      await retryFn();
      return;
    } catch (error) {
      if (i === tries - 1) {
        throw error;
      }

      await sleep(timeout);
    }
  }
}

/**
 * Retry assertions, reloading page between each try.
 *
 * @param {number}   tries   Number of tries.
 * @param {function} retryFn Async function to retry.
 *     Function should contain at least one assert.
 */
async function retryWithRefresh(tries, retryFn) {
  const url = await getCurrentUrl();

  for (let i = 0; i < tries; i++) {
    try {
      await retryFn();
      return;
    } catch (error) {
      if (i === tries - 1) {
        throw error;
      }

      await testController.navigateTo(url);
    }
  }
}

/**
 * Stop and restore the execution after some time.
 *
 * @param   {number}  ms Time to sleep.
 * @returns {Promise}    Returns a Promise which will be resolved after the
 *     sleep timeout to pass.
 */
async function sleep(ms) {
  return new Promise((done) => setTimeout(done, ms));
}

/**
 * Stop and restore the execution after given time in minutes.
 *
 * @param   {number}  minutes Time to sleep.
 * @returns {Promise}         Returns a Promise which will be resolved after the
 *     sleep timeout to pass.
 */
async function sleepInMinutes(minutes) {
  let amount = minutes * 60000;
  return new Promise((done) => setTimeout(done, amount));
}

module.exports = {
  assertCurrentUrl,
  randomProjectId,
  randomUserEmail,
  retry,
  retryWithRefresh,
  sleep,
  sleepInMinutes,
};

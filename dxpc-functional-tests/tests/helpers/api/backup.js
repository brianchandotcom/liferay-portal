import axios from 'axios';
import FormData from 'form-data';
import Fs from 'fs';
import Path from 'path';

import config from '../../../config';
import { sleep } from '../utils';
import { formatApiError, getGoogleOauth } from './api-utils';

const baseUrl = function (projectId) {
  return `https://backup-${projectId}.${config.serviceDomain}`;
};

/**
 * Fetch backup service status.
 *
 * @param   {string} projectId Project Id.
 * @returns {number}           Status code of response,
 *     or 0 in case of error.
 */
async function fetchBackupServiceStatus(projectId) {
  let response;

  try {
    response = await axios({
      method: 'get',
      url: baseUrl(projectId),
      auth: {
        username: config.teamuser.email,
        password: config.teamuser.pw,
      },
    });
  } catch (error) {
    console.log(formatApiError(error));
    return 0;
  }

  return response.status;
}

/**
 * Download backup from GCP storage.
 *
 * @param {string} type Backup type [database || volume]
 */
async function downloadMockedBackup(type, extension = 'tgz') {
  let response;
  const path = Path.resolve(
    config.projectRoot,
    config.dependencies,
    `${type}.${extension}`
  );
  const writer = Fs.createWriteStream(path);
  const url = `https://storage.googleapis.com/storage/v1/b/liferaycloud-functional-backup-test/o/${type}.${extension}?alt=media`;
  const OAuth2Token = await getGoogleOauth([
    'https://www.googleapis.com/auth/devstorage.read_only',
  ]);

  try {
    response = await axios({
      method: 'get',
      url,
      headers: {
        Authorization: `Bearer ${OAuth2Token}`,
      },
      responseType: 'stream',
    });
    response.data.pipe(writer);
  } catch (error) {
    throw new Error(
      formatApiError(error, 'Error downloading backup from GCloud Storage')
    );
  }

  return new Promise((resolve, reject) => {
    writer.on('finish', resolve);
    writer.on('error', reject);
  });
}

/**
 * Wait up to 20 minutes for backup service endpoint to be available.
 *
 * @param {string} projectId Project id.
 */
async function waitForBackupService(projectId) {
  let tries = 0;
  let statusCode;

  for (;;) {
    if (tries === 240) {
      throw new Error('Timed out waiting for backup service to come up');
    }
    tries++;

    statusCode = await fetchBackupServiceStatus(projectId);

    if (statusCode === 200) {
      return;
    }

    await sleep(5000);
  }
}

/**
 * Upload a backup.
 *
 * @param {string} projectId   ProjectId, including env.
 * @param {string} dbFilePath  Path to db file.
 * @param {string} volFilePath Path to volume file.
 */
async function uploadBackup(projectId, dbFilePath, volFilePath) {
  const form = new FormData();

  form.append('database', Fs.createReadStream(dbFilePath));
  form.append('volume', Fs.createReadStream(volFilePath));

  try {
    await axios({
      method: 'post',
      url: `${baseUrl(projectId)}/backup/upload`,
      data: form,
      maxContentLength: Infinity,
      maxBodyLength: Infinity,
      headers: form.getHeaders(),
      auth: {
        username: config.teamuser.email,
        password: config.teamuser.pw,
      },
    });
  } catch (error) {
    throw new Error(formatApiError(error, 'Failed to upload backup'));
  }
}

module.exports = {
  waitForBackupService,
  downloadMockedBackup,
  uploadBackup,
};

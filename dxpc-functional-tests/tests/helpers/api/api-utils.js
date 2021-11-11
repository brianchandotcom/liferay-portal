import axios from 'axios';
import crypto from 'crypto';
import moment from 'moment';

import config from '../../../config';
import { sleep } from '../utils';

/**
 * Print API error message.
 *
 * @param {object} error Error object.
 */
export function formatApiError(error, message = '') {
  if (error.response) {
    message += `\nFailed with status code: ${error.response.status}\n`;
    message += `Message: ${error.response.statusText}\n`;
    if (error.response.data.errors) {
      for (let i = 0; i < error.response.data.errors.length; i++) {
        message += JSON.stringify(error.response.data.errors[i]) + '\n';
      }
    }
    if (error.response.data.message) {
      message += error.response.data.message + '\n';
    }
  } else {
    message += `\n${error.message}\n`;
  }

  message += moment().format('YYYY-MM-DD, HH:mm:ss Z');

  return message;
}

/**
 * Retry api call, waits 30 seconds between tries.
 *
 * @param {number}   tries   Number of tries.
 * @param {function} retryFn Async function to retry.
 */
export async function retryApi(tries, retryFn) {
  for (let i = 0; i < tries; i++) {
    try {
      await retryFn();
      return;
    } catch (error) {
      if (i === tries - 1) {
        throw error;
      }
      console.log(error.message);
      console.log('API request failed, retrying in 30 seconds...');
      await sleep(30000);
    }
  }
}

export async function getGoogleOauth(scopes) {
  const assertion = encodeGoogleOAuthJWT({
    email:
      'functional-tests-backup@liferaycloud-development.iam.gserviceaccount.com',
    scopes,
    key: new Buffer.from(config.googleServiceKey, 'base64').toString('utf8'),
  });

  try {
    const res = await axios({
      method: 'post',
      url: 'https://oauth2.googleapis.com/token',
      data: {
        grant_type: 'urn:ietf:params:oauth:grant-type:jwt-bearer',
        assertion,
      },
    });

    return res.data.access_token;
  } catch (error) {
    formatApiError(error);
    throw new Error(formatApiError(error));
  }
}

function encodeGoogleOAuthJWT(options) {
  const iat = Math.floor(new Date().getTime() / 1000);

  const exp = iat + Math.floor((options.expiration || 60 * 60 * 1000) / 1000);

  const claims = {
    iss: options.email,
    scope: options.scopes.join(' '),
    aud: 'https://oauth2.googleapis.com/token',
    exp: exp,
    iat: iat,
  };

  const JWT_header = new Buffer.from(
    JSON.stringify({ alg: 'RS256', typ: 'JWT' })
  ).toString('base64');
  const JWT_claimset = new Buffer.from(JSON.stringify(claims)).toString(
    'base64'
  );
  const unsignedJWT = [JWT_header, JWT_claimset].join('.');

  try {
    const JWT_signature = crypto
      .createSign('RSA-SHA256')
      .update(unsignedJWT)
      .sign(options.key, 'base64');
    const signedJWT = [unsignedJWT, JWT_signature].join('.');

    return signedJWT;
  } catch (e) {
    const signErr = new Error(
      'failed to sign JWT, the key is probably invalid'
    );
    signErr.inner = e;
    throw signErr;
  }
}

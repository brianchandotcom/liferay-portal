import axios from 'axios';

import config from '../../../config';
import { formatApiError } from './api-utils';

/**
 * Fetch builds with given build group id.
 *
 * @param   {string}   projectId              Project id.
 * @param   {object}   params                 Parameters to filter builds by.
 * @param   {string}   [params.buildGroupUid]
 * @param   {string}   [params.serviceId]
 * @param   {number}   [params.limit]
 * @param   {string}   [params.status]
 * @returns {object[]}                        One or more build objects.
 */
async function fetchBuilds(projectId, params) {
  let response;

  try {
    response = await axios({
      method: 'get',
      url: `${config.apiBaseUrl}/projects/${projectId}/builds`,
      params,
      auth: {
        username: config.teamuser.email,
        password: config.teamuser.pw,
      },
    });
  } catch (error) {
    throw new Error(formatApiError(error, 'Error fetching build'));
  }

  return response.data;
}

module.exports = {
  fetchBuilds,
};

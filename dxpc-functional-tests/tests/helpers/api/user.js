import axios from 'axios';

import config from '../../../config';
import { randomUserEmail } from '../utils';
import { formatApiError, retryApi } from './api-utils';

/**
 * Create test user.
 *
 * @returns {object} Returns the created test user.
 */
async function createTestUser(planId = 'premium') {
  const userEmail = randomUserEmail();
  const firstName = userEmail.split('@')[0];

  const userDetails = {
    firstName: firstName,
    lastName: 'Tester',
    email: userEmail,
    isConfirmed: true,
  };

  let user;

  await retryApi(3, async () => {
    user = await createUser(userDetails);
  });

  await retryApi(3, async () => {
    await updateUserPlan(user.id, planId);
  });

  return user;
}

/**
 * Create a generic user.
 *
 * @param   {Object}  user               User to be created.
 * @param   {string}  user.firstName     First name of the user.
 * @param   {string}  user.lastName      Last name of the user.
 * @param   {string}  user.email         Email address of the user.
 * @param   {boolean} [user.isConfirmed] Whether the created user is confirmed.
 * @returns {Object}                     User object.
 */
async function createUser({ firstName, lastName, email, isConfirmed }) {
  let data = {
    email: email,
    firstName: firstName,
    lastName: lastName,
    password: config.tester.pw,
  };

  if (isConfirmed) {
    data.confirmed = '';
  }

  let response, user;

  try {
    response = await axios({
      method: 'post',
      url: `${config.apiBaseUrl}/user/create`,
      data: data,
    });
    user = response.data;
  } catch (error) {
    await deleteUser(email);
    throw new Error(formatApiError(error, 'Failed to create user'));
  }

  return user;
}

/**
 * Delete a user by ID or email address.
 *
 * @param {string} userIdentifier User identifier; may be the user's email address or ID.
 */
async function deleteUser(userIdentifier) {
  let id = userIdentifier;

  if (userIdentifier.includes('@')) {
    const user = await fetchAdminUser(userIdentifier);

    id = user.id;
  }

  await deleteUserById(id);
}

/**
 * Delete a user using the user's ID.
 *
 * @param {string} userId User ID.
 */
async function deleteUserById(userId) {
  try {
    await axios({
      method: 'delete',
      url: `${config.apiBaseUrl}/admin/user/${userId}`,
      param: { force: true },
      auth: {
        username: config.teamuser.email,
        password: config.teamuser.pw,
      },
    });
  } catch (error) {
    console.log(
      formatApiError(error, `WARNING: Failed to delete user ${userId}`)
    );
  }
}

/**
 * Get Admin User.
 *
 * @param   {string} userEmail User email.
 * @returns {object}           User object.
 */
async function fetchAdminUser(userEmail) {
  let response, user;

  try {
    response = await axios.get(
      `${config.apiBaseUrl}/admin/users?email=${userEmail}`,
      {
        auth: {
          username: config.teamuser.email,
          password: config.teamuser.pw,
        },
      }
    );

    user = response.data;
  } catch (error) {
    throw new Error(formatApiError(error, `Failed to fetch user ${userEmail}`));
  }

  return user;
}

/**
 * Update tester user.
 *
 * @param {string} userId User ID.
 * @param {string} data   Data to be passed in.
 */
async function updateUser(userId, data) {
  try {
    await axios({
      method: 'patch',
      url: `${config.apiBaseUrl}/admin/users/${userId}`,
      data: data,
      auth: {
        username: config.teamuser.email,
        password: config.teamuser.pw,
      },
    });
  } catch (error) {
    throw new Error(formatApiError(error, `Failed to update user ${userId}`));
  }
}

/**
 * Update tester user plan.
 *
 * @param {string} userId User ID.
 * @param {string} planId Plan id to subscribe user to.
 */
async function updateUserPlan(userId, planId) {
  await updateUser(userId, { planId: planId });
}

/**
 * Check if user exists or not.
 *
 * @param   {string}  userEmail User email.
 * @returns {boolean}           True if user exists, else false.
 */
async function userExists(userEmail) {
  const user = await fetchAdminUser(userEmail);

  if (user) {
    return true;
  }

  return false;
}

module.exports = {
  createTestUser,
  createUser,
  deleteUser,
  fetchAdminUser,
  updateUser,
  updateUserPlan,
  userExists,
};

import axios from 'axios';
import { t as testController } from 'testcafe';

import config from '../../../config';
import { randomProjectId } from '../utils';
import { formatApiError, retryApi } from './api-utils';

/**
 * Create a project. If this is for a standard functional test, please use
 * CreateTestProject below.
 *
 * Note: If creating a project in fixture.before, pass in the ownerEmail
 * explicitly, as the `testController` object is only available in `test` context.
 *
 * @param   {string}  projectId             Project id.
 * @param   {object}  options               Options object.
 * @param   {Boolean} [options.environment] Environment flag Default is `false`
 * @param   {object}  [options.metadata]    Metadata flag. Default is setting
 *     type to `production`
 * @param   {string}  [options.ownerEmail]  Project owner's Email. Default is
 *     `testController.fixtureCtx.tester.email`
 * @param   {string}  [options.cluster]     The cluster where the project will
 *     be deployed.
 * @returns {Object}                        Project object.
 */
async function createProject(projectId, options = {}) {
  let response;
  let project;

  try {
    response = await axios({
      method: 'post',
      url: `${config.apiBaseUrl}/projects`,
      data: {
        projectId: projectId,
        cluster: options.cluster || config.projectRegion,
        environment: options.environment || false,
        metadata: options.metadata || { type: 'production' },
      },
      auth: {
        username: options.ownerEmail || testController.fixtureCtx.tester.email,
        password: config.tester.pw,
      },
    });
    project = response.data;
  } catch (error) {
    await deleteProject(projectId);
    throw new Error(
      formatApiError(error, `Failed to create project ${projectId}`)
    );
  }

  return project;
}

/**
 * Create a test project. Will attempt to create a project 3 times before failing.
 *
 * Note: Do not use this in fixture.before (testController will not resolve).
 * Use createProject method instead.
 *
 * @param   {string}  projectId             Project id. Default is to create a
 *     random projectId.
 * @param   {object}  options               Options object.
 * @param   {Boolean} [options.environment] Environment flag Default is `false`
 * @param   {object}  [options.metadata]    Metadata flag. Default is setting
 *     type to `production`
 * @param   {string}  [options.ownerEmail]  Project owner's Email. Default is
 *     `testController.fixtureCtx.tester.email`
 * @param   {string}  [options.cluster]     The cluster where the project will
 *     be deployed.
 * @returns {Object}                        Project object.
 */
async function createTestProject(projectId = randomProjectId(), options = {}) {
  let project;

  (testController.fixtureCtx.projectsToDelete ??= new Set()).add(projectId);

  await retryApi(3, async () => {
    project = await createProject(projectId, options);
  });
  return project;
}

/**
 * Delete a project.
 *
 * @param {string} projectId          Project id.
 * @param {Set}    [projectsToDelete] FixtureContext.projectsToDelete.
 */
async function deleteProject(projectId, projectsToDelete) {
  try {
    await axios({
      method: 'delete',
      url: `${config.apiBaseUrl}/projects/${projectId}`,
      auth: {
        username: config.teamuser.email,
        password: config.teamuser.pw,
      },
    });
  } catch (error) {
    if (error.response && error.response.status === 404) {
      return;
    }
    console.log(
      formatApiError(error, `WARNING: Failed to delete project ${projectId}`)
    );
  }

  if (projectsToDelete) {
    projectsToDelete.delete(projectId);
  } else {
    testController.fixtureCtx.projectsToDelete?.delete(projectId);
  }
}

/**
 * Fetch project by projectId.
 *
 * @param   {string} projectId ProjectId.
 * @returns {Object}           Project object.
 */
async function fetchAdminProject(projectId) {
  let response;

  try {
    response = await axios({
      method: 'get',
      url: `${config.apiBaseUrl}/admin/projects/${projectId}`,
      auth: {
        username: config.teamuser.email,
        password: config.teamuser.pw,
      },
    });
  } catch (error) {
    throw new Error(formatApiError(error, 'Failed to fetch project.'));
  }

  return response.data;
}

/**
 * Fetch projectUid.
 *
 * @param   {string} projectId ProjectId.
 * @returns {string}           Project Uid.
 */
async function fetchProjectUid(projectId) {
  const project = await fetchAdminProject(projectId);

  return project.id;
}

/**
 * Get project owner's email.
 *
 * @param   {string} projectId Project id.
 * @returns {string}           Owner's email.
 */
async function getProjectOwnerEmail(projectId) {
  const project = await fetchAdminProject(projectId);
  const ownerId = project.ownerId;
  let response;

  try {
    response = await axios({
      method: 'get',
      url: `${config.apiBaseUrl}/admin/users`,
      params: { id: ownerId },
      auth: {
        username: config.teamuser.email,
        password: config.teamuser.pw,
      },
    });
  } catch (error) {
    throw new Error(formatApiError(error, 'Failed to fetch user.'));
  }

  return response.data.email;
}

/**
 * Teardown any remaining projects during fixture.after.
 *
 * @param {object} ctx Fixture context object.
 */
async function teardownProjects(ctx) {
  const { projectsToDelete } = ctx;

  if (!projectsToDelete || projectsToDelete.size === 0) {
    return;
  }

  console.log(`Cleaning up ${projectsToDelete.size} projects`);

  for (const projectId of projectsToDelete) {
    await deleteProject(projectId, ctx.projectsToDelete);
  }
}

/**
 * Updates a project's metadata.
 *
 * @param {string} projectId Project id.
 * @param {object} metadata  Metadata to be updated.
 */
async function updateProjectMetadata(projectId, metadata) {
  let currentMetadata = await fetchAdminProject(projectId).metadata;
  let mergedMetadata = { ...currentMetadata, ...metadata };

  try {
    await axios({
      method: 'patch',
      url: `${config.apiBaseUrl}/projects/${projectId}`,
      data: {
        metadata: mergedMetadata,
      },
      auth: {
        username: config.teamuser.email,
        password: config.teamuser.pw,
      },
    });
  } catch (error) {
    throw new Error(formatApiError(error, 'Error updating project metadata'));
  }
}

module.exports = {
  createProject,
  createTestProject,
  deleteProject,
  fetchAdminProject,
  fetchProjectUid,
  getProjectOwnerEmail,
  teardownProjects,
  updateProjectMetadata,
};

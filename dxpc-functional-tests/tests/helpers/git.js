import fs from 'fs';
import path from 'path';

import config from '../../config';
import { execCmd } from './cmd';

const tempDir = path.join(config.projectRoot, 'temp');

/**
 * @param {string} project Git project, same as provisioned projectId.
 * @param {string} branch  Branch to checkout.
 */
export function checkoutBranch(project, branch) {
  execCmd({
    command: 'git',
    args: ['checkout', branch],
    directory: `${tempDir}/${project}`,
  });
}

/** @param {string} remoteRepoUrl */
export function cloneRepo(remoteRepoUrl) {
  if (!fs.existsSync(tempDir)) {
    fs.mkdirSync(tempDir);
  }

  execCmd({
    command: 'git',
    args: ['clone', remoteRepoUrl],
    directory: tempDir,
  });
}

/**
 * @param {string} project Git project, same as provisioned projectId.
 * @param {string} msg     Commit message.
 */
export function commitChanges(project, msg) {
  execCmd({
    command: 'git',
    args: ['commit', '-m', msg],
    directory: `${tempDir}/${project}`,
  });
}

/**
 * @param {string} project Git project, same as provisioned projectId.
 * @param {string} branch  Branch to fetch.
 */
export function fetchBranch(project, branch) {
  execCmd({
    command: 'git',
    args: ['fetch', 'origin', `${branch}:${branch}`],
    directory: `${tempDir}/${project}`,
  });
}

/**
 * @param {string} project Git project, same as provisioned projectId.
 * @param {string} branch  Branch to push.
 */
export function pushBranch(project, branch) {
  execCmd({
    command: 'git',
    args: ['push', 'origin', branch],
    directory: `${tempDir}/${project}`,
  });
}

export function setupUser(project, email, name) {
  execCmd({
    command: 'git',
    args: ['config', '--local', 'user.email', email],
    directory: `${tempDir}/${project}`,
  });

  execCmd({
    command: 'git',
    args: ['config', '--local', 'user.name', name],
    directory: `${tempDir}/${project}`,
  });
}

/**
 * @param {string}   project Git project, same as provisioned projectId.
 * @param {string[]} files   Files to be staged.
 */
export function stageFiles(project, files) {
  files.unshift('add');

  execCmd({
    command: 'git',
    args: files,
    directory: `${tempDir}/${project}`,
  });
}

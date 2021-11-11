import fs from 'fs';
import path from 'path';

import config from '../../config';

/**
 * Delete a file.
 *
 * @param {string} filePath Relative file path from project root.
 */
export function deleteFile(filePath) {
  fs.unlinkSync(`${config.projectRoot}/${filePath}`);
}

/**
 * Find files within a directory that match a given pattern for the file name.
 *
 * @param   {string} dir     Directory in which to search for file.
 * @param   {regexp} pattern Pattern used to match file name in directory.
 * @returns         String Array of files.
 */
export function findByPattern(dir, pattern) {
  return fs.readdirSync(dir).filter((fileName) => pattern.test(fileName));
}

/**
 * @param {string}          filePath Full path to file.
 * @param {string | object} oldData  Original content in file to be
 *     replaced; can be a string or a regular expression.
 * @param {string | object} newData  Updated content to replace original
 *     content in file; can be a string or a regular expression.
 */
export function replace(filePath, oldData, newData) {
  let oldFileContent;

  try {
    oldFileContent = fs.readFileSync(filePath, 'utf-8');
  } catch (error) {
    throw new Error(`Unable to read file ${filePath}`);
  }

  const newFileContent = oldFileContent.replace(oldData, newData);

  try {
    fs.writeFileSync(filePath, newFileContent, 'utf-8');
  } catch (error) {
    throw new Error(`Unable to write to file ${filePath}`);
  }
}

/**
 * @param {string} filePath  Full path to file.
 * @param {string} imageName Image name.
 * @param {string} version   Image tag.
 */
export function updateLcpJson(filePath, imageName, version) {
  let image;

  switch (imageName) {
    case 'ci':
      image = 'jenkins';
      break;
    case 'liferay':
      image = 'liferay-dxp';
      break;
    default:
      image = imageName;
  }

  const regex = new RegExp(`${image}:.*(?=")`);

  replace(path.join(filePath, 'LCP.json'), regex, `${image}:${version}`);
}

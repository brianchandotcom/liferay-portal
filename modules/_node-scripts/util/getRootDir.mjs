import fs from 'fs/promises';
import path from 'path';

let cachedRootDir;

export default async function getRootDir() {
	if (cachedRootDir) {
		return cachedRootDir;
	}

	let rootDir = path.resolve('.');
	let found = false;

	while(path.dirname(rootDir) !== rootDir) {
		try {
			await fs.stat(path.join(rootDir, 'yarn.lock'));

			found = true;

			break;
		}
		catch(error) {
			if (error.code !== 'ENOENT') {
				throw error;
			}

			rootDir = path.resolve(rootDir, '..');
		}
	}

	if (!found) {
		throw new Error('Unable to find root project folder (is yarn.lock missing?)');
	}

	cachedRootDir = rootDir;

	return rootDir;
}

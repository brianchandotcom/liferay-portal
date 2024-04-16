import fs from 'fs/promises';
import path from 'path';

let cachedRootDir;

export default async function getRootDir() {
	if (cachedRootDir) {
		return cachedRootDir;
	}

	let rootDir = path.resolve('.');

	while(rootDir !== '/') {
		try {
			await fs.stat(path.join(rootDir, 'yarn.lock'));

			break;
		}
		catch(error) {
			if (error.code !== 'ENOENT') {
				throw error;
			}

			rootDir = path.resolve(rootDir, '..');
		}
	}

	cachedRootDir = rootDir;

	return rootDir;
}

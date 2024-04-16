import fs from 'fs/promises';
import path from 'path';

export default async function writePackageJson(projectDescription) {
	const filePath = `./build/node/packageRunBuild/resources/package.json`;

	const {main, name, version} = projectDescription;

	const json = {
		main,
		name,
		version
	};

	await fs.mkdir(path.dirname(filePath), {recursive: true});
	await fs.writeFile(filePath, JSON.stringify(json, null, '\t'), 'utf-8');
}

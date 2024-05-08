import fs from 'fs/promises';
import path from 'path';

export default async function writeManifestJson(projectDescription) {
	const filePath = `./build/node/packageRunBuild/resources/manifest.json`;

	const {name, version} = projectDescription;

	const json = {
		packages: {
			'/': {
				dest: {
					dir: '.',
					id: '/',
					name,
					version
				},
				modules: {
					'index.js': {
						flags: {
							esModule: true,
							useESM: true
						}
					}
				},
				src: {
					id: '/',
					name,
					version
				}
			},
		},
	};

	await fs.mkdir(path.dirname(filePath), {recursive: true});
	await fs.writeFile(filePath, JSON.stringify(json, null, '\t'), 'utf-8');
}


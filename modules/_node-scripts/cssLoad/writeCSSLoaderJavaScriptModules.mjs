import fs from 'fs/promises';
import path from 'path';

import getFlatName from '../util/getFlatName.mjs';

export default async function writeCSSLoaderJavaScriptModules(projectExports, projectWebContextPath) {
	if (!projectExports) {
		return;
	}

	await Promise.all(
		projectExports
			.filter(moduleName => moduleName.endsWith('.css'))
			.map(moduleName => writeCSSLoaderJavaScriptModule(projectWebContextPath, moduleName))
	);
}

async function writeCSSLoaderJavaScriptModule(webContextPath, moduleName) {
	const flatModuleName = getFlatName(moduleName);

	const cssLoaderPath = 
		`./build/node/packageRunBuild/resources/__liferay__/exports/${flatModuleName}.js`;

	const source = `
const link = document.createElement('link');
link.setAttribute('rel','stylesheet');
link.setAttribute('type','text/css');
link.setAttribute(
'href',
Liferay.ThemeDisplay.getPathContext() + '/o${webContextPath}/__liferay__/css/${flatModuleName}'
);
document.querySelector('head').appendChild(link);
`;

	await fs.mkdir(path.dirname(cssLoaderPath), {recursive:true});
	await fs.writeFile(cssLoaderPath, source);
}

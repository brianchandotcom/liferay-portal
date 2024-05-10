import path from 'path';

import getExternals from './getExternals.mjs';
import getCssLoaderPlugin from './plugins/getCssLoaderPlugin.mjs';
import getExactAliasPlugin from './plugins/getExactAliasPlugin.mjs';
import getImportBridgesPlugin from './plugins/getImportBridgesPlugin.mjs';
import getScssLoaderPlugin from './plugins/getScssLoaderPlugin.mjs';
import runEsbuild from './runEsbuild.mjs';

export default async function bundleJavaScriptMain(
	globalImports, globalSymbols, projectMain, projectWebContextPath
) {
	if (!projectMain) {
		return;
	}

	const esbuildConfig = {
		bundle: true,
		entryNames: 'index',
		entryPoints: [path.resolve(projectMain)],
		external: getExternals(globalImports, 'main'),
		format: 'esm',
		loader: { 
			'.scss': 'css',
			'.js': 'jsx',
			'.png': 'empty'
		},
		outdir: './build/node/packageRunBuild/resources/__liferay__',
		sourcemap: true,
		target: ['es2020'],
		plugins: [
			getCssLoaderPlugin(globalImports, 'main'),
			getExactAliasPlugin(globalImports, 'main'),
			getImportBridgesPlugin(globalImports, globalSymbols),
			getScssLoaderPlugin(projectWebContextPath),
		]
	};

	return runEsbuild(esbuildConfig, 'main');
}

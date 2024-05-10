import path from 'path';

import getCssLoaderPlugin from './getCssLoaderPlugin.mjs';
import getExactAliasPlugin from './getExactAliasPlugin.mjs';
import getImportBridgesPlugin from './getImportBridgesPlugin.mjs';
import getScssLoaderPlugin from './getScssLoaderPlugin.mjs';
import getExternals from './getExternals.mjs';
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

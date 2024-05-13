import path from 'path';

import { BUILD_MAIN_EXPORTS_PATH } from '../util/constants.mjs';
import getExternals from './getExternals.mjs';
import getCssLoaderPlugin from './plugins/getCssLoaderPlugin.mjs';
import getExactAliasPlugin from './plugins/getExactAliasPlugin.mjs';
import getImportBridgesPlugin from './plugins/getImportBridgesPlugin.mjs';
import getScssLoaderPlugin from './plugins/getScssLoaderPlugin.mjs';
import runEsbuild from './runEsbuild.mjs';


export default async function bundleJavaScriptMain(
	globalImports, overridenPackageSymbols, projectMain, projectWebContextPath
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
		outdir: BUILD_MAIN_EXPORTS_PATH,
		sourcemap: true,
		target: ['es2020'],
		plugins: [
			getCssLoaderPlugin(globalImports, 'main'),
			getExactAliasPlugin(globalImports, 'main'),
			getImportBridgesPlugin(globalImports, overridenPackageSymbols),
			getScssLoaderPlugin(projectWebContextPath),
		]
	};

	return runEsbuild(esbuildConfig, 'main');
}

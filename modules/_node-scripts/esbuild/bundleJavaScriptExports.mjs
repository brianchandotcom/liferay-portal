import { BUILD_MAIN_EXPORTS_PATH } from '../util/constants.mjs';
import getFlatName from '../util/getFlatName.mjs';
import getEntryPoint from './getEntryPoint.mjs';
import getExternals from './getExternals.mjs';
import getExactAliasPlugin from './plugins/getExactAliasPlugin.mjs';
import getImportBridgesPlugin from './plugins/getImportBridgesPlugin.mjs';
import runEsbuild from './runEsbuild.mjs';
import writeExportBridge from './writeExportBridge.mjs';

export default async function bundleJavaScriptExports(
	globalImports, globalSymbols, projectExports
) {
	if (!projectExports.length) {
		return;
	}

	await Promise.all(
		projectExports
			.filter(moduleName => !moduleName.endsWith('.css'))
			.map(moduleName => bundle(globalImports, globalSymbols, moduleName))
	);
}

async function bundle(globalImports, globalSymbols, moduleName) {
	const esbuildConfig = {
		bundle: true,
		entryPoints: [getEntryPoint(moduleName)],
		external: getExternals(globalImports, 'exports'),
		format: 'esm',
		outdir: BUILD_MAIN_EXPORTS_PATH,
		sourcemap: true,
		target: ['es2020'],
		plugins: [
			getExactAliasPlugin(globalImports, 'exports', [moduleName]),
			getImportBridgesPlugin(globalImports, globalSymbols),
		]
	};
	
	await writeExportBridge(globalSymbols, moduleName);

	return runEsbuild(esbuildConfig, getFlatName(moduleName));
}

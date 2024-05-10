import { BUILD_MAIN_EXPORTS_PATH } from '../util/constants.mjs';
import getFlatName from '../util/getFlatName.mjs';
import getEntryPoint from './getEntryPoint.mjs';
import runEsbuild from './runEsbuild.mjs';

export default async function bundleCSSExports(projectExports) {
	if (!projectExports.length) {
		return;
	}

	await Promise.all(
		projectExports
			.filter(moduleName => moduleName.endsWith('.css'))
			.map(moduleName => bundle(moduleName))
	);
}

async function bundle(moduleName) {
	const esbuildConfig = {
		entryPoints: [getEntryPoint(moduleName)],
		loader: {
			'.png': 'empty',
		},
		outdir: BUILD_MAIN_EXPORTS_PATH,
		sourcemap: true,
	};
	
	return runEsbuild(esbuildConfig, getFlatName(moduleName));
}

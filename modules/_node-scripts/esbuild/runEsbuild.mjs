import esbuild from 'esbuild';
import fs from 'fs/promises';
import path from 'path';

export default async function runEsbuild(config, configName) {
	await Promise.all([
		doRunEsbuild(config, configName),
		writeDebugEsbuildConfig(config, configName),
	]);
}

async function doRunEsbuild(config, configName) {
	const start = performance.now();

	try {
		await esbuild.build(config);
	}
	catch(error) {
		throw new Error(`Esbuild command failed: ${error}`);
	}

	const lapse = performance.now() - start;

	console.log(`Esbuild for ${configName} took: ${(lapse/1000).toFixed(3)} s`);
}

async function writeDebugEsbuildConfig(config, configName) {
	const configFilePath = path.join(
		'build', 'node-build', `${configName}.esbuild.config.json`
	);

	await fs.mkdir(path.dirname(configFilePath), {recursive: true});
	await fs.writeFile(configFilePath, JSON.stringify(config, null, '\t'), 'utf-8');
}

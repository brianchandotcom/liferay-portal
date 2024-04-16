import getGlobalImports from './configuration/getGlobalImports.mjs';
import getGlobalSymbols from './configuration/getGlobalSymbols.mjs';
import getProjectDescription from './configuration/getProjectDescription.mjs';
import getProjectExports from './configuration/getProjectExports.mjs';
import getProjectMain from './configuration/getProjectMain.mjs';
import getProjectNpmScriptsConfig from './configuration/getProjectNpmScriptsConfig.mjs';
import getProjectWebContextPath from './configuration/getProjectWebContextPath.mjs';
import writeCSSLoaderJavaScriptModules from './cssLoad/writeCSSLoaderJavaScriptModules.mjs';
import bundleCSSExports from './esbuild/bundleCSSExports.mjs';
import bundleJavaScriptExports from './esbuild/bundleJavaScriptExports.mjs';
import bundleJavaScriptMain from './esbuild/bundleJavaScriptMain.mjs';
import runNpmScripts from './npmscripts/runNpmScripts.mjs';
import writeAMD2ESMBridges from './npmscripts/writeAMD2ESMBridges.mjs';
import writeManifestJson from './npmscripts/writeManifestJson.mjs';
import writePackageJson from './npmscripts/writePackageJson.mjs';
import writeTimings from './util/writeTimings.mjs';

const start = Date.now();

const [
	globalImports,
	globalSymbols,
	projectDescription,
	projectExports,
	projectMain,
	projectNpmScriptsConfig,
	projectWebContextPath,
] = await Promise.all([
	getGlobalImports(),
	getGlobalSymbols(),
	getProjectDescription(),
	getProjectExports(),
	getProjectMain(),
	getProjectNpmScriptsConfig(),
	getProjectWebContextPath()
]);

const endConfig = Date.now();

await Promise.all([
	bundleJavaScriptMain(globalImports, globalSymbols, projectMain, projectWebContextPath),
	bundleJavaScriptExports(globalImports, globalSymbols, projectExports),
	bundleCSSExports(projectExports),
	writeCSSLoaderJavaScriptModules(projectExports, projectWebContextPath),
	writePackageJson(projectDescription),
	writeManifestJson(projectDescription),
	writeAMD2ESMBridges(projectDescription, projectWebContextPath),
	runNpmScripts(projectNpmScriptsConfig),
]);

await writeTimings(start, endConfig);

import getExportedSymbols from '../../util/getExportedSymbols.mjs';
import getFlatName from '../../util/getFlatName.mjs';
import {IMPORT_BRIDGE_FILTER, decodeBridgePath} from '../getImportBridgePath.mjs';
import getPathPrefix from '../getPathPrefix.mjs';

export default function getImportBridgesPlugin(globalImports, globalSymbols) {
	const importBridgesCache = {};

	return {
		name: 'import-bridges-plugin',

		setup(build) {
			build.onLoad(
				{ 
					filter: IMPORT_BRIDGE_FILTER 
				}, 
				async (args) => {
					const {path: loadPath} = args;

					if (importBridgesCache[loadPath]) {
						return importBridgesCache[loadPath];
					}

					const {moduleName, type} = decodeBridgePath(loadPath);

					const {external, webContextPath} = globalImports[moduleName];

					const contents = getImportBridgeCode(
						globalImports, globalSymbols, type, moduleName, external, 
						webContextPath
					);	

					importBridgesCache[loadPath] = {
						contents,
						loader: 'js',
					};

					return importBridgesCache[loadPath];
				}
			);
		},
	};
}

function getImportBridgeCode(
	globalImports, globalSymbols, type, moduleName, external, webContextPath
) {
	let hasDefault;

	if (globalImports[moduleName]?.external === false) {
		hasDefault = false;
	}
	else {
		const symbols = getExportedSymbols(globalSymbols, moduleName);

		hasDefault = !!symbols['default'];
	}

	if (moduleName === 'react' || moduleName === 'react-dom' || moduleName === 'object-hash' || moduleName === 'prop-types') {
		hasDefault = true;
	}

	const pathPrefix = getPathPrefix(type);

	const modulePath = external ? `exports/${getFlatName(moduleName)}.js` : 'index.js';

	let source = `
export * from '${pathPrefix}/${webContextPath}/__liferay__/${modulePath}';
`;

	if (hasDefault) {
		source += `
import __default__ from '${pathPrefix}/${webContextPath}/__liferay__/${modulePath}';
export default __default__;
`;
	}

	return source;
}

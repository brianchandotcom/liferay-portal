import getFlatName from '../util/getFlatName.mjs';
import getExportBridgePath from './getExportBridgePath.mjs';

export default function getEntryPoint(moduleName) {
	let entryPoint;

	if (moduleName.endsWith('.css')) {
		entryPoint = {
			in: moduleName,
			out: `css/${getFlatName(moduleName).replace(/\.css$/, '')}`
		};
	}
	else {
		entryPoint = {
			in: getExportBridgePath(moduleName),
			out: `exports/${getFlatName(moduleName)}`
		};
	}

	return entryPoint;
}

import getFlatName from '../util/getFlatName.mjs';

export default function getExportBridgePath(moduleName) {			
	return `./build/node-build/export/${getFlatName(moduleName)}.js`;
}


const VALID_TYPES = ['main', 'exports'];

export const IMPORT_BRIDGE_FILTER = /\/\$\/bridge\/.*$/;

export default function getImportBridgePath(moduleName, type) {
	if (!VALID_TYPES.includes(type)) {
		throw new Error(`Invalid type: ${type}`);
	}

	return `/$/bridge/for/${type}/${moduleName}`;
}

export function decodeBridgePath(bridgePath) {
	const parts = bridgePath.split('/');

	return {
		moduleName: parts.slice(5).join('/'),
		type: parts[4]
	};
}

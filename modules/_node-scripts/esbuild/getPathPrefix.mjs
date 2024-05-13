const TYPE_PREFIX = {
	exports: '../../..',
	main: '../..'
};

/**
 * Get the prefix needed to make relative URLs that can be used in ESM context.
 *
 * @param type
 * This designates the context where the relative path prefix will be interpreted by the browser. It
 * can be `exports` or `main`. The former means the URL will appear in a npm export bundle, whereas
 * the latter means it will appear in the main entry point (index.js).
 */
export default function getPathPrefix(type) {
	const prefix = TYPE_PREFIX[type];

	if (prefix === undefined) {
		throw new Error(`Invalid type: ${type}`);
	}

	return prefix;
}

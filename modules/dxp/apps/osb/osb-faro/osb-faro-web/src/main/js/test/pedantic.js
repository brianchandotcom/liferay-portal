/**
 * Throw an error if a test triggers a warning or error message
 */
function pedantic(message) {
	throw message instanceof Error ? message : new Error(message);
}

/* eslint-disable no-console */

const error = console.error;
const warn = console.warn;

export function enable() {
	console.error = pedantic;
	console.warn = pedantic;
}

export function disable() {
	console.error = error;
	console.warn = warn;
}

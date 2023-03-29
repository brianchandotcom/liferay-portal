import Promise from 'metal-promise';

/**
 * Executes functions that return Promises in sequence. If a Promise is to reject, the execution will stop.
 * @param {Array} fns - an array of functions that return Promises.
 * @return {Promise}
 */
export function sequence(fns: Array<(value: any) => Promise<any>>) {
	return (value?: any) =>
		fns.reduce(
			(result, fn) => result.then(() => fn(value)),
			Promise.resolve()
		);
}

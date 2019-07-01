/**
 * Maps integer statuses given by the backend to strings
 *
 * @export
 * @param {number} status
 * @returns {string}
 */
export function mapExperimentsStatus(status) {
	switch (status) {
		case 0:
		default:
			return 'draft';
	}
}

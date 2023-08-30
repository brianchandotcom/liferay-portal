export function getDocs() {
	return Liferay.Util.fetch(
		'/o/headless-delivery/v1.0/sites/20119/documents',
		{method: 'GET'}
	).then(res => res.json());
}
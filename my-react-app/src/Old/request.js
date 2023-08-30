export function getUsers() {
	return Liferay.Util.fetch(
		'/o/headless-admin-user/v1.0/user-accounts',
		{method: 'GET'}
	).then(res => res.json());
}
export function addUser({emailAddress, familyName, givenName, userName}) {
	const data = {
		alternateName: userName,
		emailAddress,
		familyName,
		givenName
	};

	const headers = new Headers();

	headers.append('Content-Type', 'application/json');

	const request = {
		body: JSON.stringify(data),
		headers,
		method: 'POST'
	};

	return Liferay.Util.fetch(
		'/o/headless-admin-user/v1.0/user-accounts',
		request
	).then(res => res.json());
}
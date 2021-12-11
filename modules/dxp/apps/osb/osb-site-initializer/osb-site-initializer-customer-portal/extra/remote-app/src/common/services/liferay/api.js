const HEADLESS_BASE_URL = `${window.location.origin}/o/headless-delivery/v1.0`;

const fetchHeadless = async ({resolveAsJson = true, url}) => {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(`${HEADLESS_BASE_URL}${url}`, {
		headers: {
			'Cache-Control': 'max-age=30, stale-while-revalidate=30',
			'x-csrf-token': Liferay.authToken,
		},
	});

	if (resolveAsJson) {
		return response.json();
	}

	return response;
};
export {fetchHeadless};

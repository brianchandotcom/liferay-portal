import { useEffect, useState } from 'react';
import { REACT_APP_LIFERAY_API } from '../utils';

const getData = (data) => {
	let currentData = data;

	if (currentData) {
		if (Object.keys(currentData)[0] === "c") {
			currentData = currentData.c;
		}

		currentData = currentData[Object.keys(currentData)[0]];

		if (Object.keys(currentData)[0] === "items") {
			currentData = currentData.items;
		}
	}

	return currentData;
}

const useGraphQL = (query) => {
	const [isLoading, setLoading] = useState(true);
	const [data, setData] = useState();
	const [error, setError] = useState();

	const queryString = JSON.stringify(query);

	const doFetch = async () => {
		setLoading(true);

		try {
			// eslint-disable-next-line @liferay/portal/no-global-fetch
			const response = await fetch(`${REACT_APP_LIFERAY_API}/o/graphql`, {
				body: queryString,
				headers: {
					'Content-Type': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
				method: 'POST',
			});

			const { data } = await response.json();

			setData(getData(data));
			setLoading(false);
		} catch (error) {
			setError(error.message);
		}
	}

	useEffect(() => {
		if (queryString) {
			doFetch();
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [queryString]);

	return {
		data,
		error,
		isLoading
	};
}

export default useGraphQL;
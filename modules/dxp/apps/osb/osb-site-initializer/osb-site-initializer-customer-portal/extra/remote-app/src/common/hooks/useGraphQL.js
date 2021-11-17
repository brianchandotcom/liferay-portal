import {useEffect, useState} from 'react';
import {REACT_APP_LIFERAY_API} from '../utils';

const getData = (data) => {
	const currentData = {};

	Object.keys(data).forEach((key) => {
		if (key === 'c') {
			Object.keys(data[key]).forEach((cObjectKey) => {
				const cObject = data[key][cObjectKey];

				if (Object.keys(cObject)[0] === 'items') {
					currentData[cObjectKey] = cObject['items'];
				}
				else {
					currentData[cObjectKey] = cObject;
				}
			});
		}
		else {
			if (Object.keys(data[key])[0] === 'items') {
				currentData[key] = data[key]['items'];
			}
			else {
				currentData[key] = data[key];
			}
		}
	});

	return currentData;
};

const useGraphQL = (queries) => {
	const [isLoading, setLoading] = useState(true);
	const [data, setData] = useState();
	const [error, setError] = useState();

	const queryString = JSON.stringify({
		query: `{${queries.join('\n')}}`,
	});

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

			const {data} = await response.json();

			setData(getData(data));
			setLoading(false);
		}
		catch (error) {
			setError(error.message);
		}
	};

	useEffect(() => {
		if (queryString) {
			doFetch();
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [queryString]);

	return {
		data,
		error,
		isLoading,
	};
};

export default useGraphQL;

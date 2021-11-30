import {useEffect, useState} from 'react';
import {LiferayService} from '../../../common/services/liferay';

export function useBusinessTypes() {
	const [data, setData] = useState();
	const [error, setError] = useState();

	useEffect(() => {
		loadBusinessTypes();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const loadBusinessTypes = async (search = '') => {
		try {
			if (!search.length) {
				return reset();
			}

			const response = await LiferayService.getBusinessTypes(search);
			setError('');

			return setData(response);
		}
		catch (error) {
			return setError(
				'Unable to make the request. Please try again later.'
			);
		}
	};

	const reset = () => {
		setData(undefined);
		setError(undefined);
	};

	return {
		businessTypes: data || [],
		isError: error,
		isLoading: !data && !error,
		reload: loadBusinessTypes,
	};
}

import {useEffect, useState} from 'react';
import {MockService} from '~/common/services/mock';

export const useLegalEntity = () => {
	const [data, setData] = useState();
	const [error, setError] = useState();

	useEffect(() => {
		_loadEntities();
	}, []);

	const _loadEntities = async () => {
		try {
			const response = await MockService.getLegalEntities();
			setData(response);
		} catch (error) {
			console.warn(error);
			setError(error);
		}
	};

	return {
		entities: data || [],
		isError: error,
		isLoading: !data && !error,
	};
};

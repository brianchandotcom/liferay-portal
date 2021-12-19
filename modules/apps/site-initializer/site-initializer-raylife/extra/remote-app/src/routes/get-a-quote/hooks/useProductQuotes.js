import {useEffect, useState} from 'react';
import {LiferayService} from '../../../common/services/liferay';

export function useProductQuotes() {
	const [productQuotes, setProductQuotes] = useState([]);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState();

	const getProductQuotes = async () => {
		try {
			const response = await LiferayService.getProductQuotes();

			setProductQuotes(response);
		}
		catch (error) {
			setError(error);
		}
		setLoading(false);
	};

	useEffect(() => {
		getProductQuotes();
	}, []);

	return {
		error,
		loading,
		productQuotes,
	};
}

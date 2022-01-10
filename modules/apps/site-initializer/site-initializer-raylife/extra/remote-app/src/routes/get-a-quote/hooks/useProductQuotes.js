import {useEffect, useState} from 'react';
import {getProductQuotes} from '../services/CommerceCatalog';

export function useProductQuotes() {
	const [productQuotes, setProductQuotes] = useState([]);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState();

	const _getProductQuotes = async () => {
		try {
			const response = await getProductQuotes();

			setProductQuotes(response);
		}
		catch (error) {
			setError(error);
		}
		setLoading(false);
	};

	useEffect(() => {
		_getProductQuotes();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return {
		error,
		loading,
		productQuotes,
	};
}

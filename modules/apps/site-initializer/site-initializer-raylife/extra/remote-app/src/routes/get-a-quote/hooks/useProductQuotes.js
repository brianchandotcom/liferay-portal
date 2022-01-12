/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

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

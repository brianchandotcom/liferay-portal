/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

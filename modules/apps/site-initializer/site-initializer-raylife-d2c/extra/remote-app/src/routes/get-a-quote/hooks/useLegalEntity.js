/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {MockService} from '../../../common/services/mock';

export function useLegalEntity() {
	const [data, setData] = useState();
	const [error, setError] = useState();

	useEffect(() => {
		_loadEntities();
	}, []);

	const _loadEntities = async () => {
		try {
			const response = await MockService.getLegalEntities();
			setData(response);
		}
		catch (error) {
			console.warn(error);
			setError(error);
		}
	};

	return {
		entities: data || [],
		isError: error,
		isLoading: !data && !error,
	};
}

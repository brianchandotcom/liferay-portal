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

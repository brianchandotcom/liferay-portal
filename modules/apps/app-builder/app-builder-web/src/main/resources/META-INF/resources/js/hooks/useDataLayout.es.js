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

import {getItem} from 'data-engine-js-components-web/js/utils/client.es';
import {errorToast} from 'data-engine-js-components-web/js/utils/toast.es';
import {useEffect, useState} from 'react';

export default function useDataLayout(dataLayoutId, dataDefinitionId) {
	const [state, setState] = useState({
		dataDefinition: null,
		dataLayout: {},
		isLoading: true,
	});

	useEffect(() => {
		Promise.all([
			getItem(`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}`),
			getItem(`/o/data-engine/v2.0/data-layouts/${dataLayoutId}`),
		])
			.then(([dataDefinition, dataLayout]) => {
				setState((prevState) => ({
					...prevState,
					dataDefinition,
					dataLayout,
					isLoading: false,
				}));
			})
			.catch(() => {
				setState((prevState) => ({
					...prevState,
					isLoading: false,
				}));

				errorToast();
			});
	}, [dataDefinitionId, dataLayoutId]);

	return state;
}

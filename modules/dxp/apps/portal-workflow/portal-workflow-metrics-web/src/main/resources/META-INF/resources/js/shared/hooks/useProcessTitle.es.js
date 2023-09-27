/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useContext, useEffect} from 'react';

import {AppContext} from '../../components/AppContext.es';
import {useFetch} from './useFetch.es';

const useProcessTitle = (processId, pageTitle = null) => {
	const {setTitle} = useContext(AppContext);

	const {fetchData} = useFetch({
		callback: (data) =>
			setTitle(data + `${pageTitle ? ': ' + pageTitle : ''}`),
		plainText: true,
		url: `/processes/${processId}/title`,
	});

	useEffect(() => {
		fetchData();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [pageTitle, processId]);
};

export {useProcessTitle};

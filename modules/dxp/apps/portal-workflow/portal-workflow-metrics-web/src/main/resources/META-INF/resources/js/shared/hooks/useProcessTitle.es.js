/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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

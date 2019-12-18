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

import {useContext, useCallback, useState} from 'react';

import {AppContext} from '../../components/AppContext.es';

const usePost = ({body = {}, headless = false, url}) => {
	const {client, clientHeadless} = useContext(AppContext);
	const [data, setData] = useState({});

	const queryBodyStr = JSON.stringify(body);
	const requestClient = headless ? clientHeadless : client;
	const postData = useCallback(
		() =>
			requestClient.post(url, body).then(({data}) => {
				setData(data);
			}),
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[queryBodyStr, url, headless]
	);

	return {
		data,
		postData
	};
};

export {usePost};

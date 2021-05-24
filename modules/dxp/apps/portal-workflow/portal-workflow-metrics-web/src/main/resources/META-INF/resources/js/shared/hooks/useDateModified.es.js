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

import {useCallback, useContext, useState} from 'react';

import {AppContext} from '../../components/AppContext.es';

const useDateModified = ({
	admin = false,
	callback = (data) => data,
	params = {},
	processId,
	fetchDateModified = false,
}) => {
	const {getClient} = useContext(AppContext);
	const [dateModified, setDateModified] = useState(null);

	const client = getClient(admin);
	const queryParamsStr = JSON.stringify(params);

	const url = `processes/${processId}/last-sla-result`;

	const fetchData = useCallback(
		() => {
			if (fetchDateModified) {
				return client.get(url, {params}).then(({data, status}) => {
					if (status === 200) {
						setDateModified(data.dateModified);

						return callback(data.dateModified);
					}
					setDateModified(null);

					return callback(null);
				});
			}
			else {
				setDateModified(null);

				return callback(null);
			}
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[client, fetchDateModified, queryParamsStr, url]
	);

	return {
		dateModified,
		fetchData,
	};
};

export {useDateModified};

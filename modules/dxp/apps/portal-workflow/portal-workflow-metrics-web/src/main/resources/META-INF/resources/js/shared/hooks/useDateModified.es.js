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

import {useState} from 'react';

import {useFetch} from './useFetch.es';

const useDateModified = ({admin = false, params = {}, processId}) => {
	const [dateModified, setDateModified] = useState(null);

	const url = `/processes/${processId}/last-sla-result`;

	const callback = (data) => {
		setDateModified(data.dateModified);
	};

	const {fetchData} = useFetch({admin, callback, params, url});

	return {
		dateModified,
		fetchData,
	};
};

export {useDateModified};

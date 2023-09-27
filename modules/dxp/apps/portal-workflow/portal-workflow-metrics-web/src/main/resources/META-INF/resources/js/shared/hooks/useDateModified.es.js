/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

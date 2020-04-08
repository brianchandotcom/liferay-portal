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

import {useMemo} from 'react';

import {useFetch} from '../../../../shared/hooks/useFetch.es';

const useCalendars = () => {
	const {data, fetchData: fetchCalendars} = useFetch({url: '/calendars'});

	const calendars = useMemo(() => data.items || [], [data]);

	const defaultCalendar = useMemo(
		() => calendars.find(({defaultCalendar}) => defaultCalendar),
		[calendars]
	);

	return {calendars, defaultCalendar, fetchCalendars};
};

export {useCalendars};

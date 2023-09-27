/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useFetch} from '../../../../shared/hooks/useFetch.es';

const useCalendars = () => {
	const {data, fetchData: fetchCalendars} = useFetch({url: '/calendars'});

	const calendars = data?.items || [];

	const defaultCalendar = calendars.find(
		({defaultCalendar}) => defaultCalendar
	);

	return {calendars, defaultCalendar, fetchCalendars};
};

export {useCalendars};

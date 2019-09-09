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

import {
	formatDateEnLocale,
	formatQueryDate,
	parseDateEnLocale,
	parseDateMomentEnLocale
} from '../../util/timeRangeUtil.es';
import {
	parse,
	stringify
} from '../../../../shared/components/router/queryString.es';
import {useContext, useState} from 'react';
import moment from '../../../../shared/util/moment.es';
import {pushToHistory} from '../../../../shared/components/filter/util/filterUtil.es';
import {TimeRangeContext} from './TimeRangeStore.es';
import {useRouter} from '../../../../shared/components/router/useRouter.es';

const useCustomTimeRange = filterKey => {
	const [errors, setErrors] = useState(undefined);
	const {getSelectedTimeRange, setTimeRanges, timeRanges} = useContext(
		TimeRangeContext
	);
	const routerProps = useRouter();

	const selectedTimeRange = getSelectedTimeRange() || {};

	const [dateEnd, setDateEnd] = useState(
		formatDateEnLocale(selectedTimeRange.dateEnd)
	);
	const [dateStart, setDateStart] = useState(
		formatDateEnLocale(selectedTimeRange.dateStart)
	);

	const applyCustomFilter = () => {
		if (!errors) {
			setTimeRanges([
				{
					...timeRanges[0],
					active: true,
					dateEnd: parseDateEnLocale(dateEnd, true),
					dateStart: parseDateEnLocale(dateStart)
				},
				...timeRanges.slice(1, timeRanges.length).map(item => ({
					...item,
					active: false
				}))
			]);

			updateQueryString();
		}
	};

	const updateQueryString = () => {
		const query = parse(routerProps.location.search);

		query.filters = {
			...query.filters,
			[filterKey]: 'custom',
			dateEnd: formatQueryDate(dateEnd, true),
			dateStart: formatQueryDate(dateStart)
		};

		pushToHistory(stringify(query), routerProps);
	};

	const validate = () => {
		const dateEndMoment = parseDateMomentEnLocale(dateEnd);
		const dateStartMoment = parseDateMomentEnLocale(dateStart);

		let errors = checkValidDate(dateEndMoment, dateStartMoment);

		if (!errors) {
			errors = checkRangeConsistency(dateEndMoment, dateStartMoment);
		}

		if (!errors) {
			errors = checkEarlierDate(dateEndMoment, dateStartMoment);
		}

		setErrors(errors);

		return errors;
	};

	return {
		applyCustomFilter,
		dateEnd,
		dateStart,
		errors,
		setDateEnd,
		setDateStart,
		validate
	};
};

const checkEarlierDate = (dateEndMoment, dateStartMoment) => {
	const earlierDate = moment()
		.date(1)
		.month(1)
		.year(1970);
	let errors;

	if (dateEndMoment.isBefore(earlierDate)) {
		errors = updateErrors(
			errors,
			'dateEnd',
			Liferay.Language.get('the-date-cannot-be-earlier-than-1970')
		);
	}

	if (dateStartMoment.isBefore(earlierDate)) {
		errors = updateErrors(
			errors,
			'dateStart',
			Liferay.Language.get('the-date-cannot-be-earlier-than-1970')
		);
	}

	return errors;
};

const checkRangeConsistency = (dateEndMoment, dateStartMoment) => {
	let errors;

	if (dateEndMoment.isBefore(dateStartMoment)) {
		errors = updateErrors(
			errors,
			'dateEnd',
			Liferay.Language.get(
				'the-end-date-cannot-be-earlier-than-the-start-date'
			)
		);
	}

	if (dateStartMoment.isAfter(dateEndMoment)) {
		errors = updateErrors(
			errors,
			'dateStart',
			Liferay.Language.get(
				'the-start-date-cannot-be-later-than-the-end-date'
			)
		);
	}

	return errors;
};

const checkValidDate = (dateEndMoment, dateStartMoment) => {
	const dateNow = new Date();
	let errors;

	if (!dateEndMoment.isValid() || dateEndMoment.isAfter(dateNow)) {
		errors = updateErrors(
			errors,
			'dateEnd',
			Liferay.Language.get('please-enter-a-valid-date')
		);
	}

	if (!dateStartMoment.isValid() || dateStartMoment.isAfter(dateNow)) {
		errors = updateErrors(
			errors,
			'dateStart',
			Liferay.Language.get('please-enter-a-valid-date')
		);
	}

	return errors;
};

const updateErrors = (errors, fieldName, message) => ({
	...(errors || {}),
	[fieldName]: message
});

export {useCustomTimeRange};

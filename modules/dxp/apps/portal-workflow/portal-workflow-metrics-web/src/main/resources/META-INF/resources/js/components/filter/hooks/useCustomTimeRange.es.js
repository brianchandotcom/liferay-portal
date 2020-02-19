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

import {
	getCapitalizedFilterKey,
	replaceHistory
} from '../../../shared/components/filter/util/filterUtil.es';
import {
	parse,
	stringify
} from '../../../shared/components/router/queryString.es';
import {useFilter} from '../../../shared/hooks/useFilter.es';
import {useRouter} from '../../../shared/hooks/useRouter.es';
import {useRouterParams} from '../../../shared/hooks/useRouterParams.es';
import {useSessionStorage} from '../../../shared/hooks/useStorage.es';
import moment from '../../../shared/util/moment.es';
import {
	formatDateEnLocale,
	formatQueryDate,
	parseDateMomentEnLocale
} from '../util/timeRangeUtil.es';

const getInitialDate = (timeRanges = [], timeRange, endDate) => {
	const {dateEnd, dateStart} =
		timeRanges.find(({id}) => id == timeRange) || {};

	return endDate ? dateEnd : dateStart;
};

const validateDate = (dateEndMoment, dateStartMoment) => {
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

const validateEarlierDate = (dateEndMoment, dateStartMoment) => {
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

const validateRangeConsistency = (dateEndMoment, dateStartMoment) => {
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

const updateErrors = (errors, fieldName, message) => ({
	...(errors || {}),
	[fieldName]: message
});

const useCustomTimeRange = (filterKey, prefixKey = '', withoutRouteParams) => {
	const [errors, setErrors] = useState(undefined);
	const {filters} = useRouterParams();
	const {dispatch, filterState, filterValues} = useFilter({
		withoutRouteParams
	});
	const [storedTimeRanges = {}] = useSessionStorage('timeRanges');

	const {items: timeRanges} = storedTimeRanges;

	const dateEndKey = getCapitalizedFilterKey(prefixKey, 'dateEnd');
	const dateStartKey = getCapitalizedFilterKey(prefixKey, 'dateStart');
	const prefixedFilterKey = getCapitalizedFilterKey(prefixKey, filterKey);

	const values = !withoutRouteParams ? filters : filterValues;

	const timeRange = values[prefixedFilterKey] || [];
	const initialDateEnd = getInitialDate(timeRanges, timeRange[0], true);
	const initialDateStart = getInitialDate(timeRanges, timeRange[0]);

	const [dateEnd, setDateEnd] = useState(
		formatDateEnLocale(values[dateEndKey] || initialDateEnd)
	);
	const [dateStart, setDateStart] = useState(
		formatDateEnLocale(values[dateStartKey] || initialDateStart)
	);

	const routerProps = useRouter();

	const applyCustomFilter = () => {
		const {dateEnd: dateEndError, dateStart: dateStartError} = errors || {};

		if (!dateEndError && !dateStartError) {
			const newValue = {
				[dateEndKey]: formatQueryDate(dateEnd, true),
				[dateStartKey]: formatQueryDate(dateStart),
				[prefixedFilterKey]: ['custom']
			};

			if (!withoutRouteParams) {
				const query = parse(routerProps.location.search);

				query.filters = {
					...query.filters,
					...newValue
				};

				replaceHistory(stringify(query), routerProps);
			}
			else {
				dispatch({...filterState, ...newValue});
			}
		}
	};

	const validate = () => {
		const dateEndMoment = parseDateMomentEnLocale(dateEnd);
		const dateStartMoment = parseDateMomentEnLocale(dateStart);

		const errors = {
			...validateDate(dateEndMoment, dateStartMoment),
			...validateEarlierDate(dateEndMoment, dateStartMoment),
			...validateRangeConsistency(dateEndMoment, dateStartMoment)
		};

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

export {useCustomTimeRange};

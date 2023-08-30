/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';

import {getCapitalizedFilterKey} from '../../../shared/components/filter/util/filterUtil.es';
import {useFilter} from '../../../shared/hooks/useFilter.es';
import {useRouterParams} from '../../../shared/hooks/useRouterParams.es';
import {getLocaleDateFormat} from '../../../shared/util/date.es';
import moment from '../../../shared/util/moment.es';
import {
	convertQueryDate,
	formatDateTime,
	parseDateMoment,
} from '../util/timeRangeUtil.es';

const updateErrors = (errors, fieldName, message) => ({
	...(errors || {}),
	[fieldName]: message,
});

const validateDate = (dateEndMoment, dateStartMoment) => {
	const dateNow = moment();
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
	const earlierDate = moment.utc([1970, 0, 1, 0]);
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

const useCustomTimeRange = (prefixKey, withoutRouteParams) => {
	const [errors, setErrors] = useState(undefined);
	const {filters} = useRouterParams();
	const {filterValues} = useFilter({
		withoutRouteParams,
	});

	const dateEndKey = getCapitalizedFilterKey(prefixKey, 'dateEnd');
	const dateFormat = getLocaleDateFormat();
	const dateStartKey = getCapitalizedFilterKey(prefixKey, 'dateStart');

	const values = !withoutRouteParams ? filters : filterValues;

	const [dateEnd, setDateEnd] = useState(
		convertQueryDate(values[dateEndKey], dateFormat)
	);
	const [dateStart, setDateStart] = useState(
		convertQueryDate(values[dateStartKey], dateFormat)
	);

	const applyCustomFilter = (handleApply) => {
		const {dateEnd: dateEndError, dateStart: dateStartError} = errors || {};

		if (!dateEndError && !dateStartError) {
			handleApply({
				dateEnd: formatDateTime(dateEnd, dateFormat, true),
				dateStart: formatDateTime(dateStart, dateFormat),
				key: 'custom',
			});
		}
	};

	const validate = () => {
		const dateEndMoment = parseDateMoment(dateEnd, dateFormat);
		const dateStartMoment = parseDateMoment(dateStart, dateFormat);

		const errors = {
			...validateDate(dateEndMoment, dateStartMoment),
			...validateEarlierDate(dateEndMoment, dateStartMoment),
			...validateRangeConsistency(dateEndMoment, dateStartMoment),
		};

		setErrors(errors);

		return errors;
	};

	return {
		applyCustomFilter,
		dateEnd,
		dateFormat,
		dateStart,
		errors,
		setDateEnd,
		setDateStart,
		validate,
	};
};

export {useCustomTimeRange};

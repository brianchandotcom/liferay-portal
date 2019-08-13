import {
	formatDate,
	formatQueryDate,
	parseDate,
	parseDateMoment
} from '../../util/timeRangeUtil';
import {
	parse,
	stringify
} from '../../../../shared/components/router/queryString';
import {useContext, useState} from 'react';
import {pushToHistory} from '../../../../shared/components/filter/util/filterUtil';
import {TimeRangeContext} from './TimeRangeStore';
import {useRouter} from '../../../../shared/components/router/useRouter';

const useCustomTimeRange = filterKey => {
	const [errors, setErrors] = useState(undefined);
	const {getSelectedTimeRange, setTimeRanges, timeRanges} = useContext(
		TimeRangeContext
	);
	const routerProps = useRouter();

	const selectedTimeRange = getSelectedTimeRange() || {};

	const [dateEnd, setDateEnd] = useState(
		formatDate(selectedTimeRange.dateEnd)
	);
	const [dateStart, setDateStart] = useState(
		formatDate(selectedTimeRange.dateStart)
	);

	const applyCustomFilter = () => {
		if (!errors) {
			setTimeRanges([
				{
					...timeRanges[0],
					active: true,
					dateEnd: parseDate(dateEnd, true),
					dateStart: parseDate(dateStart)
				},
				...timeRanges.slice(1, timeRanges.length).map(item => ({
					...item,
					active: false
				}))
			]);

			updateQueryString();
		}
	};

	const updateErrors = (errors, fieldName, message) => ({
		...(errors || {}),
		[fieldName]: message
	});

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
		const dateEndMoment = parseDateMoment(dateEnd);
		const dateNowMoment = new Date();
		const dateStartMoment = parseDateMoment(dateStart);
		let errors;

		if (!dateEndMoment.isValid() || dateEndMoment.isAfter(dateNowMoment)) {
			errors = updateErrors(
				errors,
				'dateEnd',
				Liferay.Language.get('please-enter-a-valid-date')
			);
		}

		if (
			!dateStartMoment.isValid() ||
			dateStartMoment.isAfter(dateNowMoment)
		) {
			errors = updateErrors(
				errors,
				'dateStart',
				Liferay.Language.get('please-enter-a-valid-date')
			);
		}

		if (errors) {
			setErrors(errors);

			return errors;
		}

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

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import className from 'classnames';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useMemo, useRef} from 'react';

import {
	ChartDispatchContext,
	ChartStateContext,
	useDateTitle,
	useIsPreviousPeriodButtonDisabled,
} from '../../context/ChartStateContext';
import ConnectionContext from '../../context/ConnectionContext';
import {
	StoreDispatchContext,
	StoreStateContext,
} from '../../context/StoreContext';
import {generateDateFormatters as dateFormat} from '../../utils/dateFormat';
import TimeSpanSelector from '../TimeSpanSelector';
import TotalCount from '../TotalCount';
import Countries from './Countries';

export default function CountriesDetail({
	currentPage,
	handleDetailPeriodChange,
	timeSpanOptions,
	trafficShareDataProvider,
	trafficSourcesDataProvider,
	trafficVolumeDataProvider,
}) {
	const {languageTag} = useContext(StoreStateContext);

	const dateFormatters = useMemo(() => dateFormat(languageTag), [
		languageTag,
	]);

	const {firstDate, lastDate} = useDateTitle();

	const title = useMemo(() => {
		return dateFormatters.formatChartTitle([firstDate, lastDate]);
	}, [dateFormatters, firstDate, lastDate]);

	const dispatch = useContext(StoreDispatchContext);

	const chartDispatch = useContext(ChartDispatchContext);

	const {pieChartLoading, timeSpanKey, timeSpanOffset} = useContext(
		ChartStateContext
	);

	const {validAnalyticsConnection} = useContext(ConnectionContext);

	const isPreviousPeriodButtonDisabled = useIsPreviousPeriodButtonDisabled();

	const firstUpdateRef = useRef(true);

	const trafficSourceDetailClasses = className(
		'c-p-3 traffic-source-detail',
		{
			'traffic-source-detail--loading': pieChartLoading,
		}
	);

	useEffect(() => {
		if (firstUpdateRef.current) {
			firstUpdateRef.current = false;

			return;
		}

		if (validAnalyticsConnection) {
			chartDispatch({
				payload: {
					loading: true,
				},
				type: 'SET_PIE_CHART_LOADING',
			});

			trafficSourcesDataProvider()
				.then((trafficSources) => {
					handleDetailPeriodChange(
						trafficSources,
						currentPage.view,
						true
					);
				})
				.catch(() => {
					dispatch({type: 'ADD_WARNING'});
				})
				.finally(() => {
					chartDispatch({
						payload: {
							loading: false,
						},
						type: 'SET_PIE_CHART_LOADING',
					});
				});
		}
	}, [
		chartDispatch,
		currentPage.view,
		dispatch,
		handleDetailPeriodChange,
		timeSpanKey,
		timeSpanOffset,
		trafficSourcesDataProvider,
		validAnalyticsConnection,
	]);

	return (
		<div className={trafficSourceDetailClasses}>
			<div className="c-mb-3 c-mt-2">
				<TimeSpanSelector
					disabledNextTimeSpan={timeSpanOffset === 0}
					disabledPreviousPeriodButton={
						isPreviousPeriodButtonDisabled
					}
					timeSpanKey={timeSpanKey}
					timeSpanOptions={timeSpanOptions}
				/>
			</div>

			{title && <h5 className="c-mb-4">{title}</h5>}

			<TotalCount
				className="mb-2"
				dataProvider={trafficVolumeDataProvider}
				label={sub(Liferay.Language.get('traffic-volume'))}
				popoverHeader={Liferay.Language.get('traffic-volume')}
				popoverMessage={Liferay.Language.get(
					'traffic-volume-is-the-number-of-page-views-coming-from-one-channel'
				)}
				popoverPosition="bottom"
			/>

			<TotalCount
				className="mb-4"
				dataProvider={trafficShareDataProvider}
				label={sub(Liferay.Language.get('traffic-share'))}
				percentage={true}
				popoverHeader={Liferay.Language.get('traffic-share')}
				popoverMessage={Liferay.Language.get(
					'traffic-share-is-the-percentage-of-traffic-sent-to-your-page-by-one-channel'
				)}
			/>

			<Countries currentPage={currentPage} />
		</div>
	);
}

CountriesDetail.propTypes = {
	currentPage: PropTypes.object.isRequired,
	handleDetailPeriodChange: PropTypes.func.isRequired,
	timeSpanOptions: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string,
			label: PropTypes.string,
		})
	).isRequired,
	trafficShareDataProvider: PropTypes.func.isRequired,
	trafficSourcesDataProvider: PropTypes.func.isRequired,
	trafficVolumeDataProvider: PropTypes.func.isRequired,
};

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

import PropTypes from 'prop-types';
import React, {useContext, useMemo} from 'react';

import BasicInformation from './BasicInformation';
import Chart from './Chart';
import TimeSpanSelector from './TimeSpanSelector';
import TotalCount from './TotalCount';
import TrafficSources from './TrafficSources';

import {
	ChartStateContext,
	useDateTitle,
	useIsPreviousPeriodButtonDisabled,
} from '../context/ChartStateContext';
import {StoreStateContext} from '../context/StoreContext';

import {generateDateFormatters as dateFormat} from '../utils/dateFormat';

export default function Main({
	author,
	canonicalURL,
	chartDataProviders,
	onSelectedLanguageClick,
	onTrafficSourceClick,
	pagePublishDate,
	pageTitle,
	timeSpanOptions,
	totalReadsDataProvider,
	totalViewsDataProvider,
	trafficSourcesDataProvider,
	viewURLs,
}) {
	const {timeSpanKey, timeSpanOffset} = useContext(ChartStateContext);
	const {languageTag} = useContext(StoreStateContext);

	const isPreviousPeriodButtonDisabled = useIsPreviousPeriodButtonDisabled();

	const {firstDate, lastDate} = useDateTitle();

	const dateFormatters = useMemo(
		() => dateFormat(languageTag),
		[languageTag]
	);

	const title = dateFormatters.formatChartTitle([firstDate, lastDate]);

	return (
		<div className="pb-3 px-3">
			<BasicInformation
				author={author}
				canonicalURL={canonicalURL}
				onSelectedLanguageClick={onSelectedLanguageClick}
				publishDate={pagePublishDate}
				title={pageTitle}
				viewURLs={viewURLs}
			/>

			{!!timeSpanOptions.length && (
				<div className="c-mb-2 c-mt-4">
					<TimeSpanSelector
						disabledNextTimeSpan={timeSpanOffset === 0}
						disabledPreviousPeriodButton={
							isPreviousPeriodButtonDisabled
						}
						timeSpanKey={timeSpanKey}
						timeSpanOptions={timeSpanOptions}
					/>
				</div>
			)}
			{title && <h5 className="c-mb-4">{title}</h5>}

			<h5 className="mt-3 sheet-subtitle">
				{Liferay.Language.get('engagement')}
			</h5>

			<TotalCount
				className="mb-2"
				dataProvider={totalViewsDataProvider}
				label={Liferay.Util.sub(Liferay.Language.get('total-views'))}
				popoverHeader={Liferay.Language.get('total-views')}
				popoverMessage={Liferay.Language.get(
					'this-number-refers-to-the-total-number-of-views-since-the-content-was-published'
				)}
			/>

			{totalReadsDataProvider && (
				<TotalCount
					dataProvider={totalReadsDataProvider}
					label={Liferay.Util.sub(
						Liferay.Language.get('total-reads')
					)}
					popoverHeader={Liferay.Language.get('total-reads')}
					popoverMessage={Liferay.Language.get(
						'this-number-refers-to-the-total-number-of-reads-since-the-content-was-published'
					)}
				/>
			)}

			<Chart
				dataProviders={chartDataProviders}
				publishDate={pagePublishDate}
				timeSpanOptions={timeSpanOptions}
			/>

			<TrafficSources
				dataProvider={trafficSourcesDataProvider}
				onTrafficSourceClick={onTrafficSourceClick}
			/>
		</div>
	);
}

Main.propTypes = {
	author: PropTypes.object.isRequired,
	canonicalURL: PropTypes.string.isRequired,
	chartDataProviders: PropTypes.arrayOf(PropTypes.func.isRequired).isRequired,
	onSelectedLanguageClick: PropTypes.func.isRequired,
	onTrafficSourceClick: PropTypes.func.isRequired,
	pagePublishDate: PropTypes.string.isRequired,
	pageTitle: PropTypes.string.isRequired,
	timeSpanOptions: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string,
			label: PropTypes.string,
		})
	).isRequired,
	totalReadsDataProvider: PropTypes.func.isRequired,
	totalViewsDataProvider: PropTypes.func.isRequired,
	trafficSourcesDataProvider: PropTypes.func.isRequired,
	viewURLs: PropTypes.arrayOf(
		PropTypes.shape({
			default: PropTypes.bool.isRequired,
			languageId: PropTypes.string.isRequired,
			languageLabel: PropTypes.string.isRequired,
			selected: PropTypes.bool.isRequired,
			viewURL: PropTypes.string.isRequired,
		})
	).isRequired,
};

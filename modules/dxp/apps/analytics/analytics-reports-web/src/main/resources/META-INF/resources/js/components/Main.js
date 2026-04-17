/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useContext, useMemo, useState} from 'react';

import {
	ChartStateContext,
	useDateTitle,
	useIsPreviousPeriodButtonDisabled,
} from '../context/ChartStateContext';
import {StoreStateContext} from '../context/StoreContext';
import {generateDateFormatters as dateFormat} from '../utils/dateFormat';
import BasicInformation from './BasicInformation';
import Chart from './Chart';
import ExperienceDropdown from './ExperienceDropdown';
import TimeSpanSelector from './TimeSpanSelector';
import TotalCount from './TotalCount';
import TrafficSources from './TrafficSources';

export default function Main({
	author,
	canonicalURL,
	chartDataProviders,
	className,
	experiencesDataProvider,
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

	const [showAlert, setShowAlert] = useState(true);

	return (
		<div className={`analytics-reports-app-main pb-3 px-3 ${className}`}>
			<BasicInformation
				author={author}
				canonicalURL={canonicalURL}
				onSelectedLanguageClick={onSelectedLanguageClick}
				publishDate={pagePublishDate}
				title={pageTitle}
				viewURLs={viewURLs}
			/>

			<div>
				{showAlert && (
					<ClayAlert
						displayType="info"
						onClose={() => setShowAlert(false)}
						role={null}
						title={Liferay.Language.get('info')}
						variant="inline"
					>
						{Liferay.Language.get(
							'the-experience-filter-does-not-affect-the-displayed-page-as-it-uses-historical-data-that-may-include-deleted-experiences'
						)}
					</ClayAlert>
				)}
			</div>

			<div className="c-mt-2">
				<ExperienceDropdown
					experiencesDataProvider={experiencesDataProvider}
				/>
			</div>

			{!!timeSpanOptions.length && (
				<div className="c-mb-2 c-mt-2">
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

			{title && <div className="c-mb-4 h5">{title}</div>}

			<div className="mt-3 sheet-subtitle">
				{Liferay.Language.get('engagement')}
			</div>

			<TotalCount
				className="mb-2"
				dataProvider={totalViewsDataProvider}
				label={sub(Liferay.Language.get('total-views'))}
				popoverHeader={Liferay.Language.get('total-views')}
				popoverMessage={Liferay.Language.get(
					'this-number-refers-to-the-total-number-of-views-since-the-content-was-published'
				)}
			/>

			{totalReadsDataProvider && (
				<TotalCount
					className="mb-2"
					dataProvider={totalReadsDataProvider}
					label={sub(Liferay.Language.get('total-reads'))}
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

Main.defaultProps = {
	author: null,
	className: '',
	totalReadsDataProvider: null,
};

Main.propTypes = {
	author: PropTypes.object,
	canonicalURL: PropTypes.string.isRequired,
	chartDataProviders: PropTypes.arrayOf(PropTypes.func.isRequired).isRequired,
	className: PropTypes.string,
	experiencesDataProvider: PropTypes.func.isRequired,
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
	totalReadsDataProvider: PropTypes.func,
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

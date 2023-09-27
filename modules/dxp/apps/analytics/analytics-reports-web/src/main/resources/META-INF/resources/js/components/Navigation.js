/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {
	useCallback,
	useContext,
	useEffect,
	useRef,
	useState,
} from 'react';

import {ChartStateContext} from '../context/ChartStateContext';
import ConnectionContext from '../context/ConnectionContext';
import {StoreStateContext} from '../context/StoreContext';
import APIService from '../utils/APIService';
import Main from './Main';
import Detail from './detail/Detail';
import Drawer from './detail/Drawer';

const noop = () => {};

export default function Navigation({
	author,
	canonicalURL,
	onSelectedLanguageClick = noop,
	pagePublishDate,
	pageTitle,
	timeSpanOptions,
	viewURLs,
}) {
	const {endpoints, namespace, page, publishedToday, warning} = useContext(
		StoreStateContext
	);

	const {validAnalyticsConnection} = useContext(ConnectionContext);

	const [currentPage, setCurrentPage] = useState({view: 'main'});

	const [loadingDetailView, setLoadingDetailView] = useState(false);

	const [trafficSources, setTrafficSources] = useState([]);

	const [trafficSourceName, setTrafficSourceName] = useState('');

	const {timeSpanKey, timeSpanOffset} = useContext(ChartStateContext);

	const detailRef = useRef(null);

	const handleCurrentPage = useCallback((currentPage) => {
		setCurrentPage({view: currentPage.view});
	}, []);

	const handleHistoricalReads = useCallback(() => {
		return APIService.getHistoricalReads(
			endpoints.analyticsReportsHistoricalReadsURL,
			{namespace, plid: page.plid, timeSpanKey, timeSpanOffset}
		).then((response) => response);
	}, [
		endpoints.analyticsReportsHistoricalReadsURL,
		namespace,
		page.plid,
		timeSpanKey,
		timeSpanOffset,
	]);

	const handleHistoricalViews = useCallback(() => {
		return APIService.getHistoricalReads(
			endpoints.analyticsReportsHistoricalViewsURL,
			{namespace, plid: page.plid, timeSpanKey, timeSpanOffset}
		).then((response) => response);
	}, [
		endpoints.analyticsReportsHistoricalViewsURL,
		namespace,
		page.plid,
		timeSpanKey,
		timeSpanOffset,
	]);

	const handleTotalReads = useCallback(() => {
		return APIService.getTotalReads(
			endpoints.analyticsReportsTotalReadsURL,
			{namespace, plid: page.plid}
		).then(({analyticsReportsTotalReads}) => analyticsReportsTotalReads);
	}, [endpoints.analyticsReportsTotalReadsURL, namespace, page.plid]);

	const handleTotalViews = useCallback(() => {
		return APIService.getTotalReads(
			endpoints.analyticsReportsTotalViewsURL,
			{namespace, plid: page.plid}
		).then(({analyticsReportsTotalViews}) => analyticsReportsTotalViews);
	}, [endpoints.analyticsReportsTotalViewsURL, namespace, page.plid]);

	const handleTrafficSources = useCallback(() => {
		return APIService.getTrafficSources(
			endpoints.analyticsReportsTrafficSourcesURL,
			{namespace, plid: page.plid, timeSpanKey, timeSpanOffset}
		).then(({trafficSources}) => trafficSources);
	}, [
		endpoints.analyticsReportsTrafficSourcesURL,
		namespace,
		page.plid,
		timeSpanKey,
		timeSpanOffset,
	]);

	const updateTrafficSourcesAndCurrentPage = useCallback(
		(trafficSources, trafficSourceName, sameTrafficSource) => {
			setTrafficSources(trafficSources);
			setTrafficSourceName(trafficSourceName);

			const trafficSource = trafficSources.find(
				(source) => source.name === trafficSourceName
			);

			if (!sameTrafficSource) {
				setLoadingDetailView(true);
			}
			setCurrentPage({
				data: sameTrafficSource ? currentPage.data : null,
				view: trafficSourceName,
			});
			APIService.getTrafficSources(trafficSource.endpointURL, {
				namespace,
				plid: page.plid,
				timeSpanKey,
				timeSpanOffset,
			}).then((response) => {
				response.title = trafficSource.title;

				setCurrentPage({
					data: response,
					view: trafficSourceName,
				});
				if (!sameTrafficSource) {
					setLoadingDetailView(false);
				}
			});
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[namespace, page.plid, timeSpanKey, timeSpanOffset]
	);

	const handleTrafficSourceName = (trafficSourceName) =>
		setTrafficSourceName(trafficSourceName);

	const handleTrafficShare = useCallback(() => {
		const trafficSource = trafficSources.find((trafficSource) => {
			return trafficSource.name === trafficSourceName;
		});

		return Promise.resolve(trafficSource?.share ?? '-');
	}, [trafficSourceName, trafficSources]);

	const handleTrafficVolume = useCallback(() => {
		const trafficSource = trafficSources.find((trafficSource) => {
			return trafficSource.name === trafficSourceName;
		});

		return Promise.resolve(trafficSource?.value ?? '-');
	}, [trafficSourceName, trafficSources]);

	const showDetail = currentPage.view !== 'main';

	useEffect(() => {
		if (showDetail && !loadingDetailView) {
			detailRef.current.scrollIntoView();
		}
	}, [showDetail, loadingDetailView]);

	return (
		<>
			{!validAnalyticsConnection && (
				<ClayAlert
					className="mb-3"
					displayType="danger"
					variant="stripe"
				>
					{Liferay.Language.get('an-unexpected-error-occurred')}
				</ClayAlert>
			)}

			{validAnalyticsConnection && warning && (
				<ClayAlert
					className="mb-3"
					displayType="warning"
					variant="stripe"
				>
					{Liferay.Language.get(
						'some-data-is-temporarily-unavailable'
					)}
				</ClayAlert>
			)}

			{validAnalyticsConnection && publishedToday && !warning && (
				<ClayAlert
					className="mb-3"
					displayType="info"
					title={Liferay.Language.get('no-data-is-available-yet')}
					variant="stripe"
				>
					{Liferay.Language.get('content-has-just-been-published')}
				</ClayAlert>
			)}

			<Main
				author={author}
				canonicalURL={canonicalURL}
				chartDataProviders={
					endpoints.analyticsReportsHistoricalReadsURL
						? [handleHistoricalViews, handleHistoricalReads]
						: [handleHistoricalViews]
				}
				className={classnames({
					'analytics-reports-app-main--hide': showDetail,
				})}
				onSelectedLanguageClick={onSelectedLanguageClick}
				onTrafficSourceClick={updateTrafficSourcesAndCurrentPage}
				pagePublishDate={pagePublishDate}
				pageTitle={pageTitle}
				timeSpanOptions={timeSpanOptions}
				totalReadsDataProvider={
					endpoints.analyticsReportsTotalReadsURL && handleTotalReads
				}
				totalViewsDataProvider={handleTotalViews}
				trafficSourcesDataProvider={handleTrafficSources}
				viewURLs={viewURLs}
			/>

			{showDetail && (
				<Drawer>
					<Detail
						currentPage={currentPage}
						handleDetailPeriodChange={
							updateTrafficSourcesAndCurrentPage
						}
						loadingData={loadingDetailView}
						onCurrentPageChange={handleCurrentPage}
						onTrafficSourceNameChange={handleTrafficSourceName}
						refProp={detailRef}
						timeSpanOptions={timeSpanOptions}
						trafficShareDataProvider={handleTrafficShare}
						trafficSourcesDataProvider={handleTrafficSources}
						trafficVolumeDataProvider={handleTrafficVolume}
					/>
				</Drawer>
			)}
		</>
	);
}

Navigation.defaultProps = {
	author: null,
};

Navigation.propTypes = {
	author: PropTypes.object,
	canonicalURL: PropTypes.string.isRequired,
	onSelectedLanguageClick: PropTypes.func.isRequired,
	pagePublishDate: PropTypes.string.isRequired,
	pageTitle: PropTypes.string.isRequired,
	timeSpanOptions: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string.isRequired,
			label: PropTypes.string.isRequired,
		})
	).isRequired,
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

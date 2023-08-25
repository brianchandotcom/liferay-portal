/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';

import SegmentsExperimentsContext from '../context.es';
import APIService from '../util/APIService.es';
import ConnectToAC from './ConnectToAC.es';
import SegmentsExperimentsSidebar from './SegmentsExperimentsSidebar.es';

const useExperimentsData = (eventTriggered, fetchDataURL, isPanelStateOpen) => {
	const [loading, setLoading] = useState(false);
	const [data, setData] = useState({context: null, props: null});

	const componentHasData = !!Object.values(data).filter(Boolean).length;

	const fetchExperimentsData = useCallback(async () => {
		try {
			setLoading(true);
			const response = await fetch(fetchDataURL);

			if (!response.ok) {
				throw new Error(`Failed to fetch ${fetchDataURL}`);
			}
			const {context, props} = await response.json();
			setData({context, props});
		}
		catch (error) {
			if (process.env.NODE_ENV === 'development') {
				console.error(error);
			}
		}
		finally {
			setLoading(false);
		}
	}, [fetchDataURL]);

	useEffect(() => {
		if ((isPanelStateOpen || eventTriggered) && !componentHasData) {
			fetchExperimentsData();
		}
	}, [
		componentHasData,
		eventTriggered,
		fetchExperimentsData,
		isPanelStateOpen,
	]);

	return [loading, data];
};

const SegmentsExperimentsMain = ({
	eventTriggered,
	fetchDataURL,
	isPanelStateOpen,
}) => {
	const [loading, data] = useExperimentsData(
		eventTriggered,
		fetchDataURL,
		isPanelStateOpen
	);

	const {context, props} = data;

	if (loading) {
		return <ClayLoadingIndicator className="my-6" />;
	}
	else if (!context || !props) {
		return null;
	}

	const isAnalyticsSync = props?.analyticsData?.isSynced;
	const {endpoints, imagesPath, page} = context;
	const {
		calculateSegmentsExperimentEstimatedDurationURL,
		createSegmentsExperimentURL,
		createSegmentsVariantURL,
		deleteSegmentsExperimentURL,
		deleteSegmentsVariantURL,
		editSegmentsExperimentStatusURL,
		editSegmentsExperimentURL,
		editSegmentsVariantLayoutURL,
		editSegmentsVariantURL,
		runSegmentsExperimentURL,
	} = endpoints;

	return isAnalyticsSync ? (
		<SegmentsExperimentsContext.Provider
			value={{
				APIService: APIService({
					contentPageEditorNamespace:
						context.contentPageEditorNamespace,
					endpoints: {
						calculateSegmentsExperimentEstimatedDurationURL,
						createSegmentsExperimentURL,
						createSegmentsVariantURL,
						deleteSegmentsExperimentURL,
						deleteSegmentsVariantURL,
						editSegmentsExperimentStatusURL,
						editSegmentsExperimentURL,
						editSegmentsVariantURL,
						runSegmentsExperimentURL,
					},
					namespace: context.namespace,
				}),
				editVariantLayoutURL: editSegmentsVariantLayoutURL,
				imagesPath,
				page,
			}}
		>
			<SegmentsExperimentsSidebar
				initialGoals={props.segmentsExperimentGoals}
				initialSegmentsExperiment={props.segmentsExperiment}
				initialSegmentsVariants={props.initialSegmentsVariants}
				initialSelectedSegmentsExperienceId={
					props.selectedSegmentsExperienceId
				}
				winnerSegmentsVariantId={props.winnerSegmentsVariantId}
			/>
		</SegmentsExperimentsContext.Provider>
	) : (
		<ConnectToAC
			analyticsCloudTrialURL={props.analyticsData?.cloudTrialURL}
			analyticsURL={props.analyticsData?.url}
			hideAnalyticsReportsPanelURL={props.hideSegmentsExperimentPanelURL}
			isAnalyticsConnected={props.analyticsData?.isConnected}
			pathToAssets={props.pathToAssets}
		/>
	);
};

SegmentsExperimentsMain.propTypes = {
	eventTriggered: PropTypes.bool,
	fetchDataURL: PropTypes.string,
	isPanelStateOpen: PropTypes.bool,
};

export default SegmentsExperimentsMain;

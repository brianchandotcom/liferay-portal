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
				initialExperimentHistory={props.historySegmentsExperiments}
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

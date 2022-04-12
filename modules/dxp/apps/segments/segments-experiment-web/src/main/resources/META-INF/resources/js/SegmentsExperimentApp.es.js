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

import React from 'react';

import ConnectToAC from './components/ConnectToAC.es';
import SegmentsExperimentsSidebar from './components/SegmentsExperimentsSidebar.es';
import SegmentsExperimentsContext from './context.es';
import APIService from './util/APIService.es';

export default function ({context, portletNamespace, props}) {
	const isAnalyticsSync = false;
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
			<div id={`${portletNamespace}-segments-experiment-root`}>
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
			</div>
		</SegmentsExperimentsContext.Provider>
	) : (
		<ConnectToAC />
	);
}

import React from 'react';
import APIService from './util/APIService.es';
import SegmentsExperimentsSidebar from './components/SegmentsExperimentsSidebar.es';
import SegmentsExperimentsContext from './context.es';

export default function({props, context}) {
	const {assetsPath, endpoints, page} = context;
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
		runSegmentsExperimentURL
	} = endpoints;

	return (
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
						runSegmentsExperimentURL
					},
					namespace: context.namespace
				}),
				assetsPath,
				editVariantLayoutURL: editSegmentsVariantLayoutURL,
				page
			}}
		>
			<SegmentsExperimentsSidebar
				initialExperimentHistory={props.historySegmentsExperiments}
				initialGoals={props.segmentsExperimentGoals}
				initialSegmentsExperiences={props.segmentsExperiences}
				initialSegmentsExperiment={props.segmentsExperiment}
				initialSegmentsVariants={props.initialSegmentsVariants}
				initialSelectedSegmentsExperienceId={
					props.selectedSegmentsExperienceId
				}
				viewSegmentsExperimentDetailsURL={
					props.viewSegmentsExperimentDetailsURL
				}
				winnerSegmentsVariantId={props.winnerSegmentsVariantId}
			/>
		</SegmentsExperimentsContext.Provider>
	);
}

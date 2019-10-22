import React from 'react';
import {render} from '@testing-library/react';
import {segmentsGoals, DEFAULT_ESTIMATED_DAYS} from './fixtures.es';
import SegmentsExperimentsSidebar from '../../src/main/resources/META-INF/resources/js/components/SegmentsExperimentsSidebar.es';
import SegmentsExperimentsContext from '../../src/main/resources/META-INF/resources/js/context.es';

export default function renderApp({
	classNameId = '',
	classPK = '',
	initialGoals = segmentsGoals,
	initialExperimentHistory = [],
	initialSegmentsExperiences = [],
	initialSegmentsExperiment,
	initialSegmentsVariants = [],
	APIService = {},
	selectedSegmentsExperienceId,
	type = 'content',
	winnerSegmentsVariantId = null
} = {}) {
	const {
		createExperiment = () => {},
		createVariant = jest.fn(_createVariantMock),
		deleteVariant = () => {},
		editExperiment = () => {},
		editExperimentStatus = jest.fn(
			_editExperimentStatusMockGenerator(initialSegmentsExperiment)
		),
		editVariant = () => {},
		getEstimatedTime = jest.fn(_getEstimatedTimeMock),
		publishExperience = jest.fn(
			_publishExperienceMockGenerator(initialSegmentsExperiment)
		),
		runExperiment = jest.fn(
			_runExperimentMockGenerator(initialSegmentsExperiment)
		)
	} = APIService;

	const renderMethods = render(
		<SegmentsExperimentsContext.Provider
			value={{
				APIService: {
					createExperiment,
					createVariant,
					deleteVariant,
					editExperiment,
					editExperimentStatus,
					editVariant,
					getEstimatedTime,
					publishExperience,
					runExperiment
				},
				assetsPath: '',
				page: {
					classNameId,
					classPK,
					type
				}
			}}
		>
			<SegmentsExperimentsSidebar
				initialExperimentHistory={initialExperimentHistory}
				initialGoals={initialGoals}
				initialSegmentsExperiences={initialSegmentsExperiences}
				initialSegmentsExperiment={initialSegmentsExperiment}
				initialSegmentsVariants={initialSegmentsVariants}
				selectedSegmentsExperienceId={selectedSegmentsExperienceId}
				winnerSegmentsVariantId={winnerSegmentsVariantId}
			/>
		</SegmentsExperimentsContext.Provider>,
		{
			baseElement: document.body
		}
	);

	return {
		...renderMethods,
		APIServiceMocks: {
			createVariant,
			editExperimentStatus,
			getEstimatedTime,
			publishExperience,
			runExperiment
		}
	};
}

/*
 * A default mock of the APIService createVariant service.
 */
const _createVariantMock = variant =>
	Promise.resolve({
		segmentsExperimentRel: {
			name: variant.name,
			segmentsExperienceId: JSON.stringify(Math.random()),
			segmentsExperimentId: JSON.stringify(Math.random()),
			segmentsExperimentRelId: JSON.stringify(Math.random()),
			split: 0.0
		}
	});

const _getEstimatedTimeMock = () =>
	Promise.resolve({
		segmentsExperimentEstimatedDaysDuration: DEFAULT_ESTIMATED_DAYS.value
	});

const _publishExperienceMockGenerator = experiment => ({
	status,
	winnerSegmentsExperienceId
}) =>
	Promise.resolve({
		segmentsExperiment: {
			...experiment,
			status: {
				label: 'completed',
				value: status
			}
		},
		winnerSegmentsExperienceId
	});

const _runExperimentMockGenerator = segmentsExperiment => ({status}) =>
	Promise.resolve({
		segmentsExperiment: {
			...segmentsExperiment,
			editable: false,
			status: {label: 'running', value: status}
		}
	});

const _editExperimentStatusMockGenerator = experiment => ({status}) => {
	return Promise.resolve({
		segmentsExperiment: {
			...experiment,
			status: {
				value: status
			}
		}
	});
};

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

import {render} from '@testing-library/react';
import React from 'react';

import SegmentsExperimentsSidebar from '../../src/main/resources/META-INF/resources/js/components/SegmentsExperimentsSidebar.es';
import SegmentsExperimentsContext from '../../src/main/resources/META-INF/resources/js/context.es';
import {DEFAULT_ESTIMATED_DAYS, segmentsGoals} from './fixtures.es';

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

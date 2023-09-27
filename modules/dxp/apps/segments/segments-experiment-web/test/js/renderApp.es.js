/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import SegmentsExperimentsSidebar from '../../src/main/resources/META-INF/resources/js/components/SegmentsExperimentsSidebar.es';
import SegmentsExperimentsContext from '../../src/main/resources/META-INF/resources/js/context.es';
import {DEFAULT_ESTIMATED_DAYS, segmentsGoals} from './fixtures.es';

/*
 * A default mock of the APIService createVariant service.
 */
const _createVariantMock = (variant) =>
	Promise.resolve({
		segmentsExperimentRel: {
			name: variant.name,
			segmentsExperienceId: JSON.stringify(Math.random()),
			segmentsExperimentId: JSON.stringify(Math.random()),
			segmentsExperimentRelId: JSON.stringify(Math.random()),
			split: 0.0,
		},
	});

const _editExperimentStatusMockGenerator = (experiment) => ({status}) => {
	return Promise.resolve({
		segmentsExperiment: {
			...experiment,
			status: {
				value: status,
			},
		},
	});
};

const _getEstimatedTimeMock = () =>
	Promise.resolve({
		segmentsExperimentEstimatedDaysDuration: DEFAULT_ESTIMATED_DAYS.value,
	});

const _publishExperienceMockGenerator = (experiment) => ({
	status,
	winnerSegmentsExperienceId,
}) =>
	Promise.resolve({
		segmentsExperiment: {
			...experiment,
			status: {
				label: 'completed',
				value: status,
			},
		},
		winnerSegmentsExperienceId,
	});

const _runExperimentMockGenerator = (segmentsExperiment) => ({status}) =>
	Promise.resolve({
		segmentsExperiment: {
			...segmentsExperiment,
			editable: false,
			status: {label: 'running', value: status},
		},
	});

export default function renderApp({
	APIService = {},
	initialGoals = segmentsGoals,
	initialSegmentsExperiment,
	initialSegmentsVariants = [],
	plid = '',
	selectedSegmentsExperienceId,
	type = 'content',
	winnerSegmentsVariantId = null,
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
		),
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
					runExperiment,
				},
				imagesPath: '',
				page: {
					plid,
					type,
				},
			}}
		>
			<SegmentsExperimentsSidebar
				initialGoals={initialGoals}
				initialSegmentsExperiment={initialSegmentsExperiment}
				initialSegmentsVariants={initialSegmentsVariants}
				selectedSegmentsExperienceId={selectedSegmentsExperienceId}
				winnerSegmentsVariantId={winnerSegmentsVariantId}
			/>
		</SegmentsExperimentsContext.Provider>,
		{
			baseElement: document.body,
		}
	);

	return {
		...renderMethods,
		APIServiceMocks: {
			createVariant,
			editExperimentStatus,
			getEstimatedTime,
			publishExperience,
			runExperiment,
		},
	};
}

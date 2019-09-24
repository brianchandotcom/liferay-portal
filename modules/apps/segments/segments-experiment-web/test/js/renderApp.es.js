/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import React from 'react';
import {render} from '@testing-library/react';
import {segmentsGoals, DEFAULT_ESTIMATED_DAYS} from './fixtures.es';
import SegmentsExperimentsSidebar from '../../src/main/resources/META-INF/resources/js/components/SegmentsExperimentsSidebar.es';
import SegmentsExperimentsContext from '../../src/main/resources/META-INF/resources/js/context.es';

export default function renderApp({
	classNameId = '',
	classPK = '',
	initialGoals = segmentsGoals,
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
		editVariant = () => {},
		getEstimatedTime = jest.fn(_getEstimatedTimeMock),
		publishExperience = jest.fn(
			_publishExperienceMockGenerator(initialSegmentsExperiment)
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
					editVariant,
					getEstimatedTime,
					publishExperience
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
				initialExperimentHistory={[]}
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
			getEstimatedTime,
			publishExperience
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

const _publishExperienceMockGenerator = experiment => ({status}) =>
	Promise.resolve({
		segmentsExperiment: {
			...experiment,
			status: {
				label: 'completed',
				value: status
			}
		}
	});

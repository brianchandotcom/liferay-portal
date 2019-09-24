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
import {segmentsGoals} from './fixtures.es';
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
		createVariant = () => {},
		deleteVariant = () => {},
		editExperiment = () => {},
		editVariant = () => {},
		getEstimatedTime = () => Promise.resolve(),
		publishExperience = () => {}
	} = APIService;

	return render(
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
}

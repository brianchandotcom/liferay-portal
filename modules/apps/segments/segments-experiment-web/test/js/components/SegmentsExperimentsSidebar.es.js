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

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render} from '@testing-library/react';
import SegmentsExperimentsSidebar from '../../../src/main/resources/META-INF/resources/js/components/SegmentsExperimentsSidebar.es';
import SegmentsExperimentsContext from '../../../src/main/resources/META-INF/resources/js/context.es';
import React from 'react';

const segmentsExperiment = {
	name: 'Experiment 1',
	description: 'Experiment 1 description',
	segmentsExperimentId: '0',
	segmentsExperienceId: '0'
};

const segmentsExperiences = [
	{
		name: 'Default',
		description: 'Default',
		segmentsExperienceId: '0',
		segmentsExperiment
	},
	{
		name: 'Experience 1',
		description: 'Experience 1 description',
		segmentsExperienceId: '1',
		segmentsExperiment
	}
];

function _renderSegmentsExperimentsSidebarComponent({
	initialSegmentsExperiences = [],
	initialSegmentsExperiment,
	selectedSegmentsExperienceId,
	createSegmentsExperimentURL = '',
	editSegmentsExperimentURL = '',
	classNameId = '',
	classPK = '',
	type = 'content'
} = {}) {
	return render(
		<SegmentsExperimentsContext.Provider
			value={{
				endpoints: {
					createSegmentsExperimentURL,
					editSegmentsExperimentURL
				},
				page: {
					classNameId,
					classPK,
					type
				}
			}}
		>
			<SegmentsExperimentsSidebar
				initialSegmentsExperiences={initialSegmentsExperiences}
				initialSegmentsExperiment={initialSegmentsExperiment}
				selectedSegmentsExperienceId={selectedSegmentsExperienceId}
			/>
		</SegmentsExperimentsContext.Provider>
	);
}

describe('SegmentsExperimentsSidebar', () => {
	afterEach(cleanup);
});

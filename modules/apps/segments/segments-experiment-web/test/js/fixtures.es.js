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

export const segmentsExperiment = {
	name: 'Experiment 1',
	description: 'Experiment 1 description',
	segmentsExperimentId: '0',
	segmentsExperienceId: '0'
};

export const segmentsExperiences = [
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

export const segmentsVariants = [
	{
		name: 'Control',
		segmentsExperienceId: '0',
		segmentsExperimentId: '0',
		segmentsExperimentRelId: '123'
	},
	{
		name: 'Variant 1',
		segmentsExperienceId: '40',
		segmentsExperimentId: '0',
		segmentsExperimentRelId: '124'
	}
];

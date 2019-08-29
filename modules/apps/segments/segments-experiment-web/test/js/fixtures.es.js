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
	confidenceLevel: 0,
	description: 'Experiment 1 description',
	editable: true,
	goal: {
		label: 'Time On Page',
		target: '',
		value: 'time-on-page'
	},
	name: 'Experiment 1',
	segmentsEntryName: 'Segment name',
	segmentsExperienceId: '0',
	segmentsExperimentId: '0',
	status: {
		label: 'Draft',
		value: 0
	}
};

export const segmentsExperiences = [
	{
		description: 'Default',
		name: 'Default',
		segmentsExperienceId: '0',
		segmentsExperiment
	},
	{
		description: 'Experience 1 description',
		name: 'Experience 1',
		segmentsExperienceId: '1',
		segmentsExperiment
	}
];

export const segmentsGoals = [
	{
		label: 'bounce-rate',
		value: 'Bounce Rate'
	},
	{
		label: 'click',
		value: 'Click'
	},
	{
		label: 'max-scroll-depth',
		value: 'Max Scroll Depth'
	},
	{
		label: 'time-on-page',
		value: 'Time On Page'
	}
];

export const segmentsVariants = [
	{
		control: true,
		name: 'Control',
		segmentsExperienceId: '0',
		segmentsExperimentId: '0',
		segmentsExperimentRelId: '123',
		split: 0.0
	},
	{
		control: false,
		name: 'Variant 1',
		segmentsExperienceId: '40',
		segmentsExperimentId: '0',
		segmentsExperimentRelId: '124',
		split: 0.0
	}
];

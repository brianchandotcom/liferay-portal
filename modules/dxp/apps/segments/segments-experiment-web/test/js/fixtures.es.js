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

export const controlVariant = [
	{
		control: true,
		name: 'Control',
		segmentsExperienceId: '0',
		segmentsExperimentId: '0',
		segmentsExperimentRelId: '123',
		split: 0.0
	}
];

export const segmentsExperiment = {
	confidenceLevel: 0,
	description: 'Experiment 1 description',
	editable: true,
	goal: {
		label: 'Bounce Rate',
		target: '',
		value: 'bounce-rate'
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
		split: 50.0,
		winner: false
	},
	{
		control: false,
		name: 'Variant 1',
		segmentsExperienceId: '1',
		segmentsExperimentId: '0',
		segmentsExperimentRelId: '124',
		split: 50.0,
		winner: true
	}
];

/*
 * Default values used by the tests in assertions and mocked responses
 */
export const DEFAULT_ESTIMATED_DAYS = {
	message: '14-days',
	value: 14
};

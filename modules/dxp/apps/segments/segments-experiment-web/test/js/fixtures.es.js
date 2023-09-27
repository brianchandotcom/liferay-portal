/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const controlVariant = {
	control: true,
	name: 'Control',
	segmentsExperienceId: '0',
	segmentsExperimentId: '0',
	segmentsExperimentRelId: '123',
	split: 0.0,
};

export const variant = {
	control: false,
	name: 'Variant',
	segmentsExperienceId: '1',
	segmentsExperimentId: '0',
	segmentsExperimentRelId: '124',
	split: 50.0,
	winner: true,
};

export const segmentsExperiment = {
	confidenceLevel: 0,
	description: 'Experiment 1 description',
	detailsURL: 'https://analytics.liferay.com/',
	editable: true,
	goal: {
		label: 'Bounce Rate',
		value: 'bounce-rate',
	},
	name: 'Experiment 1',
	segmentsEntryName: 'Segment name',
	segmentsExperienceId: '0',
	segmentsExperimentId: '0',
	status: {
		label: 'Draft',
		value: 0,
	},
};

export const segmentsExperiences = [
	{
		description: 'Default',
		name: 'Default',
		segmentsExperienceId: '0',
		segmentsExperiment,
	},
	{
		description: 'Experience 1 description',
		name: 'Experience 1',
		segmentsExperienceId: '1',
		segmentsExperiment,
	},
];

export const segmentsGoals = [
	{
		label: 'bounce-rate',
		value: 'Bounce Rate',
	},
	{
		label: 'click',
		value: 'Click',
	},
	{
		label: 'max-scroll-depth',
		value: 'Max Scroll Depth',
	},
	{
		label: 'time-on-page',
		value: 'Time On Page',
	},
];

export const segmentsVariants = [controlVariant, variant];

/*
 * Default values used by the tests in assertions and mocked responses
 */
export const DEFAULT_ESTIMATED_DAYS = {
	message: '14-days',
	value: 14,
};

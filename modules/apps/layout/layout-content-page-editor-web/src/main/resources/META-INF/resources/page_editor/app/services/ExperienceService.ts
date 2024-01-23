/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutData} from '../../types/layout_data/LayoutData';
import {FragmentEntryLinkMap} from '../actions/addFragmentEntryLinks';
import {config} from '../config/index';
import serviceFetch from './serviceFetch';

export interface SegmentsExperience {
	active: boolean;
	hasLockedSegmentsExperiment?: boolean;
	name: string;
	priority: number;
	segmentsEntryId: string;
	segmentsExperienceId: string;
	segmentsExperimentURL?: string;
}

export type SegmentsExperienceMap = Record<string, SegmentsExperience>;

export default {
	createExperience({body}: {body: {name: string; segmentsEntryId: string}}) {
		return serviceFetch<{
			fragmentEntryLinks: FragmentEntryLinkMap;
			layoutData: LayoutData;
			segmentsExperience: SegmentsExperience;
		}>(config.addSegmentsExperienceURL, {
			body: {
				...body,
				active: true,
			},
		});
	},

	duplicateExperience({body}: {body: {segmentsExperienceId: string}}) {
		return serviceFetch<{
			fragmentEntryLinks: FragmentEntryLinkMap;
			layoutData: LayoutData;
			segmentsExperience: SegmentsExperience;
		}>(config.duplicateSegmentsExperienceURL, {body});
	},

	removeExperience({body}: {body: {segmentsExperienceId: string}}) {
		return serviceFetch<void>(config.deleteSegmentsExperienceURL, {body});
	},

	selectExperience({
		body,
	}: {
		body: {
			loadFragmentEntryLinks: boolean;
			segmentsExperienceId: string;
		};
	}) {
		return serviceFetch<{
			fragmentEntryLinks: FragmentEntryLinkMap;
			portletIds: string[];
		}>(config.getExperienceDataURL, {body});
	},

	updateExperience({
		body,
	}: {
		body: {
			active: boolean;
			name: string;
			segmentsEntryId: string;
			segmentsExperienceId: string;
		};
	}) {
		return serviceFetch<void>(config.updateSegmentsExperienceURL, {body});
	},

	updateExperiencePriority({
		body,
	}: {
		body: {
			newPriority: number;
			segmentsExperienceId: string;
		};
	}) {
		return serviceFetch<{
			availableSegmentsExperiences: SegmentsExperienceMap;
		}>(config.updateSegmentsExperiencePriorityURL, {body});
	},
};

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutData} from '../../types/layout_data/LayoutData';
import {FragmentEntryLinkMap} from '../actions/addFragmentEntryLinks';
export interface SegmentsExperience {
	active: boolean;
	hasLockedSegmentsExperiment?: boolean;
	name: string;
	priority: number;
	segmentsEntryId: string;
	segmentsExperienceId: string;
	segmentsExperimentURL?: string;
}
export declare type SegmentsExperienceMap = Record<string, SegmentsExperience>;
declare const _default: {
	createExperience({
		body,
	}: {
		body: {
			name: string;
			segmentsEntryId: string;
		};
	}): Promise<{
		fragmentEntryLinks: FragmentEntryLinkMap;
		layoutData: LayoutData;
		segmentsExperience: SegmentsExperience;
	}>;
	duplicateExperience({
		body,
	}: {
		body: {
			segmentsExperienceId: string;
		};
	}): Promise<{
		fragmentEntryLinks: FragmentEntryLinkMap;
		layoutData: LayoutData;
		segmentsExperience: SegmentsExperience;
	}>;
	removeExperience({
		body,
	}: {
		body: {
			segmentsExperienceId: string;
		};
	}): Promise<void>;
	selectExperience({
		body,
	}: {
		body: {
			loadFragmentEntryLinks: boolean;
			segmentsExperienceId: string;
		};
	}): Promise<{
		fragmentEntryLinks: FragmentEntryLinkMap;
		portletIds: string[];
	}>;
	updateExperience({
		body,
	}: {
		body: {
			active: boolean;
			name: string;
			segmentsEntryId: string;
			segmentsExperienceId: string;
		};
	}): Promise<void>;
	updateExperiencePriority({
		body,
	}: {
		body: {
			newPriority: number;
			segmentsExperienceId: string;
		};
	}): Promise<{
		availableSegmentsExperiences: SegmentsExperienceMap;
	}>;
};
export default _default;

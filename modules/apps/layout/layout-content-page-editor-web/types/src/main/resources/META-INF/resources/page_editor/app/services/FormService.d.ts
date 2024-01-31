/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OnNetworkStatus} from './draftServiceFetch';
import type {FormLayoutDataItem} from '../../types/layout_data/FormLayoutDataItem';
import type {LayoutData} from '../../types/layout_data/LayoutData';
import type {FragmentEntryLinkMap} from '../actions/addFragmentEntryLinks';
export interface FormField {
	key: string;
	label: string;
	name: string;
	required: boolean;
	type: string;
	typeLabel: string;
}
export interface FormFieldSet {
	fields: FormField[];
	label?: string;
}
declare const _default: {
	getFormConfig({
		classNameId,
	}: {
		classNameId: string;
	}): Promise<{
		supportStatus: boolean;
	}>;
	getFormFields({
		classNameId,
		classTypeId,
	}: {
		classNameId: string;
		classTypeId: string;
	}): Promise<FormFieldSet[]>;
	getFragmentEntryInputFieldTypes({
		fragmentEntryKey,
		groupId,
	}: {
		fragmentEntryKey: string;
		groupId?: string | undefined;
	}): Promise<string[]>;
	updateFormItemConfig({
		itemConfig,
		itemId,
		onNetworkStatus,
		segmentsExperienceId,
	}: {
		itemConfig: FormLayoutDataItem['config'];
		itemId: string;
		onNetworkStatus: OnNetworkStatus;
		segmentsExperienceId: string;
	}): Promise<{
		addedFragmentEntryLinks: FragmentEntryLinkMap;
		errorMessage?: string | undefined;
		layoutData: LayoutData;
		removedFragmentEntryLinkIds: string[];
	}>;
};
export default _default;

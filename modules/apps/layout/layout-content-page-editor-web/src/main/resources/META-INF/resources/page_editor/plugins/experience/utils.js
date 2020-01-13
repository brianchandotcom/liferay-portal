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

import {cancelDebounce, debounce} from 'frontend-js-web';
import {useRef} from 'react';

import {LAYOUT_DATA_ITEM_TYPES} from '../../app/config/constants/layoutDataItemTypes';

export function useDebounceCallback(callback, milliseconds) {
	const callbackRef = useRef(debounce(callback, milliseconds));

	return [callbackRef.current, () => cancelDebounce(callbackRef.current)];
}

const MODAL_EXPERIENCE_STATE_KEY = 'modalExperienceState';

/**
 * @typedef {Object} ModalExperienceState
 * @param {string} ModalExperienceState.classPK
 * @param {string?} ModalExperienceState.experienceId
 * @param {string} ModalExperienceState.experienceName
 * @param {string} ModalExperienceState.segmentId
 */

/**
 * Stores the `modalExperienceState` so it can be recovered later
 *
 * It also destroys any information previously stored for the same purpose
 *
 * @param {ModalExperienceState} modalExperienceState
 */
export function storeModalExperienceState(modalExperienceState) {
	window.sessionStorage.setItem(
		MODAL_EXPERIENCE_STATE_KEY,
		JSON.stringify(modalExperienceState)
	);
}

/**
 * Rescovers the `modalExperienceState` destroying any information stored for that purpose
 *
 * @return {ModalExperienceState|null}
 */
export function recoverModalExperienceState() {
	const state = window.sessionStorage.getItem(MODAL_EXPERIENCE_STATE_KEY);
	if (state !== null) {
		window.sessionStorage.removeItem(MODAL_EXPERIENCE_STATE_KEY);

		return JSON.parse(state);
	}

	return null;
}

function distinct(element, index, array) {
	return array.indexOf(element) === index;
}

function onlyFragmentType(element) {
	return element.type === LAYOUT_DATA_ITEM_TYPES.fragment;
}

function notInArray(arrayToSearch) {
	return function(element) {
		return arrayToSearch.indexOf(element) === -1;
	};
}

export function getUniqueFragmentEntryLinks({
	experienceIdToExclude,
	layoutData,
	layoutDataList,
	selectedExperienceId
}) {
	const layoutDataToCompare =
		selectedExperienceId === experienceIdToExclude
			? layoutData
			: layoutDataList.find(
					i => i.segmentsExperienceId === experienceIdToExclude
			  ).layoutData;

	const fragmentEntryLinkIdsInLayout = Object.entries(
		layoutDataToCompare.items
	)
		.map(([, value]) => value)
		.filter(value => onlyFragmentType(value))
		.map(({config}) => config.fragmentEntryLinkId);

	const layoutDatasWithoutExcluded = layoutDataList
		.filter(({segmentsExperienceId: id}) => {
			return id !== experienceIdToExclude;
		})
		.map(({layoutData: layout}) => {
			if (selectedExperienceId) {
				/**
				 * Updated layoutData
				 */
				return layoutData;
			}
			return layout;
		});

	const fragmentEntryLinksIdsInNotExcluded = layoutDatasWithoutExcluded
		.map(layoutData => layoutData.items)
		.reduce(
			(prevItems, items) => [...prevItems, ...Object.values(items)],
			[]
		)
		.filter(onlyFragmentType)
		.map(({config}) => config.fragmentEntryLinkId)
		.filter(distinct);

	const notInOtherLayoutsFilter = notInArray(
		fragmentEntryLinksIdsInNotExcluded
	);

	const uniqueFragmentEntryLinkIdsToLayout = fragmentEntryLinkIdsInLayout.filter(
		notInOtherLayoutsFilter
	);

	return uniqueFragmentEntryLinkIdsToLayout;
}

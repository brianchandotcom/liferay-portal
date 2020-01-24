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

function addExperience(state, experience) {
	if (process.env.NODE_ENV !== 'production') {
		if (experience.priority === undefined) {
			console.warn(
				'An Experience without priority has been added to the state'
			);
		}
		if (experience.hasLockedSegmentsExperiment === undefined) {
			console.warn(
				'An Experience without a `hasLockedSegmentsExperiment` key has been added to the state'
			);
		}
		if (
			state.availableSegmentsExperiences[
				experience.segmentsExperienceId
			] !== undefined
		) {
			console.warn(
				'An existing Experience is trying to be added to the state. Updated instead'
			);
		}
	}

	return {
		...state,
		availableSegmentsExperiences: {
			...state.availableSegmentsExperiences,
			[experience.segmentsExperienceId]: experience
		}
	};
}

/**
 * Sets `lockedSegmentsExperience` in the state, depending on the Experience
 * @param {object} state
 * @param {string | null} state.selectedSidebarPanelId
 * @param {object} experience
 * @param {boolean} experience.hasLockedSegmentsExperiment
 * @return {object} nextState
 */
function setExperienceLock(state, experience) {
	const lockedSegmentsExperience = experience.hasLockedSegmentsExperiment;

	//TODO selectedSidebarPanelId

	return {
		...state,
		lockedSegmentsExperience
	};
}

/**
 * Stores a the layout data of a new experience in layoutDataList
 * @param {object} state
 * @param {Array<{segmentsExperienceId: string}>} state.layoutDataList
 * @param {object} state.layoutData
 * @param {string} state.defaultSegmentsExperienceId
 * @param {string} segmentsExperienceId The segmentsExperience id that owns this LayoutData
 * @param {string} layoutData The new LayoutData to store
 * @returns {object}
 */
function storeNewLayoutData(state, segmentsExperienceId, layoutData) {
	const nextState = state;

	nextState.layoutDataList.push({
		layoutData,
		segmentsExperienceId
	});

	return nextState;
}

function selectExperience(state, experienceId) {
	return {
		...state,
		segmentsExperienceId: experienceId
	};
}

function switchLayoutData(state, {currentExperienceId, targetExperienceId}) {
	let nextState = state;
	const {layoutData: prevLayoutData, layoutDataList} = nextState;

	const layoutDataItem = state.layoutDataList.find(
		layoutDataItem =>
			layoutDataItem.segmentsExperienceId === targetExperienceId
	);

	nextState = {
		...nextState,
		layoutData: layoutDataItem.layoutData,
		layoutDataList: layoutDataList.map(layoutDataItem => {
			if (currentExperienceId === layoutDataItem.segmentsExperienceId) {
				return {
					...layoutDataItem,
					layoutData: prevLayoutData
				};
			}

			return layoutDataItem;
		})
	};

	return nextState;
}

/**
 * Updates the fragmentEntryLinks editableValues in State
 *
 * @param {object} state
 * @param {string} state.defaultSegmentsExperienceId
 * @param {object} state.fragmentEntryLinks
 * @param {string} fragmentEntryLinks
 * @returns {object}
 */
function updateFragmentEntryLinksEditableValues(
	state,
	fragmentEntryLinks = {}
) {
	const updatedFragmentEntryLinks = state.fragmentEntryLinks;

	Object.entries(fragmentEntryLinks).forEach(([id, editableValues]) => {
		updatedFragmentEntryLinks[id].editableValues = editableValues;
	});

	return {
		...state,
		fragmentEntryLinks: updatedFragmentEntryLinks
	};
}

function deleteExperienceById(state, segmentsExperienceId) {
	const experiences = {...state.availableSegmentsExperiences};

	delete experiences[segmentsExperienceId];

	return {
		...state,
		availableSegmentsExperiences: experiences
	};
}

function removeLayoutDataItemById(state, segmentsExperienceId) {
	const layoutDataList = state.layoutDataList;

	const updatedLayoutDataList = layoutDataList.filter(
		layoutDataItem =>
			layoutDataItem.segmentsExperienceId !== segmentsExperienceId
	);

	return {
		...state,
		layoutDataList: updatedLayoutDataList
	};
}

/**
 * Sets used widgets based on the portletIds array
 * @param {!Array} widgets
 * @param {{!Array} portletIds
 * @return {Array}
 * @review
 */
function updateUsedWidgets(widgets, portletIds) {
	const filteredWidgets = [...widgets];

	filteredWidgets.forEach(widgetCategory => {
		const {categories = [], portlets = []} = widgetCategory;

		widgetCategory.categories = updateUsedWidgets(categories, portletIds);
		widgetCategory.portlets = portlets.map(portlet => {
			if (
				portletIds.indexOf(portlet.portletId) !== -1 &&
				!portlet.instanceable
			) {
				portlet.used = true;
			} else {
				portlet.used = false;
			}

			return portlet;
		});
	});

	return filteredWidgets;
}

/**
 * Attaches to the state a the experiment status of a experience
 */
function setExperimentStatus(state, experienceId) {
	const selectedExperience = state.availableSegmentsExperiences[experienceId];

	return {
		...state,
		segmentsExperimentStatus: selectedExperience.segmentsExperimentStatus
	};
}

/**
 *
 * @param {object} state
 * @param {string} segmentsExperienceId
 * @returns {object}
 */
function setUsedWidgets(state, {portletIds}) {
	const updatedWidgets = updateUsedWidgets(state.widgets, portletIds);

	return {
		...state,
		widgets: updatedWidgets
	};
}

export {
	addExperience,
	deleteExperienceById,
	removeLayoutDataItemById,
	selectExperience,
	setExperienceLock,
	setExperimentStatus,
	setUsedWidgets,
	storeNewLayoutData,
	switchLayoutData,
	updateFragmentEntryLinksEditableValues
};

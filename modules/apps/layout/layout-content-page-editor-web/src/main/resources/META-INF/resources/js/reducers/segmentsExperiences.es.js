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

import {
	addSegmentsExperience,
	getExperienceUsedPortletIds,
	removeExperience,
	updatePageEditorLayoutData
} from '../utils/FragmentsEditorFetchUtils.es';
import {getRowFragmentEntryLinkIds} from '../utils/FragmentsEditorGetUtils.es';
import {setIn, updateUsedWidgets} from '../utils/FragmentsEditorUpdateUtils.es';
import {
	containsFragmentEntryLinkId,
	getEmptyLayoutData,
	getLayoutDataFragmentEntryLinkIds
} from '../utils/LayoutDataList.es';
import {getFragmentEntryLinkContent} from './fragments.es';

const EDIT_SEGMENTS_EXPERIENCE_URL =
	'/segments.segmentsexperience/update-segments-experience';

const UPDATE_SEGMENTS_EXPERIENCE_PRIORITY_URL =
	'/segments.segmentsexperience/update-segments-experience-priority';

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
function _storeNewLayoutData(state, segmentsExperienceId, layoutData) {
	const nextState = state;
	nextState.layoutDataList.push({
		layoutData,
		segmentsExperienceId
	});

	return nextState;
}

/**
 *
 * @param {object} state
 * @param {object} state.layoutData
 * @param {Array<{segmentsExperienceId: string ,layoutData: object}>} state.layoutDataList
 * @param {string} segmentsExperienceId
 * @returns {Promise}
 */
function _switchLayoutDataList(state, segmentsExperienceId) {
	let nextState = state;

	return new Promise((resolve, reject) => {
		try {
			updatePageEditorLayoutData(
				state.layoutData,
				state.segmentsExperienceId || state.defaultSegmentsExperienceId
			)
				.then(() => {
					const prevLayout = nextState.layoutData;
					const prevSegmentsExperienceId =
						state.segmentsExperienceId ||
						nextState.defaultSegmentsExperienceId;

					let layoutData = {};

					if (segmentsExperienceId === prevSegmentsExperienceId) {
						layoutData = nextState.layoutData;
					}
					else {
						const layoutDataItem = nextState.layoutDataList.find(
							segmentedLayout => {
								return (
									segmentedLayout.segmentsExperienceId ===
									segmentsExperienceId
								);
							}
						);

						layoutData = layoutDataItem
							? layoutDataItem.layoutData
							: getEmptyLayoutData();
					}

					nextState = setIn(nextState, ['layoutData'], layoutData);

					const newlayoutDataList = nextState.layoutDataList.map(
						segmentedLayout => {
							return segmentedLayout.segmentsExperienceId ===
								prevSegmentsExperienceId
								? {...segmentedLayout, layoutData: prevLayout}
								: segmentedLayout;
						}
					);

					nextState = setIn(
						nextState,
						['layoutDataList'],
						newlayoutDataList
					);

					resolve(nextState);
				})
				.catch(error => {
					reject(error);
				});
		}
		catch (e) {
			reject(e);
		}
	});
}

/**
 *
 * @param {object} state
 * @param {Array<{segmentsExperienceId: string}>} state.layoutDataList
 * @param {string} state.defaultSegmentsExperienceId
 * @returns {object}
 */
function _switchLayoutDataToDefault(state) {
	let nextState = state;

	const baseLayoutData = nextState.layoutDataList.find(layoutDataItem => {
		return (
			layoutDataItem.segmentsExperienceId ===
			nextState.defaultSegmentsExperienceId
		);
	});

	nextState = setIn(nextState, ['layoutData'], baseLayoutData.layoutData);

	return nextState;
}

/**
 *
 * @param {object} state
 * @param {Array<{segmentsExperienceId: string}>} state.layoutDataList
 * @param {string} segmentsExperienceId
 * @returns {object}
 */
function _removeLayoutDataItem(state, segmentsExperienceId) {
	let nextState = state;

	nextState = setIn(
		nextState,
		['layoutDataList'],
		nextState.layoutDataList.filter(layoutDataItem => {
			return layoutDataItem.segmentsExperienceId !== segmentsExperienceId;
		})
	);

	return nextState;
}

/**
 *
 * @param {object} state
 * @param {string} segmentsExperienceId
 * @returns {object}
 */
function _setUsedWidgets(state, segmentsExperienceId) {
	return getExperienceUsedPortletIds(segmentsExperienceId).then(
		portletIds => {
			const widgets = updateUsedWidgets(state.widgets, portletIds);

			state = setIn(state, ['widgets'], widgets);

			return state;
		}
	);
}

/**
 *
 * @param {object} state
 * @param {string} segmentsExperienceId
 * @returns {object}
 */
function _updateFragmentEntryLinks(state, segmentsExperienceId) {
	const fragmentEntryLinkIds = getLayoutDataFragmentEntryLinkIds(
		state.layoutData
	);

	const promises = fragmentEntryLinkIds.map(fragmentEntryLinkId => {
		let fragmentEntryLink = state.fragmentEntryLinks[fragmentEntryLinkId];

		return getFragmentEntryLinkContent(
			state.renderFragmentEntryURL,
			fragmentEntryLink,
			state.portletNamespace,
			segmentsExperienceId
		).then(response => {
			fragmentEntryLink = response;

			state = setIn(
				state,
				['fragmentEntryLinks', fragmentEntryLinkId],
				fragmentEntryLink
			);
		});
	});

	return Promise.all(promises).then(() => state);
}

/**
 * @param {object} state
 * @param {string} state.classNameId
 * @param {string} state.classPK
 * @param {string} state.defaultLanguageId
 * @param {string} state.defaultSegmentsExperienceId
 * @param {Array} state.layoutData
 * @param {Array<{segmentsExperienceId: string}>} state.layoutDataList
 * @param {object} action
 * @param {string} action.segmentsEntryId
 * @param {string} action.name
 * @param {string} action.type
 * @return {Promise}
 * @review
 */
function createSegmentsExperienceReducer(state, action) {
	return new Promise((resolve, reject) => {
		const {name, segmentsEntryId} = action;
		let nextState = state;

		addSegmentsExperience({
			name,
			segmentsEntryId
		})
			.then(objectResponse => {
				if (objectResponse.error) {
					throw objectResponse.error;
				}

				return objectResponse;
			})
			.then(function _success({
				fragmentEntryLinks,
				layoutData,
				segmentsExperience
			}) {
				const {
					active,
					name,
					priority,
					segmentsEntryId,
					segmentsExperienceId,
					segmentsExperimentStatus
				} = segmentsExperience;

				nextState = setIn(
					nextState,
					['availableSegmentsExperiences', segmentsExperienceId],
					{
						active,
						name,
						priority,
						segmentsEntryId,
						segmentsExperienceId
					}
				);

				nextState = _storeNewLayoutData(
					nextState,
					segmentsExperienceId,
					layoutData
				);

				nextState = _updateFragmentEntryLinksEditableValues(
					nextState,
					fragmentEntryLinks
				);

				nextState = _setExperienceLock(nextState, {
					hasLockedSegmentsExperiment: false
				});

				_switchLayoutDataList(nextState, segmentsExperienceId)
					.then(newState => ({
						...newState,
						segmentsExperienceId,
						segmentsExperimentStatus
					}))
					.then(nextNewState =>
						_updateFragmentEntryLinks(
							nextNewState,
							segmentsExperienceId
						)
					)
					.then(nextNewState =>
						_setUsedWidgets(
							nextNewState,
							action.segmentsExperienceId
						)
					)
					.then(nextNewState => {
						resolve(nextNewState);
					})
					.catch(e => {
						reject(e);
					});
			})
			.catch(function _fail(error) {
				reject(error);
			});
	});
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
function _updateFragmentEntryLinksEditableValues(
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

/**
 * @param {object} state
 * @param {Array} state.availableSegmentsExperiences
 * @param {string} state.defaultSegmentsExperienceId
 * @param {{structure: Array}} state.layoutData
 * @param {array} state.layoutDataList
 * @param {string} state.segmentsExperienceId
 * @param {object} action
 * @param {string} action.segmentsExperienceId
 * @param {string} action.type
 * @returns {Promise}
 */
function deleteSegmentsExperienceReducer(state, action) {
	return new Promise((resolve, reject) => {
		try {
			let nextState = state;
			const {segmentsExperienceId} = action;

			const fragmentEntryLinkIds = nextState.layoutData.structure
				.reduce(
					(allFragmentEntryLinkIds, row) => [
						...allFragmentEntryLinkIds,
						...getRowFragmentEntryLinkIds(row)
					],
					[]
				)
				.filter(
					fragmentEntryLinkId =>
						!containsFragmentEntryLinkId(
							nextState.layoutDataList,
							fragmentEntryLinkId,
							segmentsExperienceId
						)
				);

			removeExperience(segmentsExperienceId, fragmentEntryLinkIds)
				.then(() => {
					const priority =
						nextState.availableSegmentsExperiences[
							segmentsExperienceId
						].priority;

					const availableSegmentsExperiences = {
						...nextState.availableSegmentsExperiences
					};

					delete availableSegmentsExperiences[segmentsExperienceId];

					const experienceIdToSelect =
						segmentsExperienceId === nextState.segmentsExperienceId
							? nextState.defaultSegmentsExperienceId
							: nextState.segmentsExperienceId;

					Object.values(availableSegmentsExperiences).forEach(
						experience => {
							const segmentExperiencePriority =
								experience.priority;

							if (segmentExperiencePriority > priority) {
								experience.priority =
									segmentExperiencePriority - 1;
							}
						}
					);

					nextState = _removeLayoutDataItem(
						nextState,
						segmentsExperienceId
					);

					nextState = _switchLayoutDataToDefault(nextState);

					nextState = setIn(
						nextState,
						['availableSegmentsExperiences'],
						availableSegmentsExperiences
					);

					nextState = setIn(
						nextState,
						['segmentsExperienceId'],
						experienceIdToSelect
					);

					nextState = _setExperienceLock(
						nextState,
						nextState.availableSegmentsExperiences[
							experienceIdToSelect
						]
					);

					return _setUsedWidgets(nextState, experienceIdToSelect);
				})
				.then(nextNewState => resolve(nextNewState))
				.catch(error => {
					reject(error);
				});
		}
		catch (e) {
			reject(e);
		}
	});
}

/**
 * Sets `lockedSegmentsExperience` in the state, depending on the Experience
 * @param {object} state
 * @param {string | null} state.selectedSidebarPanelId
 * @param {object} experience
 * @param {boolean} experience.hasLockedSegmentsExperiment
 * @return {object} nextState
 */
function _setExperienceLock(state, experience) {
	const lockedSegmentsExperience = experience.hasLockedSegmentsExperiment;
	const selectedSidebarPanelId = lockedSegmentsExperience
		? null
		: state.selectedSidebarPanelId;

	return {
		...state,
		lockedSegmentsExperience,
		selectedSidebarPanelId
	};
}

/**
 *
 *
 * @export
 * @param {object} state
 * @param {object} state.layoutData
 * @param {object} state.layoutDataList
 * @param {string} state.segmentsExperienceId
 * @param {object} action
 * @param {string} action.segmentsExperienceId
 * @param {object} action.segmentsExperimentStatus
 * @param {string} action.type
 * @returns {Promise}
 */
function selectSegmentsExperienceReducer(state, action) {
	return new Promise((resolve, reject) => {
		const nextState = state;

		_switchLayoutDataList(nextState, action.segmentsExperienceId)
			.then(newState => {
				const experience =
					nextState.availableSegmentsExperiences[
						action.segmentsExperienceId
					];

				const nextNewState = {
					...newState,
					segmentsExperienceId: action.segmentsExperienceId,
					segmentsExperimentStatus:
						experience.segmentsExperimentStatus
				};

				return _setExperienceLock(nextNewState, experience);
			})
			.then(nextNewState =>
				_updateFragmentEntryLinks(
					nextNewState,
					action.segmentsExperienceId
				)
			)
			.then(nextNewState =>
				_setUsedWidgets(nextNewState, action.segmentsExperienceId)
			)
			.then(nextNewState => resolve(nextNewState))
			.catch(e => {
				reject(e);
			});
	});
}

/**
 * @param {object} state
 * @param {object} action
 * @param {string} action.segmentsEntryId
 * @param {string} action.name
 * @param {string} action.segmentsExperienceId
 * @param {string} action.type
 * @return {Promise}
 * @review
 */
function editSegmentsExperienceReducer(state, action) {
	return new Promise((resolve, reject) => {
		const {name, segmentsEntryId, segmentsExperienceId} = action;
		let nextState = state;

		const nameMap = JSON.stringify({
			[state.defaultLanguageId]: name
		});

		Liferay.Service(
			EDIT_SEGMENTS_EXPERIENCE_URL,
			{
				active: true,
				nameMap,
				segmentsEntryId,
				segmentsExperienceId
			},
			obj => {
				const {
					active,
					nameCurrentValue,
					priority,
					segmentsEntryId,
					segmentsExperienceId
				} = obj;

				nextState = {
					...nextState,
					availableSegmentsExperiences: {
						...nextState.availableSegmentsExperiences,
						[segmentsExperienceId]: {
							...nextState.availableSegmentsExperiences[
								segmentsExperienceId
							],
							active,
							name: nameCurrentValue,
							priority,
							segmentsEntryId,
							segmentsExperienceId
						}
					}
				};

				resolve(nextState);
			},
			error => {
				reject(error);
			}
		);
	});
}

/**
 *
 *
 * @param {object} state
 * @param {Array} state.availableSegmentsExperiences
 * @param {object} action
 * @param {('up' | 'down')} action.direction
 * @param {string} action.segmentsExperienceId
 * @param {number|string} action.priority
 * @param {string} action.type
 * @return {Promise}
 */
function updateSegmentsExperiencePriorityReducer(state, action) {
	return new Promise((resolve, reject) => {
		const {direction, priority: oldPriority, segmentsExperienceId} = action;
		let nextState = state;

		const {availableSegmentsExperiences} = nextState;

		const priorityForSubtarget =
			typeof oldPriority === 'number'
				? oldPriority
				: parseInt(oldPriority, 10);

		const targetExperience =
			availableSegmentsExperiences[segmentsExperienceId];

		const availableSegmentsExperiencesArray = Object.entries(
			availableSegmentsExperiences
		)
			.sort(([, a], [, b]) => a.priority - b.priority)
			.map(([, experience]) => experience);

		const targetExperiencePosition = availableSegmentsExperiencesArray.findIndex(
			experience => experience === targetExperience
		);

		const subTargetPosition =
			direction === 'up'
				? targetExperiencePosition + 1
				: targetExperiencePosition - 1;

		const subTargetExperience =
			availableSegmentsExperiencesArray[subTargetPosition];

		const priorityForTarget = subTargetExperience.priority;

		Liferay.Service(UPDATE_SEGMENTS_EXPERIENCE_PRIORITY_URL, {
			newPriority: priorityForTarget,
			segmentsExperienceId
		})
			.then(() => {
				const updatedTargetExperience = {
					...targetExperience,
					priority: priorityForTarget
				};

				const updatedSubtargetExperience = {
					...subTargetExperience,
					priority: priorityForSubtarget
				};

				nextState = {
					...nextState,
					availableSegmentsExperiences: {
						...nextState.availableSegmentsExperiences,
						[updatedSubtargetExperience.segmentsExperienceId]: updatedSubtargetExperience,
						[updatedTargetExperience.segmentsExperienceId]: updatedTargetExperience
					}
				};

				resolve(nextState);
			})
			.catch(error => {
				reject(error);
			});
	});
}

export {
	createSegmentsExperienceReducer,
	deleteSegmentsExperienceReducer,
	editSegmentsExperienceReducer,
	updateSegmentsExperiencePriorityReducer,
	selectSegmentsExperienceReducer
};

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

import {SELECT_SEGMENTS_EXPERIENCE} from '../../plugins/experience/actions';
import {
	ADD_FRAGMENT_ENTRY_LINKS,
	ADD_ITEM,
	DELETE_ITEM,
	DUPLICATE_ITEM,
	MOVE_ITEM,
	UPDATE_COL_SIZE_START,
	UPDATE_EDITABLE_VALUES,
	UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION,
	UPDATE_ITEM_CONFIG,
	UPDATE_LANGUAGE_ID,
	UPDATE_MULTIPLE_UNDO_STATE,
	UPDATE_UNDO_ACTIONS,
} from '../actions/types';
import updatePageContents from '../actions/updatePageContents';
import ExperienceService from '../services/ExperienceService';
import FragmentService from '../services/FragmentService';
import InfoItemService from '../services/InfoItemService';
import LayoutService from '../services/LayoutService';

const UNDO_SERVICES = {
	[ADD_FRAGMENT_ENTRY_LINKS]: LayoutService.deleteItem,
	[ADD_ITEM]: LayoutService.updateLayoutData,
	[DELETE_ITEM]: LayoutService.updateLayoutData,
	[DUPLICATE_ITEM]: LayoutService.deleteItem,
	[MOVE_ITEM]: LayoutService.updateLayoutData,
	[SELECT_SEGMENTS_EXPERIENCE]: ExperienceService.selectExperience,
	[UPDATE_COL_SIZE_START]: LayoutService.updateLayoutData,
	[UPDATE_EDITABLE_VALUES]: FragmentService.updateEditableValues,
	[UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION]:
		FragmentService.updateConfigurationValues,
	[UPDATE_ITEM_CONFIG]: LayoutService.updateLayoutData,
	[UPDATE_LANGUAGE_ID]: () => Promise.resolve(),
};

const getServiceBody = (dispatch, undo, undoState) => {
	switch (undo.type) {
		case ADD_ITEM:
		case DELETE_ITEM:
		case MOVE_ITEM:
		case UPDATE_COL_SIZE_START:
		case UPDATE_ITEM_CONFIG: {
			return {
				layoutData: undo.layoutData,
				onNetworkStatus: dispatch,
				segmentsExperienceId: undoState.segmentsExperienceId,
			};
		}
		case SELECT_SEGMENTS_EXPERIENCE: {
			return {
				body: {
					segmentsExperienceId: undo.segmentsExperienceId,
				},
				dispatch,
			};
		}
		case UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION: {
			return {
				configurationValues: undo.editableValues,
				fragmentEntryLinkId: undo.fragmentEntryLinkId,
				onNetworkStatus: dispatch,
			};
		}
		default:
			return {
				onNetworkStatus: dispatch,
				segmentsExperienceId: undoState.segmentsExperienceId,
				...undo,
			};
	}
};

const updateUndoState = (serviceResponse, undo, undoState) => {
	switch (undo.type) {
		case ADD_FRAGMENT_ENTRY_LINKS:
		case DUPLICATE_ITEM: {
			return {
				...undoState,
				deletedFragmentEntryLinkIds: undoState.deletedFragmentEntryLinkIds.concat(
					serviceResponse.deletedFragmentEntryLinkIds
				),
				layoutData: serviceResponse.layoutData,
			};
		}
		case ADD_ITEM:
		case DELETE_ITEM:
		case MOVE_ITEM:
		case UPDATE_COL_SIZE_START:
		case UPDATE_ITEM_CONFIG: {
			return {...undoState, layoutData: undo.layoutData};
		}
		case SELECT_SEGMENTS_EXPERIENCE: {
			return {
				...undoState,
				portletIds: serviceResponse,
				segmentsExperienceId: undo.segmentsExperienceId,
			};
		}
		case UPDATE_EDITABLE_VALUES: {
			return {
				...undoState,
				fragmentEntryLinks: {
					...undoState.fragmentEntryLinks,
					[undo.fragmentEntryLinkId]: {
						...undoState.fragmentEntryLinks[
							undo.fragmentEntryLinkId
						],
						editableValues: undo.editableValues,
					},
				},
			};
		}
		case UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION: {
			return {
				...undoState,
				fragmentEntryLinks: {
					...undoState.fragmentEntryLinks,
					[undo.fragmentEntryLinkId]:
						serviceResponse.fragmentEntryLink,
				},
				layoutData: serviceResponse.layoutData,
			};
		}
		case UPDATE_LANGUAGE_ID: {
			return {...undoState, languageId: undo.languageId};
		}

		default:
			return undoState;
	}
};

export default function multipleUndo({numberOfActions, store}) {
	return (dispatch) => {
		if (!store.undoHistory || store.undoHistory.length === 0) {
			return;
		}

		const undosToUndo = store.undoHistory.slice(0, numberOfActions);

		const remainingUndos = store.undoHistory.slice(
			numberOfActions,
			store.undoHistory.length
		);

		dispatch({type: UPDATE_UNDO_ACTIONS, undoHistory: remainingUndos});

		let undoState = {
			deletedFragmentEntryLinkIds: [],
			fragmentEntryLinks: store.fragmentEntryLinks,
			languageId: store.languageId,
			layoutData: store.layoutData,
			layoutDataList: store.layoutDataList,
			segmentsExperienceId: store.segmentsExperienceId,
		};

		undosToUndo
			.reduce((promise, undo) => {
				return promise.then(() => {
					const service = UNDO_SERVICES[undo.type];

					return service(
						getServiceBody(dispatch, undo, undoState)
					).then((response) => {
						undoState = updateUndoState(response, undo, undoState);
					});
				});
			}, Promise.resolve())
			.then(() => {
				InfoItemService.getPageContents({
					onNetworkStatus: dispatch,
				}).then((pageContents) => {
					dispatch(
						updatePageContents({
							pageContents,
						})
					);
				});
			})
			.then(() => {
				dispatch({
					...undoState,
					type: UPDATE_MULTIPLE_UNDO_STATE,
				});
			});
	};
}

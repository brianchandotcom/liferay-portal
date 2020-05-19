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
	setUsedWidgets,
	switchLayoutData,
} from '../../plugins/experience/reducers/utils';
import {
	ADD_UNDO_ACTION,
	UPDATE_MULTIPLE_UNDO_STATE,
	UPDATE_UNDO_ACTIONS,
} from '../actions/types';
import {getDerivedStateForUndo} from '../components/undo/undoActions';
import {getWidgetPath} from '../utils/getWidgetPath';
import {setWidgetUsage} from '../utils/setWidgetUsage';

const MAX_UNDO_ACTIONS = 20;

export default function undoReducer(state, action) {
	switch (action.type) {
		case ADD_UNDO_ACTION: {
			const {actionType} = action;

			const nextUndoHistory = state.undoHistory || [];

			return {
				...state,
				undoHistory: [
					getDerivedStateForUndo({action, state, type: actionType}),
					...nextUndoHistory.slice(0, MAX_UNDO_ACTIONS - 1),
				],
			};
		}
		case UPDATE_MULTIPLE_UNDO_STATE: {
			const {
				deletedFragmentEntryLinkIds,
				fragmentEntryLinks,
				languageId,
				layoutData,
				portletIds,
				segmentsExperienceId,
			} = action;

			let nextState = {
				...state,
				fragmentEntryLinks,
				languageId,
				layoutData,
				segmentsExperienceId,
			};

			if (state.segmentsExperienceId !== segmentsExperienceId) {
				nextState = switchLayoutData(nextState, {
					currentExperienceId: state.segmentsExperienceId,
					targetExperienceId: segmentsExperienceId,
				});
			}

			if (portletIds) {
				nextState = setUsedWidgets(nextState, {portletIds});
			}

			if (deletedFragmentEntryLinkIds.length) {
				const nextFragmentEntryLinks = {...fragmentEntryLinks};

				deletedFragmentEntryLinkIds.forEach((fragmentEntryLinkId) => {
					delete nextFragmentEntryLinks[fragmentEntryLinkId];
				});

				nextState = {
					...nextState,
					fragmentEntryLinks: nextFragmentEntryLinks,
				};

				const deletedWidgets = deletedFragmentEntryLinkIds
					.map(
						(fragmentEntryLinkId) =>
							fragmentEntryLinks[fragmentEntryLinkId]
					)
					.filter(
						(fragmentEntryLink) =>
							fragmentEntryLink.editableValues.portletId
					);

				if (deletedWidgets.length) {
					let nextWidgets = state.widgets;

					deletedWidgets.forEach((fragmentEntryLink) => {
						if (
							fragmentEntryLink.editableValues &&
							fragmentEntryLink.editableValues.portletId &&
							!fragmentEntryLink.editableValues.instanceable
						) {
							const widgetPath = getWidgetPath(
								nextWidgets,
								fragmentEntryLink.editableValues.portletId
							);

							nextWidgets = setWidgetUsage(
								nextWidgets,
								widgetPath,
								{
									used: false,
								}
							);
						}
					});

					nextState = {...nextState, widgets: nextWidgets};
				}
			}

			return nextState;
		}
		case UPDATE_UNDO_ACTIONS: {
			return {
				...state,
				undoHistory: action.undoHistory,
			};
		}
		default:
			return state;
	}
}

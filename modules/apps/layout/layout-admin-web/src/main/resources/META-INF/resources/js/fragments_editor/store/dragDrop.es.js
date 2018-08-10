import {Config} from 'metal-state';

/**
 * Initial state
 * @review
 * @type {object}
 */

const DRAG_DROP_INITIAL_STATE = {
	fragmentEntryLinkId: Config.string().value(null),
	fragmentEntryLinkName: Config.string().value(null),
	hoveredFragmentEntryLinkBorder: Config.string().value(null),
	hoveredFragmentEntryLinkId: Config.string().value(null)
};

/**
 * Update drag target action type
 * @review
 * @type {string}
 */

const UPDATE_DRAG_TARGET = 'updateDragTarget';

/**
 * @param {!MetalStore} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!string} payload.hoveredFragmentEntryLinkBorder
 * @param {!string} payload.hoveredFragmentEntryLinkId
 * @return {MetalStore}
 * @review
 */

function updateDragTargetReducer(state, actionType, payload) {
	let nextState = state;

	if (actionType === UPDATE_DRAG_TARGET) {
		nextState = Object.assign(
			{},
			state,
			{
				hoveredFragmentEntryLinkBorder: payload.hoveredFragmentEntryLinkBorder,
				hoveredFragmentEntryLinkId: payload.hoveredFragmentEntryLinkId
			}
		);
	}

	return nextState;
}

export {
	DRAG_DROP_INITIAL_STATE,
	UPDATE_DRAG_TARGET,
	updateDragTargetReducer
};
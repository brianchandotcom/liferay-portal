import {ADD_MAPPED_ASSET_ENTRY} from '../actions/actions.es';
import {setIn} from '../utils/FragmentsEditorUpdateUtils.es';

/**
 * @param {object} state
 * @param {object} action
 * @param {string} action.classNameId
 * @param {string} action.classPK
 * @param {string} action.title
 * @param {string} action.type
 */
function addMappingAssetEntry(state, action) {
	let nextState = state;

	if (action.type === ADD_MAPPED_ASSET_ENTRY) {
		const hasAssetEntry = nextState.mappedAssetEntries.some(
			assetEntry =>
				assetEntry.classNameId === action.classNameId &&
				assetEntry.classPK === action.classPK
		);

		if (!hasAssetEntry) {
			nextState = setIn(
				nextState,
				['mappedAssetEntries'],
				[...nextState.mappedAssetEntries, action]
			);
		}
	}

	return nextState;
}

export {addMappingAssetEntry};

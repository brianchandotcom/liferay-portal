import {ADD_MAPPED_ASSET_ENTRY} from '../actions/actions.es';
import {setIn} from '../utils/FragmentsEditorUpdateUtils.es';

/**
 * @param {object} state
 * @param {string} actionType
 * @param {object} payload
 * @param {string} payload.classNameId
 * @param {string} payload.classPK
 * @param {string} payload.title
 */
function addMappingAssetEntry(state, actionType, payload) {
	let nextState = state;

	if (actionType === ADD_MAPPED_ASSET_ENTRY) {
		const hasAssetEntry = nextState.mappedAssetEntries.some(
			assetEntry => (
				(assetEntry.classNameId === payload.classNameId) &&
				(assetEntry.classPK === payload.classPK)
			)
		);

		if (!hasAssetEntry) {
			nextState = setIn(
				nextState,
				['mappedAssetEntries'],
				[...nextState.mappedAssetEntries, payload]
			);
		}
	}

	return nextState;
}

export {addMappingAssetEntry};
import {getFragmentRowIndex} from './FragmentsEditorGetUtils.es';

/**
 * Tells if a fragmentEntryLink is referenced in any (but the current one)
 * LayoutData inside LayoutDataList
 *
 * @param {array} LayoutDataList
 * @param {string} fragmentEntryLinkId
 * @param {string} [skipSegmentsExperienceId] - allows to skip searching in layoutData by segmentsExperienceId
 * @returns {boolean}
 */
function containsFragmentEntryLinkId(
	LayoutDataList,
	fragmentEntryLinkId,
	skipSegmentsExperienceId
) {
	return LayoutDataList
		.filter(
			function _avoidCurrentExperienceLayoutDataItem(LayoutDataItem) {
				return LayoutDataItem.segmentsExperienceId !== skipSegmentsExperienceId;
			}
		).some(
			function _getFragmentRowIndexWrapper(LayoutDataItem) {
				const index = getFragmentRowIndex(
					LayoutDataItem.layoutData.structure,
					fragmentEntryLinkId
				);
				return index !== -1;
			}
		);
}

export {
	containsFragmentEntryLinkId
};
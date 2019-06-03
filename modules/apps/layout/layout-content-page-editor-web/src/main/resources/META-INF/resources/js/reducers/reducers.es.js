import {
	addFragmentEntryLinkReducer,
	clearFragmentEditorReducer,
	disableFragmentEditorReducer,
	enableFragmentEditorReducer,
	moveFragmentEntryLinkReducer,
	removeFragmentEntryLinkReducer,
	updateEditableValueReducer,
	updateFragmentEntryLinkConfigReducer
} from './fragments.es';
import {addMappingAssetEntry} from './mapping.es';
import {addPortletReducer} from './portlets.es';
import {
	addRowReducer,
	moveRowReducer,
	removeRowReducer,
	updateRowColumnsNumberReducer,
	updateRowColumnsReducer,
	updateRowConfigReducer
} from './rows.es';
import {
	createSegmentsExperienceReducer,
	deleteSegmentsExperienceReducer,
	editSegmentsExperienceReducer,
	selectSegmentsExperienceReducer,
	updateSegmentsExperiencePriorityReducer
} from './segmentsExperiences.es';
import {updateSelectedSidebarPanelId} from './sidebar.es';
import {
	hideMappingDialogReducer,
	hideMappingTypeDialogReducer,
	openAssetTypeDialogReducer,
	openMappingFieldsDialogReducer,
	selectMappeableTypeReducer
} from './dialogs.es';
import {languageIdReducer} from './translations.es';
import {saveChangesReducer} from './changes.es';
import {
	updateActiveItemReducer,
	updateDropTargetReducer,
	updateHoveredItemReducer
} from './placeholders.es';

/**
 * List of reducers
 * @type {function[]}
 */
const reducers = [
	addFragmentEntryLinkReducer,
	addMappingAssetEntry,
	addPortletReducer,
	addRowReducer,
	clearFragmentEditorReducer,
	disableFragmentEditorReducer,
	enableFragmentEditorReducer,
	hideMappingDialogReducer,
	hideMappingTypeDialogReducer,
	languageIdReducer,
	moveFragmentEntryLinkReducer,
	moveRowReducer,
	openAssetTypeDialogReducer,
	openMappingFieldsDialogReducer,
	removeFragmentEntryLinkReducer,
	removeRowReducer,
	saveChangesReducer,
	selectMappeableTypeReducer,
	selectSegmentsExperienceReducer,
	createSegmentsExperienceReducer,
	deleteSegmentsExperienceReducer,
	editSegmentsExperienceReducer,
	updateSegmentsExperiencePriorityReducer,
	updateActiveItemReducer,
	updateDropTargetReducer,
	updateEditableValueReducer,
	updateFragmentEntryLinkConfigReducer,
	updateHoveredItemReducer,
	updateRowColumnsNumberReducer,
	updateRowColumnsReducer,
	updateRowConfigReducer,
	updateSelectedSidebarPanelId
];

export {reducers};
export default reducers;

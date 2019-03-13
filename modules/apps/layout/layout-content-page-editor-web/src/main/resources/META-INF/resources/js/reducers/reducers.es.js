import {addFragmentEntryLinkReducer, moveFragmentEntryLinkReducer, removeFragmentEntryLinkReducer, updateEditableValueReducer, updateFragmentEntryLinkConfigReducer} from './fragments.es';
import {addMappingAssetEntry} from './mapping.es';
import {addPortletReducer} from './portlets.es';
import {addSectionReducer, moveSectionReducer, removeSectionReducer, updateSectionConfigReducer} from './sections.es';
import {createSegmentsExperienceReducer, deleteSegmentsExperienceReducer, endCreateSegmentsExperience, selectSegmentsExperienceReducer, startCreateSegmentsExperience} from './segmentsExperiences.es';
import {hideFragmentsEditorSidebarReducer, toggleFragmentsEditorSidebarReducer} from './sidebar.es';
import {hideMappingDialogReducer, hideMappingTypeDialogReducer, openAssetTypeDialogReducer, openMappingFieldsDialogReducer, selectMappeableTypeReducer} from './dialogs.es';
import {languageIdReducer, translationStatusReducer} from './translations.es';
import {saveChangesReducer} from './changes.es';
import {updateActiveItemReducer, updateDropTargetReducer, updateHoveredItemReducer} from './placeholders.es';

/**
 * List of reducers
 * @type {function[]}
 */
const reducers = [
	addFragmentEntryLinkReducer,
	addMappingAssetEntry,
	addPortletReducer,
	addSectionReducer,
	hideFragmentsEditorSidebarReducer,
	hideMappingDialogReducer,
	hideMappingTypeDialogReducer,
	languageIdReducer,
	moveFragmentEntryLinkReducer,
	moveSectionReducer,
	openAssetTypeDialogReducer,
	openMappingFieldsDialogReducer,
	removeFragmentEntryLinkReducer,
	removeSectionReducer,
	saveChangesReducer,
	selectMappeableTypeReducer,
	selectSegmentsExperienceReducer,
	createSegmentsExperienceReducer,
	deleteSegmentsExperienceReducer,
	endCreateSegmentsExperience,
	startCreateSegmentsExperience,
	toggleFragmentsEditorSidebarReducer,
	translationStatusReducer,
	updateActiveItemReducer,
	updateDropTargetReducer,
	updateEditableValueReducer,
	updateFragmentEntryLinkConfigReducer,
	updateHoveredItemReducer,
	updateSectionConfigReducer
];

export {reducers};
export default reducers;
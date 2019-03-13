import {addFragmentEntryLinkReducer, moveFragmentEntryLinkReducer, removeFragmentEntryLinkReducer, updateEditableValueReducer, updateFragmentEntryLinkConfigReducer} from './fragments.es';
import {addPortletReducer} from './portlets.es';
import {addSectionReducer, moveSectionReducer, removeSectionReducer, updateSectionConfigReducer} from './sections.es';
import {hideFragmentsEditorSidebarReducer, toggleFragmentsEditorSidebarReducer} from './sidebar.es';
import {hideMappingDialogReducer, hideMappingTypeDialogReducer, openAssetTypeDialogReducer, openMappingFieldsDialogReducer, selectMappeableTypeReducer} from './dialogs.es';
import {languageIdReducer, translationStatusReducer} from './translations.es';
import {saveChangesReducer} from './changes.es';
import {updateActiveItemReducer, updateDropTargetReducer, updateHoveredItemReducer} from './placeholders.es';
import {createSegmentsExperienceReducer, deleteSegmentsExperienceReducer, editSegmentsExperienceReducer, endCreateSegmentsExperience, endEditExperienceReducer, selectSegmentsExperienceReducer, startCreateSegmentsExperience, startEditExperienceReducer} from './segmentsExperiences.es';

/**
 * List of reducers
 * @type {function[]}
 */
const reducers = [
	addFragmentEntryLinkReducer,
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
	startEditExperienceReducer,
	endEditExperienceReducer,
	editSegmentsExperienceReducer,
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
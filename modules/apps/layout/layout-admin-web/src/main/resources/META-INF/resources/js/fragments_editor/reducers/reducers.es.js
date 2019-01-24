import {addFragmentEntryLinkReducer, moveFragmentEntryLinkReducer, removeFragmentEntryLinkReducer, updateEditableValueReducer} from './fragments.es';
import {addPortletReducer} from './portlets.es';
import {addSectionReducer, moveSectionReducer, removeSectionReducer, updateSectionConfigReducer} from './sections.es';
import {hideFragmentsEditorSidebarReducer, toggleFragmentsEditorSidebarReducer} from './sidebar.es';
import {hideMappingDialogReducer, hideMappingTypeDialogReducer, openAssetTypeDialogReducer, openMappingFieldsDialogReducer, selectMappeableTypeReducer} from './dialogs.es';
import {languageIdReducer, translationStatusReducer} from './translations.es';
import {saveChangesReducer} from './changes.es';
import {updateActiveItemReducer, updateDropTargetReducer, updateHighlightMappingReducer, updateHoveredItemReducer} from './placeholders.es';
import {switchToSegment} from './segments.es';

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
	switchToSegment,
	toggleFragmentsEditorSidebarReducer,
	translationStatusReducer,
	updateActiveItemReducer,
	updateDropTargetReducer,
	updateEditableValueReducer,
	updateHighlightMappingReducer,
	updateHoveredItemReducer,
	updateSectionConfigReducer
];

export {reducers};
export default reducers;
import Component from 'metal-component';
import Soy from 'metal-soy';

import './FragmentEntryLink.es';
import getConnectedComponent from '../../store/ConnectedComponent.es';
import templates from './FragmentEntryLinkListSection.soy';
import {shouldUpdatePureComponent} from '../../utils/FragmentsEditorComponentUtils.es';

/**
 * FragmentEntryLinkListSection
 */
class FragmentEntryLinkListSection extends Component {

	/**
	 * @inheritdoc
	 * @return {boolean}
	 * @review
	 */
	shouldUpdate(changes) {
		return shouldUpdatePureComponent(changes);
	}

}

const ConnectedFragmentEntryLinkListSection = getConnectedComponent(
	FragmentEntryLinkListSection,
	[
		'activeItemId',
		'activeItemType',
		'dropTargetBorder',
		'dropTargetItemId',
		'dropTargetItemType',
		'hoveredItemId',
		'hoveredItemType',
		'selectedMappingTypes',
		'spritemap'
	]
);

Soy.register(ConnectedFragmentEntryLinkListSection, templates);

export {ConnectedFragmentEntryLinkListSection, FragmentEntryLinkListSection};
export default FragmentEntryLinkListSection;
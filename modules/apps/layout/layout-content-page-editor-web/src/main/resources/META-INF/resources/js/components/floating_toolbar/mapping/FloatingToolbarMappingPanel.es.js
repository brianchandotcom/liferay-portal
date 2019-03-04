import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import './FloatingToolbarMappingPanelDelegateTemplate.soy';
import getConnectedComponent from '../../../store/ConnectedComponent.es';
import {setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';
import templates from './FloatingToolbarMappingPanel.soy';

const MAPPING_SOURCE_SPECIFIC_CONTENT_KEY = 'specific_content';

const MAPPING_SOURCE_SUBTYPE_KEY = 'subtype';

/**
 * FloatingToolbarMappingPanel
 */
class FloatingToolbarMappingPanel extends Component {

	/**
	 * Get mapping sources
	 * @param {!string} subtypeLabel
	 * @private
	 * @static
	 * @review
	 */
	static _getMappingSources(subtypeLabel) {
		return [
			{
				id: MAPPING_SOURCE_SUBTYPE_KEY,
				label: Liferay.Util.sub(
					Liferay.Language.get('x-default'),
					subtypeLabel
				)
			},
			{
				id: MAPPING_SOURCE_SPECIFIC_CONTENT_KEY,
				label: Liferay.Language.get('specific-content')
			}
		];
	}

	/**
	 * @inheritdoc
	 * @param {object} state
	 * @return {object}
	 * @review
	 */
	prepareStateForRender(state) {
		let nextState = state;

		if (this.selectedMappingTypes.subtype) {
			nextState = setIn(
				nextState,
				['_mappingSources'],
				FloatingToolbarMappingPanel._getMappingSources(
					this.selectedMappingTypes.subtype.label
				)
			);
		}

		return nextState;
	}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbarMappingPanel.STATE = {

	/**
	 * @default undefined
	 * @memberof FloatingToolbarMappingPanel
	 * @review
	 * @type {object}
	 */
	store: Config
		.object()
		.value(null),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarMappingPanel
	 * @review
	 * @type {!string}
	 */
	itemId: Config
		.string()
		.required()
};

const ConnectedFloatingToolbarMappingPanel = getConnectedComponent(
	FloatingToolbarMappingPanel,
	['selectedMappingTypes']
);

Soy.register(ConnectedFloatingToolbarMappingPanel, templates);

export {ConnectedFloatingToolbarMappingPanel, FloatingToolbarMappingPanel};
export default ConnectedFloatingToolbarMappingPanel;
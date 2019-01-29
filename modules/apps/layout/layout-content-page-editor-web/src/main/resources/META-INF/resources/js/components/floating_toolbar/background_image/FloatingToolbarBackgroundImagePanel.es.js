import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import './FloatingToolbarBackgroundImagePanelDelegateTemplate.soy';
import {getConnectedComponent} from '../../../store/ConnectedComponent.es';
import {openImageSelector} from '../../fragment_processors/EditableImageFragmentProcessor.es';
import templates from './FloatingToolbarBackgroundImagePanel.soy';
import {UPDATE_LAST_SAVE_DATE, UPDATE_SAVING_CHANGES_STATUS, UPDATE_SECTION_CONFIG, UPDATE_TRANSLATION_STATUS} from '../../../actions/actions.es';

/**
 * FloatingToolbarBackgroundImagePanel
 */
class FloatingToolbarBackgroundImagePanel extends Component {

	/**
	 * Show image selector
	 * @private
	 * @review
	 */
	_handleSelectButtonClick() {
		openImageSelector(
			this.imageSelectorURL,
			this.portletNamespace,
			url => this._updateSectionBackgroundImage(url)
		);
	}

	/**
	 * Remove existing image if any
	 * @private
	 * @review
	 */
	_handleClearButtonClick() {
		this._updateSectionBackgroundImage('');
	}

	/**
	 * Updates section image
	 * @param {string} backgroundImage Section image
	 * @private
	 * @review
	 */
	_updateSectionBackgroundImage(backgroundImage) {
		this.store
			.dispatchAction(
				UPDATE_SAVING_CHANGES_STATUS,
				{
					savingChanges: true
				}
			)
			.dispatchAction(
				UPDATE_SECTION_CONFIG,
				{
					config: {
						backgroundImage
					},
					sectionId: this.itemId
				}
			)
			.dispatchAction(
				UPDATE_TRANSLATION_STATUS
			)
			.dispatchAction(
				UPDATE_LAST_SAVE_DATE,
				{
					lastSaveDate: new Date()
				}
			)
			.dispatchAction(
				UPDATE_SAVING_CHANGES_STATUS,
				{
					savingChanges: false
				}
			);
	}

}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbarBackgroundImagePanel.STATE = {

	/**
	 * @default undefined
	 * @memberof FloatingToolbarBackgroundImagePanel
	 * @review
	 * @type {!string}
	 */
	itemId: Config
		.string()
		.required()
};

const ConnectedFloatingToolbarBackgroundImagePanel = getConnectedComponent(
	FloatingToolbarBackgroundImagePanel,
	[
		'imageSelectorURL',
		'portletNamespace'
	]
);

Soy.register(ConnectedFloatingToolbarBackgroundImagePanel, templates);

export {ConnectedFloatingToolbarBackgroundImagePanel, FloatingToolbarBackgroundImagePanel};
export default ConnectedFloatingToolbarBackgroundImagePanel;
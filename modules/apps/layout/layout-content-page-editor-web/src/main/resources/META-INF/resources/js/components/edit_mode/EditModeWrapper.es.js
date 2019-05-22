import Component from 'metal-component';
import getConnectedComponent from '../../store/ConnectedComponent.es';

/**
 * @type string
 */
const WRAPPER_CLASS = 'fragment-entry-link-list-wrapper';

/**
 * @type string
 */
const WRAPPER_PADDED_CLASS = 'fragment-entry-link-list-wrapper--padded';

/**
 * EditModeWrapper
 * @review
 */
class EditModeWrapper extends Component {
	/**
	 * @inheritdoc
	 */
	created() {
		this._handleSelectedSidebarPanelIdChanged = this._handleSelectedSidebarPanelIdChanged.bind(
			this
		);

		this.on(
			'selectedSidebarPanelIdChanged',
			this._handleSelectedSidebarPanelIdChanged
		);

		this._handleSelectedSidebarPanelIdChanged();
	}

	/**
	 * Callback called when the sidebar visibily changes
	 */
	_handleSelectedSidebarPanelIdChanged() {
		const wrapper = document.getElementById('wrapper');

		if (wrapper) {
			wrapper.classList.add(WRAPPER_CLASS);

			if (this.selectedSidebarPanelId) {
				wrapper.classList.add(WRAPPER_PADDED_CLASS);
			} else {
				wrapper.classList.remove(WRAPPER_PADDED_CLASS);
			}
		}
	}
}

const ConnectedEditModeWrapper = getConnectedComponent(EditModeWrapper, [
	'selectedSidebarPanelId'
]);

export {ConnectedEditModeWrapper, EditModeWrapper};
export default ConnectedEditModeWrapper;

import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import './SidebarAddedFragments.es';
import './SidebarAvailableFragments.es';
import './SidebarMapping.es';
import templates from './SidebarFragmentsSection.soy';

/**
 * Added tab ID
 * @review
 * @type {!string}
 */

const ADDED_TAB_ID = 'added';

/**
 * Default selected tab
 * @review
 * @type {!string}
 */

const DEFAULT_TAB_ID = 'available';

/**
 * SidebarFragmentsSection
 */

class SidebarFragmentsSection extends Component {

	/**
	 * @inheritDoc
	 * @review
	 */

	created() {
		const addedTab = this.sidebarTabs.find(
			tab => tab.id === ADDED_TAB_ID
		);

		if (addedTab) {
			this._addedTabEnabled = addedTab.enabled;
		}
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	prepareStateForRender(state) {
		return Object.assign(
			{},
			state,
			{
				sidebarTabs: state.sidebarTabs.map(
					sidebarTab => {
						return sidebarTab.id !== ADDED_TAB_ID ?
							sidebarTab :
							Object.assign(
								{},
								sidebarTab,
								{enabled: state._addedTabEnabled}
							);
					}
				)
			}
		);
	}

	/**
	 * Enable or disable added tab depending on we have fragments or not
	 * @private
	 * @review
	 */

	toggleAddedTab(enabled) {
		this._addedTabEnabled = !!enabled;

		if (!enabled) {
			this._selectedTab = DEFAULT_TAB_ID;
		}
	}

	/**
	 * Updates _selectedTab according to the clicked element
	 * @param {!MouseEvent} event
	 * @private
	 * @review
	 */

	_handleTabClick(event) {
		this._selectedTab = event.delegateTarget.dataset.tabId;
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

SidebarFragmentsSection.STATE = {

	/**
	 * Tabs being shown in sidebar
	 * @default undefined
	 * @instance
	 * @memberOf SidebarFragmentsSection
	 * @review
	 * @type {!Array<{
	 *   enabled: bool,
	 * 	 id: string,
	 * 	 label: string
	 * }>}
	 */

	sidebarTabs: Config.arrayOf(
		Config.shapeOf(
			{
				enabled: Config.bool().required(),
				id: Config.string().required(),
				label: Config.string().required()
			}
		)
	).required(),

	/**
	 * Whether to show added tab or not
	 * @default null
	 * @instance
	 * @memberOf SidebarFragmentsSection
	 * @private
	 * @review
	 * @type {bool}
	 */

	_addedTabEnabled: Config
		.bool()
		.internal()
		.value(null),

	/**
	 * Tab selected inside sidebar
	 * @default DEFAULT_TAB_ID
	 * @instance
	 * @memberOf SidebarFragmentsSection
	 * @private
	 * @review
	 * @type {string}
	 */

	_selectedTab: Config
		.string()
		.internal()
		.value(DEFAULT_TAB_ID)
};

Soy.register(SidebarFragmentsSection, templates);

export {SidebarFragmentsSection};
export default SidebarFragmentsSection;
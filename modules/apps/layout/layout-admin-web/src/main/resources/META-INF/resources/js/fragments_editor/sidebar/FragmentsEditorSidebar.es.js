import 'frontend-taglib/contextual_sidebar/ContextualSidebar.es';
import Component from 'metal-component';
import {Config} from 'metal-state';
import {EventEmitterProxy} from 'metal-events';
import Soy from 'metal-soy';

import './SidebarAddedFragments.es';
import './SidebarAvailableFragments.es';
import './SidebarMapping.es';
import templates from './FragmentsEditorSidebar.soy';

/**
 * Default selected tab
 * @review
 * @type {!string}
 */

const DEFAULT_TAB_ID = 'available';

/**
 * FragmentsEditorSidebar
 * @review
 */

class FragmentsEditorSidebar extends Component {

	/**
	 * @inheritDoc
	 * @review
	 */

	dispose() {
		this._disposeTabEventProxy();
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	rendered() {
		if (
			this.refs.contextualSidebar &&
			this.refs.contextualSidebar.refs.sidebarTab
		) {
			this._updateTabEventProxy(
				this.refs.contextualSidebar.refs.sidebarTab
			);
		}
	}

	/**
	 * Enable or disable added tab depending on we have fragments or not
	 * @private
	 * @review
	 */

	toggleAddedTab(enabled) {
		const addedTabIndex = this.sidebarTabs.findIndex(tab => tab.id === 'added');

		if (addedTabIndex !== -1) {
			const newAddedTab = Object.assign(
				{},
				this.sidebarTabs[addedTabIndex],
				{enabled: enabled}
			);

			const newSidebarTabs = [...this.sidebarTabs];

			newSidebarTabs[addedTabIndex] = newAddedTab;

			this.sidebarTabs = newSidebarTabs;
		}

		if (!enabled) {
			this._selectedTab = DEFAULT_TAB_ID;
		}
	}

	/**
	 * Dispose the existing _tabEventProxy, if any.
	 * @private
	 * @review
	 */

	_disposeTabEventProxy() {
		if (this._tabEventProxy) {
			this._tabEventProxy.dispose();

			this._tabEventProxy = null;
		}
	}

	/**
	 * @private
	 * @review
	 */

	_handleHideSidebarButtonClick() {
		this.emit('hide');
	}

	/**
	 * Updates _selectedTab according to the clicked element
	 * @param {!Event} event
	 * @private
	 * @review
	 */

	_handleTabClick(event) {
		this._selectedTab = event.delegateTarget.dataset.tabId ||
			DEFAULT_TAB_ID;
	}

	/**
	 *
	 * Update or create the _tabEventProxy to propagate all events emitted
	 * from the given tab.
	 * @param {EventEmitter} sidebarTab
	 * @private
	 * @review
	 */

	_updateTabEventProxy(sidebarTab) {
		if (!this._tabEventProxy) {
			this._tabEventProxy = new EventEmitterProxy(sidebarTab, this);
		}
		else {
			this._tabEventProxy.setOriginEmitter(sidebarTab);
		}
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

FragmentsEditorSidebar.STATE = {

	/**
	 * Tabs being shown in sidebar
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditorSidebar
	 * @review
	 * @type {!Array<{
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
	 * Tab selected inside sidebar
	 * @default DEFAULT_TAB_ID
	 * @instance
	 * @memberOf FragmentsEditorSidebar
	 * @private
	 * @review
	 * @type {string}
	 */

	_selectedTab: Config
		.string()
		.internal()
		.value(DEFAULT_TAB_ID),

	/**
	 * Event proxy used for propagating tabs events
	 * @default null
	 * @instance
	 * @memberOf FragmentsEditorSidebar
	 * @private
	 * @review
	 * @type {object|null}
	 */

	_tabEventProxy: Config
		.object()
		.internal()
		.value(null)
};

Soy.register(FragmentsEditorSidebar, templates);

export {FragmentsEditorSidebar};
export default FragmentsEditorSidebar;
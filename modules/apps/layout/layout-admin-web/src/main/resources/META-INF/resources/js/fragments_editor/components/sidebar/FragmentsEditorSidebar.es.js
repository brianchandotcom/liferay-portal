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
 * FragmentsEditorSidebar
 * @review
 */

class FragmentsEditorSidebar extends Component {

	/**
	 * @inheritDoc
	 * @review
	 */

	created() {
		const addedTab = this.sidebarTabs.find(tab => tab.id === ADDED_TAB_ID);

		if (addedTab) {
			this._addedTabEnabled = addedTab.enabled;
		}
	}

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
	 * Disallow setting element display to none
	 * @inheritDoc
	 * @review
	 */

	syncVisible() {}

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

	_handleHide() {
		this.emit('hide');
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
	 * CSS class for the fragments drop target.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditor
	 * @review
	 * @type {!string}
	 */

	dropTargetClass: Config.string(),

	/**
	 * Tabs being shown in sidebar
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditorSidebar
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
	 * @memberOf FragmentsEditorSidebar
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
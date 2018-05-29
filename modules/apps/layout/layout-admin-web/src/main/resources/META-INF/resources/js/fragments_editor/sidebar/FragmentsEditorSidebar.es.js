import 'frontend-taglib/contextual_sidebar/ContextualSidebar.es';
import Component from 'metal-component';
import {EventEmitterProxy} from 'metal-events';
import Soy from 'metal-soy';

import './SidebarAddedFragments.es';
import './SidebarAvailableFragments.es';
import './SidebarMapping.es';
import templates from './FragmentsEditorSidebar.soy';

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
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

FragmentsEditorSidebar.STATE = {};

Soy.register(FragmentsEditorSidebar, templates);

export {FragmentsEditorSidebar};
export default FragmentsEditorSidebar;
import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import SidebarLayoutsDragDrop from './utils/SidebarLayoutsDragDrop.es';
import templates from './SidebarLayoutsSection.soy';

/**
 * SidebarLayoutsSection
 */

class SidebarLayoutsSection extends Component {

	/**
	 * @inheritDoc
	 */
	rendered(firstRendered) {
		if (firstRendered) {
			this._initializeSidebarLayoutsDragDrop();
		}
	}

	/**
	 * @inheritdoc
	 */
	dispose() {
		this._sidebarLayoutsDragDrop.dispose();
	}

	/**
	 * Initializes sidebarLayoutsDragDrop instance
	 * @private
	 * @review
	 */
	_initializeSidebarLayoutsDragDrop() {
		if (this._sidebarLayoutsDragDrop) {
			this._sidebarLayoutsDragDrop.dispose();
		}

		this._sidebarLayoutsDragDrop = new SidebarLayoutsDragDrop();
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */

SidebarLayoutsSection.STATE = {

	/**
	 * List of layouts to be shown
	 * @default []
	 * @memberOf SidebarLayoutsSection
	 * @private
	 * @review
	 * @type {Array}
	 */

	_layouts: Config.arrayOf(
		Config.shapeOf(
			{
				columns: Config.arrayOf(Config.string())
			}
		)
	).value(
		[
			{columns: ['12']},
			{columns: ['6', '6']},
			{columns: ['4', '4', '4']},
			{columns: ['3', '6', '3']},
			{columns: ['3', '3', '3', '3']}
		]
	),

	/**
	 * Internal SidebarLayoutsDragDrop instance
	 * @default null
	 * @instance
	 * @memberOf SidebarLayoutsSection
	 * @review
	 * @type {object|null}
	 */

	_sidebarLayoutsDragDrop: Config.internal().value(null)
};

Soy.register(SidebarLayoutsSection, templates);

export {SidebarLayoutsSection};
export default SidebarLayoutsSection;
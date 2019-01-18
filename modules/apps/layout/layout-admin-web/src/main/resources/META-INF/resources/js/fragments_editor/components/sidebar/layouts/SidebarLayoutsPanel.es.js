import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import {
	ADD_SECTION,
	CLEAR_DROP_TARGET,
	UPDATE_DROP_TARGET
} from '../../../actions/actions.es';
import {FRAGMENTS_EDITOR_ITEM_TYPES} from '../../../utils/constants';
import SidebarLayoutsDragDrop from './utils/SidebarLayoutsDragDrop.es';
import {Store} from '../../../store/store.es';
import templates from './SidebarLayoutsPanel.soy';

/**
 * SidebarLayoutsPanel
 */
class SidebarLayoutsPanel extends Component {

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
	disposed() {
		this._sidebarLayoutsDragDrop.dispose();
	}

	/**
	 * Handles dragLayout event and dispatches action to update drag target
	 * @param {object} eventData
	 * @param {string} eventData.hoveredSectionBorder
	 * @param {string} eventData.hoveredSectionId
	 */
	_handleDragLayout(eventData) {
		const {hoveredSectionBorder, hoveredSectionId} = eventData;

		this.store.dispatchAction(
			UPDATE_DROP_TARGET,
			{
				dropTargetBorder: hoveredSectionBorder,
				dropTargetItemId: hoveredSectionId,
				dropTargetItemType: FRAGMENTS_EDITOR_ITEM_TYPES.section
			}
		);
	}

	/**
	 * Handles dropLayout event and dispatches action to add a section
	 * @param {!object} eventData
	 * @param {!number} eventData.layoutIndex
	 * @private
	 * @review
	 */
	_handleDropLayout(eventData) {
		const layoutColumns = this._layouts[eventData.layoutIndex].columns;

		this.store.dispatchAction(
			ADD_SECTION,
			{
				layoutColumns
			}
		).dispatchAction(
			CLEAR_DROP_TARGET
		);

		requestAnimationFrame(
			() => {
				this._initializeSidebarLayoutsDragDrop();
			}
		);
	}

	/**
	 * Handles leaveLayoutTarget event and dispatches
	 * action to clear drag target
	 * @private
	 * @review
	 */
	_handleLeaveLayoutTarget() {
		this.store.dispatchAction(
			CLEAR_DROP_TARGET
		);
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

		this._sidebarLayoutsDragDrop.on(
			'dragLayout',
			this._handleDragLayout.bind(this)
		);

		this._sidebarLayoutsDragDrop.on(
			'dropLayout',
			this._handleDropLayout.bind(this)
		);

		this._sidebarLayoutsDragDrop.on(
			'leaveLayoutTarget',
			this._handleLeaveLayoutTarget.bind(this)
		);
	}

}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
SidebarLayoutsPanel.STATE = {

	/**
	 * Store instance
	 * @default undefined
	 * @instance
	 * @memberOf SidebarLayoutsPanel
	 * @review
	 * @type {Store}
	 */
	store: Config.instanceOf(Store),

	/**
	 * List of layouts to be shown
	 * @default []
	 * @memberOf SidebarLayoutsPanel
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
			{
				columns: ['12']
			},
			{
				columns: [
					'6',
					'6'
				]
			},
			{
				columns: [
					'4',
					'4',
					'4'
				]
			},
			{
				columns: [
					'3',
					'6',
					'3'
				]
			},
			{
				columns: [
					'3',
					'3',
					'3',
					'3'
				]
			}
		]
	),

	/**
	 * Internal SidebarLayoutsDragDrop instance
	 * @default null
	 * @instance
	 * @memberOf SidebarLayoutsPanel
	 * @review
	 * @type {object|null}
	 */
	_sidebarLayoutsDragDrop: Config.internal().value(null)
};

Soy.register(SidebarLayoutsPanel, templates);

export {SidebarLayoutsPanel};
export default SidebarLayoutsPanel;
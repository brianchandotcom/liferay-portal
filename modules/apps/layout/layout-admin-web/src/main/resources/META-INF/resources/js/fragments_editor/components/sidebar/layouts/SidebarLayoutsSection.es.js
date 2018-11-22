import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import {
	ADD_SECTION,
	CLEAR_DRAG_TARGET,
	UPDATE_DRAG_TARGET
} from '../../../actions/actions.es';
import {DROP_TARGET_TYPES} from '../../../reducers/placeholders.es';
import SidebarLayoutsDragDrop from './utils/SidebarLayoutsDragDrop.es';
import {Store} from '../../../store/store.es';
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
	 * Handles dragLayout event and dispatches action to update drag target
	 * @param {object} eventData
	 * @param {string} eventData.hoveredSectionBorder
	 * @param {string} eventData.hoveredSectionId
	 */
	_handleDragLayout(eventData) {
		const {hoveredSectionBorder, hoveredSectionId} = eventData;

		this.store.dispatchAction(
			UPDATE_DRAG_TARGET,
			{
				hoveredElementBorder: hoveredSectionBorder,
				hoveredElementId: hoveredSectionId,
				hoveredElementType: DROP_TARGET_TYPES.section
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
			CLEAR_DRAG_TARGET
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
			CLEAR_DRAG_TARGET
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
SidebarLayoutsSection.STATE = {

	/**
	 * Store instance
	 * @default undefined
	 * @instance
	 * @memberOf SidebarLayoutsSection
	 * @review
	 * @type {Store}
	 */
	store: Config.instanceOf(Store),

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
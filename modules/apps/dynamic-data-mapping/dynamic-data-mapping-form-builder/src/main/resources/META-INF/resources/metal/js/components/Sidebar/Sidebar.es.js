import {ClayActionsDropdown, ClayDropdown} from 'clay-dropdown';
import {Config} from 'metal-state';
import {Drag, DragDrop} from 'metal-drag-drop';
import {EventHandler} from 'metal-events';
import {focusedFieldStructure} from '../../util/config.es';
import {normalizeSettingsContextPages} from '../../util/fieldSupport.es';
import {selectText} from '../../util/dom.es';
import autobind from 'autobind-decorator';
import classnames from 'classnames';
import ClayButton from 'clay-button';
import Component, {Fragment} from 'metal-jsx';
import dom from 'metal-dom';
import FieldTypeBox from '../FieldTypeBox/FieldTypeBox.es.js';
import FormRenderer, {FormSupport} from '../Form/index.es.js';
import WithEvaluator from '../Form/Evaluator.es';
import {PagesVisitor} from '../../util/visitors.es';

const EVALUATOR_URL = '/o/dynamic-data-mapping-form-context-provider/';
const FormWithEvaluator = WithEvaluator(FormRenderer);

/**
 * Sidebar is a tooling to mount forms.
 */

class Sidebar extends Component {
	static STATE = {

		/**
		 * @default 0
		 * @instance
		 * @memberof Sidebar
		 * @type {?number}
		 */

		activeTab: Config.number().value(0).internal(),

		/**
		 * @default _dropdownFieldTypesValueFn
		 * @instance
		 * @memberof Sidebar
		 * @type {?array}
		 */

		dropdownFieldTypes: Config.array().valueFn('_dropdownFieldTypesValueFn'),

		/**
		 * @instance
		 * @memberof Sidebar
		 * @type {array}
		 */

		fieldTypesGroup: Config.object().valueFn('_fieldTypesGroupValueFn'),

		/**
		 * @default false
		 * @instance
		 * @memberof Sidebar
		 * @type {?bool}
		 */

		open: Config.bool().valueFn('_openValueFn'),

		/**
		 * @default object
		 * @instance
		 * @memberof Sidebar
		 * @type {?object}
		 */

		tabs: Config.object().value(
			{
				add: {
					items: [
						Liferay.Language.get('elements')
					]
				},
				edit: {
					items: [
						Liferay.Language.get('basic'),
						Liferay.Language.get('properties')
					]
				}
			}
		).internal()
	};

	static PROPS = {

		/**
		 * @default {}
		 * @instance
		 * @memberof Sidebar
		 * @type {?object}
		 */

		focusedField: focusedFieldStructure.value({}),

		/**
		 * @default []
		 * @instance
		 * @memberof Sidebar
		 * @type {?(array|undefined)}
		 */

		fieldTypes: Config.array().value([]),

		/**
		 * @default false
		 * @instance
		 * @memberof Sidebar
		 * @type {?bool}
		 */

		open: Config.bool().value(false),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Sidebar
		 * @type {?(string|undefined)}
		 */

		spritemap: Config.string().required()
	};

	/**
	 * @inheritDoc
	 */

	created() {
		this._eventHandler = new EventHandler();
		this._handleCloseButtonClicked = this._handleCloseButtonClicked.bind(this);
		this._handleTabItemClicked = this._handleTabItemClicked.bind(this);

		const transitionEnd = this._getTransitionEndEvent();

		this.supportsTransitionEnd = transitionEnd !== false;
		this.transitionEnd = transitionEnd || 'transitionend';
	}

	/**
	 * @inheritDoc
	 */

	attached() {
		this._bindDragAndDrop();

		this._eventHandler.add(
			dom.on(document, 'mousedown', this._handleDocumentMouseDown.bind(this), true)
		);
	}

	/**
	 * @inheritDoc
	 */

	disposeInternal() {
		super.disposeInternal();

		this._eventHandler.removeAllListeners();
		this.disposeDragAndDrop();
		this.emit('fieldBlurred');
	}

	syncVisible(visible) {
		if (!visible) {
			this.emit('fieldBlurred');
		}
	}

	/**
	 * Close the Sidebar and remove event to handle document click.
	 * @public
	 */

	close() {
		this.setState(
			{
				open: false
			}
		);
	}

	disposeDragAndDrop() {
		if (this._dragAndDrop) {
			this._dragAndDrop.dispose();
		}
	}

	/**
	 * Open the Sidebar and attach event to handle document click.
	 * @public
	 */

	open() {
		const {transitionEnd} = this;

		dom.once(
			this.refs.container,
			transitionEnd,
			() => {
				if (this._isEditMode()) {
					const firstInput = this.element.querySelector('input');

					if (firstInput && document.activeElement !== firstInput) {
						firstInput.focus();
						selectText(firstInput);
					}
				}
			}
		);

		this.setState(
			{
				activeTab: 0,
				open: true
			}
		);

		this.refreshDragAndDrop();
	}

	refreshDragAndDrop() {
		this._dragAndDrop.setState(
			{
				targets: '.ddm-target'
			}
		);
	}

	/**
	 * Start drag and drop and attach events to manipulate.
	 * @protected
	 */

	_bindDragAndDrop() {
		this._dragAndDrop = new DragDrop(
			{
				dragPlaceholder: Drag.Placeholder.CLONE,
				sources: '.ddm-drag-item',
				targets: '.ddm-target',
				useShim: false
			}
		);

		this._eventHandler.add(
			this._dragAndDrop.on(
				DragDrop.Events.END,
				this._handleDragEnded.bind(this)
			),
			this._dragAndDrop.on(Drag.Events.START, this._handleDragStarted.bind(this))
		);
	}

	_cancelFieldChanges(indexes) {
		this.emit(
			'fieldChangesCanceled',
			indexes
		);
	}

	_checkSettingsActionsVisibility(target) {
		const {fieldSettingsActions} = this.refs;
		let dropdownPortal;

		if (fieldSettingsActions) {
			const {dropdown} = fieldSettingsActions.refs;
			const {portal} = dropdown.refs;

			dropdownPortal = portal.element.contains(target);
		}

		return dropdownPortal;
	}

	_deleteField(indexes) {
		this.emit(
			'fieldDeleted',
			indexes
		);
	}

	_dropdownFieldTypesValueFn() {
		const {fieldTypes} = this.props;

		return fieldTypes.filter(
			({system}) => {
				return !system;
			}
		).map(
			fieldType => {
				return {
					...fieldType,
					type: 'item'
				};
			}
		);
	}

	_duplicateField(indexes) {
		this.emit(
			'fieldDuplicated',
			indexes
		);
	}

	_fieldTypesGroupValueFn() {
		const {fieldTypes} = this.props;
		const group = {
			basic: {
				fields: [],
				label: Liferay.Language.get('field-types-basic-elements')
			},
			customized: {
				fields: [],
				label: Liferay.Language.get('field-types-customized-elements')
			}
		};

		return fieldTypes.reduce(
			(prev, next, index, original) => {
				if (next.group && !next.system) {
					prev[next.group].fields.push(next);
				}

				return prev;
			},
			group
		);
	}

	/**
	 * Checks to see if browser supports CSS3 Transitions and returns the name
	 * of the transitionend event; returns false if it's not supported
	 * @protected
	 * @return {string|boolean} The name of the transitionend event or false
	 * if not supported
	 */
	_getTransitionEndEvent() {
		const el = document.createElement('metalClayTransitionEnd');

		const transitionEndEvents = {
			MozTransition: 'transitionend',
			OTransition: 'oTransitionEnd otransitionend',
			transition: 'transitionend',
			WebkitTransition: 'webkitTransitionEnd'
		};

		let eventName = false;

		for (const name in transitionEndEvents) {
			if (el.style[name] !== undefined) {
				eventName = transitionEndEvents[name];
				break;
			}
		}

		return eventName;
	}

	/**
	 * @protected
	 */

	_handleCloseButtonClicked() {
		this.close();
	}

	/**
	 * Handle the click of the document and close the sidebar when
	 * clicking outside the Sidebar.
	 * @param {Event} event
	 * @protected
	 */

	_handleDocumentMouseDown({target}) {
		const {element, state, transitionEnd} = this;
		const {open} = state;
		const alloyEditorToolbarNode = dom.closest(target, '.ae-ui');
		const fieldColumnNode = dom.closest(target, '.col-ddm');
		const fieldTypesDropdownNode = dom.closest(target, '.dropdown-menu');
		const modalNode = dom.closest(target, '.modal');

		if (!open || alloyEditorToolbarNode || fieldTypesDropdownNode || fieldColumnNode ||
			modalNode || this._checkSettingsActionsVisibility(target) || element.contains(target)) {

			return;
		}

		this.close();

		dom.once(
			this.refs.container,
			transitionEnd,
			() => this.emit('fieldBlurred')
		);

		if (!modalNode) {
			setTimeout(() => this.emit('fieldBlurred'), 500);
		}
	}

	/**
	 * Handle with drag and close sidebar when moving.
	 * @protected
	 */

	_handleDragStarted() {
		this.refreshDragAndDrop();

		this.close();
	}

	/**
	 * Continues the propagation of event.
	 * @param {array} data
	 * @protected
	 */
	@autobind
	_handleEvaluatorChanged(pages) {
		const {focusedField} = this.props;

		this.emit(
			'focusedFieldUpdated',
			{
				...focusedField,
				settingsContext: {
					...focusedField.settingsContext,
					pages
				}
			}
		);
	}

	/**
	 * Handle a field move to dispatch the event to add in layout.
	 * @param {Object} data
	 * @param {Event} event
	 * @protected
	 */

	_handleDragEnded(data, event) {
		event.preventDefault();

		if (!data.target) {
			return;
		}

		const {fieldTypes} = this.props;
		const fieldTypeName = data.source.dataset.fieldTypeName;

		const fieldType = fieldTypes.find(({name}) => name === fieldTypeName);
		const indexes = FormSupport.getIndexes(data.target.parentElement);

		this.emit(
			'fieldAdded',
			{
				data,
				fieldType: {
					...fieldType,
					editable: true
				},
				target: indexes
			}
		);
	}

	/**
	 * Continues the propagation of event.
	 * @param {Object} data
	 * @protected
	 */
	@autobind
	_handleFieldEdited(data) {
		this.emit('fieldEdited', data);
	}

	/**
	 * Handle click on the field settings dropdown
	 * @protected
	 */
	@autobind
	_handleFieldSettingsClicked({data: {item}}) {
		const {columnIndex, pageIndex, rowIndex} = this.props.focusedField;
		const {settingsItem} = item;
		const indexes = {
			columnIndex,
			pageIndex,
			rowIndex
		};

		if (settingsItem === 'duplicate-field') {
			this._duplicateField(indexes);
		}
		else if (settingsItem === 'delete-field') {
			this._deleteField(indexes);
		}
		else if (settingsItem === 'cancel-field-changes') {
			this._cancelFieldChanges(indexes);
		}
	}

	/**
	 * Handle click on the dropdown to change the field type.
	 * @protected
	 */
	@autobind
	_handleChangeFieldTypeItemClicked({data}) {
		const {focusedField, namespace} = this.props;
		const newFieldType = data.item;
		const newSettingsContext = {
			...newFieldType.settingsContext,
			pages: normalizeSettingsContextPages(newFieldType.settingsContext.pages, namespace, newFieldType, focusedField.fieldName)
		};
		const settingsContext = this._mergeFieldTypeSettings(focusedField.settingsContext, newSettingsContext);

		this.emit(
			'focusedFieldUpdated',
			{
				...focusedField,
				...newFieldType,
				settingsContext,
				type: newFieldType.name
			}
		);
	}

	_mergeFieldTypeSettings(oldSettingsContext, newSettingsContext) {
		const newVisitor = new PagesVisitor(newSettingsContext.pages);
		const oldVisitor = new PagesVisitor(oldSettingsContext.pages);

		const getByFieldNameAndType = (fieldName, type) => {
			let field;

			oldVisitor.mapFields(
				oldField => {
					if (oldField.fieldName === fieldName && oldField.type === type) {
						field = oldField;
					}
				}
			);

			return field;
		};

		return {
			...newSettingsContext,
			pages: newVisitor.mapFields(
				newField => {
					const mergedField = getByFieldNameAndType(newField.fieldName, newField.type);

					if (mergedField) {
						newField = {
							...mergedField,
							visible: newField.visible
						};
					}

					return newField;
				}
			)
		};
	}

	/**
	 * Handle click on the previous button.
	 * @protected
	 */

	_handlePreviousButtonClicked() {
		const {transitionEnd} = this;

		this.close();

		dom.once(
			this.refs.container,
			transitionEnd,
			() => {
				this.emit('fieldBlurred');
				this.open();
			}
		);
	}

	/**
	 * Handle click on the tab item and manipulate the active tab.
	 * @param {number} index
	 * @param {Event} event
	 * @protected
	 */

	_handleTabItemClicked(event) {
		const {target} = event;
		const {dataset: {index}} = dom.closest(target, '.nav-item');

		event.preventDefault();

		this.setState(
			{
				activeTab: parseInt(index, 10)
			}
		);
	}

	/**
	 * Checks whether it is safe to go to edit mode.
	 * @param {string} mode
	 * @protected
	 * @return {bool}
	 */

	_isEditMode() {
		const {focusedField} = this.props;

		return !(
			Object.keys(focusedField).length === 0 &&
			focusedField.constructor === Object
		);
	}

	_openValueFn() {
		const {open} = this.props;

		return open;
	}

	_renderFieldTypeGroups() {
		const {spritemap} = this.props;
		const {fieldTypesGroup} = this.state;
		const group = Object.keys(fieldTypesGroup);

		return (
			<div aria-orientation="vertical" class="ddm-field-types-panel panel-group" id="accordion03" role="tablist">
				{group.map(
					(key, index) => (
						<div class="panel panel-secondary" key={`fields-group-${key}-${index}`}>
							<a
								aria-controls="collapseTwo"
								aria-expanded="true"
								class="collapse-icon panel-header panel-header-link"
								data-parent="#accordion03"
								data-toggle="collapse"
								href={`#ddm-field-types-${key}-body`}
								id={`ddm-field-types-${key}-header`}
								role="tab"
							>
								<span class="panel-title">{fieldTypesGroup[key].label}</span>
								<span class="collapse-icon-closed">
									<svg aria-hidden="true" class="lexicon-icon lexicon-icon-angle-right">
										<use xlink:href={`${spritemap}#angle-right`} />
									</svg>
								</span>
								<span class="collapse-icon-open">
									<svg aria-hidden="true" class="lexicon-icon lexicon-icon-angle-down">
										<use xlink:href={`${spritemap}#angle-down`} />
									</svg>
								</span>
							</a>
							<div
								aria-labelledby={`#ddm-field-types-${key}-header`}
								class="panel-collapse show"
								id={`ddm-field-types-${key}-body`}
								role="tabpanel"
							>

								<div class="panel-body p-0 m-0 list-group">
									{fieldTypesGroup[key].fields.map(
										fieldType => (
											<FieldTypeBox
												fieldType={fieldType}
												key={fieldType.name}
												spritemap={spritemap}
											/>
										)
									)}
								</div>
							</div>
						</div>
					)
				)}
			</div>
		);
	}

	_renderNavItems() {
		const {activeTab, tabs} = this.state;

		return tabs[this._isEditMode() ? 'edit' : 'add'].items.map(
			(name, index) => {
				const style = classnames(
					'nav-link',
					{
						active: index === activeTab
					}
				);

				return (
					<li
						class="nav-item"
						data-index={index}
						data-onclick={this._handleTabItemClicked}
						key={`tab${index}`}
						ref={`tab${index}`}
					>
						<a
							aria-controls="sidebarLightDetails"
							class={style}
							data-toggle="tab"
							href="javascript:;"
							role="tab"
						>
							<span class="navbar-text-truncate">{name}</span>
						</a>
					</li>
				);
			}
		);
	}

	_renderTopBar() {
		const {fieldTypes, focusedField, spritemap} = this.props;
		const editMode = this._isEditMode();
		const fieldActions = [
			{
				label: Liferay.Language.get('duplicate-field'),
				settingsItem: 'duplicate-field'
			},
			{
				label: Liferay.Language.get('remove-field'),
				settingsItem: 'delete-field'
			},
			{
				label: Liferay.Language.get('cancel-field-changes'),
				settingsItem: 'cancel-field-changes'
			}
		];
		const focusedFieldType = fieldTypes.find(({name}) => name === focusedField.type);
		const previousButtonEvents = {
			click: this._handlePreviousButtonClicked.bind(this)
		};

		return (
			<ul class="tbar-nav">
				{!editMode && (
					<li class="tbar-item tbar-item-expand text-left">
						<div class="tbar-section">
							<span class="text-truncate-inline">
								<span class="text-truncate">{Liferay.Language.get('add-elements')}</span>
							</span>
						</div>
					</li>
				)}
				{editMode && (
					<Fragment>
						<li class="tbar-item">
							<ClayButton
								editable={true}
								events={previousButtonEvents}
								icon="angle-left"
								ref="previousButton"
								size="sm"
								spritemap={spritemap}
								style="secondary"
							/>
						</li>
						<li class="tbar-item ddm-fieldtypes-dropdown tbar-item-expand text-left">
							<div>
								<ClayDropdown
									events={{
										itemClicked: this._handleChangeFieldTypeItemClicked
									}}
									icon={focusedFieldType.icon}
									items={this.state.dropdownFieldTypes}
									itemsIconAlignment={'left'}
									label={focusedFieldType.label}
									spritemap={spritemap}
									style={'secondary'}
									triggerClasses={'nav-link btn-sm'}
								/>
							</div>
						</li>
						<li class="tbar-item">
							<ClayActionsDropdown
								events={{
									itemClicked: this._handleFieldSettingsClicked
								}}
								items={fieldActions}
								ref="fieldSettingsActions"
								spritemap={spritemap}
								triggerClasses={'component-action'}
							/>
						</li>
					</Fragment>
				)}
				<li class="tbar-item">
					<a
						class="component-action sidebar-close"
						data-onclick={this._handleCloseButtonClicked}
						href="#1"
						ref="close"
						role="button"
					>
						<svg
							aria-hidden="true"
							class="lexicon-icon lexicon-icon-times"
						>
							<use
								xlink:href={`${spritemap}#times`}
							/>
						</svg>
					</a>
				</li>
			</ul>
		);
	}

	/**
	 * @inheritDoc
	 */

	render() {
		const {activeTab, open} = this.state;
		const {
			focusedField,
			spritemap
		} = this.props;

		const {settingsContext} = focusedField;

		const layoutRenderEvents = {
			evaluated: this._handleEvaluatorChanged,
			fieldEdited: this._handleFieldEdited
		};

		const editMode = this._isEditMode();

		const styles = classnames('sidebar-container', {open});

		return (
			<div class={styles} ref="container">
				<div class="sidebar sidebar-light">
					<nav class="component-tbar tbar">
						<div class="container-fluid">
							{this._renderTopBar()}
						</div>
					</nav>
					<nav class="component-navigation-bar navbar navigation-bar navbar-collapse-absolute navbar-expand-md navbar-underline">
						<a
							aria-controls="sidebarLightCollapse00"
							aria-expanded="false"
							aria-label="Toggle Navigation"
							class="collapsed navbar-toggler navbar-toggler-link"
							data-toggle="collapse"
							href="#sidebarLightCollapse00"
							role="button"
						>
							<span class="navbar-text-truncate">{'Details'}</span>
							<svg
								aria-hidden="true"
								class="lexicon-icon lexicon-icon-caret-bottom"
							>
								<use xlink:href={`${spritemap}#caret-bottom`} />
							</svg>
						</a>
						<div
							class="collapse navbar-collapse"
							id="sidebarLightCollapse00"
						>
							<ul class="nav navbar-nav" role="tablist">
								{this._renderNavItems()}
							</ul>
						</div>
					</nav>
					<div class="ddm-sidebar-body">
						{!editMode &&
							this._renderFieldTypeGroups()
						}
						{editMode && (
							<div class="sidebar-body ddm-field-settings">
								<div class="tab-content">
									<FormWithEvaluator
										activePage={activeTab}
										editable={true}
										events={layoutRenderEvents}
										fieldType={focusedField.type}
										formContext={settingsContext}
										modeRenderer="list"
										pages={settingsContext.pages}
										ref="FormRenderer"
										spritemap={spritemap}
										url={EVALUATOR_URL}
									/>
								</div>
							</div>
						)}
					</div>
				</div>
			</div>
		);
	}
}

export default Sidebar;
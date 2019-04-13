import * as FormSupport from '../Form/FormSupport.es';
import classnames from 'classnames';
import ClayButton from 'clay-button';
import Component, {Fragment} from 'metal-jsx';
import dom from 'metal-dom';
import FieldTypeBox from '../FieldTypeBox/FieldTypeBox.es.js';
import FormRenderer from '../Form/FormRenderer.es';
import UA from 'metal-useragent';
import WithEvaluator from '../Form/Evaluator.es';
import {ClayActionsDropdown, ClayDropdownBase} from 'clay-dropdown';
import {ClayIcon} from 'clay-icon';
import {Config} from 'metal-state';
import {Drag, DragDrop} from 'metal-drag-drop';
import {EventHandler} from 'metal-events';
import {focusedFieldStructure} from '../../util/config.es';
import {getFieldProperties, normalizeSettingsContextPages} from '../../util/fieldSupport.es';
import {PagesVisitor, RulesVisitor} from '../../util/visitors.es';
import {selectText} from '../../util/dom.es';

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
		 * @default undefined
		 * @instance
		 * @memberof Sidebar
		 * @type {?string}
		 */

		defaultLanguageId: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Sidebar
		 * @type {?string}
		 */

		editingLanguageId: Config.string(),

		/**
		 * @default []
		 * @instance
		 * @memberof Sidebar
		 * @type {?(array|undefined)}
		 */

		fieldTypes: Config.array().value([]),

		/**
		 * @default {}
		 * @instance
		 * @memberof Sidebar
		 * @type {?object}
		 */

		focusedField: focusedFieldStructure.value({}),

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

	attached() {
		this._bindDragAndDrop();

		this._eventHandler.add(
			dom.on(document, 'mousedown', this._handleDocumentMouseDown, true)
		);
	}

	changeFieldType(type) {
		const {defaultLanguageId, editingLanguageId, fieldTypes, focusedField} = this.props;
		const newFieldType = fieldTypes.find(({name}) => name === type);
		const newSettingsContext = {
			...newFieldType.settingsContext,
			pages: normalizeSettingsContextPages(newFieldType.settingsContext.pages, editingLanguageId, newFieldType, focusedField.fieldName)
		};
		let {settingsContext} = focusedField;

		if (type !== focusedField.type) {
			settingsContext = this._mergeFieldTypeSettings(settingsContext, newSettingsContext);
		}

		this.emit(
			'focusedFieldUpdated',
			{
				...focusedField,
				...newFieldType,
				...getFieldProperties(settingsContext, defaultLanguageId, editingLanguageId),
				settingsContext,
				type: newFieldType.name
			}
		);

		this.refs.evaluableForm.evaluate();
	}

	close() {
		this.setState(
			{
				open: false
			}
		);
	}

	created() {
		this._eventHandler = new EventHandler();
		const transitionEnd = this._getTransitionEndEvent();

		this.supportsTransitionEnd = transitionEnd !== false;
		this.transitionEnd = transitionEnd || 'transitionend';

		this._handleChangeFieldTypeItemClicked = this._handleChangeFieldTypeItemClicked.bind(this);
		this._handleCloseButtonClicked = this._handleCloseButtonClicked.bind(this);
		this._handleDocumentMouseDown = this._handleDocumentMouseDown.bind(this);
		this._handleDragEnded = this._handleDragEnded.bind(this);
		this._handleDragStarted = this._handleDragStarted.bind(this);
		this._handleEvaluatorChanged = this._handleEvaluatorChanged.bind(this);
		this._handleFieldSettingsClicked = this._handleFieldSettingsClicked.bind(this);
		this._handlePreviousButtonClicked = this._handlePreviousButtonClicked.bind(this);
		this._handleSettingsFieldBlurred = this._handleSettingsFieldBlurred.bind(this);
		this._handleSettingsFieldEdited = this._handleSettingsFieldEdited.bind(this);
		this._handleTabItemClicked = this._handleTabItemClicked.bind(this);
		this._renderFieldTypeDropdownLabel = this._renderFieldTypeDropdownLabel.bind(this);
	}

	disposeDragAndDrop() {
		if (this._dragAndDrop) {
			this._dragAndDrop.dispose();
		}
	}

	disposeInternal() {
		super.disposeInternal();

		this._eventHandler.removeAllListeners();
		this.disposeDragAndDrop();
		this.emit('fieldBlurred');
	}

	getFormContext() {
		const {defaultLanguageId, editingLanguageId, focusedField} = this.props;
		const {settingsContext} = focusedField;
		const visitor = new PagesVisitor(settingsContext.pages);

		return {
			...settingsContext,
			pages: visitor.mapFields(
				field => {
					return {
						...field,
						defaultLanguageId,
						editingLanguageId,
						readOnly: this.isFieldReadOnly(field)
					};
				}
			)
		};
	}

	isActionsDisabled() {
		const {defaultLanguageId, editingLanguageId} = this.props;

		return defaultLanguageId !== editingLanguageId;
	}

	isChangeFieldTypeEnabled() {
		return !this.isActionsDisabled();
	}

	isFieldReadOnly(field) {
		const {defaultLanguageId, editingLanguageId} = this.props;

		return !field.localizable && editingLanguageId !== defaultLanguageId;
	}

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
				targets: UA.isIE ? this._dragAndDrop.setterTargetsFn_('.ddm-target') : '.ddm-target'
			}
		);
	}

	render() {
		const {activeTab, open} = this.state;
		const {
			editingLanguageId,
			focusedField,
			spritemap
		} = this.props;

		const layoutRenderEvents = {
			evaluated: this._handleEvaluatorChanged,
			fieldBlurred: this._handleSettingsFieldBlurred,
			fieldEdited: this._handleSettingsFieldEdited
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
										editingLanguageId={editingLanguageId}
										events={layoutRenderEvents}
										fieldType={focusedField.type}
										formContext={this.getFormContext()}
										paginationMode="tabbed"
										ref="evaluableForm"
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

	syncEditingLanguageId() {
		const {evaluableForm} = this.refs;

		if (evaluableForm) {
			evaluableForm.evaluate();
		}
	}

	syncVisible(visible) {
		if (!visible) {
			this.emit('fieldBlurred');
		}
	}

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
				this._handleDragEnded
			),
			this._dragAndDrop.on(Drag.Events.START, this._handleDragStarted)
		);
	}

	_cancelFieldChanges(indexes) {
		this.emit('fieldChangesCanceled', indexes);
	}

	_deleteField(indexes) {
		this.emit('fieldDeleted', {indexes});
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
		this.emit('fieldDuplicated', {indexes});
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
			(prev, next) => {
				if (next.group && !next.system) {
					prev[next.group].fields.push(next);
				}

				return prev;
			},
			group
		);
	}

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

	_handleChangeFieldTypeItemClicked({data}) {
		const newFieldType = data.item.name;

		this.changeFieldType(newFieldType);
	}

	_handleCloseButtonClicked() {
		this.close();
	}

	_handleDocumentMouseDown({target}) {
		const {transitionEnd} = this;
		const {open} = this.state;

		if (
			this._isCloseButton(target) ||
			(open && (
				!this._isSidebarElement(target) &&
				!this._isTranslationItem(target)
			))
		) {
			this.close();

			dom.once(
				this.refs.container,
				transitionEnd,
				() => this.emit('fieldBlurred')
			);

			if (!this._isModalElement(target)) {
				setTimeout(() => this.emit('fieldBlurred'), 500);
			}
		}
	}

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

	_handleDragStarted() {
		this.refreshDragAndDrop();

		this.close();
	}

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

	_handleFieldSettingsClicked({data: {item}}) {
		const {columnIndex, pageIndex, rowIndex} = this.props.focusedField;
		const {settingsItem} = item;
		const indexes = {
			columnIndex,
			pageIndex,
			rowIndex
		};

		if (!item.disabled) {
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
	}

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

	_handleSettingsFieldBlurred(event) {
		this.emit('settingsFieldBlurred', event);
	}

	_handleSettingsFieldEdited(event) {
		this.emit('settingsFieldEdited', event);
	}

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

	_hasRuleExpression(fieldName) {
		const {rules} = this.props;
		const visitor = new RulesVisitor(rules);

		return visitor.containsFieldExpression(fieldName);
	}

	_isCloseButton(node) {
		const {closeButton} = this.refs;

		return closeButton.contains(node);
	}

	_isEditMode() {
		const {focusedField} = this.props;

		return !(
			Object.keys(focusedField).length === 0 &&
			focusedField.constructor === Object
		);
	}

	_isModalElement(node) {
		return dom.closest(node, '.modal');
	}

	_isSettingsElement(target) {
		const {fieldSettingsActions} = this.refs;
		let dropdownPortal;

		if (fieldSettingsActions) {
			const {dropdown} = fieldSettingsActions.refs;
			const {portal} = dropdown.refs;

			dropdownPortal = portal.element.contains(target);
		}

		return dropdownPortal;
	}

	_isSidebarElement(node) {
		const {element} = this;
		const alloyEditorToolbarNode = dom.closest(node, '.ae-ui');
		const fieldColumnNode = dom.closest(node, '.col-ddm');
		const fieldTypesDropdownNode = dom.closest(node, '.dropdown-menu');

		return (
			alloyEditorToolbarNode || fieldTypesDropdownNode || fieldColumnNode ||
			element.contains(node) || this._isSettingsElement(node)
		);
	}

	_isTranslationItem(node) {
		return !!dom.closest(node, '.lfr-translationmanager');
	}

	_mergeFieldTypeSettings(oldSettingsContext, newSettingsContext) {
		const newVisitor = new PagesVisitor(newSettingsContext.pages);
		const oldVisitor = new PagesVisitor(oldSettingsContext.pages);

		const excludedFields = [
			'indexType',
			'type',
			'validation'
		];

		const getPreviousField = ({fieldName, type, value}) => {
			let field;

			oldVisitor.findField(
				oldField => {
					if (
						excludedFields.indexOf(fieldName) === -1 &&
						oldField.fieldName === fieldName &&
						oldField.type === type
					) {
						field = oldField;
					}

					return field;
				}
			);

			return field;
		};

		return {
			...newSettingsContext,
			pages: newVisitor.mapFields(
				newField => {
					const previousField = getPreviousField(newField);

					if (previousField) {
						const {multiple, visible} = newField;

						for (const prop in newField) {
							if (previousField.hasOwnProperty(prop)) {
								newField[prop] = previousField[prop];
							}
						}

						newField.multiple = multiple;
						newField.visible = visible;

						if (newField.fieldName === 'predefinedValue') {
							delete newField.value;
						}
					}

					return newField;
				}
			)
		};
	}

	_openValueFn() {
		const {open} = this.props;

		return open;
	}

	_renderFieldTypeDropdownLabel() {
		const {fieldTypes, focusedField, spritemap} = this.props;
		const {icon, label} = fieldTypes.find(({name}) => name === focusedField.type);

		return (
			<Fragment>
				<ClayIcon
					elementClasses={'inline-item inline-item-before'}
					spritemap={spritemap}
					symbol={icon}
				/>
				{label}
				<ClayIcon
					elementClasses={'inline-item inline-item-after'}
					spritemap={spritemap}
					symbol={'caret-bottom'}
				/>
			</Fragment>
		);
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
				disabled: this.isActionsDisabled(),
				label: Liferay.Language.get('duplicate-field'),
				settingsItem: 'duplicate-field'
			},
			{
				disabled: this.isActionsDisabled(),
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
			click: this._handlePreviousButtonClicked
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
								disabled={this.isActionsDisabled()}
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
								<ClayDropdownBase
									disabled={!this.isChangeFieldTypeEnabled()}
									events={{
										itemClicked: this._handleChangeFieldTypeItemClicked
									}}
									icon={focusedFieldType.icon}
									items={this.state.dropdownFieldTypes}
									itemsIconAlignment={'left'}
									label={this._renderFieldTypeDropdownLabel}
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
						ref="closeButton"
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
}

export default Sidebar;
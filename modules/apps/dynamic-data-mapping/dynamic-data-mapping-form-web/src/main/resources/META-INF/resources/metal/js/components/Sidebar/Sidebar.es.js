import {Config} from 'metal-state';
import {Drag, DragDrop} from 'metal-drag-drop';
import {EventHandler} from 'metal-events';
import {focusedFieldStructure} from '../../util/config.es';
import {selectText} from '../../util/dom.es';
import classnames from 'classnames';
import ClayButton from 'clay-button';
import Component, {Fragment} from 'metal-jsx';
import dom from 'metal-dom';
import FieldTypeBox from '../FieldTypeBox/FieldTypeBox.es.js';
import FormRenderer, {FormSupport} from '../Form/index.es.js';

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

	_fieldTypesGroupValueFn() {
		const {fieldTypes} = this.props;
		const group = {
			basic: {
				fields: [],
				label: Liferay.Language.get('basic-elements')
			},
			customized: {
				fields: [],
				label: Liferay.Language.get('customized-elements')
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

	_openValueFn() {
		const {open} = this.props;

		return open;
	}

	/**
	 * Handle the click of the document and close the sidebar when
	 * clicking outside the Sidebar.
	 * @param {Event} event
	 * @protected
	 */

	_handleDocumentMouseDown(event) {
		const {open} = this.state;
		const {transitionEnd} = this;

		if (!open || this.element.contains(event.target)) {
			return;
		}

		this.close();

		dom.once(
			this.refs.container,
			transitionEnd,
			() => this.emit('fieldBlurred')
		);
	}

	/**
	 * Handle with drag and close sidebar when moving.
	 * @protected
	 */

	_handleDragStarted() {
		this.close();
	}

	/**
	 * Continues the propagation of event.
	 * @param {Object} data
	 * @protected
	 */

	_handleFieldEdited(data) {
		this.emit('fieldEdited', data);
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
		const {index} = target.parentElement.dataset;

		event.preventDefault();

		this.setState(
			{
				activeTab: parseInt(index, 10)
			}
		);
	}

	/**
	 * @protected
	 */

	_handleCloseButtonClicked() {
		this.close();
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

	/**
	 * Start drag and drop and attach events to manipulate.
	 * @protected
	 */

	_bindDragAndDrop() {
		this._dragAndDrop = new DragDrop(
			{
				dragPlaceholder: Drag.Placeholder.CLONE,
				sources: '.ddm-drag-item',
				targets: '.ddm-target'
			}
		);

		this._eventHandler.add(
			this._dragAndDrop.on(
				DragDrop.Events.END,
				this._handleDragEnded.bind(this)
			),
			this._dragAndDrop.on(DragDrop.Events.DRAG, this._handleDragStarted.bind(this))
		);
	}

	refreshDragAndDrop() {
		this._dragAndDrop.setState(
			{
				targets: '.ddm-target'
			}
		);
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

	disposeDragAndDrop() {
		if (this._dragAndDrop) {
			this._dragAndDrop.dispose();
		}
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

	syncVisible(visible) {
		if (!visible) {
			this.emit('fieldBlurred');
		}
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

		let settingsContext;

		const layoutRenderEvents = {
			fieldEdited: this._handleFieldEdited.bind(this)
		};

		const editMode = this._isEditMode();

		if (editMode) {
			settingsContext = focusedField.settingsContext;
		}

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
							this._groupFieldTypes()
						}
						{editMode && (
							<div class="sidebar-body">
								<div class="tab-content">
									<FormRenderer
										activePage={activeTab}
										editable={true}
										events={layoutRenderEvents}
										modeRenderer="list"
										pages={settingsContext.pages}
										ref="FormRenderer"
										spritemap={spritemap}
									/>
								</div>
							</div>
						)}
					</div>
				</div>
			</div>
		);
	}

	_groupFieldTypes() {
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

	_renderTopBar() {
		const {fieldTypes, focusedField, spritemap} = this.props;
		const editMode = this._isEditMode();
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
								<span class="text-truncate">{'Add Elements'}</span>
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
						<li class="tbar-item tbar-item-expand text-left">
							<div>
								<ClayButton
									disabled={true}
									icon={focusedFieldType.icon}
									label={focusedFieldType.label}
									size="sm"
									spritemap={spritemap}
									style="secondary"
								/>
							</div>
						</li>
					</Fragment>
				)}
				<li class="tbar-item">
					<a
						class="component-action"
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
}

export default Sidebar;
import {Config} from 'metal-state';
import {Drag, DragDrop} from 'metal-drag-drop';
import {EventHandler} from 'metal-events';
import {focusedFieldStructure} from '../../util/config.es';
import {selectText} from '../../util/dom.es';
import classnames from 'classnames';
import ClayButton from 'clay-button';
import Component, {Fragment} from 'metal-jsx';
import dom from 'metal-dom';
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

		fieldTypes: Config.array().value([]).required(),

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
		if (!open || this.element.contains(event.target)) {
			return;
		}
		this.close();
		setTimeout(() => this.emit('fieldBlurred'), 500);
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
		const fieldIndex = data.source.dataset.ddmFieldTypeIndex;
		const fieldType = fieldTypes[Number(fieldIndex)];
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
		this.close();

		setTimeout(
			() => {
				this.emit('fieldBlurred');
				this.open();
			},
			500
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

		this._dragAndDrop.on(
			DragDrop.Events.END,
			this._handleDragEnded.bind(this)
		);
		this._dragAndDrop.on(DragDrop.Events.DRAG, this._handleDragStarted.bind(this));
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
	}

	/**
	 * @inheritDoc
	 */

	dispose() {
		this._eventHandler.removeAllListeners();
	}

	/**
	 * Open the Sidebar and attach event to handle document click.
	 * @public
	 */

	open() {
		this.setState(
			{
				activeTab: 0,
				open: true
			}
		);
		this.once(
			'rendered',
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
		this.refreshDragAndDrop();
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
			<div class={styles} ref="sidebar">
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
								<use xlinkHref={`${spritemap}#caret-bottom`} />
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
						{!editMode && (
							<ul class="list-group">
								<li class="list-group-header">
									<h3 class="list-group-header-title">{Liferay.Language.get('basic-elements')}</h3>
								</li>
								{this._renderListElements()}
							</ul>
						)}
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
								xlinkHref={`${spritemap}#times`}
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

	_renderListElements() {
		const {fieldTypes, spritemap} = this.props;

		return fieldTypes.filter(fieldType => !fieldType.system).map(
			(fieldType, index) => {
				return (
					<div
						class="ddm-drag-item list-group-item list-group-item-flex"
						data-ddm-field-type-index={index}
						key={`field${index}`}
						ref={`field${index}`}
					>
						<div class="autofit-col">
							<div class="sticker sticker-secondary">
								<svg
									aria-hidden="true"
									class={`lexicon-icon lexicon-icon-${
										fieldType.icon
									}`}
								>
									<use
										xlinkHref={`${spritemap}#${fieldType.icon}`}
									/>
								</svg>
							</div>
						</div>
						<div class="autofit-col autofit-col-expand">
							<h4 class="list-group-title text-truncate">
								<span>{fieldType.label}</span>
							</h4>
							{fieldType.description && (
								<p class="list-group-subtitle text-truncate">
									{fieldType.description}
								</p>
							)}
						</div>
					</div>
				);
			}
		);
	}
}

export default Sidebar;
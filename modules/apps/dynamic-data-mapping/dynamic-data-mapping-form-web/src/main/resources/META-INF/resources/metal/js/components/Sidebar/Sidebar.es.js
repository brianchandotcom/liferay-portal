import {Config} from 'metal-state';
import {Drag, DragDrop} from 'metal-drag-drop';
import {EventHandler} from 'metal-events';
import classnames from 'classnames';
import ClayButton from 'clay-button';
import Component, {Fragment} from 'metal-jsx';
import dom from 'metal-dom';
import LayoutRenderer, {LayoutSupport} from '../Layout/index.es.js';

/**
 * Sidebar is a tooling to mount forms.
 */

class Sidebar extends Component {
	static STATE = {

		/**
		 * @default add
		 * @instance
		 * @memberof Sidebar
		 * @type {?string}
		 */

		mode: Config.oneOf(['add', 'edit']).value('add'),

		/**
		 * @default false
		 * @instance
		 * @memberof Sidebar
		 * @type {?bool}
		 */

		show: Config.bool().value(false),

		/**
		 * @default 0
		 * @instance
		 * @memberof Sidebar
		 * @type {?number}
		 */

		activeTab: Config.number().value(0)
	};

	static PROPS = {

		/**
		 * @default undefined
		 * @instance
		 * @memberof Sidebar
		 * @type {?(array<object>|undefined)}
		 */

		context: Config.array().value([]),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Sidebar
		 * @type {?(array<object>|undefined)}
		 */

		fieldContext: Config.array().value([]),

		/**
		 * @default []
		 * @instance
		 * @memberof Sidebar
		 * @type {?(array|undefined)}
		 */

		fieldLists: Config.array().value([]),

		/**
		 * @default {}
		 * @instance
		 * @memberof Sidebar
		 * @type {?object}
		 */

		focusedField: Config.shapeOf(
			{
				columnIndex: Config.oneOfType(
					[
						Config.bool().value(false),
						Config.number()
					]
				),
				pageIndex: Config.number(),
				rowIndex: Config.number(),
				type: Config.string().required()
			}
		).value({}),

		/**
		 * @default add
		 * @instance
		 * @memberof Sidebar
		 * @type {?string}
		 */

		mode: Config.oneOf(['add', 'edit']).value('add'),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Sidebar
		 * @type {?(string|undefined)}
		 */

		spritemap: Config.string().required(),

		/**
		 * @default object
		 * @instance
		 * @memberof Sidebar
		 * @type {?object}
		 */

		tabs: Config.object().value(
			{
				add: {
					items: ['Elements']
				},
				edit: {
					items: ['Basic', 'Properties']
				}
			}
		)
	};

	/**
	 * Handle the click of the document and close the sidebar when
	 * clicking outside the Sidebar.
	 * @param {Event} event
	 * @protected
	 */

	_handleDocClick(event) {
		if (this.element.contains(event.target)) {
			return;
		}
		this.close();
	}

	/**
	 * Handle with drag and close sidebar when moving.
	 * @protected
	 */

	_handleDrag() {
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

	_handleFieldMoved(data, event) {
		event.preventDefault();

		if (!data.target) {
			return;
		}

		const {fieldLists} = this.props;
		const fieldIndex = data.source.getAttribute(
			'data-ddm-field-type-index'
		);
		const fieldProperties = fieldLists[Number(fieldIndex)];
		const indexTarget = LayoutSupport.getIndexes(data.target.parentElement);

		this.emit(
			'fieldAdded',
			{
				data,
				fieldProperties,
				target: indexTarget
			}
		);
	}

	/**
	 * Handle click on the previous button.
	 * @protected
	 */

	_handleOnClickPrevious() {
		this.setState(
			{
				'mode': 'add'
			}
		);
	}

	/**
	 * Handle click on the tab item and manipulate the active tab.
	 * @param {number} index
	 * @param {Event} event
	 * @protected
	 */

	_handleOnClickTab(event) {
		const {target} = event;
		const {index} = target.dataset;

		event.preventDefault();

		this.setState(
			{
				'activeTab': parseInt(index, 10)
			}
		);
	}

	/**
	 * @protected
	 */

	_handleOnClose() {
		this.close();
	}

	/**
	 * Checks whether it is safe to go to edit mode.
	 * @param {string} mode
	 * @protected
	 * @return {bool}
	 */

	_isEditMode() {
		const {fieldContext, fieldLists, focusedField} = this.props;

		return !!(
			!(
				Object.keys(focusedField).length === 0 &&
				focusedField.constructor === Object
			) &&
			fieldContext.length &&
			fieldLists.length
		);
	}

	/**
	 * Set the internal mode state.
	 * @param {String} mode
	 * @protected
	 */

	_setMode(mode) {
		if (this._isEditMode()) {
			this.setState(
				{
					mode
				}
			);
		}
	}

	/**
	 * Start drag and drop and attach events to manipulate.
	 * @protected
	 */

	_startDrag() {
		this._dragAndDrop = new DragDrop(
			{
				dragPlaceholder: Drag.Placeholder.CLONE,
				sources: '.ddm-drag-item',
				targets: '.ddm-target'
			}
		);

		this._dragAndDrop.on(
			DragDrop.Events.END,
			this._handleFieldMoved.bind(this)
		);
		this._dragAndDrop.on(DragDrop.Events.DRAG, this._handleDrag.bind(this));
	}

	/**
	 * @inheritDoc
	 */

	attached() {
		this._startDrag();
	}

	/**
	 * Close the Sidebar and remove event to handle document click.
	 * @public
	 */

	close() {
		this.setState(
			{
				'show': false
			}
		);
		this._eventHandler.removeAllListeners();
	}

	/**
	 * @inheritDoc
	 */

	created() {
		this._eventHandler = new EventHandler();
		this._handleOnClickTab = this._handleOnClickTab.bind(this);
		this._handleOnClose = this._handleOnClose.bind(this);
		this._setMode(this.props.mode);
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

	show() {
		this.setState(
			{
				'show': true
			}
		);

		this._eventHandler.add(
			dom.on(document, 'click', this._handleDocClick.bind(this), true)
		);
	}

	/**
	 * @inheritDoc
	 */

	willReceiveProps(nextProps) {
		if (
			typeof nextProps.context !== 'undefined' &&
			nextProps.context.newVal.length
		) {
			this._dragAndDrop.disposeInternal();
			this._startDrag();
		}

		if (
			typeof nextProps.mode !== 'undefined' &&
			nextProps.mode.newVal &&
			this._isEditMode()
		) {
			this.show();
		}

		if (typeof nextProps.mode !== 'undefined' && nextProps.mode.newVal) {
			this._setMode(nextProps.mode.newVal);
		}
	}

	/**
	 * @inheritDoc
	 */

	render() {
		const {activeTab, mode, show} = this.state;
		const {
			fieldContext,
			fieldLists,
			focusedField,
			spritemap
		} = this.props;
		let currentField = null;

		const layoutRenderEvents = {
			fieldEdited: this._handleFieldEdited.bind(this)
		};

		if (mode === 'edit') {
			currentField = fieldLists.find(item => item.type == focusedField.type);
		}

		const styles = classnames('sidebar-container', {show});

		const angleLeftEvents = {
			click: this._handleOnClickPrevious.bind(this)
		};

		return (
			<div class={styles} ref="sidebar">
				<div class="sidebar sidebar-light">
					<nav class="component-tbar tbar">
						<div class="container-fluid">
							<ul class="tbar-nav">
								{mode === 'add' && show && (
									<li class="tbar-item tbar-item-expand text-left">
										<div class="tbar-section">
											<span class="text-truncate-inline">
												<span class="text-truncate">{'Add Elements'}</span>
											</span>
										</div>
									</li>
								)}
								{mode === 'edit' && show && (
									<Fragment>
										<li class="tbar-item">
											<ClayButton
												editable={true}
												events={angleLeftEvents}
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
													icon={currentField.icon}
													label={currentField.name}
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
										data-onclick={this._handleOnClose}
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
								{this._renderNavItem()}
							</ul>
						</div>
					</nav>
					<div class="ddm-sidebar-body">
						{mode === 'add' &&
							!!fieldLists.length && (
							<ul class="list-group">
								<li class="list-group-header">
									<h3 class="list-group-header-title">{'Basic Elements'}</h3>
								</li>
								{this._renderListElements()}
							</ul>
						)}
						{mode === 'edit' && (
							<div class="sidebar-body">
								<div class="tab-content">
									<LayoutRenderer
										activePage={activeTab}
										events={layoutRenderEvents}
										modeRenderer="list"
										pages={fieldContext}
										ref="layoutRenderer"
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

	_renderNavItem() {
		const {activeTab, mode} = this.state;
		const {tabs} = this.props;

		return tabs[mode].items.map(
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
						data-onclick={this._handleOnClickTab}
						key={`tab${index}`}
						ref={`tab${index}`}
					>
						<a
							aria-controls="sidebarLightDetails"
							class={style}
							data-toggle="tab"
							href="#"
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
		const {fieldLists, spritemap} = this.props;

		return fieldLists.map(
			(field, index) => {
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
										field.icon
									}`}
								>
									<use
										xlinkHref={`${spritemap}#${field.icon}`}
									/>
								</svg>
							</div>
						</div>
						<div class="autofit-col autofit-col-expand">
							<h4 class="list-group-title text-truncate">
								<span>{field.name}</span>
							</h4>
							{field.description && (
								<p class="list-group-subtitle text-truncate">
									{field.description}
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
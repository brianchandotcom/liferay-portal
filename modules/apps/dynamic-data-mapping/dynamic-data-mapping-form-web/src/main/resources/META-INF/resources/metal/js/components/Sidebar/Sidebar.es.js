import {Config} from 'metal-state';
import {DragDrop, Drag} from 'metal-drag-drop';
import {EventHandler} from 'metal-events';
import classnames from 'classnames';
import ClayButton from 'clay-button';
import Component, { Fragment } from 'metal-jsx';
import dom from 'metal-dom';
import LayoutRenderer, { LayoutSupport } from '../Layout/index.es.js';

/**
 * Sidebar is a tooling to mount forms.
 * @extends Component.
 */
class Sidebar extends Component {
    static STATE = {
        /**
         * @default false
         * @instance
         * @memberof Sidebar
         * @type {?bool}
         */
        show: Config.bool().value(false),

        /**
         * @default add
         * @instance
         * @memberof Sidebar
         * @type {?string}
         */
        mode: Config.oneOf(['add', 'edit']).value('add'),

        /**
         * @default 0
         * @instance
         * @memberof Sidebar
         * @type {?number}
         */
        tabActive: Config.number().value(0)
    }

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
         * @default {}
         * @instance
         * @memberof Sidebar
         * @type {?object}
         */
        fieldFocus: Config.shapeOf({
            indexColumn: Config.oneOfType([
                Config.bool().value(false),
                Config.number()
            ]),
            indexPage: Config.number(),
            indexRow: Config.number(),
            type: Config.string().required(),
        }).value({}),

        /**
         * @default []
         * @instance
         * @memberof Sidebar
         * @type {?(array|undefined)}
         */
        fieldLists: Config.array().value([]),

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
        tabs: Config.object().value({
            'add': {
                items: ['Elements']
            },
            'edit': {
                items: ['Basic', 'Properties']
            }
        }),
    }

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
    _handleFieldEdit(data) {
        this.emit('fieldEdit', data);
    }

    /**
     * Handle a field move to dispatch the event to add in layout.
     * @param {Event} event
     * @param {Object} data
     * @protected
     */
    _handleFieldMove(data, event) {
        event.preventDefault();

        if (!data.target) {
            return;
        }

        const { fieldLists } = this.props;
        const indexTarget = LayoutSupport.getIndexes(data.target.parentElement);
        const fieldIndex = data.source.getAttribute('data-ddm-field-type-index');
        const fieldProperties = fieldLists[Number(fieldIndex)];

        this.emit('fieldAdd', {
            target: indexTarget,
            fieldProperties,
            data,
        });
    }

    /**
     * Handle click on the previous button.
     * @protected
     */
    _handleOnClickPrevious() {
        this.state.mode = 'add';
    }

    /**
     * Handle click on the tab item and manipulate the active tab.
     * @param {Event} event
     * @param {number} index
     * @protected
     */
    _handleOnClickTab(index, event) {
        event.preventDefault();

        this.state.tabActive = index;
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
     * @returns {bool}
     */
    _isEditMode(mode = this.state.mode) {
        const { fieldFocus, fieldLists, fieldContext } = this.props;

        return !!(mode === 'edit'
            && !(
                Object.keys(fieldFocus).length === 0 
                && fieldFocus.constructor === Object
            )
            && fieldContext.length
            && fieldLists.length);
    }

    /**
     * Set the internal mode state.
     * @param {String} mode
     * @protected
     */
    _setMode(mode) {
        if (this._isEditMode(mode)) {
            this.state.mode = mode;
        }
    }

    /**
     * Start drag and drop and attach events to manipulate.
     * @protected
     */
    _startDrag() {
        this._dragAndDrop = new DragDrop({
            dragPlaceholder: Drag.Placeholder.CLONE,
            sources: '.ddm-drag-item',
            targets: '.ddm-target',
        });

        this._dragAndDrop.on(DragDrop.Events.END, this._handleFieldMove.bind(this));
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
        this.state.show = false;
        this._eventHandler.removeAllListeners();
    }

    /**
     * @inheritDoc
     */
    created() {
        this._eventHandler = new EventHandler();
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
        this.state.show = true;

        this._eventHandler.add(
            dom.on(document, 'click', this._handleDocClick.bind(this), true),
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
            this._isEditMode(nextProps.mode.newVal)
        ) {
            this.show();
        }

        if (
            typeof nextProps.mode !== 'undefined' &&
            nextProps.mode.newVal
        ) {
            this._setMode(nextProps.mode.newVal);
        }
    }

    /**
     * @inheritDoc
     */
    render() {
        const { show, tabActive, mode } = this.state;
        const { 
            spritemap, 
            fieldLists, 
            fieldFocus, 
            context, 
            fieldContext 
        } = this.props;

        const layoutRenderEvents = {
            fieldEdit: this._handleFieldEdit.bind(this)
        }
        let currentField = null;

        if (mode === 'edit') {
            currentField = fieldLists.find(item => {
                return item.type == fieldFocus.type;
            });
        }

        const styles = classnames('sidebar-container', {
            'show': show,
        });

        const angleLeftEvents = {
            click: this._handleOnClickPrevious.bind(this),
        };

        return(
            <div class={styles} ref="sidebar">
                <div class="sidebar sidebar-light">
                    <nav class="component-tbar tbar">
                        <div class="container-fluid">
                            <ul class="tbar-nav">
                                {mode === 'add' && (
                                    <li class="tbar-item tbar-item-expand text-left">
                                        <div class="tbar-section">
                                            <span class="text-truncate-inline">
                                                <span class="text-truncate">Add Elements</span>
                                            </span>
                                        </div>
                                    </li>
                                )}
                                {mode === 'edit' && (
                                    <Fragment>
                                        <li class="tbar-item">
                                            <ClayButton
                                                events={angleLeftEvents}
                                                icon="angle-left"
                                                spritemap={spritemap}
                                                style="secondary"
                                                ref="previousButton"
                                                size="sm"
                                            />
                                        </li>
                                        <li class="tbar-item tbar-item-expand text-left">
                                            <div>
                                                <ClayButton
                                                    icon={currentField.icon}
                                                    spritemap={spritemap}
                                                    style="secondary"
                                                    label={currentField.name}
                                                    size="sm"
                                                    disabled={true}
                                                />
                                            </div>
                                        </li>
                                    </Fragment>
                                )}
                                <li class="tbar-item">
                                    <a class="component-action" href="#1" role="button" data-onclick={this._handleOnClose.bind(this)} ref="close">
                                        <svg aria-hidden="true" class="lexicon-icon lexicon-icon-times">
                                            <use xlink:href={`${spritemap}#times`} />
                                        </svg>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </nav>
                    <nav class="component-navigation-bar navbar navigation-bar navbar-collapse-absolute navbar-expand-md navbar-underline">
                        <a aria-controls="sidebarLightCollapse00" aria-expanded="false" aria-label="Toggle Navigation" class="collapsed navbar-toggler navbar-toggler-link" data-toggle="collapse" href="#sidebarLightCollapse00" role="button">
                            <span class="navbar-text-truncate">Details</span>
                            <svg aria-hidden="true" class="lexicon-icon lexicon-icon-caret-bottom">
                                <use xlink:href={`${spritemap}#caret-bottom`} />
                            </svg>
                        </a>
                        <div class="collapse navbar-collapse" id="sidebarLightCollapse00">
                            <ul class="nav navbar-nav" role="tablist">
                                {this._renderNavItem()}
                            </ul>
                        </div>
                    </nav>
                    <div class="ddm-sidebar-body">
                        {(mode === 'add' && !!fieldLists.length) && (
                            <ul class="list-group">
                                <li class="list-group-header">
                                    <h3 class="list-group-header-title">Basic Elements</h3>
                                </li>
                                {this._renderListElements()}
                            </ul>
                        )}
                        {mode === 'edit' && (
                            <div class="sidebar-body">
                                <div class="tab-content">
                                    <LayoutRenderer
                                        activePage={tabActive}
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
        const { tabActive, mode } = this.state;
        const { tabs } = this.props;

        return tabs[mode].items.map((name, index) => {
            const style = classnames('nav-link', {
                'active': index === tabActive,
            });

            return (
                <li ref={`tab${index}`} class="nav-item" data-onclick={this._handleOnClickTab.bind(this, index)}>
                    <a aria-controls="sidebarLightDetails" class={style} data-toggle="tab" href="#" role="tab">
                        <span class="navbar-text-truncate">{name}</span>
                    </a>
                </li>
            );
        });
    }

    _renderListElements() {
        const { fieldLists, spritemap } = this.props;

        return fieldLists.map((field, index) => {
            return(
                <div ref={`field${index}`} class="ddm-drag-item list-group-item list-group-item-flex" data-ddm-field-type-index={index}>
                    <div class="autofit-col">
                        <div class="sticker sticker-secondary">
                                <svg aria-hidden="true" class={`lexicon-icon lexicon-icon-${field.icon}`}>
                                    <use xlink:href={`${spritemap}#${field.icon}`} />
                                </svg>
                        </div>
                    </div>
                    <div class="autofit-col autofit-col-expand">
                        <h4 class="list-group-title text-truncate">
                            <span>{field.name}</span>
                        </h4>
                        {field.description && (
                            <p class="list-group-subtitle text-truncate">{field.description}</p>
                        )}
                    </div>
                </div>
            );
        });
    }
}

export default Sidebar;
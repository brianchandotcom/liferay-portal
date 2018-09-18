import {Config} from 'metal-state';
import {EventHandler} from 'metal-events';
import {isKeyInSet, isModifyingKey} from './util/dom.es';
import {pageStructure} from './util/config.es';
import {sub} from './util/strings.es';
import autobind from 'autobind-decorator';
import AutoSave from './util/AutoSave.es';
import Builder from './pages/FormBuilder/index.es';
import ClayModal from 'clay-modal';
import Component from 'metal-jsx';
import dom from 'metal-dom';
import core from 'metal';
import LayoutProvider from './components/LayoutProvider/index.es';
import loader from './components/FieldsLoader/index.es';
import PublishButton from './components/PublishButton/PublishButton.es';
import PreviewButton from './components/PreviewButton/PreviewButton.es';
import RuleBuilder from './pages/RuleBuilder/index.es';
import StateSyncronizer from './util/StateSyncronizer.es';

/**
 * Form.
 * @extends Component
 */

class Form extends Component {
	static PROPS = {

		/**
		 * The context for rendering a layout that represents a form.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		context: Config.shapeOf(
			{
				pages: Config.arrayOf(pageStructure),
				paginationMode: Config.string(),
				rules: Config.array(),
				successPageSettings: Config.object()
			}
		).required().setter('_setContext'),

		/**
		 * The default language id of the form.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		defaultLanguageId: Config.string().value(themeDisplay.getDefaultLanguageId()),

		/**
		 * The default language id of the form.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		editingLanguageId: Config.string().value(themeDisplay.getDefaultLanguageId()),

		/**
		 * A map with all translated values available as the form description.
		 * @default 0
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		formInstanceId: Config.string().value(0),

		/**
		 * A map with all translated values available as the form description.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		localizedDescription: Config.object().value({}),

		/**
		 * A map with all translated values available as the form name.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		functionsMetadata: Config.object().value({}),

		/**
		 * The context for rendering a layout that represents a form.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		localizedName: Config.object().value({}),

		/**
		 * The namespace of the portlet.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!string}
		 */

		namespace: Config.string().required(),

		/**
		 * Wether the form is published or not
		 * @default false
		 * @instance
		 * @memberof Form
		 * @type {!boolean}
		 */

		published: Config.bool().value(false),

		/**
		 * The rules of a form.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */
		rolesURL: Config.string().required(),

		/**
		 * The rules of a form.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		rules: Config.array(),

		/**
		 * The path to the SVG spritemap file containing the icons.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!string}
		 */

		spritemap: Config.string().required(),

		/**
		 * Map of translated strings
		 * @default {}
		 * @instance
		 * @memberof Form
		 * @type {!object}
		 */

		strings: Config.object().value({})
	};

	static STATE = {

		/**
		 * Represent the current active screen mode where 0 => FormBuilder and 1 => RuleBuilder
		 * @default 0
		 * @instance
		 * @memberof Form
		 * @type {!number}
		 */

		activeFormMode: Config.number().value(0),

		/**
		 * Internal mirror of the pages state
		 * @default _pagesValueFn
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		pages: Config.arrayOf(pageStructure).valueFn('_pagesValueFn'),

		/**
		 * @default _paginationModeValueFn
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		paginationMode: Config.string().valueFn('_paginationModeValueFn'),

		/**
		 * The label of the save button
		 * @default 'save-form'
		 * @instance
		 * @memberof Form
		 * @type {!string}
		 */

		saveButtonLabel: Config.string().valueFn('_saveButtonLabelValueFn')
	}

	@autobind
	_resolvePublishURL() {
		return this._autoSave.save(false).then(
			() => {
				return {
					formInstanceId: this._getFormInstanceId(),
					publishURL: this._createFormURL()
				};
			}
		);
	}

	@autobind
	_resolvePreviewURL() {
		return this._autoSave.save(true).then(
			() => {
				return `${this._createFormURL()}/preview`;
			}
		);
	}

	_saveButtonLabelValueFn() {
		const {strings} = this.props;

		return strings['save-form'];
	}

	checkEditorLimit(event, limit) {
		const charCode = (event.which) ? event.which : event.keyCode;

		if (this.isForbiddenKey(event, limit) && (charCode != 91)) {
			event.preventDefault();
		}
	}

	disposed() {
		super.disposed();

		if (this._autoSave) {
			this._autoSave.dispose();
		}

		this._eventHandler.removeAllListeners();
	}

	/**
	 * @inheritDoc
	 */

	attached() {
		const {layoutProvider} = this.refs;
		const {localizedDescription, localizedName, namespace} = this.props;
		const {paginationMode} = this.state;

		this._eventHandler = new EventHandler();

		Promise.all(
			[
				this._createEditor('nameEditor').then(
					editor => {
						this._eventHandler.add(
							dom.on(editor.element.$, 'keydown', this._handleNameEditorKeydown.bind(this)),
							dom.on(editor.element.$, 'keyup', this._handleNameEditorCopyAndPaste.bind(this)),
							dom.on(editor.element.$, 'keypress', this._handleNameEditorCopyAndPaste.bind(this))
						);

						return editor;
					}
				),
				this._createEditor('descriptionEditor'),
				this._getSettingsDDMForm()
			]
		).then(
			results => {
				const translationManager = Liferay.component(`${namespace}translationManager`);

				this._stateSyncronizer = new StateSyncronizer(
					{
						descriptionEditor: results[1],
						layoutProvider,
						localizedDescription,
						localizedName,
						nameEditor: results[0],
						namespace,
						paginationMode,
						settingsDDMForm: results[2],
						translationManager
					},
					this.element
				);

				this._autoSave = new AutoSave(
					{
						form: document.querySelector(`#${namespace}editForm`),
						interval: Liferay.DDM.FormSettings.autosaveInterval,
						namespace,
						stateSyncronizer: this._stateSyncronizer,
						url: Liferay.DDM.FormSettings.autosaveURL
					},
					this.element
				);

				this._eventHandler.add(this._autoSave.on('autosaved', this._updateAutoSaveMessage.bind(this)));
			}
		);

		this._eventHandler.add(
			dom.on('.back-url-link', 'click', this._handleBackButtonClicked.bind(this)),
			dom.on('.forms-management-bar li', 'click', this._handleFormNavClicked.bind(this))
		);
	}

	_createFormURL() {
		let formURL;

		const settingsDDMForm = Liferay.component('settingsDDMForm');

		const requireAuthenticationField = settingsDDMForm.getField('requireAuthentication');

		if (requireAuthenticationField.getValue()) {
			formURL = Liferay.DDM.FormSettings.restrictedFormURL;
		}
		else {
			formURL = Liferay.DDM.FormSettings.sharedFormURL;
		}

		return formURL + this._getFormInstanceId();
	}

	_getFormInstanceId() {
		const {namespace} = this.props;

		return document.querySelector(`#${namespace}formInstanceId`).value;
	}

	_getSettingsDDMForm() {
		let promise;

		const settingsDDMForm = Liferay.component('settingsDDMForm');

		if (settingsDDMForm) {
			promise = Promise.resolve(settingsDDMForm);
		}
		else {
			promise = Liferay.componentReady('settingsDDMForm');
		}

		return promise;
	}

	_handleBackButtonClicked(event) {
		if (this._autoSave.hasUnsavedChanges()) {
			event.preventDefault();
			event.stopPropagation();

			const href = event.delegateTarget.href;

			this.refs.discardChangesModal.visible = true;

			const listener = this.refs.discardChangesModal.addListener(
				'clickButton',
				({target}) => {
					if (target.classList.contains('close-modal')) {
						window.location.href = href;
					}

					listener.dispose();

					this.refs.discardChangesModal.emit('hide');
				}
			);
		}
	}

	_updateAutoSaveMessage({savedAsDraft, modifiedDate}) {
		const {namespace, strings} = this.props;

		let message = '';

		if (savedAsDraft) {
			message = strings['draft-x'];
		}
		else {
			message = strings['saved-x'];
		}

		const autoSaveMessageNode = document.querySelector(`#${namespace}autosaveMessage`);

		autoSaveMessageNode.innerHTML = sub(
			message,
			[
				modifiedDate
			]
		);
	}

	isForbiddenKey(event, limit) {
		const charCode = event.which ? event.which : event.keyCode;
		let forbidden = false;

		if (
			event.target.innerText.length >= limit &&
			isModifyingKey(charCode) &&
			!isKeyInSet(charCode, ['BACKSPACE', 'DELETE', 'ESC', 'ENTER'])
		) {
			forbidden = true;
		}
		return forbidden;
	}

	preventCopyAndPaste(event, limit) {
		const {target} = event;

		if (this.isForbiddenKey(event, limit)) {
			target.innerText = target.innerText.substr(0, limit);

			const range = document.createRange();
			const sel = window.getSelection();

			range.setStart(target.childNodes[0], target.textContent.length);
			range.collapse(true);

			sel.removeAllRanges();
			sel.addRange(range);
		}
	}

	/**
	 * @inheritDoc
	 */

	render() {
		const {
			context,
			formInstanceId,
			namespace,
			published,
			spritemap,
			strings
		} = this.props;

		const {saveButtonLabel} = this.state;

		const layoutProviderProps = {
			...this.props,
			events: {
				pagesChanged: this._handlePagesChanged.bind(this),
				paginationModeChanged: this._handlePaginationModeChanded.bind(this)
			},
			initialPages: context.pages,
			initialPaginationMode: context.paginationMode,
			initialSuccessPageSettings: context.successPageSettings,
			ref: 'layoutProvider'
		};

		const showRuleBuilder = parseInt(this.state.activeFormMode, 10) === 1;

		return (
			<div class={'ddm-form-builder'}>
				<LayoutProvider {...layoutProviderProps}>
					<RuleBuilder
						functionsMetadata={this.props.functionsMetadata}
						pages={context.pages}
						rules={this.props.rules}
						spritemap={spritemap}
						url={this.props.rolesURL}
						visible={showRuleBuilder}
					/>
					<Builder
						namespace={this.props.namespace}
						ref="builder"
						visible={!showRuleBuilder}
					/>
				</LayoutProvider>

				<div class="container-fluid-1280">
					<div class="button-holder ddm-form-builder-buttons">
						<PublishButton
							formInstanceId={formInstanceId}
							namespace={namespace}
							published={published}
							resolvePublishURL={this._resolvePublishURL}
							spritemap={spritemap}
							url={Liferay.DDM.FormSettings.publishFormInstanceURL}
						/>
						<button class="btn ddm-button btn-default" data-onclick="_handleSaveButtonClicked" ref="saveButton">
							{saveButtonLabel}
						</button>
						<PreviewButton
							namespace={namespace}
							resolvePreviewURL={this._resolvePreviewURL}
							spritemap={spritemap}
						/>
					</div>

					<ClayModal
						body={strings['any-unsaved-changes-will-be-lost-are-you-sure-you-want-to-leave']}
						footerButtons={
							[
								{
									'alignment': 'right',
									'label': strings.leave,
									'style': 'secondary',
									'type': 'close'
								},
								{
									'alignment': 'right',
									'label': strings.stay,
									'style': 'primary',
									'type': 'button'
								}
							]
						}
						ref={'discardChangesModal'}
						size={'sm'}
						spritemap={spritemap}
						title={strings['leave-form']}
					/>
				</div>
			</div>
		);
	}

	submitForm() {
		const {namespace} = this.props;

		this._stateSyncronizer.syncInputs();

		submitForm(document.querySelector(`#${namespace}editForm`));
	}

	_paginationModeValueFn() {
		const {context} = this.props;

		return context.paginationMode;
	}

	_createEditor(name) {
		const {namespace} = this.props;

		const editorName = `${namespace}${name}`;

		const editor = window[editorName];

		let promise;

		if (editor) {
			editor.create();

			promise = Promise.resolve(CKEDITOR.instances[editorName]);
		}
		else {
			promise = new Promise(
				resolve => {
					Liferay.on(
						'editorAPIReady',
						event => {
							if (event.editorName === editorName) {
								event.editor.create();

								resolve(CKEDITOR.instances[editorName]);
							}
						}
					);
				}
			);
		}

		return promise;
	}

	/**
	 * @param newVal
	 * Handles "paginationModeChanged" event. Updates the page mode to use it when form builder is saved.
	 */

	_handlePaginationModeChanded({newVal}) {
		this.setState(
			{
				paginationMode: newVal
			}
		);
	}

	_handleFormNavClicked(event) {
		const {delegateTarget, target} = event;
		const {navItemIndex} = delegateTarget.dataset;

		if (navItemIndex !== this.state.activeFormMode) {
			const newActiveFormMode = parseInt(navItemIndex, 10);

			this.setState(
				{
					activeFormMode: newActiveFormMode
				}
			);

			document.querySelector('.forms-management-bar li > a.active').classList.remove('active');
			target.classList.add('active');

			this.syncActiveFormMode(newActiveFormMode);
		}
	}

	syncActiveFormMode(activeFormMode) {
		const formBasicInfo = document.querySelector('.ddm-form-basic-info');
		const formBuilderButtons = document.querySelector('.ddm-form-builder-buttons');
		const publishIcon = document.querySelector('.publish-icon');
		const translationManager = document.querySelector('.ddm-translation-manager');

		if (parseInt(activeFormMode, 10)) {
			formBasicInfo.classList.add('hide');
			formBuilderButtons.classList.add('hide');
			publishIcon.classList.add('hide');
			translationManager.classList.add('hide');
		}
		else {
			formBasicInfo.classList.remove('hide');
			formBuilderButtons.classList.remove('hide');
			publishIcon.classList.remove('hide');
			translationManager.classList.remove('hide');
		}
	}

	/*
	 * Handles "pagesChanged" event. Updates hidden input with serialized From Builder context.
	 * @param {!Event} event
	 * @private
	 */

	_handlePagesChanged(event) {
		this.setState(
			{
				pages: event.newVal
			}
		);
	}

	/**
	 * Handles click on save button. Saves Form when clicked.
	 * @param {!Event} event
	 * @private
	 */

	_handleSaveButtonClicked(event) {
		const {strings} = this.props;

		event.preventDefault();

		this.setState(
			{
				saveButtonLabel: strings.saving
			}
		);

		this.submitForm();
	}

	_handleNameEditorCopyAndPaste(event) {
		return this.preventCopyAndPaste(event, 120);
	}

	_handleNameEditorKeydown(event) {
		return this.checkEditorLimit(event, 120);
	}

	_openSidebar() {
		const {builder} = this.refs;

		if (builder) {
			const {sidebar} = builder.refs;

			sidebar.open();
		}
	}

	_pagesValueFn() {
		const {context} = this.props;

		return context.pages;
	}

	/*
	 * Returns the map with all translated names or a map with just "Intitled Form" in case
	 * there are no translations available.
	 * @private
	 */

	_setContext(context) {
		let {successPageSettings} = context;
		const {successPage} = context;

		if (!successPageSettings) {
			successPageSettings = successPage;
		}

		if (core.isString(successPageSettings.title)) {
			successPageSettings.title = {};
			successPageSettings.title[themeDisplay.getLanguageId()] = '';
		}

		if (core.isString(successPageSettings.body)) {
			successPageSettings.body = {};
			successPageSettings.body[themeDisplay.getLanguageId()] = '';
		}

		if (!context.pages.length) {
			context = {
				...context,
				pages: [
					{
						description: '',
						localizedDescription: {
							[themeDisplay.getLanguageId()]: ''
						},
						localizedTitle: {
							[themeDisplay.getLanguageId()]: ''
						},
						rows: [
							{
								columns: [
									{
										fields: [],
										size: 12
									}
								]
							}
						],
						title: ''
					}
				],
				paginationMode: 'wizard',
				successPageSettings
			};
		}

		return context;
	}
}

const DDMForm = (props, container, callback) => {
	loader(
		() => callback(new Form(props, container)),
		props.modules,
		[...props.dependencies]
	);
};

export default DDMForm;
export {DDMForm};
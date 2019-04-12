import AutoSave from './util/AutoSave.es';
import ClayModal from 'clay-modal';
import Component from 'metal-jsx';
import core from 'metal';
import dom from 'metal-dom';
import FormBuilder from 'dynamic-data-mapping-form-builder/js/components/FormBuilder/FormBuilder.es';
import LayoutProvider from 'dynamic-data-mapping-form-builder/js/components/LayoutProvider/LayoutProvider.es';
import Notifications from './util/Notifications.es';
import PreviewButton from './components/PreviewButton/PreviewButton.es';
import PublishButton from './components/PublishButton/PublishButton.es';
import RuleBuilder from 'dynamic-data-mapping-form-builder/js/components/RuleBuilder/RuleBuilder.es';
import ShareFormPopover from './components/ShareFormPopover/ShareFormPopover.es';
import StateSyncronizer from './util/StateSyncronizer.es';
import {Config} from 'metal-state';
import {EventHandler} from 'metal-events';
import {isKeyInSet, isModifyingKey} from 'dynamic-data-mapping-form-builder/js/util/dom.es';
import {pageStructure} from 'dynamic-data-mapping-form-builder/js/util/config.es';
import {sub} from 'dynamic-data-mapping-form-builder/js/util/strings.es';

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
				pages: Config.arrayOf(Config.object()),
				paginationMode: Config.string(),
				rules: Config.array(),
				successPageSettings: Config.object()
			}
		).required().setter('_setContext'),

		/**
		 * The rules of a form.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		dataProviderInstanceParameterSettingsURL: Config.string().required(),

		/**
		 * The rules of a form.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		dataProviderInstancesURL: Config.string().required(),

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
		 * @default []
		 * @instance
		 * @memberof Form
		 * @type {?(array|undefined)}
		 */

		fieldTypes: Config.array().value([]),

		/**
		 * A map with all translated values available as the form description.
		 * @default 0
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		formInstanceId: Config.number().value(0),

		/**
		 * A map with all translated values available as the form name.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		functionsMetadata: Config.object().value({}),

		/**
		 * A map with all translated values available as the form name.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		functionsURL: Config.string(),

		/**
		 * A map with all translated values available as the form description.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		localizedDescription: Config.object().value({}),

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
		 * The url to be redirected when canceling the Element Set edition.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!string}
		 */

		redirectURL: Config.string(),

		/**
		 * The rules of a form.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		rolesURL: Config.string(),

		/**
		 * The rules of a form.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		rules: Config.array().value([]),

		/**
		 * The path to the SVG spritemap file containing the icons.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!boolean}
		 */

		saved: Config.bool(),

		/**
		 * Wether to show an alert telling the user about the result of the
		 * "Publish" operation.
		 * @default false
		 * @instance
		 * @memberof Form
		 * @type {!boolean}
		 */

		showPublishAlert: Config.bool().value(false),

		/**
		 * The path to the SVG spritemap file containing the icons.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!string}
		 */

		spritemap: Config.string().required(),

		view: Config.string()
	};

	static STATE = {

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
		 * Wether the RuleBuilder should be visible or not.
		 * @default false
		 * @instance
		 * @memberof Form
		 * @type {!boolean}
		 */

		ruleBuilderVisible: Config.bool().value(false),

		/**
		 * The label of the save button
		 * @default 'save-form'
		 * @instance
		 * @memberof Form
		 * @type {!string}
		 */

		saveButtonLabel: Config.string().valueFn('_saveButtonLabelValueFn')
	}

	attached() {
		const {layoutProvider} = this.refs;
		const {
			localizedDescription,
			localizedName,
			namespace,
			published,
			showPublishAlert
		} = this.props;
		const {paginationMode} = this.state;

		this._eventHandler = new EventHandler();

		const dependencies = [
			this._createEditor('nameEditor').then(
				editor => {
					this._eventHandler.add(
						dom.on(editor.element.$, 'keydown', this._handleNameEditorKeydown),
						dom.on(editor.element.$, 'keyup', this._handleNameEditorCopyAndPaste),
						dom.on(editor.element.$, 'keypress', this._handleNameEditorCopyAndPaste)
					);

					return editor;
				}
			),
			this._createEditor('descriptionEditor')
		];

		if (this.isFormBuilderView()) {
			dependencies.push(this._getSettingsDDMForm());
			dependencies.push(this._getTranslationManager());
		}

		Promise.all(dependencies).then(
			results => {
				const translationManager = results[3];

				if (translationManager) {
					translationManager.on(
						'editingLocaleChange',
						event => {
							this.props.editingLanguageId = event.newVal;
						}
					);

					translationManager.on(
						'deleteAvailableLocale',
						event => {
							layoutProvider.emit('languageIdDeleted', event);
						}
					);
				}

				this._stateSyncronizer = new StateSyncronizer(
					{
						descriptionEditor: results[1],
						layoutProvider,
						localizedDescription,
						localizedName,
						nameEditor: results[0],
						namespace,
						paginationMode,
						published,
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

				this._eventHandler.add(this._autoSave.on('autosaved', this._updateAutoSaveMessage));
			}
		);

		this._eventHandler.add(
			dom.on('.back-url-link', 'click', this._handleBackButtonClicked),
			dom.on('.forms-management-bar li', 'click', this._handleFormNavClicked)
		);

		if (showPublishAlert) {
			if (published) {
				this._showPublishedAlert(this._createFormURL());
			}
			else {
				this._showUnpublishedAlert();
			}
		}
	}

	checkEditorLimit(event, limit) {
		const charCode = (event.which) ? event.which : event.keyCode;

		if (this.isForbiddenKey(event, limit) && (charCode != 91)) {
			event.preventDefault();
		}
	}

	created() {
		this._createFormURL = this._createFormURL.bind(this);
		this._handleBackButtonClicked = this._handleBackButtonClicked.bind(this);
		this._handleFormNavClicked = this._handleFormNavClicked.bind(this);
		this._handleNameEditorCopyAndPaste = this._handleNameEditorCopyAndPaste.bind(this);
		this._handleNameEditorKeydown = this._handleNameEditorKeydown.bind(this);
		this._handlePaginationModeChanded = this._handlePaginationModeChanded.bind(this);
		this._resolvePreviewURL = this._resolvePreviewURL.bind(this);
		this._updateAutoSaveMessage = this._updateAutoSaveMessage.bind(this);
		this.submitForm = this.submitForm.bind(this);
	}

	disposed() {
		super.disposed();

		if (this._autoSave) {
			this._autoSave.dispose();
		}

		Notifications.closeAlert();

		this._eventHandler.removeAllListeners();
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

	isFormBuilderView() {
		const {view} = this.props;

		return view !== 'fieldSets';
	}

	isShowRuleBuilder() {
		const {ruleBuilderVisible} = this.state;

		return ruleBuilderVisible && this.isFormBuilderView();
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

	render() {
		const {
			context,
			defaultLanguageId,
			editingLanguageId,
			fieldSetDefinitionURL,
			fieldSets,
			fieldTypes,
			groupId,
			namespace,
			published,
			redirectURL,
			spritemap,
			view
		} = this.props;

		const layoutProviderProps = {
			...this.props,
			defaultLanguageId,
			editingLanguageId,
			events: {
				paginationModeChanged: this._handlePaginationModeChanded
			},
			initialPages: context.pages,
			initialPaginationMode: context.paginationMode,
			initialSuccessPageSettings: context.successPageSettings,
			ref: 'layoutProvider'
		};
		const {saveButtonLabel} = this.state;

		return (
			<div class={'ddm-form-builder'}>
				<LayoutProvider {...layoutProviderProps}>
					<RuleBuilder
						dataProviderInstanceParameterSettingsURL={this.props.dataProviderInstanceParameterSettingsURL}
						dataProviderInstancesURL={this.props.dataProviderInstancesURL}
						fieldTypes={fieldTypes}
						functionsMetadata={this.props.functionsMetadata}
						functionsURL={this.props.functionsURL}
						pages={context.pages}
						rolesURL={this.props.rolesURL}
						rules={this.props.rules}
						spritemap={spritemap}
						visible={this.isShowRuleBuilder()}
					/>

					{!this.isShowRuleBuilder() && (
						<FormBuilder
							fieldSetDefinitionURL={fieldSetDefinitionURL}
							fieldSets={fieldSets}
							fieldTypes={fieldTypes}
							groupId={groupId}
							namespace={this.props.namespace}
							ref="builder"
							rules={this.props.rules}
							spritemap={spritemap}
							view={view}
							visible={!this.isShowRuleBuilder()}
						/>
					)}
				</LayoutProvider>

				<div class="container-fluid-1280">
					{this.isFormBuilderView() && (
						<div class="button-holder ddm-form-builder-buttons">
							<PublishButton
								namespace={namespace}
								published={published}
								spritemap={spritemap}
								submitForm={this.submitForm}
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
					)}

					{!this.isFormBuilderView() && (
						<div class="button-holder ddm-form-builder-buttons">
							<button
								class="btn btn-primary ddm-button btn-default"
								data-onclick="_handleSaveButtonClicked"
								ref="saveFieldSetButton"
							>
								{saveButtonLabel}
							</button>
							<a
								class="btn btn-cancel btn-default btn-link"
								data-onclick="_handleCancelButtonClicked"
								href={redirectURL}
								ref="cancelFieldSetButton"
							>
								{Liferay.Language.get('cancel')}
							</a>
						</div>
					)}

					<ClayModal
						body={Liferay.Language.get('any-unsaved-changes-will-be-lost-are-you-sure-you-want-to-leave')}
						footerButtons={
							[
								{
									'alignment': 'right',
									'label': Liferay.Language.get('leave'),
									'style': 'secondary',
									'type': 'close'
								},
								{
									'alignment': 'right',
									'label': Liferay.Language.get('stay'),
									'style': 'primary',
									'type': 'button'
								}
							]
						}
						ref={'discardChangesModal'}
						size={'sm'}
						spritemap={spritemap}
						title={Liferay.Language.get('leave-form')}
					/>
				</div>
				{published && (
					<ShareFormPopover
						alignElement={document.querySelector('.share-form-icon')}
						spritemap={spritemap}
						url={this._createFormURL()}
						visible={false}
					/>
				)}
			</div>
		);
	}

	submitForm() {
		const {namespace} = this.props;

		this._stateSyncronizer.syncInputs();

		submitForm(document.querySelector(`#${namespace}editForm`));
	}

	syncRuleBuilderVisible(ruleBuilderVisible) {
		const {published, saved} = this.props;
		const formBasicInfo = document.querySelector('.ddm-form-basic-info');
		const formBuilderButtons = document.querySelector('.ddm-form-builder-buttons');
		const publishIcon = document.querySelector('.publish-icon');
		const shareURLButton = document.querySelector('.lfr-ddm-share-url-button');
		const translationManager = document.querySelector('.ddm-translation-manager');

		if (ruleBuilderVisible) {
			formBasicInfo.classList.add('hide');
			formBuilderButtons.classList.add('hide');
			shareURLButton.classList.add('hide');

			if (publishIcon) {
				publishIcon.classList.add('hide');
			}

			if (translationManager) {
				translationManager.classList.add('hide');
			}
		}
		else {
			formBasicInfo.classList.remove('hide');
			formBuilderButtons.classList.remove('hide');

			if (publishIcon) {
				publishIcon.classList.remove('hide');
			}

			if (translationManager) {
				translationManager.classList.remove('hide');
			}

			if (saved || published) {
				shareURLButton.classList.remove('hide');
			}
		}
	}

	willReceiveProps({published = {}}) {
		if (published.newVal != null) {
			this._updateShareFormIcon(published.newVal);
		}
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

	_createFormURL() {
		let formURL;

		const settingsDDMForm = Liferay.component('settingsDDMForm');

		if (settingsDDMForm && settingsDDMForm.getField('requireAuthentication').getValue()) {
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

	_getTranslationManager() {
		let promise;

		const translationManager = Liferay.component('translationManager');

		if (translationManager) {
			promise = Promise.resolve(translationManager);
		}
		else {
			promise = Liferay.componentReady('translationManager');
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

	_handleCancelButtonClicked(event) {
		const href = event.delegateTarget.href;

		event.preventDefault();
		event.stopPropagation();

		window.location.href = href;
	}

	_handleFormNavClicked(event) {
		const {delegateTarget} = event;
		const navItem = dom.closest(delegateTarget, '.nav-item');
		const navItemIndex = Number(navItem.dataset.navItemIndex);
		const navLink = navItem.querySelector('.nav-link');

		this.setState(
			{
				ruleBuilderVisible: navItemIndex === 1
			}
		);

		document.querySelector('.forms-management-bar li > a.active').classList.remove('active');
		navLink.classList.add('active');

		this.syncRuleBuilderVisible(this.state.ruleBuilderVisible);
	}

	_handleNameEditorCopyAndPaste(event) {
		return this.preventCopyAndPaste(event, 120);
	}

	_handleNameEditorKeydown(event) {
		return this.checkEditorLimit(event, 120);
	}

	_handlePaginationModeChanded({newVal}) {
		this.setState(
			{
				paginationMode: newVal
			}
		);
	}

	_handleSaveButtonClicked(event) {
		event.preventDefault();

		this.setState(
			{
				saveButtonLabel: Liferay.Language.get('saving')
			}
		);

		this.submitForm();
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

	_paginationModeValueFn() {
		const {context} = this.props;

		return context.paginationMode;
	}

	_resolvePreviewURL() {
		return this._autoSave.save(true).then(
			() => {
				return `${this._createFormURL()}/preview`;
			}
		);
	}

	_saveButtonLabelValueFn() {
		let label = Liferay.Language.get('save');

		if (this.isFormBuilderView()) {
			label = Liferay.Language.get('save-form');
		}

		return label;
	}

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

		const emptyLocalizableValue = {
			[themeDisplay.getLanguageId()]: ''
		};

		if (!context.pages.length) {
			context = {
				...context,
				pages: [
					{
						description: '',
						localizedDescription: emptyLocalizableValue,
						localizedTitle: emptyLocalizableValue,
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

		return {
			...context,
			pages: context.pages.map(
				page => {
					let {description, localizedDescription, localizedTitle, title} = page;

					if (!core.isString(description)) {
						description = description[themeDisplay.getLanguageId()];
						localizedDescription = {
							[themeDisplay.getLanguageId()]: description
						};
					}

					if (!core.isString(title)) {
						title = title[themeDisplay.getLanguageId()];
						localizedTitle = {
							[themeDisplay.getLanguageId()]: title
						};
					}

					return {
						...page,
						description,
						localizedDescription,
						localizedTitle,
						title
					};
				}
			)
		};
	}

	_showPublishedAlert(publishURL) {
		const message = Liferay.Language.get('the-form-was-published-successfully-access-it-with-this-url-x');

		Notifications.showAlert(
			message.replace(
				/\{0\}/gim,
				`<span style="font-weight: 500"><a href=${publishURL} target="_blank">${publishURL}</a></span>`
			)
		);
	}

	_showUnpublishedAlert() {
		Notifications.showAlert(Liferay.Language.get('the-form-was-unpublished-successfully'));
	}

	_updateAutoSaveMessage({savedAsDraft, modifiedDate}) {
		const {namespace} = this.props;

		let message = '';

		if (savedAsDraft) {
			message = Liferay.Language.get('draft-x');
		}
		else {
			message = Liferay.Language.get('saved-x');
		}

		const autoSaveMessageNode = document.querySelector(`#${namespace}autosaveMessage`);

		autoSaveMessageNode.innerHTML = sub(
			message,
			[
				modifiedDate
			]
		);
	}

	_updateShareFormIcon(published) {
		const {saved} = this.props;
		const shareFormIcon = document.querySelector('.share-form-icon');

		if (saved && published) {
			shareFormIcon.classList.remove('ddm-btn-disabled');
			shareFormIcon.setAttribute('title', Liferay.Language.get('copy-url'));
		}
		else {
			shareFormIcon.classList.add('ddm-btn-disabled');
			shareFormIcon.setAttribute('title', Liferay.Language.get('publish-the-form-to-get-its-shareable-link'));
		}
	}
}

export default Form;
export {Form};
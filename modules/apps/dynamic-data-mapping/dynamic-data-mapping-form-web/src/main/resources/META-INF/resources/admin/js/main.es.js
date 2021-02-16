/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayModal from 'clay-modal';
import {pageStructure} from 'dynamic-data-mapping-form-builder/js/util/config.es';
import {sub} from 'dynamic-data-mapping-form-builder/js/util/strings.es';
import {FormApp, PagesVisitor} from 'dynamic-data-mapping-form-renderer';
import {EventHandler, delegate} from 'frontend-js-web';
import Component from 'metal-jsx';
import {Config} from 'metal-state';

import ShareFormModal from './components/ShareFormModal/ShareFormModal.es';
import AutoSave from './util/AutoSave.es';
import FormURL from './util/FormURL.es';
import Notifications from './util/Notifications.es';
import StateSyncronizer from './util/StateSyncronizer.es';

/**
 * Form.
 * @extends Component
 */

class Form extends Component {
	attached() {
		const {namespace, published, showPublishAlert} = this.props;

		const {paginationMode} = this.state;

		this.store = this.refs.app.reactComponentRef;

		this._eventHandler = new EventHandler();

		const dependencies = [];

		if (this.isFormBuilderView()) {
			dependencies.push(this._getSettingsDDMForm());
		}

		Promise.all(dependencies).then(([settingsDDMForm]) => {
			this._stateSyncronizer = new StateSyncronizer(
				{
					namespace,
					paginationMode,
					published,
					settingsDDMForm,
					store: this.store,
				},
				this.element
			);

			this._autoSave = new AutoSave(
				{
					form: document.querySelector(`#${namespace}editForm`),
					interval: Liferay.DDM.FormSettings.autosaveInterval,
					namespace,
					stateSyncronizer: this._stateSyncronizer,
					url: Liferay.DDM.FormSettings.autosaveURL,
				},
				this.element
			);

			this._eventHandler.add(
				this._autoSave.on('autosaved', this._updateAutoSaveMessage)
			);
		});

		const previewButton = document.querySelector('.lfr-ddm-preview-button');

		if (previewButton) {
			previewButton.addEventListener(
				'click',
				this._handlePreviewButtonClicked
			);
		}

		const saveButton = document.querySelector('.lfr-ddm-save-button');

		if (saveButton) {
			saveButton.addEventListener('click', this._handleSaveButtonClicked);
		}

		const publishButton = document.querySelector('.lfr-ddm-publish-button');

		if (publishButton) {
			publishButton.addEventListener(
				'click',
				this._handlePublishButtonClicked
			);
		}

		this._backButtonClickEventHandler = delegate(
			document.body,
			'click',
			`#${namespace}controlMenu .sites-control-group span.lfr-portal-tooltip`,
			this._handleBackButtonClicked
		);

		const shareURLButton = document.querySelector(
			'.lfr-ddm-share-url-button'
		);

		if (showPublishAlert) {
			if (published) {
				this._showPublishedAlert(this._createFormURL());
				shareURLButton.removeAttribute('title');
			}
			else {
				this._showUnpublishedAlert();
			}
		}
	}

	created() {
		this._handlePreviewButtonClicked = this._handlePreviewButtonClicked.bind(
			this
		);
		this._handleSaveButtonClicked = this._handleSaveButtonClicked.bind(
			this
		);
		this._handlePublishButtonClicked = this._handlePublishButtonClicked.bind(
			this
		);
		this._handleBackButtonClicked = this._handleBackButtonClicked.bind(
			this
		);

		this._createFormURL = this._createFormURL.bind(this);
		this._handlePaginationModeChanded = this._handlePaginationModeChanded.bind(
			this
		);
		this._resolvePreviewURL = this._resolvePreviewURL.bind(this);
		this._updateAutoSaveMessage = this._updateAutoSaveMessage.bind(this);
		this.submitForm = this.submitForm.bind(this);
	}

	disposed() {
		if (this._autoSave) {
			this._autoSave.dispose();
		}

		if (this._stateSyncronizer) {
			this._stateSyncronizer.dispose();
		}

		Notifications.closeAlert();

		this._backButtonClickEventHandler.dispose();

		this._eventHandler.removeAllListeners();

		const previewButton = document.querySelector('.lfr-ddm-preview-button');

		if (previewButton) {
			previewButton.removeEventListener(
				'click',
				this._handlePreviewButtonClicked
			);
		}

		const saveButton = document.querySelector('.lfr-ddm-save-button');

		if (saveButton) {
			saveButton.removeEventListener(
				'click',
				this._handleSaveButtonClicked
			);
		}

		const publishButton = document.querySelector('.lfr-ddm-publish-button');

		if (publishButton) {
			publishButton.removeEventListener(
				'click',
				this._handlePublishButtonClicked
			);
		}
	}

	isFormBuilderView() {
		const {view} = this.props;

		return view !== 'fieldSets';
	}

	publish(event) {
		this.props.published = true;

		return this._savePublished(event, true);
	}

	render() {
		const {
			autocompleteUserURL,
			context,
			namespace,
			published,
			shareFormInstanceURL,
			spritemap,
			...otherProps
		} = this.props;

		const App = FormApp;

		return (
			<div>
				<App
					{...otherProps}
					{...context}
					namespace={namespace}
					ref="app"
					spritemap={spritemap}
				/>

				<div class="container container-fluid-1280">
					<ClayModal
						body={Liferay.Language.get(
							'any-unsaved-changes-will-be-lost-are-you-sure-you-want-to-leave'
						)}
						footerButtons={[
							{
								alignment: 'right',
								label: Liferay.Language.get('leave'),
								style: 'secondary',
								type: 'close',
							},
							{
								alignment: 'right',
								label: Liferay.Language.get('stay'),
								style: 'primary',
								type: 'button',
							},
						]}
						ref="discardChangesModal"
						size="sm"
						spritemap={spritemap}
						title={Liferay.Language.get('leave-form')}
					/>
					{published && (
						<ShareFormModal
							autocompleteUserURL={autocompleteUserURL}
							localizedName={this.store.current.localizedName}
							portletNamespace={namespace}
							shareFormInstanceURL={shareFormInstanceURL}
							spritemap={spritemap}
							url={this._createFormURL()}
						/>
					)}
				</div>
			</div>
		);
	}

	submitForm() {
		const {namespace} = this.props;

		this._stateSyncronizer.syncInputs();

		submitForm(document.querySelector(`#${namespace}editForm`));
	}

	unpublish(event) {
		this.props.published = false;

		return this._savePublished(event, false);
	}

	_createFormURL() {
		const settingsDDMForm = Liferay.component('settingsDDMForm');

		let requireAuthentication = false;

		if (settingsDDMForm && settingsDDMForm.reactComponentRef.current) {
			const settingsPageVisitor = new PagesVisitor(
				settingsDDMForm.reactComponentRef.current.get('pages')
			);

			settingsPageVisitor.mapFields((field) => {
				if (field.fieldName === 'requireAuthentication') {
					requireAuthentication = field.value;
				}
			});
		}

		const formURL = new FormURL(
			this._getFormInstanceId(),
			this.props.published,
			requireAuthentication
		);

		return formURL.create();
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

			const href = event.delegateTarget.firstElementChild.href;

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

	_handlePaginationModeChanded({newVal}) {
		this.setState({
			paginationMode: newVal,
		});
	}

	_handlePreviewButtonClicked() {
		return this._resolvePreviewURL()
			.then((previewURL) => {
				window.open(previewURL, '_blank');

				return previewURL;
			})
			.catch(() => {
				Notifications.showError(
					Liferay.Language.get('your-request-failed-to-complete')
				);
			});
	}

	_handlePublishButtonClicked(event) {
		const {published} = this.props;
		let promise;

		if (published) {
			promise = this.unpublish(event);
		}
		else {
			promise = this.publish(event);
		}

		return promise;
	}

	_handleRulesModified() {
		this._autoSave.save(true);
	}

	_handleSaveButtonClicked(event) {
		event.preventDefault();

		this.setState({
			saveButtonLabel: Liferay.Language.get('saving'),
		});

		this.submitForm();
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
		return this._autoSave.save(true).then(() => {
			return `${this._createFormURL()}/preview`;
		});
	}

	_saveButtonLabelValueFn() {
		let label = Liferay.Language.get('save');

		if (this.isFormBuilderView()) {
			label = Liferay.Language.get('save-form');
		}

		return label;
	}

	_savePublished(event) {
		const {namespace} = this.props;
		const url = Liferay.DDM.FormSettings.publishFormInstanceURL;

		event.preventDefault();

		const form = document.querySelector(`#${namespace}editForm`);

		if (form) {
			form.setAttribute('action', url);
		}

		return Promise.resolve(this.submitForm());
	}

	_setContext(context) {
		const emptyLocalizableValue = {
			[themeDisplay.getLanguageId()]: '',
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
										size: 12,
									},
								],
							},
						],
						title: '',
					},
				],
			};
		}

		return {
			...context,
			pages: context.pages.map((page) => {
				let {
					description,
					localizedDescription,
					localizedTitle,
					title,
				} = page;

				if (description && typeof description !== 'string') {
					description = description[themeDisplay.getLanguageId()];
					localizedDescription = {
						[themeDisplay.getLanguageId()]: description,
					};
				}

				if (title && typeof title !== 'string') {
					title = title[themeDisplay.getLanguageId()];
					localizedTitle = {
						[themeDisplay.getLanguageId()]: title,
					};
				}

				return {
					...page,
					description,
					localizedDescription,
					localizedTitle,
					title,
				};
			}),
		};
	}

	_showPublishedAlert(publishURL) {
		const message = Liferay.Language.get(
			'the-form-was-published-successfully-access-it-with-this-url-x'
		);

		Notifications.showAlert(
			message.replace(
				/\{0\}/gim,
				`<span style="font-weight: 500"><a href=${publishURL} target="_blank">${publishURL}</a></span>`
			)
		);
	}

	_showUnpublishedAlert() {
		Notifications.showAlert(
			Liferay.Language.get('the-form-was-unpublished-successfully')
		);
	}

	_updateAutoSaveMessage({modifiedDate, savedAsDraft}) {
		const {namespace} = this.props;

		let message = '';

		if (savedAsDraft) {
			message = Liferay.Language.get('draft-x');
		}
		else {
			message = Liferay.Language.get('saved-x');
		}

		const autoSaveMessageNode = document.querySelector(
			`#${namespace}autosaveMessage`
		);

		autoSaveMessageNode.innerHTML = sub(message, [modifiedDate]);
	}
}

Form.PROPS = {

	/**
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!string}
	 */

	autocompleteUserURL: Config.string(),

	/**
	 * The context for rendering a layout that represents a form.
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!array}
	 */

	context: Config.shapeOf({
		dataEngineSidebar: Config.bool(),
		pages: Config.arrayOf(Config.object()),
		paginationMode: Config.string(),
		rules: Config.array(),
		successPageSettings: Config.object(),
	})
		.required()
		.setter('_setContext'),

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

	defaultLanguageId: Config.string().value(
		themeDisplay.getDefaultLanguageId()
	),

	/**
	 * The default language id of the form.
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!array}
	 */

	editingLanguageId: Config.string().value(
		themeDisplay.getDefaultLanguageId()
	),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {?string}
	 */

	fieldSetDefinitionURL: Config.string(),

	/**
	 * @default []
	 * @instance
	 * @memberof Form
	 * @type {?(array|undefined)}
	 */

	fieldSets: Config.array().value([]),

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
	 * Whether the form is published or not
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
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!string}
	 */

	shareFormInstanceURL: Config.string(),

	/**
	 * Whether to show an alert telling the user about the result of the
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

	view: Config.string(),
};

Form.STATE = {

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

	saveButtonLabel: Config.string().valueFn('_saveButtonLabelValueFn'),
};

export default Form;
export {Form};

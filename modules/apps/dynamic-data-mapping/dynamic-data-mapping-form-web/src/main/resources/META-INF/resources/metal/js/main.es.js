import {Config} from 'metal-state';
import {pageStructure} from './util/config.es';
import Builder from './pages/builder/index.es';
import Component from 'metal-jsx';
import dom from 'metal-dom';
import LayoutProvider from './components/LayoutProvider/index.es';
import loader from './components/FieldsLoader/index.es';
import RuleBuilder from './pages/RuleBuilder/index.es';
import withAppComposer from './hocs/withAppComposer/index.es';
import {EventHandler} from 'metal-events';

const LayoutProviderWithAppComposer = withAppComposer(LayoutProvider);

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
				rules: Config.array()
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

		spritemap: Config.string().required()
	};

	static STATE = {

		/**
		 * The represent the current active screen mode where 0 => FormBuilder and 1 => RuleBuilder
		 * @default 0
		 * @instance
		 * @memberof Form
		 * @type {!number}
		 */

		activeFormMode: Config.number().value(0),

		/**
		 * The represent the current active screen mode where 0 => FormBuilder and 1 => RuleBuilder
		 * @default _pagesValueFn
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */

		pages: Config.arrayOf(pageStructure).valueFn('_pagesValueFn'),

		/**
		 * The represent the current active screen mode where 0 => FormBuilder and 1 => RuleBuilder
		 * @default 'save-form'
		 * @instance
		 * @memberof Form
		 * @type {!string}
		 */

		saveButtonLabel: Config.string().value(Liferay.Language.get('save-form'))
	}

	/*
	 * Returns the map with all translated names or a map with just "Intitled Form" in case
	 * there are no translations available.
	 * @private
	 */

	_setContext(context) {
		if (!context.pages.length) {
			context = {
				...context,
				pages: [
					{
						description: '',
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
				]
			};
		}

		return context;
	}

	_getLocalizedName() {
		const {
			defaultLanguageId,
			localizedName
		} = this.props;

		if (!localizedName[defaultLanguageId].trim()) {
			localizedName[defaultLanguageId] = Liferay.Language.get('untitled-form');
		}

		return localizedName;
	}

	_getLocalizedDescription() {
		const {localizedDescription} = this.props;

		return localizedDescription;
	}

	_getDescriptionEditor() {
		const {namespace} = this.props;

		return window[`${namespace}descriptionEditor`];
	}

	_getNameEditor() {
		const {namespace} = this.props;

		return window[`${namespace}nameEditor`];
	}

	_getSerializedFormBuilderContext() {
		const state = this.getState();

		const visitor = new PagesVisitor(state.pages);

		return JSON.stringify(
			{
				...state,
				pages: visitor.mapPages(
					page => {
						return {
							...page,
							description: page.localizedDescription,
							title: page.localizedTitle
						};
					}
				)
			}
		);
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

	_handleNameEditorChanged(event) {
		const {editingLanguageId, localizedName} = this.props;
		const nameEditor = this._getNameEditor();

		localizedName[editingLanguageId] = nameEditor.getHTML();
	}

	_handleDescriptionEditorChanged(event) {
		const {editingLanguageId, localizedDescription} = this.props;
		const descriptionEditor = this._getDescriptionEditor();

		localizedDescription[editingLanguageId] = descriptionEditor.getHTML();
	}

	/**
	 * Handles click on save button. Saves Form when clicked.
	 * @param {!Event} event
	 * @private
	 */

	_handleSaveButtonClicked(event) {
		event.preventDefault();

		this.setState(
			{
				saveButtonLabel: Liferay.Language.get('saving')
			}
		);

		this.submitForm();
	}

	/**
	 * Handles click on plus button. Button shows Sidebar when clicked.
	 * @private
	 */

	_handleAddFieldButtonClicked() {
		const {builder} = this.refs;

		if (builder) {
			const {sidebar} = builder.refs;

			sidebar.open();
		}
	}

	created() {
		this._eventHandler = new EventHandler();

		sidebar.open();
	}

	_pagesValueFn() {
		const {context} = this.props;

		return context.pages;
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

	attached() {
		this._eventHandler.add(
			dom.on('#addFieldButton', 'click', this._handleAddFieldButtonClicked.bind(this)),
			dom.on('.forms-management-bar li', 'click', this._handleFormNavClicked.bind(this))
		);

		this._createEditor('nameEditor').then(editor => editor.on('change', this._handleNameEditorChanged.bind(this)));
		this._createEditor('descriptionEditor').then(editor => editor.on('change', this._handleDescriptionEditorChanged.bind(this)));
	}

	_handleFormNavClicked(event) {
		const {delegateTarget, target} = event;
		const {navItemIndex} = delegateTarget.dataset;
		const addButton = document.querySelector('#addFieldButton');
		const formBuilderButtons = document.querySelector('.ddm-form-builder-buttons');
		const publishIcon = document.querySelector('.publish-icon');
		if (navItemIndex !== this.state.activeFormMode) {
			this.setState(
				{
					activeFormMode: parseInt(navItemIndex, 10)
				}
			);
			document.querySelector('.forms-management-bar li>a.active').classList.remove('active');
			if (parseInt(this.state.activeFormMode, 10)) {
				formBuilderButtons.classList.add('hide');
				publishIcon.classList.add('hide');
			}
			else {
				formBuilderButtons.classList.remove('hide');
				addButton.classList.remove('hide');
				publishIcon.classList.remove('hide');
			}
			target.classList.add('active');
		}
	}

	getState() {
		const {
			context,
			defaultLanguageId,
			localizedDescription,
			namespace
		} = this.props;

		const translationManager = Liferay.component(`${namespace}translationManager`);

		return {
			availableLanguageIds: translationManager && translationManager.get('availableLocales'),
			defaultLanguageId,
			description: localizedDescription,
			name: this._getLocalizedName(),
			pages: context.pages,
			rules: [],
			successPageSettings: {
				body: {},
				enabled: false,
				title: {}
			}
		};
	}

	submitForm() {
		const {namespace} = this.props;

		this.refs.nameInput.value = JSON.stringify(this._getLocalizedName());

		submitForm(document.querySelector(`#${namespace}editForm`));
	}

	syncInputValues() {
		const state = this.getState();
		const {
			description,
			name
		} = state;

		const settingsDDMForm = Liferay.component('settingsDDMForm');

		this.refs.descriptionInput.value = JSON.stringify(description);
		this.refs.nameInput.value = JSON.stringify(name);
		this.refs.serializedFormBuilderContextInput.value = this._getSerializedFormBuilderContext();
		this.refs.serializedSettingsContextInput.value = JSON.stringify(settingsDDMForm.toJSON());

		console.log(this.refs.serializedFormBuilderContextInput.value);
	}

	/**
	 * @inheritDoc
	 */

	render() {
		const {
			context,
			namespace,
			spritemap
		} = this.props;

		const {
			saveButtonLabel
		} = this.state;

		const layoutProviderProps = {
			...this.props,
			events: {
				pagesChanged: this._handlePagesChanged.bind(this)
			},
			pages: context.pages
		};

		let currentBuilder = <Builder namespace={this.props.namespace} ref="builder" />;

		if (parseInt(this.state.activeFormMode, 10)) {
			currentBuilder = <RuleBuilder pages={context.pages} rules={this.props.rules} spritemap={spritemap} />;
		}

		const settingsDDMForm = Liferay.component('settingsDDMForm');

		return (
			<div>
				<input name={`${namespace}description`} ref="descriptionInput" type="hidden" value={JSON.stringify(this._getLocalizedDescription())} />
				<input name={`${namespace}name`} ref="nameInput" type="hidden" value={JSON.stringify(this._getLocalizedName())} />
				<input name={`${namespace}serializedFormBuilderContext`} ref="serializedFormBuilderContextInput" type="hidden" value={this._getSerializedFormBuilderContext()} />
				<input name={`${namespace}serializedSettingsContext`} ref="serializedSettingsContextInput" type="hidden" value={settingsDDMForm && JSON.stringify(settingsDDMForm.get('context'))} />

				<LayoutProviderWithAppComposer {...layoutProviderProps}>
					{currentBuilder}
				</LayoutProviderWithAppComposer>

				<div class="container-fluid-1280">
					<div class="button-holder ddm-form-builder-buttons">
						<button class="btn btn-primary ddm-button btn-default" ref="publishButton" type="button">
							{Liferay.Language.get('publish-button')}
						</button>
						<button class="btn ddm-button btn-default" data-onclick="_handleSaveButtonClicked" ref="saveButton">
							{saveButtonLabel}
						</button>
						<button class="btn ddm-button btn-link" ref="previewButton" type="button">
							{Liferay.Language.get('preview-form')}
						</button>
					</div>
				</div>
			</div>
		);
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
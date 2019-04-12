import * as FormSupport from 'dynamic-data-mapping-form-builder/js/components/Form/FormSupport.es';
import Component from 'metal-jsx';
import {Config} from 'metal-state';
import {EventHandler} from 'metal-events';
import {PagesVisitor} from 'dynamic-data-mapping-form-builder/js/util/visitors.es';

class StateSyncronizer extends Component {
	static PROPS = {
		descriptionEditor: Config.any(),
		layoutProvider: Config.any(),
		localizedDescription: Config.object().value({}),
		localizedName: Config.object().value({}),
		nameEditor: Config.any(),
		namespace: Config.string().required(),
		published: Config.bool(),
		settingsDDMForm: Config.any(),
		translationManager: Config.any()
	};

	created() {
		const {descriptionEditor, nameEditor, translationManager} = this.props;

		this._eventHandler = new EventHandler();

		this._eventHandler.add(
			descriptionEditor.on('change', this._handleDescriptionEditorChanged.bind(this)),
			nameEditor.on('change', this._handleNameEditorChanged.bind(this))
		);

		if (translationManager) {
			translationManager.on(
				'editingLocaleChange',
				event => {
					this.syncEditors(event.newVal);
				}
			);
		}
	}

	disposeInternal() {
		super.disposeInternal();

		this._eventHandler.removeAllListeners();
	}

	getAvailableLanguageIds() {
		const {translationManager} = this.props;
		let availableLanguageIds = [{id: this.getDefaultLanguageId()}];

		if (translationManager) {
			availableLanguageIds = translationManager.get('availableLocales');
		}

		return availableLanguageIds.map(({id}) => id);
	}

	getDefaultLanguageId() {
		const {translationManager} = this.props;
		let defaultLanguageId = themeDisplay.getDefaultLanguageId();

		if (translationManager) {
			defaultLanguageId = translationManager.get('defaultLocale');
		}

		return defaultLanguageId;
	}

	getEditingLanguageId() {
		const {translationManager} = this.props;
		let editingLanguageId = this.getDefaultLanguageId();

		if (translationManager) {
			editingLanguageId = translationManager.get('editingLocale');
		}

		return editingLanguageId;
	}

	getState() {
		const {layoutProvider, localizedDescription, localizedName} = this.props;

		const state = {
			availableLanguageIds: this.getAvailableLanguageIds(),
			defaultLanguageId: this.getDefaultLanguageId(),
			description: localizedDescription,
			name: localizedName,
			pages: layoutProvider.state.pages,
			paginationMode: layoutProvider.state.paginationMode,
			rules: layoutProvider.getRules(),
			successPageSettings: layoutProvider.state.successPageSettings
		};

		return state;
	}

	isEmpty() {
		const {layoutProvider} = this.props;

		return FormSupport.emptyPages(layoutProvider.state.pages);
	}

	syncEditors(editingLanguageId) {
		const {
			descriptionEditor,
			localizedDescription,
			localizedName,
			nameEditor
		} = this.props;

		let description = localizedDescription[editingLanguageId];

		if (!description) {
			description = localizedDescription[this.getDefaultLanguageId()];
		}

		window[descriptionEditor.name].setHTML(description);

		let name = localizedName[editingLanguageId];

		if (!name) {
			name = localizedName[this.getDefaultLanguageId()];
		}

		window[nameEditor.name].setHTML(name);
	}

	syncInputs() {
		const {namespace, settingsDDMForm} = this.props;
		const state = this.getState();
		const {
			description,
			name
		} = state;

		if (settingsDDMForm) {
			const settings = settingsDDMForm.get('context');

			document.querySelector(`#${namespace}serializedSettingsContext`).value = JSON.stringify(settings);
		}

		document.querySelector(`#${namespace}name`).value = JSON.stringify(name);
		document.querySelector(`#${namespace}description`).value = JSON.stringify(description);
		document.querySelector(`#${namespace}serializedFormBuilderContext`).value = this._getSerializedFormBuilderContext();
	}

	_getSerializedFormBuilderContext() {
		const state = this.getState();

		const visitor = new PagesVisitor(state.pages);

		const pages = visitor.mapPages(
			page => {
				return {
					...page,
					description: page.localizedDescription,
					title: page.localizedTitle
				};
			}
		);

		visitor.setPages(pages);

		return JSON.stringify(
			{
				...state,
				pages: visitor.mapFields(
					field => {
						return {
							...field,
							settingsContext: {
								...field.settingsContext,
								pages: this._getSerializedSettingsContextPages(field.settingsContext.pages)
							}
						};
					}
				)
			}
		);
	}

	_getSerializedSettingsContextPages(pages) {
		const defaultLanguageId = this.getDefaultLanguageId();
		const visitor = new PagesVisitor(pages);

		return visitor.mapFields(
			field => {
				if (field.type === 'options') {
					const {value} = field;
					const newValue = {};

					for (const locale in value) {
						newValue[locale] = value[locale].filter(({value}) => value !== '');
					}

					if (!newValue[defaultLanguageId]) {
						newValue[defaultLanguageId] = [];
					}

					field = {
						...field,
						value: newValue
					};
				}

				return field;
			}
		);
	}

	_handleDescriptionEditorChanged() {
		const {descriptionEditor, localizedDescription} = this.props;
		const editor = window[descriptionEditor.name];

		localizedDescription[this.getEditingLanguageId()] = editor.getHTML();
	}

	_handleNameEditorChanged() {
		const {localizedName, nameEditor} = this.props;
		const editor = window[nameEditor.name];

		localizedName[this.getEditingLanguageId()] = editor.getHTML();
	}
}

export default StateSyncronizer;
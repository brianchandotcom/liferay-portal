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

import {FormSupport, PagesVisitor} from 'dynamic-data-mapping-form-renderer';
import {EventHandler} from 'metal-events';
import Component from 'metal-jsx';
import {Config} from 'metal-state';

class StateSyncronizer extends Component {
	created() {
		const {descriptionEditor, nameEditor, translationManager} = this.props;

		this._eventHandler = new EventHandler();

		this._eventHandler.add(
			descriptionEditor.on(
				'change',
				this._handleDescriptionEditorChanged.bind(this)
			),
			nameEditor.on('change', this._handleNameEditorChanged.bind(this))
		);

		if (translationManager) {
			this._translationManagerHandles = [
				translationManager.on(
					'availableLocales',
					this.onRemoveAvailableLocales.bind(this)
				),
				translationManager.on('editingLocale', ({newValue}) => {
					this.syncEditors(newValue);
				})
			];
		}
	}

	deleteLanguageId(languageId) {
		const {localizedDescription, localizedName} = this.props;

		delete localizedDescription[languageId];
		delete localizedName[languageId];

		this.syncEditors();
	}

	disposed() {
		this._eventHandler.removeAllListeners();

		if (this._translationManagerHandles) {
			this._translationManagerHandles.forEach(handle => handle.detach());
		}
	}

	getAvailableLanguageIds() {
		const {translationManager} = this.props;
		let availableLanguageIds = [{id: this.getDefaultLanguageId()}];

		if (translationManager) {
			const availableLocalesMap = translationManager.get(
				'availableLocales'
			);

			availableLanguageIds = [...availableLocalesMap.keys()];
		}

		return availableLanguageIds;
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
		const {localizedDescription, localizedName, store} = this.props;

		const state = {
			availableLanguageIds: this.getAvailableLanguageIds(),
			defaultLanguageId: this.getDefaultLanguageId(),
			description: localizedDescription,
			name: localizedName,
			pages: store.state.pages,
			paginationMode: store.state.paginationMode,
			rules: store.getRules(),
			successPageSettings: store.state.successPageSettings
		};

		return state;
	}

	isEmpty() {
		const {store} = this.props;

		return FormSupport.isEmpty(store.state.pages);
	}

	onRemoveAvailableLocales({newValue, previousValue}) {
		const removedItems = new Map();

		previousValue.forEach((value, key) => {
			if (!newValue.has(key)) {
				removedItems.set(key, value);
			}
		});

		if (removedItems.length > 0) {
			this.deleteLanguageId(removedItems.keys().next().value);
		}
	}

	syncEditors(editingLanguageId = this.getDefaultLanguageId()) {
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
		const {description, name} = state;

		Object.keys(state.name).forEach(key => {
			state.name[key] = Liferay.Util.unescape(state.name[key]);
		});

		Object.keys(state.description).forEach(key => {
			state.description[key] = Liferay.Util.unescape(
				state.description[key]
			);
		});

		if (settingsDDMForm) {
			document.querySelector(
				`#${namespace}serializedSettingsContext`
			).value = JSON.stringify({
				pages: settingsDDMForm.pages
			});
		}

		document.querySelector(`#${namespace}name`).value = JSON.stringify(
			name
		);
		document.querySelector(
			`#${namespace}description`
		).value = JSON.stringify(description);
		document.querySelector(
			`#${namespace}serializedFormBuilderContext`
		).value = this._getSerializedFormBuilderContext();
	}

	_getSerializedFormBuilderContext() {
		const state = this.getState();

		const visitor = new PagesVisitor(state.pages);

		const pages = visitor.mapPages(page => {
			return {
				...page,
				description: page.localizedDescription,
				title: page.localizedTitle
			};
		});

		visitor.setPages(pages);

		return JSON.stringify({
			...state,
			pages: visitor.mapFields(field => {
				return {
					...field,
					settingsContext: {
						...field.settingsContext,
						availableLanguageIds: this.getAvailableLanguageIds(),
						defaultLanguageId: this.getDefaultLanguageId(),
						pages: this._getSerializedSettingsContextPages(
							field.settingsContext.pages
						)
					}
				};
			})
		});
	}

	_getSerializedSettingsContextPages(pages) {
		const defaultLanguageId = this.getDefaultLanguageId();
		const visitor = new PagesVisitor(pages);

		return visitor.mapFields(field => {
			if (field.type === 'options') {
				const {value} = field;
				const newValue = {};

				Object.keys(value).forEach(locale => {
					newValue[locale] = value[locale].filter(
						({value}) => value !== ''
					);
				});

				if (!newValue[defaultLanguageId]) {
					newValue[defaultLanguageId] = [];
				}

				field = {
					...field,
					value: newValue
				};
			}

			return field;
		});
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

StateSyncronizer.PROPS = {
	descriptionEditor: Config.any(),
	localizedDescription: Config.object().value({}),
	localizedName: Config.object().value({}),
	nameEditor: Config.any(),
	namespace: Config.string().required(),
	published: Config.bool(),
	settingsDDMForm: Config.any(),
	store: Config.any(),
	translationManager: Config.any()
};

export default StateSyncronizer;

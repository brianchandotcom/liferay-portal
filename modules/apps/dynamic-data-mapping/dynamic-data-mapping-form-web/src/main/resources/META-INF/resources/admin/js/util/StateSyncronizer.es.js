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
import Component from 'metal-jsx';
import {Config} from 'metal-state';

class StateSyncronizer extends Component {
	created() {
		const {descriptionEditor, nameEditor} = this.props;

		descriptionEditor.addEventListener(
			'input',
			this._handleDescriptionEditorChanged.bind(this)
		);

		nameEditor.addEventListener(
			'input',
			this._handleNameEditorChanged.bind(this)
		);
	}

	disposed() {
		const {descriptionEditor, nameEditor} = this.props;

		nameEditor.removeEventListener(
			'input',
			this._handleNameEditorChanged.bind(this)
		);
		descriptionEditor.removeEventListener(
			'input',
			this._handleDescriptionEditorChanged.bind(this)
		);
	}

	getState() {
		const {localizedDescription, localizedName, store} = this.props;

		return {
			availableLanguageIds: store.current.availableLanguageIds,
			defaultLanguageId: store.current.defaultLanguageId,
			description: localizedDescription,
			name: localizedName,
			pages: store.current.pages,
			paginationMode: store.current.paginationMode,
			rules: store.current.rules,
			successPageSettings: store.current.successPageSettings,
		};
	}

	isEmpty() {
		const {store} = this.props;

		return FormSupport.isEmpty(store.current.pages);
	}

	syncInputs() {
		const {namespace, settingsDDMForm} = this.props;
		const state = this.getState();
		const {description, name} = state;

		Object.keys(state.name).forEach((key) => {
			state.name[key] = Liferay.Util.unescape(state.name[key]);
		});

		Object.keys(state.description).forEach((key) => {
			state.description[key] = Liferay.Util.unescape(
				state.description[key]
			);
		});

		if (settingsDDMForm && settingsDDMForm.reactComponentRef.current) {
			document.querySelector(
				`#${namespace}serializedSettingsContext`
			).value = JSON.stringify({
				pages: settingsDDMForm.reactComponentRef.current.get('pages'),
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

		const pages = visitor.mapPages((page) => {
			return {
				...page,
				description: page.localizedDescription,
				title: page.localizedTitle,
			};
		});

		visitor.setPages(pages);

		return JSON.stringify({
			...state,
			pages: visitor.mapFields((field) => {
				return {
					...field,
					settingsContext: {
						...field.settingsContext,
						availableLanguageIds: state.availableLanguageIds,
						defaultLanguageId: state.defaultLanguageId,
						pages: this._getSerializedSettingsContextPages(
							field.settingsContext.pages
						),
					},
				};
			}),
		});
	}

	_getSerializedSettingsContextPages(pages) {
		const defaultLanguageId = this.props.store.current.defaultLanguageId;
		const visitor = new PagesVisitor(pages);

		return visitor.mapFields((field) => {
			if (field.type === 'options') {
				const {value} = field;
				const newValue = {};

				Object.keys(value).forEach((locale) => {
					newValue[locale] = value[locale].filter(
						({value}) => value !== ''
					);
				});

				if (!newValue[defaultLanguageId]) {
					newValue[defaultLanguageId] = [];
				}

				field = {
					...field,
					value: newValue,
				};
			}

			return field;
		});
	}

	_handleDescriptionEditorChanged() {
		const {descriptionEditor, localizedDescription} = this.props;

		localizedDescription[this.getEditingLanguageId()] =
			descriptionEditor.value;
	}

	_handleNameEditorChanged() {
		const {localizedName, nameEditor} = this.props;

		localizedName[this.getEditingLanguageId()] = nameEditor.value;
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
	translationManager: Config.any(),
};

export default StateSyncronizer;

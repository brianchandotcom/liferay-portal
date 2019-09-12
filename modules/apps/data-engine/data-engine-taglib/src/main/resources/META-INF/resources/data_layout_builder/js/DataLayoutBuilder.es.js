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

import Component, {Config} from 'metal-jsx';
import core from 'metal';
import FormBuilder from 'dynamic-data-mapping-form-builder/js/components/FormBuilder/FormBuilder.es';
import LayoutProvider from 'dynamic-data-mapping-form-builder/js/components/LayoutProvider/LayoutProvider.es';
import {pageStructure} from 'dynamic-data-mapping-form-builder/js/util/config.es';
import {PagesVisitor} from 'dynamic-data-mapping-form-renderer/js/util/visitors.es';

/**
 * Data Layout Builder.
 * @extends Component
 */
class DataLayoutBuilder extends Component {
	attached() {
		const {layoutProvider} = this.refs;
		const {localizable} = this.props;

		if (localizable) {
			const dependencies = [this._getTranslationManager()];

			Promise.all(dependencies).then(results => {
				const translationManager = results[0];

				if (translationManager) {
					translationManager.on('availableLocalesChange', event => {
						this.props.availableLanguageIds = event.newVal.map(
							({id}) => id
						);
					});

					translationManager.on('editingLocaleChange', event => {
						this.props.editingLanguageId = event.newVal;
					});

					translationManager.on('deleteAvailableLocale', event => {
						layoutProvider.emit('languageIdDeleted', event);
					});
				}
			});
		}
	}

	dispatch(event, payload) {
		this.refs.layoutProvider.dispatch(event, payload);
	}

	getDefinitionAndLayout(pages) {
		const {availableLanguageIds, defaultLanguageId} = this.props;
		const fieldDefinitions = [];
		const pagesVisitor = new PagesVisitor(pages);

		const newPages = pagesVisitor.mapFields(
			({fieldName, settingsContext}) => {
				const fieldConfig = {
					customProperties: {}
				};
				const settingsContextVisitor = new PagesVisitor(
					settingsContext.pages
				);

				settingsContextVisitor.mapFields(
					({fieldName, localizable, localizedValue, value}) => {
						if (fieldName === 'predefinedValue') {
							fieldName = 'defaultValue';
						} else if (fieldName === 'type') {
							fieldName = 'fieldType';
						}

						if (localizable) {
							if (this._isCustomProperty(fieldName)) {
								fieldConfig.customProperties[
									fieldName
								] = localizedValue;
							} else {
								fieldConfig[fieldName] = localizedValue;
							}
						} else {
							if (this._isCustomProperty(fieldName)) {
								fieldConfig.customProperties[fieldName] = value;
							} else {
								fieldConfig[fieldName] = value;
							}
						}
					},
					false
				);

				fieldDefinitions.push(fieldConfig);

				return fieldName;
			},
			false
		);

		return {
			definition: {
				availableLanguageIds,
				dataDefinitionFields: fieldDefinitions,
				defaultLanguageId
			},
			layout: {
				dataLayoutPages: newPages.map(page => {
					const rows = page.rows.map(row => {
						const columns = row.columns.map(column => {
							return {
								columnSize: column.size,
								fieldNames: column.fields
							};
						});

						return {
							dataLayoutColumns: columns
						};
					});

					return {
						dataLayoutRows: rows,
						description: page.localizedDescription,
						title: page.localizedTitle
					};
				}),
				paginationMode: 'wizard'
			}
		};
	}

	getFieldTypes() {
		const {fieldTypes} = this.props;

		return fieldTypes;
	}

	getProvider() {
		return this.refs.layoutProvider;
	}

	getStore() {
		return {
			...this.refs.layoutProvider.state
		};
	}

	render() {
		const {
			context,
			defaultLanguageId,
			editingLanguageId,
			fieldTypes,
			portletNamespace,
			spritemap
		} = this.props;

		const layoutProviderProps = {
			...this.props,
			defaultLanguageId,
			editingLanguageId,
			events: {
				pagesChanged: this._handlePagesChanged.bind(this)
			},
			initialPages: context.pages,
			initialPaginationMode: context.paginationMode,
			ref: 'layoutProvider'
		};

		const LayoutProviderTag = LayoutProvider;

		return (
			<div class={'ddm-form-builder'}>
				<LayoutProviderTag {...layoutProviderProps}>
					<FormBuilder
						defaultLanguageId={defaultLanguageId}
						editingLanguageId={editingLanguageId}
						fieldTypes={fieldTypes}
						portletNamespace={portletNamespace}
						paginationMode={'wizard'}
						ref="builder"
						spritemap={spritemap}
					/>
				</LayoutProviderTag>
			</div>
		);
	}

	serialize(pages) {
		const {definition, layout} = this.getDefinitionAndLayout(pages);

		return {
			definition: JSON.stringify(definition),
			layout: JSON.stringify(layout)
		};
	}

	_getTranslationManager() {
		let promise;

		const translationManager = Liferay.component('translationManager');

		if (translationManager) {
			promise = Promise.resolve(translationManager);
		} else {
			promise = Liferay.componentReady('translationManager');
		}

		return promise;
	}

	_handlePagesChanged({newVal}) {
		const {dataDefinitionInputId, dataLayoutInputId} = this.props;

		if (dataDefinitionInputId && dataLayoutInputId) {
			const dataDefinitionInput = document.querySelector(
				`#${dataDefinitionInputId}`
			);
			const dataLayoutInput = document.querySelector(
				`#${dataLayoutInputId}`
			);

			const data = this.serialize(newVal);

			dataLayoutInput.value = data.layout;
			dataDefinitionInput.value = data.definition;
		}
	}

	_isCustomProperty(name) {
		const fields = [
			'defaultValue',
			'fieldType',
			'indexable',
			'label',
			'localizable',
			'name',
			'repeatable',
			'tip'
		];

		return fields.indexOf(name) === -1;
	}

	_setContext(context) {
		const {defaultLanguageId} = this.props;

		const emptyLocalizableValue = {
			[defaultLanguageId]: ''
		};

		const pages = context.pages || [];

		if (!pages.length) {
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
				rules: context.rules || []
			};
		}

		return {
			...context,
			pages: context.pages.map(page => {
				let {
					description,
					localizedDescription,
					localizedTitle,
					title
				} = page;

				if (!core.isString(description)) {
					description = description[defaultLanguageId];
					localizedDescription = {
						[defaultLanguageId]: description
					};
				}

				if (!core.isString(title)) {
					title = title[defaultLanguageId];
					localizedTitle = {
						[defaultLanguageId]: title
					};
				}

				return {
					...page,
					description,
					localizedDescription,
					localizedTitle,
					title
				};
			}),
			rules: context.rules || []
		};
	}
}

DataLayoutBuilder.PROPS = {
	availableLanguageIds: Config.array(),
	componentId: Config.string(),
	context: Config.shapeOf({
		pages: Config.arrayOf(pageStructure),
		paginationMode: Config.string(),
		rules: Config.array()
	})
		.required()
		.setter('_setContext'),
	dataDefinitionInputId: Config.string(),
	dataLayoutInputId: Config.string(),
	defaultLanguageId: Config.string().value(
		themeDisplay.getDefaultLanguageId()
	),
	editingLanguageId: Config.string().value(
		themeDisplay.getDefaultLanguageId()
	),
	fieldTypes: Config.array().value([]),
	localizable: Config.bool().value(false),
	portletNamespace: Config.string().required(),
	spritemap: Config.string().required()
};

export default DataLayoutBuilder;
export {DataLayoutBuilder};

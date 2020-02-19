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

import FormBuilderWithLayoutProvider from 'dynamic-data-mapping-form-builder';
import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';
import core from 'metal';
import React from 'react';

import EventEmitter from './EventEmitter.es';
import saveDefinitionAndLayout from './saveDefinitionAndLayout.es';

/**
 * Data Layout Builder.
 * @extends React.Component
 */
class DataLayoutBuilder extends React.Component {
	constructor(props) {
		super(props);

		this.containerRef = React.createRef();
		this.eventEmitter = new EventEmitter();
		this.state = {};
	}

	componentDidMount() {
		const {
			dataLayoutBuilderId,
			fieldTypes,
			localizable,
			portletNamespace
		} = this.props;

		const context = this._setContext(this.props.context);

		this.formBuilderWithLayoutProvider = new FormBuilderWithLayoutProvider(
			{
				events: {
					attached: () => {
						this.props.onLoad(this);

						Liferay.component(dataLayoutBuilderId, this);
					}
				},
				formBuilderProps: {
					fieldTypes,
					paginationMode: 'wizard',
					portletNamespace,
					ref: 'builder'
				},
				layoutProviderProps: {
					...this.props,
					context,
					defaultLanguageId: themeDisplay.getDefaultLanguageId(),
					editingLanguageId: themeDisplay.getDefaultLanguageId(),
					initialPages: context.pages,
					initialPaginationMode: context.paginationMode,
					ref: 'layoutProvider'
				}
			},
			this.containerRef.current
		);

		if (localizable) {
			this._localeChangedHandler = Liferay.after(
				'inputLocalized:localeChanged',
				this._onLocaleChange.bind(this)
			);
		}
	}

	dispatch(event, payload) {
		const layoutProvider = this.getLayoutProvider();

		if (layoutProvider && layoutProvider.dispatch) {
			layoutProvider.dispatch(event, payload);
		}
	}

	dispatchAction(action) {
		const {appContext} = this.props;
		const [, dispatch] = appContext;

		if (dispatch) {
			dispatch(action);
		}
	}

	getState() {
		const {appContext} = this.props;
		const [state] = appContext;

		return state;
	}

	on(eventName, listener) {
		this.eventEmitter.on(eventName, listener);
	}

	removeEventListener(eventName, listener) {
		this.eventEmitter.removeListener(eventName, listener);
	}

	emit(event, payload, error = false) {
		this.eventEmitter.emit(event, payload, error);
	}

	componentWillUnmount() {
		const {dataLayoutBuilderId} = this.props;
		const {formBuilderWithLayoutProvider} = this;

		if (formBuilderWithLayoutProvider) {
			formBuilderWithLayoutProvider.dispose();
		}

		if (this._localeChangedHandler) {
			this._localeChangedHandler.detach();
		}

		Liferay.destroyComponent(dataLayoutBuilderId);
	}

	getDefinitionField({settingsContext}) {
		const fieldConfig = {
			customProperties: {}
		};
		const settingsContextVisitor = new PagesVisitor(settingsContext.pages);

		settingsContextVisitor.mapFields(
			({fieldName, localizable, localizedValue, value}) => {
				if (fieldName === 'predefinedValue') {
					fieldName = 'defaultValue';
				}
				else if (fieldName === 'type') {
					fieldName = 'fieldType';
				}

				if (localizable) {
					if (this._isCustomProperty(fieldName)) {
						fieldConfig.customProperties[
							fieldName
						] = localizedValue;
					}
					else {
						fieldConfig[fieldName] = localizedValue;
					}
				}
				else {
					if (this._isCustomProperty(fieldName)) {
						fieldConfig.customProperties[fieldName] = value;
					}
					else {
						fieldConfig[fieldName] = value;
					}
				}
			},
			false
		);

		return fieldConfig;
	}

	getDefinitionAndLayout(pages) {
		const {
			defaultLanguageId = themeDisplay.getDefaultLanguageId()
		} = this.props;
		const availableLanguageIds = this.state.availableLanguageIds ||
			this.props.availableLanguageIds || [
				themeDisplay.getDefaultLanguageId()
			];
		const fieldDefinitions = [];
		const pagesVisitor = new PagesVisitor(pages);

		const newPages = pagesVisitor.mapFields(field => {
			fieldDefinitions.push(this.getDefinitionField(field));

			return field.fieldName;
		}, false);

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

	getFieldSettingsContext(dataDefinitionField) {
		const {editingLanguageId = themeDisplay.getLanguageId()} = this.props;
		const fieldTypes = this.getFieldTypes();
		const fieldType = fieldTypes.find(({name}) => {
			return name === dataDefinitionField.fieldType;
		});
		const {settingsContext} = fieldType;
		const visitor = new PagesVisitor(settingsContext.pages);

		return {
			...settingsContext,
			pages: visitor.mapFields(field => {
				const {fieldName, localizable} = field;
				const propertyName = this._getDataDefinitionFieldPropertyName(
					fieldName
				);
				const propertyValue = this._getDataDefinitionFieldPropertyValue(
					dataDefinitionField,
					propertyName
				);

				let value = propertyValue;

				if (
					localizable &&
					propertyValue &&
					Object.prototype.hasOwnProperty.call(
						propertyValue,
						editingLanguageId
					)
				) {
					value = propertyValue[editingLanguageId];
				}

				let localizedValue = {};

				if (localizable) {
					localizedValue = {...propertyValue};
				}

				return {
					...field,
					localizedValue,
					value
				};
			})
		};
	}

	getFieldTypes() {
		const {fieldTypes} = this.props;

		return fieldTypes;
	}

	getLayoutProvider() {
		const {layoutProvider} = this.formBuilderWithLayoutProvider.refs;

		return layoutProvider;
	}

	getStore() {
		const layoutProvider = this.getLayoutProvider();

		return {
			...layoutProvider.state
		};
	}

	render() {
		return (
			<div className={'ddm-form-builder'} ref={this.containerRef}></div>
		);
	}

	save(params = {}) {
		const {
			contentType,
			dataDefinitionId,
			dataLayoutId,
			groupId
		} = this.props;
		const {pages} = this.getStore();
		const {
			definition: dataDefinition,
			layout: dataLayout
		} = this.getDefinitionAndLayout(pages);

		return saveDefinitionAndLayout({
			contentType,
			dataDefinition,
			dataDefinitionId,
			dataLayout,
			dataLayoutId,
			groupId,
			params
		});
	}

	serialize(pages) {
		const {definition, layout} = this.getDefinitionAndLayout(pages);

		return {
			definition: JSON.stringify(definition),
			layout: JSON.stringify(layout)
		};
	}

	_isCustomProperty(name) {
		const fields = [
			'defaultValue',
			'fieldType',
			'indexable',
			'indexType',
			'label',
			'localizable',
			'name',
			'readOnly',
			'repeatable',
			'required',
			'showLabel',
			'tip'
		];

		return fields.indexOf(name) === -1;
	}

	_getDataDefinitionFieldPropertyName(propertyName) {
		const map = {
			fieldName: 'name',
			predefinedValue: 'defaultValue',
			type: 'fieldType'
		};

		return map[propertyName] || propertyName;
	}

	_getDataDefinitionFieldPropertyValue(dataDefinitionField, propertyName) {
		const {customProperties} = dataDefinitionField;

		if (customProperties && this._isCustomProperty(propertyName)) {
			return customProperties[propertyName];
		}

		return dataDefinitionField[propertyName];
	}

	_onLocaleChange(event) {
		const selectedLanguageId = event.item.getAttribute('data-value');
		let {availableLanguageIds = []} = this.props;

		availableLanguageIds = [
			...new Set([...availableLanguageIds, selectedLanguageId])
		];

		this.setState({
			availableLanguageIds
		});

		this.formBuilderWithLayoutProvider.props.layoutProviderProps = {
			...this.formBuilderWithLayoutProvider.props.layoutProviderProps,
			availableLanguageIds,
			editingLanguageId: selectedLanguageId
		};
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

export default DataLayoutBuilder;
export {DataLayoutBuilder};

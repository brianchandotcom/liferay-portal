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

import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import FormBuilderWithLayoutProvider from 'dynamic-data-mapping-form-builder';
import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';
import React from 'react';

import {
	getDDMFormField,
	getDDMFormFieldSettingsContext,
	getDataDefinitionField,
	getFieldSetDDMForm,
} from '../utils/dataConverter.es';
import generateDataDefinitionFieldName from '../utils/generateDataDefinitionFieldName.es';
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
			config: {allowMultiplePages, allowNestedFields, allowSuccessPage},
			contentTypeConfig: {allowInvalidAvailableLocalesForProperty},
			dataLayoutBuilderId,
			fieldTypes,
			portletNamespace,
		} = this.props;

		const context = this._setContext(this.props.context);

		this.formBuilderWithLayoutProvider = new FormBuilderWithLayoutProvider(
			{
				events: {
					attached: () => {
						this.props.onLoad(this);

						Liferay.component(dataLayoutBuilderId, this);
					},
				},
				formBuilderProps: {
					allowInvalidAvailableLocalesForProperty,
					allowNestedFields,
					fieldTypes,
					portletNamespace,
					ref: 'builder',
				},
				layoutProviderProps: {
					...this.props,
					allowMultiplePages,
					allowSuccessPage,
					context,
					defaultLanguageId:
						context.defaultLanguageId ||
						themeDisplay.getDefaultLanguageId(),
					editingLanguageId:
						context.defaultLanguageId ||
						themeDisplay.getDefaultLanguageId(),
					initialPages: context.pages,
					ref: 'layoutProvider',
					rules: context.rules,
				},
			},
			this.containerRef.current
		);

		this._localeChangedHandler = Liferay.after(
			'inputLocalized:localeChanged',
			this._onLocaleChange.bind(this)
		);
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

	emit(event, payload, error = false) {
		this.eventEmitter.emit(event, payload, error);
	}

	fieldNameGenerator(dataDefinitionFields) {
		const layoutProvider = this.getLayoutProvider();

		layoutProvider.props = {
			...layoutProvider.props,
			fieldNameGenerator: (
				desiredFieldName,
				currentFieldName,
				blacklist
			) =>
				generateDataDefinitionFieldName(
					dataDefinitionFields,
					desiredFieldName,
					currentFieldName,
					blacklist
				),
			shouldAutoGenerateName: () => false,
		};
	}

	getDataDefinitionAndDataLayout(pages, rules) {
		const {
			defaultLanguageId = themeDisplay.getDefaultLanguageId(),
		} = this.props;
		const availableLanguageIds = this.state.availableLanguageIds ||
			this.props.availableLanguageIds || [
				themeDisplay.getDefaultLanguageId(),
			];
		const fieldDefinitions = [];
		const pagesVisitor = new PagesVisitor(pages);

		const newPages = pagesVisitor.mapFields((field) => {
			fieldDefinitions.push(getDataDefinitionField(field));

			return field.fieldName;
		}, false);

		const layoutProvider = this.getLayoutProvider();

		return {
			definition: {
				availableLanguageIds,
				dataDefinitionFields: fieldDefinitions,
				defaultLanguageId,
			},
			layout: {
				dataLayoutPages: newPages.map((page) => {
					const rows = page.rows.map((row) => {
						const columns = row.columns.map((column) => {
							return {
								columnSize: column.size,
								fieldNames: column.fields,
							};
						});

						return {
							dataLayoutColumns: columns,
						};
					});

					return {
						dataLayoutRows: rows,
						description: page.localizedDescription,
						title: page.localizedTitle,
					};
				}),
				dataRules: rules.map((rule) => {
					if (typeof rule.name === 'string') {
						rule.name = {
							[defaultLanguageId]: rule.name,
						};
					}

					delete rule.ruleEditedIndex;

					return rule;
				}),
				paginationMode: layoutProvider.getPaginationMode(),
			},
		};
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by DataConverter.getDDMFormFieldSettingsContext()
	 */
	getDDMFormFieldSettingsContext(dataDefinitionField, defaultLanguageId) {
		const {fieldTypes} = this.props;
		const {editingLanguageId} = this.getState();

		return getDDMFormFieldSettingsContext({
			dataDefinitionField,
			defaultLanguageId,
			editingLanguageId,
			fieldTypes,
		});
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by DataConverter.getDDMFormField()
	 */
	getDDMFormField(dataDefinition, fieldName) {
		const {
			editingLanguageId = themeDisplay.getDefaultLanguageId(),
			fieldTypes,
		} = this.props;

		return getDDMFormField({
			dataDefinition,
			editingLanguageId,
			fieldName,
			fieldTypes,
		});
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by DataConverter.getFieldSetDDMForm()
	 */
	getFieldSetDDMForm({availableLanguageIds, fieldSet}) {
		const {
			contentTypeConfig: {allowInvalidAvailableLocalesForProperty},
			editingLanguageId,
			fieldTypes,
		} = this.props;

		return getFieldSetDDMForm({
			allowInvalidAvailableLocalesForProperty,
			availableLanguageIds,
			editingLanguageId,
			fieldSet,
			fieldTypes,
		});
	}

	getFieldTypes() {
		const {fieldTypes} = this.props;

		return fieldTypes;
	}

	getFormData() {
		const layoutProvider = this.getLayoutProvider();
		const {defaultLanguageId} = layoutProvider.props;

		const {pages, rules} = this.getStore();

		const pagesVisitor = new PagesVisitor(pages);

		const newPages = pagesVisitor.mapFields(
			(field) => {
				const {settingsContext} = field;

				const settingsContextPagesVisitor = new PagesVisitor(
					settingsContext.pages
				);

				const newSettingsContext = {
					...settingsContext,
					pages: settingsContextPagesVisitor.mapFields(
						(settingsField) => {
							if (settingsField.type === 'options') {
								const {value} = settingsField;
								const newValue = {};

								Object.keys(value).forEach((locale) => {
									newValue[locale] = value[locale]?.filter(
										(localizedValue) =>
											localizedValue.value !== '' &&
											localizedValue.label !== ''
									);
								});

								if (!newValue[defaultLanguageId]) {
									newValue[defaultLanguageId] = [];
								}

								settingsField = {
									...settingsField,
									value: newValue,
								};
							}

							return settingsField;
						}
					),
				};

				return {
					...field,
					settingsContext: newSettingsContext,
				};
			},
			true,
			true
		);

		return this.getDataDefinitionAndDataLayout(newPages, rules || []);
	}

	getLayoutProvider() {
		const {layoutProvider} = this.formBuilderWithLayoutProvider.refs;

		return layoutProvider;
	}

	getState() {
		const {appContext} = this.props;
		const [state] = appContext;

		return state;
	}

	getStore() {
		const layoutProvider = this.getLayoutProvider();

		return {
			...layoutProvider.state,
		};
	}

	on(eventName, listener) {
		this.eventEmitter.on(eventName, listener);
	}

	onEditingLanguageIdChange({
		editingLanguageId,
		defaultLanguageId = themeDisplay.getDefaultLanguageId(),
	}) {
		const layoutProvider = this.getLayoutProvider();
		const availableLanguageIds = [
			...new Set([
				...layoutProvider.props.availableLanguageIds,
				editingLanguageId,
			]),
		];

		this.setState({
			availableLanguageIds,
		});

		const props = {
			availableLanguageIds,
			defaultLanguageId,
			editingLanguageId,
		};

		layoutProvider.props = {
			...layoutProvider.props,
			...props,
		};

		this.formBuilderWithLayoutProvider.props.layoutProviderProps = {
			...this.formBuilderWithLayoutProvider.props.layoutProviderProps,
			...props,
		};

		this.formBuilderWithLayoutProvider.props.layoutProviderProps = this.formBuilderWithLayoutProvider.props.layoutProviderProps; // eslint-disable-line

		const focusedField = layoutProvider.getFocusedField();

		if (Object.keys(focusedField).length) {
			layoutProvider
				.getEvents()
				.fieldClicked({activePage: 0, ...focusedField});
		}
	}

	removeEventListener(eventName, listener) {
		this.eventEmitter.removeListener(eventName, listener);
	}

	render() {
		const {sidebarOpen = false} = this.getState();

		return (
			<div
				className={classNames(
					'data-engine-form-builder ddm-form-builder pb-5',
					{
						'ddm-form-builder--sidebar-open': sidebarOpen,
					}
				)}
			>
				<ClayLayout.Sheet ref={this.containerRef} />
			</div>
		);
	}

	save(params = {}) {
		const {
			contentType,
			dataDefinitionId,
			dataLayoutId,
			groupId,
		} = this.props;
		const {
			definition: dataDefinition,
			layout: dataLayout,
		} = this.getFormData();

		return saveDefinitionAndLayout({
			contentType,
			dataDefinition,
			dataDefinitionId,
			dataLayout,
			dataLayoutId,
			groupId,
			params,
		});
	}

	serialize(pages, rules) {
		const {definition, layout} = this.getDataDefinitionAndDataLayout(
			pages,
			rules || []
		);

		return {
			definition: JSON.stringify(definition),
			layout: JSON.stringify(layout),
		};
	}

	_onLocaleChange(event) {
		const layoutProvider = this.getLayoutProvider();
		const selectedLanguageId = event.item.getAttribute('data-value');
		const {defaultLanguageId} = layoutProvider.props;

		this.onEditingLanguageIdChange({
			defaultLanguageId,
			editingLanguageId: selectedLanguageId,
		});
	}

	_setContext(context) {
		const {config, defaultLanguageId} = this.props;

		const emptyLocalizableValue = {
			[defaultLanguageId]: '',
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
						rows: [],
						title: '',
					},
				],
				paginationMode: config.paginationMode || 'wizard',
				rules: context.rules || [],
			};
		}

		return {
			...context,
			pages: context.pages.map((page) => {
				let {
					description = '',
					localizedDescription,
					localizedTitle,
					title = '',
				} = page;
				description = description === null ? '' : description;
				title = title === null ? '' : title;

				if (typeof description !== 'string') {
					description = description[defaultLanguageId];
					localizedDescription = {
						[defaultLanguageId]: description,
					};
				}

				if (typeof title !== 'string') {
					title = title[defaultLanguageId];
					localizedTitle = {
						[defaultLanguageId]: title,
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
			rules: context.rules || [],
		};
	}
}

export default DataLayoutBuilder;
export {DataLayoutBuilder};

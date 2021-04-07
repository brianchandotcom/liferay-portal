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

import {
	PagesVisitor,
	RulesVisitor,
	generateInstanceId,
	generateName,
	getRepeatedIndex,
} from 'data-engine-js-components-web';
import {openModal} from 'frontend-js-web';
import Component from 'metal-jsx';
import {Config} from 'metal-state';

import RulesSupport from '../../components/RuleBuilder/RulesSupport.es';
import {pageStructure, ruleStructure} from '../../util/config.es';
import {
	addField,
	createField,
	getFieldProperties,
	localizeField,
} from '../../util/fieldSupport.es';
import handleColumnResized from './handlers/columnResizedHandler.es';
import handleFieldBlurred from './handlers/fieldBlurredHandler.es';
import handleFieldClicked from './handlers/fieldClickedHandler.es';
import handleFieldDeleted from './handlers/fieldDeletedHandler.es';
import handleFieldDuplicated from './handlers/fieldDuplicatedHandler.es';
import handleFieldEdited from './handlers/fieldEditedHandler.es';
import handleFieldEditedProperties from './handlers/fieldEditedPropertiesHandler.es';
import handleFieldMoved from './handlers/fieldMovedHandler.es';
import handleFieldSetAdded from './handlers/fieldSetAddedHandler.es';
import handleFocusedFieldEvaluationEnded from './handlers/focusedFieldEvaluationEndedHandler.es';
import handleSectionAdded from './handlers/sectionAddedHandler.es';
import {generateFieldName} from './util/fields.es';

/**
 * LayoutProvider listens to your children's events to
 * control the `pages` and make manipulations.
 * @extends Component
 */

class LayoutProvider extends Component {
	dispatch = (event, payload) => {
		try {
			this.emit(event, payload);
		}
		catch (e) {
			console.error(e.message);
		}
	};

	getChildContext() {
		return {
			dispatch: this.dispatch,
			store: this,
		};
	}

	getEvents() {
		return {
			activePageUpdated: this._handleActivePageUpdated.bind(this),
			columnResized: this._handleColumnResized.bind(this),
			fieldAdded: this._handleFieldAdded.bind(this),
			fieldBlurred: this._handleFieldBlurred.bind(this),
			fieldChangesCanceled: this._handleFieldChangesCanceled.bind(this),
			fieldClicked: this._handleFieldClicked.bind(this),
			fieldDeleted: this._handleFieldDeleted.bind(this),
			fieldDuplicated: this._handleFieldDuplicated.bind(this),
			fieldEdited: this._handleFieldEdited.bind(this),
			fieldEditedProperties: this._handleFieldEditedProperties.bind(this),
			fieldHovered: this._handleFieldHovered.bind(this),
			fieldMoved: this._handleFieldMoved.bind(this),
			fieldSetAdded: this._handleFieldSetAdded.bind(this),
			focusedFieldEvaluationEnded: this._handleFocusedFieldEvaluationEnded.bind(
				this
			),
			sectionAdded: this._handleSectionAdded.bind(this),
			sidebarFieldBlurred: this._handleSidebarFieldBlurred.bind(this),
		};
	}

	getFocusedField() {
		const {defaultLanguageId, editingLanguageId} = this.props;
		let {focusedField} = this.state;

		if (focusedField && focusedField.settingsContext) {
			const settingsContext = {
				...focusedField.settingsContext,
				pages: this.getLocalizedPages(
					focusedField.settingsContext.pages
				),
			};

			focusedField = {
				...focusedField,
				...getFieldProperties(
					settingsContext,
					defaultLanguageId,
					editingLanguageId
				),
				settingsContext,
			};
		}

		return focusedField;
	}

	getLocalizedPages(pages) {
		const {editingLanguageId} = this.props;
		const settingsVisitor = new PagesVisitor(pages);

		return settingsVisitor.mapFields((field) =>
			localizeField(field, field.locale, editingLanguageId)
		);
	}

	getPages() {
		const {defaultLanguageId, editingLanguageId} = this.props;
		const {availableLanguageIds = [editingLanguageId]} = this.props;

		const visitor = new PagesVisitor(this.state.pages);

		return visitor.mapFields(
			(field) => {
				const {settingsContext} = field;

				const newSettingsContext = {
					...settingsContext,
					availableLanguageIds,
					defaultLanguageId,
					pages: this.getLocalizedPages(settingsContext.pages),
				};

				const newField = {
					...getFieldProperties(
						newSettingsContext,
						defaultLanguageId,
						editingLanguageId
					),
					name: generateName(field.name, {
						instanceId: field.instanceId || generateInstanceId(),
						repeatedIndex: getRepeatedIndex(field.name),
					}),
					settingsContext: newSettingsContext,
				};

				if (
					field.type === 'select' &&
					field.dataSourceType &&
					field.dataSourceType.includes('data-provider')
				) {
					return {
						...newField,
						options: field.options,
					};
				}

				return newField;
			},
			true,
			true
		);
	}

	getPaginationMode() {
		const {allowMultiplePages} = this.props;
		const {paginationMode} = this.state;

		if (allowMultiplePages) {
			return paginationMode;
		}

		return 'single-page';
	}

	getRules() {
		let {rules} = this.state;

		if (rules) {
			const visitor = new RulesVisitor(rules);

			rules = visitor.mapConditions((condition) => {
				if (condition.operands[0].type == 'list') {
					condition = {
						...condition,
						operands: [
							{
								label: 'user',
								repeatable: false,
								type: 'user',
								value: 'user',
							},
							{
								...condition.operands[0],
								label: condition.operands[0].value,
							},
						],
					};
				}

				return condition;
			});
		}

		return rules;
	}

	render() {
		const {
			allowSuccessPage,
			children,
			defaultLanguageId,
			editingLanguageId,
			fieldActions,
			spritemap,
		} = this.props;
		const {activePage, rules, successPageSettings} = this.state;

		return (
			<span>
				{(children || []).map((child) => ({
					...child,
					props: {
						...child.props,
						...this.otherProps(),
						activePage,
						allowSuccessPage,
						defaultLanguageId,
						editingLanguageId,
						fieldActions,
						focusedField: this.getFocusedField(),
						pages: this.getPages(),
						paginationMode: this.getPaginationMode(),
						rules,
						spritemap,
						successPageSettings,
					},
				}))}
			</span>
		);
	}

	_handleDeleteFieldModalButtonClicked(event) {
		this.setState(handleFieldDeleted(this.props, this.state, event));
	}

	_fieldActionsValueFn() {
		return [
			{
				action: ({activePage, fieldName}) =>
					this.dispatch('fieldDuplicated', {activePage, fieldName}),
				label: Liferay.Language.get('duplicate'),
			},
			{
				action: ({activePage, fieldName}) => {
					this.dispatch('fieldDeleted', {activePage, fieldName});
				},
				label: Liferay.Language.get('delete'),
			},
		];
	}

	_fieldNameGeneratorValueFn() {
		return (desiredName, currentName, blacklist = []) => {
			const {pages} = this.state;
			const {generateFieldNameUsingFieldLabel} = this.props;

			return generateFieldName(
				pages,
				desiredName,
				currentName,
				blacklist,
				generateFieldNameUsingFieldLabel
			);
		};
	}

	_handleActivePageUpdated(activePage) {
		this.setState({
			activePage,
		});
	}

	_handleColumnResized({column, direction, loc}) {
		const {props, state} = this;

		this.setState(
			handleColumnResized({column, direction, loc, props, state})
		);
	}

	_handleFieldAdded(event) {
		const {
			availableLanguageIds = [editingLanguageId],
			defaultLanguageId,
			editingLanguageId,
		} = this.props;
		const {
			data: {parentFieldName},
			indexes,
			newField,
		} = event;

		const newState = addField({
			...this.props,
			indexes,
			newField: newField ?? createField(this.props, event),
			pages: this.state.pages,
			parentFieldName,
		});

		const {focusedField} = newState;

		let {pages} = newState;

		const visitor = new PagesVisitor(pages);

		pages = visitor.mapFields(
			(field) => {
				const {settingsContext} = field;

				const newSettingsContext = {
					...settingsContext,
					availableLanguageIds,
					defaultLanguageId,
					pages: this.getLocalizedPages(settingsContext.pages),
				};

				const newField = {
					...field,
					...getFieldProperties(
						newSettingsContext,
						defaultLanguageId,
						editingLanguageId
					),
					settingsContext: newSettingsContext,
				};

				if (field.name === focusedField.name) {
					focusedField.settingsContext = newSettingsContext;
				}

				return newField;
			},
			true,
			true
		);

		this.setState({
			...newState,
			focusedField,
			pages,
		});
	}

	_handleFieldHovered(fieldHovered) {
		this.setState({fieldHovered});
	}

	_handleFieldBlurred(event) {
		this.setState(handleFieldBlurred(this.props, this.state, event));
	}

	_handleFieldChangesCanceled() {
		const {
			activePage,
			focusedField,
			pages,
			previousFocusedField,
		} = this.state;
		const {settingsContext} = previousFocusedField;

		const visitor = new PagesVisitor(settingsContext.pages);

		visitor.mapFields(({fieldName, value}) => {
			this._handleFieldEdited({
				propertyName: fieldName,
				propertyValue: value,
			});
		});

		visitor.setPages(pages);

		this.setState({
			activePage,
			focusedField: previousFocusedField,
			pages: visitor.mapFields((field) => {
				if (field.fieldName === focusedField.fieldName) {
					return {
						...field,
						settingsContext,
					};
				}

				return field;
			}),
		});
	}

	_handleFieldClicked(event) {
		this.setState(handleFieldClicked(this.props, this.state, event));
	}

	_handleFieldDeleted(event) {
		const {rules} = this.state;

		if (
			rules &&
			RulesSupport.findRuleByFieldName(event.fieldName, null, rules)
		) {
			openModal({
				bodyHTML: Liferay.Language.get(
					'a-rule-is-applied-to-this-field'
				),
				buttons: [
					{
						displayType: 'secondary',
						label: Liferay.Language.get('cancel'),
						type: 'cancel',
					},
					{
						displayType: 'danger',
						label: Liferay.Language.get('confirm'),
						onClick: () => {
							this._handleDeleteFieldModalButtonClicked(event);
						},
						type: 'cancel',
					},
				],
				id: 'ddm-delete-field-with-rule-modal',
				size: 'md',
				title: Liferay.Language.get('delete-field-with-rule-applied'),
			});
		}
		else {
			this.setState(handleFieldDeleted(this.props, this.state, event));
		}
	}

	_handleFieldDuplicated(event) {
		this.setState(handleFieldDuplicated(this.props, this.state, event));
	}

	_handleFieldEdited(properties) {
		this.setState(handleFieldEdited(this.props, this.state, properties));
	}

	_handleFieldEditedProperties(properties) {
		this.setState(
			handleFieldEditedProperties(this.props, this.state, properties)
		);
	}

	_handleFieldMoved(event) {
		this.setState(handleFieldMoved(this.props, this.state, event));
	}

	_handleFieldSetAdded(event) {
		this.setState(handleFieldSetAdded(this.props, this.state, event));
	}

	_handleFocusedFieldEvaluationEnded({
		changedEditingLanguage,
		changedFieldType,
		instanceId,
		settingsContext,
	}) {
		this.setState(
			handleFocusedFieldEvaluationEnded(
				this.props,
				this.state,
				changedEditingLanguage,
				changedFieldType,
				instanceId,
				settingsContext
			)
		);
	}

	_handleSectionAdded(event) {
		this.setState(handleSectionAdded(this.props, this.state, event));
	}

	_handleSidebarFieldBlurred() {
		this.setState({
			focusedField: {},
		});
	}

	_pagesValueFn() {
		const {initialPages} = this.props;

		return initialPages;
	}

	_rulesValueFn() {
		const {rules} = this.props;

		return rules;
	}

	_setEvents(value) {
		return {
			...this.getEvents(),
			...value,
		};
	}

	_setInitialPages(initialPages) {
		const visitor = new PagesVisitor(initialPages);

		return visitor.mapFields(
			(field) => {
				const {settingsContext} = field;

				return {
					...field,
					localizedValue: {},
					settingsContext: {
						...this._setInitialSettingsContext(settingsContext),
					},
					value: undefined,
					visible: true,
				};
			},
			true,
			true
		);
	}

	_setInitialSettingsContext(settingsContext) {
		const visitor = new PagesVisitor(settingsContext.pages);

		return {
			...settingsContext,
			pages: visitor.mapFields((field) => {
				if (field.type === 'options') {
					const getOptions = (languageId, field) => {
						return field.value[languageId].map((option) => {
							return {
								...option,
								edited: true,
							};
						});
					};

					Object.keys(field.value).forEach((languageId) => {
						field = {
							...field,
							value: {
								...field.value,
								[languageId]: getOptions(languageId, field),
							},
						};
					});
				}

				return field;
			}),
		};
	}

	_setPages(pages) {
		return pages.filter(({contentRenderer}) => {
			return contentRenderer !== 'success';
		});
	}
}

LayoutProvider.PROPS = {

	/**
	 * @instance
	 * @memberof LayoutProvider
	 * @type {boolean}
	 */

	allowMultiplePages: Config.bool().value(true),

	/**
	 * @instance
	 * @memberof LayoutProvider
	 * @type {boolean}
	 */

	allowSuccessPage: Config.bool().value(true),

	/**
	 * @default undefined
	 * @instance
	 * @memberof LayoutProvider
	 * @type {?string}
	 */

	defaultLanguageId: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof LayoutProvider
	 * @type {?string}
	 */

	editingLanguageId: Config.string(),

	/**
	 * @default {}
	 * @instance
	 * @memberof LayoutProvider
	 * @type {?object}
	 */

	events: Config.setter('_setEvents').value({}),

	/**
	 * @default undefined
	 * @instance
	 * @memberof LayoutProvider
	 * @type {?string}
	 */

	fieldActions: Config.array().valueFn('_fieldActionsValueFn'),

	/**
	 * @default _fieldNameGeneratorValueFn
	 * @instance
	 * @memberof LayoutProvider
	 * @type {?function}
	 */

	fieldNameGenerator: Config.func().valueFn('_fieldNameGeneratorValueFn'),

	/**
	 * @default undefined
	 * @instance
	 * @memberof LayoutProvider
	 * @type {?string}
	 */

	fieldSetDefinitionURL: Config.string(),

	/**
	 * @default []
	 * @instance
	 * @memberof LayoutProvider
	 * @type {?(array|undefined)}
	 */

	fieldSets: Config.array().value([]),

	/**
	 * @default false
	 * @instance
	 * @memberof LayoutProvider
	 * @type {?bool}
	 */

	generateFieldNameUsingFieldLabel: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof LayoutProvider
	 * @type {?(array|undefined)}
	 */

	initialPages: Config.arrayOf(pageStructure)
		.setter('_setInitialPages')
		.value([]),

	/**
	 * @default undefined
	 * @instance
	 * @memberof LayoutProvider
	 * @type {?(array|undefined)}
	 */

	rules: Config.arrayOf(ruleStructure),

	/**
	 * @default undefined
	 * @instance
	 * @memberof LayoutProvider
	 * @type {?(array|undefined)}
	 */

	spritemap: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof LayoutProvider
	 * @type {?string}
	 */

	view: Config.string(),
};

LayoutProvider.STATE = {

	/**
	 * @instance
	 * @memberof FormPage
	 * @type {?number}
	 */

	activePage: Config.number().value(0),

	/**
	 * @default {}
	 * @instance
	 * @memberof LayoutProvider
	 * @type {?object}
	 */
	fieldHovered: Config.object().value({}),

	/**
	 * @default {}
	 * @instance
	 * @memberof LayoutProvider
	 * @type {?object}
	 */
	focusedField: Config.shapeOf({
		columnIndex: Config.oneOfType([
			Config.bool().value(false),
			Config.number(),
		]).required(),
		pageIndex: Config.number().required(),
		rowIndex: Config.number().required(),
		type: Config.string().required(),
	}).value({}),

	/**
	 * @default undefined
	 * @instance
	 * @memberof LayoutProvider
	 * @type {?array}
	 */

	pages: Config.arrayOf(pageStructure)
		.setter('_setPages')
		.valueFn('_pagesValueFn'),

	/**
	 * @instance
	 * @memberof LayoutProvider
	 * @type {string}
	 */

	paginationMode: Config.string().value('wizard'),

	/**
	 * @default {}
	 * @instance
	 * @memberof LayoutProvider
	 * @type {?object}
	 */

	previousFocusedField: Config.shapeOf({
		columnIndex: Config.oneOfType([
			Config.bool().value(false),
			Config.number(),
		]).required(),
		pageIndex: Config.number().required(),
		rowIndex: Config.number().required(),
		type: Config.string().required(),
	}).value({}),

	/**
	 * @default undefined
	 * @instance
	 * @memberof LayoutProvider
	 * @type {?(array|undefined)}
	 */

	rules: Config.arrayOf(ruleStructure).valueFn('_rulesValueFn'),
};

export default LayoutProvider;

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
	FormSupport,
	PagesVisitor,
	RulesVisitor,
	generateName,
	getRepeatedIndex,
} from 'dynamic-data-mapping-form-renderer';
import {openToast} from 'frontend-js-web';
import Component from 'metal-jsx';
import {Config} from 'metal-state';

import {pageStructure, ruleStructure} from '../../util/config.es';
import {
	generateInstanceId,
	getFieldProperties,
	localizeField,
} from '../../util/fieldSupport.es';
import {setLocalizedValue} from '../../util/i18n.es';
import handleColumnResized from './handlers/columnResizedHandler.es';
import handleElementSetAdded from './handlers/elementSetAddedHandler.es';
import handleFieldAdded from './handlers/fieldAddedHandler.es';
import handleFieldBlurred from './handlers/fieldBlurredHandler.es';
import handleFieldClicked from './handlers/fieldClickedHandler.es';
import handleFieldDeleted from './handlers/fieldDeletedHandler.es';
import handleFieldDuplicated from './handlers/fieldDuplicatedHandler.es';
import handleFieldEdited from './handlers/fieldEditedHandler.es';
import handleFieldEditedProperties from './handlers/fieldEditedPropertiesHandler.es';
import handleFieldMoved from './handlers/fieldMovedHandler.es';
import handleFieldSetAdded from './handlers/fieldSetAddedHandler.es';
import handleFocusedFieldEvaluationEnded from './handlers/focusedFieldEvaluationEndedHandler.es';
import handleLanguageIdDeleted from './handlers/languageIdDeletedHandler.es';
import handleSectionAdded from './handlers/sectionAddedHandler.es';
import {generateFieldName} from './util/fields.es';

/**
 * LayoutProvider listens to your children's events to
 * control the `pages` and make manipulations.
 * @extends Component
 */

class LayoutProvider extends Component {
	createNewPage() {
		const languageId = this.props.editingLanguageId;
		const page = {
			description: '',
			enabled: true,
			rows: [FormSupport.implAddRow(12, [])],
			showRequiredFieldsWarning: true,
			title: '',
		};

		setLocalizedValue(page, languageId, 'title', '');
		setLocalizedValue(page, languageId, 'description', '');

		return page;
	}

	dispatch(event, payload) {
		try {
			this.emit(event, payload);
		}
		catch (e) {
			openToast({
				message: e.message,
				type: 'danger',
			});
		}
	}

	getChildContext() {
		return {
			dispatch: this.dispatch.bind(this),
			store: this,
		};
	}

	getEvents() {
		return {
			activePageUpdated: this._handleActivePageUpdated.bind(this),
			columnResized: this._handleColumnResized.bind(this),
			elementSetAdded: this._handleElementSetAdded.bind(this),
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
			languageIdDeleted: this._handleLanguageIdDeleted.bind(this),
			pageAdded: this._handlePageAdded.bind(this),
			pageDeleted: this._handlePageDeleted.bind(this),
			pageReset: this._handlePageReset.bind(this),
			pagesSwapped: this._handlePagesSwapped.bind(this),
			pagesUpdated: this._handlePagesUpdated.bind(this),
			paginationModeUpdated: this._handlePaginationModeUpdated.bind(this),
			paginationNextClicked: this._handlePaginationNextClicked.bind(this),
			paginationPreviousClicked: this._handlePaginationPreviousClicked.bind(
				this
			),
			ruleAdded: this._handleRuleAdded.bind(this),
			ruleDeleted: this._handleRuleDeleted.bind(this),
			ruleEdited: this._handleRuleSaved.bind(this),
			ruleSaved: this._handleRuleSaved.bind(this),
			ruleValidatorChanged: this._handleRuleValidatorChanged.bind(this),
			sectionAdded: this._handleSectionAdded.bind(this),
			sidebarFieldBlurred: this._handleSidebarFieldBlurred.bind(this),
			successPageChanged: this._handleSuccessPageChanged.bind(this),
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
		const {defaultLanguageId, editingLanguageId} = this.props;
		const settingsVisitor = new PagesVisitor(pages);

		return settingsVisitor.mapFields((field) =>
			localizeField(field, defaultLanguageId, editingLanguageId)
		);
	}

	getPages() {
		const {defaultLanguageId, editingLanguageId} = this.props;
		const {availableLanguageIds = [editingLanguageId]} = this.props;
		const {focusedField} = this.state;
		let {pages} = this.state;

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

				return {
					...getFieldProperties(
						newSettingsContext,
						defaultLanguageId,
						editingLanguageId
					),
					name: generateName(field.name, {
						instanceId: field.instanceId || generateInstanceId(),
						repeatedIndex: getRepeatedIndex(field.name),
					}),
					selected: focusedField.fieldName === field.fieldName,
					settingsContext: newSettingsContext,
				};
			},
			true,
			true
		);

		visitor.setPages(pages);

		return visitor.mapPages((page) => {
			let {description, title} = page;

			if (page.localizedDescription[editingLanguageId]) {
				description = page.localizedDescription[editingLanguageId];
			}
			else if (
				page.localizedDescription[defaultLanguageId] &&
				page.localizedDescription[editingLanguageId] === undefined
			) {
				description = page.localizedDescription[defaultLanguageId];
			}

			if (page.localizedTitle[editingLanguageId]) {
				title = page.localizedTitle[editingLanguageId];
			}
			else if (
				page.localizedTitle[defaultLanguageId] &&
				page.localizedTitle[editingLanguageId] === undefined
			) {
				title = page.localizedTitle[defaultLanguageId];
			}

			return {
				...page,
				description,
				title,
			};
		});
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

	_fieldActionsValueFn() {
		return [
			{
				action: ({activePage, fieldName}) =>
					this.dispatch('fieldDuplicated', {activePage, fieldName}),
				label: Liferay.Language.get('duplicate'),
			},
			{
				action: ({activePage, fieldName}) =>
					this.dispatch('fieldDeleted', {activePage, fieldName}),
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

	_handleColumnResized({column, container, direction, source}) {
		const {props, state} = this;

		this.setState(
			handleColumnResized(
				props,
				state,
				source,
				container,
				column,
				direction
			)
		);
	}

	_handleElementSetAdded(event) {
		this.setState(handleElementSetAdded(this.props, this.state, event));
	}

	_handleFieldAdded(event) {
		this.setState(handleFieldAdded(this.props, this.state, event));
	}

	_handleFieldHovered(fieldHovered) {
		this.setState({fieldHovered});
	}

	_handleFieldBlurred(event) {
		this.setState(handleFieldBlurred(this.state, event));
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
		this.setState(handleFieldDeleted(this.props, this.state, event));
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

	_handleLanguageIdDeleted({locale}) {
		const {focusedField, pages} = this.state;

		this.setState(handleLanguageIdDeleted(focusedField, pages, locale));
	}

	_handlePageAdded({pageIndex}) {
		const {pages} = this.state;

		pages.splice(pageIndex + 1, 0, this.createNewPage());

		this.setState({
			activePage: pageIndex + 1,
			pages,
		});
	}

	_handlePageDeleted(pageIndex) {
		const {pages} = this.state;

		this.setState({
			activePage: Math.max(0, pageIndex - 1),
			pages: pages.filter((page, index) => index != pageIndex),
		});
	}

	_handlePageReset({pageIndex}) {
		const {pages} = this.state;

		pages.splice(pageIndex, 1, this.createNewPage());

		this.setState({
			pages,
		});
	}

	_handlePagesSwapped({firstIndex, secondIndex}) {
		const {pages} = this.state;

		const [firstPage, secondPage] = [pages[firstIndex], pages[secondIndex]];

		this.setState({
			pages: pages.map((page, index) => {
				if (index === firstIndex) {
					return secondPage;
				}
				else if (index === secondIndex) {
					return firstPage;
				}

				return page;
			}),
		});
	}

	_handlePagesUpdated(pages) {
		this.setState({
			pages: [...pages],
		});
	}

	_handlePaginationModeUpdated() {
		const {paginationMode} = this.state;
		let newMode = 'paginated';

		if (paginationMode === newMode) {
			newMode = 'wizard';
		}

		this.setState({
			paginationMode: newMode,
		});
	}

	_handlePaginationNextClicked() {
		const {activePage, pages} = this.state;
		const pageIndex = Math.min(activePage + 1, pages.length - 1);
		this.dispatch('activePageUpdated', pageIndex);
	}

	_handlePaginationPreviousClicked() {
		const {activePage} = this.state;
		const pageIndex = Math.max(activePage - 1, 0);
		this.dispatch('activePageUpdated', pageIndex);
	}

	_handleRuleAdded(rule) {
		this.setState({
			rules: [...this.state.rules, rule],
		});

		this.emit('rulesModified');
	}

	_handleRuleDeleted({ruleId}) {
		const {rules} = this.state;

		this.setState({
			rules: rules.filter((rule, index) => index !== ruleId),
		});

		this.emit('rulesModified');
	}

	_handleRuleValidatorChanged(invalidRule) {
		this.emit('ruleValidatorChanged', invalidRule);
	}

	_handleRuleSaved(event) {
		const {actions, conditions, ruleEditedIndex} = event;
		const logicalOperator = event['logical-operator'];
		const {rules} = this.state;

		const newRule = {
			actions,
			conditions,
			'logical-operator': logicalOperator,
		};

		rules.splice(ruleEditedIndex, 1, newRule);

		this.setState({
			rules,
		});

		this.emit('rulesModified');
	}

	_handleSectionAdded(event) {
		this.setState(handleSectionAdded(this.props, this.state, event));
	}

	_handleSidebarFieldBlurred() {
		this.setState({
			focusedField: {},
		});
	}

	_handleSuccessPageChanged(successPageSettings) {
		this.setState({
			successPageSettings,
		});
	}

	_pagesValueFn() {
		const {initialPages} = this.props;

		return initialPages;
	}

	_paginationModeValueFn() {
		return this.props.initialPaginationMode;
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
					readOnly: true,
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

	_successPageSettingsValueFn() {
		const {defaultLanguageId, initialSuccessPageSettings} = this.props;

		if (
			!initialSuccessPageSettings ||
			Object.keys(initialSuccessPageSettings.body).length > 1
		) {
			return initialSuccessPageSettings;
		}

		const {body, title, ...otherProps} = initialSuccessPageSettings;

		return {
			...otherProps,
			body: {
				...body,
				[defaultLanguageId]:
					body[defaultLanguageId] === ''
						? Liferay.Language.get(
								'your-information-was-successfully-received-thank-you-for-filling-out-the-form'
						  )
						: body[defaultLanguageId],
			},
			title: {
				...title,
				[defaultLanguageId]:
					title[defaultLanguageId] === ''
						? Liferay.Language.get('thank-you')
						: title[defaultLanguageId],
			},
		};
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
	 * @default 'wizard'
	 * @instance
	 * @memberof LayoutProvider
	 * @type {?string}
	 */

	initialPaginationMode: Config.string().value('wizard'),

	/**
	 * @instance
	 * @memberof LayoutProvider
	 * @type {object}
	 */

	initialSuccessPageSettings: Config.shapeOf({
		body: Config.object(),
		enabled: Config.bool(),
		title: Config.object(),
	}),

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

	paginationMode: Config.string().valueFn('_paginationModeValueFn'),

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

	/**
	 * @default undefined
	 * @instance
	 * @memberof LayoutProvider
	 * @type {?(object|undefined)}
	 */

	successPageSettings: Config.object().valueFn('_successPageSettingsValueFn'),
};

export default LayoutProvider;

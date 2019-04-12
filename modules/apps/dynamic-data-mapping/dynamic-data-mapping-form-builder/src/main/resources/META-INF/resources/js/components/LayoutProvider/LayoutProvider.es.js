import * as FormSupport from '../Form/FormSupport.es';
import Component from 'metal-jsx';
import {Config} from 'metal-state';
import {getFieldProperties} from '../../util/fieldSupport.es';
import {pageStructure, ruleStructure} from '../../util/config.es';
import {PagesVisitor, RulesVisitor} from '../../util/visitors.es';
import {setLocalizedValue} from '../../util/i18n.es';

import handleColumnResized from './handlers/columnResizedHandler.es';
import handleFieldAdded from './handlers/fieldAddedHandler.es';
import handleFieldBlurred from './handlers/fieldBlurredHandler.es';
import handleFieldClicked from './handlers/fieldClickedHandler.es';
import handleFieldDeleted from './handlers/fieldDeletedHandler.es';
import handleFieldDuplicated from './handlers/fieldDuplicatedHandler.es';
import handleFieldEdited from './handlers/fieldEditedHandler.es';

/**
 * LayoutProvider listens to your children's events to
 * control the `pages` and make manipulations.
 * @extends Component
 */

class LayoutProvider extends Component {
	static PROPS = {

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

		events: Config.setter('_setEvents'),

		/**
		 * @default undefined
		 * @instance
		 * @memberof LayoutProvider
		 * @type {?(array|undefined)}
		 */

		initialPages: Config.arrayOf(pageStructure).setter('_setInitialPages').value([]),

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

		initialSuccessPageSettings: Config.shapeOf(
			{
				body: Config.object(),
				enabled: Config.bool(),
				title: Config.object()
			}
		),

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

		spritemap: Config.string()

	};

	static STATE = {

		/**
		 * @instance
		 * @memberof FormPage
		 * @type {?number}
		 */

		activePage: Config.number().value(0),

		/**
		 * @default undefined
		 * @instance
		 * @memberof LayoutProvider
		 * @type {?object}
		 */

		focusedField: Config.shapeOf(
			{
				columnIndex: Config.oneOfType(
					[
						Config.bool().value(false),
						Config.number()
					]
				).required(),
				pageIndex: Config.number().required(),
				rowIndex: Config.number().required(),
				type: Config.string().required()
			}
		).value({}),

		/**
		 * @default undefined
		 * @instance
		 * @memberof LayoutProvider
		 * @type {?array}
		 */

		pages: Config.arrayOf(pageStructure).valueFn('_pagesValueFn'),

		/**
		 * @instance
		 * @memberof LayoutProvider
		 * @type {string}
		 */

		paginationMode: Config.string().valueFn('_paginationModeValueFn'),

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

		successPageSettings: Config.object().valueFn('_successPageSettingsValueFn')
	};

	createNewPage() {
		const languageId = this.props.editingLanguageId;
		const page = {
			description: '',
			enabled: true,
			rows: [FormSupport.implAddRow(12, [])],
			showRequiredFieldsWarning: true,
			title: ''
		};

		setLocalizedValue(page, languageId, 'title', '');
		setLocalizedValue(page, languageId, 'description', '');

		return page;
	}

	getChildContext() {
		return {
			dispatch: this.emit.bind(this),
			store: this
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
			fieldMoved: this._handleFieldMoved.bind(this),
			focusedFieldUpdated: this._handleFocusedFieldUpdated.bind(this),
			pageAdded: this._handlePageAdded.bind(this),
			pageDeleted: this._handlePageDeleted.bind(this),
			pageReset: this._handlePageReset.bind(this),
			pagesUpdated: this._handlePagesUpdated.bind(this),
			paginationModeUpdated: this._handlePaginationModeUpdated.bind(this),
			ruleAdded: this._handleRuleAdded.bind(this),
			ruleDeleted: this._handleRuleDeleted.bind(this),
			ruleSaved: this._handleRuleSaved.bind(this),
			sidebarFieldBlurred: this._handleSidebarFieldBlurred.bind(this),
			successPageChanged: this._handleSuccessPageChanged.bind(this)
		};
	}

	getFocusedField() {
		let {focusedField} = this.state;

		if (focusedField && focusedField.settingsContext) {
			focusedField = {
				...focusedField,
				settingsContext: {
					...focusedField.settingsContext,
					pages: this.getLocalizedPages(focusedField.settingsContext.pages)
				}
			};
		}

		return focusedField;
	}

	getLocalizedPages(pages) {
		const {
			defaultLanguageId,
			editingLanguageId
		} = this.props;
		const settingsVisitor = new PagesVisitor(pages);

		return settingsVisitor.mapFields(
			field => {
				let value = field.value;

				if (field.localizable) {
					let localizedValue = field.localizedValue[editingLanguageId];

					if (localizedValue === undefined) {
						localizedValue = field.localizedValue[defaultLanguageId];
					}

					value = localizedValue;
				}

				if (value && value.JSONArray) {
					value = value.JSONArray;
				}

				return {
					...field,
					defaultLanguageId,
					editingLanguageId,
					value
				};
			}
		);
	}

	getPages() {
		const {defaultLanguageId, editingLanguageId} = this.props;
		let {pages} = this.state;
		const visitor = new PagesVisitor(pages);

		pages = visitor.mapFields(
			field => {
				const {settingsContext} = field;

				return {
					...getFieldProperties(settingsContext, defaultLanguageId, editingLanguageId),
					settingsContext: {
						...settingsContext,
						pages: this.getLocalizedPages(settingsContext.pages)
					}
				};
			}
		);

		visitor.setPages(pages);

		return visitor.mapPages(
			page => {
				let {description, title} = page;

				if (page.localizedDescription[editingLanguageId]) {
					description = page.localizedDescription[editingLanguageId];
				}
				else if (page.localizedDescription[defaultLanguageId]) {
					description = page.localizedDescription[defaultLanguageId];
				}

				if (page.localizedTitle[editingLanguageId]) {
					title = page.localizedTitle[editingLanguageId];
				}
				else if (page.localizedTitle[defaultLanguageId]) {
					title = page.localizedTitle[defaultLanguageId];
				}

				return {
					...page,
					description,
					title
				};
			}
		);
	}

	getRules() {
		let {rules} = this.state;

		if (rules) {
			const visitor = new RulesVisitor(rules);

			rules = visitor.mapConditions(
				condition => {
					if (condition.operands[0].type == 'list') {
						condition = {
							...condition,
							operands: [
								{
									label: 'user',
									repeatable: false,
									type: 'user',
									value: 'user'
								},
								{
									...condition.operands[0],
									label: condition.operands[0].value
								}
							]
						};
					}

					return condition;
				}
			);
		}

		return rules;
	}

	render() {
		const {
			children,
			defaultLanguageId,
			editingLanguageId,
			spritemap
		} = this.props;
		const {
			activePage,
			paginationMode,
			rules,
			successPageSettings
		} = this.state;

		if (children.length) {
			for (let index = 0; index < children.length; index++) {
				const child = children[index];

				Object.assign(
					child.props,
					{
						...this.otherProps(),
						activePage,
						defaultLanguageId,
						editingLanguageId,
						events: this.getEvents(),
						focusedField: this.getFocusedField(),
						pages: this.getPages(),
						paginationMode,
						rules,
						spritemap,
						successPageSettings
					}
				);
			}
		}

		return (
			<span>{children}</span>
		);
	}

	_handleActivePageUpdated(activePage) {
		this.setState(
			{
				activePage
			}
		);
	}

	_handleColumnResized({column, direction, source}) {
		const {state} = this;

		this.setState(handleColumnResized(state, source, column, direction));
	}

	_handleFieldAdded(event) {
		this.setState(handleFieldAdded(this.props, this.state, event));
	}

	_handleFieldBlurred(event) {
		this.setState(handleFieldBlurred(this.state, event));
	}

	_handleFieldChangesCanceled() {
		const {focusedField: {originalContext}} = this.state;

		Object.keys(originalContext).forEach(
			propertyName => {
				this._handleFieldEdited(
					{
						propertyName,
						propertyValue: originalContext[propertyName]
					}
				);
			}
		);
	}

	_handleFieldClicked(event) {
		this.setState(handleFieldClicked(this.state, event));
	}

	_handleFieldDeleted(event) {
		this.setState(handleFieldDeleted(this.state, event));
	}

	_handleFieldDuplicated(event) {
		this.setState(handleFieldDuplicated(this.state, event));
	}

	_handleFieldEdited(properties) {
		const {editingLanguageId} = this.props;

		this.setState(handleFieldEdited(this.state, editingLanguageId, properties));
	}

	_handleFieldMoved({addedToPlaceholder, target, source}) {
		let {pages} = this.state;
		const {columnIndex, pageIndex, rowIndex} = source;

		const column = FormSupport.getColumn(
			pages,
			pageIndex,
			rowIndex,
			columnIndex
		);
		const {fields} = column;
		const newRow = FormSupport.implAddRow(12, fields);

		pages = FormSupport.removeFields(
			pages,
			pageIndex,
			rowIndex,
			columnIndex
		);

		if (target.rowIndex > pages[pageIndex].rows.length - 1) {
			pages = FormSupport.addRow(pages, target.rowIndex, target.pageIndex, newRow);
		}
		else if (addedToPlaceholder) {
			pages = FormSupport.addRow(pages, target.rowIndex, target.pageIndex, newRow);
		}
		else {
			pages = FormSupport.addFieldToColumn(pages, target.pageIndex, target.rowIndex, target.columnIndex, fields[0]);
		}

		pages[pageIndex].rows = FormSupport.removeEmptyRows(pages, pageIndex);

		this.setState(
			{
				pages
			}
		);
	}

	_handleFocusedFieldUpdated(focusedField) {
		const {columnIndex, pageIndex, rowIndex} = focusedField;
		const {pages} = this.state;

		this.setState(
			{
				focusedField,
				pages: this._setColumnFields(
					pages,
					{
						columnIndex,
						pageIndex,
						rowIndex
					},
					[focusedField]
				)
			}
		);
	}

	_handlePageAdded() {
		const {pages} = this.state;

		this.setState(
			{
				activePage: pages.length,
				pages: [
					...pages,
					this.createNewPage()
				]
			}
		);
	}

	_handlePageDeleted(pageIndex) {
		const {pages} = this.state;

		this.setState(
			{
				activePage: Math.max(0, pageIndex - 1),
				pages: pages.filter(
					(page, index) => index != pageIndex
				)
			}
		);
	}

	_handlePageReset() {
		this.setState(
			{
				pages: [this.createNewPage()]
			}
		);
	}

	_handlePagesUpdated(pages) {
		this.setState(
			{
				pages: [...pages]
			}
		);
	}

	_handlePaginationModeUpdated() {
		const {paginationMode} = this.state;
		let newMode = 'paginated';

		if (paginationMode === newMode) {
			newMode = 'wizard';
		}

		this.setState(
			{
				paginationMode: newMode
			}
		);
	}

	_handleRuleAdded(rule) {
		this.setState(
			{
				rules: [
					...this.state.rules,
					rule
				]
			}
		);
	}

	_handleRuleDeleted({ruleId}) {
		const {rules} = this.state;

		this.setState(
			{
				rules: rules.filter((rule, index) => index !== ruleId)
			}
		);
	}

	_handleRuleSaved(event) {
		const {actions, conditions, ruleEditedIndex} = event;
		const logicalOperator = event['logical-operator'];
		const {rules} = this.state;

		rules.splice(
			ruleEditedIndex,
			1,
			{
				actions,
				conditions,
				'logical-operator': logicalOperator
			}
		);

		this.setState(
			{
				rules
			}
		);
	}

	_handleSidebarFieldBlurred() {
		this.setState(
			{
				focusedField: {}
			}
		);
	}

	_handleSuccessPageChanged(successPageSettings) {
		this.setState(
			{
				successPageSettings
			}
		);
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

	_setColumnFields(pages, target, fields) {
		const {columnIndex, pageIndex, rowIndex} = target;

		return FormSupport.setColumnFields(
			pages,
			pageIndex,
			rowIndex,
			columnIndex,
			fields
		);
	}

	_setEvents(value) {
		return {
			...this.getEvents(),
			...value
		};
	}

	_setInitialPages(initialPages) {
		const visitor = new PagesVisitor(initialPages);

		return visitor.mapFields(
			field => {
				const {settingsContext} = field;

				return {
					...field,
					localizedValue: {},
					readOnly: true,
					settingsContext: {
						...this._setInitialSettingsContext(settingsContext)
					},
					value: undefined,
					visible: true
				};
			}
		);
	}

	_setInitialSettingsContext(settingsContext) {
		const visitor = new PagesVisitor(settingsContext.pages);

		return {
			...settingsContext,
			pages: visitor.mapFields(
				field => {
					if (field.type === 'options') {
						const getOptions = (languageId, field) => {
							return field.value[languageId].map(
								option => {
									return {
										...option,
										edited: true
									};
								}
							);
						};

						for (const languageId in field.value) {
							field = {
								...field,
								value: {
									...field.value,
									[languageId]: getOptions(languageId, field)
								}
							};
						}
					}

					return field;
				}
			)
		};
	}

	_successPageSettingsValueFn() {
		return this.props.initialSuccessPageSettings;
	}
}

export default LayoutProvider;
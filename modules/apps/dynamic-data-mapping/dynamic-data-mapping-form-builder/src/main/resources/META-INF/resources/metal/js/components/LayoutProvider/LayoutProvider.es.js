import {Config} from 'metal-state';
import {FormSupport} from '../Form/index.es';
import {pageStructure, rule} from '../../util/config.es';
import {PagesVisitor} from '../../util/visitors.es';
import {setLocalizedValue} from '../../util/i18n.es';
import {sub} from '../../util/strings.es';
import Component from 'metal-jsx';
import autobind from 'autobind-decorator';
import {generateInstanceId} from '../../util/fieldSupport.es';
import RulesSupport from '../RuleBuilder/RulesSupport.es';

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

		rules: Config.arrayOf(rule),

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
		 * @type {?(array|undefined)}
		 */

		rules: Config.arrayOf(rule).valueFn('_rulesValueFn'),

		successPageSettings: Config.object().valueFn('_successPageSettingsValueFn')
	};

	_handleActivePageUpdated(activePage) {
		this.setState(
			{
				activePage
			}
		);
	}

	/**
	 * @param {!Object} data
	 * @private
	 */

	_handleFieldClicked(focusedField) {
		this.setState(
			{
				focusedField
			}
		);
	}

	_handleFieldChangesCanceled() {
		const {focusedField: {originalContext}} = this.state;

		this._handleFieldEdited(originalContext);
	}

	/**
	 * @param {!Object} payload
	 * @private
	 */

	_handleFieldAdded({focusedField: {name, settingsContext, fieldName}, target}) {
		const {pageIndex, rowIndex} = target;
		const {editingLanguageId, spritemap} = this.props;
		let {pages} = this.state;
		let {columnIndex} = target;

		const fieldProperties = {
			...FormSupport.getFieldProperties(settingsContext, editingLanguageId),
			fieldName,
			instanceId: generateInstanceId(8),
			name,
			settingsContext,
			spritemap,
			type: name
		};

		if (FormSupport.rowHasFields(pages, pageIndex, rowIndex)) {
			pages = FormSupport.addRow(pages, rowIndex, pageIndex);
			columnIndex = 0;
		}

		this.setState(
			{
				focusedField: {
					...fieldProperties,
					columnIndex,
					originalContext: fieldProperties,
					pageIndex,
					rowIndex
				},
				pages: FormSupport.addFieldToColumn(
					pages,
					pageIndex,
					rowIndex,
					columnIndex,
					fieldProperties
				)
			}
		);
	}

	_handleFieldBlurred() {
		this.setState(
			{
				focusedField: {}
			}
		);
	}

	formatRules(pages) {
		const visitor = new PagesVisitor(pages);

		const rules = this.state.rules.map(
			rule => {
				const {actions, conditions} = rule;

				conditions.forEach(
					(condition, index) => {
						let firstOperandFieldExists = false;
						let secondOperandFieldExists = false;

						const secondOperand = condition.operands[1];

						visitor.mapFields(
							({fieldName}) => {
								if (condition.operands[0].value === fieldName) {
									firstOperandFieldExists = true;
								}

								if (secondOperand && secondOperand.value === fieldName) {
									secondOperandFieldExists = true;
								}
							}
						);

						if (condition.operands[0].value === 'user') {
							firstOperandFieldExists = true;
						}

						if (!firstOperandFieldExists) {
							RulesSupport.clearAllConditionFieldValues(condition);
						}

						if (!secondOperandFieldExists && secondOperand && secondOperand.type == 'field') {
							RulesSupport.clearSecondOperandValue(condition);
						}
					}
				);

				return {
					...rule,
					actions: RulesSupport.syncActions(pages, actions),
					conditions
				};
			}
		);

		return rules;
	}

	/**
	 * @param {!Object} event
	 * @private
	 */

	_handleFieldDeleted({rowIndex, pageIndex, columnIndex}) {
		const {pages} = this.state;
		let newContext = FormSupport.removeFields(
			pages,
			pageIndex,
			rowIndex,
			columnIndex
		);

		newContext = this._removeEmptyRow(
			newContext,
			{
				columnIndex,
				pageIndex,
				rowIndex
			}
		);

		this.setState(
			{
				focusedField: {},
				pages: newContext,
				rules: this.formatRules(newContext)
			}
		);
	}

	/**
	 * @param {!Object}
	 * @private
	 */

	_handleFieldDuplicated({rowIndex, pageIndex, columnIndex}) {
		const {pages} = this.state;
		const field = FormSupport.getField(pages, pageIndex, rowIndex, columnIndex);
		const label = sub(
			Liferay.Language.get('copy-of-x'),
			[field.label]
		);
		const newFieldName = FormSupport.generateFieldName(field.type);
		const visitor = new PagesVisitor(field.settingsContext.pages);

		const duplicatedField = {
			...field,
			fieldName: newFieldName,
			label,
			name: newFieldName,
			settingsContext: {
				...field.settingsContext,
				pages: visitor.mapFields(
					field => {
						if (field.fieldName === 'name') {
							field = {
								...field,
								value: newFieldName
							};
						}
						else if (field.fieldName === 'label') {
							field = {
								...field,
								value: label
							};
						}
						return {
							...field
						};
					}
				)
			}
		};
		const newRowIndex = rowIndex + 1;

		const newPages = FormSupport.addRow(pages, newRowIndex, pageIndex);

		FormSupport.addFieldToColumn(newPages, pageIndex, newRowIndex, columnIndex, duplicatedField);

		this.setState(
			{
				focusedField: {
					...duplicatedField,
					columnIndex,
					pageIndex,
					rowIndex: newRowIndex
				},
				pages: newPages
			}
		);
	}

	/**
	 * @param {!Object} event
	 * @private
	 */

	_handleFieldEdited(properties) {
		const {focusedField, pages} = this.state;
		const {fieldName} = focusedField;

		this.setState(
			{
				focusedField: {
					...focusedField,
					...properties
				},
				pages: FormSupport.updateField(
					pages,
					fieldName,
					properties
				)
			}
		);
	}

	/**
	 * @param {!Object} event
	 * @private
	 */

	_handleFieldMoved({target, source}) {
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

		pages = FormSupport.addRow(pages, target.rowIndex, target.pageIndex, newRow);

		pages[pageIndex].rows = FormSupport.removeEmptyRows(pages, pageIndex);

		this.setState(
			{
				pages
			}
		);
	}

	@autobind
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

	/**
	 * @param {!Number} pageIndex
	 * @private
	 */

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

	/**
	 * @param {!Array} pages
	 * @private
	 */

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

	/**
	 * @private
	 */

	_handlePageReset() {
		this.setState(
			{
				pages: [this.createNewPage()]
			}
		);
	}

	_handlePaginationModeUpdated() {
		const {paginationMode} = this.state;
		let newMode = 'pagination';

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

	/**
	 * Update the success page settings
	 * @param {!Object} successPageSettings
	 * @private
	 */
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

	/**
	 * @param {!Array} pages
	 * @param {!Object} source
	 * @private
	 * @return {Object}
	 */

	_removeEmptyRow(pages, source) {
		const {pageIndex, rowIndex} = source;

		if (!FormSupport.rowHasFields(pages, pageIndex, rowIndex)) {
			pages = FormSupport.removeRow(pages, pageIndex, rowIndex);
		}

		return pages;
	}

	_rulesValueFn() {
		const {rules} = this.props;

		return rules;
	}

	_successPageSettingsValueFn() {
		return this.props.initialSuccessPageSettings;
	}

	/**
	 * @param {!Array} pages
	 * @param {!Object} target
	 * @param {!Object} field
	 * @private
	 * @return {Object}
	 */

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

	_setInitialPages(initialPages) {
		const visitor = new PagesVisitor(initialPages);

		return visitor.mapFields(
			field => {
				return {
					...field,
					localizedValue: {},
					readOnly: true,
					value: undefined,
					visible: true
				};
			}
		);
	}

	/**
	 * Return a new page object
	 * @private
	 * @returns {object}
	 */

	createNewPage() {
		const languageId = themeDisplay.getLanguageId();
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

	render() {
		const {children, spritemap} = this.props;
		const {activePage, focusedField, pages, paginationMode, rules, successPageSettings} = this.state;

		if (children.length) {
			const events = {
				activePageUpdated: this._handleActivePageUpdated.bind(this),
				fieldAdded: this._handleFieldAdded.bind(this),
				fieldBlurred: this._handleFieldBlurred.bind(this),
				fieldChangesCanceled: this._handleFieldChangesCanceled.bind(this),
				fieldClicked: this._handleFieldClicked.bind(this),
				fieldDeleted: this._handleFieldDeleted.bind(this),
				fieldDuplicated: this._handleFieldDuplicated.bind(this),
				fieldEdited: this._handleFieldEdited.bind(this),
				fieldMoved: this._handleFieldMoved.bind(this),
				focusedFieldUpdated: this._handleFocusedFieldUpdated,
				pageAdded: this._handlePageAdded.bind(this),
				pageDeleted: this._handlePageDeleted.bind(this),
				pageReset: this._handlePageReset.bind(this),
				paginationModeUpdated: this._handlePaginationModeUpdated.bind(this),
				ruleAdded: this._handleRuleAdded.bind(this),
				ruleDeleted: this._handleRuleDeleted.bind(this),
				ruleSaved: this._handleRuleSaved.bind(this),
				successPageChanged: this._handleSuccessPageChanged.bind(this)
			};

			for (let index = 0; index < children.length; index++) {
				const child = children[index];

				Object.assign(
					child.props,
					{
						...this.otherProps(),
						activePage,
						events,
						focusedField,
						pages,
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
}

export default LayoutProvider;
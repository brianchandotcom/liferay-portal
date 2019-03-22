import * as FormSupport from '../Form/FormSupport.es';
import autobind from 'autobind-decorator';
import Component from 'metal-jsx';
import {Config} from 'metal-state';
import {pageStructure, ruleStructure} from '../../util/config.es';
import {PagesVisitor} from '../../util/visitors.es';
import {setLocalizedValue} from '../../util/i18n.es';

import handleFieldAdded from './handlers/fieldAddedHandler.es';
import handleFieldBlurred from './handlers/fieldBlurredHandler.es';
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
		editingLanguageId: Config.string(),

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
		 * @default en_US
		 * @instance
		 * @memberof LayoutProvider
		 * @type {?string}
		 */
		locale: Config.string().value(themeDisplay.getLanguageId()),

		/**
		 * @default undefined
		 * @instance
		 * @memberof LayoutProvider
		 * @type {?(array|undefined)}
		 */

		rules: Config.arrayOf(ruleStructure).valueFn('_rulesValueFn'),

		successPageSettings: Config.object().valueFn('_successPageSettingsValueFn'),

		lastResizedPosition: Config.number()
	};

	_formatCurrentColumn({pageIndex, rowIndex, columnIndex}, size) {
		const {pages} = this.state;

		let newPages = [
			...pages
		];

		const currentColumn = FormSupport.getColumn(pages, pageIndex, rowIndex, columnIndex);
		const currentPosition = FormSupport.getColumnPosition(pages, pageIndex, rowIndex, columnIndex);
		const newSize = Math.abs(currentPosition - size);
		const nextColumn = FormSupport.getColumn(newPages, pageIndex, rowIndex, columnIndex + 1);

		if (nextColumn) {
			if ((currentPosition >= size) && (currentColumn.size != 1)) {
				currentColumn.size -= newSize;
				nextColumn.size += newSize;
			}
			else if ((nextColumn.size == 1) && (nextColumn.fields.length == 0)) {
				currentColumn.size += newSize;
				newPages[pageIndex].rows[rowIndex].columns.splice(columnIndex + 1, 1);
			}
			else if (nextColumn.size != 1) {
				currentColumn.size += newSize;
				nextColumn.size -= newSize;
			}
		}

		else if ((currentColumn.size != 1) && size < 12) {
			currentColumn.size -= 1;
			newPages = FormSupport.addColumnToLastPosition(newPages, pageIndex, rowIndex);
		}

		return newPages;
	}

	_formatPreviousColumn(source, {pageIndex, rowIndex, columnIndex}, size) {
		const {pages} = this.state;

		let newPages = [
			...pages
		];

		const numberOfColumns = pages[Number(pageIndex)].rows[Number(rowIndex)].columns.length;

		if (columnIndex > numberOfColumns - 1) {
			columnIndex--;
		}

		const currentColumn = FormSupport.getColumn(pages, pageIndex, rowIndex, columnIndex);
		const previousColumn = FormSupport.getColumn(newPages, pageIndex, rowIndex, columnIndex - 1);
		const previousColumnPosition = FormSupport.getColumnPosition(pages, pageIndex, rowIndex, columnIndex - 1);

		const newSize = Math.abs(previousColumnPosition - size);

		if (previousColumn && previousColumn.size == 1 && previousColumn.fields.length == 0 && (previousColumnPosition >= size)) {
			newPages[pageIndex].rows[rowIndex].columns.splice(columnIndex - 1, 1);
			currentColumn.size += 1;
		}

		else {
			if (previousColumnPosition >= size && previousColumn.size != 1) {
				currentColumn.size += newSize;
				previousColumn.size -= newSize;
			}
			else if (previousColumn && currentColumn.size != 1 && previousColumn.size != 1) {
				currentColumn.size -= newSize;
				previousColumn.size += newSize;
			}
			else if (previousColumn && previousColumnPosition < size && previousColumn.size == 1 && currentColumn.size != 1) {
				currentColumn.size -= newSize;
				previousColumn.size += newSize;
			}
			if (!previousColumn && columnIndex == 0 && currentColumn.size != 1 && size < 12) {
				source.setAttribute('data-ddm-field-column', 1);
				currentColumn.size -= 1;
				newPages = FormSupport.addColumn(
					newPages,
					0,
					pageIndex,
					rowIndex,
					{
						fields: [],
						size: 1
					}
				);
			}
		}

		return newPages;
	}

	_handleActivePageUpdated(activePage) {
		this.setState(
			{
				activePage
			}
		);
	}

	_handleColumnResized({source, sourceIndexes, leftResize, targetDatasetCol}) {
		const {pageIndex, rowIndex} = sourceIndexes;
		const size = Number(targetDatasetCol);

		let newPages;

		if (leftResize) {
			newPages = this._formatPreviousColumn(source, sourceIndexes, size);
		}
		else {
			newPages = this._formatCurrentColumn(sourceIndexes, size);
			if (newPages[pageIndex].rows[rowIndex].columns.length > 1) {
				newPages = this._removeColumnWithSizeZero(newPages, pageIndex, rowIndex);
			}
		}

		newPages[pageIndex].rows = FormSupport.removeEmptyRows(newPages, pageIndex);

		this.setState(
			{
				lastResizedPosition: size,
				pages: [
					...newPages
				]
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

	/**
	 * @param {!Object} event
	 * @private
	 */

	_handleFieldAdded(event) {
		this.setState(handleFieldAdded(this.props, this.state, event));
	}

	_handleFieldBlurred(event) {
		this.setState(handleFieldBlurred(this.state, event));
	}

	_handleSidebarFieldBlurred() {
		this.setState(
			{
				focusedField: {}
			}
		);
	}

	/**
	 * @param {!Object} event
	 * @private
	 */

	_handleFieldDeleted(event) {
		this.setState(handleFieldDeleted(this.state, event));
	}

	/**
	 * @param {!Object}
	 * @private
	 */

	_handleFieldDuplicated(event) {
		this.setState(handleFieldDuplicated(this.state, event));
	}

	/**
	 * @param {!Object} event
	 * @private
	 */

	_handleFieldEdited(properties) {
		this.setState(handleFieldEdited(this.state, properties));
	}

	/**
	 * @param {!Object} event
	 * @private
	 */

	_handleFieldMoved({targetIsEmptyRow, target, source}) {
		let {pages} = this.state;
		const {columnIndex, pageIndex, rowIndex} = source;
		const targetColumnIndex = target.columnIndex;
		const targetRowIndex = target.rowIndex;

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

		if (targetRowIndex > pages[Number(pageIndex)].rows.length - 1) {
			pages = FormSupport.addRow(pages, target.rowIndex, target.pageIndex, newRow);
		}
		else if (targetIsEmptyRow) {
			pages = FormSupport.addRow(pages, target.rowIndex, target.pageIndex, newRow);
		}
		else {
			pages = FormSupport.addFieldToColumn(pages, target.pageIndex, targetRowIndex, targetColumnIndex, fields);
		}

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

	_handlePagesUpdated(pages) {
		this.setState(
			{
				pages
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

	_resizeNextColumn(nextColumn, currentPosition, size, newSize) {
		if (nextColumn && currentPosition >= size) {
			nextColumn.size += newSize;
		}
		else if (nextColumn) {
			nextColumn.size -= newSize;
		}
	}

	_resizePreviousColumn(previousColumn, previousColumnPosition, size, newSize) {
		if (previousColumn && previousColumnPosition >= size) {
			previousColumn.size -= newSize;
		}
		else if (previousColumn) {
			previousColumn.size += newSize;
		}
	}

	/**
	 * @param {!Object} data
	 * @private
	 */

	_removeColumnWithSizeZero(newPages, pageIndex, rowIndex) {
		const columnsArray = newPages[pageIndex].rows[rowIndex].columns;

		if (columnsArray.some(e => e.size == 0)) {
			delete columnsArray.pop();
		}
		return newPages;
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
		const {children, editingLanguageId, spritemap} = this.props;
		const {activePage, focusedField, pages, paginationMode, rules, successPageSettings} = this.state;

		if (children.length) {
			const events = {
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
				focusedFieldUpdated: this._handleFocusedFieldUpdated,
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

			for (let index = 0; index < children.length; index++) {
				const child = children[index];

				Object.assign(
					child.props,
					{
						...this.otherProps(),
						activePage,
						editingLanguageId,
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
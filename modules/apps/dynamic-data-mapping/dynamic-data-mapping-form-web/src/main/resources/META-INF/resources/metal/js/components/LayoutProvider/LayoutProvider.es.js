import {Config} from 'metal-state';
import {FormSupport} from '../Form/index.es';
import {pageStructure} from '../../util/config.es';
import {PagesVisitor} from '../../util/visitors.es';
import {setLocalizedValue} from '../../util/i18n.es';
import {sub} from '../../util/strings.es';
import Component from 'metal-jsx';

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

		initialPages: Config.arrayOf(pageStructure).value([]),

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
		).value({})
	};

	_pagesValueFn() {
		return this.props.initialPages;
	}

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

	_handleClickedField(data) {
		this.setState(
			{
				focusedField: data
			}
		);
	}

	/**
	 * @param {!Object} payload
	 * @private
	 */

	_handleFieldAdded({fieldType, target}) {
		const {pageIndex, rowIndex} = target;
		const {spritemap} = this.props;
		let {columnIndex} = target;
		let {pages} = this.state;

		const fieldProperties = {
			...fieldType,
			name: fieldType.name,
			spritemap,
			type: fieldType.name
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
				pages: newContext
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
		const newFieldName = FormSupport.generateFieldName(field.type);
		const visitor = new PagesVisitor(field.settingsContext.pages);

		const duplicatedField = {
			...field,
			fieldName: newFieldName,
			label: sub(
				Liferay.Language.get('copy-of-x'),
				[field.label]
			),
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
						return field;
					}
				)
			}
		};

		const newRowIndex = rowIndex + 1;

		const newPages = FormSupport.addRow(pages, newRowIndex, pageIndex);

		FormSupport.addFieldToColumn(newPages, pageIndex, newRowIndex, columnIndex, duplicatedField);

		this.setState(
			{
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
		pages = FormSupport.removeFields(
			pages,
			pageIndex,
			rowIndex,
			columnIndex
		);
		if (FormSupport.rowHasFields(pages, target.pageIndex, target.rowIndex)) {
			pages = FormSupport.addRow(pages, target.rowIndex, target.pageIndex);
			target.columnIndex = 0;
		}
		pages = this._setColumnFields(
			pages,
			target,
			fields
		);
		pages = this._removeEmptyRow(pages, source);

		this.setState(
			{
				focusedField: fields[0],
				pages
			}
		);
	}

	/**
	 * @param {!Number} pageIndex
	 * @private
	 */

	_handlePageDeleted(pageIndex) {
		const {pages} = this.state;

		console.log(pageIndex, pages);

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

	/**
	 * @param {!Array} pages
	 * @private
	 */

	_handlePagesUpdated(pages) {
		this.setState(
			{
				pages
			}
		);
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

	/**
	 * @param {!Array} pages
	 * @param {!Object} target
	 * @param {!Object} field
	 * @private
	 * @return {Object}
	 */

	_addRow(pages, target, fields) {
		const {pageIndex, rowIndex} = target;
		const newRow = FormSupport.implAddRow(12, fields);

		return FormSupport.addRow(pages, rowIndex, pageIndex, newRow);
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

	render() {
		const {children, spritemap} = this.props;
		const {activePage, focusedField, pages} = this.state;

		if (children.length) {
			const events = {
				activePageUpdated: this._handleActivePageUpdated.bind(this),
				fieldAdded: this._handleFieldAdded.bind(this),
				fieldBlurred: this._handleFieldBlurred.bind(this),
				fieldClicked: this._handleClickedField.bind(this),
				fieldDeleted: this._handleFieldDeleted.bind(this),
				fieldDuplicated: this._handleFieldDuplicated.bind(this),
				fieldEdited: this._handleFieldEdited.bind(this),
				fieldMoved: this._handleFieldMoved.bind(this),
				pageAdded: this._handlePageAdded.bind(this),
				pageDeleted: this._handlePageDeleted.bind(this),
				pageReset: this._handlePageReset.bind(this),
				pagesUpdated: this._handlePagesUpdated.bind(this)
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
						spritemap
					}
				);
			}
		}

		return children;
	}
}

export default LayoutProvider;
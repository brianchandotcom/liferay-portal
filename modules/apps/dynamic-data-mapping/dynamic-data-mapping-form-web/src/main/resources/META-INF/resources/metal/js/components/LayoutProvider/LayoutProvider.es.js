import {Config} from 'metal-state';
import {FormSupport} from '../Form/index.es';
import {pageStructure} from '../../util/config.es';
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

		pages: Config.arrayOf(pageStructure).value([]),

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

		pages: Config.array(),

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
		 * @default add
		 * @instance
		 * @memberof LayoutProvider
		 * @type {?string}
		 */

		mode: Config.oneOf(['add', 'edit']).value('add')
	};

	/**
	 * @inheritDoc
	 */

	constructor(props, pages) {
		super(props, pages);

		this.state.pages = props.pages;
	}

	/**
	 * @param {!Object} data
	 * @private
	 */

	_handleClickedField(data) {
		this.setState(
			{
				focusedField: data,
				mode: 'edit'
			}
		);
	}

	/**
	 * @param {!Object} payload
	 * @private
	 */

	_handleFieldAdd({target, fieldProperties}) {
		const {spritemap} = this.props;
		const {pages} = this.state;
		const {columnIndex, pageIndex, rowIndex} = target;

		fieldProperties = Object.assign({}, fieldProperties, {spritemap});

		let newContext = null;

		if (target.columnIndex === false) {
			const newRow = FormSupport.implAddRow(12, [fieldProperties]);

			newContext = FormSupport.addRow(
				pages,
				rowIndex,
				pageIndex,
				newRow
			);
		}
		else {
			newContext = FormSupport.addFieldToColumn(
				pages,
				pageIndex,
				rowIndex,
				columnIndex,
				fieldProperties
			);
		}

		this.setState(
			{
				focusedField: {
					columnIndex,
					pageIndex,
					rowIndex,
					type: fieldProperties.type
				},
				mode: 'edit',
				pages: newContext
			}
		);
	}

	/**
	 * @param {!Object} event
	 * @private
	 */

	_handleDeleteField({rowIndex, pageIndex, columnIndex}) {
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

	_handleDuplicatedField({rowIndex, pageIndex, columnIndex}) {
		const {pages} = this.state;
		const field = FormSupport.getField(pages, pageIndex, rowIndex, columnIndex);

		const duplicatedField = {
			...field,
			label: sub(
				Liferay.Language.get('copy-of-x'),
				[field.label]
			),
			name: FormSupport.generateFieldName(field)
		};

		const newRowIndex = rowIndex + 1;

		const newContext = FormSupport.addRow(pages, newRowIndex, pageIndex);

		FormSupport.addFieldToColumn(newContext, pageIndex, newRowIndex, columnIndex, duplicatedField);

		this.setState(
			{
				pages: newContext
			}
		);
	}

	/**
	 * @param {!Object} event
	 * @private
	 */

	_handleFieldEdited({value, key}) {
		const {focusedField, pages} = this.state;
		const {columnIndex, pageIndex, rowIndex} = focusedField;
		const column = FormSupport.getColumn(
			pages,
			pageIndex,
			rowIndex,
			columnIndex
		);

		const implPropertiesField = {
			[key]: value
		};

		const newField = Object.assign(
			{},
			column.fields[0],
			implPropertiesField
		);

		FormSupport.changeFieldsFromColumn(
			pages,
			pageIndex,
			rowIndex,
			columnIndex,
			[newField]
		);

		this.setState(
			{
				pages: this.state.pages
			}
		);
	}

	/**
	 * @param {!Object} event
	 * @private
	 */

	_handleFieldMoved({target, source}) {
		const {pages} = this.state;
		const {columnIndex, pageIndex, rowIndex} = source;
		const column = FormSupport.getColumn(
			pages,
			pageIndex,
			rowIndex,
			columnIndex
		);
		const {fields} = column;

		let newContext = FormSupport.removeFields(
			pages,
			pageIndex,
			rowIndex,
			columnIndex
		);

		if (target.columnIndex === false) {
			newContext = this._removeEmptyRow(newContext, source);
			newContext = this._addRow(
				newContext,
				target,
				fields
			);
		}
		else {
			newContext = this._setColumnFields(
				newContext,
				target,
				fields
			);
			newContext = this._removeEmptyRow(newContext, source);
		}

		this.setState(
			{
				focusedField: fields[0],
				pages: newContext
			}
		);
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

		if (!FormSupport.hasFieldsRow(pages, pageIndex, rowIndex)) {
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
		const {focusedField, mode, pages} = this.state;

		if (children.length) {
			const events = {
				deleteField: this._handleDeleteField.bind(this),
				duplicateField: this._handleDuplicatedField.bind(this),
				fieldAdded: this._handleFieldAdd.bind(this),
				fieldClicked: this._handleClickedField.bind(this),
				fieldEdited: this._handleFieldEdited.bind(this),
				fieldMoved: this._handleFieldMoved.bind(this),
				pagesUpdated: this._handlePagesUpdated.bind(this)
			};

			for (let index = 0; index < children.length; index++) {
				const child = children[index];

				Object.assign(
					child.props,
					{
						...this.otherProps(),
						events,
						focusedField,
						mode,
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
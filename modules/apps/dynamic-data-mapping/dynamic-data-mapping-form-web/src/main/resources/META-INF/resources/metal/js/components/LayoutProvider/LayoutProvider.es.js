import {Config} from 'metal-state';
import {LayoutSupport} from '../Layout/index.es';
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

		pages: Config.array(),

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
			const newRow = LayoutSupport.implAddRow(12, [fieldProperties]);

			newContext = LayoutSupport.addRow(
				pages,
				rowIndex,
				pageIndex,
				newRow
			);
		}
		else {
			newContext = LayoutSupport.addFieldToColumn(
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
		let newContext = LayoutSupport.removeFields(
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
		const field = LayoutSupport.getField(pages, pageIndex, rowIndex, columnIndex);

		const duplicatedField = {
			...field,
			name: LayoutSupport.generateFieldName(field)
		};

		const newRowIndex = rowIndex + 1;

		const newContext = LayoutSupport.addRow(pages, newRowIndex, pageIndex);

		LayoutSupport.addFieldToColumn(newContext, pageIndex, newRowIndex, columnIndex, duplicatedField);

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
		const column = LayoutSupport.getColumn(
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

		LayoutSupport.changeFieldsFromColumn(
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
		const column = LayoutSupport.getColumn(
			pages,
			pageIndex,
			rowIndex,
			columnIndex
		);
		const {fields} = column;

		let newContext = LayoutSupport.removeFields(
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
	 * @param {!Object} pages
	 * @private
	 */

	_handleUpdatePages(pages) {
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

		if (!LayoutSupport.hasFieldsRow(pages, pageIndex, rowIndex)) {
			pages = LayoutSupport.removeRow(pages, pageIndex, rowIndex);
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
		const newRow = LayoutSupport.implAddRow(12, fields);

		return LayoutSupport.addRow(pages, rowIndex, pageIndex, newRow);
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

		return LayoutSupport.setColumnFields(
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

		let child;

		if (children.length) {
			const Child = children[0];

			const events = {
				deleteField: this._handleDeleteField.bind(this),
				duplicateField: this._handleDuplicatedField.bind(this),
				fieldAdded: this._handleFieldAdd.bind(this),
				fieldClicked: this._handleClickedField.bind(this),
				fieldEdited: this._handleFieldEdited.bind(this),
				fieldMoved: this._handleFieldMoved.bind(this),
				updatePages: this._handleUpdatePages.bind(this)
			};

			Object.assign(
				Child.props,
				{
					...this.otherProps(),
					events,
					focusedField,
					mode,
					pages,
					spritemap
				}
			);

			child = Child;
		}

		return child;
	}
}

export default LayoutProvider;
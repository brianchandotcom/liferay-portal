import {Config} from 'metal-state';
import {LayoutSupport} from '../Layout/index.es';
import Component from 'metal-jsx';

/**
 * LayoutProvider listens to your children's events to
 * control the `context` and make manipulations.
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

		context: Config.array(),

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

		context: Config.array(),

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

	constructor(props, context) {
		super(props, context);

		this.state.context = props.context;
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
		const {context} = this.state;
		const {columnIndex, pageIndex, rowIndex} = target;

		fieldProperties = Object.assign({}, fieldProperties, {spritemap});

		let newContext = null;

		if (target.columnIndex === false) {
			const newRow = LayoutSupport.implAddRow(12, [fieldProperties]);

			newContext = LayoutSupport.addRow(
				context,
				rowIndex,
				pageIndex,
				newRow
			);
		}
		else {
			newContext = LayoutSupport.addFieldToColumn(
				context,
				pageIndex,
				rowIndex,
				columnIndex,
				fieldProperties
			);
		}

		this.setState(
			{
				context: newContext,
				focusedField: {
					columnIndex,
					pageIndex,
					rowIndex,
					type: fieldProperties.type
				},
				mode: 'edit'
			}
		);
	}

	/**
	 * @param {!Object} event
	 * @private
	 */

	_handleDeleteField({rowIndex, pageIndex, columnIndex}) {
		const {context} = this.state;
		let newContext = LayoutSupport.removeFields(
			context,
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
				context: newContext,
				focusedField: {}
			}
		);
	}

	/**
	 * @param {!Object}
	 * @private
	 */

	_handleDuplicatedField({rowIndex, pageIndex, columnIndex}) {
		const {context} = this.state;
		const field = LayoutSupport.getField(context, pageIndex, rowIndex, columnIndex);

		const duplicatedField = {
			...field,
			name: LayoutSupport.generateFieldName(field)
		};

		const newRowIndex = rowIndex + 1;

		const newContext = LayoutSupport.addRow(context, newRowIndex, pageIndex);

		LayoutSupport.addFieldToColumn(newContext, pageIndex, newRowIndex, columnIndex, duplicatedField);

		this.setState(
			{
				context: newContext
			}
		);
	}

	/**
	 * @param {!Object} event
	 * @private
	 */

	_handleFieldEdited({value, key}) {
		const {context, focusedField} = this.state;
		const {columnIndex, pageIndex, rowIndex} = focusedField;
		const column = LayoutSupport.getColumn(
			context,
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
			context,
			pageIndex,
			rowIndex,
			columnIndex,
			[newField]
		);

		this.setState(
			{
				context: this.state.context
			}
		);
	}

	/**
	 * @param {!Object} event
	 * @private
	 */

	_handleFieldMoved({target, source}) {
		const {context} = this.state;
		const {columnIndex, pageIndex, rowIndex} = source;
		const column = LayoutSupport.getColumn(
			context,
			pageIndex,
			rowIndex,
			columnIndex
		);
		const {fields} = column;

		let newContext = LayoutSupport.removeFields(
			context,
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
				context: newContext,
				focusedField: fields[0]
			}
		);
	}

	/**
	 * @param {!Array} context
	 * @param {!Object} source
	 * @private
	 * @return {Object}
	 */

	_removeEmptyRow(context, source) {
		const {pageIndex, rowIndex} = source;

		if (!LayoutSupport.hasFieldsRow(context, pageIndex, rowIndex)) {
			context = LayoutSupport.removeRow(context, pageIndex, rowIndex);
		}

		return context;
	}

	/**
	 * @param {!Array} context
	 * @param {!Object} target
	 * @param {!Object} field
	 * @private
	 * @return {Object}
	 */

	_addRow(context, target, fields) {
		const {pageIndex, rowIndex} = target;
		const newRow = LayoutSupport.implAddRow(12, fields);

		return LayoutSupport.addRow(context, rowIndex, pageIndex, newRow);
	}

	/**
	 * @param {!Array} context
	 * @param {!Object} target
	 * @param {!Object} field
	 * @private
	 * @return {Object}
	 */

	_setColumnFields(context, target, fields) {
		const {columnIndex, pageIndex, rowIndex} = target;

		return LayoutSupport.setColumnFields(
			context,
			pageIndex,
			rowIndex,
			columnIndex,
			fields
		);
	}

	render() {
		const {children, spritemap} = this.props;
		const {context, focusedField, mode} = this.state;

		let child;

		if (children.length) {
			const Child = children[0];

			const events = {
				deleteField: this._handleDeleteField.bind(this),
				duplicateField: this._handleDuplicatedField.bind(this),
				fieldAdded: this._handleFieldAdd.bind(this),
				fieldClicked: this._handleClickedField.bind(this),
				fieldEdited: this._handleFieldEdited.bind(this),
				fieldMoved: this._handleFieldMoved.bind(this)
			};

			Object.assign(
				Child.props,
				{
					...this.otherProps(),
					context,
					events,
					focusedField,
					mode,
					spritemap
				}
			);

			child = Child;
		}

		return child;
	}
}

export default LayoutProvider;
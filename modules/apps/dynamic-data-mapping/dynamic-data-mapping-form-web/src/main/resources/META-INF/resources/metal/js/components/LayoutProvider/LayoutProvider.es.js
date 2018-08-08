import Component from 'metal-jsx';
import {Config} from 'metal-state';

import {LayoutSupport} from '../Layout/index.es';

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
		spritemap: Config.string(),
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
		 * @default add
		 * @instance
		 * @memberof LayoutProvider
		 * @type {?string}
		 */
		mode: Config.oneOf(['add', 'edit']).value('add'),
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
	_handleFieldClicked(data) {
		this.setState({
			focusedField: data,
			mode: 'edit',
		});
	}

	/**
	 * @param {!Object} payload
	 * @private
	 */
	_handleFieldAdd({target, fieldProperties}) {
		const {spritemap} = this.props;
		const {context} = this.state;
		const {rowIndex, pageIndex, columnIndex} = target;

		fieldProperties = Object.assign({}, fieldProperties, {spritemap});

		let newContext = null;

		if (target.columnIndex === false) {
			let newRow = LayoutSupport.implAddRow(12, [fieldProperties]);

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

		this.setState({
			context: newContext,
			focusedField: {
				columnIndex,
				pageIndex,
				rowIndex,
				type: fieldProperties.type,
			},
			mode: 'edit',
		});
	}

	/**
	 * @param {!Object} event
	 * @private
	 */
	_handleFieldDelete({rowIndex, pageIndex, columnIndex}) {
		const {context} = this.state;
		let newContext = LayoutSupport.removeFields(
			context,
			pageIndex,
			rowIndex,
			columnIndex
		);

		newContext = this._cleanRowEmpty(newContext, {
			rowIndex,
			pageIndex,
			columnIndex,
		});

		this.setState({
			context: newContext,
			focusedField: {},
		});
	}

	/**
	 * @param {!Object} event
	 * @private
	 */
	_handleFieldEdited({value, key}) {
		const {context, focusedField} = this.state;
		const {columnIndex, pageIndex, rowIndex} = focusedField;
		const fieldSelected = LayoutSupport.getColumn(
			context,
			pageIndex,
			rowIndex,
			columnIndex
		);

		const implPropertiesField = {
			[key]: value,
		};

		const newField = Object.assign(
			{},
			fieldSelected[0],
			implPropertiesField
		);

		LayoutSupport.changeFieldsFromColumn(
			context,
			pageIndex,
			rowIndex,
			columnIndex,
			[newField]
		);

		this.setState({
			context: this.state.context,
		});
	}

	/**
	 * @param {!Object} event
	 * @private
	 */
	_handleFieldMoved({target, source}) {
		const {context} = this.state;
		const fieldSourceToMove = this._getFieldSourceToMove(context, source);

		let newContext = LayoutSupport.removeFields(
			context,
			source.pageIndex,
			source.rowIndex,
			source.columnIndex
		);

		if (target.columnIndex === false) {
			newContext = this._cleanRowEmpty(newContext, source);
			newContext = this._addFieldToRow(
				newContext,
				target,
				fieldSourceToMove
			);
		}
		else {
			newContext = this._addFieldToColumn(
				newContext,
				target,
				fieldSourceToMove
			);
			newContext = this._cleanRowEmpty(newContext, source);
		}

		this.setState({
			context: newContext,
			focusedField: {},
		});

		newContext = null;
	}

	/**
	 * @param {!Array} context
	 * @param {!Object} source
	 * @private
	 * @return {Object}
	 */
	_cleanRowEmpty(context, source) {
		const {rowIndex, pageIndex} = source;

		if (!LayoutSupport.hasFieldsRow(context, pageIndex, rowIndex)) {
			return LayoutSupport.removeRow(context, pageIndex, rowIndex);
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
	_addFieldToRow(context, target, field) {
		const {rowIndex, pageIndex} = target;
		const newRow = LayoutSupport.implAddRow(12, field);

		return LayoutSupport.addRow(context, rowIndex, pageIndex, newRow);
	}

	/**
	 * @param {!Array} context
	 * @param {!Object} target
	 * @param {!Object} field
	 * @private
	 * @return {Object}
	 */
	_addFieldToColumn(context, target, field) {
		const {rowIndex, pageIndex, columnIndex} = target;

		return LayoutSupport.addFields(
			context,
			pageIndex,
			rowIndex,
			columnIndex,
			field
		);
	}

	/**
	 * @param {!Array} context
	 * @param {!Object} source
	 * @private
	 * @return {Object}
	 */
	_getFieldSourceToMove(context, source) {
		const {rowIndex, pageIndex, columnIndex} = source;

		return LayoutSupport.getColumn(
			context,
			pageIndex,
			rowIndex,
			columnIndex
		);
	}

	render() {
		const {children, spritemap} = this.props;
		const {focusedField, context, mode} = this.state;

		if (children.length) {
			const Child = children[0];

			const events = {
				fieldAdded: this._handleFieldAdd.bind(this),
				fieldEdited: this._handleFieldEdited.bind(this),
				fieldClicked: this._handleFieldClicked.bind(this),
				fieldDeleted: this._handleFieldDelete.bind(this),
				fieldMoved: this._handleFieldMoved.bind(this),
			};

			Object.assign(Child.props, {
				...this.otherProps(),
				events,
				context,
				focusedField,
				mode,
				spritemap,
			});

			return Child;
		}

		return;
	}
}

export default LayoutProvider;

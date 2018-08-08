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
		fieldFocus: Config.shapeOf({
			indexColumn: Config.oneOfType([
				Config.bool().value(false),
				Config.number(),
			]).required(),
			indexPage: Config.number().required(),
			indexRow: Config.number().required(),
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
			fieldFocus: data,
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
		const {indexRow, indexPage, indexColumn} = target;

		fieldProperties = Object.assign({}, fieldProperties, {spritemap});

		let newContext = null;

		if (target.indexColumn === false) {
			let newRow = LayoutSupport.implAddRow(12, [fieldProperties]);

			newContext = LayoutSupport.addRow(
				context,
				indexRow,
				indexPage,
				newRow
			);
		}
		else {
			newContext = LayoutSupport.addFieldToColumn(
				context,
				indexPage,
				indexRow,
				indexColumn,
				fieldProperties
			);
		}

		this.setState({
			context: newContext,
			fieldFocus: {
				indexColumn,
				indexPage,
				indexRow,
				type: fieldProperties.type,
			},
			mode: 'edit',
		});
	}

	/**
	 * @param {!Object} event
	 * @private
	 */
	_handleFieldDelete({indexRow, indexPage, indexColumn}) {
		const {context} = this.state;
		let newContext = LayoutSupport.removeFields(
			context,
			indexPage,
			indexRow,
			indexColumn
		);

		newContext = this._cleanRowEmpty(newContext, {
			indexRow,
			indexPage,
			indexColumn,
		});

		this.setState({
			context: newContext,
			fieldFocus: {},
		});
	}

	/**
	 * @param {!Object} event
	 * @private
	 */
	_handleFieldEdit({value, key}) {
		const {context, fieldFocus} = this.state;
		const {indexColumn, indexPage, indexRow} = fieldFocus;
		const fieldSelected = LayoutSupport.getColumn(
			context,
			indexPage,
			indexRow,
			indexColumn
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
			indexPage,
			indexRow,
			indexColumn,
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
	_handleFieldMove({target, source}) {
		const {context} = this.state;
		const fieldSourceToMove = this._getFieldSourceToMove(context, source);

		let newContext = LayoutSupport.removeFields(
			context,
			source.indexPage,
			source.indexRow,
			source.indexColumn
		);

		if (target.indexColumn === false) {
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
			fieldFocus: {},
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
		const {indexRow, indexPage} = source;

		if (!LayoutSupport.hasFieldsRow(context, indexPage, indexRow)) {
			return LayoutSupport.removeRow(context, indexPage, indexRow);
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
		const {indexRow, indexPage} = target;
		const newRow = LayoutSupport.implAddRow(12, field);

		return LayoutSupport.addRow(context, indexRow, indexPage, newRow);
	}

	/**
	 * @param {!Array} context
	 * @param {!Object} target
	 * @param {!Object} field
	 * @private
	 * @return {Object}
	 */
	_addFieldToColumn(context, target, field) {
		const {indexRow, indexPage, indexColumn} = target;

		return LayoutSupport.addFields(
			context,
			indexPage,
			indexRow,
			indexColumn,
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
		const {indexRow, indexPage, indexColumn} = source;

		return LayoutSupport.getColumn(
			context,
			indexPage,
			indexRow,
			indexColumn
		);
	}

	render() {
		const {children, spritemap} = this.props;
		const {fieldFocus, context, mode} = this.state;

		if (children.length) {
			const Child = children[0];

			const events = {
				fieldAdd: this._handleFieldAdd.bind(this),
				fieldEdit: this._handleFieldEdit.bind(this),
				fieldClicked: this._handleFieldClicked.bind(this),
				fieldDelete: this._handleFieldDelete.bind(this),
				fieldMove: this._handleFieldMove.bind(this),
			};

			Object.assign(Child.props, {
				...this.otherProps(),
				events,
				context,
				fieldFocus,
				mode,
				spritemap,
			});

			return Child;
		}

		return;
	}
}

export default LayoutProvider;

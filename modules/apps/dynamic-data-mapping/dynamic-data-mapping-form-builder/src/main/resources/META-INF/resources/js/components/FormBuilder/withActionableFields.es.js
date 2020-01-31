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

import {ClayActionsDropdown} from 'clay-dropdown';
import {FormSupport, PagesVisitor} from 'dynamic-data-mapping-form-renderer';
import dom from 'metal-dom';
import {EventHandler} from 'metal-events';
import Component from 'metal-jsx';
import {Config} from 'metal-state';

import {focusedFieldStructure, pageStructure} from '../../util/config.es';

const getFieldIndexes = (pages, fieldName) => {
	const visitor = new PagesVisitor(pages);
	let indexes = {};

	visitor.mapFields((field, fieldIndex, columnIndex, rowIndex, pageIndex) => {
		if (field.fieldName === fieldName) {
			indexes = {columnIndex, pageIndex, rowIndex};
		}
	});

	return indexes;
};

const getFieldContainer = (pages, fieldName) => {
	const {columnIndex, pageIndex, rowIndex} = getFieldIndexes(
		pages,
		fieldName
	);

	return document.querySelector(
		[
			'.col-ddm',
			`[data-ddm-field-column="${columnIndex}"]`,
			`[data-ddm-field-page="${pageIndex}"]`,
			`[data-ddm-field-row="${rowIndex}"]`,
			' .ddm-field-container'
		].join('')
	);
};

class Actions extends Component {
	created() {
		this.on('fieldNameChanged', this._handleFieldNameChanged);

		this.expanded = false;
	}

	render() {
		const {expanded} = this;
		const {disabled, items, label, spritemap} = this.props;

		return (
			<div
				class="ddm-field-actions-container"
				onMouseDown={this._handleElementClicked.bind(this)}
			>
				<span class="actions-label">{label}</span>

				<ClayActionsDropdown
					disabled={disabled}
					events={{
						expandedChanged: this._handleExpandedChanged.bind(this),
						itemClicked: this._handleItemClicked.bind(this)
					}}
					expanded={expanded}
					items={items}
					ref="dropdown"
					spritemap={spritemap}
				/>
			</div>
		);
	}

	syncExpanded(expanded) {
		const {pages} = this.props;
		const {fieldName} = this.state;
		const fieldContainer = getFieldContainer(pages, fieldName);

		if (!fieldContainer) {
			return;
		}

		if (expanded) {
			fieldContainer.classList.add('expanded');
		} else {
			fieldContainer.classList.remove('expanded');
		}
	}

	_handleElementClicked({target}) {
		const {disabled} = this.props;
		const {dropdown} = this.refs;

		if (!dropdown.element.contains(target)) {
			const {dispatch} = this.context;
			const {fieldName} = this.state;
			const {pages} = this.props;
			const indexes = getFieldIndexes(pages, fieldName);

			dispatch('fieldClicked', indexes);
		} else if (!this.expanded && !disabled) {
			this.expanded = true;
		}
	}

	_handleExpandedChanged({newVal}) {
		this.expanded = newVal;

		this.syncExpanded(newVal);
	}

	_handleFieldNameChanged({newVal, prevVal}) {
		const {pages} = this.props;
		const {expanded} = this.state;
		const newFieldContainer = getFieldContainer(pages, newVal);
		const prevFieldContainer = getFieldContainer(pages, prevVal);

		if (prevFieldContainer && newFieldContainer !== prevFieldContainer) {
			prevFieldContainer.classList.remove('expanded');

			if (expanded && newFieldContainer) {
				newFieldContainer.classList.add('expanded');
			}
		}
	}

	_handleItemClicked({
		data: {
			item: {action}
		}
	}) {
		const {fieldName} = this.state;
		const {pages} = this.props;
		const indexes = getFieldIndexes(pages, fieldName);

		action(indexes);

		this.refs.dropdown.expanded = false;
	}
}

Actions.PROPS = {
	/**
	 * @default false
	 * @instance
	 * @memberof Actions
	 * @type {!boolean}
	 */

	disabled: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Actions
	 * @type {!string}
	 */

	label: Config.string(),

	/**
	 * @default []
	 * @instance
	 * @memberof Actions
	 * @type {?array<object>}
	 */

	pages: Config.arrayOf(pageStructure).value([]),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Actions
	 * @type {!string}
	 */

	spritemap: Config.string().required()
};

Actions.STATE = {
	/**
	 * @default {}
	 * @instance
	 * @memberof Actions
	 * @type {!Object}
	 */

	fieldName: Config.string()
};

const withActionableFields = ChildComponent => {
	class ActionableFields extends Component {
		attached() {
			this._eventHandler = new EventHandler();

			this._eventHandler.add(
				this.delegate(
					'mouseenter',
					'.ddm-field-container',
					this._handleMouseEnterField.bind(this)
				)
			);
		}

		disposeInternal() {
			super.disposeInternal();

			this._eventHandler.removeAllListeners();
		}

		isActionsEnabled() {
			const {defaultLanguageId, editingLanguageId} = this.props;

			return defaultLanguageId === editingLanguageId;
		}

		render() {
			const {fieldActions, pages, spritemap} = this.props;

			return (
				<div>
					<ChildComponent {...this.props} />

					<Actions
						disabled={!this.isActionsEnabled()}
						items={fieldActions}
						pages={pages}
						portalElement={document.body}
						ref="selectedFieldActions"
						spritemap={spritemap}
						visible={false}
					/>

					<Actions
						disabled={!this.isActionsEnabled()}
						items={fieldActions}
						pages={pages}
						portalElement={document.body}
						ref="hoveredFieldActions"
						spritemap={spritemap}
						visible={false}
					/>
				</div>
			);
		}

		rendered() {
			const {focusedField} = this.props;
			const {hoveredFieldActions, selectedFieldActions} = this.refs;

			if (Object.keys(focusedField).length > 0) {
				const {fieldName} = focusedField;

				this.showActions(selectedFieldActions, fieldName);
			} else {
				selectedFieldActions.props.visible = false;
			}

			if (hoveredFieldActions.state.fieldName) {
				this.showActions(
					hoveredFieldActions,
					hoveredFieldActions.state.fieldName
				);
			}
		}

		showActions(actions, fieldName) {
			actions.props.label = this._getFieldType(fieldName);
			actions.props.visible = true;

			if (fieldName !== actions.state.fieldName) {
				actions.setState({fieldName});

				actions.refs.dropdown.expanded = false;
			}

			actions.forceUpdate(() => {
				window.requestAnimationFrame(() => {
					const {pages} = this.props;
					const fieldContainer = getFieldContainer(pages, fieldName);

					if (fieldContainer) {
						fieldContainer.appendChild(actions.element);
					}
				});
			});
		}

		_getColumnField(indexes) {
			const {pages} = this.props;
			const visitor = new PagesVisitor(pages);
			let field;

			visitor.mapFields(
				(
					currentField,
					fieldIndex,
					columnIndex,
					rowIndex,
					pageIndex
				) => {
					if (
						indexes.pageIndex === pageIndex &&
						indexes.rowIndex === rowIndex &&
						indexes.columnIndex === columnIndex
					) {
						field = currentField;
					}
				}
			);

			return field;
		}

		_getFieldType(fieldName) {
			const {fieldTypes, pages} = this.props;
			const visitor = new PagesVisitor(pages);
			const field = visitor.findField(
				field => field.fieldName === fieldName
			);

			return (
				field &&
				fieldTypes.find(fieldType => {
					return fieldType.name === field.type;
				}).label
			);
		}

		_handleMouseEnterField({delegateTarget}) {
			if (!delegateTarget.classList.contains('selected')) {
				const {hoveredFieldActions} = this.refs;
				const indexes = FormSupport.getIndexes(
					dom.closest(delegateTarget, '.col-ddm')
				);
				const {fieldName} = this._getColumnField(indexes);

				this.showActions(hoveredFieldActions, fieldName);
			}
		}
	}

	ActionableFields.PROPS = {
		/**
		 * @default
		 * @instance
		 * @memberof FormBuilder
		 * @type {?number}
		 */

		activePage: Config.number().value(0),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FormBuilder
		 * @type {?string}
		 */

		defaultLanguageId: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FormBuilder
		 * @type {?string}
		 */

		editingLanguageId: Config.string(),

		/**
		 * @default []
		 * @instance
		 * @memberof FormBuilder
		 * @type {?(array|undefined)}
		 */

		fieldActions: Config.array().value([]),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FormBuilder
		 * @type {?string}
		 */

		fieldSetDefinitionURL: Config.string(),

		/**
		 * @default []
		 * @instance
		 * @memberof FormBuilder
		 * @type {?(array|undefined)}
		 */

		fieldSets: Config.array().value([]),

		/**
		 * @default []
		 * @instance
		 * @memberof FormBuilder
		 * @type {?(array|undefined)}
		 */

		fieldTypes: Config.array().value([]),

		/**
		 * @default {}
		 * @instance
		 * @memberof FormBuilder
		 * @type {?object}
		 */

		focusedField: focusedFieldStructure.value({}),

		/**
		 * @default []
		 * @instance
		 * @memberof FormBuilder
		 * @type {?array<object>}
		 */

		pages: Config.arrayOf(pageStructure).value([]),

		/**
		 * @instance
		 * @memberof FormBuilder
		 * @type {string}
		 */

		paginationMode: Config.string().required(),

		/**
		 * @instance
		 * @memberof FormBuilder
		 * @type {string}
		 */

		portletNamespace: Config.string().required(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FormRenderer
		 * @type {!string}
		 */

		spritemap: Config.string().required(),

		/**
		 * @instance
		 * @memberof FormBuilder
		 * @type {object}
		 */

		successPageSettings: Config.shapeOf({
			body: Config.object(),
			enabled: Config.bool(),
			title: Config.object()
		}).value({}),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FormBuilder
		 * @type {?string}
		 */

		view: Config.string()
	};

	return ActionableFields;
};

export default withActionableFields;

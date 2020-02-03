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

const getNestedFieldIndexes = (context, fieldName) => {
	let indexes = [];

	const visitor = new PagesVisitor(context);

	visitor.mapFields((field, fieldIndex, columnIndex, rowIndex, pageIndex) => {
		let nestedIndexes = [];

		if (typeof field === 'string') {
			field = context[0].nestedFields.find(
				nestedField =>
					nestedField.fieldName === field
			);
		}

		if (field && field.fieldName === fieldName) {
			indexes = [{columnIndex, pageIndex, rowIndex}];
		} else if (field && field.fieldName !== fieldName && field.nestedFields) {
			nestedIndexes = getNestedFieldIndexes([field], fieldName);

			if (nestedIndexes.length) {
				indexes = [{columnIndex, pageIndex, rowIndex}, ...nestedIndexes]
			}
		}

	});

	return indexes;
}

const getFieldContainer = (pages, fieldName) => {
	const nestedFieldIndexes = getNestedFieldIndexes(pages, fieldName);

	let selector = '';

	nestedFieldIndexes.forEach((fieldIndexes, i) => {
		const {columnIndex, pageIndex, rowIndex} = fieldIndexes;

		if (i === 0) {
			selector = [
				'.ddm-form-page > .row > .col-ddm',
				`[data-ddm-field-column="${columnIndex}"]`,
				`[data-ddm-field-page="${pageIndex}"]`,
				`[data-ddm-field-row="${rowIndex}"]`,
				'> .ddm-field-container'
			].join('');
		}
		else {
			selector = [
				selector,
				'.ddm-field-container > .ddm-drag > .form-group > .row > .col-ddm',
				`[data-ddm-field-column="${columnIndex}"]`,
				`[data-ddm-field-page="${pageIndex}"]`,
				`[data-ddm-field-row="${rowIndex}"]`,
				'> .ddm-field-container'
			].join('');
		}
	});

	if (selector) {
		return document.querySelector(selector);
	}
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
		}
		else {
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
		}
		else if (!this.expanded && !disabled) {
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

			this._eventHandler.add(
				this.delegate(
					'mouseleave',
					'.ddm-field-container',
					this._handleMouseLeaveField.bind(this)
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
			}
			else {
				selectedFieldActions.props.visible = false;
			}

			if (hoveredFieldActions.state.fieldName) {
				this.showActions(
					hoveredFieldActions,
					hoveredFieldActions.state.fieldName
				);
			}
		}

		showActions(actions, fieldName, field) {
			actions.props.label = this._getFieldType(fieldName, field);
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

		_getColumnField(nestedIndexes) {
			const {pages} = this.props;

			let context = pages;
			let field;

			nestedIndexes.forEach(indexes => {
				const {columnIndex, pageIndex, rowIndex} = indexes;

				field = FormSupport.getField(
					context,
					field ? 0 : pageIndex,
					rowIndex,
					columnIndex
				);

				context = [field];
			});

			return field;
		}

		_getFieldType(fieldName, field) {
			const {fieldTypes, pages} = this.props;

			if (!field) {
				const visitor = new PagesVisitor(pages);
				field = visitor.findField(
					fieldItem => fieldItem.fieldName === fieldName
				);
			}

			return (
				field &&
				fieldTypes.find(fieldType => {
					return fieldType.name === field.type;
				}).label
			);
		}

		_handleMouseEnterField(event) {
			event.stopPropagation();

			const {delegateTarget} = event;

			if (!delegateTarget.classList.contains('selected')) {
				const {hoveredFieldActions} = this.refs;
				const indexes = FormSupport.getNestedIndexes(
					dom.closest(delegateTarget, '.col-ddm')
				);
				const field = this._getColumnField(indexes);

				if (field) {
					this.showActions(hoveredFieldActions, field.fieldName, field);
				}
			}
		}

		_handleMouseLeaveField(event) {
			const delegateTarget = dom.closest(
				event.delegateTarget.parentElement,
				'.ddm-field-container'
			);

			if (
				delegateTarget &&
				!delegateTarget.classList.contains('selected')
			) {
				const {hoveredFieldActions} = this.refs;
				const indexes = FormSupport.getNestedIndexes(
					dom.closest(delegateTarget, '.col-ddm')
				);
				const field = this._getColumnField(indexes);

				this.showActions(hoveredFieldActions, field.fieldName, field);
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

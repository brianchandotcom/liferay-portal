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
import {FormSupport} from 'dynamic-data-mapping-form-renderer';
import dom from 'metal-dom';
import {EventHandler} from 'metal-events';
import Component from 'metal-jsx';
import {Align} from 'metal-position';
import {Config} from 'metal-state';

import {pageStructure} from '../../util/config.es';
import {CSS_DDM_FIELDSET} from '../../util/cssClasses.es';
import {getField, isFieldSet} from '../../util/fieldSupport.es';

const getFieldContainer = (fieldName) => {
	return document.querySelector(
		`.ddm-field-container[data-field-name="${fieldName}"]`
	);
};

class FieldActionsDropDown extends Component {
	align() {
		const {fieldName} = this.state;
		const fieldContainer = getFieldContainer(fieldName);

		if (this.element && fieldContainer) {
			Align.align(this.element, fieldContainer, Align.TopLeft);
		}
	}

	attached() {
		this._eventHandler.add(dom.on(window, 'resize', this.align.bind(this)));
	}

	close() {
		this.setState({expanded: false});
	}

	created() {
		this._eventHandler = new EventHandler();

		this.close();
	}

	disposed() {
		this._eventHandler.removeAllListeners();
	}

	getCssClasses() {
		const cssClasses = ['ddm-field-actions-container'];
		const {visible} = this.props;

		if (!visible) {
			cssClasses.push('hide');
		}

		if (this.isFieldSet()) {
			cssClasses.push(CSS_DDM_FIELDSET);
		}

		return cssClasses.join(' ');
	}

	getLabel() {
		const {fieldName} = this.state;
		const {fieldTypes, pages} = this.props;
		const field = FormSupport.findFieldByFieldName(pages, fieldName);

		return (
			field &&
			fieldTypes.find((fieldType) => {
				return fieldType.name === field.type;
			}).label
		);
	}

	isFieldSet() {
		const {fieldName} = this.state;
		const {pages} = this.props;

		const field = getField(pages, fieldName);

		return field && isFieldSet(field);
	}

	open() {
		this.setState({expanded: true});
	}

	render() {
		const {expanded} = this.state;
		const {disabled, items, spritemap} = this.props;

		return (
			<div
				class={this.getCssClasses()}
				onMouseDown={this._handleOnMouseDown.bind(this)}
				onMouseLeave={this._handleOnMouseLeave.bind(this)}
			>
				<span class="actions-label">{this.getLabel()}</span>

				<ClayActionsDropdown
					disabled={disabled}
					events={{
						expandedChanged: this._handleExpandedChanged.bind(this),
						itemClicked: this._handleItemClicked.bind(this),
					}}
					expanded={expanded}
					items={items}
					ref="dropdown"
					spritemap={spritemap}
				/>
			</div>
		);
	}

	rendered() {
		this.align();

		requestAnimationFrame(() => {
			this.align();

			this.refs.dropdown.refs.dropdown.refs.portal.emit('rendered');
		});
	}

	syncVisible(visible) {
		if (visible) {
			this.element.classList.remove('hide');
		}
		else {
			this.element.classList.add('hide');
		}
	}

	_handleOnMouseDown(event) {
		const {dropdown} = this.refs;

		event.stopImmediatePropagation();
		event.stopPropagation();

		if (!dropdown.element.contains(event.target)) {
			const {dispatch} = this.context;
			const {fieldName} = this.state;

			dispatch('fieldClicked', {fieldName});
		}
	}

	_handleOnMouseLeave(event) {
		const {fieldName} = this.state;

		if (!fieldName) {
			return;
		}

		this.emit('mouseLeave', {
			...event,
			container: getFieldContainer(fieldName),
			relatedTarget: event.relatedTarget,
		});
	}

	_handleExpandedChanged({newVal}) {
		this.setState({expanded: newVal});
	}

	_handleItemClicked({
		data: {
			item: {action},
		},
	}) {
		const {fieldName} = this.state;

		action(fieldName);

		this.close();
	}
}

FieldActionsDropDown.PROPS = {

	/**
	 * @default false
	 * @instance
	 * @memberof FieldActionsDropDown
	 * @type {!boolean}
	 */

	disabled: Config.bool().value(false),

	/**
	 * @default []
	 * @instance
	 * @memberof FieldActionsDropDown
	 * @type {?array<object>}
	 */

	fieldTypes: Config.array().value([]),

	/**
	 * @default []
	 * @instance
	 * @memberof FieldActionsDropDown
	 * @type {?array<object>}
	 */

	pages: Config.arrayOf(pageStructure).value([]),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FieldActionsDropDown
	 * @type {!string}
	 */

	spritemap: Config.string().required(),
};

FieldActionsDropDown.STATE = {

	/**
	 * @default false
	 * @instance
	 * @memberof FieldActionsDropDown
	 * @type {!boolean}
	 */

	expanded: Config.bool(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FieldActionsDropDown
	 * @type {!string}
	 */

	fieldName: Config.string(),
};

export default FieldActionsDropDown;

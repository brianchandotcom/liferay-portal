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
import Component from 'metal-jsx';
import {Config} from 'metal-state';

import {pageStructure} from '../../util/config.es';

const getFieldContainer = (pages, fieldName) => {
	return document.querySelector(
		`.ddm-field-container[data-field-name="${fieldName}"]`
	);
};

class FieldActionsDropDown extends Component {
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

			dispatch('fieldClicked', {fieldName});
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
			item: {action},
		},
	}) {
		const {fieldName} = this.state;

		action(fieldName);

		this.refs.dropdown.expanded = false;
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
	 * @default undefined
	 * @instance
	 * @memberof FieldActionsDropDown
	 * @type {!string}
	 */

	label: Config.string(),

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
	 * @default {}
	 * @instance
	 * @memberof FieldActionsDropDown
	 * @type {!Object}
	 */

	fieldName: Config.string(),
};

export {getFieldContainer};

export default FieldActionsDropDown;

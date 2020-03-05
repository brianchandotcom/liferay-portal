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

import '../FieldBase/FieldBase.es';

import './SectionRegister.soy';

import 'dynamic-data-mapping-form-renderer/js/components/PageRenderer/PageRenderer.es';
import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './Section.soy';

class Section extends Component {
	prepareStateForRender(state) {
		const {nestedFields} = this;

		const newState = {
			...state,
			nestedRows: state.rows.map(row => ({
				...row,
				columns: row.columns.map(column => ({
					...column,
					fields: column.fields.map(fieldName => {
						return nestedFields.find(
							nestedField => nestedField.fieldName === fieldName
						);
					}),
				})),
			})),
		};

		return newState;
	}

	_handleFieldBlurred(event) {
		this.emit('fieldBlurred', event);
	}

	_handleFieldEdited(event) {
		this.emit('fieldEdited', event);
	}

	_handleFieldFocused(event) {
		this.emit('fieldFocused', event);
	}

	_setRows(rows) {
		if (typeof rows === 'string') {
			try {
				return JSON.parse(rows);
			}
			catch (e) {
				return [];
			}
		}

		return rows;
	}
}

Section.STATE = {
	/**
	 * @default undefined
	 * @instance
	 * @memberof Section
	 * @type {?(string|undefined)}
	 */

	errorMessage: Config.string(),

	/**
	 * @default false
	 * @instance
	 * @memberof Section
	 * @type {?bool}
	 */

	evaluable: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Section
	 * @type {?(string|undefined)}
	 */

	fieldName: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Section
	 * @type {?(string|undefined)}
	 */

	label: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Section
	 * @type {?(string|undefined)}
	 */

	name: Config.string().required(),

	/**
	 * @default []
	 * @instance
	 * @memberof Section
	 * @type {?(Array)}
	 */

	nestedFields: Config.array().value([]),

	/**
	 * @default '000000'
	 * @instance
	 * @memberof Section
	 * @type {?(string|undefined)}
	 */

	predefinedValue: Config.string().value('000000'),

	/**
	 * @default false
	 * @instance
	 * @memberof Section
	 * @type {?bool}
	 */

	readOnly: Config.bool().value(false),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Section
	 * @type {?(bool|undefined)}
	 */

	repeatable: Config.bool().value(false),

	/**
	 * @default false
	 * @instance
	 * @memberof Section
	 * @type {?(bool|undefined)}
	 */

	required: Config.bool().value(false),

	/**
	 * @default []
	 * @instance
	 * @memberof Section
	 * @type {?(Array|string)}
	 */

	rows: Config.oneOfType([Config.array(), Config.string()])
		.setter('_setRows')
		.value([]),

	/**
	 * @default true
	 * @instance
	 * @memberof Section
	 * @type {?(bool|undefined)}
	 */

	showLabel: Config.bool().value(true),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Section
	 * @type {?(string|undefined)}
	 */

	spritemap: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Section
	 * @type {?(string|undefined)}
	 */

	tip: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof Section
	 * @type {?(string|undefined)}
	 */

	value: Config.string(),
};

Soy.register(Section, templates);

export {Section};
export default Section;

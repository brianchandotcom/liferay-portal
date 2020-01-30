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

import './GridRegister.soy';

import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './Grid.soy';

class Grid extends Component {
	_handleFieldBlurred(event) {
		this.emit('fieldBlurred', {
			fieldInstance: this,
			originalEvent: event
		});
	}

	_handleFieldChanged(event) {
		const {target} = event;
		const value = {
			...this.value,
			[target.name]: target.value
		};

		this.setState(
			{
				value
			},
			() => {
				this.emit('fieldEdited', {
					fieldInstance: this,
					originalEvent: event,
					value
				});
			}
		);
	}

	_handleFieldFocused(event) {
		this.emit('fieldFocused', {
			fieldInstance: this,
			originalEvent: event
		});
	}
}

Grid.STATE = {
	/**
	 * @default undefined
	 * @memberof Grid
	 * @type {?array<object>}
	 */

	columns: Config.arrayOf(
		Config.shapeOf({
			label: Config.string(),
			value: Config.string()
		})
	).value([
		{
			label: 'col1',
			value: 'fieldId'
		}
	]),

	/**
	 * @default false
	 * @memberof Grid
	 * @type {?bool}
	 */

	evaluable: Config.bool().value(false),

	fieldName: Config.string(),

	/**
	 * @default undefined
	 * @memberof Grid
	 * @type {?(string|undefined)}
	 */

	label: Config.string(),

	/**
	 * @default false
	 * @memberof Grid
	 * @type {?bool}
	 */

	readOnly: Config.bool().value(false),

	/**
	 * @default undefined
	 * @memberof Grid
	 * @type {?(bool|undefined)}
	 */

	repeatable: Config.bool(),

	/**
	 * @default false
	 * @memberof Grid
	 * @type {?(bool|undefined)}
	 */

	required: Config.bool().value(false),

	/**
	 * @default undefined
	 * @memberof Grid
	 * @type {?array<object>}
	 */

	rows: Config.arrayOf(
		Config.shapeOf({
			label: Config.string(),
			value: Config.string()
		})
	).value([
		{
			label: 'row',
			value: 'jehf'
		}
	]),

	/**
	 * @default true
	 * @memberof Grid
	 * @type {?(bool|undefined)}
	 */

	showLabel: Config.bool().value(true),

	/**
	 * @default undefined
	 * @memberof Grid
	 * @type {?(string|undefined)}
	 */

	spritemap: Config.string(),

	/**
	 * @default undefined
	 * @memberof Grid
	 * @type {?(string|undefined)}
	 */

	tip: Config.string(),

	/**
	 * @default grid
	 * @memberof Grid
	 * @type {?(string|undefined)}
	 */

	type: Config.string().value('grid'),

	/**
	 * @default {}
	 * @memberof Grid
	 * @type {?(string|undefined)}
	 */

	value: Config.object().value({})
};

Soy.register(Grid, templates);

export default Grid;

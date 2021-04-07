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

import '../components/Tooltip/Tooltip.es';

import 'clay-icon';
import {compose, getRepeatedIndex} from 'data-engine-js-components-web';
import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import withDispatch from '../util/withDispatch.es';
import templates from './FieldBase.soy';
import withLocale from './withLocale.es';
import withRepetitionControls from './withRepetitionControls.es';

class FieldBase extends Component {
	prepareStateForRender(state) {
		const repeatedIndex = getRepeatedIndex(this.name);

		return {
			...state,
			showRepeatableAddButton: this.repeatable,
			showRepeatableRemoveButton: this.repeatable && repeatedIndex > 0,
		};
	}
}

FieldBase.STATE = {

	/**
	 * @default input
	 * @memberof FieldBase
	 * @type {?html}
	 */

	contentRenderer: Config.any(),

	/**
	 * @default false
	 * @memberof FieldBase
	 * @type {?boolean}
	 */

	displayErrors: Config.bool().value(false),

	/**
	 * @default undefined
	 * @memberof FieldBase
	 * @type {?(string|undefined)}
	 */

	id: Config.string(),

	/**
	 * @default undefined
	 * @memberof FieldBase
	 * @type {?(string|undefined)}
	 */

	label: Config.string(),

	/**
	 * @default undefined
	 * @memberof FieldBase
	 * @type {?(string|undefined)}
	 */

	name: Config.string(),

	/**
	 * @default undefined
	 * @memberof FieldBase
	 * @type {?(bool|undefined)}
	 */

	repeatable: Config.bool(),

	/**
	 * @default undefined
	 * @memberof FieldBase
	 * @type {?(bool|undefined)}
	 */

	required: Config.bool(),

	/**
	 * @default true
	 * @memberof FieldBase
	 * @type {?(bool|undefined)}
	 */

	showLabel: Config.bool().value(true),

	/**
	 * @default undefined
	 * @memberof FieldBase
	 * @type {?(string|undefined)}
	 */

	spritemap: Config.string().required(),

	/**
	 * @default undefined
	 * @memberof FieldBase
	 * @type {?(string|undefined)}
	 */

	tip: Config.string(),

	/**
	 * @default undefined
	 * @memberof FieldBase
	 * @type {?(string|undefined)}
	 */

	tooltip: Config.string(),
};

const composed = compose(
	withDispatch,
	withRepetitionControls,
	withLocale
)(FieldBase);

Soy.register(composed, templates);

export default composed;

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

import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import 'clay-label';

import statusToLabelDisplayType from '../../utils/ExperimentsStatus.es';
import templates from './ExperimentsLabel.soy';

/**
 * This component is wrapper of ClayLabel
 * It takes a value that gets translated to a label style and rendered
 * ExperimentsLabel
 * @review
 */
class ExperimentsLabel extends Component {
	prepareStateForRender(state) {
		return {
			...state,
			_style: statusToLabelDisplayType(state.value)
		};
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
ExperimentsLabel.STATE = {
	/**
	 * This prop is generated in the `prepareStateForRender` from the `value` prop
	 * @default undefined
	 * @review
	 * @type {string}
	 */
	_style: Config.string().internal(),

	/**
	 * @default undefined
	 * @review
	 * @type {string}
	 */
	label: Config.string().required(),

	/**
	 * An Experiment status value that is only used to be translated to `_style`
	 * @default undefined
	 * @review
	 * @type {number}
	 */
	value: Config.number().required()
};

Soy.register(ExperimentsLabel, templates);

export default ExperimentsLabel;

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
import templates from './ExperimentsLabel.soy';
import 'clay-label';

/**
 * This code lives also in `segments/segments-experiment-web` module
 * TODO import it from there
 */

const STATUS_COMPLETED = 2;
const STATUS_DRAFT = 0;
const STATUS_FINISHED_NO_WINNER = 4;
const STATUS_FINISHED_WINNER = 3;
const STATUS_PAUSED = 5;
const STATUS_RUNNING = 1;
const STATUS_SCHEDULED = 7;
const STATUS_TERMINATED = 6;

export const statusToLabelDisplayType = status => STATUS_TO_TYPE[status];

const STATUS_TO_TYPE = {
	[STATUS_COMPLETED]: 'success',
	[STATUS_DRAFT]: 'secondary',
	[STATUS_FINISHED_NO_WINNER]: 'secondary',
	[STATUS_FINISHED_WINNER]: 'success',
	[STATUS_PAUSED]: 'warning',
	[STATUS_RUNNING]: 'primary',
	[STATUS_SCHEDULED]: 'warning',
	[STATUS_TERMINATED]: 'danger'
};

/**
 * This component is wrapper of ClayLabel
 * It takes a value that gets translated to a label style and rendered
 */
class ExperimentsLabel extends Component {
	prepareStateForRender(state) {
		const {label, value} = state;

		const style = statusToLabelDisplayType(value);

		return {
			label,
			style
		};
	}
}

ExperimentsLabel.STATE = {
	/**
	 * @default undefined
	 * @review
	 * @type {string}
	 */
	label: Config.string().required(),

	/**
	 * This prop is generated in the `prepareStateForRender` from the `value` prop
	 * @default undefined
	 * @review
	 * @type {string}
	 */
	style: Config.string().internal(),

	/**
	 * An Experiment status value that is only used to be translated to `style`
	 * @default undefined
	 * @review
	 * @type {number}
	 */
	value: Config.number().required()
};

Soy.register(ExperimentsLabel, templates);

export {ExperimentsLabel};
export default ExperimentsLabel;

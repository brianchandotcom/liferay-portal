/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React from 'react';

const NavigationButtons = ({moveController, position}) => {
	return (
		<div
			className="move-controller"
			style={{
				bottom: position.bottom,
				left: position.left,
				right: position.right,
				top: position.top,
			}}
			type="button"
		>
			<a
				className="box top"
				onClick={() => moveController('up')}
				type="button"
			>
				<ClayIcon className="icon" symbol="angle-left" />
			</a>

			<a
				className="box right"
				onClick={() => moveController('right')}
				type="button"
			>
				<ClayIcon className="icon" symbol="angle-up" />
			</a>

			<a
				className="box left"
				onClick={() => moveController('left')}
				type="button"
			>
				<ClayIcon className="icon" symbol="angle-down" />
			</a>

			<a
				className="bottom box"
				onClick={() => moveController('down')}
				type="button"
			>
				<ClayIcon className="icon" symbol="angle-down" />
			</a>
		</div>
	);
};

export default NavigationButtons;

NavigationButtons.propTypes = {
	moveController: PropTypes.func,
};

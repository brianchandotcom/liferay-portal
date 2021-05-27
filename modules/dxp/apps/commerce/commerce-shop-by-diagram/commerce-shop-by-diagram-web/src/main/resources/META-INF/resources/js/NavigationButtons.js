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

const NavigationButtons = ({
	moveDown,
	moveLeft,
	moveRight,
	moveUp,
	spritemap,
}) => {
	const navigationButtonStyle = {
		bottom: '15px',
		left: '',
		right: '50px',
		top: '',
	};

	return (
		<div id="move-controller" style={navigationButtonStyle}>
			<div className="box top" onClick={moveUp}>
				<ClayIcon
					className="icon"
					spritemap={spritemap}
					symbol="angle-left"
				/>
			</div>
			<div className="box right" onClick={moveRight}>
				<ClayIcon
					className="icon"
					spritemap={spritemap}
					symbol="angle-up"
				/>
			</div>
			<div className="box left" onClick={moveLeft}>
				<ClayIcon
					className="icon"
					spritemap={spritemap}
					symbol="angle-down"
				/>
			</div>
			<div className="bottom box" onClick={moveDown}>
				<ClayIcon
					className="icon"
					spritemap={spritemap}
					symbol="angle-down"
				/>
			</div>
		</div>
	);
};

export default NavigationButtons;

NavigationButtons.propTypes = {
	moveBottom: PropTypes.func,
	moveLeft: PropTypes.func,
	moveRight: PropTypes.func,
	moveUp: PropTypes.func,
	position: PropTypes.shape({
		bottom: PropTypes.string,
		left: PropTypes.string,
		right: PropTypes.string,
		top: PropTypes.string,
	}),
	spritemap: PropTypes.string,
};

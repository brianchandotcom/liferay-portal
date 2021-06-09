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

const ZoomController = ({position, spritemap, zoomIn, zoomOut}) => {
	const zoomButtonStyle = {
		bottom: position.bottom,
		left: position.left,
		right: position.right,
		top: position.top,
	};

	return (
		<div id="zoom-controller" style={zoomButtonStyle}>
			<div className="box plus" data-testid="zoomIn" onClick={zoomIn}>
				<ClayIcon
					className="icon"
					spritemap={spritemap}
					symbol="plus"
				/>
			</div>

			<div className="box hr" onClick={zoomOut}>
				<ClayIcon
					className="icon"
					data-testid="zoomOut"
					spritemap={spritemap}
					symbol="hr"
				/>
			</div>
		</div>
	);
};

export default ZoomController;

ZoomController.propTypes = {
	position: PropTypes.shape({
		bottom: PropTypes.string,
		left: PropTypes.string,
		right: PropTypes.string,
		top: PropTypes.string,
	}),
	spritemap: PropTypes.string,
	zoomIn: PropTypes.func,
	zoomOut: PropTypes.func,
};

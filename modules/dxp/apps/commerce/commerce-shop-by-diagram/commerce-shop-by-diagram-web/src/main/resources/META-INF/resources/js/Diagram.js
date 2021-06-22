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

import {ClayIconSpriteContext} from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import DiagramFooter from './DiagramFooter';
import DiagramHeader from './DiagramHeader';
import ImagePins from './ImagePins';

import '../css/diagram.scss';

const Diagram = ({
	enablePanZoom,
	enableResetZoom,
	imageSettings,
	imageURL,
	namespace,
	navigationController,
	spritemap,
	zoomController,
}) => {
	const [resetZoom, setResetZoom] = useState(false);
	const [zoomInHandler, setZoomInHandler] = useState(false);
	const [zoomOutHandler, setZoomOutHandler] = useState(false);
	const [changedScale, setChangedScale] = useState(false);
	const [scale, setScale] = useState(1);
	const [selectedOption, setSelectedOption] = useState(1);

	return (
		<div className="diagram mx-auto">
			<ClayIconSpriteContext.Provider value={spritemap}>
				<DiagramHeader />

				<ImagePins
					changedScale={changedScale}
					enablePanZoom={enablePanZoom}
					enableResetZoom={enableResetZoom}
					imageSettings={imageSettings}
					imageURL={imageURL}
					namespace={namespace}
					navigationController={navigationController}
					resetZoom={resetZoom}
					scale={scale}
					selectedOption={selectedOption}
					setChangedScale={setChangedScale}
					setResetZoom={setResetZoom}
					setScale={setScale}
					setSelectedOption={setSelectedOption}
					setZoomInHandler={setZoomInHandler}
					setZoomOutHandler={setZoomOutHandler}
					zoomController={zoomController}
					zoomInHandler={zoomInHandler}
					zoomOutHandler={zoomOutHandler}
				/>

				<DiagramFooter
					changedScale={changedScale}
					enableResetZoom={enableResetZoom}
					selectedOption={selectedOption}
					setChangedScale={setChangedScale}
					setResetZoom={setResetZoom}
					setSelectedOption={setSelectedOption}
					setZoomInHandler={setZoomInHandler}
					setZoomOutHandler={setZoomOutHandler}
				/>
			</ClayIconSpriteContext.Provider>
		</div>
	);
};

Diagram.defaultProps = {
	enablePanZoom: true,
	enableResetZoom: true,
	imageSettings: {
		height: '300px',
		width: '100%',
	},
	navigationController: {
		dragStep: 10,
		enable: true,
		enableDrag: false,
		position: {
			bottom: '15px',
			left: '',
			right: '50px',
			top: '',
		},
	},
	pins: [],
	zoomController: {
		enable: true,
		position: {
			bottom: '0px',
			left: '',
			right: '200px',
			top: '',
		},
	},
};

Diagram.propTypes = {
	enablePanZoom: PropTypes.bool,
	enableResetZoom: PropTypes.bool,
	imageSettings: PropTypes.shape({
		height: PropTypes.string,
		width: PropTypes.string,
	}),
	imageURL: PropTypes.string.isRequired,
	namespace: PropTypes.string.isRequired,
	navigationController: PropTypes.shape({
		dragStep: PropTypes.number,
		enable: PropTypes.bool,
		enableDrag: PropTypes.bool,
		position: PropTypes.shape({
			bottom: PropTypes.string,
			left: PropTypes.string,
			right: PropTypes.string,
			top: PropTypes.string,
		}),
	}),
	zoomController: PropTypes.shape({
		enable: PropTypes.bool,
		position: PropTypes.shape({
			bottom: PropTypes.string,
			left: PropTypes.string,
			right: PropTypes.string,
			top: PropTypes.string,
		}),
	}),
};

export default Diagram;

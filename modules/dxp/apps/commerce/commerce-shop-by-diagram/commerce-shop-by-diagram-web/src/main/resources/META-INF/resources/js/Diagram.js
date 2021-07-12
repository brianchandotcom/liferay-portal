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
import React, {useEffect, useState} from 'react';

import DiagramFooter from './DiagramFooter';
import DiagramHeader from './DiagramHeader';
import ImagePins from './ImagePins';

import '../css/diagram.scss';

const Diagram = ({
	enablePanZoom,
	enableResetZoom,
	imageSettings,
	imageURL,
	isAdmin,
	namespace,
	navigationController,
	newPinSettings,
	pins,
	spritemap,
	zoomController,
}) => {
	const [addPinHandler, setAddPinHandler] = useState(false);
	const [removePinHandler, setRemovePinHandler] = useState({
		handler: false,
		pin: null,
	});
	const [diagramSizes, setDiagramSizes] = useState({k: 1, x: 0, y: 0});
	const [resetZoom, setResetZoom] = useState(false);
	const [zoomInHandler, setZoomInHandler] = useState(false);
	const [zoomOutHandler, setZoomOutHandler] = useState(false);
	const [changedScale, setChangedScale] = useState(false);
	const [scale, setScale] = useState(1);
	const [selectedOption, setSelectedOption] = useState(1);
	const [cPins, setCpins] = useState(pins);
	const [showTooltip, setShowTooltip] = useState({
		details: {
			cx: 0,
			cy: 0,
			id: null,
			label: '',
			linked_to_sku: 'sku',
			quantity: null,
			sku: '',
		},
		tooltip: null,
	});
	const [addNewPinState, setAddNewPinState] = useState({
		fill: newPinSettings.colorPicker.selectedColor,
		radius: newPinSettings.defaultRadius,
	});

	useEffect(() => {
		if (!showTooltip.tooltip) {
			const newCPinState = cPins.map((element) => {
				if (element.id === showTooltip.details.id) {
					return {
						cx: cPins[element.id].cx,
						cy: cPins[element.id].cy,
						draggable: cPins[element.id].draggable,
						fill: cPins[element.id].fill,
						id: showTooltip.details.id,
						label: showTooltip.details.label,
						linked_to_sku: showTooltip.details.linked_to_sku,
						quantity: showTooltip.details.quantity,
						r: cPins[element.id].r,
						sku: showTooltip.details.sku,
					};
				}
				else {
					return element;
				}
			});
			setCpins(newCPinState);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [showTooltip, setShowTooltip]);

	return (
		<div className="diagram mx-auto">
			<ClayIconSpriteContext.Provider value={spritemap}>
				<DiagramHeader
					addNewPinState={addNewPinState}
					isAdmin={isAdmin}
					namespace={namespace}
					newPinSettings={newPinSettings}
					setAddNewPinState={setAddNewPinState}
					setAddPinHandler={setAddPinHandler}
					setSelectedOption={setSelectedOption}
				/>

				<ImagePins
					addNewPinState={addNewPinState}
					addPinHandler={addPinHandler}
					cPins={cPins}
					changedScale={changedScale}
					diagramSizes={diagramSizes}
					enablePanZoom={enablePanZoom}
					enableResetZoom={enableResetZoom}
					imageSettings={imageSettings}
					imageURL={imageURL}
					isAdmin={isAdmin}
					namespace={namespace}
					navigationController={navigationController}
					removePinHandler={removePinHandler}
					resetZoom={resetZoom}
					scale={scale}
					selectedOption={selectedOption}
					setAddPinHandler={setAddPinHandler}
					setChangedScale={setChangedScale}
					setCpins={setCpins}
					setDiagramSizes={setDiagramSizes}
					setRemovePinHandler={setRemovePinHandler}
					setResetZoom={setResetZoom}
					setScale={setScale}
					setSelectedOption={setSelectedOption}
					setShowTooltip={setShowTooltip}
					setZoomInHandler={setZoomInHandler}
					setZoomOutHandler={setZoomOutHandler}
					showTooltip={showTooltip}
					zoomController={zoomController}
					zoomInHandler={zoomInHandler}
					zoomOutHandler={zoomOutHandler}
				/>

				<DiagramFooter
					changedScale={changedScale}
					enableResetZoom={enableResetZoom}
					isAdmin={isAdmin}
					selectedOption={selectedOption}
					setAddPinHandler={setAddPinHandler}
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
	newPinSettings: {
		colorPicker: {
			defaultColors: [
				'AC68D7',
				'96D470',
				'F2EE8F',
				'F4C4A9',
				'F1A3BB',
				'67DC19',
				'959FEF',
				'A6C198',
				'FED998',
				'#38F95',
				'FD9945',
				'1A588B',
			],
			selectedColor: '0B5FFF',
			useNative: true,
		},
		defaultRadius: 15,
	},
	pins: [],
	spritemap: './assets/clay/icons.svg',
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
	cPins: PropTypes.arrayOf(
		PropTypes.shape({
			cx: PropTypes.double,
			cy: PropTypes.double,
			draggable: PropTypes.bool,
			fill: PropTypes.string,
			id: PropTypes.number,
			label: PropTypes.string,
			linked_to_sku: PropTypes.oneOf(['sku', 'diagram']),
			quantity: PropTypes.number,
			r: PropTypes.number,
			sku: PropTypes.string,
		})
	),
	enablePanZoom: PropTypes.bool,
	enableResetZoom: PropTypes.bool,
	imageSettings: PropTypes.shape({
		height: PropTypes.string,
		width: PropTypes.string,
	}),
	imageURL: PropTypes.string.isRequired,
	isAdmin: PropTypes.bool.isRequired,
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
	newPinSettings: PropTypes.shape({
		colorPicker: PropTypes.shape({
			defaultColors: PropTypes.array,
			selectedColor: PropTypes.string,
			useNative: PropTypes.bool,
		}),
		defaultRadius: PropTypes.number,
	}),
	setPins: PropTypes.func,
	showTooltip: PropTypes.shape({
		details: PropTypes.shape({
			cx: PropTypes.double,
			cy: PropTypes.double,
			id: PropTypes.number,
			label: PropTypes.string,
			linked_to_sku: PropTypes.oneOf(['sku', 'diagram']),
			quantity: PropTypes.number,
			sku: PropTypes.string,
		}),
		tooltip: PropTypes.bool,
	}),
	spritemap: PropTypes.string,
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

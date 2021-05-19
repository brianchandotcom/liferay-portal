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

import ClayButton from '@clayui/button';
import ClayColorPicker from '@clayui/color-picker';
import ClayDropDown from '@clayui/drop-down';
import ClaySlider from '@clayui/slider';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const DiagramHeader = ({
	addNewPinState,
	newPinSettings,
	radiusChoice,
	setAddNewPinState,
	setAddPinHandler,
}) => {
	const [active, setActive] = useState(false);
	const [customColors, setCustoms] = useState(
		newPinSettings.colorPicker.defaultColors
	);
	const [color, setColor] = useState(
		newPinSettings.colorPicker.selectedColor
	);

	// useEffect(() => {

	//     setAddNewPinState({
	//         color: addNewPinState.color,
	//         radius: addNewPinState.radius
	//     })

	// }, [color, addNewPinState])

	// useEffect(() => {
	//     setCustoms(newPinSettings.colorPicker.defaultColors)
	// }, [newPinSettings.colorPicker.defaultColors])

	return (
		<div className="d-flex diagram diagram-header justify-content-between">
			<div className="d-flex text-align-center">
				<label className="align-middle my-auto">
					{Liferay.Language.get('diagram\'s-pin:')}
				</label>

				<ClayDropDown
					active={active}
					className="my-auto"
					onActiveChange={setActive}
					trigger={
						<ClayButton
							alt="Click-to-select-custom-diameter"
							className="ml-3 select-diameter"
							displayType="secondary"
						>
							{Liferay.Language.get('default-diameter')}

						</ClayButton>
					}
				>
					<ClayDropDown.ItemList className="diagram-header-picker">
						<ClayDropDown.Group header="SELECT COLOR">
							<ClayColorPicker
								colors={customColors}
								label="Custom Colors"
								max={100}
								name="diagram-color-picker"
								onColorsChange={setCustoms}
								onValueChange={(item) => {
									setColor(item.replace('#', ''));
									setAddNewPinState({
										color: item.replace('#', ''),
										radius: addNewPinState.radius,
									});
								}}
								showHex={true}
								step={1}
								useNative={newPinSettings.colorPicker.useNative}
								value={addNewPinState.fill}
							/>
						</ClayDropDown.Group>
					</ClayDropDown.ItemList>

					<ClayDropDown.Divider />

					<ClayDropDown.ItemList>
						<ClayDropDown.Group header="STANDARD">
							{radiusChoice.map((item, i) => (
								<ClayDropDown.Item
									key={i}
									onClick={() => {
										setAddNewPinState({
											fill: addNewPinState.fill,
											radius: item.value,
										});
									}}
								>
									{item.label}
								</ClayDropDown.Item>
							))}
						</ClayDropDown.Group>
					</ClayDropDown.ItemList>

					<ClayDropDown.Divider />
					<ClayDropDown.Caption>
						<div className="form-group">
							<label htmlFor="slider">CUSTOM</label>
							<ClaySlider
								id="slider"
								onValueChange={(item) =>
									setAddNewPinState({
										color,
										radius: item,
									})
								}
								value={addNewPinState.radius}
							/>
						</div>
						<ClayButton
							block="true"
							displayType="primary"
							onClick={() => setAddPinHandler(true)}
						>
							{Liferay.Language.get('add-pin')}
						</ClayButton>
					</ClayDropDown.Caption>
				</ClayDropDown>
			</div>

			<ClayButton
				className="auto-mapping my-auto pull-right"
				displayType="secondary"
			>
				{Liferay.Language.get('auto-mapping')}
			</ClayButton>
		</div>
	);
};

DiagramHeader.defaultProps = {
	radiusChoice: [
		{
			label: 'Small',
			value: 10,
		},
		{
			label: 'Medium',
			value: 20,
		},
		{
			label: 'Large',
			value: 30,
		},
	],
};

export default DiagramHeader;

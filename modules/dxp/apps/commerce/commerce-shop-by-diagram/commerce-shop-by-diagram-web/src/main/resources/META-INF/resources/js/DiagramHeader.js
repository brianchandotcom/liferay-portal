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
import ClayForm from '@clayui/form';
import ClaySlider from '@clayui/slider';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const DiagramHeader = ({
	addNewPinState,
	isAdmin,
	namespace,
	newPinSettings,
	setAddNewPinState,
	setAddPinHandler,
}) => {
	const RADIUS_CHOICE = [
		{
			label: Liferay.Language.get('small'),
			value: 10,
		},
		{
			label: Liferay.Language.get('medium'),
			value: 20,
		},
		{
			label: Liferay.Language.get('large'),
			value: 30,
		},
	];
	const [active, setActive] = useState(false);
	const [customColors, setCustomColors] = useState(
		newPinSettings.colorPicker.defaultColors
	);

	return (
		<div className="d-flex diagram diagram-header justify-content-between">
			{isAdmin && (
				<div className="d-flex text-align-center">
					<label className="align-middle my-auto">
						{Liferay.Language.get('diagram-pin')}
					</label>

					<ClayDropDown
						active={active}
						className="my-auto"
						onActiveChange={setActive}
						trigger={
							<ClayButton
								aria-label={Liferay.Language.get(
									'click-to-select-custom-diameter'
								)}
								className="ml-3 select-diameter"
								displayType="secondary"
							>
								{Liferay.Language.get('default-diameter')}
							</ClayButton>
						}
					>
						<ClayDropDown.ItemList className="diagram-header-picker">
							<ClayDropDown.Group
								header={Liferay.Language.get('select-color')}
							>
								<ClayColorPicker
									colors={customColors}
									label={Liferay.Language.get('custom-color')}
									max={100}
									name={`${namespace}diagram-color-picker`}
									onColorsChange={setCustomColors}
									onValueChange={(item) => {
										setAddNewPinState({
											fill: item.replace('#', ''),
											radius: addNewPinState.radius,
										});
									}}
									showHex={true}
									step={1}
									title={Liferay.Language.get('custom-color')}
									useNative={
										newPinSettings.colorPicker.useNative
									}
									value={addNewPinState.fill}
								/>
							</ClayDropDown.Group>
						</ClayDropDown.ItemList>

						<ClayDropDown.Divider />

						<ClayDropDown.ItemList>
							<ClayDropDown.Group
								header={Liferay.Language.get('select-radius')}
							>
								{RADIUS_CHOICE.map((item) => (
									<ClayDropDown.Item
										key={item.value}
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
							<ClayForm>
								<ClayForm.Group small>
									<label htmlFor="slider">
										{Liferay.Language.get('custom-radius')}
									</label>

									<ClaySlider
										id={`${namespace}custom-radius`}
										onValueChange={(item) =>
											setAddNewPinState({
												fill: addNewPinState.fill,
												radius: item,
											})
										}
										value={addNewPinState.radius}
									/>

									<ClayButton
										block
										onClick={() => setAddPinHandler(true)}
									>
										{Liferay.Language.get('add-pin')}
									</ClayButton>
								</ClayForm.Group>
							</ClayForm>
						</ClayDropDown.Caption>
					</ClayDropDown>
				</div>
			)}
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

DiagramHeader.propTypes = {
	addNewPinState: PropTypes.shape({
		fill: PropTypes.string,
		radius: PropTypes.number,
	}),
	newPinSettings: PropTypes.shape({
		colorPicker: PropTypes.shape({
			defaultColors: PropTypes.array,
			selectedColor: PropTypes.string,
			useNative: PropTypes.bool,
		}),
		defautlRadius: PropTypes.number,
	}),
	radiusChoice: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.string,
			value: PropTypes.number,
		})
	),
	setAddNewPinState: PropTypes.func,
};

export default DiagramHeader;

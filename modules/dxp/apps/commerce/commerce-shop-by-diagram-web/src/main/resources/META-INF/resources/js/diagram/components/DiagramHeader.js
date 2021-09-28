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
import ClayDropDown from '@clayui/drop-down';
import ClayManagementToolbar from '@clayui/management-toolbar';
import ClaySlider from '@clayui/slider';
import classNames from 'classnames';
import {debounce} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {
	PINS_RADIUS_MAX,
	PINS_RADIUS_MIN,
	PINS_RADIUS_OPTIONS,
	PINS_RADIUS_STEP,
} from '../utilities/constants';

function getLabel(pinsRadius) {
	return Object.values(PINS_RADIUS_OPTIONS).reduce(
		(label, definition) =>
			definition.value === pinsRadius ? definition.label : label,
		Liferay.Language.get('custom')
	);
}

function updateLabel(pinsRadius, updateButtonLabel) {
	const label = getLabel(pinsRadius);

	updateButtonLabel(label);
}

const debouncedUpdateLabel = debounce(updateLabel, 300);

function DiagramHeader({pinsRadius, updatePinsRadius}) {
	const [active, setActive] = useState(false);
	const [buttonLabel, updateButtonLabel] = useState(getLabel(pinsRadius));

	const smallActive = pinsRadius === PINS_RADIUS_OPTIONS.small.value;
	const mediumActive = pinsRadius === PINS_RADIUS_OPTIONS.medium.value;
	const largeActive = pinsRadius === PINS_RADIUS_OPTIONS.large.value;

	useEffect(() => {
		debouncedUpdateLabel(pinsRadius, updateButtonLabel);
	}, [pinsRadius]);

	return (
		<ClayManagementToolbar className="py-2">
			<ClayManagementToolbar.ItemList>
				<ClayManagementToolbar.Item>
					<label className="mr-1">
						{Liferay.Language.get('pin-size')}
					</label>
					<ClayDropDown
						active={active}
						className="my-auto"
						onActiveChange={setActive}
						trigger={
							<ClayButton displayType="secondary">
								{buttonLabel}
							</ClayButton>
						}
					>
						<ClayDropDown.ItemList>
							<ClayDropDown.Group
								header={Liferay.Language.get('standard')}
							>
								<ClayDropDown.Item
									active={smallActive}
									onClick={() =>
										updatePinsRadius(
											PINS_RADIUS_OPTIONS.small.value
										)
									}
								>
									{PINS_RADIUS_OPTIONS.small.label}
								</ClayDropDown.Item>
								<ClayDropDown.Item
									active={mediumActive}
									onClick={() =>
										updatePinsRadius(
											PINS_RADIUS_OPTIONS.medium.value
										)
									}
								>
									{PINS_RADIUS_OPTIONS.medium.label}
								</ClayDropDown.Item>
								<ClayDropDown.Item
									active={largeActive}
									onClick={() =>
										updatePinsRadius(
											PINS_RADIUS_OPTIONS.large.value
										)
									}
								>
									{PINS_RADIUS_OPTIONS.large.label}
								</ClayDropDown.Item>

								<ClayDropDown.Item>
									<div className="form-group">
										<label htmlFor="custom-radius-slider">
											{Liferay.Language.get('custom')}
										</label>

										<div
											className={classNames(
												'slider-wrapper',
												{
													disabled:
														smallActive ||
														mediumActive ||
														largeActive,
												}
											)}
										>
											<ClaySlider
												id="custom-radius-slider"
												max={PINS_RADIUS_MAX}
												min={PINS_RADIUS_MIN}
												onValueChange={updatePinsRadius}
												showTooltip={false}
												step={PINS_RADIUS_STEP}
												value={pinsRadius}
											/>
										</div>
									</div>
								</ClayDropDown.Item>
							</ClayDropDown.Group>
						</ClayDropDown.ItemList>
					</ClayDropDown>
				</ClayManagementToolbar.Item>
			</ClayManagementToolbar.ItemList>
		</ClayManagementToolbar>
	);
}

export default DiagramHeader;

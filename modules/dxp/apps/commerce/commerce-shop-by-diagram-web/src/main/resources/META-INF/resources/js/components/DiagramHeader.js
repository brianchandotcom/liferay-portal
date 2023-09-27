/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClaySlider from '@clayui/slider';
import classNames from 'classnames';
import {ManagementToolbar} from 'frontend-js-components-web';
import {debounce} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {PINS_RADIUS} from '../utilities/constants';

function getLabel(pinsRadius) {
	return Object.values(PINS_RADIUS.OPTIONS).reduce(
		(label, definition) =>
			definition.value === pinsRadius ? definition.label : label,
		Liferay.Language.get('custom')
	);
}

function updateLabel(pinsRadius, updateButtonLabel) {
	const label = getLabel(pinsRadius);

	updateButtonLabel(label);
}

const debouncedUpdateLabel = debounce(updateLabel, 500);

function DiagramHeader({
	dropdownActive,
	pinsRadius,
	setDropdownActive,
	updatePinsRadius,
}) {
	const [buttonLabel, setButtonLabel] = useState(getLabel(pinsRadius));

	const smallActive = pinsRadius === PINS_RADIUS.OPTIONS.small.value;
	const mediumActive = pinsRadius === PINS_RADIUS.OPTIONS.medium.value;
	const largeActive = pinsRadius === PINS_RADIUS.OPTIONS.large.value;

	useEffect(() => {
		debouncedUpdateLabel(pinsRadius, setButtonLabel);
	}, [pinsRadius]);

	return (
		<ManagementToolbar.Container className="py-2">
			<ManagementToolbar.ItemList>
				<ManagementToolbar.Item>
					<label className="mr-3">
						{Liferay.Language.get('pin-size')}
					</label>

					<ClayDropDown
						active={dropdownActive}
						className="my-auto"
						onActiveChange={setDropdownActive}
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
											PINS_RADIUS.OPTIONS.small.value
										)
									}
								>
									{PINS_RADIUS.OPTIONS.small.label}
								</ClayDropDown.Item>

								<ClayDropDown.Item
									active={mediumActive}
									onClick={() =>
										updatePinsRadius(
											PINS_RADIUS.OPTIONS.medium.value
										)
									}
								>
									{PINS_RADIUS.OPTIONS.medium.label}
								</ClayDropDown.Item>

								<ClayDropDown.Item
									active={largeActive}
									onClick={() =>
										updatePinsRadius(
											PINS_RADIUS.OPTIONS.large.value
										)
									}
								>
									{PINS_RADIUS.OPTIONS.large.label}
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
												max={PINS_RADIUS.MAX}
												min={PINS_RADIUS.MIN}
												onChange={updatePinsRadius}
												showTooltip={false}
												step={PINS_RADIUS.STEP}
												value={pinsRadius}
											/>
										</div>
									</div>
								</ClayDropDown.Item>
							</ClayDropDown.Group>
						</ClayDropDown.ItemList>
					</ClayDropDown>
				</ManagementToolbar.Item>
			</ManagementToolbar.ItemList>
		</ManagementToolbar.Container>
	);
}

export default DiagramHeader;

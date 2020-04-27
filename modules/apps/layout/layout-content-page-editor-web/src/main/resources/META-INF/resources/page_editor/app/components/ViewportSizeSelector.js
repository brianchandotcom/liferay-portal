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

import {ClayButtonWithIcon, default as ClayButton} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import * as Actions from '../actions/index';
import {config} from '../config/index';
import {useDispatch} from '../store/index';

const handleClick = ({dispatch, setActive, sizeId}) => {
	dispatch(Actions.switchViewportSize({size: sizeId}));
	setActive(false);
};

const SelectorButtonList = ({
	availableViewportSizes,
	dispatch,
	dropdown,
	selectedSize,
	setActive,
}) => {
	return Object.values(availableViewportSizes).map((viewportSize) => {
		const {icon, label, sizeId} = viewportSize;
		const active = selectedSize === sizeId;

		return dropdown ? (
			<ClayDropDown.Item
				key={label}
				onClick={() => handleClick({dispatch, setActive, sizeId})}
				symbolLeft={icon}
			>
				{viewportSize.label}
			</ClayDropDown.Item>
		) : (
			<ClayButtonWithIcon
				aria-label={label}
				aria-pressed={active}
				className={classNames({
					active,
					'btn-monospaced': true,
				})}
				displayType="secondary"
				key={sizeId}
				onClick={() => handleClick({dispatch, setActive, sizeId})}
				small
				symbol={icon}
				title={label}
			/>
		);
	});
};

export default function ViewportSizeSelector({selectedSize}) {
	const {availableViewportSizes} = config;
	const dispatch = useDispatch();
	const [active, setActive] = useState(false);

	return (
		<>
			<ClayButton.Group className="d-lg-block d-none">
				<SelectorButtonList
					availableViewportSizes={availableViewportSizes}
					dispatch={dispatch}
					selectedSize={selectedSize}
					setActive={setActive}
				/>
			</ClayButton.Group>
			<ClayDropDown
				active={active}
				className="d-lg-none"
				hasLeftSymbols
				hasRightSymbols
				onActiveChange={setActive}
				trigger={
					<ClayButton
						className="btn-monospaced"
						displayType="secondary"
						small
					>
						<ClayIcon
							symbol={availableViewportSizes[selectedSize].icon}
						/>
						<span className="sr-only">
							{availableViewportSizes[selectedSize].label}
						</span>
					</ClayButton>
				}
			>
				<ClayDropDown.ItemList>
					<SelectorButtonList
						availableViewportSizes={availableViewportSizes}
						dispatch={dispatch}
						dropdown
						selectedSize={selectedSize}
						setActive={setActive}
					/>
				</ClayDropDown.ItemList>
			</ClayDropDown>
		</>
	);
}

ViewportSizeSelector.propTypes = {
	selectedSize: PropTypes.string,
};

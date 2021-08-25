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

import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';
import {useStore, useZoomPanHelper} from 'react-flow-renderer';

const MoreSteps = ({focusNode, steps}) => {
	const [active, setActive] = useState(false);

	const onClickFocus = (step) => () => {
		setActive(false);
		focusNode(step)();
	};

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={7}
			onActiveChange={setActive}
			trigger={
				<a className="current-step-link more-link">
					{Liferay.Language.get('more').toLowerCase()}...
				</a>
			}
		>
			<ClayDropDown.ItemList>
				{steps.map((step) => (
					<ClayDropDown.Item key={step} onClick={onClickFocus(step)}>
						{step}
					</ClayDropDown.Item>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
};

export default function CurrentStep({steps = []}) {
	const store = useStore();
	const {setCenter} = useZoomPanHelper();

	if (!steps?.length) {
		return null;
	}

	const focusNode = (step) => () => {
		const {nodes} = store.getState();
		const node = nodes.find(({id}) => id === step);

		if (node) {
			const x = node.__rf.position.x + node.__rf.width / 2;
			const y = node.__rf.position.y + node.__rf.height / 2;
			const zoom = 1.85;

			setCenter(x, y, zoom);
		}
	};

	let firstSteps = steps;
	let moreSteps = [];

	if (steps.length > 4) {
		firstSteps = steps.slice(0, 3);
		moreSteps = steps.slice(3);
	}

	return (
		<div className="current-step">
			<ClayIcon className="current-icon ml-2" symbol="live" />
			<span className="current-step-text">
				{Liferay.Language.get('current-step')}:
			</span>
			{firstSteps.map((step, index) => (
				<a
					className="current-step-link"
					key={step}
					onClick={focusNode(step)}
				>
					{step}
					{index !== steps.length - 1 && ','}
				</a>
			))}
			{!!moreSteps.length && (
				<MoreSteps focusNode={focusNode} steps={moreSteps} />
			)}
		</div>
	);
}

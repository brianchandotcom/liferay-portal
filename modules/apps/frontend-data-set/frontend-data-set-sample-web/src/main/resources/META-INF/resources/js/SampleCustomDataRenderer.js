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

import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';
import {openModal} from 'frontend-js-web';
import React from 'react';

const SampleCustomDataRenderer = ({
	actions,
	itemData,
	itemId,
	loadData,
	openSidePanel,
	options,
	rootPropertyName,
	value,
	valuePath,
}) => {
	const ModalBody = ({closeModal}) => {
		return (
			<>
				<div>First action label: {actions[0].label}</div>
				<div>Item ID: {itemId}</div>
				<div>Item color: {itemData.color}</div>
				<div>Field label: {options.label}</div>
				<div>First field name: {rootPropertyName}</div>
				<div>Second field name: {valuePath[1]}</div>
				<br />
				<div>
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => {
								closeModal();

								loadData();
							}}
						>
							Reload Data
						</ClayButton>

						<ClayButton
							displayType="secondary"
							onClick={() => {
								closeModal();

								openSidePanel({
									url: 'about:blank',
								});
							}}
						>
							Open Side Panel
						</ClayButton>
					</ClayButton.Group>
				</div>
			</>
		);
	};

	return (
		<>
			<ClayLink
				onClick={() =>
					openModal({
						bodyComponent: ModalBody,
						title: Liferay.Language.get('details'),
					})
				}
				style={{cursor: 'pointer'}}
			>
				{value}
			</ClayLink>
		</>
	);
};

export default SampleCustomDataRenderer;

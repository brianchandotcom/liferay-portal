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
import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

import {useSelector} from '../../app/store/index';
import {openInfoItemSelector} from '../../core/openInfoItemSelector';

export default function ItemSelector({
	eventName,
	itemSelectorURL,
	label,
	onItemSelect,
	selectedItemTitle
}) {
	const mappedInfoItems = useSelector(state => state.mappedInfoItems);
	const [active, setActive] = useState(false);

	return (
		<>
			{label && <label htmlFor="itemSelectorInput">{label}</label>}

			<div className="d-flex">
				<ClayInput
					className="mr-2"
					id="itemSelectorInput"
					readOnly
					sizing="sm"
					type="text"
					value={selectedItemTitle || ''}
				/>

				{mappedInfoItems.length > 0 ? (
					<ClayDropDown
						active={active}
						onActiveChange={setActive}
						trigger={
							<ClayButton
								displayType="secondary"
								onClick={() => setActive(true)}
								small
							>
								<ClayIcon symbol="plus" />
							</ClayButton>
						}
					>
						<ClayDropDown.ItemList>
							{mappedInfoItems.map(item => (
								<ClayDropDown.Item
									key={item.classNameId}
									onClick={() => {
										onItemSelect(item);
										setActive(false);
									}}
								>
									{item.title}
								</ClayDropDown.Item>
							))}
							<ClayDropDown.Divider />
							<ClayDropDown.Item
								onClick={() =>
									openInfoItemSelector(
										onItemSelect,
										eventName,
										itemSelectorURL
									)
								}
							>
								{Liferay.Language.get('select-content')} ...
							</ClayDropDown.Item>
						</ClayDropDown.ItemList>
					</ClayDropDown>
				) : (
					<ClayButton
						displayType="secondary"
						onClick={() =>
							openInfoItemSelector(
								onItemSelect,
								eventName,
								itemSelectorURL
							)
						}
						small
					>
						<ClayIcon symbol="plus" />
					</ClayButton>
				)}
			</div>
		</>
	);
}

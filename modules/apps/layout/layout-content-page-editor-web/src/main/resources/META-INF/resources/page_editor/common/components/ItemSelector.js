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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {config} from '../../app/config/index';
import {useSelector} from '../../app/store/index';
import {useId} from '../../app/utils/useId';
import {openItemSelector} from '../../core/openItemSelector';

export default function ItemSelector({
	eventName,
	itemSelectorURL,
	label,
	onItemSelect,
	quickMappedInfoItems = [],
	modalProps,
	selectedItemTitle,
	showAddButton = true,
	showMappedItems = true,
	showLabel = true,
	transformValueCallback,
}) {
	const [active, setActive] = useState(false);
	const itemSelectorInputId = useId();
	const mappedInfoItems = useSelector((state) => state.mappedInfoItems || []);

	const mappedItems =
		quickMappedInfoItems.length === 0
			? mappedInfoItems
			: quickMappedInfoItems;

	const defaultEventName = `${config.portletNamespace}selectInfoItem`;

	const openModal = () =>
		openItemSelector({
			callback: onItemSelect,
			eventName: eventName || defaultEventName,
			itemSelectorURL: itemSelectorURL || config.infoItemSelectorURL,
			modalProps,
			transformValueCallback,
		});

	const selectContentIcon = selectedItemTitle ? 'change' : 'plus';

	return (
		<>
			{showLabel && <label htmlFor={itemSelectorInputId}>{label}</label>}

			<div className="d-flex">
				<ClayInput
					className={classNames('mr-2', {
						'page-editor__item-selector__content-input': showAddButton,
					})}
					id={itemSelectorInputId}
					onClick={() => {
						if (showAddButton) {
							openModal();
						}
					}}
					placeholder={Liferay.Util.sub(
						Liferay.Language.get('select-x'),
						label
					)}
					readOnly
					sizing="sm"
					type="text"
					value={selectedItemTitle || ''}
				/>

				{showAddButton &&
					(mappedItems.length > 0 && showMappedItems ? (
						<ClayDropDown
							active={active}
							onActiveChange={setActive}
							trigger={
								<ClayButtonWithIcon
									aria-label={Liferay.Language.get(
										'select-content-button'
									)}
									className="page-editor__item-selector__content-button"
									displayType="secondary"
									onClick={() => setActive(true)}
									small
									symbol={selectContentIcon}
								/>
							}
						>
							<ClayDropDown.ItemList>
								{mappedItems.map((item) => (
									<ClayDropDown.Item
										key={`${item.classNameId}-${item.classPK}`}
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
									onClick={() => {
										openModal();

										setActive(false);
									}}
								>
									{Liferay.Language.get('select-content')}...
								</ClayDropDown.Item>
							</ClayDropDown.ItemList>
						</ClayDropDown>
					) : (
						<ClayButtonWithIcon
							aria-label={Liferay.Language.get(
								'select-content-button'
							)}
							className="page-editor__item-selector__content-button"
							displayType="secondary"
							onClick={openModal}
							small
							symbol={selectContentIcon}
						/>
					))}

				{selectedItemTitle && (
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get(
							'clear-content-button'
						)}
						className={
							'ml-2 page-editor__item-selector__content-button'
						}
						displayType="secondary"
						onClick={() => onItemSelect({})}
						small
						symbol="times-circle"
					/>
				)}
			</div>
		</>
	);
}

ItemSelector.propTypes = {
	eventName: PropTypes.string,
	itemSelectorURL: PropTypes.string,
	label: PropTypes.string.isRequired,
	onItemSelect: PropTypes.func.isRequired,
	selectedItemTitle: PropTypes.string,
	transformValueCallback: PropTypes.func.isRequired,
};

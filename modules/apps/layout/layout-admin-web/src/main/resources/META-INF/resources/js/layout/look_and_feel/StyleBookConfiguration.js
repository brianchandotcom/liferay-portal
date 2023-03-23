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
import ClayForm, {ClayInput} from '@clayui/form';
import {openSelectionModal} from 'frontend-js-web';
import React, {useState} from 'react';

export default function StyleBookConfiguration({
	changeStyleBookURL,
	portletNamespace,
	styleBookEntryId: initialStyleBookEntryId,
	styleBookEntryName: initialStyleBookEntryName,
}) {
	const [styleBookEntry, setStyleBookEntry] = useState({
		name: initialStyleBookEntryName,
		styleBookEntryId: initialStyleBookEntryId,
	});

	const handleChangeStyleBookClick = () => {
		openSelectionModal({
			iframeBodyCssClass: '',
			onSelect(selectedItem) {
				if (selectedItem) {
					const itemValue = JSON.parse(selectedItem.value);

					setStyleBookEntry({
						name: itemValue.name,
						styleBookEntryId: itemValue.styleBookEntryId,
					});
				}
			},
			selectEventName: `${portletNamespace}selectStyleBook`,
			title: Liferay.Language.get('select-style-book'),
			url: changeStyleBookURL,
		});
	};

	return (
		<>
			<input
				name={`${portletNamespace}styleBookEntryId`}
				type="hidden"
				value={styleBookEntry.styleBookEntryId}
			/>

			<h3 className="sheet-subtitle">
				{Liferay.Language.get('style-book')}
			</h3>

			<div className="d-flex">
				<ClayForm.Group className="flex-grow-1 mb-0">
					<ClayInput
						onClick={handleChangeStyleBookClick}
						readOnly
						value={styleBookEntry.name}
					/>
				</ClayForm.Group>

				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('change-style-book')}
					className="ml-2"
					displayType="secondary"
					onClick={handleChangeStyleBookClick}
					symbol="plus"
				/>
			</div>
		</>
	);
}

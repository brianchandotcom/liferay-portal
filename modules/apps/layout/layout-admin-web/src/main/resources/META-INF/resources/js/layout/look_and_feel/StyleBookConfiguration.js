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
import {createRenderURL, openSelectionModal} from 'frontend-js-web';
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
		const renderURL = createRenderURL(changeStyleBookURL, {
			styleBookEntryId: styleBookEntry.styleBookEntryId,
		});

		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			iframeBodyCssClass: '',
			multiple: true,
			onSelect(selectedItem) {
				if (selectedItem) {
					setStyleBookEntry({
						name: selectedItem.name,
						styleBookEntryId: selectedItem.stylebookentryid,
					});
				}
			},
			selectEventName: `${portletNamespace}selectStyleBook`,
			title: Liferay.Language.get('select-style-book'),
			url: renderURL.toString(),
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

			<p>
				<strong>{`${Liferay.Language.get(
					'style-book-name'
				)}: `}</strong>

				{styleBookEntry.name}
			</p>

			<ClayButton.Group spaced>
				<ClayButton
					displayType="secondary"
					onClick={handleChangeStyleBookClick}
					small
				>
					{Liferay.Language.get('change-style-book')}
				</ClayButton>
			</ClayButton.Group>
		</>
	);
}

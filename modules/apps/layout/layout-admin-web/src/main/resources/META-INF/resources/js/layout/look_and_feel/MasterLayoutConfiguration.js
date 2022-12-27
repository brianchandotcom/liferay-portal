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
import {openSelectionModal} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

const DEFAULT_MASTER_LAYOUT_PLID = '0';

export default function MasterLayoutConfiguration({
	changeMasterLayoutURL,
	editMasterLayoutURL,
	masterLayoutName: initialMasterLayoutName,
	masterLayoutPlid: initialMasterLayoutPlid,
	portletNamespace,
}) {
	const [masterLayout, setMasterLayout] = useState({
		name: initialMasterLayoutName,
		plid: initialMasterLayoutPlid || DEFAULT_MASTER_LAYOUT_PLID,
	});

	const handleChangeMasterButtonClick = () => {
		openSelectionModal({
			iframeBodyCssClass: '',
			onSelect(selectedItem) {
				if (selectedItem) {
					const itemValue = JSON.parse(selectedItem.value);

					setMasterLayout({
						name: itemValue.name,
						plid: itemValue.plid,
					});
				}
			},
			selectEventName: `${portletNamespace}selectMasterLayout`,
			title: Liferay.Language.get('select-master'),
			url: changeMasterLayoutURL,
		});
	};

	useEffect(() => {
		const themeContainer = document.getElementById(
			`${portletNamespace}themeContainer`
		);

		if (!themeContainer) {
			return;
		}

		if (masterLayout.plid === DEFAULT_MASTER_LAYOUT_PLID) {
			themeContainer.classList.remove('hide');
			themeContainer.removeAttribute('aria-hidden');
		}
		else {
			themeContainer.classList.add('hide');
			themeContainer.setAttribute('aria-hidden', 'true');
		}
	}, [masterLayout.plid, portletNamespace]);

	return (
		<>
			<input
				name={`${portletNamespace}masterLayoutPlid`}
				type="hidden"
				value={masterLayout.plid}
			/>

			<h3 className="sheet-subtitle">{Liferay.Language.get('master')}</h3>

			<p>
				<strong>{`${Liferay.Language.get('master-name')}: `}</strong>

				{masterLayout.name}
			</p>

			{editMasterLayoutURL &&
			masterLayout.plid &&
			masterLayout.plid !== DEFAULT_MASTER_LAYOUT_PLID ? (
				<ClayButton.Group spaced>
					<ClayLink
						className="btn btn-secondary btn-sm"
						href={editMasterLayoutURL}
					>
						{Liferay.Language.get('edit-master')}
					</ClayLink>

					<ClayButton
						displayType="secondary"
						onClick={handleChangeMasterButtonClick}
						size="sm"
					>
						{Liferay.Language.get('change-master')}
					</ClayButton>
				</ClayButton.Group>
			) : (
				<ClayButton
					displayType="secondary"
					onClick={handleChangeMasterButtonClick}
					size="sm"
				>
					{Liferay.Language.get('change-master')}
				</ClayButton>
			)}
		</>
	);
}

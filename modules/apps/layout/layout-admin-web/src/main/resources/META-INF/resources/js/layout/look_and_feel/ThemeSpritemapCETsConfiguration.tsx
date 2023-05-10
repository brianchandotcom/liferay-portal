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

import ClayAlert from '@clayui/alert';
import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import {openSelectionModal} from 'frontend-js-web';
import React, {useState} from 'react';

export default function ThemeSpritemapCETsConfiguration({
	portletNamespace,
	selectThemeSpritemapCETEventName,
	themeSpritemapCET = {cetExternalReferenceCode: '', name: ''},
	themeSpritemapCETSelectorURL,
}: IProps) {
	const [extensionName, setExtensionName] = useState(themeSpritemapCET.name);
	const [cetExternalReferenceCode, setCETExternalReferenceCode] = useState(
		themeSpritemapCET.cetExternalReferenceCode
	);

	const onClick = () => {
		openSelectionModal<{value: string}>({
			onSelect: (selectedItem) => {
				const item = JSON.parse(selectedItem.value);

				setCETExternalReferenceCode(item.cetExternalReferenceCode);
				setExtensionName(item.name);
			},
			selectEventName: selectThemeSpritemapCETEventName,
			title: Liferay.Language.get(
				'select-theme-spritemap-client-extension'
			),
			url: themeSpritemapCETSelectorURL,
		});
	};

	return (
		<>
			<ClayAlert displayType="info" title={Liferay.Language.get('info')}>
				{Liferay.Language.get(
					'to-add-or-edit-the-existing-spritemap-simply-copy-paste-and-make-changes-as-needed-to-your-registered-extension'
				)}
			</ClayAlert>

			<p className="text-secondary">
				{Liferay.Language.get(
					'use-this-client-extension-to-fully-replace-the-default-spritemap-contained-in-the-theme'
				)}
			</p>
			<ClayInput
				name={`${portletNamespace}themeSpritemapCETExternalReferenceCode`}
				type="hidden"
				value={cetExternalReferenceCode}
			/>

			<ClayForm.Group>
				<label
					htmlFor={`${portletNamespace}themeSpritemapExtensionNameInput`}
				>
					{Liferay.Language.get('theme-spritemap-client-extension')}
				</label>

				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayInput
							id={`${portletNamespace}themeSpritemapExtensionNameInput`}
							onClick={onClick}
							placeholder={Liferay.Language.get('name')}
							readOnly
							type="text"
							value={extensionName}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem shrink>
						{extensionName ? (
							<>
								<ClayButtonWithIcon
									aria-label={Liferay.Language.get('replace')}
									className="mr-2"
									displayType="secondary"
									onClick={onClick}
									symbol="change"
								/>

								<ClayButtonWithIcon
									aria-label={Liferay.Language.get('delete')}
									displayType="secondary"
									onClick={() => {
										setExtensionName('');
										setCETExternalReferenceCode('');
									}}
									symbol="trash"
								/>
							</>
						) : (
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get('select')}
								displayType="secondary"
								onClick={onClick}
								symbol="plus"
							/>
						)}
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>
		</>
	);
}

interface IProps {
	portletNamespace: string;
	selectThemeSpritemapCETEventName: string;
	themeSpritemapCET: {
		cetExternalReferenceCode?: string;
		name?: string;
	};
	themeSpritemapCETSelectorURL: string;
}

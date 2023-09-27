/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import {openSelectionModal} from 'frontend-js-web';
import React, {useState} from 'react';

export default function ThemeSpritemapCETsConfiguration({
	isReadOnly,
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
		if (isReadOnly) {
			return true;
		}

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
									className="c-mr-2"
									disabled={isReadOnly}
									displayType="secondary"
									onClick={onClick}
									symbol="change"
								/>

								<ClayButtonWithIcon
									aria-label={Liferay.Language.get('delete')}
									disabled={isReadOnly}
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
								disabled={isReadOnly}
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
	isReadOnly: boolean;
	portletNamespace: string;
	selectThemeSpritemapCETEventName: string;
	themeSpritemapCET: {
		cetExternalReferenceCode?: string;
		name?: string;
	};
	themeSpritemapCETSelectorURL: string;
}

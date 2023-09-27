/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import {openSelectionModal} from 'frontend-js-web';
import React, {useState} from 'react';

export default function ThemeCSSReplacementSelector({
	isReadOnly,
	placeholder,
	portletNamespace,
	selectThemeCSSClientExtensionEventName,
	selectThemeCSSClientExtensionURL,
	themeCSSCETExternalReferenceCode,
	themeCSSExtensionName,
}: IProps) {
	const [extensionName, setExtensionName] = useState(themeCSSExtensionName);
	const [cetExternalReferenceCode, setCETExternalReferenceCode] = useState(
		themeCSSCETExternalReferenceCode
	);

	const onClick = () => {
		if (isReadOnly) {
			return;
		}

		openSelectionModal<{value: string}>({
			onSelect: (selectedItem) => {
				const item = JSON.parse(selectedItem.value);

				setCETExternalReferenceCode(item.cetExternalReferenceCode);
				setExtensionName(item.name);
			},
			selectEventName: selectThemeCSSClientExtensionEventName,
			title: Liferay.Language.get('select-theme-css-client-extension'),
			url: selectThemeCSSClientExtensionURL,
		});
	};

	return (
		<>
			<p className="text-secondary">
				{Liferay.Language.get(
					'use-this-client-extension-to-fully-replace-the-default-css-contained-in-the-theme'
				)}
			</p>

			<ClayInput
				name={`${portletNamespace}themeCSSCETExternalReferenceCode`}
				type="hidden"
				value={cetExternalReferenceCode}
			/>
			<ClayForm.Group>
				<label
					htmlFor={`${portletNamespace}themeCSSReplacementExtension`}
				>
					{Liferay.Language.get('theme-css')}
				</label>

				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayInput
							id={`${portletNamespace}themeCSSReplacementExtension`}
							onClick={onClick}
							placeholder={placeholder}
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
	placeholder: string;
	portletNamespace: string;
	selectThemeCSSClientExtensionEventName: string;
	selectThemeCSSClientExtensionURL: string;
	themeCSSCETExternalReferenceCode: string;
	themeCSSExtensionName: string;
}

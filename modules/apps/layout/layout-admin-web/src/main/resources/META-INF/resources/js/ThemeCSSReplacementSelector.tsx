/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import {openSelectionModal} from 'frontend-js-components-web';
import React, {useState} from 'react';

export default function ThemeCSSReplacementSelector({
	helpText,
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

	const formTextId = `${portletNamespace}formText`;

	return (
		<>
			<p className="text-secondary">
				{Liferay.Language.get(
					"use-this-client-extension-to-replace-the-theme's-css"
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
							aria-describedby={formTextId}
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

				{helpText && (
					<ClayForm.FeedbackGroup>
						<ClayForm.Text id={formTextId}>
							{helpText}
						</ClayForm.Text>
					</ClayForm.FeedbackGroup>
				)}
			</ClayForm.Group>
		</>
	);
}

interface IProps {
	helpText?: string;
	isReadOnly: boolean;
	placeholder: string;
	portletNamespace: string;
	selectThemeCSSClientExtensionEventName: string;
	selectThemeCSSClientExtensionURL: string;
	themeCSSCETExternalReferenceCode: string;
	themeCSSExtensionName: string;
}

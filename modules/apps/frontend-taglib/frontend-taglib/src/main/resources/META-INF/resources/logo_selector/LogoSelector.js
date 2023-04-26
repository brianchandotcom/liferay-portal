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
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

export default function LogoSelector({
	defaultLogoURL,
	description,
	label,
	logoName: initialLogoName,
	logoURL: initialLogoURL,
	portletNamespace,
	selectLogoURL,
}) {
	const [values, setValues] = useState({
		deleteLogo: false,
		fileEntryId: '',
		logoName:
			initialLogoURL === defaultLogoURL
				? Liferay.Language.get('default')
				: initialLogoName ||
				  sub(Liferay.Language.get('custom-x'), label),
		logoURL: initialLogoURL,
	});

	const onChangeLogo = () => {
		Liferay.Util.openWindow({
			cache: false,
			dialog: {
				destroyOnHide: true,
			},
			dialogIframe: {
				bodyCssClass: 'dialog-with-footer',
			},
			id: `${portletNamespace}changeLogo`,
			title: sub(Liferay.Language.get('upload-x'), label),
			uri: selectLogoURL.replace(
				escape('[$CURRENT_LOGO_URL$]'),
				escape(logoURL)
			),
		});
	};

	const onClearLogo = () => {
		setValues({
			deleteLogo: true,
			fileEntryId: '',
			logoName: Liferay.Language.get('default'),
			logoURL: defaultLogoURL,
		});
	};

	useEffect(() => {
		const handleChangeLogo = ({
			fileEntryId,
			previewURL,
			tempImageFileName,
		}) => {
			setValues({
				deleteLogo: false,
				fileEntryId,
				logoName: tempImageFileName,
				logoURL: previewURL,
			});
		};

		Liferay.on('changeLogo', handleChangeLogo);

		return () => {
			Liferay.detach('changeLogo', handleChangeLogo);
		};
	}, []);

	const {deleteLogo, fileEntryId, logoName, logoURL} = values;

	return (
		<>
			<input
				name={`${portletNamespace}deleteLogo`}
				type="hidden"
				value={deleteLogo}
			/>

			<input
				name={`${portletNamespace}fileEntryId`}
				type="hidden"
				value={fileEntryId}
			/>

			{description && (
				<p
					className="text-secondary"
					id={`${portletNamespace}description`}
				>
					{description}
				</p>
			)}

			{logoURL ? (
				<img
					alt={sub(Liferay.Language.get('current-x'), label)}
					className="logo-selector-img mb-3"
					src={logoURL}
				/>
			) : (
				<p className="text-secondary">{Liferay.Language.get('none')}</p>
			)}

			<ClayForm.Group>
				<label htmlFor={`${portletNamespace}logoName`}>{label}</label>

				<div className="d-flex">
					<ClayInput
						aria-describedby={
							description
								? `${portletNamespace}description`
								: null
						}
						className="mr-2"
						id={`${portletNamespace}logoName`}
						readOnly
						value={logoName || Liferay.Language.get('custom-x')}
					/>

					<ClayButtonWithIcon
						aria-label={sub(
							Liferay.Language.get('change-x'),
							label
						)}
						className="flex-shrink-0 mr-2"
						displayType="secondary"
						onClick={onChangeLogo}
						symbol="change"
						title={sub(Liferay.Language.get('change-x'), label)}
					/>

					<ClayButtonWithIcon
						aria-label={sub(Liferay.Language.get('clear-x'), label)}
						className="flex-shrink-0"
						disabled={logoURL === defaultLogoURL}
						displayType="secondary"
						onClick={onClearLogo}
						symbol="times-circle"
						title={sub(Liferay.Language.get('clear-x'), label)}
					/>
				</div>
			</ClayForm.Group>
		</>
	);
}

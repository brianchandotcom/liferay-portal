/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

function LogoSelector({
	defaultIcon,
	disabled,
	label,
	logoId: initialLogoId,
	logoURL: initialLogoURL,
	name,
	namespace,
	onChange,
	selectLogoURL,
}) {
	const [values, setValues] = useState({
		deleteLogo: false,
		fileEntryId: initialLogoId || 0,
		logoName: !initialLogoId
			? Liferay.Language.get('default')
			: sub(Liferay.Language.get('custom-x'), label),
		logoURL: initialLogoURL,
	});

	const onChangeLogo = () => {
		Liferay.Util.openModal({
			id: `${namespace}changeLogo`,
			iframeBodyCssClass: 'dialog-with-footer',
			size: 'full-screen',
			title: sub(Liferay.Language.get('upload-x'), label),
			url: selectLogoURL.replace(
				escape('[$CURRENT_LOGO_URL$]'),
				escape(logoURL)
			),
		});
	};

	const onClearLogo = () => {
		setValues({
			deleteLogo: true,
			fileEntryId: 0,
			logoName: Liferay.Language.get('default'),
			logoURL: initialLogoURL,
		});
	};

	useEffect(() => {
		onChange({
			target: {
				name,
				value: parseInt(values.fileEntryId, 10) || 0,
			},
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [name, values]);

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
				name={`${namespace}deleteLogo`}
				type="hidden"
				value={deleteLogo}
			/>

			<input
				name={`${namespace}fileEntryId`}
				type="hidden"
				value={fileEntryId}
			/>

			{fileEntryId ? (
				<img
					alt={sub(Liferay.Language.get('current-x'), label)}
					className="logo-selector-img mb-3"
					src={logoURL}
				/>
			) : (
				<svg className="logo-selector-default-img mb-3">
					<use href={defaultIcon}></use>
				</svg>
			)}

			<ClayForm.Group>
				<label htmlFor={`${namespace}logoName`}>{label}</label>

				<div className="d-flex">
					<ClayInput
						className="mr-2"
						id={`${namespace}logoName`}
						readOnly
						value={logoName || Liferay.Language.get('custom-x')}
					/>

					<ClayButtonWithIcon
						aria-label={sub(
							Liferay.Language.get('change-x'),
							label
						)}
						className="flex-shrink-0 mr-2"
						disabled={disabled}
						displayType="secondary"
						onClick={onChangeLogo}
						symbol="change"
						title={sub(Liferay.Language.get('change-x'), label)}
					/>

					<ClayButtonWithIcon
						aria-label={sub(Liferay.Language.get('clear-x'), label)}
						className="flex-shrink-0"
						disabled={!fileEntryId || disabled}
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

LogoSelector.defaultProps = {
	disabled: false,
	label: Liferay.Language.get('image'),
	logoId: 0,
	name: 'logoId',
};

LogoSelector.propTypes = {
	defaultIcon: PropTypes.string.isRequired,
	disabled: PropTypes.bool,
	label: PropTypes.string,
	logoId: PropTypes.number,
	logoURL: PropTypes.string,
	name: PropTypes.string.isRequired,
	namespace: PropTypes.string.isRequired,
	onChange: PropTypes.func.isRequired,
	selectLogoURL: PropTypes.string.isRequired,
};

export default LogoSelector;

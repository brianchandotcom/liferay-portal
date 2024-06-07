/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classnames from 'classnames';
import {openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import ChartContext from '../ChartContext';
import {getCountries} from '../data/countries';
import {updateOrganization} from '../data/organizations';
import LogoSelector from '../utils/LogoSelector';
import {
	ACTION_KEYS,
	DEFAULT_IMAGE_PATHS_MAP,
	INFO_PANEL_MODE_MAP,
	MODEL_TYPE_MAP,
	SYMBOLS_MAP,
} from '../utils/constants';
import {hasPermission} from '../utils/index';

function EditOrganizationInfoPanel({
	data,
	namespace,
	pathImage,
	selectLogoURL,
	spritemap,
	type,
	updatePanelViewHandler,
}) {
	const [organizationData, setOrganizationData] = useState({
		...data,
		addressCountryCode: data.location?.addressCountryCode,
		addressRegionCode: data.location?.addressRegionCode,
		errors: {},
		isValid: true,
	});
	const [isLoading, setIsLoading] = useState(false);
	const {chartInstanceRef} = useContext(ChartContext);
	const [countries, setCountries] = useState([]);

	useEffect(() => {
		setIsLoading(true);

		getCountries().then((data) => {
			setCountries(
				(data?.items || [])
					.filter((country) => {
						return country.active;
					})
					.map((country) => {
						country.name =
							country.title_i18n[
								Liferay.ThemeDisplay.getLanguageId()
							] || country.title_i18n['en_US'];

						return country;
					})
					.sort((a, b) => {
						return a.name > b.name ? 1 : b.name > a.name ? -1 : 0;
					})
			);

			setIsLoading(false);
		});
	}, []);

	const onChangeHandler = ({target}) => {
		const errors = organizationData.errors;
		const targetName = target.name.replace(namespace, '');

		if (['name'].indexOf(targetName) >= 0) {
			if (!target.value || target.value.length <= 0) {
				errors[targetName] = Liferay.Language.get(
					'this-field-is-required'
				);
			}
			else {
				delete errors[targetName];
			}
		}

		setOrganizationData((prevState) => ({
			...prevState,
			errors,
			isValid: !Object.keys(errors).length,
			[targetName]: target.value,
		}));
	};

	const onSaveHandler = useCallback(() => {
		if (
			!organizationData.isValid ||
			!hasPermission(organizationData, ACTION_KEYS.organization.UPDATE)
		) {
			return;
		}

		setIsLoading(true);

		const data = {
			imageId: organizationData.imageId,
			location: {
				addressCountry: organizationData.addressCountryCode,
				addressRegion: organizationData.addressRegionCode,
			},
			name: organizationData.name,
		};

		updateOrganization(organizationData.id, data)
			.then((newData) => {
				newData = Object.assign(organizationData, newData);
				newData.modelType = newData.type;
				newData.type = type;

				chartInstanceRef.current.updateNodeContent(newData);

				updatePanelViewHandler({
					data: newData,
					mode: INFO_PANEL_MODE_MAP.view,
					type,
				});

				setIsLoading(false);

				openToast({
					message: Liferay.Language.get(
						'your-request-completed-successfully'
					),
					type: 'success',
				});
			})
			.catch((error) => {
				setIsLoading(false);

				openToast({
					message:
						error.message ||
						error.title ||
						Liferay.Language.get('an-error-occurred'),
					title: Liferay.Language.get('error'),
					type: 'danger',
				});
			});
	}, [organizationData, chartInstanceRef, type, updatePanelViewHandler]);

	const onCancelHandler = useCallback(() => {
		updatePanelViewHandler({
			data: organizationData,
			mode: INFO_PANEL_MODE_MAP.view,
			type,
		});
	}, [organizationData, type, updatePanelViewHandler]);

	return (
		<>
			<div className="sidebar-header">
				<div className="autofit-row sidebar-section">
					<div className="autofit-col autofit-col-expand">
						<h1 className="component-title">{data.name}</h1>

						<h2 className="component-subtitle">
							{Liferay.Language.get('organization')}
						</h2>
					</div>
				</div>
			</div>
			<div className="flex-grow-1 sidebar-body">
				<div>
					<LogoSelector
						defaultIcon={`${spritemap}#${SYMBOLS_MAP[type]}`}
						disabled={isLoading}
						logoId={organizationData.imageId}
						logoURL={
							organizationData.image ||
							pathImage + DEFAULT_IMAGE_PATHS_MAP.organization
						}
						name="imageId"
						namespace={namespace}
						onChange={onChangeHandler}
						selectLogoURL={selectLogoURL}
					/>

					<ClayForm.Group
						className={classnames({
							'has-error': !!organizationData.errors.name,
						})}
					>
						<label htmlFor={`${namespace}name`}>
							{Liferay.Language.get('name')}

							<ClayIcon
								className="c-ml-1 reference-mark"
								symbol="asterisk"
							/>
						</label>

						<ClayInput
							disabled={isLoading}
							id={`${namespace}name`}
							name={`${namespace}name`}
							onChange={onChangeHandler}
							required={true}
							type="text"
							value={organizationData.name}
						/>

						<ErrorMessage
							errors={organizationData.errors}
							name="name"
						/>
					</ClayForm.Group>

					<ClayForm.Group>
						<label htmlFor={`${namespace}typeLabel`}>
							{Liferay.Language.get('type-label')}
						</label>

						<ClayInput
							disabled={true}
							id={`${namespace}typeLabel`}
							name={`${namespace}typeLabel`}
							readOnly={true}
							type="text"
							value={Liferay.Language.get('organization')}
						/>
					</ClayForm.Group>

					<ClayForm.Group
						className={classnames({
							'has-error':
								!!organizationData.errors.addressCountryCode,
						})}
					>
						<label htmlFor={`${namespace}addressCountryCode`}>
							{Liferay.Language.get('country')}
						</label>

						<ClaySelectWithOption
							disabled={isLoading}
							id={`${namespace}addressCountryCode`}
							name={`${namespace}addressCountryCode`}
							onChange={onChangeHandler}
							options={[
								{},
								...countries.map((country) => {
									return {
										label: country.name,
										value: country.a2,
									};
								}),
							]}
							value={organizationData.addressCountryCode}
						/>

						<ErrorMessage
							errors={organizationData.errors}
							name="addressCountryCode"
						/>
					</ClayForm.Group>

					<ClayForm.Group
						className={classnames({
							'has-error':
								!!organizationData.errors.addressRegionCode,
						})}
					>
						<label htmlFor={`${namespace}addressRegionCode`}>
							{Liferay.Language.get('region')}
						</label>

						<ClaySelectWithOption
							disabled={isLoading}
							id={`${namespace}addressRegionCode`}
							name={`${namespace}addressRegionCode`}
							onChange={onChangeHandler}
							options={[
								{},
								...(
									(
										countries.find((country) => {
											return (
												country.a2 ===
												organizationData.addressCountryCode
											);
										}) || {}
									).regions || []
								)
									.filter((region) => {
										return region.active;
									})
									.map((region) => {
										return {
											label:
												region.title_i18n[
													Liferay.ThemeDisplay.getLanguageId()
												] || region.title_i18n['en_US'],
											value: region.regionCode,
										};
									}),
							]}
							value={organizationData.addressRegionCode}
						/>

						<ErrorMessage
							errors={organizationData.errors}
							name="addressRegionCode"
						/>
					</ClayForm.Group>
				</div>
			</div>
			<div className="sidebar-footer">
				<ClayButton
					disabled={!organizationData.isValid || isLoading}
					displayType="primary"
					monospaced={false}
					onClick={onSaveHandler}
				>
					{Liferay.Language.get('save')}
				</ClayButton>

				<ClayButton
					className="ml-2"
					disabled={isLoading}
					displayType="secondary"
					monospaced={false}
					onClick={onCancelHandler}
				>
					{Liferay.Language.get('cancel')}
				</ClayButton>
			</div>
		</>
	);
}

const ErrorMessage = ({errors, name}) => {
	return (
		<>
			{!!errors[name] && (
				<div className="form-feedback-group">
					<div className="form-feedback-item">{errors[name]}</div>
				</div>
			)}
		</>
	);
};

EditOrganizationInfoPanel.defaultProps = {
	type: MODEL_TYPE_MAP.account,
};

EditOrganizationInfoPanel.propTypes = {
	data: PropTypes.object.isRequired,
	namespace: PropTypes.string,
	pathImage: PropTypes.string.isRequired,
	selectLogoURL: PropTypes.string,
	spritemap: PropTypes.string.isRequired,
	type: PropTypes.string,
	updatePanelViewHandler: PropTypes.func.isRequired,
};

export default EditOrganizationInfoPanel;

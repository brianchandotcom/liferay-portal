/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classnames from 'classnames';
import {openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import ChartContext from '../ChartContext';
import {getAccount, updateAccount} from '../data/accounts';
import FieldsWrapper from '../objects/FieldsWrapper';
import LogoSelector from '../utils/LogoSelector';
import {
	ACTION_KEYS,
	DEFAULT_IMAGE_PATHS_MAP,
	INFO_PANEL_MODE_MAP,
	MODEL_TYPE_MAP,
	SYMBOLS_MAP,
} from '../utils/constants';
import {hasPermission, localizeModelType} from '../utils/index';

function EditAccountInfoPanel({
	data,
	namespace,
	pathImage,
	selectLogoURL,
	spritemap,
	type,
	updatePanelViewHandler,
}) {
	const [accountData, setAccountData] = useState({
		...data,
		errors: {},
		isValid: true,
	});
	const [isLoading, setIsLoading] = useState(false);
	const [accountObjectDefinition, setAccountObjectDefinition] = useState([]);
	const {chartInstanceRef} = useContext(ChartContext);

	useEffect(() => {
		if (!accountData.id || accountData.fullLoaded) {
			return;
		}

		getAccount(accountData.id)
			.then((newData) => {
				newData = Object.assign(accountData, newData);
				newData.fullLoaded = true;
				newData.modelType = newData.type;
				newData.type = type;

				chartInstanceRef.current.updateNodeContent(newData);

				setAccountData((prevState) => ({
					...prevState,
					...newData,
				}));
			})
			.catch((error) => {
				openToast({
					message:
						error.message ||
						error.title ||
						Liferay.Language.get('an-error-occurred'),
					title: Liferay.Language.get('error'),
					type: 'danger',
				});
			});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [accountData.id]);

	const onChangeHandler = ({target}) => {
		const errors = accountData.errors;
		const targetName = target.name.replace(namespace, '');

		if (targetName === 'name') {
			if (!target.value || target.value.length <= 0) {
				errors.name = Liferay.Language.get('this-field-is-required');
			}
			else {
				delete errors.name;
			}
		}

		setAccountData((prevState) => ({
			...prevState,
			errors,
			isValid: !Object.keys(errors).length,
			[targetName]: target.value,
		}));
	};

	const onObjectFieldsChangeHandler = useCallback(
		({data, hasError, name}) => {
			const errors = accountData.errors;

			if (hasError) {
				errors[name] = true;
			}
			else {
				delete errors[name];
			}

			setAccountData((prevState) => ({
				...prevState,
				...data,
				errors,
				isValid: !Object.keys(errors).length,
			}));
		},
		[accountData]
	);

	const onObjectDefinitionLoadHandler = useCallback(({data}) => {
		setAccountObjectDefinition(data);
	}, []);

	const onSaveHandler = useCallback(() => {
		if (
			!accountData.isValid ||
			!hasPermission(accountData, ACTION_KEYS.account.UPDATE)
		) {
			return;
		}

		setIsLoading(true);

		const data = {
			description: accountData.description,
			externalReferenceCode: accountData.externalReferenceCode,
			logoId: accountData.logoId,
			name: accountData.name,
			taxId: accountData.taxId,
		};

		accountObjectDefinition.forEach((objectDefinition) => {
			const objectDefinitionName = objectDefinition.name;

			if (
				objectDefinitionName in accountData &&
				accountData[objectDefinitionName] !== null
			) {
				data[objectDefinitionName] = accountData[objectDefinitionName];
			}
		});

		updateAccount(accountData.id, data)
			.then((newData) => {
				newData = Object.assign(accountData, newData);
				newData.modelType = newData.type;
				newData.type = type;

				chartInstanceRef.current.updateNodeContent(newData);

				updatePanelViewHandler({
					data: newData,
					mode: INFO_PANEL_MODE_MAP.view,
					type,
				});
				openToast({
					message: Liferay.Language.get(
						'your-request-completed-successfully'
					),
					type: 'success',
				});
			})
			.catch((error) => {
				openToast({
					message:
						error.message ||
						error.title ||
						Liferay.Language.get('an-error-occurred'),
					title: Liferay.Language.get('error'),
					type: 'danger',
				});
			});

		setIsLoading(false);
	}, [
		accountData,
		accountObjectDefinition,
		chartInstanceRef,
		type,
		updatePanelViewHandler,
	]);

	const onCancelHandler = useCallback(() => {
		updatePanelViewHandler({
			data,
			mode: INFO_PANEL_MODE_MAP.view,
			type,
		});
	}, [data, type, updatePanelViewHandler]);

	return (
		<>
			<div className="sidebar-header">
				<div className="autofit-row sidebar-section">
					<div className="autofit-col autofit-col-expand">
						<h1 className="component-title">{data.name}</h1>

						<h2 className="component-subtitle">
							{Liferay.Language.get('account')}
						</h2>
					</div>
				</div>
			</div>
			<div className="flex-grow-1 sidebar-body">
				<LogoSelector
					defaultIcon={`${spritemap}#${SYMBOLS_MAP[type]}`}
					disabled={isLoading}
					logoId={accountData.logoId}
					logoURL={
						accountData.logoURL ||
						pathImage + DEFAULT_IMAGE_PATHS_MAP.account
					}
					namespace={namespace}
					onChange={onChangeHandler}
					selectLogoURL={selectLogoURL}
				/>

				<ClayForm.Group
					className={classnames({
						'has-error': !!accountData.errors.name,
					})}
				>
					<label htmlFor={`${namespace}name`}>
						{Liferay.Language.get('account-name')}

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
						value={accountData.name}
					/>

					<ErrorMessage errors={accountData.errors} name="name" />
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor={`${namespace}type`}>
						{Liferay.Language.get('type')}
					</label>

					<ClayInput
						disabled={true}
						id={`${namespace}type`}
						name={`${namespace}type`}
						readOnly={true}
						type="text"
						value={localizeModelType(accountData.modelType)}
					/>
				</ClayForm.Group>

				<ClayForm.Group
					className={classnames({
						'has-error': !!accountData.errors.taxId,
					})}
				>
					<label htmlFor={`${namespace}taxId`}>
						{Liferay.Language.get('tax-id')}
					</label>

					<ClayInput
						disabled={isLoading}
						id={`${namespace}taxId`}
						name={`${namespace}taxId`}
						onChange={onChangeHandler}
						type="text"
						value={accountData.taxId}
					/>

					<ErrorMessage errors={accountData.errors} name="taxId" />
				</ClayForm.Group>

				<ClayForm.Group
					className={classnames({
						'has-error': !!accountData.errors.externalReferenceCode,
					})}
				>
					<label htmlFor={`${namespace}externalReferenceCode`}>
						{Liferay.Language.get('external-reference-code')}
					</label>

					<ClayInput
						component="textarea"
						disabled={isLoading}
						id={`${namespace}externalReferenceCode`}
						name={`${namespace}externalReferenceCode`}
						onChange={onChangeHandler}
						type="text"
						value={accountData.externalReferenceCode}
					/>

					<ErrorMessage
						errors={accountData.errors}
						name="externalReferenceCode"
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor={`${namespace}accountId`}>
						{Liferay.Language.get('account-id')}
					</label>

					<ClayInput
						disabled={true}
						id={`${namespace}accountId`}
						name={`${namespace}accountId`}
						readOnly={true}
						type="text"
						value={accountData.id}
					/>
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor={`${namespace}description`}>
						{Liferay.Language.get('description')}
					</label>

					<ClayInput
						component="textarea"
						disabled={isLoading}
						id={`${namespace}description`}
						name={`${namespace}description`}
						onChange={onChangeHandler}
						type="text"
						value={accountData.description}
					/>

					<ErrorMessage
						errors={accountData.errors}
						name="description"
					/>
				</ClayForm.Group>

				{Liferay.FeatureFlags['COMMERCE-13024'] &&
				accountData.fullLoaded ? (
					<FieldsWrapper
						mode="edit"
						namespace={namespace}
						objectData={accountData}
						objectExternalReferenceCode="L_ACCOUNT"
						onObjectDataChange={onObjectFieldsChangeHandler}
						onObjectDefinitionLoad={onObjectDefinitionLoadHandler}
					></FieldsWrapper>
				) : (
					<></>
				)}
			</div>

			<div className="sidebar-footer">
				<ClayButton
					disabled={!accountData.isValid || isLoading}
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

EditAccountInfoPanel.defaultProps = {
	type: MODEL_TYPE_MAP.account,
};

EditAccountInfoPanel.propTypes = {
	data: PropTypes.object.isRequired,
	namespace: PropTypes.string,
	pathImage: PropTypes.string.isRequired,
	selectLogoURL: PropTypes.string,
	spritemap: PropTypes.string.isRequired,
	type: PropTypes.string,
	updatePanelViewHandler: PropTypes.func.isRequired,
};

export default EditAccountInfoPanel;

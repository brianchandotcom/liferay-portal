/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {openConfirmModal, openToast, sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import ChartContext from '../ChartContext';
import {deleteAccount, getAccount} from '../data/accounts';
import FieldsWrapper from '../objects/FieldsWrapper';
import {
	ACTION_KEYS,
	INFO_PANEL_MODE_MAP,
	MODEL_TYPE_MAP,
	SYMBOLS_MAP,
} from '../utils/constants';
import {hasPermission, localizeModelType} from '../utils/index';

function ViewAccountInfoPanel({
	closePanelViewHandler,
	data,
	namespace,
	spritemap,
	type,
	updatePanelViewHandler,
}) {
	const [accountData, setAccountData] = useState(data);
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

	useEffect(() => {
		setAccountData(data);
	}, [data]);

	const deleteHandler = useCallback(() => {
		openConfirmModal({
			message: sub(
				Liferay.Language.get('x-will-be-deleted'),
				accountData.name
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					deleteAccount(accountData.id)
						.then(() => {
							chartInstanceRef.current.deleteNodes(
								[accountData],
								true
							);

							openToast({
								message: Liferay.Language.get(
									'your-request-completed-successfully'
								),
								type: 'success',
							});

							closePanelViewHandler();
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
				}
			},
		});
	}, [accountData, chartInstanceRef, closePanelViewHandler]);

	const editHandler = useCallback(() => {
		updatePanelViewHandler({
			data: accountData,
			mode: INFO_PANEL_MODE_MAP.edit,
			type,
		});
	}, [accountData, type, updatePanelViewHandler]);

	return (
		<>
			<div className="sidebar-header">
				<div className="autofit-row sidebar-section">
					<div className="autofit-col autofit-col-expand">
						<h1 className="component-title">{accountData.name}</h1>

						<h2 className="component-subtitle">
							{Liferay.Language.get('account')}
						</h2>
					</div>

					{(hasPermission(accountData, ACTION_KEYS.account.DELETE) ||
						hasPermission(
							accountData,
							ACTION_KEYS.account.UPDATE
						)) && (
						<div className="autofit-col">
							<ul className="autofit-padded-no-gutters autofit-row">
								<li className="autofit-col">
									<ClayDropDown
										trigger={
											<ClayButtonWithIcon
												aria-label={Liferay.Language.get(
													'more-actions'
												)}
												className="btn-outline-borderless btn-outline-secondary"
												displayType="unstyled"
												symbol="ellipsis-v"
											/>
										}
									>
										<ClayDropDown.ItemList>
											{hasPermission(
												accountData,
												ACTION_KEYS.account.UPDATE
											) && (
												<ClayDropDown.Item
													onClick={editHandler}
												>
													{Liferay.Language.get(
														'edit'
													)}
												</ClayDropDown.Item>
											)}

											{hasPermission(
												accountData,
												ACTION_KEYS.account.DELETE
											) && (
												<ClayDropDown.Item
													onClick={deleteHandler}
												>
													{Liferay.Language.get(
														'delete'
													)}
												</ClayDropDown.Item>
											)}
										</ClayDropDown.ItemList>
									</ClayDropDown>
								</li>
							</ul>
						</div>
					)}
				</div>
			</div>
			<div className="sidebar-body">
				<div>
					{accountData.logoId ? (
						<img
							alt={Liferay.Language.get('image')}
							className="logo-selector-img mb-3"
							src={accountData.logoURL}
						/>
					) : (
						<svg className="logo-selector-default-img mb-3">
							<use
								href={`${spritemap}#${SYMBOLS_MAP[type]}`}
							></use>
						</svg>
					)}
				</div>

				<div>
					<div className="sidebar-dt">
						{Liferay.Language.get('image')}
					</div>

					<div className="sidebar-dd">
						{!accountData.logoId
							? Liferay.Language.get('default')
							: sub(
									Liferay.Language.get('custom-x'),
									Liferay.Language.get('image')
								)}
					</div>
				</div>

				<div>
					<div className="sidebar-dt">
						{Liferay.Language.get('account-name')}
					</div>

					<div className="sidebar-dd">{accountData.name || '-'}</div>
				</div>

				<div>
					<div className="sidebar-dt">
						{Liferay.Language.get('type')}
					</div>

					<div className="sidebar-dd">
						{localizeModelType(accountData.modelType)}
					</div>
				</div>

				<div>
					<div className="sidebar-dt">
						{Liferay.Language.get('tax-id')}
					</div>

					<div className="sidebar-dd">{accountData.taxId || '-'}</div>
				</div>

				<div>
					<div className="sidebar-dt">
						{Liferay.Language.get('external-reference-code')}
					</div>

					<div className="sidebar-dd">
						{accountData.externalReferenceCode || '-'}
					</div>
				</div>

				<div>
					<div className="sidebar-dt">
						{Liferay.Language.get('account-id')}
					</div>

					<div className="sidebar-dd">{accountData.id}</div>
				</div>

				<div>
					<div className="sidebar-dt">
						{Liferay.Language.get('description')}
					</div>

					<div className="sidebar-dd">
						{accountData.description || '-'}
					</div>
				</div>

				{Liferay.FeatureFlags['COMMERCE-13024'] &&
				accountData.fullLoaded ? (
					<FieldsWrapper
						mode="view"
						namespace={namespace}
						objectData={accountData}
						objectExternalReferenceCode="L_ACCOUNT"
					></FieldsWrapper>
				) : (
					<></>
				)}
			</div>
		</>
	);
}

ViewAccountInfoPanel.defaultProps = {
	type: MODEL_TYPE_MAP.account,
};

ViewAccountInfoPanel.propTypes = {
	closePanelViewHandler: PropTypes.func.isRequired,
	data: PropTypes.object.isRequired,
	namespace: PropTypes.string,
	spritemap: PropTypes.string.isRequired,
	type: PropTypes.string.isRequired,
	updatePanelViewHandler: PropTypes.func.isRequired,
};

export default ViewAccountInfoPanel;

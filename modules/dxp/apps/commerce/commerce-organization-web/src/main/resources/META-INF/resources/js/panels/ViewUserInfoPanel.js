/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {openConfirmModal, openToast, sub} from 'frontend-js-web';
import moment from 'moment';
import PropTypes from 'prop-types';
import React, {
	useCallback,
	useContext,
	useEffect,
	useRef,
	useState,
} from 'react';

import ChartContext from '../ChartContext';
import {deleteUser, getUser, getUserFullNameDefinition} from '../data/users';
import FieldsWrapper from '../objects/FieldsWrapper';
import {
	ACTION_KEYS,
	DEFAULT_USER_ACCOUNT_FULL_NAME_DEFINITION_FIELDS,
	INFO_PANEL_MODE_MAP,
	MODEL_TYPE_MAP,
	SYMBOLS_MAP,
} from '../utils/constants';
import {hasPermission} from '../utils/index';

function ViewUserInfoPanel({
	closePanelViewHandler,
	data,
	namespace,
	spritemap,
	type,
	updatePanelViewHandler,
}) {
	const [userData, setUserData] = useState(data);
	const {chartInstanceRef} = useContext(ChartContext);
	const [userLanguageId] = useState(data.languageId);
	const [fullNameDefinition, setFullNameDefinition] = useState([]);

	const momentLocaleFormatRef = useRef(
		moment()
			.locale(Liferay.ThemeDisplay.getLanguageId())
			.localeData()
			.longDateFormat('L')
	);

	useEffect(() => {
		getUserFullNameDefinition(userLanguageId).then((data) => {
			setFullNameDefinition(
				data?.userAccountFullNameDefinitionFields ||
					DEFAULT_USER_ACCOUNT_FULL_NAME_DEFINITION_FIELDS
			);
		});
	}, [userLanguageId]);

	useEffect(() => {
		setUserData(data);
	}, [data]);

	useEffect(() => {
		if (!userData.id || userData.fullLoaded) {
			return;
		}

		getUser(userData.id)
			.then((newData) => {
				newData = Object.assign(userData, newData);
				newData.fullLoaded = true;
				newData.modelType = newData.type;
				newData.type = type;

				chartInstanceRef.current.updateNodeContent(newData);

				setUserData((prevState) => ({
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
	}, [userData.id]);

	const deleteHandler = useCallback(() => {
		openConfirmModal({
			message: sub(
				Liferay.Language.get('x-will-be-deleted'),
				userData.name
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					deleteUser(userData.id)
						.then(() => {
							chartInstanceRef.current.deleteNodes(
								[userData],
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
	}, [chartInstanceRef, closePanelViewHandler, userData]);

	const editHandler = useCallback(() => {
		updatePanelViewHandler({
			data: userData,
			mode: INFO_PANEL_MODE_MAP.edit,
			type,
		});
	}, [userData, type, updatePanelViewHandler]);

	const isFieldVisible = (key) => {
		return !!fullNameDefinition.find((item) => {
			return item.key === key;
		});
	};

	return (
		<>
			<div className="sidebar-header">
				<div className="autofit-row sidebar-section">
					<div className="autofit-col autofit-col-expand">
						<h1 className="component-title">
							{userData.alternateName}
						</h1>

						<h2 className="component-subtitle">
							{Liferay.Language.get('user')}
						</h2>
					</div>

					{(hasPermission(userData, ACTION_KEYS.user.UPDATE) ||
						hasPermission(userData, ACTION_KEYS.user.DELETE)) && (
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
												userData,
												ACTION_KEYS.user.UPDATE
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
												userData,
												ACTION_KEYS.user.DELETE
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
					<div className="sheet-subtitle">
						{Liferay.Language.get('user-display-data')}
					</div>

					<div>
						{userData.imageId ? (
							<img
								alt={Liferay.Language.get('image')}
								className="logo-selector-img mb-3"
								src={userData.image}
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
							{!userData.imageId
								? Liferay.Language.get('default')
								: sub(
										Liferay.Language.get('custom-x'),
										Liferay.Language.get('image')
									)}
						</div>
					</div>

					<div>
						<div className="sidebar-dt">
							{Liferay.Language.get('screen-name')}
						</div>

						<div className="sidebar-dd">
							{userData.alternateName || '-'}
						</div>
					</div>

					<div>
						<div className="sidebar-dt">
							{Liferay.Language.get('email-address')}
						</div>

						<div className="sidebar-dd">
							{userData.emailAddress || '-'}
						</div>
					</div>

					<div>
						<div className="sidebar-dt">
							{Liferay.Language.get('user-id')}
						</div>

						<div className="sidebar-dd">{userData.id || '-'}</div>
					</div>
				</div>

				<div>
					<div className="sheet-subtitle">
						{Liferay.Language.get('personal-information')}
					</div>

					<div>
						<div className="sidebar-dt">
							{Liferay.Language.get('language')}
						</div>

						<div className="sidebar-dd">
							{userData.languageDisplayName || '-'}
						</div>
					</div>

					{isFieldVisible('prefix') && (
						<div>
							<div className="sidebar-dt">
								{Liferay.Language.get('prefix')}
							</div>

							<div className="sidebar-dd">
								{userData.honorificPrefix || '-'}
							</div>
						</div>
					)}

					{isFieldVisible('first-name') && (
						<div>
							<div className="sidebar-dt">
								{Liferay.Language.get('first-name')}
							</div>

							<div className="sidebar-dd">
								{userData.givenName || '-'}
							</div>
						</div>
					)}

					{isFieldVisible('middle-name') && (
						<div>
							<div className="sidebar-dt">
								{Liferay.Language.get('middle-name')}
							</div>

							<div className="sidebar-dd">
								{userData.additionalName || '-'}
							</div>
						</div>
					)}

					{isFieldVisible('last-name') && (
						<div>
							<div className="sidebar-dt">
								{Liferay.Language.get('last-name')}
							</div>

							<div className="sidebar-dd">
								{userData.familyName || '-'}
							</div>
						</div>
					)}

					{isFieldVisible('suffix') && (
						<div>
							<div className="sidebar-dt">
								{Liferay.Language.get('suffix')}
							</div>

							<div className="sidebar-dd">
								{userData.honorificSuffix || '-'}
							</div>
						</div>
					)}

					<div>
						<div className="sidebar-dt">
							{Liferay.Language.get('job-title')}
						</div>

						<div className="sidebar-dd">
							{userData.jobTitle || '-'}
						</div>
					</div>

					<div>
						<div className="sidebar-dt">
							{Liferay.Language.get('birthday')}
						</div>

						<div className="sidebar-dd">
							{moment(userData.birthDate).format(
								momentLocaleFormatRef.current
							) || '-'}
						</div>
					</div>
				</div>

				{Liferay.FeatureFlags['COMMERCE-13024'] &&
				userData.fullLoaded ? (
					<FieldsWrapper
						mode="view"
						namespace={namespace}
						objectData={userData}
						objectExternalReferenceCode="L_USER"
					></FieldsWrapper>
				) : (
					<></>
				)}
			</div>
		</>
	);
}

ViewUserInfoPanel.defaultProps = {
	type: MODEL_TYPE_MAP.account,
};

ViewUserInfoPanel.propTypes = {
	closePanelViewHandler: PropTypes.func.isRequired,
	data: PropTypes.object.isRequired,
	namespace: PropTypes.string,
	spritemap: PropTypes.string.isRequired,
	type: PropTypes.string.isRequired,
	updatePanelViewHandler: PropTypes.func.isRequired,
};

export default ViewUserInfoPanel;

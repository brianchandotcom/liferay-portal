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
import {deleteUser, getUserFullNameDefinition} from '../data/users';
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
	spritemap,
	type,
	updatePanelViewHandler,
}) {
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

	const deleteHandler = useCallback(() => {
		openConfirmModal({
			message: sub(Liferay.Language.get('x-will-be-deleted'), data.name),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					deleteUser(data.id)
						.then(() => {
							chartInstanceRef.current.deleteNodes([data], true);

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
	}, [chartInstanceRef, closePanelViewHandler, data]);

	const editHandler = useCallback(() => {
		updatePanelViewHandler({
			data,
			mode: INFO_PANEL_MODE_MAP.edit,
			type,
		});
	}, [data, type, updatePanelViewHandler]);

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
							{data.alternateName}
						</h1>

						<h2 className="component-subtitle">
							{Liferay.Language.get('user')}
						</h2>
					</div>

					{(hasPermission(data, ACTION_KEYS.user.UPDATE) ||
						hasPermission(data, ACTION_KEYS.user.DELETE)) && (
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
												data,
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
												data,
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
						{data.imageId ? (
							<img
								alt={Liferay.Language.get('image')}
								className="logo-selector-img mb-3"
								src={data.image}
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
							{!data.imageId
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
							{data.alternateName || '-'}
						</div>
					</div>

					<div>
						<div className="sidebar-dt">
							{Liferay.Language.get('email-address')}
						</div>

						<div className="sidebar-dd">
							{data.emailAddress || '-'}
						</div>
					</div>

					<div>
						<div className="sidebar-dt">
							{Liferay.Language.get('user-id')}
						</div>

						<div className="sidebar-dd">{data.id || '-'}</div>
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

						<div className="sidebar-dd">{data.language || '-'}</div>
					</div>

					{isFieldVisible('prefix') && (
						<div>
							<div className="sidebar-dt">
								{Liferay.Language.get('prefix')}
							</div>

							<div className="sidebar-dd">
								{data.honorificPrefix || '-'}
							</div>
						</div>
					)}

					{isFieldVisible('first-name') && (
						<div>
							<div className="sidebar-dt">
								{Liferay.Language.get('first-name')}
							</div>

							<div className="sidebar-dd">
								{data.givenName || '-'}
							</div>
						</div>
					)}

					{isFieldVisible('middle-name') && (
						<div>
							<div className="sidebar-dt">
								{Liferay.Language.get('middle-name')}
							</div>

							<div className="sidebar-dd">
								{data.additionalName || '-'}
							</div>
						</div>
					)}

					{isFieldVisible('last-name') && (
						<div>
							<div className="sidebar-dt">
								{Liferay.Language.get('last-name')}
							</div>

							<div className="sidebar-dd">
								{data.familyName || '-'}
							</div>
						</div>
					)}

					{isFieldVisible('suffix') && (
						<div>
							<div className="sidebar-dt">
								{Liferay.Language.get('suffix')}
							</div>

							<div className="sidebar-dd">
								{data.honorificSuffix || '-'}
							</div>
						</div>
					)}

					<div>
						<div className="sidebar-dt">
							{Liferay.Language.get('job-title')}
						</div>

						<div className="sidebar-dd">{data.jobTitle || '-'}</div>
					</div>

					<div>
						<div className="sidebar-dt">
							{Liferay.Language.get('birthday')}
						</div>

						<div className="sidebar-dd">
							{moment(data.birthDate).format(
								momentLocaleFormatRef.current
							) || '-'}
						</div>
					</div>
				</div>
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
	spritemap: PropTypes.string.isRequired,
	type: PropTypes.string.isRequired,
	updatePanelViewHandler: PropTypes.func.isRequired,
};

export default ViewUserInfoPanel;

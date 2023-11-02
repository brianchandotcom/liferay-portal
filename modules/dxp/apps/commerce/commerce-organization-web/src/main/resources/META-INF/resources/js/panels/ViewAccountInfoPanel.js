/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {openConfirmModal, openToast, sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useContext} from 'react';

import ChartContext from '../ChartContext';
import {deleteAccount} from '../data/accounts';
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
	spritemap,
	type,
	updatePanelViewHandler,
}) {
	const {chartInstanceRef} = useContext(ChartContext);

	const deleteHandler = useCallback(() => {
		openConfirmModal({
			message: sub(Liferay.Language.get('x-will-be-deleted'), data.name),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					deleteAccount(data.id)
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

					{(hasPermission(data, ACTION_KEYS.account.DELETE) ||
						hasPermission(data, ACTION_KEYS.account.UPDATE)) && (
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
												data,
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
					{data.logoId ? (
						<img
							alt={Liferay.Language.get('image')}
							className="logo-selector-img mb-3"
							src={data.logoURL}
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
						{!data.logoId
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

					<div className="sidebar-dd">{data.name || '-'}</div>
				</div>

				<div>
					<div className="sidebar-dt">
						{Liferay.Language.get('type')}
					</div>

					<div className="sidebar-dd">
						{localizeModelType(data.modelType)}
					</div>
				</div>

				<div>
					<div className="sidebar-dt">
						{Liferay.Language.get('tax-id')}
					</div>

					<div className="sidebar-dd">{data.taxId || '-'}</div>
				</div>

				<div>
					<div className="sidebar-dt">
						{Liferay.Language.get('external-reference-code')}
					</div>

					<div className="sidebar-dd">
						{data.externalReferenceCode || '-'}
					</div>
				</div>

				<div>
					<div className="sidebar-dt">
						{Liferay.Language.get('account-id')}
					</div>

					<div className="sidebar-dd">{data.id}</div>
				</div>

				<div>
					<div className="sidebar-dt">
						{Liferay.Language.get('description')}
					</div>

					<div className="sidebar-dd">{data.description || '-'}</div>
				</div>
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
	spritemap: PropTypes.string.isRequired,
	type: PropTypes.string.isRequired,
	updatePanelViewHandler: PropTypes.func.isRequired,
};

export default ViewAccountInfoPanel;

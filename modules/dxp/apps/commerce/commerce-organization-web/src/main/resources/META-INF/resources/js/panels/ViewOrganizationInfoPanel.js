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
import {deleteOrganization} from '../data/organizations';
import {
	ACTION_KEYS,
	INFO_PANEL_MODE_MAP,
	MODEL_TYPE_MAP,
	SYMBOLS_MAP,
} from '../utils/constants';
import {hasPermission} from '../utils/index';

function ViewOrganizationInfoPanel({
	closePanelViewHandler,
	data,
	spritemap,
	type,
	updatePanelViewHandler,
}) {
	const {chartInstanceRef} = useContext(ChartContext);
	const [organizationData, setOrganizationData] = useState(data);

	useEffect(() => {
		setOrganizationData(data);
	}, [data]);

	const deleteHandler = useCallback(() => {
		openConfirmModal({
			message: sub(
				Liferay.Language.get('x-will-be-deleted'),
				organizationData.name
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					deleteOrganization(organizationData.id)
						.then(() => {
							chartInstanceRef.current.deleteNodes(
								[organizationData],
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
	}, [chartInstanceRef, closePanelViewHandler, organizationData]);

	const editHandler = useCallback(() => {
		updatePanelViewHandler({
			data: organizationData,
			mode: INFO_PANEL_MODE_MAP.edit,
			type,
		});
	}, [organizationData, type, updatePanelViewHandler]);

	return (
		<>
			<div className="sidebar-header">
				<div className="autofit-row sidebar-section">
					<div className="autofit-col autofit-col-expand">
						<h1 className="component-title">
							{organizationData.name}
						</h1>

						<h2 className="component-subtitle">
							{Liferay.Language.get('organization')}
						</h2>
					</div>

					{(hasPermission(
						organizationData,
						ACTION_KEYS.organization.UPDATE
					) ||
						(!organizationData.isRootNode &&
							hasPermission(
								organizationData,
								ACTION_KEYS.organization.DELETE
							))) && (
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
												organizationData,
												ACTION_KEYS.organization.UPDATE
											) && (
												<ClayDropDown.Item
													onClick={editHandler}
												>
													{Liferay.Language.get(
														'edit'
													)}
												</ClayDropDown.Item>
											)}

											{!organizationData.isRootNode &&
												hasPermission(
													organizationData,
													ACTION_KEYS.organization
														.DELETE
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
					<div>
						{organizationData.imageId ? (
							<img
								alt={Liferay.Language.get('image')}
								className="logo-selector-img mb-3"
								src={organizationData.image}
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
							{!organizationData.imageId
								? Liferay.Language.get('default')
								: sub(
										Liferay.Language.get('custom-x'),
										Liferay.Language.get('image')
									)}
						</div>
					</div>

					<div>
						<div className="sidebar-dt">
							{Liferay.Language.get('name')}
						</div>

						<div className="sidebar-dd">
							{organizationData.name || '-'}
						</div>
					</div>

					<div>
						<div className="sidebar-dt">
							{Liferay.Language.get('type-label')}
						</div>

						<div className="sidebar-dd">
							{Liferay.Language.get('organization')}
						</div>
					</div>

					<div>
						<div className="sidebar-dt">
							{Liferay.Language.get('country')}
						</div>

						<div className="sidebar-dd">
							{organizationData.location?.addressCountry || '-'}
						</div>
					</div>

					<div>
						<div className="sidebar-dt">
							{Liferay.Language.get('region')}
						</div>

						<div className="sidebar-dd">
							{organizationData.location?.addressRegion || '-'}
						</div>
					</div>
				</div>
			</div>
		</>
	);
}

ViewOrganizationInfoPanel.defaultProps = {
	type: MODEL_TYPE_MAP.organization,
};

ViewOrganizationInfoPanel.propTypes = {
	closePanelViewHandler: PropTypes.func.isRequired,
	data: PropTypes.object.isRequired,
	spritemap: PropTypes.string.isRequired,
	type: PropTypes.string.isRequired,
	updatePanelViewHandler: PropTypes.func.isRequired,
};

export default ViewOrganizationInfoPanel;

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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';
import {fetch} from 'frontend-js-web';

import UserIcon from './UserIcon.es';

const ManageCollaborators = ({collaboratorsResourceURL, portletNamespace}) => {
	const [data, setData] = useState(null);

	useEffect(() => {
		fetch(collaboratorsResourceURL)
			.then(res => res.json())
			.then(setData);
	}, [collaboratorsResourceURL]);

	const handleClick = () => {
		Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true,
				height: 470,
				on: {
					visibleChange: event => {
						if (!event.newVal) {
							// TODO refresh collaborators
						}
					}
				},
				width: 600
			},
			id: `${portletNamespace}manageCollaboratorsDialog`,
			title: Liferay.Language.get('collaborators'),
			uri: data.manageCollaboratorsURL
		});
	};

	if (!data) return <></>;

	const {owner, total, manageCollaboratorsURL, collaborators} = data;

	const moreCollaboratorsCount = total - collaborators.length;

	return (
		<>
			<div className="autofit-row manage-collaborators sidebar-panel">
				<div className="autofit-col manage-collaborators-owner">
					<div
						className="lfr-portal-tooltip"
						data-title={Liferay.Util.sub(
							Liferay.Language.get('x-is-the-owner'),
							owner.fullName
						)}
					>
						<UserIcon {...owner} size="" />
					</div>
				</div>

				<div className="autofit-col autofit-col-expand">
					<div className="autofit-row">
						{collaborators.map(collaborator => (
							<div
								className="autofit-col manage-collaborators-collaborator"
								key={collaborator.userId}
							>
								<div
									className="lfr-portal-tooltip"
									data-title={collaborator.fullName}
								>
									<UserIcon {...collaborator} size="" />
								</div>
							</div>
						))}
						{moreCollaboratorsCount > 0 && (
							<div className="autofit-col manage-collaborators-collaborator">
								<div
									className="lfr-portal-tooltip"
									data-title={
										moreCollaboratorsCount == 1
											? Liferay.Util.sub(
													Liferay.Language.get(
														'x-more-collaborator'
													),
													moreCollaboratorsCount
											  )
											: Liferay.Util.sub(
													Liferay.Language.get(
														'x-more-collaborators'
													),
													moreCollaboratorsCount
											  )
									}
								>
									<ClaySticker
										className={`sticker-use-icon user-icon-color-0`}
										displayType="secondary"
										shape="circle"
									>
										<ClayIcon symbol="users" />
									</ClaySticker>
								</div>
							</div>
						)}
					</div>
				</div>
			</div>
			{manageCollaboratorsURL && (
				<div className="autofit-row sidebar-panel">
					<ClayButton
						className="btn-link manage-collaborators-btn"
						displayType="link"
						onClick={handleClick}
						small
					>
						{Liferay.Language.get('manage-collaborators')}
					</ClayButton>
				</div>
			)}
		</>
	);
};

ManageCollaborators.propTypes = {
	collaboratorsURL: PropTypes.string,
	portletNamespace: PropTypes.string.isRequired
};

export default ManageCollaborators;

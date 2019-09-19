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
import React from 'react';

import UserIcon from './UserIcon.es';

const ManageCollaborators = ({
	manageCollaboratorsRenderURL,
	owner,
	portletNamespace,
	sharingEntriesCount,
	sharingEntryToUsers,
	showManageCollaborators = false
}) => {
	const moreCollaboratorsCount = sharingEntriesCount - 4;

	const handleClick = () => {
		Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true,
				height: 470,
				width: 600,
				on: {
					visibleChange: event => {
						if (!event.newVal) {
							// TODO:
							console.log('refresh collaborators!!');
						}
					}
				}
			},
			id: `${portletNamespace}manageCollaboratorsDialog`,
			title: Liferay.Language.get('collaborators'),
			uri: manageCollaboratorsRenderURL
		});
	};

	return (
		<>
			<div className="autofit-row manage-collaborators sidebar-panel">
				<div className="autofit-col manage-collaborators-owner">
					<div
						className="lfr-portal-tooltip"
						data-title={Liferay.Util.sub(
							Liferay.Language.get('x-is-the-owner'),
							// TODO owner.fullname
							`${owner.firstName} ${owner.lastName}`
						)}
					>
						<UserIcon {...owner} size="" />
					</div>
				</div>

				<div className="autofit-col autofit-col-expand">
					<div className="autofit-row">
						{sharingEntryToUsers.map(sharingEntryToUser => (
							<div
								className="autofit-col manage-collaborators-collaborator"
								key={sharingEntryToUser.userId}
							>
								<div
									className="lfr-portal-tooltip"
									// TODO sharingEntryToUser.fullname
									data-title={`${sharingEntryToUser.firstName} ${sharingEntryToUser.lastName}`}
								>
									<UserIcon {...sharingEntryToUser} size="" />
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
			{showManageCollaborators && (
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
	manageCollaboratorsRenderURL: PropTypes.string,
	owner: PropTypes.shape({
		fullName: PropTypes.string.isRequired,
		portraitURL: PropTypes.string,
		userId: PropTypes.string.isRequired
	}).isRequired,
	portletNamespace: PropTypes.string.isRequired,
	sharingEntriesCount: PropTypes.number.isRequired,
	sharingEntryToUsers: PropTypes.arrayOf(
		PropTypes.shape({
			fullName: PropTypes.string.isRequired,
			portraitURL: PropTypes.string,
			userId: PropTypes.string.isRequired
		})
	).isRequired,
	showManageCollaborators: PropTypes.bool
};

export default ManageCollaborators;

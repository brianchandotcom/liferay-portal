/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClaySticker from '@clayui/sticker';
import {fetch, sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import UserIcon from './UserIcon.es';

const Collaborators = ({
	canManageCollaborators,
	classNameId,
	classPK,
	collaboratorsResourceURL,
	initialData,
}) => {
	const [data, setData] = useState(initialData);

	useEffect(() => {
		Liferay.on('sharing:changed', (event) => {
			if (
				classNameId === event.classNameId &&
				event.classPK === classPK
			) {
				fetch(collaboratorsResourceURL)
					.then((res) => res.json())
					.then(setData);
			}
		});
	}, [classNameId, classPK, collaboratorsResourceURL]);

	const {collaborators, owner, total} = data;

	if (total < 1) {
		return (
			<ClayLayout.ContentRow className="sidebar-section" noGutters>
				<ClayLayout.ContentCol>
					<UserIcon {...owner} size="" />
				</ClayLayout.ContentCol>

				<ClayLayout.ContentCol expand>
					<div className="component-title username">
						{owner.displayURL ? (
							<a href={owner.displayURL}>{owner.fullName}</a>
						) : (
							owner.fullName
						)}
					</div>

					<div className="component-subtitle">
						{Liferay.Language.get('owner')}
					</div>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>
		);
	}

	const moreCollaboratorsCount = total - collaborators.length;

	return (
		<>
			<ClayLayout.ContentRow className="sidebar-section">
				<ClayLayout.ContentCol className="collaborators-owner">
					<div
						className="lfr-portal-tooltip"
						data-title={sub(
							Liferay.Language.get('x-is-the-owner'),
							owner.fullName
						)}
					>
						<UserIcon {...owner} size="" />
					</div>
				</ClayLayout.ContentCol>

				<ClayLayout.ContentCol expand>
					<ClayLayout.ContentRow>
						{collaborators.map((collaborator) => (
							<ClayLayout.ContentCol
								className="collaborators-collaborator"
								key={collaborator.userId}
							>
								<div
									className="lfr-portal-tooltip"
									data-title={collaborator.fullName}
								>
									<UserIcon {...collaborator} size="" />
								</div>
							</ClayLayout.ContentCol>
						))}

						{moreCollaboratorsCount > 0 && (
							<ClayLayout.ContentCol className="collaborators-collaborator">
								<div
									className="lfr-portal-tooltip"
									data-title={
										moreCollaboratorsCount === 1
											? sub(
													Liferay.Language.get(
														'x-more-collaborator'
													),
													moreCollaboratorsCount
											  )
											: sub(
													Liferay.Language.get(
														'x-more-collaborators'
													),
													moreCollaboratorsCount
											  )
									}
								>
									<ClaySticker
										className="sticker-use-icon user-icon-color-0"
										displayType="secondary"
										shape="circle"
									>
										<ClayIcon symbol="users" />
									</ClaySticker>
								</div>
							</ClayLayout.ContentCol>
						)}
					</ClayLayout.ContentRow>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			{canManageCollaborators && (
				<ClayLayout.ContentRow className="sidebar-section">
					<ClayButton
						className="btn-link collaborators-btn"
						displayType="link"
						onClick={() =>
							Liferay.Sharing.manageCollaborators(
								classNameId,
								classPK
							)
						}
						small
					>
						{Liferay.Language.get('manage-collaborators')}
					</ClayButton>
				</ClayLayout.ContentRow>
			)}
		</>
	);
};

const userShape = PropTypes.shape({
	displayURL: PropTypes.string,
	fullName: PropTypes.string.isRequired,
	portraitURL: PropTypes.string,
	userId: PropTypes.string.isRequired,
});

Collaborators.propTypes = {
	canManageCollaborators: PropTypes.bool,
	classNameId: PropTypes.string,
	classPK: PropTypes.string,
	collaboratorsResourceURL: PropTypes.string,
	initialData: PropTypes.shape({
		collaborators: PropTypes.arrayOf(userShape).isRequired,
		owner: userShape.isRequired,
		total: PropTypes.number.isRequired,
	}).isRequired,
};

export default Collaborators;

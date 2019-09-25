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
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClaySticker from '@clayui/sticker';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';
import {fetch} from 'frontend-js-web';

import UserIcon from './UserIcon.es';

const Collaborators = ({
	canManageCollaborators,
	classNameId,
	classPK,
	collaboratorsResourceURL
}) => {
	const [data, setData] = useState(null);

	const updateCollaborators = useCallback(() => {
		fetch(collaboratorsResourceURL)
			.then(res => res.json())
			.then(setData);
	}, [collaboratorsResourceURL]);

	useEffect(() => updateCollaborators(), [updateCollaborators]);

	useEffect(() => {
		Liferay.on('sharing:changed', event => {
			if (
				classNameId === event.classNameId &&
				event.classPK === classPK
			) {
				updateCollaborators();
			}
		});
	}, [classNameId, classPK, updateCollaborators]);

	if (!data) {
		return <ClayLoadingIndicator />;
	}

	const {owner, total, collaborators} = data;

	if (total < 1) {
		return null;
	}

	const moreCollaboratorsCount = total - collaborators.length;

	return (
		<>
			<div className="autofit-row sidebar-panel">
				<div className="autofit-col collaborators-owner">
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
								className="autofit-col collaborators-collaborator"
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
							<div className="autofit-col collaborators-collaborator">
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
			{canManageCollaborators && (
				<div className="autofit-row sidebar-panel">
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
				</div>
			)}
		</>
	);
};

Collaborators.propTypes = {
	canManageCollaborators: PropTypes.bool,
	classNameId: PropTypes.string,
	classPK: PropTypes.string,
	collaboratorsURL: PropTypes.string
};

export default Collaborators;

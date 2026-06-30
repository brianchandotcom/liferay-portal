/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CollaboratorService, openToast} from 'frontend-js-components-web';
import React from 'react';

import {COLLABORATOR_TYPE} from '../../../common/utils/constants';
import {openCMSModal} from '../../../common/utils/openCMSModal';
import CMSShareModalContent, {
	Collaborator,
} from '../../modal/share_modal_content/CMSShareModalContent';

export default async function shareAction({
	autocompleteURL,
	canManageCollaborators = true,
	collaboratorURL,
	creator,
	entryClassName,
	itemId,
	title,
}: {
	autocompleteURL: string;
	canManageCollaborators?: boolean;
	collaboratorURL: string;
	creator: {
		contentType: string;
		id: number;
		image?: string;
		name: string;
	};
	entryClassName: string;
	itemId: number;
	title: string;
}) {
	try {
		const items = await CollaboratorService.getCollaborators(
			collaboratorURL,
			itemId
		);

		const initialCollaborators: Collaborator[] = items
			.reverse()
			.map(
				({
					actionIds,
					dateExpired,
					emailAddress,
					id,
					name,
					portrait,
					share,
					type,
				}) => {
					const isExternalUser =
						type === COLLABORATOR_TYPE.EXTERNAL_USER;

					return {
						actionIds: isExternalUser
							? 'VIEW'
							: actionIds
									.filter(
										(actionId) => actionId !== 'DOWNLOAD'
									)
									.sort()
									.join(','),
						dateExpired,
						share,
						type,
						user: isExternalUser
							? {
									emailAddress: emailAddress ?? '',
									id: emailAddress ?? '',
									name: emailAddress ?? name,
								}
							: {
									id: id?.toString() ?? '',
									image: portrait,
									name,
								},
					} as Collaborator;
				}
			);

		openCMSModal({
			className: 'share-modal',
			contentComponent: ({closeModal}: {closeModal: () => void}) => (
				<CMSShareModalContent
					autocompleteURL={autocompleteURL}
					canManageCollaborators={canManageCollaborators}
					closeModal={closeModal}
					collaboratorURL={collaboratorURL}
					creator={{...creator, id: creator.id.toString()}}
					entryClassName={entryClassName}
					initialCollaborators={initialCollaborators}
					itemId={itemId}
					title={title}
				/>
			),
			size: 'md',
		});
	}
	catch (error: any) {
		openToast({
			message:
				error.message ||
				Liferay.Language.get('an-unexpected-error-occurred'),
			type: 'danger',
		});
	}
}

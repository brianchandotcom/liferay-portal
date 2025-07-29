/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';
import React from 'react';

import manageMembersAction, {
	ManageMembersData,
} from '../props_transformer/actions/manageMembersAction';

export enum SpaceSummaryHeaderActions {
	OPEN_MEMBERS_MODAL = 'open-members-modal',
}

export type SpaceSummaryHeaderPermissions = {
	canManageMembers: boolean;
};

type SpaceModalPropsType = {
	action: SpaceSummaryHeaderActions;
	assetLibraryCreatorUserId: string;
	assetLibraryId: string;
};

interface SpaceSummaryHeaderProps {
	label: string;
	permissions?: SpaceSummaryHeaderPermissions;
	spaceModalProps?: SpaceModalPropsType;
	title: string;
	url: string;
}

export default function SpaceSummaryHeader({
	label,
	permissions,
	spaceModalProps,
	title,
	url,
}: SpaceSummaryHeaderProps) {
	const openMembersModal = (props: SpaceModalPropsType) => {
		const {assetLibraryCreatorUserId, assetLibraryId} = props;

		const loadData = () => window.location.reload();
		const data: ManageMembersData = {
			assetLibraryCreatorUserId,
			assetLibraryId,
			canManageMembers: Boolean(permissions?.canManageMembers),
			title,
		};

		manageMembersAction(data, loadData);
	};

	const getActionCallback = () => {
		if (
			spaceModalProps?.action ===
			SpaceSummaryHeaderActions.OPEN_MEMBERS_MODAL
		) {
			return openMembersModal(spaceModalProps);
		}
	};

	return (
		<div className="align-items-center d-flex justify-content-between">
			<h2 className="font-weight-semi-bold m-0 text-4">{title}</h2>

			{spaceModalProps ? (
				<ClayButton
					displayType="link"
					onClick={getActionCallback}
					size="sm"
				>
					{label}
				</ClayButton>
			) : (
				<ClayLink href={url}>{label}</ClayLink>
			)}
		</div>
	);
}

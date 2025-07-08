/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';
import React from 'react';

import addMembersAction, { AddMembersData } from '../FDSPropsTransformer/actions/addMembersAction';

enum SpaceSummaryHeaderActions {
	OPEN_MEMBERS_MODAL = 'open-members-modal',
}

type SpaceMembersModalPropsType = {
	action: SpaceSummaryHeaderActions;
	assetLibraryCreatorUserId: string;
	assetLibraryId: string;
};

interface SpaceSummaryHeaderProps {
	label: string;
	spaceMembersModalProps?: SpaceMembersModalPropsType;
	title: string;
	url: string;
}

export default function SpaceSummaryHeader({
	label,
	spaceMembersModalProps,
	title,
	url,
}: SpaceSummaryHeaderProps) {
	const openMembersModal = (props: SpaceMembersModalPropsType) => {
		const {assetLibraryCreatorUserId, assetLibraryId } = props;

		const loadData = () => window.location.reload();
		const data: AddMembersData = {
				assetLibraryCreatorUserId,
				assetLibraryId,
				title
		}

		addMembersAction(data, loadData);
	};

	const getActionCallback = () => {
		if (
			spaceMembersModalProps?.action ===
			SpaceSummaryHeaderActions.OPEN_MEMBERS_MODAL
		) {
			return openMembersModal(spaceMembersModalProps);
		}
	};

	return (
		<div className="align-items-center d-flex justify-content-between">
			<h2 className="font-weight-semi-bold m-0 text-4">{title}</h2>

			{spaceMembersModalProps ? (
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

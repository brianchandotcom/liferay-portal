/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import React, {useState} from 'react';

import createAssetAction from '../props_transformer/actions/createAssetAction';
import createFolderAction from '../props_transformer/actions/createFolderAction';
import manageConnectedSitesAction, {
	ManageConnectedSitesData,
} from '../props_transformer/actions/manageConnectedSitesAction';
import manageMembersAction, {
	ManageMembersData,
} from '../props_transformer/actions/manageMembersAction';
import multipleFilesUploadAction from '../props_transformer/actions/multipleFilesUploadAction';
import addOnClickToCreationMenuItems from '../props_transformer/utils/addOnClickToCreationMenuItems';

const ACTIONS = {
	createAsset: createAssetAction,
	createFolder: createFolderAction,
	uploadMultipleFiles: multipleFilesUploadAction,
};

export enum SpaceSummaryHeaderActions {
	OPEN_MEMBERS_MODAL = 'open-members-modal',
	OPEN_SITES_MODAL = 'open-sites-modal',
}

export type SpaceSummaryHeaderPermissions = {
	hasAssignMembersPermission: boolean;
	hasConnectSitesPermission: boolean;
};

type SpaceModalPropsType = {
	action: SpaceSummaryHeaderActions;
	assetLibraryCreatorUserId: string;
	externalReferenceCode: string;
};

interface SpaceSummaryHeaderProps {
	creationMenu?: any;
	label: string;
	permissions?: SpaceSummaryHeaderPermissions;
	spaceModalProps?: SpaceModalPropsType;
	title: string;
	url: string;
}

export default function SpaceSummaryHeader({
	creationMenu,
	label,
	permissions,
	spaceModalProps,
	title,
	url,
}: SpaceSummaryHeaderProps) {
	const [active, setActive] = useState(false);

	const loadData = () => window.location.reload();

	const CreationMenu = () => {
		if (!creationMenu?.primaryItems?.length) {
			return null;
		}

		return (
			<ClayDropDown
				active={active}
				className="ml-2"
				onActiveChange={setActive}
				trigger={
					<ClayButtonWithIcon
						aria-label={`Add ${title}`}
						displayType="secondary"
						small
						symbol="plus"
						title={`Add ${title}`}
					/>
				}
			>
				<ClayDropDown.ItemList>
					{addOnClickToCreationMenuItems(
						creationMenu.primaryItems,
						ACTIONS
					).map((item: any, i: number) => (
						<ClayDropDown.Item
							key={i}
							onClick={() => {
								setActive(false);
								item.onClick({loadData});
							}}
						>
							{item.icon && (
								<span className="pr-2">
									<ClayIcon symbol={item.icon} />
								</span>
							)}

							{item.label}
						</ClayDropDown.Item>
					))}
				</ClayDropDown.ItemList>
			</ClayDropDown>
		);
	};

	const openMembersModal = (props: SpaceModalPropsType) => {
		const {assetLibraryCreatorUserId, externalReferenceCode} = props;

		const data: ManageMembersData = {
			assetLibraryCreatorUserId,
			externalReferenceCode,
			hasAssignMembersPermission: Boolean(
				permissions?.hasAssignMembersPermission
			),
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
		else if (
			spaceModalProps?.action ===
			SpaceSummaryHeaderActions.OPEN_SITES_MODAL
		) {
			const data: ManageConnectedSitesData = {
				externalReferenceCode: spaceModalProps.externalReferenceCode,
				hasConnectSitesPermission: Boolean(
					permissions?.hasConnectSitesPermission
				),
			};

			return manageConnectedSitesAction(data, loadData);
		}
	};

	return (
		<div className="align-items-center d-flex justify-content-between">
			<h2 className="font-weight-semi-bold m-0 text-4">{title}</h2>

			<div className="align-items-center d-flex">
				{url ? (
					<ClayLink
						className="text-3 text-weight-semi-bold"
						href={url}
					>
						{label}
					</ClayLink>
				) : (
					<ClayButton
						className="text-3 text-weight-semi-bold"
						displayType="link"
						onClick={getActionCallback}
						size="sm"
					>
						{label}
					</ClayButton>
				)}

				<CreationMenu />
			</div>
		</div>
	);
}

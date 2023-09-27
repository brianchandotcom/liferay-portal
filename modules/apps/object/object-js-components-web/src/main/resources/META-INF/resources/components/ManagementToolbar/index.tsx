/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayManagementToolbar from '@clayui/management-toolbar';
import classNames from 'classnames';
import {navigate, sub} from 'frontend-js-web';
import React, {useState} from 'react';

import './index.scss';
import {NotificationTemplate} from '../../utils/api';
import {ModalEditExternalReferenceCode} from './ModalEditExternalReferenceCode';

export type Entity = NotificationTemplate | ObjectDefinition;

interface ManagementToolbarProps {
	backURL: string;
	badgeClassName?: string;
	badgeLabel?: string;
	className?: string;
	enableBoxShadow?: boolean;
	entityId: number;
	externalReferenceCode: string;
	externalReferenceCodeSaveURL: string;
	hasPublishPermission: boolean;
	hasUpdatePermission: boolean;
	helpMessage: string;
	isApproved?: boolean;
	isRootDescendantNode?: boolean;
	label: string;
	onExternalReferenceCodeChange?: (value: string) => void;
	onGetEntity: () => Promise<Entity>;
	onSubmit: (props: boolean) => void;
	portletNamespace: string;
	screenNavigationCategoryKey?: string;
	showEntityDetails?: boolean;
}

export function ManagementToolbar({
	backURL,
	badgeClassName,
	badgeLabel,
	className,
	enableBoxShadow = true,
	entityId,
	externalReferenceCode: initialExternalReferenceCode,
	externalReferenceCodeSaveURL,
	hasPublishPermission,
	hasUpdatePermission,
	helpMessage,
	isApproved,
	isRootDescendantNode,
	label,
	onExternalReferenceCodeChange,
	onGetEntity,
	onSubmit,
	portletNamespace,
	screenNavigationCategoryKey,
	showEntityDetails = true,
}: ManagementToolbarProps) {
	const [externalReferenceCode, setExternalReferenceCode] = useState(
		initialExternalReferenceCode
	);
	const [visibleModal, setVisibleModal] = useState<boolean>(false);

	const [disabled, setDisabled] = useState(!hasPublishPermission);

	const onPublish = () => {
		onSubmit(false);

		setDisabled(true);

		setTimeout(() => {
			setDisabled(false);
		}, 1000);
	};

	return (
		<>
			<ClayManagementToolbar
				className={classNames(
					`lfr__management-toolbar ${className}`,
					enableBoxShadow && 'lfr__management-toolbar--box-shadow'
				)}
			>
				<ClayManagementToolbar.ItemList>
					<div className="border-right ml-sm-2 mr-3 pr-3">
						<h3 className="mb-0 text-truncate">{label}</h3>

						{badgeLabel && (
							<strong className={`${badgeClassName} label`}>
								{badgeLabel}
							</strong>
						)}
					</div>

					{showEntityDetails && (
						<div>
							<div>
								<span className="text-secondary">
									{`${Liferay.Language.get('id')}:`}
								</span>

								<strong className="ml-2">{entityId}</strong>
							</div>

							<div className="mt-1">
								<span className="text-secondary">
									{`${Liferay.Language.get('erc')}:`}
								</span>

								<strong className="ml-2">
									{externalReferenceCode}
								</strong>

								<span
									className="ml-3 text-secondary"
									title={helpMessage}
								>
									<ClayIcon symbol="question-circle" />
								</span>

								<ClayButton
									aria-label={sub(
										Liferay.Language.get('edit-x'),
										Liferay.Language.get(
											'external-reference-code'
										)
									)}
									className="ml-3 p-0 text-secondary"
									displayType="unstyled"
									onClick={() => setVisibleModal(true)}
								>
									<ClayIcon symbol="pencil" />
								</ClayButton>
							</div>
						</div>
					)}
				</ClayManagementToolbar.ItemList>

				{(!screenNavigationCategoryKey ||
					screenNavigationCategoryKey === 'details') && (
					<ClayManagementToolbar.ItemList>
						<ClayButton.Group key={1} spaced>
							<ClayButton
								displayType="secondary"
								id={`${portletNamespace}cancel`}
								name="cancel"
								onClick={() => navigate(backURL)}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								disabled={!hasUpdatePermission}
								displayType={
									isApproved ||
									isApproved === undefined ||
									isRootDescendantNode
										? 'primary'
										: 'secondary'
								}
								id={`${portletNamespace}save`}
								name="save"
								onClick={() => onSubmit(true)}
							>
								{Liferay.Language.get('save')}
							</ClayButton>

							{isApproved !== undefined &&
								!isApproved &&
								!isRootDescendantNode && (
									<ClayButton
										disabled={
											!hasUpdatePermission || disabled
										}
										id={`${portletNamespace}publish`}
										name="publish"
										onClick={() => onPublish()}
									>
										{Liferay.Language.get('publish')}
									</ClayButton>
								)}
						</ClayButton.Group>
					</ClayManagementToolbar.ItemList>
				)}
			</ClayManagementToolbar>

			{visibleModal && (
				<ModalEditExternalReferenceCode
					externalReferenceCode={externalReferenceCode}
					handleOnClose={() => setVisibleModal(false)}
					helpMessage={helpMessage}
					onExternalReferenceCodeChange={
						onExternalReferenceCodeChange
					}
					onGetEntity={onGetEntity}
					saveURL={externalReferenceCodeSaveURL}
					setExternalReferenceCode={setExternalReferenceCode}
				/>
			)}
		</>
	);
}

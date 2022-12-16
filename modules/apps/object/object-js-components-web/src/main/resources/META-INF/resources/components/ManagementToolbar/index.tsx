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
import ClayManagementToolbar from '@clayui/management-toolbar';
import {useModal} from '@clayui/modal';
import {navigate} from 'frontend-js-web';
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
	entityId: number;
	externalReferenceCode: string;
	externalReferenceCodeSaveURL: string;
	hasPublishPermission: boolean;
	hasUpdatePermission: boolean;
	helpMessage: string;
	isApproved?: boolean;
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
	entityId,
	externalReferenceCode: initialExternalReferenceCode,
	externalReferenceCodeSaveURL,
	hasPublishPermission,
	hasUpdatePermission,
	helpMessage,
	isApproved,
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

	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	return (
		<>
			<ClayManagementToolbar
				className={`lfr__management-toolbar ${className}`}
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
									isApproved || isApproved === undefined
										? 'primary'
										: 'secondary'
								}
								id={`${portletNamespace}save`}
								name="save"
								onClick={() => onSubmit(true)}
							>
								{Liferay.Language.get('save')}
							</ClayButton>

							{isApproved !== undefined && !isApproved && (
								<ClayButton
									disabled={!hasPublishPermission}
									id={`${portletNamespace}publish`}
									name="publish"
									onClick={() => onSubmit(false)}
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
					helpMessage={helpMessage}
					observer={observer}
					onClose={onClose}
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

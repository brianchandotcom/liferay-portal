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
import {
	API,
	ModalEditExternalReferenceCode,
} from '@liferay/object-js-components-web';
import classNames from 'classnames';
import {navigate} from 'frontend-js-web';
import React, {useState} from 'react';

export default function ManagementToolbar({
	backURL,
	externalReferenceCode: initialExternalReferenceCode,
	hasPublishObjectPermission,
	hasUpdateObjectDefinitionPermission,
	isApproved,
	label,
	objectDefinitionId,
	portletNamespace,
	screenNavigationCategoryKey,
	system,
}: IProps) {
	const [externalReferenceCode, setExternalReferenceCode] = useState<string>(
		initialExternalReferenceCode
	);
	const [visibleModal, setVisibleModal] = useState<boolean>(false);

	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	const submitObjectDefinition = (draft: boolean) => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (!draft) {
			form?.querySelector(`#${portletNamespace}cmd`)?.setAttribute(
				'value',
				'publish'
			);
		}

		form?.querySelector(
			`#${portletNamespace}externalReferenceCode`
		)?.setAttribute('value', externalReferenceCode);

		(form as HTMLFormElement)?.submit();
	};

	return (
		<>
			<ClayManagementToolbar className="border-bottom">
				<ClayManagementToolbar.ItemList>
					<div className="border-right ml-sm-2 mr-3 pr-3">
						<h3 className="mb-0 text-truncate">{label}</h3>

						<strong
							className={classNames(
								system ? 'label-info' : 'label-warning',
								'label'
							)}
						>
							{system
								? Liferay.Language.get('system')
								: Liferay.Language.get('custom')}
						</strong>
					</div>

					<div>
						<div>
							<span className="text-secondary">
								{`${Liferay.Language.get('id')}:`}
							</span>

							<strong className="ml-2">
								{objectDefinitionId}
							</strong>
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
								title={Liferay.Language.get(
									'internal-key-to-reference-the-object-definition'
								)}
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
								disabled={!hasUpdateObjectDefinitionPermission}
								displayType="primary"
								id={`${portletNamespace}save`}
								name="save"
								onClick={() => submitObjectDefinition(true)}
							>
								{Liferay.Language.get('save')}
							</ClayButton>

							{!isApproved && (
								<ClayButton
									disabled={!hasPublishObjectPermission}
									id={`${portletNamespace}publish`}
									name="publish"
									onClick={() =>
										submitObjectDefinition(false)
									}
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
					getEntity={() =>
						API.getObjectDefinitionById(objectDefinitionId)
					}
					helpMessage={Liferay.Language.get(
						'internal-key-to-reference-the-object-definition'
					)}
					observer={observer}
					onClose={onClose}
					saveURL={`/o/object-admin/v1.0/object-definitions/${objectDefinitionId}`}
					setExternalReferenceCode={setExternalReferenceCode}
				/>
			)}
		</>
	);
}

interface IProps {
	backURL: string;
	externalReferenceCode: string;
	hasPublishObjectPermission: boolean;
	hasUpdateObjectDefinitionPermission: boolean;
	isApproved: boolean;
	label: string;
	objectDefinitionId: number;
	portletNamespace: string;
	screenNavigationCategoryKey: string;
	system: boolean;
}

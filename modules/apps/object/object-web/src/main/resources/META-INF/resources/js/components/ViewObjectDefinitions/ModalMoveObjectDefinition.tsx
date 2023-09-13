/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayEmptyState from '@clayui/empty-state';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClayModal, {ClayModalProvider, useModal} from '@clayui/modal';
import {
	API,
	ManagementToolbarSearch,
	filterArrayByQuery,
	getLocalizableLabel,
} from '@liferay/object-js-components-web';
import {ManagementToolbar} from 'frontend-js-components-web';
import {openToast, sub} from 'frontend-js-web';
import React, {useMemo, useState} from 'react';

import {defaultLanguageId} from '../../utils/constants';

import './ModalMoveObjectDefinition.scss';

interface ModalMoveObjectDefinitionProps {
	handleOnClose: () => void;
	objectDefinition: ObjectDefinition;
	objectFolders: ObjectFolder[];
	selectedObjectFolder: Partial<ObjectFolder>;
	setMoveObjectDefinition: (value: ObjectDefinition | null) => void;
}

export function ModalMoveObjectDefinition({
	handleOnClose,
	objectDefinition,
	objectFolders,
	selectedObjectFolder,
	setMoveObjectDefinition,
}: ModalMoveObjectDefinitionProps) {
	const [query, setQuery] = useState('');
	const [
		selectedObjectFolderExternalReferenceCode,
		setSelectedObjectFolderExternalReferenceCode,
	] = useState<string>('');
	const [error, setError] = useState<string>('');

	const {observer, onClose} = useModal({
		onClose: () => {
			setMoveObjectDefinition(null);
			handleOnClose();
		},
	});

	const filteredObjectFolders = objectFolders.filter(
		(item) =>
			item.externalReferenceCode !==
			selectedObjectFolder.externalReferenceCode
	);

	const modalObjectFolderItems = useMemo(() => {
		const filteredItems = filterArrayByQuery({
			array: filteredObjectFolders,
			query,
			str: 'label',
		});

		return query ? filteredItems : filteredObjectFolders;
	}, [query, filteredObjectFolders]);

	const handleMoveObject = async () => {
		const movedObjectDefinition: ObjectDefinition = {
			...objectDefinition,
			objectFolderExternalReferenceCode: selectedObjectFolderExternalReferenceCode,
		};

		try {
			await API.save({
				item: movedObjectDefinition,
				method: 'PATCH',
				url: `/o/object-admin/v1.0/object-definitions/${objectDefinition.id}`,
			});

			onClose();

			openToast({
				message: sub(
					Liferay.Language.get('x-was-moved-successfully'),
					`<strong>${getLocalizableLabel(
						defaultLanguageId,
						movedObjectDefinition.label,
						movedObjectDefinition.name
					)}</strong>`
				),
				type: 'success',
			});

			setTimeout(() => window.location.reload(), 1000);
		}
		catch (error) {
			setError((error as Error).message);
		}
	};

	return (
		<ClayModalProvider>
			<ClayModal observer={observer}>
				<ClayModal.Header>
					{`${Liferay.Language.get('move')} "${getLocalizableLabel(
						defaultLanguageId,
						objectDefinition?.label
					)}"`}
				</ClayModal.Header>

				<ClayModal.Body>
					{error && (
						<ClayAlert displayType="danger">{error}</ClayAlert>
					)}

					{!filteredObjectFolders.length ? (
						<p>
							{Liferay.Language.get(
								'it-is-not-possible-to-move-this-object-definition-because-there-are-no-object-folders-available'
							)}
						</p>
					) : (
						<>
							<ManagementToolbar.Container className="lfr-object__object-web-view-modal-move-object-definition-toolbar">
								<ManagementToolbar.ItemList expand>
									<ManagementToolbarSearch
										query={query}
										setQuery={setQuery}
									/>
								</ManagementToolbar.ItemList>
							</ManagementToolbar.Container>

							{!modalObjectFolderItems.length && query ? (
								<div className="lfr-object__object-web-view-modal-move-object-definition-empty-state">
									<ClayEmptyState
										description={Liferay.Language.get(
											'sorry,-no-results-were-found'
										)}
										title={Liferay.Language.get(
											'no-results-found'
										)}
									/>
								</div>
							) : (
								<ClayList className="lfr-object__object-web-view-modal-move-object-definition-list">
									{modalObjectFolderItems.map(
										({
											externalReferenceCode,
											label,
											name,
										}) => (
											<ClayList.Item
												action
												active={
													selectedObjectFolderExternalReferenceCode ===
													externalReferenceCode
												}
												className="cursor-pointer lfr-object__object-web-view-modal-move-object-definition-list-item"
												flex
												key={name}
												onClick={() => {
													setSelectedObjectFolderExternalReferenceCode(
														externalReferenceCode
													);
												}}
											>
												<div>
													<ClayIcon symbol="diagram" />

													<span className="lfr-object__object-web-view-modal-move-object-definition-list-item-label">
														{getLocalizableLabel(
															defaultLanguageId,
															label,
															name
														)}
													</span>
												</div>
											</ClayList.Item>
										)
									)}
								</ClayList>
							)}
						</>
					)}
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						!filteredObjectFolders.length ? (
							<ClayButton
								displayType="secondary"
								onClick={() => onClose()}
							>
								{Liferay.Language.get('close')}
							</ClayButton>
						) : (
							<ClayButton.Group key={1} spaced>
								<ClayButton
									displayType="secondary"
									onClick={() => onClose()}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>

								<ClayButton
									displayType="primary"
									onClick={() => handleMoveObject()}
									type="submit"
								>
									{Liferay.Language.get('move')}
								</ClayButton>
							</ClayButton.Group>
						)
					}
				/>
			</ClayModal>
		</ClayModalProvider>
	);
}

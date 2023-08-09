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
	foldersList: Folder[];
	handleOnClose: () => void;
	objectDefinition: ObjectDefinition;
	selectedFolder: Partial<Folder>;
	setMoveObjectDefinition: (value: ObjectDefinition | null) => void;
}

export function ModalMoveObjectDefinition({
	foldersList,
	handleOnClose,
	objectDefinition,
	selectedFolder,
	setMoveObjectDefinition,
}: ModalMoveObjectDefinitionProps) {
	const [query, setQuery] = useState('');
	const [selectedFolderERC, setSelectedFolderERC] = useState<string>('');
	const [error, setError] = useState<string>('');

	const {observer, onClose} = useModal({
		onClose: () => {
			setMoveObjectDefinition(null);
			handleOnClose();
		},
	});

	const filteredFoldersList = foldersList.filter(
		(item) =>
			item.externalReferenceCode !== selectedFolder.externalReferenceCode
	);

	const modalItems = useMemo(() => {
		const filteredItems = filterArrayByQuery({
			array: filteredFoldersList,
			query,
			str: 'label',
		});

		return query ? filteredItems : filteredFoldersList;
	}, [query, filteredFoldersList]);

	const handleMoveObject = async () => {
		const movedObjectDefinition: ObjectDefinition = {
			...objectDefinition,
			objectFolderExternalReferenceCode: selectedFolderERC,
		};

		try {
			await API.save(
				`/o/object-admin/v1.0/object-definitions/${objectDefinition.id}`,
				movedObjectDefinition,
				'PATCH'
			);

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

					{!filteredFoldersList.length ? (
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

							{!modalItems.length && query ? (
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
									{modalItems.map(
										({
											externalReferenceCode,
											label,
											name,
										}) => (
											<ClayList.Item
												action
												active={
													selectedFolderERC ===
													externalReferenceCode
												}
												className="cursor-pointer lfr-object__object-web-view-modal-move-object-definition-list-item"
												flex
												key={name}
												onClick={() => {
													setSelectedFolderERC(
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
						!filteredFoldersList.length ? (
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

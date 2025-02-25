/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import Form, {ClayInput} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import React, {useEffect, useState} from 'react';

import {getAssetsLibrariesByCompany} from '../../api/api';

export default function CreationFolderModalContent({
	assetLibraryId,
	closeModal,
}: {
	assetLibraryId?: string;
	closeModal: voidReturn;
}) {
	const [assetLibraries, setAssetsLibraries] = useState<
		{id: string; name: string}[]
	>(assetLibraryId ? [{id: assetLibraryId, name: ''}] : []);
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		if (!assetLibraryId) {
			setLoading(true);

			getAssetsLibrariesByCompany().then((result: any) => {
				setAssetsLibraries(result);
				setLoading(false);
			});
		}
	}, [assetLibraryId]);

	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('new-folder')}
			</ClayModal.Header>
			<ClayModal.Body>
				{loading ? (
					<div className="loader-container">
						<ClayLoadingIndicator />
					</div>
				) : (
					<>
						<Form.Group>
							<label htmlFor="folderName">
								{Liferay.Language.get('name')}
							</label>

							<ClayInput id="folderName" type="text" />
						</Form.Group>

						{assetLibraries.length === 1 ? (
							<input type="hidden" value={assetLibraries[0].id} />
						) : (
							<Form.Group>
								<label htmlFor="space">
									{Liferay.Language.get('space')}
								</label>

								<Picker id="space" items={assetLibraries}>
									{({id, name}) => (
										<Option key={id}>{name}</Option>
									)}
								</Picker>
							</Form.Group>
						)}
					</>
				)}
			</ClayModal.Body>
			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={closeModal}
							type="button"
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton displayType="primary" type="button">
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}

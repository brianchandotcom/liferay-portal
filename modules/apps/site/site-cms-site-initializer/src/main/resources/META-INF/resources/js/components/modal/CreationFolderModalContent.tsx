/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import React, {useEffect, useState} from 'react';

import {getAssetsLibrariesByCompany} from '../../api/api';
import {FieldPicker, FieldText} from '../forms/';

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
						<FieldText
							label={Liferay.Language.get('name')}
							name="folderName"
							required
						/>

						{assetLibraries.length === 1 ? (
							<input type="hidden" value={assetLibraries[0].id} />
						) : (
							<FieldPicker
								helpMessage={Liferay.Language.get(
									'choose-the-space-for-the-new-folder'
								)}
								items={assetLibraries.map(({id, name}) => ({
									label: name,
									value: id,
								}))}
								label={Liferay.Language.get('space')}
								name="folderName"
								placeholder={Liferay.Language.get(
									'select-a-space'
								)}
								required
							/>
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

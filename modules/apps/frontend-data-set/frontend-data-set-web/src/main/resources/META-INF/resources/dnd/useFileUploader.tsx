/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';
import React, {useEffect, useState} from 'react';

import {TOnFileDrop} from '../DnDContext';
import getItemValueFromSelectedItemsKey from '../utils/getItemValueFromSelectedItemsKey';
import isFileDropEnabled from '../utils/isFileDropEnabled';
import {IFileDropSettings} from '../utils/types';

/* This hook connects FDS with state about dropped files, allowing integration
	with a file uploader component in the future. Current implementation
	shows a modal with the dropped files.
 */
const useFileUploader = ({
	fileDropSettings,
	selectedItemsKey,
}: {
	fileDropSettings: IFileDropSettings;
	selectedItemsKey: string | undefined;
}): {
	onFileDrop: TOnFileDrop;
} => {
	const [droppedFiles, setDroppedFiles] = useState([]);
	const [dropTarget, setDropTarget] = useState(null);

	const onFileDrop: TOnFileDrop = (droppedItem: any, dropTarget?: any) => {
		if (droppedItem) {

			// @ts-ignore

			const files = droppedItem.files;
			setDroppedFiles(files);
			setDropTarget(dropTarget ? dropTarget : null);
		}
	};

	useEffect(() => {
		if (!isFileDropEnabled(fileDropSettings) || !droppedFiles?.length) {
			return;
		}

		const ModalBody = () => {

			// @ts-ignore

			const label = (file) =>
				`'${file.name}' of size '${file.size}' and type '${file.type}'`;

			return (
				<div>
					{droppedFiles.map((file) => (

						// @ts-ignore

						<li key={file.name}>{label(file)}</li>
					))}

					{dropTarget ? (
						<span>
							Dropped on item{' '}

							{getItemValueFromSelectedItemsKey(
								dropTarget,
								selectedItemsKey || 'id'
							)}
						</span>
					) : (
						<span>Dropped on the FDS, no specific drop target</span>
					)}
				</div>
			);
		};

		openModal({
			bodyComponent: ModalBody,
			size: 'lg',
			title: Liferay.Language.get('files'),
		});
	}, [droppedFiles, dropTarget, fileDropSettings, selectedItemsKey]);

	return {onFileDrop};
};

export default useFileUploader;

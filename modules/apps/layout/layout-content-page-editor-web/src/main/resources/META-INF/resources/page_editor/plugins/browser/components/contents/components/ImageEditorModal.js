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

import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import {ImageEditor} from 'item-selector-taglib';
import PropTypes from 'prop-types';
import React from 'react';

import updatePreviewImage from '../../../../../app/actions/updatePreviewImage';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../../../../app/config/constants/editableTypes';
import {config} from '../../../../../app/config/index';
import {useDispatch} from '../../../../../app/contexts/StoreContext';
import FragmentService from '../../../../../app/services/FragmentService';
import ImageService from '../../../../../app/services/ImageService';

export default function ImageEditorModal({
	editImageURL,
	fileEntryId,
	fragmentEntryLinks,
	onCloseModal,
	previewURL,
}) {
	const dispatch = useDispatch();

	const {observer, onClose} = useModal({
		onClose: onCloseModal,
	});

	const onSave = (response) => {
		if (response?.success) {
			onClose();

			const fragmentsToUpdate = Object.values(fragmentEntryLinks).filter(
				(fragmentEntryLink) => {
					const editableValues = Object.entries(
						fragmentEntryLink.editableValues[
							EDITABLE_FRAGMENT_ENTRY_PROCESSOR
						]
					);

					return editableValues.some(
						([key, value]) =>
							fragmentEntryLink.editableTypes[key] ===
								EDITABLE_TYPES.image &&
							!fragmentEntryLink.removed &&
							(value.classPK === fileEntryId ||
								value[config.defaultLanguageId]?.classPK ===
									fileEntryId)
					);
				}
			);

			const getFileEntryPromise = ImageService.getFileEntry({
				fileEntryId,
			});

			const getFragmentEntryLinkContentPromises = fragmentsToUpdate.map(
				({fragmentEntryLinkId}) =>
					FragmentService.renderFragmentEntryLinkContent({
						fragmentEntryLinkId,
					}).then(({content}) => ({content, fragmentEntryLinkId}))
			);

			Promise.all([
				getFileEntryPromise,
				...getFragmentEntryLinkContentPromises,
			]).then(([{fileEntryURL}, ...contents]) => {
				dispatch(
					updatePreviewImage({
						contents,
						fileEntryId,
						previewURL: fileEntryURL,
					})
				);
			});
		}
	};

	return (
		<ClayModal
			className="image-editor-modal"
			observer={observer}
			size="full-screen"
		>
			<ClayModal.Header>
				{Liferay.Language.get('edit-image')}
			</ClayModal.Header>

			<ClayModal.Body>
				{previewURL ? (
					<ImageEditor
						imageId={fileEntryId}
						imageSrc={previewURL}
						onCancel={onClose}
						onSave={onSave}
						saveURL={editImageURL}
					/>
				) : (
					<ClayLoadingIndicator />
				)}
			</ClayModal.Body>
		</ClayModal>
	);
}

ImageEditorModal.propTypes = {
	editImageURL: PropTypes.string,
	fileEntryId: PropTypes.string,
	fragmentEntryLinks: PropTypes.object,
	onCloseModal: PropTypes.func,
	previewURLsRef: PropTypes.string,
};

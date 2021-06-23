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

import ClayModal, {useModal} from '@clayui/modal';
import {ImageEditor} from 'item-selector-taglib';
import PropTypes from 'prop-types';
import React from 'react';

import {updateFragmentEntryLinkContent} from '../../../../../app/actions/index';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../app/config/constants/editableFragmentEntryProcessor';
import {useDispatch} from '../../../../../app/contexts/StoreContext';
import FragmentService from '../../../../../app/services/FragmentService';

const getUpdatedImage = (content) => {
	const div = document.createElement('div');

	div.innerHTML = content;

	return div.getElementsByTagName('img')[0].src;
};

export default function ImageEditorModal({
	editImageURL,
	fileEntryId,
	fragmentEntryLinks,
	onCloseModal,
	previewURLsRef
}) {
	const dispatch = useDispatch();

	const {observer, onClose} = useModal({
		onClose: onCloseModal,
	});

	const onSave = (response) => {
		if (response?.success) {
			onClose();

			const fragmentsWithImage = Object.values(
				fragmentEntryLinks
			).filter((fragmentEntryLink) =>
				Object.values(
					fragmentEntryLink.editableValues[
						EDITABLE_FRAGMENT_ENTRY_PROCESSOR
					]
				).filter(
					(value) => value.classPK === fileEntryId && !value.removed
				)
			);

			fragmentsWithImage.forEach(({fragmentEntryLinkId}) => {
				FragmentService.renderFragmentEntryLinkContent({
					fragmentEntryLinkId,
				}).then(({content}) => {
					dispatch(
						updateFragmentEntryLinkContent({
							content,
							fragmentEntryLinkId,
						})
					);

					previewURLsRef.current = {
						...previewURLsRef.current,
						[fileEntryId]: getUpdatedImage(content),
					};
				});
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
				{previewURLsRef.current && (
					<ImageEditor
						imageId={fileEntryId}
						imageSrc={previewURLsRef.current[fileEntryId]}
						onCancel={onClose}
						onSave={onSave}
						saveURL={editImageURL}
					/>
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
	previewURLsRef: PropTypes.object,
};

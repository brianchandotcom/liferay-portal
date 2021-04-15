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
import {fetch, objectToFormData} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import ImageEditor from './ImageEditor';

export default ({editImageURL, portletNamespace}) => {
	const fileEntryIdRef = useRef();

	const [imageURL, setImageURL] = useState();
	const [showModal, setShowModal] = useState();

	const handleOnClose = () => {
		setShowModal(false);
	};

	const handleSaveButtonClick = (canvas) => {
		canvas.toBlob((blob) => {
			debugger;
			const formData = new FormData();

			formData.append('fileEntryId', fileEntryIdRef.current);
			formData.append('imageBlob', blob, fileEntryIdRef.current);

			fetch(editImageURL, {
				body: formData,
				method: 'POST',
			})
		});
	};

	const {observer} = useModal({
		onClose: handleOnClose,
	});

	useEffect(() => {
		window[`${portletNamespace}editWithImageEditor`] = ({
			fileEntryId,
			imageURL,
		}) => {
			fileEntryIdRef.current = fileEntryId;

			setImageURL(imageURL);
			setShowModal(true);
		};
	}, [portletNamespace]);

	return (
		<>
			{showModal && (
				<ClayModal
					className="image-editor-modal"
					observer={observer}
					size="full-screen"
				>
					<ClayModal.Header>
						{Liferay.Language.get('editing-image')}
					</ClayModal.Header>

					<ClayModal.Body>
						{imageURL && (
							<ImageEditor
								imageSrc={imageURL}
								onSave={handleSaveButtonClick}
							/>
						)}
					</ClayModal.Body>
				</ClayModal>
			)}
		</>
	);
};

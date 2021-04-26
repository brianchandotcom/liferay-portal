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
import {navigate} from 'frontend-js-web';
import {ImageEditor} from 'item-selector-taglib';
import React, {useEffect, useRef, useState} from 'react';

export default ({editImageURL, portletNamespace, redirectURL}) => {
	const fileEntryIdRef = useRef();

	const [imageURL, setImageURL] = useState();
	const [showModal, setShowModal] = useState();

	const handleOnClose = () => {
		setShowModal(false);
	};

	const handleSave = (response) => {
		if (response?.success) {
			navigate(redirectURL);
		}
	};

	const {observer, onClose} = useModal({
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

		return () => {
			window[`${portletNamespace}editWithImageEditor`] = () => {};
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
								imageId={fileEntryIdRef.current}
								imageSrc={imageURL}
								onCancel={onClose}
								onSave={handleSave}
								saveURL={editImageURL}
							/>
						)}
					</ClayModal.Body>
				</ClayModal>
			)}
		</>
	);
};

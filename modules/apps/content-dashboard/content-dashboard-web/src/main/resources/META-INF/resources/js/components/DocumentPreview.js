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

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClayModal, {useModal} from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const DocumentPreview = ({documentSrc, documentTitle, downloadURL}) => {
	const [showModal, setShowModal] = useState(false);

	const {observer} = useModal({
		onClose: () => setShowModal(false),
	});

	const handleShowModal = () => setShowModal(true);

	return (
		<>
			<div className="document-preview sidebar-section sidebar-section--spaced">
				<figure className="document-preview-figure">
					<img alt={documentTitle} src={documentSrc} />
					<ClayIcon
						className="document-preview-icon"
						onClick={handleShowModal}
						symbol="shortcut"
					/>
				</figure>
				<div>
					{downloadURL && (
						<ClayLink
							className="btn btn-secondary"
							href={downloadURL}
						>
							{Liferay.Language.get('download')}
						</ClayLink>
					)}
				</div>
			</div>
			{showModal && (
				<ClayModal observer={observer} size="full-screen">
					<ClayModal.Header>{documentTitle}</ClayModal.Header>
					<ClayModal.Body className="p-0">
						<figure className="h-100 m-0 text-center">
							<img
								alt={documentTitle}
								className="h-100"
								src={documentSrc}
							/>
						</figure>
					</ClayModal.Body>
				</ClayModal>
			)}
		</>
	);
};

DocumentPreview.propTypes = {
	documentSrc: PropTypes.string.isRequired,
	documentTitle: PropTypes.string,
	downloadURL: PropTypes.string,
};

export default DocumentPreview;

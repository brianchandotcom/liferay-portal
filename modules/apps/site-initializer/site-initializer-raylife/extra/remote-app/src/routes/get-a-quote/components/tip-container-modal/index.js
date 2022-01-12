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

import Modal from '../../../../common/components/modal';

const TipContainerModal = ({isMobile, onClose, webContentModal}) => (
	<Modal
		backdropLight={isMobile}
		closeable={false}
		onClose={onClose}
		show={webContentModal.show}
		size="small-mobile"
	>
		<div
			className="tip-container-modal"
			dangerouslySetInnerHTML={{
				__html: webContentModal.html,
			}}
		/>

		<div className="d-flex justify-content-center">
			<button
				className="btn btn-inverted btn-rounded btn-style-primary shadow-none text-uppercase"
				onClick={onClose}
			>
				Dismiss
			</button>
		</div>
	</Modal>
);

export default TipContainerModal;

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

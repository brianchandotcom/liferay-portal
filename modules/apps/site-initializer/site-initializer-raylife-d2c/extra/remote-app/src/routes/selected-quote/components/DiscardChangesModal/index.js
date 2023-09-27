/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Modal from '../../../../common/components/modal';
import useWindowDimensions from '../../../../common/hooks/useWindowDimensions';

const DiscardSelectedFiles = ({onClose, onDiscardChanges, show}) => {
	const {
		device: {isMobile},
	} = useWindowDimensions();

	return (
		<Modal
			closeable={false}
			footer={
				<div className="align-items-center d-flex flex-row justify-content-between ml-2 mr-1 mt-auto">
					<button
						className="btn btn-link link text-link-md text-neutral-7 text-small-caps upload-documents-mobile"
						onClick={onClose}
					>
						Cancel
					</button>

					<button
						className="btn btn-primary rounded text-link-md text-small-caps"
						onClick={() => {
							onDiscardChanges();
							onClose();
						}}
					>
						Continue
					</button>
				</div>
			}
			onClose={onClose}
			show={show}
			size={isMobile ? 'small-mobile' : 'small'}
		>
			<div className="align-items-center d-flex flex-column justify-content-between mb-4 mb-md-0 mt-2 mt-md-auto progress-saved-content">
				<div className="align-items-center d-flex flex-column progress-saved-body">
					<div className="font-weight-semi-bold pt-1 text-center text-neutral-8 text-paragraph-lg upload-documents-mobile">
						This will discard the files you have uploaded so far.
						Continue?
					</div>
				</div>
			</div>
		</Modal>
	);
};

export default DiscardSelectedFiles;

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

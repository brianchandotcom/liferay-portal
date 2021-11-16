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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import PropTypes from 'prop-types';
import React from 'react';

import ExportModalBody from './ExportModalBody';
import ExportPoller from './ExportPoller';

const ExportModal = ({
	closeModal,
	formDataQuerySelector,
	formSubmitURL,
	observer,
}) => {
	const {
		contentType,
		downloadFile,
		errorMessage,
		loading,
		percentage,
		readyToDownload,
	} = ExportPoller(formDataQuerySelector, formSubmitURL);

	let modalType;
	let iconType;

	if (readyToDownload) {
		modalType = 'modal-success';
		iconType = 'check-circle-full';
	}
	else if (errorMessage) {
		modalType = 'modal-danger';
		iconType = 'exclamation-full';
	}
	else {
		modalType = 'modal-info';
		iconType = 'info-circle';
	}

	return (
		<ClayModal className={modalType} observer={observer} size="md">
			<ClayModal.Header>
				<ClayIcon className="mr-2" symbol={iconType} />
				{Liferay.Language.get('export-file')}
			</ClayModal.Header>

			<ExportModalBody
				contentType={contentType}
				errorMessage={errorMessage}
				percentage={percentage}
				readyToDownload={readyToDownload}
			/>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							disabled={loading}
							displayType="secondary"
							onClick={closeModal}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							disabled={!readyToDownload}
							displayType={
								readyToDownload ? 'success' : 'primary'
							}
							onClick={downloadFile}
						>
							{loading && (
								<span className="inline-item inline-item-before">
									<span
										aria-hidden="true"
										className="loading-animation"
									></span>
								</span>
							)}
							{Liferay.Language.get('download')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};

ExportModal.propTypes = {
	closeModal: PropTypes.func.isRequired,
	formDataQuerySelector: PropTypes.string.isRequired,
	formSubmitURL: PropTypes.string.isRequired,
	namespace: PropTypes.string.isRequired,
	observer: PropTypes.object.isRequired,
};

export default ExportModal;

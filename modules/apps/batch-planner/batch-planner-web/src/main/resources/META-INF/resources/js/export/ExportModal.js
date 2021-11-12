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
import ClayLink from '@clayui/link';
import ClayModal from '@clayui/modal';
import PropTypes from 'prop-types';
import React from 'react';

import ExportModalBody from './ExportModalBody';
import usePollingExport from './exportReducer';

const ExportModal = ({
	closeModal,
	formDataQuerySelector,
	formSubmitURL,
	observer,
}) => {
	const {
		contentType,
		errorMessage,
		exportFileURL,
		loading,
		percentage,
	} = usePollingExport(formDataQuerySelector, formSubmitURL);

	return (
		<ClayModal
			className={exportFileURL ? 'modal-success' : 'modal-info'}
			observer={observer}
			size="md"
		>
			<ClayModal.Header>
				<ClayIcon
					className="mr-2"
					symbol={exportFileURL ? 'check-circle-full' : 'info-circle'}
				/>
				{Liferay.Language.get('export-file')}
			</ClayModal.Header>

			<ExportModalBody
				contentType={contentType}
				errorMessage={errorMessage}
				exportFileURL={exportFileURL}
				percentage={percentage}
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

						{!exportFileURL ? (
							<ClayButton disabled displayType="primary">
								<span className="inline-item inline-item-before">
									<span
										aria-hidden="true"
										className="loading-animation"
									></span>
								</span>
								{Liferay.Language.get('download')}
							</ClayButton>
						) : (
							<ClayLink
								className="btn btn-success"
								download="Export.zip"
								href={exportFileURL}
								target="_blank"
							>
								{Liferay.Language.get('download')}
							</ClayLink>
						)}
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

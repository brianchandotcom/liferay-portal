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
import ClayForm from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayModal, {useModal} from '@clayui/modal';
import ClayProgressBar from '@clayui/progress-bar';
import PropTypes from 'prop-types';
import React from 'react';

import {fetchErrorReportFile, importStatus} from '../BatchPlannerImport';
import Poller from '../Poller';

const ImportModal = ({closeModal, formDataQuerySelector, formImportURL}) => {
	const {downloadFile, errorMessage, loading, percentage, ready} = Poller(
		formDataQuerySelector,
		formImportURL,
		importStatus,
		fetchErrorReportFile
	);
	const {observer} = useModal();

	let modalStatus;
	let title;
	let labelType;
	let label;

	if (ready) {
		modalStatus = 'success';
		title = Liferay.Language.get(
			'the-import-process-was-completed-successfully'
		);
		labelType = 'success';
		label = Liferay.Language.get('completed');
	}
	else if (errorMessage) {
		modalStatus = 'danger';
		title = errorMessage;
		labelType = 'danger';
		label = Liferay.Language.get('failed');
	}
	else {
		modalStatus = 'info';
		title = Liferay.Language.get(
			'data-is-being-imported-you-can-close-dialog'
		);
		labelType = 'warning';
		label = Liferay.Language.get('running');
	}

	return (
		<ClayModal observer={observer} size="md" status={modalStatus}>
			<ClayModal.Header>
				{Liferay.Language.get('import-file')}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm.Group>
					<ClayForm.FeedbackGroup>
						<ClayForm.FeedbackItem>{title}</ClayForm.FeedbackItem>

						<ClayLabel displayType={labelType}>{label}</ClayLabel>
					</ClayForm.FeedbackGroup>

					<ClayProgressBar value={percentage} warn={!!errorMessage} />
				</ClayForm.Group>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType={null} onClick={closeModal}>
							{Liferay.Language.get('back-to-the-list')}
						</ClayButton>

						{errorMessage && (
							<ClayButton
								disabled={loading}
								displayType="danger"
								onClick={downloadFile}
								type="submit"
							>
								{Liferay.Language.get('download-error-report')}
							</ClayButton>
						)}
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};

ImportModal.propTypes = {
	formImportURL: PropTypes.string.isRequired,
};

export default ImportModal;

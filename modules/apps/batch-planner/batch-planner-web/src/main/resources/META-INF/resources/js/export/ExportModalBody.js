/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayModal from '@clayui/modal';
import ClayProgressBar from '@clayui/progress-bar';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

import {EXPORT_FILE_NAME} from '../constants';

const ExportModalBody = ({errorMessage, percentage, readyToDownload}) => {
	let labelType;
	let label;
	let title;

	if (readyToDownload) {
		title = Liferay.Language.get(
			'your-file-has-been-generated-and-is-ready-to-download'
		);
		labelType = 'success';
		label = Liferay.Language.get('completed');
	}
	else if (errorMessage) {
		title = errorMessage;
		labelType = 'danger';
		label = Liferay.Language.get('failed');
	}
	else {
		title = Liferay.Language.get('export-file-is-being-created');
		labelType = 'warning';
		label = Liferay.Language.get('running');
	}

	return (
		<ClayModal.Body>
			<ClayForm.Group
				className={classnames({'has-error': !!errorMessage})}
			>
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>{title}</ClayForm.FeedbackItem>

					<ClayForm.FeedbackItem>
						{EXPORT_FILE_NAME}
					</ClayForm.FeedbackItem>

					<ClayLabel displayType={labelType}>{label}</ClayLabel>
				</ClayForm.FeedbackGroup>

				<ClayProgressBar value={percentage} warn={!!errorMessage} />
			</ClayForm.Group>
		</ClayModal.Body>
	);
};

ExportModalBody.propTypes = {
	errorMessage: PropTypes.string,
	percentage: PropTypes.number,
	readyToDownload: PropTypes.bool,
};

ExportModalBody.defaultProps = {
	percentage: 0,
	readyToDownload: false,
};

export default ExportModalBody;

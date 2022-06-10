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

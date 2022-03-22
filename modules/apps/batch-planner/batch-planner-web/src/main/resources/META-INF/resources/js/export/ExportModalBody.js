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
import ClayIcon from '@clayui/icon';
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
		title = Liferay.Language.get('error');
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
				<div>
					{title}

					<div className="align-items-start d-flex pb-2 pt-2">
						<ClayIcon
							className="mr-2 mt-1"
							symbol="document-default"
						/>

						<div className="d-flex flex-column">
							<span>{EXPORT_FILE_NAME}</span>

							<ClayLabel displayType={labelType}>
								{label}
							</ClayLabel>
						</div>
					</div>
				</div>

				<div
					className="progress-container"
					data-percentage={readyToDownload ? 100 : percentage}
					data-title={
						readyToDownload
							? Liferay.Language.get('completed')
							: Liferay.Language.get('in-progress')
					}
				>
					<ClayProgressBar
						value={readyToDownload ? 100 : percentage}
						warn={!!errorMessage}
					/>
				</div>

				{errorMessage && (
					<ClayForm.FeedbackGroup>
						<ClayForm.FeedbackItem>
							<ClayForm.FeedbackIndicator symbol="exclamation-full" />

							{errorMessage}
						</ClayForm.FeedbackItem>
					</ClayForm.FeedbackGroup>
				)}
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

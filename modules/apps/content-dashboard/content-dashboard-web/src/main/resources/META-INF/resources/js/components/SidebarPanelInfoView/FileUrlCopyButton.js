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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import {openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const FEEDBACK_DELAY = 2000;

const FileUrlCopyButton = ({url}) => {
	const [showFeedback, setShowFeedback] = useState(false);
	const [error, setError] = useState(null);

	const clipboardHandler = async () => {
		try {
			await navigator.clipboard.writeText(url);

			setShowFeedback(true);

			openToast({
				message: Liferay.Language.get('copied-link-to-the-clipboard'),
			});
		}
		catch (error) {
			setError(error);
			setShowFeedback(true);

			openToast({
				message: error,
				title: Liferay.Language.get('an-error-occurred'),
				type: 'warning',
			});
		}
		finally {
			setTimeout(() => {
				setShowFeedback(false);
				setError(null);
			}, FEEDBACK_DELAY);
		}
	};

	return (
		<div className="file-url-copy-button">
			<ClayForm.Group>
				<ClayInput.Group>
					<ClayInput.GroupItem prepend>
						<ClayInput readOnly type="text" value={url} />
					</ClayInput.GroupItem>

					<ClayInput.GroupItem append shrink>
						<ClayButtonWithIcon
							displayType="secondary"
							onClick={clipboardHandler}
							symbol={
								showFeedback
									? 'check'
									: error
									? 'exclamation-circle'
									: 'copy'
							}
							title={Liferay.Language.get('copy')}
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>
		</div>
	);
};

FileUrlCopyButton.propTypes = {
	url: PropTypes.string.isRequired,
};

export default FileUrlCopyButton;

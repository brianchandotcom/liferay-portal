/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const REGEX_URL = /((([A-Za-z]{3,9}:(?:\/\/)?)(?:[-;:&=+$,\w]+@)?[A-Za-z0-9.-]+|(https?:\/\/|www.|[-;:&=+$,\w]+@)[A-Za-z0-9.-]+)((?:\/[+~%/.\w-_]*)?\??(?:[-+=&;%@.\w_]*)#?(?:[\w]*))((.*):(\d*)\/?(.*))?)/;

const STR_BLANK = '';

const DestinationUrlInput = ({
	autofocus = false,
	initialDestinationUrl = STR_BLANK,
	namespace,
}) => {
	const [requiredError, setRequiredError] = useState(false);
	const [urlError, setUrlError] = useState(false);
	const [destinationUrl, setDestinationUrl] = useState(initialDestinationUrl);

	const handleTryRedirection = () => {
		let testUrl = destinationUrl;
		const protocol = 'http';
		if (!testUrl.startsWith(protocol)) {
			testUrl = protocol + '://' + testUrl;
		}

		window.open(testUrl, '_blank');
	};

	const isAbsoluteUrl = (url) => {
		return REGEX_URL && REGEX_URL.test(url);
	};

	return (
		<ClayForm.Group
			className={requiredError || urlError ? 'has-error' : STR_BLANK}
		>
			<label htmlFor={`${namespace}destinationURL`}>
				{Liferay.Language.get('destination-url')}

				<span className="inline-item-after reference-mark">
					<ClayIcon symbol="asterisk" />

					<span className="hide-accessible sr-only">
						{Liferay.Language.get('required')}
					</span>
				</span>
			</label>

			<ClayInput.Group>
				<ClayInput.GroupItem prepend>
					<ClayInput
						aria-label={Liferay.Language.get('destination-url')}
						autoFocus={autofocus}
						id={`${namespace}destinationURL`}
						name={`${namespace}destinationURL`}
						onBlur={({currentTarget}) => {
							setRequiredError(!currentTarget.value);
							setUrlError(!isAbsoluteUrl(currentTarget.value));
						}}
						onChange={({currentTarget}) =>
							setDestinationUrl(currentTarget.value)
						}
						type="text"
						value={destinationUrl}
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem append shrink>
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('check-url')}
						disabled={
							destinationUrl === STR_BLANK ||
							!isAbsoluteUrl(destinationUrl)
						}
						displayType="secondary"
						onClick={handleTryRedirection}
						symbol="shortcut"
						title={Liferay.Language.get('check-url')}
					/>
				</ClayInput.GroupItem>
			</ClayInput.Group>

			{requiredError && (
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						{Liferay.Language.get('this-field-is-required')}
					</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			)}

			{!requiredError && urlError && (
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						<ClayForm.FeedbackIndicator symbol="exclamation-full" />

						{Liferay.Language.get('this-url-is-not-supported')}
					</ClayForm.FeedbackItem>

					<div
						className="small"
						dangerouslySetInnerHTML={{
							__html: sub(
								Liferay.Language.get('enter-an-absolute-url'),
								'<em>',
								'</em>'
							),
						}}
					/>
				</ClayForm.FeedbackGroup>
			)}
		</ClayForm.Group>
	);
};

DestinationUrlInput.propTypes = {
	autofocus: PropTypes.bool,
	initialDestinationUrl: PropTypes.string,
	namespace: PropTypes.string.isRequired,
};

export default DestinationUrlInput;

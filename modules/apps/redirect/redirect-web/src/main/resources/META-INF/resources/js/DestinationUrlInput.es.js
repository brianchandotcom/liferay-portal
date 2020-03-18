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

import ClayIcon from '@clayui/icon';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {useState} from 'react';

const VALIDATION_TYPE = {
	checking: 'checking',
	error: 'has-error',
	info: 'has-success',
	warning: 'has-warning',
};

const Notification = ({type}) => {
	if (type === VALIDATION_TYPE.checking) {
		return (
			<>
				<ClayLoadingIndicator className="d-inline-block m-0 mr-2" small />
				{Liferay.Language.get('cheking-url')}
			</>
		);
	} else if (type === VALIDATION_TYPE.error) {
		return Liferay.Language.get('this-field-is-required');
	} else if (type === VALIDATION_TYPE.info) {
		return (
			<>
				<ClayForm.FeedbackIndicator symbol="check-circle-full" />
				{Liferay.Language.get('working-url')}
			</>
		);
	} else if (type === VALIDATION_TYPE.warning) {
		return (
			<>
				<ClayForm.FeedbackIndicator symbol="warning-full" />
				{Liferay.Language.get('could-not-find-url')}
			</>
		);
	}
};

const DestinationUrlInput = ({initialUrl}) => {
	const [destinationUrl, setDestinationUrl] = useState(initialUrl);
	const [validationType, setValidationType] = useState('');

	const onInputBlur = event => {
		const url = event.currentTarget.value;

		if (!url) {
			setValidationType(VALIDATION_TYPE.error);
		} else {
			setValidationType(VALIDATION_TYPE.checking);

			setTimeout(() => {
				if (url === 'a') {
					setValidationType(VALIDATION_TYPE.warning);
				} else {
					setValidationType(VALIDATION_TYPE.info);
				}
			}, 1000)
		}
	};

	return (
		<ClayForm.Group className={validationType}>
			<label htmlFor="destinationUrlInput">
				{Liferay.Language.get('destination-url')}

				<span className="inline-item-after reference-mark">
					<ClayIcon symbol={'asterisk'} />

					<span className="hide-accessible">
						{Liferay.Language.get('required')}
					</span>
				</span>
			</label>

			<ClayInput
				id="destinationUrlInput"
				onBlur={onInputBlur}
				required
				value={destinationUrl}
				type="text"
			/>

			{validationType && (
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						<Notification type={validationType} />
					</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			)}
		</ClayForm.Group>
	);
};

export default DestinationUrlInput;
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
import ClayForm, {ClayInput} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useRef, useState} from 'react';

import {sub} from './utils.es';

const TIMEOUT_MS = 5000;

const ItemSelectorUrl = ({eventName}) => {
	const inputName = 'urlInputReact';
	const [url, setUrl] = useState('');
	const [loaded, setLoaded] = useState(false);
	const [previewError, setPreviewError] = useState(false);
	const isMounted = useIsMounted();
	const timerTimeoutRef = useRef(null);

	const handleImgPreviewError = () => {
		if (isMounted()) {
			setLoaded(true);
			setPreviewError(true);
			clearTimeout(timerTimeoutRef.current);
		}
	};

	const handleLoad = () => {
		if (isMounted()) {
			setLoaded(true);
			setPreviewError(false);
			clearTimeout(timerTimeoutRef.current);
		}
	};

	const handleSubmit = event => {
		event.preventDefault();

		if (!loaded) {
			return;
		}

		const eventData = {
			data: {
				returnType: 'URL',
				value: url
			}
		};

		Liferay.Util.getOpener().Liferay.fire(eventName, eventData);
	};

	const handleUrlChange = event => {
		const value = event.target.value.trim();
		setUrl(value);
		setLoaded(false);
		setPreviewError(false);

		if (timerTimeoutRef.current) {
			clearTimeout(timerTimeoutRef.current);
		}

		if (value) {
			timerTimeoutRef.current = setTimeout(
				handleImgPreviewError,
				TIMEOUT_MS
			);
		}
	};

	const isLoading = !previewError && url && !loaded;

	return (
		<>
			<form onSubmit={handleSubmit}>
				<ClayForm.Group>
					<label htmlFor={inputName}>
						{Liferay.Language.get('url')}
					</label>
					<ClayInput
						id={inputName}
						onChange={handleUrlChange}
						placeholder="http://"
						type="text"
						value={url}
					/>
					<p className="form-text">
						{sub(Liferay.Language.get('for-example-x'), [
							'http://www.liferay.com/liferay.png'
						])}
					</p>
				</ClayForm.Group>
				<ClayButton disabled={!loaded} type="submit">
					{Liferay.Language.get('add')}
				</ClayButton>
			</form>
			<div className="aspect-ratio aspect-ratio-16-to-9 bg-light mt-4">
				{!previewError && url && (
					<img
						className={classNames(
							'aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-fluid aspect-ratio-item-vertical-fluid',
							{
								invisible: !loaded
							}
						)}
						onError={handleImgPreviewError}
						onLoad={handleLoad}
						src={url}
					/>
				)}
				{(isLoading || previewError) && (
					<div className="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-fluid">
						{isLoading && <ClayLoadingIndicator />}
						{previewError && (
							<strong className="text-secondary">
								{Liferay.Language.get(
									'there-is-no-preview-available'
								)}
							</strong>
						)}
					</div>
				)}
			</div>
		</>
	);
};

ItemSelectorUrl.propTypes = {
	eventName: PropTypes.string.isRequired
};

export default function(props) {
	return <ItemSelectorUrl {...props} />;
}

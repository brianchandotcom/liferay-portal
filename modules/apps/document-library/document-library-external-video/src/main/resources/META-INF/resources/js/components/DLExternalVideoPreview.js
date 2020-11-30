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
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import {useIsMounted} from 'frontend-js-react-web';
import {addParams, fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {useDebounceCallback} from '../utils/hooks';
import validateUrl from '../utils/validateUrl';
import ConditionalWrapper from './ConditionalWrapper';

const DLExternalVideoPreview = ({
	externalVideoHTML = '',
	externalVideoURL = '',
	getDLExternalVideoFieldsURL,
	namespace,
	onAdd,
	onSelectedVideo,
	small,
}) => {
	const inputName = 'externalVideoURLInput';
	const [validURL, setValidURL] = useState(false);
	const [error, setError] = useState('');
	const [loading, setLoading] = useState(false);
	const [url, setUrl] = useState(externalVideoURL);
	const [videoHtml, setVideoHtml] = useState(externalVideoHTML);
	const isMounted = useIsMounted();

	const [getFields] = useDebounceCallback((dlExternalVideoURL) => {
		setValidURL(false);
		fetch(
			addParams(
				{
					[`${namespace}dlExternalVideoURL`]: dlExternalVideoURL,
				},
				getDLExternalVideoFieldsURL
			)
		)
			.then((res) => res.json())
			.then((fields) => {
				if (isMounted()) {
					setLoading(false);
					setValidURL(true);
					setVideoHtml(fields.HTML);

					if (onSelectedVideo) {
						onSelectedVideo(fields);
					}
				}
			})
			.catch(() => {
				if (isMounted()) {
					setLoading(false);
					setVideoHtml(externalVideoHTML);
					setError(
						Liferay.Language.get(
							'sorry,-this-platform-is-not-supported'
						)
					);
				}
			});
	}, 500);

	const handleUrlChange = (event) => {
		const value = event.target.value.trim();

		setError('');
		setUrl(value);

		if (value && validateUrl(value)) {
			setLoading(true);
			getFields(value);
		}
		else {
			setLoading(false);
			setValidURL(false);
			setVideoHtml(externalVideoHTML);
		}
	};

	const addDisabled = !!(error || loading || !url || !validURL);

	const handleAddSubmit = (event) => {
		event.preventDefault();

		if (addDisabled) {
			return;
		}

		onAdd(url);
	};

	return (
		<ConditionalWrapper
			condition={!!onAdd}
			wrapper={(children) => (
				<form onSubmit={handleAddSubmit}>{children}</form>
			)}
		>
			<label htmlFor={inputName}>
				{Liferay.Language.get('video-url')}
			</label>
			<ClayInput
				id={inputName}
				onChange={handleUrlChange}
				placeholder="http://"
				type="text"
				value={url}
			/>
			<p className="form-text">
				{Liferay.Language.get('video-url-help')}
			</p>

			{onAdd && (
				<ClayButton disabled={addDisabled} type="submit">
					{Liferay.Language.get('add')}
				</ClayButton>
			)}

			<div
				className={classNames('external-video-preview mt-4', {
					['external-video-preview-small']: small,
				})}
			>
				{videoHtml ? (
					<div
						className="external-video-preview-aspect-ratio"
						dangerouslySetInnerHTML={{__html: videoHtml}}
					/>
				) : (
					<div className="external-video-preview-aspect-ratio">
						<div className="external-video-preview-placeholder">
							{loading ? (
								<ClayLoadingIndicator />
							) : (
								<>
									<ClayIcon symbol="video" />
									{error && (
										<div className="external-video-preview-placeholder-text">
											{error}
										</div>
									)}
								</>
							)}
						</div>
					</div>
				)}
			</div>
		</ConditionalWrapper>
	);
};

DLExternalVideoPreview.propTypes = {
	externalVideoHTML: PropTypes.string,
	externalVideoURL: PropTypes.string,
	getDLExternalVideoFieldsURL: PropTypes.string.isRequired,
	namespace: PropTypes.string.isRequired,
	onSelectedVideo: PropTypes.func,
};

export default DLExternalVideoPreview;

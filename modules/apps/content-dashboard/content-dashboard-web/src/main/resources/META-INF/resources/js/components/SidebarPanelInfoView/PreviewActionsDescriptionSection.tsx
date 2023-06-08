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

import ClayLink from '@clayui/link';
import React from 'react';

const {default: Preview} = require('./Preview');
const {default: Share} = require('./Share');

const PreviewActionsDescriptionSection = ({
	description,
	downloadURL,
	fetchSharingButtonURL,
	handleError,
	preview,
	title,
}: IProps): JSX.Element => {
	const hasActions = downloadURL || fetchSharingButtonURL;

	return (
		<>
			{preview && preview.imageURL && (
				<Preview
					compressed={!!hasActions}
					imageURL={preview.imageURL}
					title={title}
					url={preview.url}
				/>
			)}

			{hasActions && (
				<div className="mt-3 sidebar-section">
					{downloadURL && (
						<ClayLink
							className="btn btn-primary mr-2"
							href={downloadURL}
						>
							{Liferay.Language.get('download')}
						</ClayLink>
					)}

					{fetchSharingButtonURL && (
						<Share
							fetchSharingButtonURL={fetchSharingButtonURL}
							onError={handleError}
						/>
					)}
				</div>
			)}

			{description && (
				<div className="sidebar-section">
					<h5 className="c-mb-1 font-weight-semi-bold">
						{Liferay.Language.get('description')}
					</h5>

					<div
						className="text-secondary"
						dangerouslySetInnerHTML={{
							__html: description,
						}}
					/>
				</div>
			)}
		</>
	);
};

interface IPreview {
	imageURL: string;
	url: string;
}

interface IProps {
	description?: string;
	downloadURL?: string;
	fetchSharingButtonURL?: string;
	handleError?: () => void;
	preview?: IPreview;
	title: string;
}

export default PreviewActionsDescriptionSection;

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import React from 'react';

// @ts-ignore

import Preview from './Preview';

// @ts-ignore

import Share from './Share';

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
					<div className="c-mb-1 font-weight-semi-bold h5">
						{Liferay.Language.get('description')}
					</div>

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

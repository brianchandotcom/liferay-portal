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
import React, {useContext} from 'react';

import {ConstantsContext} from '../context/ConstantsContext';
import {StoreDispatchContext, StoreStateContext} from '../context/StoreContext';
import loadIssues from '../utils/loadIssues';

export default function NoIssuesLoaded() {
	const {data, languageId} = useContext(StoreStateContext);
	const {portletNamespace} = useContext(ConstantsContext);
	const dispatch = useContext(StoreDispatchContext);

	const {imagesPath} = data;

	const defaultIllustration = `${imagesPath}/issues_default.svg`;

	const onLaunchButtonClick = () => {
		const url = data.pageURLs.find(
			(pageURL) =>
				pageURL.languageId === (languageId || data.defaultLanguageId)
		);

		loadIssues({
			dispatch,
			languageId,
			portletNamespace,
			url,
		});
	};

	return (
		<div className="pb-3 px-3 text-center">
			<img
				alt={Liferay.Language.get(
					'default-page-audit-image-alt-description'
				)}
				className="my-4"
				src={defaultIllustration}
				width="120px"
			/>

			<p className="text-secondary">
				{Liferay.Language.get(
					"launch-a-page-audit-to-check-issues-that-impact-on-your-page's-accesibility-and-seo"
				)}
			</p>

			<ClayButton
				displayType="secondary"
				onClick={onLaunchButtonClick}
				title={Liferay.Language.get('launch-page-audit')}
			>
				{Liferay.Language.get('launch-page-audit')}
			</ClayButton>
		</div>
	);
}

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

import {fetch, objectToFormData} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import PagePreview from './PagePreview';
import Sidebar from './Sidebar';
import {StyleBookContextProvider} from './StyleBookContext';

const StyleBookEditor = ({
	namespace,
	previewURL,
	publishURL,
	saveDraftURL,
	styleBookEntryId,
	tokenCategories = [],
	tokenValues: initialTokenValues = {},
} = {}) => {
	const [tokenValues, setTokenValues] = useState(initialTokenValues);

	useEffect(() => {
		if (tokenValues === initialTokenValues) {
			return;
		}

		const body = objectToFormData({
			[`${namespace}tokenValues`]: JSON.stringify(tokenValues),
			[`${namespace}styleBookEntryId`]: styleBookEntryId,
		});

		fetch(saveDraftURL, {body, method: 'post'});
	}, [
		initialTokenValues,
		namespace,
		saveDraftURL,
		styleBookEntryId,
		tokenValues,
	]);

	return (
		<StyleBookContextProvider
			value={{
				namespace,
				previewURL,
				publishURL,
				saveDraftURL,
				setTokenValues,
				styleBookEntryId,
				tokenCategories,
				tokenValues,
			}}
		>
			<div className="style-book-editor">
				<PagePreview />
				<Sidebar />
			</div>
		</StyleBookContextProvider>
	);
};

export default StyleBookEditor;

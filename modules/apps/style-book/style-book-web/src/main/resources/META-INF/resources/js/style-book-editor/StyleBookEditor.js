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

import {fetch, objectToFormData, openToast} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import PagePreview from './PagePreview';
import Sidebar from './Sidebar';
import {StyleBookContextProvider} from './StyleBookContext';
import {config, initializeConfig} from './config';
import {DRAFT_STATUS} from './constants/draftStatusConstants';
import {useCloseProductMenu} from './useCloseProductMenu';

const StyleBookEditor = ({tokenValues: initialTokenValues}) => {
	useCloseProductMenu();

	const [tokenValues, setTokenValues] = useState(initialTokenValues);
	const [draftStatus, setDraftStatus] = useState(DRAFT_STATUS.notSaved);

	useEffect(() => {
		if (tokenValues === initialTokenValues) {
			return;
		}

		setDraftStatus(DRAFT_STATUS.saving);

		saveDraft(tokenValues, config.styleBookEntryId)
			.then(() => {
				setDraftStatus(DRAFT_STATUS.draftSaved);
			})
			.catch((error) => {
				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}

				setDraftStatus(DRAFT_STATUS.notSaved);

				openToast({
					message: error.message,
					title: Liferay.Language.get('error'),
					type: 'danger',
				});
			});
	}, [initialTokenValues, tokenValues]);

	return (
		<StyleBookContextProvider
			value={{
				draftStatus,
				setTokenValues,
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

export default function ({
	namespace,
	previewURL,
	publishURL,
	redirectURL,
	saveDraftURL,
	styleBookEntryId,
	tokenCategories = [],
	tokenValues = {},
} = {}) {
	initializeConfig({
		namespace,
		previewURL,
		publishURL,
		redirectURL,
		saveDraftURL,
		styleBookEntryId,
		tokenCategories,
	});

	return <StyleBookEditor tokenValues={tokenValues} />;
}

function saveDraft(tokenValues, styleBookEntryId) {
	const body = objectToFormData({
		[`${config.namespace}tokenValues`]: JSON.stringify(tokenValues),
		[`${config.namespace}styleBookEntryId`]: styleBookEntryId,
	});

	return fetch(config.saveDraftURL, {body, method: 'post'})
		.then((response) => {
			return response
				.clone()
				.json()
				.catch(() => response.text())
				.then((body) => [response, body]);
		})
		.then(([response, body]) => {
			if (response.status >= 400 || typeof body !== 'object') {
				throw new Error(
					Liferay.Language.get('an-unexpected-error-occurred')
				);
			}

			if (body.error) {
				throw new Error(body.error);
			}

			return body;
		});
}

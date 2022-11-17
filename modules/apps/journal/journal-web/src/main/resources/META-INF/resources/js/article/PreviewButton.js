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
import {openModal, openToast} from 'frontend-js-web';
import React from 'react';

export default function PreviewButton({
	disabled,
	getPreviewURL,
	namespace,
	newArticle,
	saveAsDraftURL,
}) {
	return (
		<ClayButton
			aria-label={Liferay.Language.get(
				'a-draft-will-be-saved-before-displaying-the-preview'
			)}
			disabled={disabled}
			displayType="secondary"
			onClick={() => {
				updateJournalInput({
					name: 'formDate',
					namespace,
					value: Date.now().toString(),
				});

				const form = document.getElementById(`${namespace}fm1`);

				const formData = new FormData(form);

				const articleId = document.getElementById(
					`${namespace}articleId`
				);

				formData.append(
					`${namespace}cmd`,
					newArticle && !articleId.value ? 'add' : 'update'
				);

				return Liferay.Util.fetch(saveAsDraftURL, {
					body: formData,
					method: form.method,
				})
					.then((response) => response.json())
					.then((response) => {
						const {
							articleId,
							error,
							friendlyUrlMap,
							version,
						} = response;

						if (error) {
							openToast({
								message: Liferay.Language.get(
									'web-content-could-not-be-previewed-due-to-an-unexpected-error-while-generating-the-draft'
								),
								title: Liferay.Language.get('error'),
								type: 'danger',
							});
						}
						else {
							updateJournalInput({
								name: 'formDate',
								namespace,
								value: Date.now().toString(),
							});

							updateJournalInput({
								name: 'articleId',
								namespace,
								value: articleId,
							});

							updateJournalInput({
								name: 'version',
								namespace,
								value: version,
							});

							Object.entries(friendlyUrlMap).forEach(
								([languageId, value]) => {
									updateJournalInput({
										name: `friendlyURL_${languageId}`,
										namespace,
										value,
									});
								}
							);

							openModal({
								title: Liferay.Language.get('preview'),
								url: getPreviewURL(response),
							});
						}
					})
					.catch(() => {
						openToast({
							message: Liferay.Language.get(
								'web-content-could-not-be-previewed-due-to-an-unexpected-error-while-generating-the-draft'
							),
							title: Liferay.Language.get('error'),
							type: 'danger',
						});
					});
			}}
			title={
				!disabled &&
				Liferay.Language.get(
					'a-draft-will-be-saved-before-displaying-the-preview'
				)
			}
		>
			{Liferay.Language.get('preview')}
		</ClayButton>
	);
}

function updateJournalInput({name, namespace, value}) {
	const input = document.getElementById(`${namespace}${name}`);

	if (input) {
		input.value = value;
	}
}

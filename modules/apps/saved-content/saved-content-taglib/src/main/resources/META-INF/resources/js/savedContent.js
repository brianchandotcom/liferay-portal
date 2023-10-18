/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {fetch, openToast, sub} from 'frontend-js-web';
import React, {useState} from 'react';

function showNotification(message, error = false) {
	openToast({
		message,
		type: error ? 'danger' : undefined,
	});
}

export default function SavedContent({
	contentName,
	saved: initialSaved,
	savedContentURL,
}) {
	const [saved, setSaved] = useState(initialSaved);
	const [loading, setLoading] = useState(false);

	const handleSubmit = (event) => {
		event.preventDefault();

		setLoading(true);
		setSaved((saved) => !saved);

		fetch(savedContentURL)
			.then((response) => response.json())
			.then(({errorMessage, successMessage}) => {
				if (errorMessage) {
					showNotification(errorMessage, true);

					return;
				}

				showNotification(successMessage);
			})
			.catch((error) => {
				setSaved((saved) => !saved);
				showNotification(error.message, true);
			})
			.finally(() => {
				setLoading(false);
			});
	};

	return (
		<form onSubmit={handleSubmit}>
			<ClayButtonWithIcon
				aria-label={
					saved
						? sub(Liferay.Language.get('remove-x'), contentName)
						: sub(Liferay.Language.get('save-x'), contentName)
				}
				disabled={loading}
				displayType="secondary"
				monospaced
				size="sm"
				symbol={saved ? 'bookmarks-full' : 'bookmarks'}
				title={
					saved
						? Liferay.Language.get('remove-content')
						: Liferay.Language.get('save-content')
				}
				type="submit"
			/>
		</form>
	);
}

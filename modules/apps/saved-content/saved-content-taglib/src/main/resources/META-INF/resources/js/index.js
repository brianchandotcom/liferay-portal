/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {fetch, objectToFormData, openToast, sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

function showNotification(message, error = false) {
	openToast({
		message,
		type: error ? 'danger' : undefined,
	});
}

export function SavedContentEntry({
	className,
	classPK,
	contentTitle,
	enabled = false,
	mySavedContentURL,
	portletNamespace,
	saved: initialSaved = false,
	savedContentEntryURL,
}) {
	const [saved, setSaved] = useState(initialSaved);
	const [loading, setLoading] = useState(false);

	const handleSubmit = (event) => {
		event.preventDefault();

		setLoading(true);
		setSaved((saved) => !saved);

		fetch(savedContentEntryURL, {
			body: objectToFormData({
				[`${portletNamespace}className`]: className,
				[`${portletNamespace}classPK`]: classPK,
			}),
			method: 'POST',
		})
			.then((response) => response.json())
			.then((response) => {
				if (response.errorMessage) {
					setSaved((saved) => !saved);
					showNotification(response.errorMessage, true);

					return;
				}

				setSaved(response.saved);

				const mySavedContentLink = `
					<a href="${mySavedContentURL}" class="alert-link">${Liferay.Language.get(
						'my-saved-content'
					)}</a>
				`.trim();

				showNotification(
					sub(
						saved
							? Liferay.Language.get(
									'x-has-been-successfully-removed-from-x'
								)
							: Liferay.Language.get('x-has-been-saved-in-x'),
						contentTitle,
						mySavedContentLink
					)
				);
			})
			.catch(() => {
				setSaved((saved) => !saved);
				showNotification(
					Liferay.Language.get('an-unexpected-error-occurred'),
					true
				);
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
						? sub(Liferay.Language.get('remove-x'), contentTitle)
						: sub(Liferay.Language.get('save-x'), contentTitle)
				}
				disabled={!enabled || loading}
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

SavedContentEntry.propTypes = {
	className: PropTypes.string.isRequired,
	classPK: PropTypes.string.isRequired,
	contentTitle: PropTypes.string.isRequired,
	enabled: PropTypes.bool,
	mySavedContentURL: PropTypes.string.isRequired,
	portletNamespace: PropTypes.string.isRequired,
	saved: PropTypes.bool,
	savedContentEntryURL: PropTypes.string.isRequired,
};

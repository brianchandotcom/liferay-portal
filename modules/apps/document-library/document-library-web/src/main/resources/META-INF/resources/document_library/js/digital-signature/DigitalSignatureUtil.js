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

import {
	createPortletURL,
	createResourceURL,
	fetch,
	navigate,
	objectToFormData,
	openModal,
} from 'frontend-js-web';

const MAXIMUM_SELECTED_FILES = 10;

const translatedKeys = {
	cancel: Liferay.Language.get('cancel'),
	continue: Liferay.Language.get('continue'),
	deselected: Liferay.Language.get('deselected'),
	maximum_files_message: Liferay.Util.sub(
		Liferay.Language.get('maximum-of-x-files-per-envelope'),
		MAXIMUM_SELECTED_FILES
	),
};

const _invalidFileExtensionContent = (invalidFileExtensions) =>
	`${Liferay.Util.sub(
		Liferay.Language.get(
			'these-file-extensions-are-not-supported-by-docusign-and-have-been-x'
		),
		_translatedStrongKeys(translatedKeys.deselected)
	)}

	<p class="mt-2">${Liferay.Util.sub(
		Liferay.Language.get('please-x-or-x-to-choose-new-documents'),
		_translatedStrongKeys(translatedKeys.continue),
		_translatedStrongKeys(translatedKeys.cancel)
	)}</p>
	
	<ul>
		${invalidFileExtensions
			.map(
				({fileName}) => `<li class="font-weight-bold">${fileName}</li>`
			)
			.join(' ')}
	</ul>`;

const _invalidFileCountContent = (
	extendedMessage
) => `<div class="alert alert-warning">
		${Liferay.Util.sub(
			Liferay.Language.get(
				'you-have-exceeded-the-maximum-amount-of-x-files-allowed-per-envelope'
			),
			MAXIMUM_SELECTED_FILES
		)}

		${
			extendedMessage
				? `<div class="mt-2">${Liferay.Util.sub(
						Liferay.Language.get(
							'please-x-or-x-to-remove-files-in-your-envelope'
						),
						_translatedStrongKeys(translatedKeys.continue),
						_translatedStrongKeys(translatedKeys.cancel)
				  )}</div>`
				: ''
		}
	</div>`;

const _composeBodyHTML = (crossedFileCountLimit, invalidFileExtensions) => {
	let bodyHTML = '';

	if (crossedFileCountLimit) {
		bodyHTML += _invalidFileCountContent(
			invalidFileExtensions.length === 0
		);
	}

	if (invalidFileExtensions.length) {
		bodyHTML += _invalidFileExtensionContent(invalidFileExtensions);
	}

	return bodyHTML;
};

const _navigateToCollectDigitalSignature = (portletId, fileEntryId) =>
	navigate(
		createPortletURL(themeDisplay.getLayoutRelativeControlPanelURL(), {
			backURL: window.location.href,
			fileEntryId,
			mvcRenderCommandName:
				'/digital_signature/collect_digital_signature',
			p_p_id: portletId,
		}).toString()
	);

const _translatedStrongKeys = (word) =>
	`<strong>${word.toLowerCase()}</strong>`;

const _showWarningModal = ({
	crossedFileCountLimit,
	digitalSignaturePortlet,
	invalidFileExtensions,
	validFileExtensionEntryIds,
}) =>
	openModal({
		bodyHTML: _composeBodyHTML(
			crossedFileCountLimit,
			invalidFileExtensions
		),
		buttons: [
			{
				displayType: 'secondary',
				label: Liferay.Language.get('cancel'),
				type: 'cancel',
			},
			{
				label: Liferay.Language.get('continue'),
				onClick: () =>
					_navigateToCollectDigitalSignature(
						digitalSignaturePortlet,
						validFileExtensionEntryIds
					),
				type: 'button',
			},
		],
		status: 'warning',
		title:
			invalidFileExtensions.length === 0
				? Liferay.Util.sub(
						Liferay.Language.get('maximum-of-x-files-per-envelope'),
						MAXIMUM_SELECTED_FILES
				  )
				: Liferay.Language.get('file-extensions-not-supported'),
	});

export const collectDigitalSignature = async (
	fileEntryIds,
	digitalSignaturePortlet
) => {
	const response = await fetch(
		createResourceURL(themeDisplay.getLayoutRelativeControlPanelURL(), {
			p_p_id: digitalSignaturePortlet,
			p_p_resource_id: '/digital_signature/get_invalid_file_extensions',
		}),
		{
			body: objectToFormData({
				[`_${digitalSignaturePortlet}_fileEntryIds`]: fileEntryIds,
			}),
			method: 'POST',
		}
	);

	const invalidFileExtensions = await response.json();

	const invalidFileExtensionEntryIds = invalidFileExtensions.map(
		({fileEntryId}) => fileEntryId
	);

	const validFileExtensionEntryIds = fileEntryIds.filter(
		(fileEntryId) =>
			!invalidFileExtensionEntryIds.includes(Number(fileEntryId))
	);

	const crossedFileCountLimit =
		validFileExtensionEntryIds.length > MAXIMUM_SELECTED_FILES;

	if (invalidFileExtensions.length || crossedFileCountLimit) {
		return _showWarningModal({
			crossedFileCountLimit,
			digitalSignaturePortlet,
			invalidFileExtensions,
			validFileExtensionEntryIds,
		});
	}

	_navigateToCollectDigitalSignature(
		digitalSignaturePortlet,
		fileEntryIds.length > 1 ? fileEntryIds.join(',') : fileEntryIds[0]
	);
};

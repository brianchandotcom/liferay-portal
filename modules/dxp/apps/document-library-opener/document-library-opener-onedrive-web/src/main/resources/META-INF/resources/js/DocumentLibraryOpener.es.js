/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getSpritemap} from '@liferay/frontend-icons-web';
import {
	fetch,
	getWindow,
	navigate,
	openSimpleInputModal,
	openToast,
	openWindow,
} from 'frontend-js-web';

const TIME_POLLING = 500;
const TIME_SHOW_MSG = 2000;
const DEFAULT_ERROR = Liferay.Language.get('an-unexpected-error-occurred');

class DocumentLibraryOpener {
	constructor({namespace}) {
		this._namespace = namespace;

		this._dialogLoadingId = `${namespace}OneDriveLoadingDialog`;
		this._refreshAfterNavigate = false;
	}

	_hideLoading() {
		getWindow(this._dialogLoadingId).hide();
	}

	_openExternal({externalURL}) {
		window.open(externalURL);
		this._hideLoading();

		if (this._refreshAfterNavigate) {
			this._refreshAfterNavigate = false;
			Liferay.Portlet.refresh(`#p_p_id${this._namespace}`);
		}
	}

	_polling({statusURL}) {
		return fetch(statusURL)
			.then((response) => {
				if (!response.ok) {
					throw DEFAULT_ERROR;
				}

				return response.json();
			})
			.then((response) => {
				if (response.error) {
					throw response.errorMessage || DEFAULT_ERROR;
				}
				else if (response.complete) {
					this._openExternal({
						externalURL: response.office365EditURL,
					});
				}
				else {
					return new Promise((resolve) => {
						setTimeout(() => {
							this._polling({statusURL}).then(resolve);
						}, TIME_POLLING);
					});
				}
			})
			.catch((error) => {
				this._showError(error);
			});
	}

	_showError(message) {
		this._hideLoading();
		openToast({
			message,
			type: 'danger',
		});
	}

	_showLoading({dialogMessage}) {
		return new Promise((resolve) => {
			openWindow(
				{
					dialog: {
						bodyContent: `<p>${dialogMessage}</p><div aria-hidden="true" class="loading-animation"></div>`,
						cssClass: 'office-365-redirect-modal',
						height: 172,
						modal: true,
						resizable: false,
						title: '',
						width: 320,
					},
					id: this._dialogLoadingId,
				},
				() => {
					setTimeout(resolve, TIME_SHOW_MSG);
				}
			);
		});
	}

	createWithName({dialogTitle, formSubmitURL}) {
		openSimpleInputModal({
			alert: {
				message: Liferay.Language.get(
					'the-document-has-been-checked-out-.please-check-in-the-document-after-edits-are-made-to-save-the-changes-into-the-document-library'
				),
				style: 'info',
				title: Liferay.Language.get('info'),
			},
			dialogTitle,
			formSubmitURL,
			mainFieldLabel: Liferay.Language.get('title'),
			mainFieldName: 'title',
			namespace: this._namespace,
			onFormSuccess: (serverResponseContent) => {
				if (serverResponseContent.oneDriveBackgroundTaskStatusURL) {
					this.open({
						dialogMessage: serverResponseContent.dialogMessage,
						refresh: true,
						statusURL:
							serverResponseContent.oneDriveBackgroundTaskStatusURL,
					});
				}
			},
			spritemap: getSpritemap(),
		});
	}

	edit({formSubmitURL}) {
		this._refreshAfterNavigate = true;

		const loadingPromise = this._showLoading({
			dialogMessage: Liferay.Language.get(
				'you-are-being-redirected-to-an-external-editor-to-edit-this-document'
			),
		});

		const fetchPromise = fetch(formSubmitURL)
			.then((response) => {
				if (!response.ok) {
					throw DEFAULT_ERROR;
				}

				return response.json();
			})
			.then((response) => {
				if (response.redirectURL) {
					navigate(response.redirectURL);
				}
				else if (response.oneDriveBackgroundTaskStatusURL) {
					return this._polling({
						statusURL: response.oneDriveBackgroundTaskStatusURL,
					});
				}
				else if (response.error) {
					throw response.error.errorMessage || DEFAULT_ERROR;
				}
			})
			.catch((error) => {
				this._showError(error);
			});

		return Promise.all([loadingPromise, fetchPromise]);
	}

	open({
		dialogMessage = Liferay.Language.get(
			'you-are-being-redirected-to-an-external-editor-to-edit-this-document'
		),
		statusURL,
		refresh = false,
	}) {
		this._refreshAfterNavigate = refresh;

		const loadingPromise = this._showLoading({
			dialogMessage,
		});
		const pollingPromise = this._polling({statusURL});

		return Promise.all([loadingPromise, pollingPromise]);
	}
}

export default DocumentLibraryOpener;

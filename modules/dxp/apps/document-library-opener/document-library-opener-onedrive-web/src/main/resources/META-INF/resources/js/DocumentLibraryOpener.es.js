/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {
	fetch,
	navigate,
	openSimpleInputModal,
	openToast
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
		Liferay.Util.getWindow(this._dialogLoadingId).hide();
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
			.then(response => {
				if (!response.ok) {
					throw DEFAULT_ERROR;
				}

				return response.json();
			})
			.then(response => {
				if (response.complete) {
					this._openExternal({
						externalURL: response.office365EditURL
					});
				} else if (response.error) {
					throw DEFAULT_ERROR;
				} else {
					return new Promise(resolve => {
						setTimeout(() => {
							this._polling({statusURL}).then(resolve);
						}, TIME_POLLING);
					});
				}
			})
			.catch(error => {
				this._showError(error);
			});
	}

	_showError(message) {
		openToast({
			message,
			title: Liferay.Language.get('error'),
			type: 'danger'
		});
	}

	_showLoading({dialogMessage}) {
		return new Promise(resolve => {
			Liferay.Util.openWindow(
				{
					dialog: {
						bodyContent: `<p>${dialogMessage}</p><div aria-hidden="true" class="loading-animation"></div>`,
						cssClass: 'office-365-redirect-modal',
						height: 172,
						modal: true,
						resizable: false,
						title: '',
						width: 320
					},
					id: this._dialogLoadingId
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
					'the-document-has-been-checked-out-please-on-finish-editing-check-in-the-document-to-save-changes-into-the-document-library'
				),
				style: 'info',
				title: Liferay.Language.get('info')
			},
			dialogTitle,
			formSubmitURL,
			mainFieldLabel: Liferay.Language.get('title'),
			mainFieldName: 'title',
			namespace: this._namespace,
			onFormSuccess: serverResponseContent => {
				if (serverResponseContent.oneDriveBackgroundTaskStatusURL) {
					this.open({
						dialogMessage: serverResponseContent.dialogMessage,
						refresh: true,
						statusURL:
							serverResponseContent.oneDriveBackgroundTaskStatusURL
					});
				}
			},
			spritemap:
				Liferay.ThemeDisplay.getPathThemeImages() + '/lexicon/icons.svg'
		});
	}

	edit({formSubmitURL}) {
		this._refreshAfterNavigate = true;

		const loadingPromise = this._showLoading({
			dialogMessage: Liferay.Language.get(
				'you-are-being-redirected-to-an-external-editor-to-edit-this-document'
			)
		});

		const fetchPromise = fetch(formSubmitURL)
			.then(response => {
				if (!response.ok) {
					throw DEFAULT_ERROR;
				}

				return response.json();
			})
			.then(response => {
				if (response.redirectURL) {
					navigate(response.redirectURL);
				} else if (response.oneDriveBackgroundTaskStatusURL) {
					return this._polling({
						statusURL: response.oneDriveBackgroundTaskStatusURL
					});
				} else if (response.error) {
					throw response.error.errorMessage || DEFAULT_ERROR;
				}
			})
			.catch(error => {
				this._showError(error);
			});

		return Promise.all([loadingPromise, fetchPromise]);
	}

	open({
		dialogMessage = Liferay.Language.get(
			'you-are-being-redirected-to-an-external-editor-to-edit-this-document'
		),
		statusURL,
		refresh = false
	}) {
		this._refreshAfterNavigate = refresh;

		const loadingPromise = this._showLoading({
			dialogMessage
		});
		const pollingPromise = this._polling({statusURL});

		return Promise.all([loadingPromise, pollingPromise]);
	}
}

export default DocumentLibraryOpener;

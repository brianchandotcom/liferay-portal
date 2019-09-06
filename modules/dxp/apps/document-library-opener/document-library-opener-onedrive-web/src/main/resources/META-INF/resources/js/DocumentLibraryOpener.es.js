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
		this._documentURL = '';
		this._isTimeConsumed = false;
		this._refreshAfterNavigate = false;
	}

	_hideLoading() {
		this._documentURL = '';
		this._isTimeConsumed = false;

		Liferay.Util.getWindow(this._dialogLoadingId).hide();
	}

	_navigate() {
		if (this._documentURL && this._isTimeConsumed) {
			window.open(this._documentURL);
			this._hideLoading();

			if (this._refreshAfterNavigate) {
				this._refreshAfterNavigate = false;
				Liferay.Portlet.refresh(`#p_p_id${this._namespace}`);
			}
		}
	}

	_polling({pollingURL}) {
		return fetch(pollingURL)
			.then(response => {
				if (!response.ok) {
					throw DEFAULT_ERROR;
				}

				return response.json();
			})
			.then(response => {
				if (response.complete) {
					this._documentURL = response.office365EditURL;

					this._navigate();
				} else if (response.error) {
					throw DEFAULT_ERROR;
				} else {
					return new Promise(resolve => {
						setTimeout(() => {
							this._polling({pollingURL}).then(resolve);
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

	_showLoading({dialogMessage, pollingURL}) {
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
					setTimeout(() => {
						this._isTimeConsumed = true;
						this._navigate();
						resolve();
					}, TIME_SHOW_MSG);

					if (pollingURL) {
						this._polling({pollingURL});
					}
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
			spritemap:
				Liferay.ThemeDisplay.getPathThemeImages() + '/lexicon/icons.svg'
		}).on('formSuccess', serverResponseContent => {
			if (serverResponseContent.oneDriveBackgroundTaskStatusURL) {
				this.open({
					dialogMessage: serverResponseContent.dialogMessage,
					pollingURL:
						serverResponseContent.oneDriveBackgroundTaskStatusURL,
					refresh: true
				});
			}
		});
	}

	edit({formSubmitURL}) {
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
						pollingURL: response.oneDriveBackgroundTaskStatusURL
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
		pollingURL,
		refresh = false
	}) {
		this._refreshAfterNavigate = refresh;

		return this._showLoading({dialogMessage, pollingURL});
	}
}

export default DocumentLibraryOpener;

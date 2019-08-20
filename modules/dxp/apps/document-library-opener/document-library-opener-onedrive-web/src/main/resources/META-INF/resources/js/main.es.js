import {
	fetch,
	openSimpleInputModal,
	openToast,
	navigate
} from 'frontend-js-web';

var TIME_POLLING = 500;
var TIME_SHOW_MSG = 2000;
const DEFAULT_ERROR = Liferay.Language.get('an-unexpected-error-occurred');

class DocumentLibraryOpener {
	constructor({namespace}) {
		this.namespace = namespace;

		this.dialogLoadingId = `${namespace}OneDriveLoadingDialog`;
		this.documentURL = '';
		this.isTimeConsumed = false;
		this.refreshAfterNavigate = false;
	}

	createWithName({dialogTitle, formSubmitURL}) {
		const {namespace} = this;

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
			namespace,
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
		this.showLoading({
			dialogMessage: Liferay.Language.get(
				'you-are-being-redirected-to-an-external-editor-to-edit-this-document'
			)
		});

		fetch(formSubmitURL)
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
					this.polling({
						pollingURL: response.oneDriveBackgroundTaskStatusURL
					});
				} else if (response.error) {
					throw DEFAULT_ERROR;
				}
			})
			.catch(error => {
				this.showError(error);
			});
	}

	hideLoading() {
		this.documentURL = '';
		this.isTimeConsumed = false;

		Liferay.Util.getWindow(this.dialogLoadingId).hide();
	}

	navigate() {
		if (this.documentURL && this.isTimeConsumed) {
			window.open(this.documentURL);
			this.hideLoading();

			if (this.refreshAfterNavigate) {
				this.refreshAfterNavigate = false;
				Liferay.Portlet.refresh(`#p_p_id${this.namespace}`);
			}
		}
	}

	open({
		dialogMessage = Liferay.Language.get(
			'you-are-being-redirected-to-an-external-editor-to-edit-this-document'
		),
		pollingURL,
		refresh = false
	}) {
		this.refreshAfterNavigate = refresh;

		this.showLoading({dialogMessage, pollingURL});
	}

	polling({pollingURL}) {
		fetch(pollingURL)
			.then(response => {
				if (!response.ok) {
					throw DEFAULT_ERROR;
				}

				return response.json();
			})
			.then(response => {
				if (response.complete) {
					this.documentURL = response.office365EditURL;

					this.navigate();
				} else if (response.error) {
					throw DEFAULT_ERROR;
				} else {
					setTimeout(() => {
						this.polling({pollingURL});
					}, TIME_POLLING);
				}
			})
			.catch(error => {
				this.showError(error);
			});
	}

	showError(message) {
		openToast({
			message,
			title: Liferay.Language.get('error'),
			type: 'danger'
		});
	}

	showLoading({dialogMessage, pollingURL}) {
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
				id: this.dialogLoadingId
			},
			() => {
				setTimeout(() => {
					this.isTimeConsumed = true;
					this.navigate();
				}, TIME_SHOW_MSG);

				if (pollingURL) {
					this.polling({pollingURL});
				}
			}
		);
	}
}

export default DocumentLibraryOpener;

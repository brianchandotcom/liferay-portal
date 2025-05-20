/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function adjustModalSize({
	contentHeight = 0,
	footerHeight = 73,
	headerHeight = 73,
}) {
	const modalBodyIframe =
		window.top.document.querySelector('.modal-body-iframe');
	const totalIframeHeight = contentHeight + footerHeight + headerHeight;

	if (modalBodyIframe && modalBodyIframe?.offsetHeight < totalIframeHeight) {
		modalBodyIframe.style.height =
			contentHeight + footerHeight + headerHeight + 'px';
	}
}

function closeModal(isSuccessful, redirectURL = '') {
	const eventDetail = {};

	if (isSuccessful) {
		eventDetail.successNotification = {
			message: Liferay.Language.get(
				'your-request-completed-successfully'
			),
			showSuccessNotification: true,
		};
	}

	if (redirectURL) {
		eventDetail.redirectURL = redirectURL;
	}

	window.parent.Liferay.fire('close-modal', eventDetail);
}

function onKeyUp(event, redirectURL) {
	event.preventDefault();

	if (event.key === 'Escape') {
		closeModal(false, redirectURL);
	}
}

function onDelegateClick(event) {
	const isCancelButton = event.target?.closest('.modal-closer') ?? null;

	if (isCancelButton) {
		window.top.Liferay.fire('close-modal');

		return;
	}

	return event;
}

function onFormSubmit(iframeForm, event) {
	window.top.Liferay.fire('is-loading-modal', {isLoading: true});

	const formWrapper = Liferay.Form.get(iframeForm.id);

	if (
		!formWrapper ||
		!formWrapper.formValidator ||
		!formWrapper.formValidator.validate
	) {
		window.top.Liferay.fire('is-loading-modal', {
			isLoading: false,
		});

		return;
	}

	formWrapper.formValidator.validate();

	if (formWrapper.formValidator.hasErrors()) {
		window.top.Liferay.fire('is-loading-modal', {
			isLoading: false,
		});

		return;
	}

	return event;
}

export default function ModalContentHandler({
	redirectURL,
	requestProcessed = false,
	useNativeSubmit = true,
}) {
	if (requestProcessed) {
		closeModal(true, redirectURL);

		return;
	}
	else {
		window.top.Liferay.fire('is-loading-modal', {isLoading: false});
	}

	const iframeWrapper = window.document.querySelector(
		'.modal-iframe-wrapper'
	);

	const iframeForm = iframeWrapper.querySelector('form');

	window.addEventListener('click', onDelegateClick);
	window.addEventListener('keyup', onKeyUp);

	const iframeFooter = iframeWrapper.querySelector('.modal-iframe-footer');

	iframeForm.appendChild(iframeFooter);

	if (useNativeSubmit) {
		iframeForm.addEventListener(
			'submit',
			onFormSubmit.bind(this, iframeForm)
		);
	}

	const iframeContent = iframeWrapper.querySelector('.modal-iframe-content');
	const iframeHeader = iframeWrapper.querySelector('.modal-iframe-header');

	adjustModalSize({
		contentHeight: iframeContent?.offsetHeight,
		footerHeight: iframeFooter?.offsetHeight,
		headerHeight: iframeHeader?.offsetHeight,
	});

	return {
		dispose() {
			window.removeEventListener('click', onDelegateClick);
			window.removeEventListener('keyup', onKeyUp);

			if (useNativeSubmit) {
				iframeForm.removeEventListener(
					'submit',
					onFormSubmit.bind(this, iframeForm)
				);
			}
		},
	};
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {navigate, objectToFormData, openToast} from 'frontend-js-web';

const INTERACTION_NOTIFICATION = 'notification';
const INTERACTION_PAGE = 'page';
const INTERACTION_URL = 'url';

const TOAST_DATA = {
	error: {
		message: Liferay.Language.get('an-unexpected-error-occurred'),
		title: Liferay.Language.get('error'),
		type: 'danger',
	},
	success: {
		message: Liferay.Language.get('your-request-completed-successfully'),
		title: Liferay.Language.get('success'),
		type: 'success',
	},
};

export default function InfoItemActionHandler({executeInfoItemActionURL}) {
	const url = new URL(window.location.href);

	if (url.searchParams.has('toastData')) {
		try {
			const data = JSON.parse(url.searchParams.get('toastData'));
			openResultToast(data);
		}
		catch (error) {
			if (process.env.NODE_ENV === 'development') {
				console.error(error);
			}
		}

		url.searchParams.delete('toastData');

		history.replaceState(null, document.head.title, url.href);
	}

	const triggers = document.querySelectorAll(
		'[data-lfr-editable-type="action"]'
	);

	const onClick = (event) => {
		triggerAction(event.target, executeInfoItemActionURL);
	};

	triggers.forEach((trigger) => {
		trigger.addEventListener('click', onClick);
	});

	return {
		dispose() {
			triggers.forEach((trigger) => {
				trigger.removeEventListener('click', onClick);
			});
		},
	};
}

function triggerAction(trigger, executeInfoItemActionURL) {
	const {
		lfrClassNameId: classNameId,
		lfrClassPk: classPK,
		lfrFieldId: fieldId,
		lfrOnErrorInteraction: errorInteraction,
		lfrOnErrorPageUrl: errorPageURL,
		lfrOnErrorReload: errorReload,
		lfrOnErrorText: errorText,
		lfrOnSuccessInteraction: successInteraction,
		lfrOnSuccessPageUrl: successPageURL,
		lfrOnSuccessReload: successReload,
		lfrOnSuccessText: successText,
	} = trigger.dataset;

	if (!fieldId) {
		return;
	}

	const loadingIndicator = getLoadingIndicator();

	trigger.classList.add('disabled');
	trigger.setAttribute('disabled', '');
	trigger.appendChild(loadingIndicator);

	Liferay.Util.fetch(new URL(executeInfoItemActionURL), {
		body: objectToFormData({
			classNameId,
			classPK,
			fieldId,
		}),
		method: 'POST',
	})
		.then((response) => response.json())
		.then(({error}) => {
			trigger.classList.remove('disabled');
			trigger.removeAttribute('disabled');
			trigger.removeChild(loadingIndicator);

			if (error) {
				handleResult(
					errorInteraction,
					errorReload,
					errorText || error,
					TOAST_DATA.error,
					errorPageURL
				);
			}
			else {
				handleResult(
					successInteraction,
					successReload,
					successText,
					TOAST_DATA.success,
					successPageURL
				);
			}
		})
		.catch(() => {
			trigger.classList.remove('disabled');
			trigger.removeAttribute('disabled');
			trigger.removeChild(loadingIndicator);

			openResultToast(TOAST_DATA.error);
		});
}

function getLoadingIndicator() {
	const element = document.createElement('span');

	element.classList.add(
		'd-inline-block',
		'loading-animation',
		'loading-animation-light',
		'loading-animation-sm',
		'ml-2',
		'my-0'
	);

	return element;
}

function handleResult(interaction, reload, text, toastData, url) {
	if (interaction === INTERACTION_NOTIFICATION) {
		if (reload) {
			const reloadURL = new URL(window.location.href);

			const data = {...toastData, message: text || toastData.message};

			reloadURL.searchParams.set('toastData', JSON.stringify(data));

			navigate(reloadURL);
		}
		else {
			openResultToast(toastData, text);
		}
	}
	else if ([INTERACTION_PAGE, INTERACTION_URL].includes(interaction)) {
		navigate(url);
	}
	else if (reload) {
		window.location.reload();
	}
}

function openResultToast({message, title, type}, text) {
	openToast({
		message: text || message,
		title,
		type,
	});
}

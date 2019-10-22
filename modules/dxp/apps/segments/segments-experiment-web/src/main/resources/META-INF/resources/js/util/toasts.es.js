import {openToast} from 'frontend-js-web';

export function openErrorToast() {
	openToast({
		message: Liferay.Language.get('an-unexpected-error-occurred'),
		title: Liferay.Language.get('error'),
		type: 'danger'
	});
}

export function openSuccessToast() {
	openToast({
		message: Liferay.Language.get('your-request-completed-successfully')
	});
}

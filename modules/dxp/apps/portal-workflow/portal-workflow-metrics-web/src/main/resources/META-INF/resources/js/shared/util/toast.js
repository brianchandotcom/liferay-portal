import liferayToast from 'frontend-js-web/liferay/toast/commands/OpenToast.es';

export function openErrorToast({message}) {
	openToast(message, Liferay.Language.get('error'), 'danger');
}

export function openSuccessToast(
	message,
	title = Liferay.Language.get('success')
) {
	openToast(message, title, 'success');
}

export function openToast(message, title, type) {
	liferayToast({message, title, type});
}

import {STORAGE_KEYS, Storage} from '~/common/services/liferay/storage';

export function getApplicationIdSearchParam() {
	const searchParams = new URLSearchParams(window.location.search);
	const applicationId = searchParams.get('applicationId');

	return applicationId;
}

export function getLoadedContentFlag() {
	return {
		applicationId: getApplicationIdSearchParam(),
		backToEdit:
			Storage.getItem(STORAGE_KEYS.BACK_TO_EDIT) &&
			JSON.parse(Storage.getItem(STORAGE_KEYS.BACK_TO_EDIT)),
	};
}

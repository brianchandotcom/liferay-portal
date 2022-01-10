import {getLiferaySiteName} from './liferay';

export function getWebDavUrl() {
	const siteName = getLiferaySiteName().replace('/web/', '');

	return `/webdav/${siteName}/document_library/`;
}

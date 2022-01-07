import {LiferayService} from '../services/liferay';

const siteName = LiferayService.getLiferaySiteName().replace('/web/', '');

export function getWebDavUrl() {
	return `/webdav/${siteName}/document_library/`;
}

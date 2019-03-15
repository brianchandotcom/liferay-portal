import {jsonToUrl} from '../../util/url';

/**
 * @module redirect
 * @description Redirects URL using the local router implementation.
 * @example
 * import redirect from '@/shared/components/router/redirect';
 * redirect('sla-list', 'SLAs', {processId: 10});
 */
const redirect = (to, query) => {
	let url = String(document.location);

	url = url.substring(0, url.lastIndexOf('#'));

	window.location.href = `${url}${jsonToUrl({query, to})}`;
};

export default redirect;
<#list apiContexts?sort_by("key") as apiContext>
export * from './${apiContext.value.classname?uncap_first}';
import { ${apiContext.value.classname} } from './${apiContext.value.classname?uncap_first}';
</#list>

import * as http from 'http';
export class HttpError extends Error {
	constructor (public response: http.IncomingMessage, public body: any, public statusCode?: number) {
		super('HTTP request failed');
		this.name = 'HttpError';
	}
}

export { RequestFile } from '../model/models';

export const APIS = [<#list apiContexts?sort_by("key") as apiContext>${apiContext.value.classname}<#if apiContext_has_next>, </#if></#list>];
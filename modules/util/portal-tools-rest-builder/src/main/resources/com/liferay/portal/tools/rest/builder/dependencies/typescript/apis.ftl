<#list apiContexts as apiContext>
export * from './${apiContext.classname?uncap_first}';
import { ${apiContext.classname} } from './${apiContext.classname?uncap_first}';
</#list>

import * as http from 'http';
export class HttpError extends Error {
	constructor (public response: http.IncomingMessage, public body: any, public statusCode?: number) {
		super('HTTP request failed');
		this.name = 'HttpError';
	}
}

export const APIS = [<#list apiContexts as apiContext>${apiContext.classname}<#if apiContext_has_next>, </#if></#list>];
import * as http from 'http';

<#list apiContexts?sort_by("classname") as apiContext>
	import {${apiContext.classname}} from './${apiContext.classname?uncap_first}';
</#list>

<#list apiContexts?sort_by("classname") as apiContext>
	export * from './${apiContext.classname?uncap_first}';
</#list>

/**
 * @author ${configYAML.author}
 * @generated
 */

export class HttpError extends Error {
	constructor(
		public response: http.IncomingMessage,
		public body: any,
		public statusCode?: number
	) {
		super('HTTP request failed');
		this.name = 'HttpError';
	}
}

export const APIS = [
<#list apiContexts?sort_by("classname") as apiContext>
	${apiContext.classname},
</#list>
];
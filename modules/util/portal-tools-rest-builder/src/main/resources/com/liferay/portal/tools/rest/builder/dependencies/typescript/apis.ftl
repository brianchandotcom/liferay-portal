<#list apiContexts?sort_by("className") as apiContext>
	import {${apiContext.className}} from './${apiContext.className?uncap_first}';
</#list>

<#list apiContexts?sort_by("className") as apiContext>
	export * from './${apiContext.className?uncap_first}';
</#list>

/**
 * @author ${configYAML.author}
 * @generated
 */

export class HttpError extends Error {
	constructor(
		public body: any,
		public response: Response,
		public statusCode: number
	) {
		super('HTTP request failed');
		this.name = 'HttpError';
	}
}

export {RequestFile} from '../model/models';
export const APIS = [
<#list apiContexts?sort_by("className") as apiContext>
	${apiContext.className},
</#list>
];
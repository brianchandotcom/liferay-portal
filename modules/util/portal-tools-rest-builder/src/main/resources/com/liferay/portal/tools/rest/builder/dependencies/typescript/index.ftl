/**
 * @author ${configYAML.author}
 * @generated
 */

<#list apiContexts?sort_by("className") as apiContext>
	export {${apiContext.className}} from './apis/${apiContext.className}';
</#list>

<#list schemas?keys?sort as schema>
	export {${schema}} from './models/${schema}';
</#list>
import {ObjectSerializer} from '../model/models';

<#if importClasses??>
	<#list importClasses?sort as import>
		<#if stringUtil.equals(import, "RequestFile")>
			<#assign importRequestFile = true />
		<#else>
			import {${import}} from '../model/${import?uncap_first}';
		</#if>
	</#list>
</#if>

import {HttpError<#if importRequestFile??>, RequestFile</#if>} from './apis';

/**
 * @author ${configYAML.author}
 * @generated
 */

export class ${className} {
	protected _basePath: string;
	protected _defaultHeaders: any = {};

	constructor(basePath?: string) {
		if (basePath) {
			this._basePath = basePath;
		}
	}

	set defaultHeaders(defaultHeaders: any) {
		this._defaultHeaders = defaultHeaders;
	}

	<#list operationsData?sort_by("operationId") as operationData>
		/**
		 * ${operationData.description!}
		 <#if operationData.parameters??>
			 <#list operationData.parameters as parameter>
				 * @param ${parameter.name} ${parameter.description!}
			 </#list>
		 </#if>
		 */
		public async ${operationData.operationId}(
			<#if operationData.parameters??>
				<#list operationData.parameters as parameter>
					${parameter.name}${parameter.required?then('', '?')}: ${parameter.dataType},
				</#list>
			</#if>
			headers: {[name: string]: string} = {}
		): Promise<{
			<#if operationData.returnDataType??>
				body: ${operationData.returnDataType};
			<#else>
				body?: any;
			</#if>
			response: Response;
		}> {
			const localVarPath = this._basePath + '${operationData.path}'
				<#list operationData.parameters as parameter>
					<#if stringUtil.equals(parameter.type, "path")>
						.replace('{${parameter.name}}',encodeURIComponent(${parameter.name}))
					</#if>
				</#list>;

			<#if operationData.parameters??>
				<#list operationData.parameters as parameter>
					<#if parameter.required>
						if (${parameter.name} === null || ${parameter.name} === undefined) {
							throw new Error('Required parameter ${parameter.name} was null or undefined when calling ${operationData.operationId}.');
						}
					</#if>
				</#list>
			</#if>

			const localVarQueryParameters: any = {};

			<#list operationData.parameters as parameter>
				<#if stringUtil.equals(parameter.type, "query")>
					if (${parameter.name} !== undefined) {
						localVarQueryParameters['${parameter.name}'] = JSON.stringify(ObjectSerializer.serialize(${parameter.name}, "${parameter.dataType}"));
					}
				</#if>
			</#list>

			const queryString = Object.keys(localVarQueryParameters).length
				? '?' + new URLSearchParams(localVarQueryParameters).toString()
				: '';

			const localVarFormParams: any = {};

			<#list operationData.parameters as parameter>
				<#if stringUtil.equals(parameter.type, "form")>
					if (${parameter.name} !== undefined) {
						<#if stringUtil.equals(parameter.dataType, "RequestFile")>
							localVarFormParams['${parameter.name}'] = ${parameter.name};
						<#else>
							localVarFormParams['${parameter.name}'] = JSON.stringify(ObjectSerializer.serialize(${parameter.name}, "${parameter.dataType}"));
						</#if>
					}
				</#if>
			</#list>

			let body;

			<#list operationData.parameters as parameter>
				<#if stringUtil.equals(parameter.type, "body")>
					body = JSON.stringify(ObjectSerializer.serialize(${parameter.name}, "${parameter.dataType}"));
				</#if>
			</#list>

			if (Object.keys(localVarFormParams).length) {
				const formData = new FormData();
				for (const key in localVarFormParams) {
					formData.append(key, localVarFormParams[key]);
				}
				body = formData;
			}

			const response = await fetch(localVarPath + queryString, {
				method: '${operationData.httpMethod}',
				headers:
					Object.assign({}, this._defaultHeaders, headers
					<#if operationData.responseContentTypes?? && operationData.responseContentTypes?has_content>
						,!headers.Accept ? {
							<#if operationData.responseContentTypes?seq_contains("application/json")>
								Accept: 'application/json'
							<#else>
								Accept: '${operationData.responseContentTypes[0]}'
							</#if>
						} : {}
					</#if>
				),
				body: body
			});

			if (response.ok) {
                const contentType = response.headers.get('content-type') || '';

                <#if operationData.returnDataType??>
                    if (contentType.includes('application/json')) {
                        return {body: ObjectSerializer.deserialize(await response.json(), "${operationData.returnDataType}"), response};
                    } else {
                        return {body: await response.text() as any, response};
                    }
                <#else>
                    if (contentType.includes('application/json')) {
                        return {body: await response.json(), response};
                    } else {
                        return {body: await response.text(), response};
                    }
                </#if>
            } else {
                throw new HttpError(
                    await response.text(),
                    response,
                    response.status
                );
            }
		}
	</#list>
}
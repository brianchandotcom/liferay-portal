import {ObjectSerializer} from '../model/models';

<#if importClasses??>
	<#list importClasses?sort as import>
		import {${import}} from '../model/${import?uncap_first}';
	</#list>
</#if>

import {HttpError} from './apis';

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
				 * @param ${parameter.name}
			 </#list>
		 </#if>
		 <#if operationData.bodyParameters?has_content>
		 	<#if (operationData.bodyParameters?keys?size == 1)>
			 	<#list operationData.bodyParameters?keys as requestBodyContentType>
					<#assign firstRequestBodyContentType = requestBodyContentType />
			 	</#list>
			 	<#list operationData.bodyParameters[firstRequestBodyContentType] as bodyParameter>
				 	* @param ${bodyParameter.name}
			 	</#list>
		 	<#else>
		 		* @param requestBody Request body that can be one of multiple content types
		 	</#if>
		 </#if>
		 * @param headers Optional custom request headers
		 */
		public async ${operationData.operationId}<#if operationData.bodyParameters?has_content && (operationData.bodyParameters?keys?size > 1)>WithContentType</#if>(
			<#if operationData.parameters??>
				<#list operationData.parameters as parameter>
					${parameter.name}${parameter.required?then('', '?')}: ${parameter.dataType},
				</#list>
				<#if operationData.bodyParameters?has_content && (operationData.bodyParameters?keys?size == 1)>
					<#list operationData.bodyParameters[firstRequestBodyContentType] as bodyParameter>
						${bodyParameter.name}${bodyParameter.required?then('', '?')}: ${bodyParameter.dataType},
					</#list>
				</#if>
			</#if>
			<#if operationData.bodyParameters?has_content && (operationData.bodyParameters?keys?size > 1)>
				requestBody:
					<#list operationData.bodyParameters?keys as requestBodyContentType>
						{
									type: '${requestBodyContentType}',
									parameters: {
										<#list operationData.bodyParameters[requestBodyContentType] as bodyParameter>
											${bodyParameter.name}${bodyParameter.required?then('', '?')}: ${bodyParameter.dataType}<#if bodyParameter?has_next>,</#if>
										</#list>
									}
						}
						<#if requestBodyContentType?has_next>
							|
						<#else>
							,
						</#if>
					</#list>
			</#if>
			headers?: {[name: string]: string}
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

			<#if operationData.bodyParameters?has_content>
				let body;
				<#if (operationData.bodyParameters?keys?size > 1)>
					<#list operationData.bodyParameters?keys as requestBodyContentType>
						if (requestBody.type === '${requestBodyContentType}') {
							<#if requestBodyContentType == 'multipart/form-data'>
								const formData = new FormData();
								<#list operationData.bodyParameters[requestBodyContentType] as bodyParameter>
									<#if stringUtil.equals(bodyParameter.dataType, "File")>
										formData.append('${bodyParameter.name}', requestBody.parameters.${bodyParameter.name});
									<#else>
										formData.append('${bodyParameter.name}', JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.${bodyParameter.name}, "${bodyParameter.dataType}")));
									</#if>
								</#list>
								body = formData;
							<#else>
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.${operationData.bodyParameters[requestBodyContentType][0].name}, "${operationData.bodyParameters[requestBodyContentType][0].dataType}"));
							</#if>
						}
					</#list>
				<#else>
					<#if firstRequestBodyContentType == 'multipart/form-data'>
						const formData = new FormData();
						<#list operationData.bodyParameters[firstRequestBodyContentType] as bodyParameter>
							<#if stringUtil.equals(bodyParameter.dataType, "File")>
								formData.append('${bodyParameter.name}', requestBody.parameters.${bodyParameter.name});
							<#else>
								formData.append('${bodyParameter.name}', JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.${bodyParameter.name}, "${bodyParameter.dataType}")));
							</#if>
						</#list>
						body = formData;
					<#else>
						body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.${operationData.bodyParameters[firstRequestBodyContentType][0].name}, "${operationData.bodyParameters[firstRequestBodyContentType][0].dataType}"));
					</#if>
				</#if>
			</#if>

			const response = await fetch(localVarPath + queryString, {
				method: '${operationData.httpMethod}',
				headers:
					Object.assign({}, this._defaultHeaders
					<#if operationData.responseContentTypes?? && operationData.responseContentTypes?has_content>
						,{
							<#if operationData.responseContentTypes?seq_contains("application/json")>
								Accept: 'application/json'
							<#else>
								Accept: '${operationData.responseContentTypes[0]}'
							</#if>
						}
					</#if>
					<#if operationData.bodyParameters?has_content>
						,(requestBody && requestBody.type !== 'multipart/form-data') ? {
							'Content-Type': requestBody.type
						} : {}
					</#if>
					,headers || {}
					)
				<#if operationData.bodyParameters?has_content>
					,body: body
				</#if>
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

		<#if operationData.bodyParameters?has_content && (operationData.bodyParameters?keys?size > 1)>
			<#list operationData.bodyParameters?keys as requestBodyContentType>
				<#if requestBodyContentType == 'application/json'>
					/**
					 * ${operationData.description!} - Default method for JSON body
					 <#if operationData.parameters??>
						 <#list operationData.parameters as parameter>
							 * @param ${parameter.name}
						 </#list>
					 </#if>
					 <#list operationData.bodyParameters[requestBodyContentType] as bodyParameter>
						 * @param ${bodyParameter.name}
					 </#list>
					 */
					public async ${operationData.operationId}(
						<#if operationData.parameters??>
							<#list operationData.parameters as parameter>
								${parameter.name}${parameter.required?then('', '?')}: ${parameter.dataType},
							</#list>
						</#if>
						<#list operationData.bodyParameters[requestBodyContentType] as bodyParameter>
							${bodyParameter.name}${bodyParameter.required?then('', '?')}: ${bodyParameter.dataType},
						</#list>
						headers?: {[name: string]: string}
					): Promise<{
						<#if operationData.returnDataType??>
							body: ${operationData.returnDataType};
						<#else>
							body?: any;
						</#if>
						response: Response;
					}> {
						return this.${operationData.operationId}WithContentType(
							<#if operationData.parameters??>
								<#list operationData.parameters as parameter>
									${parameter.name},
								</#list>
							</#if>
							{
								type: 'application/json',
								parameters: {
									<#list operationData.bodyParameters[requestBodyContentType] as bodyParameter>
										${bodyParameter.name}: ${bodyParameter.name}<#if bodyParameter?has_next>,</#if>
									</#list>
								}
							},
							headers
						);
					}
				</#if>
			</#list>
		</#if>
	</#list>
}
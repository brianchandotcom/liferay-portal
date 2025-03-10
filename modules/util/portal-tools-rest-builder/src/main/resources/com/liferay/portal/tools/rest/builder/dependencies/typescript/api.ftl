import http from 'http';

import localVarRequest from 'request';
/* tslint:disable:no-unused-locals */
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
const defaultBasePath = 'http://localhost';

/**
 * @author ${configYAML.author}
 * @generated
 */

export class ${className} {
	protected _basePath = defaultBasePath;
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
			options: {
				headers: {[name: string]: string};
			} = {headers: {}}
		): Promise<{
			<#if operationData.returnDataType??>
				body: ${operationData.returnDataType};
			<#else>
				body?: any;
			</#if>
			response: http.IncomingMessage;
		}> {
			const localVarPath = this._basePath + '${operationData.path}'
				<#list operationData.parameters as parameter>
					<#if stringUtil.equals(parameter.type, "path")>
						.replace(
							'{' + '${parameter.name}' + '}',
							encodeURIComponent(String(${parameter.name}))
						)
					</#if>
				</#list>;
			const localVarQueryParameters: any = {};
			const localVarHeaderParams: any = (<any>Object).assign({}, this._defaultHeaders);
			<#if operationData.responseContentTypes?? && operationData.responseContentTypes?has_content>
				const responseContentTypes = [<#list operationData.responseContentTypes as responseContentType>'${responseContentType}'<#sep>, </#list>];
				if (responseContentTypes.indexOf('application/json') >= 0) {
					localVarHeaderParams.Accept = 'application/json';
				} else {
					localVarHeaderParams.Accept = responseContentTypes.join(',');
				}
			</#if>
			const localVarFormParams: any = {};

			<#if operationData.parameters??>
				<#list operationData.parameters as parameter>
					<#if parameter.required>
						if (${parameter.name} === null || ${parameter.name} === undefined) {
							throw new Error('Required parameter ${parameter.name} was null or undefined when calling ${operationData.operationId}.');
						}
					</#if>
				</#list>
			</#if>
			<#list operationData.parameters as parameter>
				<#if stringUtil.equals(parameter.type, "query")>
					if (${parameter.name} !== undefined) {
						localVarQueryParameters['${parameter.name}'] = ObjectSerializer.serialize(${parameter.name}, "${parameter.dataType}");
					}
				</#if>
			</#list>
			(<any>Object).assign(localVarHeaderParams, options.headers);

			<#assign localVarUseFormData = false />
			<#list operationData.parameters as parameter>
				<#if stringUtil.equals(parameter.type, "form")>
					if (${parameter.name} !== undefined) {
						<#if stringUtil.equals(parameter.dataType, "RequestFile")>
							<#assign localVarUseFormData = true />
							localVarFormParams['${parameter.name}'] = ${parameter.name};
						<#else>
							localVarFormParams['${parameter.name}'] = ObjectSerializer.serialize(${parameter.name}, "${parameter.dataType}");
						</#if>
					}
				</#if>
			</#list>

			const localVarRequestOptions: localVarRequest.Options = {
				<#list operationData.parameters as parameter>
					<#if stringUtil.equals(parameter.type, "body")>
						body: ObjectSerializer.serialize(${parameter.name}, "${parameter.dataType}"),
					</#if>
				</#list>
				headers: localVarHeaderParams,
				json: true,
				method: '${operationData.httpMethod}',
				qs: localVarQueryParameters,
				uri: localVarPath,
			};

			if (Object.keys(localVarFormParams).length) {
				<#if localVarUseFormData>
					(<any>localVarRequestOptions).formData = localVarFormParams;
				<#else>
					localVarRequestOptions.form = localVarFormParams;
				</#if>
			}
			return new Promise<{ <#if operationData.returnDataType??> body: ${operationData.returnDataType};<#else> body?: any;</#if> response: http.IncomingMessage;}>((resolve, reject) => {
				localVarRequest(localVarRequestOptions, (error, response, body) => {
					if (error) {
						reject(error);
					}
					else {
						if (
							response.statusCode &&
							response.statusCode >= 200 &&
							response.statusCode <= 299
						) {
							<#if operationData.returnDataType??>
								body = ObjectSerializer.deserialize(body, "${operationData.returnDataType}");
							</#if>
							resolve({body, response});
						}
						else {
							reject(
								new HttpError(
									body,
									response,
									response.statusCode
								)
							);
						}
					}
				});
			});
		}
	</#list>
}
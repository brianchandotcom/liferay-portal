<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/html/portal/api/jsonws/init.jsp" %>

<%
String signature = ParamUtil.getString(request, "signature");
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(signature) %>">
		<div class="lfr-api-method lfr-api-section">

			<%
			JSONWebServiceActionMapping jsonWebServiceActionMapping = JSONWebServiceActionsManagerUtil.getJSONWebServiceActionMapping(signature);

			String invocationPath = jsonWebServiceActionMapping.getPath();

			if (Validator.isNotNull(contextPath)) {
				invocationPath = invocationPath.substring(1);
				invocationPath = jsonWebServiceActionMapping.getContextPath() + StringPool.PERIOD + invocationPath;
			}
			%>

			<h2><%= invocationPath %></h2>

			<dl class="lfr-api-http-method">
				<dt>
					<liferay-ui:message key="http-method" />
				</dt>
				<dd class="lfr-action-label">
					<%= jsonWebServiceActionMapping.getMethod() %>
				</dd>
			</dl>

			<%
			Class<?> actionClass = jsonWebServiceActionMapping.getActionClass();

			String actionClassName = actionClass.getName();

			int pos = actionClassName.lastIndexOf(CharPool.PERIOD);

			Method actionMethod = jsonWebServiceActionMapping.getActionMethod();
			%>

			<div class="lfr-api-param">
				<span class="lfr-api-param-name">
					<%= actionClassName.substring(0, pos) %>.<span class="class-name"><%= actionClassName.substring(pos + 1) %></span>
				</span>

				<%
				Class serviceClass = actionClass;

				if (actionClassName.contains(".service.") && actionClassName.endsWith("ServiceUtil")) {

					String implClassName = StringUtil.replace(
						actionClassName, new String[] {".service.", "ServiceUtil"},
						new String[] {".service.impl.", "ServiceImpl"});

					try {
						serviceClass = JavadocUtil.loadClass(actionClass.getClassLoader(), implClassName);
					}
					catch (Exception e) {
					}
				}

				JavadocClass javadocClass = JavadocManagerUtil.lookupJavadocClass(serviceClass);

				String comment = null;

				if (javadocClass != null) {
					comment = javadocClass.getComment();
				}
				%>

				<c:if test="<%= Validator.isNotNull(comment) %>">
					<p class="lfr-api-param-comment">
						<%= comment %>
					</p>
				</c:if>

			</div>
			<div class="lfr-api-param">
				<span class="lfr-api-param-name">
					<span class="method-name"><%= actionMethod.getName() %></span>
				</span>

				<%
				JavadocMethod javadocMethod = JavadocManagerUtil.lookupJavadocMethod(actionMethod);

				comment = null;

				if (javadocMethod != null) {
					comment = javadocMethod.getComment();
				}
				%>

				<c:if test="<%= Validator.isNotNull(comment) %>">
					<p class="lfr-api-param-comment">
						<%= comment %>
					</p>
				</c:if>
			</div>
		</div>

		<div class="lfr-api-parameters lfr-api-section">
			<h3><liferay-ui:message key="parameters" /></h3>

			<%
			if (PropsValues.JSON_SERVICE_AUTH_TOKEN_ENABLED) {
			%>

				<div class="lfr-api-param">
					<span class="lfr-api-param-name">
						p_auth
					</span>

					<span class="lfr-action-label lfr-api-param-type">
						String
					</span>

					<p class="lfr-api-param-comment">
						authentication token used to validate the request
					</p>
				</div>

			<%
			}

			MethodParameter[] methodParameters = jsonWebServiceActionMapping.getMethodParameters();

			for (int i = 0; i < methodParameters.length; i++) {
				MethodParameter methodParameter = methodParameters[i];

				Class methodParameterTypeClass = methodParameter.getType();

				String methodParameterTypeClassName = null;

				if (methodParameterTypeClass.isArray()) {
					methodParameterTypeClassName = methodParameterTypeClass.getComponentType() + "[]";
				}
				else {
					methodParameterTypeClassName = methodParameterTypeClass.getName();
				}
			%>

				<div class="lfr-api-param">
					<span class="lfr-api-param-name">
						<%= methodParameter.getName() %>
					</span>

					<span class="lfr-action-label lfr-api-param-type">
						<%= methodParameterTypeClassName %>
					</span>

					<%
					String parameterComment = null;

					if (javadocMethod != null) {
						parameterComment = javadocMethod.getParameterComment(i);
					}
					%>

					<c:if test="<%= Validator.isNotNull(parameterComment) %>">
						<p class="lfr-api-param-comment">
							<%= parameterComment %>
						</p>
					</c:if>
				</div>

			<%
			}
			%>

		</div>

		<div class="lfr-api-return-type lfr-api-section">
			<h3><liferay-ui:message key="return-type" /></h3>

			<div class="lfr-api-param">

				<%
				Class<?> returnTypeClass = actionMethod.getReturnType();

				String returnTypeName = "";

				while (returnTypeClass.isArray()) {
					returnTypeClass = returnTypeClass.getComponentType();

					returnTypeName += "[]";
				}

				returnTypeName = returnTypeClass.getName() + returnTypeName;
				%>

				<span class="lfr-api-param-name">
					<%= returnTypeName %>
				</span>

				<%
				String returnComment = null;

				if (javadocMethod != null) {
					returnComment = javadocMethod.getReturnComment();
				}
				%>

				<c:if test="<%= Validator.isNotNull(returnComment) %>">
					<p class="lfr-api-param-comment">
						<%= returnComment %>
					</p>
				</c:if>
			</div>
		</div>

		<div class="lfr-api-exception lfr-api-section">
			<h3><liferay-ui:message key="exception" /></h3>

			<%
			Class<?>[] exceptionTypeClasses = actionMethod.getExceptionTypes();

			for (int i = 0; i < exceptionTypeClasses.length; i++) {
				Class<?> exceptionTypeClass = exceptionTypeClasses[i];
			%>

				<div class="lfr-api-param">
					<span class="lfr-api-param-name">
						<%= exceptionTypeClass.getName() %>
					</span>

					<%
					String throwsComment = null;

					if (javadocMethod != null) {
						throwsComment = javadocMethod.getThrowsComment(i);
					}
					%>

					<c:if test="<%= Validator.isNotNull(throwsComment) %>">
						<div class="lfr-api-param-comment">
							<%= throwsComment %>
						</div>
					</c:if>
				</div>

			<%
				}
			%>

		</div>

		<div class="lfr-api-execute lfr-api-section">
			<h3><liferay-ui:message key="execute" /></h3>

			<%
			String enctype = StringPool.BLANK;

			for (MethodParameter methodParameter : methodParameters) {
				Class<?> methodParameterTypeClass = methodParameter.getType();

				if (methodParameterTypeClass.equals(File.class)) {
					enctype = "multipart/form-data";

					break;
				}
			}
			%>

			<div class="aui-helper-hidden lfr-api-results" id="serviceResults">
				<liferay-ui:tabs
					names="result,javascript-example,curl-example,url-example"
					refresh="<%= false %>"
				>
					<liferay-ui:section>
						<pre class="lfr-code-block" id="serviceOutput"></pre>
					</liferay-ui:section>
					<liferay-ui:section>
						<pre class="lfr-code-block" id="jsExample"></pre>
					</liferay-ui:section>
					<liferay-ui:section>
						<pre class="lfr-code-block" id="curlExample"></pre>
					</liferay-ui:section>
					<liferay-ui:section>
						<pre class="lfr-code-block" id="urlExample"></pre>
					</liferay-ui:section>
				</liferay-ui:tabs>
			</div>

			<aui:script>
				Liferay.TPL_DATA_TYPES = {
					array: {},
					other: {},
					string: {}
				};
			</aui:script>

			<aui:form action="<%= jsonWSPath + invocationPath %>" enctype="<%= enctype %>" method="<%= jsonWebServiceActionMapping.getMethod() %>" name="execute">

				<%
				if (PropsValues.JSON_SERVICE_AUTH_TOKEN_ENABLED) {
				%>

					<aui:input id='<%= "field" + methodParameters.length %>' label="p_auth" name="p_auth" readonly="true" suffix="String" value="<%= AuthTokenUtil.getToken(request) %>" />

				<%
				}

				for (int i = 0; i < methodParameters.length; i++) {
					MethodParameter methodParameter = methodParameters[i];

					String methodParameterName = methodParameter.getName();

					if (methodParameterName.equals("serviceContext")) {
						continue;
					}

					Class<?> methodParameterTypeClass = methodParameter.getType();

					String methodParameterTypeClassName = null;

					if (methodParameterTypeClass.isArray()) {
						methodParameterTypeClassName = methodParameterTypeClass.getComponentType() + "[]";
					}
					else {
						methodParameterTypeClassName = methodParameterTypeClass.getName();
					}

					if (methodParameterTypeClass.equals(File.class)) {
				%>

						<aui:input id='<%= "field" + i %>' label="<%= methodParameterName %>" name="<%= methodParameterName %>" suffix="<%= methodParameterTypeClassName %>" type="file" />

					<%
					}
					else if (methodParameterTypeClass.equals(boolean.class)) {
					%>

						<aui:field-wrapper label="<%= methodParameterName %>">
							<aui:input checked="<%= true %>" id='<%= "fieldTrue" + i %>' inlineField="<%= true %>" label="true" name="<%= methodParameterName %>" type="radio" value="<%= true %>" />

							<aui:input id='<%= "fieldFalse" + i %>' inlineField="<%= true %>" label="false" name="<%= methodParameterName %>" type="radio" value="<%= false %>" />

							<span class="aui-suffix"><%= methodParameterTypeClassName %></span>
						</aui:field-wrapper>

					<%
					}
					else {
						int size = 10;

						if (methodParameterTypeClass.equals(String.class)) {
							size = 60;
						}
					%>

						<aui:input id='<%= "field" + i %>' label="<%= methodParameterName %>" name="<%= methodParameterName %>" size="<%= size %>" suffix="<%= methodParameterTypeClassName %>" />

				<%
					}
				%>

					<aui:script>

						<%
						String jsObjectType = "other";

						if (methodParameterTypeClass.isArray()) {
							jsObjectType = "array";
						}
						else if (methodParameterTypeClass.equals(String.class)) {
							jsObjectType = "string";
						}
						%>

						Liferay.TPL_DATA_TYPES['<%= jsObjectType %>']['<%= methodParameterName %>'] = true;
					</aui:script>

				<%
				}
				%>

				<aui:button type="submit" value="invoke" />
			</aui:form>
		</div>

		<aui:script use="aui-io,aui-template,querystring-parse">
			var REGEX_QUERY_STRING = new RegExp('([^?=&]+)(?:=([^&]*))?', 'g');

			var form = A.one('#execute');

			var curlTpl = A.Template.from('#curlTpl');
			var scriptTpl = A.Template.from('#scriptTpl');
			var urlTpl = A.Template.from('#urlTpl');

			var tplDataTypes = Liferay.TPL_DATA_TYPES;

			var stringType = tplDataTypes.string;
			var arrayType = tplDataTypes.array;

			var formatDataType = function(key, value, includeNull) {
				value = decodeURIComponent(value.replace(/\+/g, ' '));

				if (stringType[key]) {
					value = '\'' + value + '\'';
				}
				else if (arrayType[key]) {
					if (!value && includeNull) {
						value = 'null';
					}
					else if (value) {
						value = '[' + value + ']';
					}
				}

				return value;
			};

			curlTpl.formatDataType = formatDataType;
			scriptTpl.formatDataType = A.rbind(formatDataType, scriptTpl, true);

			urlTpl.toURIParam = function(value) {
				return A.Lang.String.uncamelize(value, '-').toLowerCase();
			};

			var curlExample = A.one('#curlExample');
			var jsExample = A.one('#jsExample');
			var urlExample = A.one('#urlExample');

			var serviceOutput = A.one('#serviceOutput');
			var serviceResults = A.one('#serviceResults');

			form.on(
				'submit',
				function(event) {
					event.halt();

					var output = A.all([curlExample, jsExample, urlExample, serviceOutput]);

					output.empty().addClass('loading-results');

					var formEl = form.getDOM();

					Liferay.Service(
						'<%= invocationPath %>',
						formEl,
						function(obj) {
							serviceOutput.html(A.JSON.stringify(obj, null, 2));

							output.removeClass('loading-results');

							location.hash = '#serviceResults';
						}
					);

					var formQueryString = A.IO.prototype._serialize(formEl);

					var curlData = [];
					var scriptData = [];

					var ignoreFields = {
						formDate: true,
						p_auth: true
					};

					formQueryString.replace(
						REGEX_QUERY_STRING,
						function(match, key, value) {
							if (!ignoreFields[key]) {
								curlData.push(
									{
										key: key,
										value: value
									}
								);

								scriptData.push(
									{
										key: key,
										value: value
									}
								);
							}
						}
					);

					var tplCurlData = {
						data: curlData
					};

					var tplScriptData = {
						data: scriptData
					};

					curlTpl.render(tplCurlData, curlExample);
					scriptTpl.render(tplScriptData, jsExample);

					var urlTplData = {
						data : [],
						extraData: []
					};

					var extraFields = {
						p_auth: true
					};

					formQueryString.replace(
						REGEX_QUERY_STRING,
						function(match, key, value) {
							if (!ignoreFields[key]) {
								if (!value) {
									key = '-' + key;
								}

								if (extraFields[key]) {
									urlTplData.extraData.push(
										{
											key: key,
											value: value
										}
									);
								}
								else {
									urlTplData.data.push(
										{
											key: key,
											value: value
										}
									);

								}
							}
						}
					);

					urlTpl.render(urlTplData, urlExample);

					serviceResults.show();
				}
			);
		</aui:script>

<textarea class="aui-helper-hidden" id="scriptTpl">
Liferay.Service(
  '<%= invocationPath %>',
  <tpl if="data.length">{
<%= StringPool.FOUR_SPACES %><tpl for="data">{key}: {[this.formatDataType(values.key, values.value)]}<tpl if="!$last">,
<%= StringPool.FOUR_SPACES %></tpl></tpl>
  },
  </tpl>function(obj) {
<%= StringPool.FOUR_SPACES %>console.log(obj);
  }
);
</textarea>

<textarea class="aui-helper-hidden" id="curlTpl">
curl <%= themeDisplay.getPortalURL() + jsonWSPath + invocationPath %> \\
  -u test@liferay.com:test <tpl if="data.length">\\
  <tpl for="data">-d {key}={[this.formatDataType(values.key, values.value)]} <tpl if="!$last">\\
  </tpl></tpl></tpl>
</textarea>

<textarea class="aui-helper-hidden" id="urlTpl">
<%= themeDisplay.getPortalURL() + jsonWSPath + invocationPath %><tpl if="data.length">/<tpl for="data">{key:this.toURIParam}<tpl if="value.length">/{value}</tpl><tpl if="!$last">/</tpl></tpl></tpl><tpl if="extraData.length">?<tpl for="extraData">{key:this.toURIParam}={value}<tpl if="!$last">&amp;</tpl></tpl></tpl>
</textarea>
	</c:when>
	<c:otherwise>
		<div class="portlet-msg-info">
			<liferay-ui:message key="please-select-a-method-on-the-left" />
		</div>
	</c:otherwise>
</c:choose>
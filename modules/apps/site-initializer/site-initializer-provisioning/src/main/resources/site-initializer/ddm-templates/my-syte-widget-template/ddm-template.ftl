<#if entries?has_content>
	<#assign
		commerceOrder = entries?first
		commerceOrderItems = commerceOrder.getCommerceOrderItems()
	/>

	<#if commerceOrderItems?has_content>
		<#assign
			commerceOrderItem = commerceOrderItems?first
			json = commerceOrderItem.getJson()
			cpInstance = commerceOrderItem.fetchCPInstance()
			cpDefinition = cpInstance.getCPDefinition()
		/>

		<div class="d-flex pb-3 pb-lg-6">
			<h1 class="flex-fill">My site</h1>
			<#if commerceOrderContentDisplayContext.getCommerceOrderStatus(commerceOrder) == "Approved">
				<#assign
					labelAction = "label-inverse-success"
				/>
			<#else>
				<#assign
					labelAction = "label-inverse-warning"
				/>
			</#if>

			<div class="mr-2">
				<span class="label label-lg ${labelAction}">
					${commerceOrderContentDisplayContext.getCommerceOrderStatus(commerceOrder)}
				</span>
			</div>

			<a href="http://${getJsonKeyValue(json, 'domain')}" target="_blank">
				Go to site

				<@liferay_ui["icon"]
					icon="shortcut"
					markupView="lexicon"
				/>
			</a>
		</div>

		<form id="liferayProvisioningSiteForm">
			<div class="form-group" id="snGroup">
				<label for="sn">Site name
					<small> (more than 4 characters)</small>
				</label>

				<input class="form-control" id="sn" readonly="" type="text" value="${getJsonKeyValue(json, 'name')}">
			</div>

			<div class="form-group">
				<label for="lod">Liferay Online Domain</label>

				<input class="form-control" id="lod" readonly="" type="text" value="${getJsonKeyValue(json, 'domain')}">
			</div>
		</form>
	</#if>
</#if>

<#function getJsonKeyValue json key>
	<#if validator.isNotNull(json)>
		<#assign jsonArray = jsonFactoryUtil.createJSONArray(json) />

		<#list 0 ..< jsonArray.length() as i>
				<#if jsonArray.get(i).key == key>
					<#return jsonArray.get(i).value.get(0)?trim>
				</#if>
		</#list>
	</#if>

	<#return "">
</#function>
<#assign count = 0 />

<div class="container-fluid container-fluid-max-xl" style="text-align:center">
	<div class="row" style="padding:15px">
		<#if entries?has_content>
			<#list entries as curCommerceAddress>
				<div class="col-md-4" style="text-align:left">
					<#assign
						editURL = commerceAddressDisplayContext.getEditCommerceAddressURL(curCommerceAddress.getCommerceAddressId())

						deleteURL = commerceAddressDisplayContext.getDeleteCommerceAddressURL(curCommerceAddress.getCommerceAddressId())

						editButtonValue = languageUtil.get(request, "edit")

						removeButtonValue = languageUtil.get(request, "remove")

						commerceCountry = curCommerceAddress.getCommerceCountry()
					/>

					<h2 style="color:#000"><strong>${htmlUtil.escape(curCommerceAddress.getName())}</strong></h2>

					<h3 style="color:#000">${htmlUtil.escape(curCommerceAddress.getStreet1())}</h3>

					<h3 style="color:#000">${htmlUtil.escape(curCommerceAddress.getZip())}, ${htmlUtil.escape(curCommerceAddress.getCity())}</h3>

					<#if commerceCountry??>
						<h3 style="color:#000">${htmlUtil.escape(commerceCountry.getName(themeDisplay.getLanguageId()))}</h3>
					</#if>

					<div class="row" style="padding-top:30px">
						<@liferay_aui.button
							cssClass="btn-lg"
							href="${editURL}"
							name="editAddressButton"
							value="${editButtonValue}"
						/>

						<@liferay_aui.button
							cssClass="btn-lg"
							href="${deleteURL}"
							name="removeAddressButton"
							value="${removeButtonValue}"
						/>
					</div>
				</div>

				<#assign count = count + 1 />

				<#if count gte 3>
					</div>

					<div class="row" style="padding:15px">

					<#assign count = 0 />
				</#if>
			</#list>
		</#if>
	</div>

	<div class="row" style="padding:50px 0 15px 0">
		<#assign addButtonValue = languageUtil.get(request, "add-address") />

		<@liferay_aui.button
			cssClass="btn-lg btn-primary"
			href="${commerceAddressDisplayContext.getAddCommerceAddressURL()}"
			name="addAddressButton"
			value="${addButtonValue}"
		/>
	</div>
</div>
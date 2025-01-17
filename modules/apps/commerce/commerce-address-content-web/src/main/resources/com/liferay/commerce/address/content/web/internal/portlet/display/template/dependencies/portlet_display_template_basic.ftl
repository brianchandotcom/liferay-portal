<#assign count = 0 />

<div class="container-fluid container-fluid-max-xl text-center">
	<div class="p-3 row">
		<#if entries?has_content>
			<#list entries as curCommerceAddress>
				<div class="col-md-4 text-left">
					<#assign
						editURL = commerceAddressDisplayContext.getEditCommerceAddressURL(curCommerceAddress.getCommerceAddressId())

						deleteURL = commerceAddressDisplayContext.getDeleteCommerceAddressURL(curCommerceAddress.getCommerceAddressId())

						editButtonValue = languageUtil.get(request, "edit")

						removeButtonValue = languageUtil.get(request, "remove")

						commerceCountry = curCommerceAddress.getCommerceCountry()
					/>

					<h2><strong>${htmlUtil.escape(curCommerceAddress.getName())}</strong></h2>

					<h3>${htmlUtil.escape(curCommerceAddress.getStreet1())}</h3>

					<h3>${htmlUtil.escape(curCommerceAddress.getZip())}, ${htmlUtil.escape(curCommerceAddress.getCity())}</h3>

					<#if country??>
						<h3>${htmlUtil.escape(country.getName(themeDisplay.getLanguageId()))}</h3>
					</#if>

					<div class="pt-4 row">
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

					<div class="p-3 row">

					<#assign count = 0 />
				</#if>
			</#list>
		</#if>
	</div>

	<div class="p-0 pb-1 pt-5 row">
		<#assign addButtonValue = languageUtil.get(request, "add-address") />

		<@liferay_aui.button
			cssClass="btn-lg btn-primary"
			href="${commerceAddressDisplayContext.getAddCommerceAddressURL()}"
			name="addAddressButton"
			value="${addButtonValue}"
		/>
	</div>
</div>
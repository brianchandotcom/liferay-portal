<#include "${templatesPath}/SVG">

<div class="col-12 m-0 product-cards row">
	<#if entries?has_content>
		<#list entries as navigationEntry>
			<#assign
				customFields = navigationEntry.getExpandoAttributes()!{}
				navItemIconId = customFields["SVG Sprite Map ID"]!""
			/>

			<div class="card-container col-12 col-sm-6 col-xl-4 d-flex justify-content-center my-1 p-3 p-xl-4">
				<a class="align-items-center d-flex home-card" href="${navigationEntry.getURL()}">
					<#if navItemIconId?has_content>
						<svg class="icon mr-3">
							<use xlink:href="#${navItemIconId}"></use>
						</svg>
					</#if>

					<h5 class="title">
						${navigationEntry.getName()}
					</h5>
				</a>
			</div>
		</#list>
	</#if>
</div>
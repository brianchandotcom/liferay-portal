<#assign
	bannerStyles = ''
	bannerClasses = ''
	bgColor = BGCOLOR.getData()
	CSSClasses = BANNERCSSCLASSES.getData()
/>

<#if CSSClasses?? && CSSClasses?has_content>
	<#assign
		bannerClasses = ' ${CSSClasses}'
	/>
</#if>

<#if bgColor?? && bgColor?has_content>
	<#assign
		bannerStyles = '${bannerStyles} background-color: ${bgColor};'
	/>
</#if>

<div class="minette-banner row${bannerClasses}" style="${bannerStyles}">
	<div class="col-md-12">
		<div class="container-fluid container-fluid-max-xl">
			${CONTENT.getData()}
		</div>
	</div>
</div>
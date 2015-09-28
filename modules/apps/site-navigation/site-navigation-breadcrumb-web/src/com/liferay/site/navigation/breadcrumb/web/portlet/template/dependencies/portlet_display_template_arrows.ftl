<#if entries?has_content>
	<div class="breadcrumb breadcrumb-arrows">
		<#list entries as entry>
			<a

			<#if entry.isBrowsable()>
				href="${entry.getURL()!""}"
			</#if>

			>${htmlUtil.escape(entry.getTitle())}</a>
		</#list>
	</div>
</#if>
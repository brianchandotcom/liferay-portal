<#if entries?has_content>
	<ul class="breadcrumb breadcrumb-vertical">
		<#list entries as entry>
			<li>
				<a

				<#if entry.isBrowsable()>
					href="${entry.getURL()!""}"
				</#if>

				>${htmlUtil.escape(entry.getTitle())}</a>
			</li>
		</#list>
	</ul>
</#if>
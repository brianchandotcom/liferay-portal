<#if entries?has_content>
	<ul class="breadcrumb breadcrumb-horizontal">
		<#list entries as entry>
			<li><a href="${entry.getRegularURL()!""} ">${htmlUtil.escape(entry.getName())}</a></li>
		</#list>
	</ul>
</#if>
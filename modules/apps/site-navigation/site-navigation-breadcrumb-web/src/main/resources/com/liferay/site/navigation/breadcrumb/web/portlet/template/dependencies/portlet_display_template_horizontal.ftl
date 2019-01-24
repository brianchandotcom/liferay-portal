<#if entries?has_content>
	<ol class="breadcrumb">
		<#list entries as entry>
			<li class="breadcrumb-item">
				<#if entry?has_next>
					<a class="breadcrumb-link" href="${entry.getURL()!""}" title="${htmlUtil.escape(entry.getTitle())}">
						<span class="breadcrumb-text-truncate">${htmlUtil.escape(entry.getTitle())}</span>
					</a>
				<#else>
					<span class="active breadcrumb-text-truncate">${htmlUtil.escape(entry.getTitle())}</span>
				</#if>
			</li>
		</#list>
	</ol>
</#if>
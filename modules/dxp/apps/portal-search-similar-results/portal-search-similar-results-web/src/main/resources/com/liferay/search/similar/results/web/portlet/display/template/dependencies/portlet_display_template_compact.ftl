<div class="display-compact">
	<ul>
		<#if entries?has_content>
			<#list entries as entry>
				<li>
					<a class="link-primary single-link" href="${entry.getViewURL()}">
						${htmlUtil.escape(entry.getTitle())}
					</a>
				</li>
			</#list>
		</#if>
	</ul>
</div>
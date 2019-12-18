<div class="display-compact">
	<ul>
		<#if entries?has_content>
			<#list entries as entry>
				<#if !entry.isTemporarilyUnavailable()>
					<li>
						<a class="link-primary single-link" href="${entry.getViewURL()}">
							${entry.getHighlightedTitle()}
						</a>
					</li>
				</#if>
			</#list>
		</#if>
	</ul>
</div>
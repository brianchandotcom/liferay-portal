<#if entries?has_content>
	<ul class="breadcrumb minette-breadcrumb">
		<#assign cssClass = "" />

		<#list entries as entry>
			<#if entry?is_last>
				<#assign cssClass = " active" />
			</#if>

			<li class="breadcrumb-item${cssClass}">
				<#if entry?has_next>
					<a
						class="breadcrumb-link"

					<#if entry.isBrowsable()>
						href="${entry.getURL()!""}"
					</#if>

					>
				<#else>
					<span class="breadcrumb-text">
				</#if>

				${htmlUtil.escape(entry.getTitle())}

				<#if entry?has_next>
					</a>
				<#else>
					</span>
				</#if>
			</li>
		</#list>
	</ul>
</#if>
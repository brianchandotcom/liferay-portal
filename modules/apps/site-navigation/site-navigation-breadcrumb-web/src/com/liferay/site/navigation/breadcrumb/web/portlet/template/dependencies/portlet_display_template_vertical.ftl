<#if entries?has_content>
	<style>
		.breadcrumb-vertical ul {
			padding-left: 0;
		}

		.breadcrumb-vertical li {
		    display: list-item;
		    text-align: left;
		}

		.breadcrumb-vertical li:last-child a {
		    color: #676767;
		}
	</style>

	<div class="breadcrumb breadcrumb-vertical">
	    <ul class="breadcrumb">
		    <#list entries as entry>
				<#assign entryHrefLink = "">

				<#if entry.isBrowsable()>
					<#assign entryURL = entry.getURL()!"">

					<#assign entryHrefLink = "href='${entryURL}'">
				</#if>

				<li><a ${entryHrefLink}>${htmlUtil.escape(entry.getTitle())}</a></li>
		    </#list>
	    </ul>
	</div>
</#if>
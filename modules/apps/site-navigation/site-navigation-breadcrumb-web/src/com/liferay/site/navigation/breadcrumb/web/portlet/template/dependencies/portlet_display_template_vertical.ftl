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
	    <ul>
		    <#list entries as entry>
			    <li><a href="${entry.getURL()!""}">${htmlUtil.escape(entry.getTitle())}</a></li>
		    </#list>
	    </ul>
	</div>
</#if>
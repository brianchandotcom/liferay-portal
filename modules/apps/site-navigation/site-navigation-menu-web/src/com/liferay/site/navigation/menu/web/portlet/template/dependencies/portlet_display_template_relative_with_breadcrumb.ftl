<#if entries?has_content>
	<style>
		.breadcrumb-horizontal ul {
			padding-left: 0;
			margin-bottom: 0;
		}

		.breadcrumb-horizontal li:last-child a {
			color: #676767;
		}

		.breadcrumb-horizontal li:before {
			content: "/ ";
			padding: 0 5px;
			color: #ccc;
		}

		.breadcrumb-horizontal li:first-child:before {
			content: "";
		}

		.breadcrumb-horizontal li:last-child a {
		    color: #676767;
		}
	</style>

	<div class="breadcrumb breadcrumb-horizontal">
	    <ul>
		    <#list entries as entry>
			    <li><a href="${entry.getRegularURL()!""} ">${htmlUtil.escape(entry.getName())}</a></li>
		    </#list>
		</ul>
	</div>
</#if>
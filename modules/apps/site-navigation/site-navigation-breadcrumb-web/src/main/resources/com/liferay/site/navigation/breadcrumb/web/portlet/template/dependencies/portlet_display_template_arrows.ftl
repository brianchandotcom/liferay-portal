<#assign liferay_util = taglibLiferayHash["/WEB-INF/tld/liferay-util.tld"] />

<#if entries?has_content>
	<@liferay_util["html-top"]>
		<style>
			.breadcrumb-arrows a {
				background: #efefef;
				display: inline-block;
				margin-right: 5px;
				padding: 10px 30px 10px;
				position: relative;
			}

			.breadcrumb-arrows a:after {
				border-bottom: 20px inset transparent;
				border-left: 20px solid #efefef;
				border-top: 20px inset transparent;
				content: "";
				height: 0;
				position: absolute;
				right: -20px;
				top: 0;
				width: 0;
				z-index: 2;
			}

			.breadcrumb-arrows a:before {
				border-bottom: 20px inset transparent;
				border-left: 20px solid #fff;
				border-top: 20px inset transparent;
				content: "";
				height: 0;
				left: 0;
				position: absolute;
				top: 0;
				width: 0;
			}

			.breadcrumb-arrows a:first-child {
				-moz-border-radius: 4px 0 0 4px;
				-webkit-border-radius: 4px 0 0 4px;
				border-radius: 4px 0 0 4px;
			}

			.breadcrumb-arrows a:last-child {
				-moz-border-radius: 0 4px 4px 0;
				-webkit-border-radius: 0 4px 4px 0;
				background: #007ACC;
				border-radius: 0 4px 4px 0;
				color: #fff;
			}

			.breadcrumb-arrows a:first-child:before, a:last-child:after {
				border: none;
			}
		</style>
	</@>

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
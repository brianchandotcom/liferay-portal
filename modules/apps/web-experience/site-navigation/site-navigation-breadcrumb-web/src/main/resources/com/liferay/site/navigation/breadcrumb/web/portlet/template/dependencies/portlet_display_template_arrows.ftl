<#if entries?has_content>
	<@liferay_util["html-top"]>
		<style>
			.breadcrumb-arrows li {
				overflow: visible;
			}

			.breadcrumb-arrows li + li::before {
				content: "";
				padding: 0;
			}

			.breadcrumb-arrows li > a {
				background: #efefef;
				border-right: 4px solid #fff;
				display: inline-block;
				padding: 12px 30px;
				position: relative;
			}

			.breadcrumb-arrows a::after {
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

			.breadcrumb-arrows li:nth-child(n+2) a::before {
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

			.breadcrumb-arrows li:first-child > a {
				-moz-border-radius: 4px 0 0 4px;
				-webkit-border-radius: 4px 0 0 4px;
				border-radius: 4px 0 0 4px;
			}

			.breadcrumb-arrows li:last-child {
				-moz-border-radius: 0 4px 4px 0;
				-webkit-border-radius: 0 4px 4px 0;
				background: #007ACC;
				border-radius: 0 4px 4px 0;
				border-right: none;
				color: #fff;
				padding: 12px 30px;
				position: relative;
			}

			.breadcrumb-arrows li:last-child::before {
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

			.breadcrumb-arrows li:first-child::before > a {
				border: none;
			}

			.breadcrumb-arrows li:last-child::after > a {
				border: none;
			}
		</style>
	</@>

	<div class="breadcrumb breadcrumb-arrows">
		<#assign cssClass = "" />

		<#list entries as entry>
			<#if entry?is_last>
				<#assign cssClass = "active" />
			</#if>

			<li class="${cssClass}">
				<#if entry?has_next>
					<a

					<#if entry.isBrowsable()>
						href="${entry.getURL()!""}"
					</#if>

					>
				</#if>

				${htmlUtil.escape(entry.getTitle())}

				<#if entry?has_next>
					</a>
				</#if>
		</#list>
	</div>
</#if>
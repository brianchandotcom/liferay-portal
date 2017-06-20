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
				background: #EFEFEF;
				border-right: 4px solid #FFF;
				display: inline-block;
				max-width: inherit;
				padding: 12px 27px;
				position: relative;
			}

			.breadcrumb-arrows li .entry {
				max-width: inherit;
				overflow: hidden;
				text-overflow: ellipsis;
				white-space: nowrap;
			}

			.breadcrumb-arrows a::after {
				border-bottom: 20px inset transparent;
				border-left: 20px solid #EFEFEF;
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
				border-left: 20px solid #FFF;
				border-top: 20px inset transparent;
				content: "";
				height: 0;
				left: 0;
				position: absolute;
				top: 0;
				width: 0;
			}

			.breadcrumb-arrows li:first-child > a {
				border-radius: 4px 0 0 4px;
			}

			.breadcrumb-arrows li:last-child {
				background: #007ACC;
				border-radius: 0 4px 4px 0;
				border-right-width: 0;
				color: #FFF;
				padding: 12px 30px;
				position: relative;
			}

			.breadcrumb-arrows li:last-child::before {
				border-bottom: 20px inset transparent;
				border-left: 20px solid #FFF;
				border-top: 20px inset transparent;
				content: "";
				height: 0;
				left: 0;
				position: absolute;
				top: 0;
				width: 0;
			}

			.breadcrumb-arrows li:first-child::before > a, .breadcrumb-arrows li:last-child::after > a {
				border-width: 0;
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

				<div class="entry">
					${htmlUtil.escape(entry.getTitle())}
				</div>

				<#if entry?has_next>
					</a>
				</#if>
		</#list>
	</div>
</#if>
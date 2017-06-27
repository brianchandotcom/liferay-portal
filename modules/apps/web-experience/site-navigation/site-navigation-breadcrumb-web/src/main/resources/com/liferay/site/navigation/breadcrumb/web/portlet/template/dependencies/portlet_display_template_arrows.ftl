<#if entries?has_content>
	<@liferay_util["html-top"]>
		<style>
			.breadcrumb-arrows li {
				background-color: #EFEFEF;
				margin-bottom: 3px;
				overflow: visible;
			}

			.breadcrumb-arrows li + li::before {
				content: none;
			}

			.breadcrumb-arrows li > a {
				border-right: 4px solid #FFF;
				display: block;
				max-width: 100%;
				position: relative;
			}

			.breadcrumb-arrows .entry {
				line-height: 40px;
				overflow: hidden;
				padding-left: 30px;
				padding-right: 7px;
				text-overflow: ellipsis;
				white-space: nowrap;
			}

			.breadcrumb-arrows a::after {
				border-bottom: 20px inset transparent;
				border-left: 20px solid #EFEFEF;
				border-top: 20px inset transparent;
				content: '';
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
				content: '';
				height: 0;
				left: 0;
				position: absolute;
				top: 0;
				width: 0;
			}

			.breadcrumb-arrows li:last-child {
				border-bottom-right-radius: 4px;
				border-right-width: 0;
				border-top-right-radius: 4px;
				position: relative;
			}

			.breadcrumb-arrows li:last-child::before {
				border-bottom: 20px inset transparent;
				border-left: 20px solid #FFF;
				border-top: 20px inset transparent;
				content: '';
				height: 0;
				left: 0;
				position: absolute;
				top: 0;
				width: 0;
			}

			.breadcrumb-arrows li:last-child .entry {
				padding-right: 23px;
			}

			.breadcrumb-arrows li:first-child {
				border-bottom-left-radius: 4px;
				border-top-left-radius: 4px;
			}

			.breadcrumb-arrows li:first-child::before {
				border-left-width: 0;
			}

			.breadcrumb-arrows li:first-child .entry {
				padding-left: 23px;
			}

			.breadcrumb-arrows .active {
				background-color: #007ACC;
			}

			.breadcrumb-arrows .active a, .breadcrumb-arrows .active .entry {
				color: #FFF;
			}

			.breadcrumb-arrows .active a::after {
				border-left-color: #007ACC;
			}

			.portlet-barebone .breadcrumb-arrows li > a, .portlet-borderless .breadcrumb-arrows li > a, .taglib-portlet-preview .breadcrumb-arrows li > a {
				border-right-color: #F5F7F8;
			}

			.portlet-barebone .breadcrumb-arrows li:last-child::before, .portlet-barebone .breadcrumb-arrows li:nth-child(n+2) a::before, .portlet-borderless .breadcrumb-arrows li:last-child::before, .portlet-borderless .breadcrumb-arrows li:nth-child(n+2) a::before, .taglib-portlet-preview .breadcrumb-arrows li:last-child::before, .taglib-portlet-preview .breadcrumb-arrows li:nth-child(n+2) a::before {
				border-left-color: #F5F7F8;
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
			</li>
		</#list>
	</div>
</#if>
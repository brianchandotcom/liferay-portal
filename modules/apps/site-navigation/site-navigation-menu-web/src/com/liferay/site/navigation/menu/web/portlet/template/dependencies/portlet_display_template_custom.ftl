<#if entries?? && entries?has_content && (entries?size >= rootLayoutLevel) >
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

	<#assign layoutLevel = rootLayoutLevel?number />

	<#if rootLayoutType == "relative">
		<#assign layoutLevel = entries?size - rootLayoutLevel />
	</#if>

	<#assign includeAllChildEntries = false />

	<#if includedLayouts == "all">
		<#assign includeAllChildEntries = true />
	</#if>

	<#if (layoutLevel > 0)>
		<#assign rootNavigationItem = entries[layoutLevel-1] />
	</#if>

	<div class="nav-menu nav-menu-style-${bulletStyle}">
		<#if headerType == "root-layout" && rootNavigationItem?has_content>
			<h2><a href="${rootNavigationItem.getRegularURL()}" ${rootNavigationItem.getTarget()}>${rootNavigationItem.getName()}</a></h2>
		<#elseif headerType == "portlet-title">
			<h2>${htmlUtil.escape(portletDisplay.getTitle())}</h2>
		<#elseif headerType == "breadcrumb">
			<div class="breadcrumb breadcrumb-horizontal">
				<ul>
					<#list entries as entry>
						<li><a href="${entry.getURL()!""}">${htmlUtil.escape(entry.getName())}</a></li>
					</#list>
				</ul>
			</div>
		</#if>

		<#if layoutLevel == 0>
			<#assign rootNavigationItems = navItem.fromLayouts(request, themeDisplay.getLayouts(), null) />

			<ul class="layouts level-${layoutLevel}">
				<#list rootNavigationItems as rootNavigationItem>
					<li class="open">
						<a href="${rootNavigationItem.getRegularURL()!""} ">${htmlUtil.escape(rootNavigationItem.getName())}</a>

						<#if includeAllChildEntries || rootNavigationItem.isBelongsToNavigationEntries(entries) >
							<@displayChildNavigation childLayoutLevel=(layoutLevel + 1) childNavigationItems=rootNavigationItem.getChildren() includeAllChildEntries=includeAllChildEntries />
						</#if>
					</li>
				</#list>
			</ul>

		<#else>
			<@displayChildNavigation childLayoutLevel=layoutLevel childNavigationItems=rootNavigationItem.getChildren() includeAllChildEntries=includeAllChildEntries />
		</#if>
	</div>
</#if>

<#macro displayChildNavigation
	childLayoutLevel
	childNavigationItems
	includeAllChildEntries
>
	<#if childNavigationItems?has_content>
		<ul class="layouts level-${childLayoutLevel}">
			<#list childNavigationItems as childNavigationItem>
				<li class="open">
					<a href="${childNavigationItem.getRegularURL()!""} ">${htmlUtil.escape(childNavigationItem.getName())}</a>

					<#if includeAllChildEntries || childNavigationItem.isBelongsToNavigationEntries(entries) >
						<@displayChildNavigation childLayoutLevel=(childLayoutLevel + 1) childNavigationItems=childNavigationItem.getChildren() includeAllChildEntries=includeAllChildEntries/>
					</#if>
				</li>
			</#list>
		</ul>
	</#if>
</#macro>
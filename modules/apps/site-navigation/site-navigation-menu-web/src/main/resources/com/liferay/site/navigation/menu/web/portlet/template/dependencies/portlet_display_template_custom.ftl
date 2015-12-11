<#include "${templatesPath}/NAVIGATION-MACRO-FTL" />

<#if entries?? && entries?has_content && (entries?size >= rootLayoutLevel?number) >
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

	<div class="nav-menu nav-menu-style-${htmlUtil.escapeAttribute(bulletStyle)}">
		<#if headerType == "root-layout" && rootNavigationItem?has_content>
			<h2><a href="${rootNavigationItem.getRegularURL()}" ${rootNavigationItem.getTarget()}>${rootNavigationItem.getName()}</a></h2>
		<#elseif headerType == "portlet-title">
			<h2>${htmlUtil.escape(portletDisplay.getTitle())}</h2>
		<#elseif headerType == "breadcrumb">
			<ul class="breadcrumb breadcrumb-horizontal">
				<#list entries as entry>
					<li>
						<#if entry.isBrowsable()>
							<a href="${entry.getURL()!""}" ${entry.getTarget()}>${htmlUtil.escape(entry.getName())}</a>
						<#else>
							${htmlUtil.escape(entry.getName())}
						</#if>
					</li>
				</#list>
			</ul>
		</#if>

		<#if layoutLevel == 0>
			<ul class="layouts level-${layoutLevel}">
				<#assign rootNavigationItems = navItem.fromLayouts(request, themeDisplay.getLayouts(), null) />

				<#list rootNavigationItems as rootNavigationItem>
					<li class="open">
						<#if entry.isBrowsable()>
							<a href="${rootNavigationItem.getRegularURL()!""}" ${rootNavigationItem.getTarget()}>${htmlUtil.escape(rootNavigationItem.getName())}</a>
						<#else>
							${htmlUtil.escape(rootNavigationItem.getName())}
						</#if>

						<#if includeAllChildEntries || rootNavigationItem.isInNavigation(entries) >
							<@displayChildNavigation
								childLayoutLevel=(layoutLevel + 1)
								childNavigationItems=rootNavigationItem.getChildren()
								includeAllChildEntries=includeAllChildEntries
							/>
						</#if>
					</li>
				</#list>
			</ul>

		<#else>
			<@displayChildNavigation
				childLayoutLevel=layoutLevel
				childNavigationItems=rootNavigationItem.getChildren()
				includeAllChildEntries=includeAllChildEntries
			/>
		</#if>
	</div>
</#if>
<style>
	.widget-resume{
		min-height: 96px;
	}
</style>

<#if currentURL?has_content>
	<#assign groupKey = currentURL?substring(currentURL?index_of('/web/') + 5, currentURL?index_of('/products'))/>
</#if>
<#assign contactsImage="/documents/d/${groupKey}/contacts-png"
				 eyeglassesImage="/documents/d/${groupKey}/eyeglasses-png"
				 lensesImage="/documents/d/${groupKey}/lenses-png"
				 sunglassesImage="/documents/d/${groupKey}/sunglasses-png"
				 />
<div class="row widget-mode-card">
            <#if entries?has_content>
                <#list entries as currentCategory>
                    <#switch currentCategory.getName()>
                        <#case "Contacts">
                            <#assign categoryImage=contactsImage />
                            <#break>
                        <#case "Eyeglasses">
                            <#assign categoryImage=eyeglassesImage />
                            <#break>
                        <#case "Lenses">
                            <#assign categoryImage=lensesImage />
                            <#break>
                        <#case "Sunglasses">
                            <#assign categoryImage=sunglassesImage />
                            <#break>
                    </#switch>

            <#assign
                categoryId = currentCategory.getCategoryId()
                categoryName = currentCategory.getName()
                categoryHref = cpAssetCategoriesNavigationDisplayContext
                .getFriendlyURL(currentCategory.getCategoryId(), themeDisplay)
								propertyName = "alphaCode"
            />

            <#if cpAssetCategoriesNavigationDisplayContext.getDefaultImageSrc(categoryId)??>
                <#assign cardImage = true />
            <#else>
                <#assign cardImage = false />
            </#if>
            <div class="col-lg-4">
                <div class="card">
                    <div class="card-header">
                            <div class="aspect-ratio aspect-ratio-8-to-3">
                                <a href="${categoryHref}">${categoryName}
                                    <img alt="thumbnail" class="aspect-ratio-item-center-middle aspect-ratio-item-fluid"
                                        src="${categoryImage}">
                                </a>
                            </div>
                    </div>
                    <div class="card-body widget-topbar">
                        <div class="autofit-row card-title">
                                <div class="autofit-col autofit-col-expand">
                                    <h3 class="title">
                                        <a class="title-link" href="${categoryHref}">${categoryName}</a>
                                    </h3>
                                </div>
                        </div>
                        <#if validator.isNotNull(currentCategory.getDescription())>
                            <#assign content = currentCategory.getDescription() />
                            <#if cardImage>
                                <p class="widget-resume">${stringUtil.shorten(htmlUtil.stripHtml(content), 150)}</p>
                            <#else>
                                <p class="widget-resume">${stringUtil.shorten(htmlUtil.stripHtml(content), 400)}</p>
                            </#if>
                        </#if>
                    </div>
                </div>
            </div>
        </#list>
    </#if>
</div>
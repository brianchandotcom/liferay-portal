<div class="row">
    <#assign
        image = cpCategoryContentDisplayContext.getDefaultImageSrc(themeDisplay)

        title = assetCategory.getTitle(locale)

        description = assetCategory.getDescription(locale)
    />

    <div>
        <img src="${image}">
    </div>

    <div class="container-fluid-1280">
        <h1>${title}</h1>
        <p>${description}</p>
    </div>
</div>
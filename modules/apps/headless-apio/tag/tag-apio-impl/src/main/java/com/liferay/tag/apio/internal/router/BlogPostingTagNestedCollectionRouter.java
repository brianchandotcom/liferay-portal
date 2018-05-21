package com.liferay.tag.apio.internal.router;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagService;
import com.liferay.blog.apio.architect.identifier.BlogPostingIdentifier;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.tag.apio.identifier.TagIdentifier;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component(immediate = true)
public class BlogPostingTagNestedCollectionRouter implements
	NestedCollectionRouter<AssetTag, TagIdentifier, Long, BlogPostingIdentifier>
{

	@Override
	public NestedCollectionRoutes<AssetTag, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<AssetTag, Long> builder) {
		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	private PageItems<AssetTag> _getPageItems(
		Pagination pagination, long blogEntryId)
		throws PortalException {

		List<AssetTag> tags = _assetTagService.getTags(BlogsEntry.class.getName(), blogEntryId);
		int count = tags.size();

		return new PageItems<>(tags, count);
	}

	@Reference
	private AssetTagService _assetTagService;
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.internal.graphql.mutation.v1_0;

import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Account;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.DDMOption;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ProductOptionValue;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Sku;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.SkuOption;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.WishList;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.WishListItem;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.AccountResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.ChannelResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.ProductOptionValueResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.SkuResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.WishListItemResource;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.WishListResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineExportTaskResource;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import jakarta.annotation.Generated;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.function.BiFunction;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setAccountResourceComponentServiceObjects(
		ComponentServiceObjects<AccountResource>
			accountResourceComponentServiceObjects) {

		_accountResourceComponentServiceObjects =
			accountResourceComponentServiceObjects;
	}

	public static void setChannelResourceComponentServiceObjects(
		ComponentServiceObjects<ChannelResource>
			channelResourceComponentServiceObjects) {

		_channelResourceComponentServiceObjects =
			channelResourceComponentServiceObjects;
	}

	public static void setProductOptionValueResourceComponentServiceObjects(
		ComponentServiceObjects<ProductOptionValueResource>
			productOptionValueResourceComponentServiceObjects) {

		_productOptionValueResourceComponentServiceObjects =
			productOptionValueResourceComponentServiceObjects;
	}

	public static void setSkuResourceComponentServiceObjects(
		ComponentServiceObjects<SkuResource>
			skuResourceComponentServiceObjects) {

		_skuResourceComponentServiceObjects =
			skuResourceComponentServiceObjects;
	}

	public static void setWishListResourceComponentServiceObjects(
		ComponentServiceObjects<WishListResource>
			wishListResourceComponentServiceObjects) {

		_wishListResourceComponentServiceObjects =
			wishListResourceComponentServiceObjects;
	}

	public static void setWishListItemResourceComponentServiceObjects(
		ComponentServiceObjects<WishListItemResource>
			wishListItemResourceComponentServiceObjects) {

		_wishListItemResourceComponentServiceObjects =
			wishListItemResourceComponentServiceObjects;
	}

	@GraphQLField(
		description = "Creates a new AccountEntry under /channels/<channelId>/accounts via AccountEntryService.addAccountEntry. The request body supplies externalReferenceCode, name, description, domains, taxId, type (defaults to business) and status (defaults to approved); logo bytes are loaded from DLAppLocalService when logoId is set. Not an upsert -- addAccountEntry is always invoked, so a duplicate externalReferenceCode raises 422. Validation -- NoSuchChannelException -> 404 when channel eligibility re-validation fails. Side effects -- When the channel restricts accounts, creates a CommerceChannelAccountEntryRel of TYPE_ELIGIBILITY linking the new account to the channel; applies default billing and shipping address IDs via AccountEntryLocalService; synchronizes organization rels through AccountEntryOrganizationRelLocalService."
	)
	public Account createChannelAccount(
			@GraphQLName("channelId") Long channelId,
			@GraphQLName("account") Account account)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource -> accountResource.postChannelAccount(
				channelId, account));
	}

	@GraphQLField
	public Response createChannelsPageExportBatch(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("sort") String sortsString,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("contentType") String contentType,
			@GraphQLName("fieldNames") String fieldNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_channelResourceComponentServiceObjects,
			this::_populateResourceContext,
			channelResource -> channelResource.postChannelsPageExportBatch(
				search, _filterBiFunction.apply(channelResource, filterString),
				_sortsBiFunction.apply(channelResource, sortsString),
				callbackURL, contentType, fieldNames));
	}

	@GraphQLField(
		description = "External-reference-code variant of postChannelProductProductOptionProductOptionValuesPage. Resolves channel, product and option by ERC and delegates to the numeric POST handler. The endpoint is a preview call -- the SkuOption[] body is scored against the option values to compute pricing and selectability for the candidate configuration. Not an upsert -- nothing is persisted. Validation -- NoSuchModelException -> 404 when any ERC is missing."
	)
	public java.util.Collection<ProductOptionValue>
			createChannelByExternalReferenceCodeChannelExternalReferenceCodeProductByExternalReferenceCodeProductExternalReferenceCodeProductOptionByExternalReferenceCodeProductOptionExternalReferenceCodeProductOptionValuesPage(
				@GraphQLName("channelExternalReferenceCode") String
					channelExternalReferenceCode,
				@GraphQLName("productExternalReferenceCode") String
					productExternalReferenceCode,
				@GraphQLName("productOptionExternalReferenceCode") String
					productOptionExternalReferenceCode,
				@GraphQLName("accountId") Long accountId,
				@GraphQLName("currencyCode") String currencyCode,
				@GraphQLName("productOptionValueId") Long productOptionValueId,
				@GraphQLName("skuId") Long skuId,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("skuOptions") SkuOption[] skuOptions)
		throws Exception {

		return _applyComponentServiceObjects(
			_productOptionValueResourceComponentServiceObjects,
			this::_populateResourceContext,
			productOptionValueResource -> {
				Page paginationPage =
					productOptionValueResource.
						postChannelByExternalReferenceCodeChannelExternalReferenceCodeProductByExternalReferenceCodeProductExternalReferenceCodeProductOptionByExternalReferenceCodeProductOptionExternalReferenceCodeProductOptionValuesPage(
							channelExternalReferenceCode,
							productExternalReferenceCode,
							productOptionExternalReferenceCode, accountId,
							currencyCode, productOptionValueId, skuId,
							Pagination.of(page, pageSize), skuOptions);

				return paginationPage.getItems();
			});
	}

	@GraphQLField(
		description = "POST counterpart of getChannelProductProductOptionProductOptionValuesPage. Accepts a SkuOption[] body that lets the DTO converter score each option value against a partially-selected configuration (selectability, price impact, availability). The response shape and pagination match the GET, just enriched with the body's selections. Not an upsert -- nothing is persisted. Validation -- NoSuchCProductException -> 404 when the productId does not resolve; PrincipalException -> 403 when the caller lacks VIEW permission on the product."
	)
	public java.util.Collection<ProductOptionValue>
			createChannelProductProductOptionProductOptionValuesPage(
				@GraphQLName("channelId") Long channelId,
				@GraphQLName("productId") Long productId,
				@GraphQLName("productOptionId") Long productOptionId,
				@GraphQLName("accountId") Long accountId,
				@GraphQLName("currencyCode") String currencyCode,
				@GraphQLName("productOptionValueId") Long productOptionValueId,
				@GraphQLName("skuId") Long skuId,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("skuOptions") SkuOption[] skuOptions)
		throws Exception {

		return _applyComponentServiceObjects(
			_productOptionValueResourceComponentServiceObjects,
			this::_populateResourceContext,
			productOptionValueResource -> {
				Page paginationPage =
					productOptionValueResource.
						postChannelProductProductOptionProductOptionValuesPage(
							channelId, productId, productOptionId, accountId,
							currencyCode, productOptionValueId, skuId,
							Pagination.of(page, pageSize), skuOptions);

				return paginationPage.getItems();
			});
	}

	@GraphQLField(
		description = "External-reference-code variant of postChannelProductSku. Resolves the CommerceChannel and CProduct by ERC and delegates. Not an upsert -- the underlying method throws UnsupportedOperationException for the DDMOption[] body shape, so callers must use the by-sku-option endpoint instead. Validation -- NoSuchModelException -> 404 when either ERC is missing; UnsupportedOperationException -> 500 for any request body."
	)
	public Sku
			createChannelByExternalReferenceCodeChannelExternalReferenceCodeProductByExternalReferenceCodeProductExternalReferenceCodeSku(
				@GraphQLName("channelExternalReferenceCode") String
					channelExternalReferenceCode,
				@GraphQLName("productExternalReferenceCode") String
					productExternalReferenceCode,
				@GraphQLName("accountId") Long accountId,
				@GraphQLName("quantity") java.math.BigDecimal quantity,
				@GraphQLName("ddmOptions") DDMOption[] ddmOptions)
		throws Exception {

		return _applyComponentServiceObjects(
			_skuResourceComponentServiceObjects, this::_populateResourceContext,
			skuResource ->
				skuResource.
					postChannelByExternalReferenceCodeChannelExternalReferenceCodeProductByExternalReferenceCodeProductExternalReferenceCodeSku(
						channelExternalReferenceCode,
						productExternalReferenceCode, accountId, quantity,
						ddmOptions));
	}

	@GraphQLField(
		description = "External-reference-code variant of postChannelProductSkuBySkuOption. Resolves channel and product by ERC and delegates. The endpoint is a SKU lookup (not a create) -- it serializes the SkuOption[] body and calls CPInstanceHelper.fetchCPInstance to locate the matching CPInstance. Not an upsert -- nothing is persisted. Validation -- NoSuchModelException -> 404 when either ERC is missing; NoSuchCPInstanceException -> 404 when no SKU matches the option selection."
	)
	public Sku
			createChannelByExternalReferenceCodeChannelExternalReferenceCodeProductByExternalReferenceCodeProductExternalReferenceCodeSkuBySkuOption(
				@GraphQLName("channelExternalReferenceCode") String
					channelExternalReferenceCode,
				@GraphQLName("productExternalReferenceCode") String
					productExternalReferenceCode,
				@GraphQLName("accountId") Long accountId,
				@GraphQLName("currencyCode") String currencyCode,
				@GraphQLName("quantity") java.math.BigDecimal quantity,
				@GraphQLName("skuUnitOfMeasureKey") String skuUnitOfMeasureKey,
				@GraphQLName("skuOptions") SkuOption[] skuOptions)
		throws Exception {

		return _applyComponentServiceObjects(
			_skuResourceComponentServiceObjects, this::_populateResourceContext,
			skuResource ->
				skuResource.
					postChannelByExternalReferenceCodeChannelExternalReferenceCodeProductByExternalReferenceCodeProductExternalReferenceCodeSkuBySkuOption(
						channelExternalReferenceCode,
						productExternalReferenceCode, accountId, currencyCode,
						quantity, skuUnitOfMeasureKey, skuOptions));
	}

	@GraphQLField(
		description = "Endpoint declared on the base resource accepting a DDMOption[] body. Not an upsert -- the current implementation throws UnsupportedOperationException, so callers must use the by-sku-option endpoint to look up a SKU instead. Validation -- UnsupportedOperationException -> 500 for any request body."
	)
	public Sku createChannelProductSku(
			@GraphQLName("channelId") Long channelId,
			@GraphQLName("productId") Long productId,
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("quantity") java.math.BigDecimal quantity,
			@GraphQLName("ddmOptions") DDMOption[] ddmOptions)
		throws Exception {

		return _applyComponentServiceObjects(
			_skuResourceComponentServiceObjects, this::_populateResourceContext,
			skuResource -> skuResource.postChannelProductSku(
				channelId, productId, accountId, quantity, ddmOptions));
	}

	@GraphQLField(
		description = "Looks up the SKU that matches a SkuOption[] selection under /channels/{channelId}/products/{productId}/skus/by-sku-option -- it is a search call, not a create. Resolves the CPDefinition, the channel, builds a CommerceContext and enforces CommerceProductViewPermission. Serializes the body to a JSONArray and calls CPInstanceHelper.fetchCPInstance to locate the matching CPInstance. When skuUnitOfMeasureKey is omitted the SKU's default unit of measure is used; quantity defaults to BigDecimal.ONE or the unit of measure's incremental order quantity. Not an upsert -- nothing is persisted. Validation -- NoSuchCProductException -> 404 when the productId does not resolve; NoSuchCPInstanceException -> 404 when no SKU matches the option selection; PrincipalException -> 403 when the caller lacks VIEW permission on the product."
	)
	public Sku createChannelProductSkuBySkuOption(
			@GraphQLName("channelId") Long channelId,
			@GraphQLName("productId") Long productId,
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("currencyCode") String currencyCode,
			@GraphQLName("quantity") java.math.BigDecimal quantity,
			@GraphQLName("skuUnitOfMeasureKey") String skuUnitOfMeasureKey,
			@GraphQLName("skuOptions") SkuOption[] skuOptions)
		throws Exception {

		return _applyComponentServiceObjects(
			_skuResourceComponentServiceObjects, this::_populateResourceContext,
			skuResource -> skuResource.postChannelProductSkuBySkuOption(
				channelId, productId, accountId, currencyCode, quantity,
				skuUnitOfMeasureKey, skuOptions));
	}

	@GraphQLField(
		description = "Deletes the wish list at /wishlists/{wishListId} via CommerceWishListService.deleteCommerceWishList. Validation -- NoSuchWishListException -> 404 when the row is missing; PrincipalException -> 403 when the caller lacks DELETE permission."
	)
	public boolean deleteWishList(@GraphQLName("wishListId") Long wishListId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_wishListResourceComponentServiceObjects,
			this::_populateResourceContext,
			wishListResource -> wishListResource.deleteWishList(wishListId));

		return true;
	}

	@GraphQLField
	public Response deleteWishListBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_wishListResourceComponentServiceObjects,
			this::_populateResourceContext,
			wishListResource -> wishListResource.deleteWishListBatch(
				callbackURL, object));
	}

	@GraphQLField(
		description = "Updates the wish list at /wishlists/<wishListId>. Not a JSON Merge Patch -- the handler performs a manual GetterUtil.getString/getBoolean fallback merge for name and defaultWishList before calling CommerceWishListService.updateCommerceWishList. Validation -- NoSuchWishListException -> 404 when the row is missing; PrincipalException -> 403 when the caller lacks UPDATE permission. Side effects -- When the body includes wishListItems, deletes the existing items via CommerceWishListItemService.deleteCommerceWishListItems and re-adds each entry through WishListItemResource.postWishlistWishListWishListItem (upsert by (accountId, wishListId, cpInstanceUuid, productId))."
	)
	public WishList patchWishList(
			@GraphQLName("wishListId") Long wishListId,
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("wishList") WishList wishList)
		throws Exception {

		return _applyComponentServiceObjects(
			_wishListResourceComponentServiceObjects,
			this::_populateResourceContext,
			wishListResource -> wishListResource.patchWishList(
				wishListId, accountId, wishList));
	}

	@GraphQLField(
		description = "External-reference-code variant of postChannelWishList. Resolves the CommerceChannel by ERC and delegates to the numeric handler. Not an upsert -- CommerceWishListService.addCommerceWishList is always invoked, so the channel and account can hold multiple wish lists by name. Validation -- NoSuchChannelException -> 404 when the channel ERC is missing. Side effects -- Persists a new CommerceWishList row scoped to the channel's site group and the resolved account."
	)
	public WishList createChannelByExternalReferenceCodeWishList(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("wishList") WishList wishList)
		throws Exception {

		return _applyComponentServiceObjects(
			_wishListResourceComponentServiceObjects,
			this::_populateResourceContext,
			wishListResource ->
				wishListResource.postChannelByExternalReferenceCodeWishList(
					externalReferenceCode, accountId, wishList));
	}

	@GraphQLField(
		description = "Creates a new CommerceWishList under /channels/<channelId>/wishlists via CommerceWishListService.addCommerceWishList scoped to the channel's site group, using the body's name and defaultWishList flag. Not an upsert at the wish-list level -- addCommerceWishList is always invoked, so a duplicate name within the same channel and account produces a second row. Validation -- None at the wish-list level (request shape is enforced by the service). Side effects -- When the body contains wishListItems, each is forwarded to WishListItemResource.postWishlistWishListWishListItem (an upsert by (accountId, wishListId, cpInstanceUuid, productId)) so the wish list is populated atomically."
	)
	public WishList createChannelWishList(
			@GraphQLName("channelId") Long channelId,
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("wishList") WishList wishList)
		throws Exception {

		return _applyComponentServiceObjects(
			_wishListResourceComponentServiceObjects,
			this::_populateResourceContext,
			wishListResource -> wishListResource.postChannelWishList(
				channelId, accountId, wishList));
	}

	@GraphQLField(
		description = "Deletes the CommerceWishListItem at /wishlist-items/{wishListItemId} via CommerceWishListItemService.deleteCommerceWishListItem. Validation -- NoSuchWishListItemException -> 404 when the row is missing; PrincipalException -> 403 when the caller lacks UPDATE permission on the parent wish list."
	)
	public boolean deleteWishListItem(
			@GraphQLName("wishListItemId") Long wishListItemId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_wishListItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			wishListItemResource -> wishListItemResource.deleteWishListItem(
				wishListItemId));

		return true;
	}

	@GraphQLField
	public Response deleteWishListItemBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_wishListItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			wishListItemResource ->
				wishListItemResource.deleteWishListItemBatch(
					callbackURL, object));
	}

	@GraphQLField(
		description = "Adds or updates a line item on the wish list at /wishlists/{wishListId}/wishlist-items. Reads skuId from the body, fetches the matching CPInstance through CPInstanceLocalService (falls back to a blank cpInstanceUuid when not found), loads the wish list and its channel, resolves the accountId through AccountUtil and calls CommerceWishListItemService.addOrUpdateCommerceWishListItem. POST is upsert by (accountId, wishListId, cpInstanceUuid, productId) -- creates a new entity when the tuple is unknown, otherwise updates the existing one. Validation -- NoSuchChannelException -> 404 when the parent channel is missing; NoSuchWishListException -> 404 when the wishListId does not resolve."
	)
	public WishListItem createWishlistWishListWishListItem(
			@GraphQLName("wishListId") Long wishListId,
			@GraphQLName("accountId") Long accountId,
			@GraphQLName("wishListItem") WishListItem wishListItem)
		throws Exception {

		return _applyComponentServiceObjects(
			_wishListItemResourceComponentServiceObjects,
			this::_populateResourceContext,
			wishListItemResource ->
				wishListItemResource.postWishlistWishListWishListItem(
					wishListId, accountId, wishListItem));
	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(AccountResource accountResource)
		throws Exception {

		accountResource.setContextAcceptLanguage(_acceptLanguage);
		accountResource.setContextCompany(_company);
		accountResource.setContextHttpServletRequest(_httpServletRequest);
		accountResource.setContextHttpServletResponse(_httpServletResponse);
		accountResource.setContextUriInfo(_uriInfo);
		accountResource.setContextUser(_user);
		accountResource.setGroupLocalService(_groupLocalService);
		accountResource.setRoleLocalService(_roleLocalService);

		accountResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		accountResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(ChannelResource channelResource)
		throws Exception {

		channelResource.setContextAcceptLanguage(_acceptLanguage);
		channelResource.setContextCompany(_company);
		channelResource.setContextHttpServletRequest(_httpServletRequest);
		channelResource.setContextHttpServletResponse(_httpServletResponse);
		channelResource.setContextUriInfo(_uriInfo);
		channelResource.setContextUser(_user);
		channelResource.setGroupLocalService(_groupLocalService);
		channelResource.setRoleLocalService(_roleLocalService);

		channelResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		channelResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(
			ProductOptionValueResource productOptionValueResource)
		throws Exception {

		productOptionValueResource.setContextAcceptLanguage(_acceptLanguage);
		productOptionValueResource.setContextCompany(_company);
		productOptionValueResource.setContextHttpServletRequest(
			_httpServletRequest);
		productOptionValueResource.setContextHttpServletResponse(
			_httpServletResponse);
		productOptionValueResource.setContextUriInfo(_uriInfo);
		productOptionValueResource.setContextUser(_user);
		productOptionValueResource.setGroupLocalService(_groupLocalService);
		productOptionValueResource.setRoleLocalService(_roleLocalService);

		productOptionValueResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		productOptionValueResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(SkuResource skuResource)
		throws Exception {

		skuResource.setContextAcceptLanguage(_acceptLanguage);
		skuResource.setContextCompany(_company);
		skuResource.setContextHttpServletRequest(_httpServletRequest);
		skuResource.setContextHttpServletResponse(_httpServletResponse);
		skuResource.setContextUriInfo(_uriInfo);
		skuResource.setContextUser(_user);
		skuResource.setGroupLocalService(_groupLocalService);
		skuResource.setRoleLocalService(_roleLocalService);

		skuResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		skuResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(WishListResource wishListResource)
		throws Exception {

		wishListResource.setContextAcceptLanguage(_acceptLanguage);
		wishListResource.setContextCompany(_company);
		wishListResource.setContextHttpServletRequest(_httpServletRequest);
		wishListResource.setContextHttpServletResponse(_httpServletResponse);
		wishListResource.setContextUriInfo(_uriInfo);
		wishListResource.setContextUser(_user);
		wishListResource.setGroupLocalService(_groupLocalService);
		wishListResource.setRoleLocalService(_roleLocalService);

		wishListResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		wishListResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(
			WishListItemResource wishListItemResource)
		throws Exception {

		wishListItemResource.setContextAcceptLanguage(_acceptLanguage);
		wishListItemResource.setContextCompany(_company);
		wishListItemResource.setContextHttpServletRequest(_httpServletRequest);
		wishListItemResource.setContextHttpServletResponse(
			_httpServletResponse);
		wishListItemResource.setContextUriInfo(_uriInfo);
		wishListItemResource.setContextUser(_user);
		wishListItemResource.setGroupLocalService(_groupLocalService);
		wishListItemResource.setRoleLocalService(_roleLocalService);

		wishListItemResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		wishListItemResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private static ComponentServiceObjects<AccountResource>
		_accountResourceComponentServiceObjects;
	private static ComponentServiceObjects<ChannelResource>
		_channelResourceComponentServiceObjects;
	private static ComponentServiceObjects<ProductOptionValueResource>
		_productOptionValueResourceComponentServiceObjects;
	private static ComponentServiceObjects<SkuResource>
		_skuResourceComponentServiceObjects;
	private static ComponentServiceObjects<WishListResource>
		_wishListResourceComponentServiceObjects;
	private static ComponentServiceObjects<WishListItemResource>
		_wishListItemResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction
		<Object, String, com.liferay.portal.kernel.search.filter.Filter>
			_filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, com.liferay.portal.kernel.search.Sort[]>
		_sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;
	private VulcanBatchEngineExportTaskResource
		_vulcanBatchEngineExportTaskResource;
	private VulcanBatchEngineImportTaskResource
		_vulcanBatchEngineImportTaskResource;

}
// LIFERAY-REST-BUILDER-HASH:-348611567
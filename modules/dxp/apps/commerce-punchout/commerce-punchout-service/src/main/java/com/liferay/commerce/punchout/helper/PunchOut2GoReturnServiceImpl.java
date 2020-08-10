/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.punchout.helper;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.punchout.service.PunchOutReturnService;
import com.liferay.commerce.service.CommerceAddressLocalService;
import com.liferay.commerce.service.CommerceCountryLocalService;
import com.liferay.commerce.service.CommerceRegionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.OutputStreamWriter;

import java.net.HttpURLConnection;
import java.net.URL;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jaclyn Ong
 */
@Component(
	immediate = true, property = "service.ranking:Integer=100",
	service = PunchOutReturnService.class
)
public class PunchOut2GoReturnServiceImpl implements PunchOutReturnService {

	public String returnToPunchOutVendor(
			CommerceOrder commerceOrder, String url)
		throws Exception {

		try {
			JSONObject cartJSONObject = _jsonFactory.createJSONObject();

			cartJSONObject.put("contract_id", StringPool.BLANK);
			cartJSONObject.put(
				"discount", commerceOrder.getTotalDiscountAmount());
			cartJSONObject.put("discount_description", StringPool.BLANK);
			cartJSONObject.put("quote_id", StringPool.BLANK);
			cartJSONObject.put("shipping", commerceOrder.getShippingAmount());
			cartJSONObject.put(
				"shipping_description", commerceOrder.getShippingOptionName());
			cartJSONObject.put("tax", commerceOrder.getTaxAmount());
			cartJSONObject.put("tax_description", StringPool.BLANK);
			cartJSONObject.put("total", commerceOrder.getTotal());

			CommerceCurrency commerceCurrency =
				commerceOrder.getCommerceCurrency();

			cartJSONObject.put("currencyCode", commerceCurrency.getCode());

			JSONArray cartItemJSONArray = _jsonFactory.createJSONArray();

			for (CommerceOrderItem commerceOrderItem :
					commerceOrder.getCommerceOrderItems()) {

				JSONObject cartItemJSONObject = _jsonFactory.createJSONObject();

				cartItemJSONObject.put(
					"skuid", commerceOrderItem.getCPInstanceId());
				cartItemJSONObject.put(
					"supplierauxid",
					commerceOrder.getCommerceOrderId() + "/" +
						commerceOrderItem.getCommerceOrderItemId());
				cartItemJSONObject.put(
					"supplierid", commerceOrderItem.getSku());

				CPDefinition cpDefinition = commerceOrderItem.getCPDefinition();

				cartItemJSONObject.put(
					"description", cpDefinition.getDescription());

				StringBundler assetCategorySB = new StringBundler();

				AssetEntry assetEntry = _assetEntryLocalService.getEntry(
					CPDefinition.class.getName(),
					cpDefinition.getCPDefinitionId());

				for (AssetCategory assetCategory : assetEntry.getCategories()) {
					assetCategorySB.append(assetCategory.getName());

					assetCategorySB.append(StringPool.COMMA);
				}

				cartItemJSONObject.put(
					"classification", assetCategorySB.toString());

				cartItemJSONObject.put("uom", "EA");

				cartItemJSONObject.put(
					"quantity", commerceOrderItem.getQuantity());
				cartItemJSONObject.put(
					"unitprice", commerceOrderItem.getUnitPrice());

				cartItemJSONObject.put(
					"commercePriceListId",
					commerceOrderItem.getCommercePriceListId());
				cartItemJSONObject.put(
					"deliveryGroup", commerceOrderItem.getDeliveryGroup());
				cartItemJSONObject.put(
					"discountAmount", commerceOrderItem.getDiscountAmount());
				cartItemJSONObject.put(
					"externalReferenceCode",
					commerceOrderItem.getExternalReferenceCode());
				cartItemJSONObject.put(
					"finalPrice", commerceOrderItem.getFinalPrice());
				cartItemJSONObject.put(
					"parentCommerceOrderItemId",
					commerceOrderItem.getParentCommerceOrderItemId());
				cartItemJSONObject.put(
					"printedNote", commerceOrderItem.getPrintedNote());
				cartItemJSONObject.put(
					"promoPrice", commerceOrderItem.getPromoPrice());
				cartItemJSONObject.put(
					"shippedQuantity", commerceOrderItem.getShippedQuantity());

				JSONObject shippingAddressJSONObject =
					_buildShippingAddressJSONObject(
						commerceOrderItem.getShippingAddressId());

				cartItemJSONObject.put(
					"shippingAddress", shippingAddressJSONObject);

				CPInstance cpInstance = _cpInstanceLocalService.fetchCPInstance(
					commerceOrderItem.getCPInstanceId());

				cartItemJSONObject.put("unspsc", cpInstance.getUnspsc());

				Date createDate = commerceOrderItem.getCreateDate();

				String formattedCreateDate = _getDateString(createDate);

				cartItemJSONObject.put("createDate", formattedCreateDate);

				Date modifiedDate = commerceOrderItem.getModifiedDate();

				String formattedModifiedDate = _getDateString(modifiedDate);

				cartItemJSONObject.put("modifiedDate", formattedModifiedDate);

				Date requestedDeliveryDate =
					commerceOrderItem.getRequestedDeliveryDate();

				String formattedRequestedDeliveryDate = _getDateString(
					requestedDeliveryDate);

				cartItemJSONObject.put(
					"requestedDeliveryDate", formattedRequestedDeliveryDate);

				cartItemJSONArray.put(cartItemJSONObject);
			}

			cartJSONObject.put("items", cartItemJSONArray);

			String cartJSON = cartJSONObject.toString();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"PunchOut2Go cart transfer request to " + url +
						"; cart JSON: " + cartJSON);
			}

			URL urlObj = new URL(url);

			HttpURLConnection httpURLConnection =
				(HttpURLConnection)urlObj.openConnection();

			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty(
				"Content-type", "application/json");

			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
				httpURLConnection.getOutputStream());

			outputStreamWriter.write(cartJSON);

			outputStreamWriter.flush();

			int responseCode = httpURLConnection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				String response = StringUtil.toLowerCase(
					StringUtil.read(httpURLConnection.getInputStream()));

				if (_log.isDebugEnabled()) {
					_log.debug(
						"JSON response received from PunchOut2Go: " + response);
				}

				JSONArray jsonArray = _jsonFactory.createJSONArray(
					StringPool.OPEN_BRACKET + response +
						StringPool.CLOSE_BRACKET);

				JSONObject jsonObject = jsonArray.getJSONObject(0);

				return jsonObject.getString("redirecturl");
			}

			_log.error(
				"PunchOut2Go cart transfer response code: " + responseCode);

			return null;
		}
		catch (Exception e) {
			_log.error("PunchOut2Go cart transfer failed", e);

			throw e;
		}
	}

	private JSONObject _buildCountryJSONObject(long commerceCountryId) {
		JSONObject countryJSONObject = _jsonFactory.createJSONObject();

		countryJSONObject.put("id", commerceCountryId);

		if (commerceCountryId < 1) {
			return countryJSONObject;
		}

		CommerceCountry commerceCountry =
			_commerceCountryLocalService.fetchCommerceCountry(
				commerceCountryId);

		if (commerceCountry == null) {
			return countryJSONObject;
		}

		countryJSONObject.put("name", commerceCountry.getName());

		countryJSONObject.put(
			"numericISOCode", commerceCountry.getNumericISOCode());

		countryJSONObject.put(
			"twoLettersISOCode", commerceCountry.getTwoLettersISOCode());

		countryJSONObject.put(
			"threeLettersISOCode", commerceCountry.getThreeLettersISOCode());

		return countryJSONObject;
	}

	private JSONObject _buildRegionJSONObject(long commerceRegionId) {
		JSONObject regionJSONObject = _jsonFactory.createJSONObject();

		regionJSONObject.put("id", commerceRegionId);

		if (commerceRegionId < 1) {
			return regionJSONObject;
		}

		CommerceRegion commerceRegion =
			_commerceRegionLocalService.fetchCommerceRegion(commerceRegionId);

		if (commerceRegion == null) {
			return regionJSONObject;
		}

		regionJSONObject.put("name", commerceRegion.getName());

		regionJSONObject.put("code", commerceRegion.getCode());

		return regionJSONObject;
	}

	private JSONObject _buildShippingAddressJSONObject(long shippingAddressId) {
		JSONObject shippingAddressJSONObject = _jsonFactory.createJSONObject();

		shippingAddressJSONObject.put("shippingAddressId", shippingAddressId);

		if (shippingAddressId < 1) {
			return shippingAddressJSONObject;
		}

		CommerceAddress shippingAddress =
			_commerceAddressLocalService.fetchCommerceAddress(
				shippingAddressId);

		if (shippingAddress == null) {
			return shippingAddressJSONObject;
		}

		shippingAddressJSONObject.put("name", shippingAddress.getName());

		shippingAddressJSONObject.put(
			"externalReferenceCode",
			shippingAddress.getExternalReferenceCode());

		shippingAddressJSONObject.put("street1", shippingAddress.getStreet1());

		shippingAddressJSONObject.put("street2", shippingAddress.getStreet2());

		shippingAddressJSONObject.put("street3", shippingAddress.getStreet3());

		shippingAddressJSONObject.put("city", shippingAddress.getCity());

		JSONObject regionJSONObject = _buildRegionJSONObject(
			shippingAddress.getCommerceRegionId());

		shippingAddressJSONObject.put("region", regionJSONObject);

		shippingAddressJSONObject.put("zip", shippingAddress.getZip());

		JSONObject countryJSONObject = _buildCountryJSONObject(
			shippingAddress.getCommerceCountryId());

		shippingAddressJSONObject.put("country", countryJSONObject);

		shippingAddressJSONObject.put(
			"isGeolocated", shippingAddress.isGeolocated());

		shippingAddressJSONObject.put(
			"isDefaultBilling", shippingAddress.isDefaultBilling());

		shippingAddressJSONObject.put(
			"isDefaultShipping", shippingAddress.isDefaultShipping());

		shippingAddressJSONObject.put(
			"description", shippingAddress.getDescription());

		shippingAddressJSONObject.put(
			"companyId", shippingAddress.getCompanyId());

		shippingAddressJSONObject.put("groupId", shippingAddress.getGroupId());

		shippingAddressJSONObject.put(
			"latitude", shippingAddress.getLatitude());

		shippingAddressJSONObject.put(
			"longitude", shippingAddress.getLongitude());

		shippingAddressJSONObject.put(
			"phoneNumber", shippingAddress.getPhoneNumber());

		shippingAddressJSONObject.put("type", shippingAddress.getType());

		Date createDate = shippingAddress.getCreateDate();

		String formattedCreateDate = _getDateString(createDate);

		shippingAddressJSONObject.put("createDate", formattedCreateDate);

		Date modifiedDate = shippingAddress.getModifiedDate();

		String formattedModifiedDate = _getDateString(modifiedDate);

		shippingAddressJSONObject.put("modifiedDate", formattedModifiedDate);

		return shippingAddressJSONObject;
	}

	private String _getDateString(Date date) {
		if (date == null) {
			return StringPool.BLANK;
		}

		return _dateFormat.format(date);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PunchOut2GoReturnServiceImpl.class);

	private static final DateFormat _dateFormat = new SimpleDateFormat(
		"yyyy-MM-dd'T'HH:mm:ss'Z'");

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private CommerceAddressLocalService _commerceAddressLocalService;

	@Reference
	private CommerceCountryLocalService _commerceCountryLocalService;

	@Reference
	private CommerceRegionLocalService _commerceRegionLocalService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private JSONFactory _jsonFactory;

}
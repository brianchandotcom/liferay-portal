/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.osb.faro.engine.client.model.Account;
import com.liferay.osb.faro.engine.client.model.Field;
import com.liferay.osb.faro.web.internal.constants.FaroConstants;
import com.liferay.osb.faro.web.internal.model.display.main.FaroEntityDisplay;
import com.liferay.osb.faro.web.internal.util.PhotoURLHelper;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.DecimalFormat;

import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shinn Lok
 */
public class AccountDisplay implements FaroEntityDisplay {

	public AccountDisplay() {
	}

	public AccountDisplay(Account account, PhotoURLHelper photoURLHelper) {
		_account = account;

		_activitiesCount = account.getActivitiesCount();
		_dateCreated = account.getDateCreated();
		_individualCount = account.getIndividualCount();
		_id = account.getId();
		_name = GetterUtil.get(getValue("accountName"), StringPool.BLANK);

		if (photoURLHelper != null) {
			_photoURL = getPhotoURL(photoURLHelper);
		}

		_type = FaroConstants.TYPE_ACCOUNT;

		addProperties(_propertyNames);
	}

	@Override
	public void addProperties(List<String> propertyNames) {
		for (String propertyName : propertyNames) {
			Object value = null;

			if (propertyName.equals("annualRevenue")) {
				value = getValue(propertyName);
				String currencyIsoCode = GetterUtil.getString(
					getValue("currencyIsoCode"));

				if (Validator.isNotNull(currencyIsoCode) && (value != null)) {
					Currency currency = Currency.getInstance(currencyIsoCode);

					String symbol = StringPool.BLANK;

					if (!StringUtil.equals(
							currency.getSymbol(), currency.getCurrencyCode())) {

						symbol = currency.getSymbol();
					}

					value = StringBundler.concat(
						currency.getCurrencyCode(), StringPool.SPACE, symbol,
						_decimalFormat.format(GetterUtil.getNumber(value)));
				}
			}
			else if (propertyName.equals("billingAddress")) {
				value = getAddress(
					"billingStreet", "billingCity", "billingState",
					"billingPostalCode");
			}
			else if (propertyName.equals("shippingAddress")) {
				value = getAddress(
					"shippingStreet", "shippingCity", "shippingState",
					"shippingPostalCode");
			}
			else {
				value = getValue(propertyName);
			}

			_propertiesMap.put(propertyName, value);
		}
	}

	@Override
	public String getId() {
		return _id;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Map<String, Object> getProperties() {
		return _propertiesMap;
	}

	@Override
	public int getType() {
		return _type;
	}

	protected void appendAddress(
		StringBundler sb, String delimiter, String field) {

		String value = GetterUtil.getString(getValue(field));

		if (Validator.isNull(value)) {
			return;
		}

		if (sb.length() > 0) {
			sb.append(delimiter);
		}

		sb.append(value);
	}

	protected String getAddress(
		String streetField, String cityField, String stateField,
		String postalCodeField) {

		StringBundler sb = new StringBundler(7);

		sb.append(GetterUtil.getString(getValue(streetField)));

		appendAddress(sb, StringPool.SPACE, cityField);
		appendAddress(sb, StringPool.COMMA_AND_SPACE, stateField);
		appendAddress(sb, StringPool.SPACE, postalCodeField);

		if (sb.length() == 0) {
			return null;
		}

		return sb.toString();
	}

	protected String getPhotoURL(PhotoURLHelper photoURLHelper) {
		try {
			return photoURLHelper.getPhotoURL(
				GetterUtil.getString(getValue("website")));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get photo for: " +
						GetterUtil.getString(getValue("website")),
					exception);
			}

			return null;
		}
	}

	protected Object getValue(String key) {
		Map<String, List<Field>> fieldsMap = _account.getOrganization();

		List<Field> properties = fieldsMap.get(key);

		if (ListUtil.isNotEmpty(properties)) {
			Field field = properties.get(0);

			return field.getValue();
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(AccountDisplay.class);

	private static final DecimalFormat _decimalFormat = new DecimalFormat(
		"#,###");
	private static final List<String> _propertyNames = Arrays.asList(
		"accountType", "annualRevenue", "billingAddress", "description", "fax",
		"industry", "numberOfEmployees", "ownership", "phone",
		"shippingAddress", "website", "yearStarted");

	@JsonIgnore
	private Account _account;

	private long _activitiesCount;
	private Date _dateCreated;
	private String _id;
	private long _individualCount;
	private String _name;
	private String _photoURL;

	@JsonProperty("properties")
	private Map<String, Object> _propertiesMap = new HashMap<>();

	private int _type;

}
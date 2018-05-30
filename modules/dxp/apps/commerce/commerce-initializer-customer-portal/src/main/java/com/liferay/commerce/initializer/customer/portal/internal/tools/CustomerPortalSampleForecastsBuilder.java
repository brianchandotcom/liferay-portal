/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.initializer.customer.portal.internal.tools;

import com.liferay.commerce.forecast.model.CommerceForecastEntryConstants;
import com.liferay.commerce.initializer.customer.portal.internal.CustomerPortalGroupInitializer;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Time;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.math.BigDecimal;
import java.math.MathContext;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Andrea Di Giorgi
 */
public class CustomerPortalSampleForecastsBuilder {

	public static void main(String[] args) throws IOException {
		if (args.length == 1) {
			new CustomerPortalSampleForecastsBuilder(args[0]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public CustomerPortalSampleForecastsBuilder(String outputDirName)
		throws IOException {

		Path outputDirPath = Paths.get(outputDirName);

		String productsJSON = _read(
			"com/liferay/commerce/initializer/customer/portal/internal" +
				"/dependencies/products.json");

		JSONArray productsJSONArray = new JSONArray(productsJSON);

		for (int customerId = 0;
			customerId <=
				CustomerPortalGroupInitializer.ACCOUNT_ORGANIZATIONS_COUNT;
			customerId++) {

			_writeForecasts(
				outputDirPath, customerId,
				CommerceForecastEntryConstants.PERIOD_MONTHLY,
				CommerceForecastEntryConstants.TARGET_QUANTITY, null);
			_writeForecasts(
				outputDirPath, customerId,
				CommerceForecastEntryConstants.PERIOD_MONTHLY,
				CommerceForecastEntryConstants.TARGET_REVENUE, null);
			_writeForecasts(
				outputDirPath, customerId,
				CommerceForecastEntryConstants.PERIOD_WEEKLY,
				CommerceForecastEntryConstants.TARGET_QUANTITY, null);
			_writeForecasts(
				outputDirPath, customerId,
				CommerceForecastEntryConstants.PERIOD_WEEKLY,
				CommerceForecastEntryConstants.TARGET_REVENUE, null);

			_writeForecasts(
				outputDirPath, customerId,
				CommerceForecastEntryConstants.PERIOD_MONTHLY,
				CommerceForecastEntryConstants.TARGET_QUANTITY,
				productsJSONArray);
			_writeForecasts(
				outputDirPath, customerId,
				CommerceForecastEntryConstants.PERIOD_MONTHLY,
				CommerceForecastEntryConstants.TARGET_REVENUE,
				productsJSONArray);
			_writeForecasts(
				outputDirPath, customerId,
				CommerceForecastEntryConstants.PERIOD_WEEKLY,
				CommerceForecastEntryConstants.TARGET_QUANTITY,
				productsJSONArray);
			_writeForecasts(
				outputDirPath, customerId,
				CommerceForecastEntryConstants.PERIOD_WEEKLY,
				CommerceForecastEntryConstants.TARGET_REVENUE,
				productsJSONArray);
		}
	}

	private BigDecimal _addRandomIncrement(
		BigDecimal value, double volatility, Boolean negative) {

		double incrementPercent = 2 * volatility * _random.nextFloat();

		if (incrementPercent > volatility) {
			incrementPercent -= 2 * volatility;
		}

		BigDecimal increment = value.multiply(
			BigDecimal.valueOf(incrementPercent / 100));

		if (negative != null) {
			increment = increment.abs();

			if (negative) {
				increment = increment.negate();
			}
		}

		value = value.add(increment);

		return value.round(MathContext.DECIMAL32);
	}

	private JSONObject _getForecast(
		int customerId, JSONObject productJSONObject, int period, int target) {

		JSONObject jsonObject = new JSONObject();

		if (customerId > 0) {
			jsonObject.put("customerId", customerId);
		}

		jsonObject.put(
			"period", CommerceForecastEntryConstants.getPeriodLabel(period));

		if (productJSONObject != null) {
			jsonObject.put("sku", productJSONObject.getString("Sku"));
		}

		jsonObject.put(
			"target", CommerceForecastEntryConstants.getTargetLabel(target));

		JSONArray valuesJSONArray = new JSONArray();

		double rangeVolatility = _getVolatility();
		double valueVolatility = _getVolatility();

		BigDecimal value = BigDecimal.valueOf(_random.nextInt(_MAX_QUANTITY));

		long increment = Time.WEEK;

		if (period == CommerceForecastEntryConstants.PERIOD_MONTHLY) {
			increment = Time.MONTH;
		}

		for (long time = Math.negateExact(_RANGE_TIME); time <= _RANGE_TIME;
			time += increment) {

			value = _addRandomIncrement(value, valueVolatility, null);

			JSONObject valueJSONObject = new JSONObject();

			valueJSONObject.put("time", time);
			valueJSONObject.put("value", value);

			if (time > 0) {
				BigDecimal lowerValue = _addRandomIncrement(
					value, rangeVolatility, true);

				if (lowerValue.compareTo(BigDecimal.ZERO) < 0) {
					lowerValue = BigDecimal.ZERO;
				}

				BigDecimal upperValue = _addRandomIncrement(
					value, rangeVolatility, false);

				if ((value.compareTo(lowerValue) < 0) ||
					(value.compareTo(upperValue) > 0)) {

					throw new ArithmeticException();
				}

				valueJSONObject.put("lowerValue", lowerValue);
				valueJSONObject.put("upperValue", upperValue);
			}

			valuesJSONArray.put(valueJSONObject);
		}

		jsonObject.put("values", valuesJSONArray);

		return jsonObject;
	}

	private Path _getOutputPath(
		Path outputDirPath, int customerId, boolean sku, int period,
		int target) {

		StringBuilder sb = new StringBuilder();

		if ((customerId <= 0) && !sku) {
			sb.append("company_");
		}
		else {
			if (sku) {
				sb.append("sku_");
			}

			if (customerId > 0) {
				sb.append("customer_");
				sb.append(customerId);
				sb.append(CharPool.UNDERLINE);
			}
		}

		sb.append(CommerceForecastEntryConstants.getPeriodLabel(period));
		sb.append(CharPool.UNDERLINE);
		sb.append(CommerceForecastEntryConstants.getTargetLabel(target));
		sb.append(".json");

		return outputDirPath.resolve(sb.toString());
	}

	private double _getVolatility() {
		return _random.nextDouble() * 10 + 2;
	}

	private String _read(String name) throws IOException {
		StringBuilder sb = new StringBuilder();

		ClassLoader classLoader =
			CustomerPortalSampleForecastsBuilder.class.getClassLoader();

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(
					classLoader.getResourceAsStream(name),
					StandardCharsets.UTF_8))) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
			}
		}

		return sb.toString();
	}

	private void _write(Path path, JSONArray jsonArray) throws IOException {
		String json = jsonArray.toString(StringPool.FOUR_SPACES.length());

		json = json.replace(StringPool.FOUR_SPACES, StringPool.TAB);

		Files.createDirectories(path.getParent());

		Files.write(path, json.getBytes(StandardCharsets.UTF_8));
	}

	private void _writeForecasts(
			Path outputDirPath, int customerId, int period, int target,
			JSONArray productsJSONArray)
		throws IOException {

		JSONArray jsonArray = new JSONArray();

		boolean sku = false;

		if (productsJSONArray != null) {
			sku = true;

			for (int i = 0; i < productsJSONArray.length(); i++) {
				JSONObject productJSONObject = productsJSONArray.getJSONObject(
					i);

				JSONObject jsonObject = _getForecast(
					customerId, productJSONObject, period, target);

				jsonArray.put(jsonObject);
			}
		}
		else {
			JSONObject jsonObject = _getForecast(
				customerId, null, period, target);

			jsonArray.put(jsonObject);
		}

		Path path = _getOutputPath(
			outputDirPath, customerId, sku, period, target);

		_write(path, jsonArray);
	}

	private static final int _MAX_QUANTITY = 1000000;

	private static final long _RANGE_TIME = 6 * Time.MONTH;

	private static final Random _random = new Random();

}
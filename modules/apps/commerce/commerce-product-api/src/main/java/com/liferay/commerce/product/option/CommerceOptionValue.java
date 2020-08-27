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

package com.liferay.commerce.product.option;

import java.math.BigDecimal;

import java.util.List;
import java.util.Objects;

/**
 * @author Riccardo Alberti
 * @author Igor Beslic
 */
public interface CommerceOptionValue {

	public long getCPInstanceId();

	public CommerceOptionValue getFirstMatch(
		List<CommerceOptionValue> commerceOptionValues);

	public String getOptionKey();

	public String getOptionValueKey();

	public BigDecimal getPrice();

	public String getPriceType();

	public int getQuantity();

	public boolean matches(CommerceOptionValue commerceOptionValue);

	public String toJSON();

	public static class Builder {

		public CommerceOptionValue build() {
			return new CommerceOptionValue() {

				@Override
				public long getCPInstanceId() {
					return _cpInstanceId;
				}

				@Override
				public CommerceOptionValue getFirstMatch(
					List<CommerceOptionValue> commerceOptionValues) {

					for (CommerceOptionValue commerceOptionValue :
							commerceOptionValues) {

						if (matches(commerceOptionValue)) {
							return commerceOptionValue;
						}
					}

					return null;
				}

				@Override
				public String getOptionKey() {
					return _optionKey;
				}

				@Override
				public String getOptionValueKey() {
					return _optionValueKey;
				}

				@Override
				public BigDecimal getPrice() {
					return _price;
				}

				@Override
				public String getPriceType() {
					return _priceType;
				}

				@Override
				public int getQuantity() {
					return _quantity;
				}

				@Override
				public boolean matches(
					CommerceOptionValue commerceOptionValue) {

					if (commerceOptionValue == null) {
						return false;
					}

					if (Objects.equals(
							_optionKey, commerceOptionValue.getOptionKey()) &&
						Objects.equals(
							_optionValueKey,
							commerceOptionValue.getOptionValueKey())) {

						return true;
					}

					return false;
				}

				@Override
				public String toJSON() {
					return String.format(
						_JSON_SERIALIZED_PATTERN, _cpInstanceId, _optionKey,
						_price, _priceType, _quantity, _optionValueKey);
				}

				private final long _cpInstanceId = Builder.this._cpInstanceId;
				private final String _optionKey = Builder.this._optionKey;
				private final String _optionValueKey =
					Builder.this._optionValueKey;
				private final BigDecimal _price = Builder.this._price;
				private final String _priceType = Builder.this._priceType;
				private final int _quantity = Builder.this._quantity;

			};
		}

		public Builder cpInstanceId(long cpInstanceId) {
			_cpInstanceId = cpInstanceId;

			return this;
		}

		public Builder optionKey(String optionKey) {
			_optionKey = optionKey;

			return this;
		}

		public Builder optionValueKey(String optionValueKey) {
			_optionValueKey = optionValueKey;

			return this;
		}

		public Builder price(BigDecimal price) {
			_price = price;

			return this;
		}

		public Builder priceType(String priceType) {
			_priceType = priceType;

			return this;
		}

		public Builder quantity(int quantity) {
			_quantity = quantity;

			return this;
		}

		private static final String _JSON_SERIALIZED_PATTERN =
			"{\"cpInstanceId\":%d, \"key\":\"%s\", \"price\":\"%s\", " +
				"\"priceType\":\"%s\", \"quantity\":%d, \"value\":\"%s\"}";

		private long _cpInstanceId;
		private String _optionKey;
		private String _optionValueKey;
		private BigDecimal _price;
		private String _priceType;
		private int _quantity;

	}

}
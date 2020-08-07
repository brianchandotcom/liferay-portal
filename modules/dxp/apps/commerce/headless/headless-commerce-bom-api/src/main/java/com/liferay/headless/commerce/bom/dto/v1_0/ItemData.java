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

package com.liferay.headless.commerce.bom.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
@GraphQLName("ItemData")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "ItemData")
public class ItemData {

	public static ItemData toDTO(String json) {
		return ObjectMapperUtil.readValue(ItemData.class, json);
	}

	@Schema
	@Valid
	public Brand[] getBrands() {
		return brands;
	}

	public void setBrands(Brand[] brands) {
		this.brands = brands;
	}

	@JsonIgnore
	public void setBrands(
		UnsafeSupplier<Brand[], Exception> brandsUnsafeSupplier) {

		try {
			brands = brandsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Brand[] brands;

	@Schema
	@Valid
	public Item[] getItems() {
		return items;
	}

	public void setItems(Item[] items) {
		this.items = items;
	}

	@JsonIgnore
	public void setItems(
		UnsafeSupplier<Item[], Exception> itemsUnsafeSupplier) {

		try {
			items = itemsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Item[] items;

	@Schema
	@Valid
	public Product[] getProducts() {
		return products;
	}

	public void setProducts(Product[] products) {
		this.products = products;
	}

	@JsonIgnore
	public void setProducts(
		UnsafeSupplier<Product[], Exception> productsUnsafeSupplier) {

		try {
			products = productsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Product[] products;

	@Schema
	@Valid
	public Spot[] getSpots() {
		return spots;
	}

	public void setSpots(Spot[] spots) {
		this.spots = spots;
	}

	@JsonIgnore
	public void setSpots(
		UnsafeSupplier<Spot[], Exception> spotsUnsafeSupplier) {

		try {
			spots = spotsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Spot[] spots;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ItemData)) {
			return false;
		}

		ItemData itemData = (ItemData)object;

		return Objects.equals(toString(), itemData.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (brands != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"brands\": ");

			sb.append("[");

			for (int i = 0; i < brands.length; i++) {
				sb.append(String.valueOf(brands[i]));

				if ((i + 1) < brands.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (items != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"items\": ");

			sb.append("[");

			for (int i = 0; i < items.length; i++) {
				sb.append(String.valueOf(items[i]));

				if ((i + 1) < items.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (products != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"products\": ");

			sb.append("[");

			for (int i = 0; i < products.length; i++) {
				sb.append(String.valueOf(products[i]));

				if ((i + 1) < products.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (spots != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"spots\": ");

			sb.append("[");

			for (int i = 0; i < spots.length; i++) {
				sb.append(String.valueOf(spots[i]));

				if ((i + 1) < spots.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		defaultValue = "com.liferay.headless.commerce.bom.dto.v1_0.ItemData",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\":");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(value);
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}
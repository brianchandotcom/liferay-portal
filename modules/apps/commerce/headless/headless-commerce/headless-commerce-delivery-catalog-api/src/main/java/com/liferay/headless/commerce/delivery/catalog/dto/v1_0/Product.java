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

package com.liferay.headless.commerce.delivery.catalog.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
@GraphQLName("Product")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Product")
public class Product implements Serializable {

	public static Product toDTO(String json) {
		return ObjectMapperUtil.readValue(Product.class, json);
	}

	public static Product unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Product.class, json);
	}

	@Schema
	@Valid
	public Attachment[] getAttachments() {
		return attachments;
	}

	public void setAttachments(Attachment[] attachments) {
		this.attachments = attachments;
	}

	@JsonIgnore
	public void setAttachments(
		UnsafeSupplier<Attachment[], Exception> attachmentsUnsafeSupplier) {

		try {
			attachments = attachmentsUnsafeSupplier.get();
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
	protected Attachment[] attachments;

	@Schema
	@Valid
	public Category[] getCategories() {
		return categories;
	}

	public void setCategories(Category[] categories) {
		this.categories = categories;
	}

	@JsonIgnore
	public void setCategories(
		UnsafeSupplier<Category[], Exception> categoriesUnsafeSupplier) {

		try {
			categories = categoriesUnsafeSupplier.get();
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
	protected Category[] categories;

	@Schema
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JsonIgnore
	public void setCreateDate(
		UnsafeSupplier<Date, Exception> createDateUnsafeSupplier) {

		try {
			createDate = createDateUnsafeSupplier.get();
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
	protected Date createDate;

	@Schema
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonIgnore
	public void setDescription(
		UnsafeSupplier<String, Exception> descriptionUnsafeSupplier) {

		try {
			description = descriptionUnsafeSupplier.get();
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
	protected String description;

	@Schema
	@Valid
	public Map<String, ?> getExpando() {
		return expando;
	}

	public void setExpando(Map<String, ?> expando) {
		this.expando = expando;
	}

	@JsonIgnore
	public void setExpando(
		UnsafeSupplier<Map<String, ?>, Exception> expandoUnsafeSupplier) {

		try {
			expando = expandoUnsafeSupplier.get();
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
	protected Map<String, ?> expando;

	@DecimalMin("0")
	@Schema(example = "30130")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long id;

	@Schema
	@Valid
	public Attachment[] getImages() {
		return images;
	}

	public void setImages(Attachment[] images) {
		this.images = images;
	}

	@JsonIgnore
	public void setImages(
		UnsafeSupplier<Attachment[], Exception> imagesUnsafeSupplier) {

		try {
			images = imagesUnsafeSupplier.get();
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
	protected Attachment[] images;

	@Schema
	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	@JsonIgnore
	public void setMetaDescription(
		UnsafeSupplier<String, Exception> metaDescriptionUnsafeSupplier) {

		try {
			metaDescription = metaDescriptionUnsafeSupplier.get();
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
	protected String metaDescription;

	@Schema
	public String getMetaKeyword() {
		return metaKeyword;
	}

	public void setMetaKeyword(String metaKeyword) {
		this.metaKeyword = metaKeyword;
	}

	@JsonIgnore
	public void setMetaKeyword(
		UnsafeSupplier<String, Exception> metaKeywordUnsafeSupplier) {

		try {
			metaKeyword = metaKeywordUnsafeSupplier.get();
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
	protected String metaKeyword;

	@Schema
	public String getMetaTitle() {
		return metaTitle;
	}

	public void setMetaTitle(String metaTitle) {
		this.metaTitle = metaTitle;
	}

	@JsonIgnore
	public void setMetaTitle(
		UnsafeSupplier<String, Exception> metaTitleUnsafeSupplier) {

		try {
			metaTitle = metaTitleUnsafeSupplier.get();
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
	protected String metaTitle;

	@Schema
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@JsonIgnore
	public void setModifiedDate(
		UnsafeSupplier<Date, Exception> modifiedDateUnsafeSupplier) {

		try {
			modifiedDate = modifiedDateUnsafeSupplier.get();
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
	protected Date modifiedDate;

	@Schema
	public Integer getMultipleOrderQuantity() {
		return multipleOrderQuantity;
	}

	public void setMultipleOrderQuantity(Integer multipleOrderQuantity) {
		this.multipleOrderQuantity = multipleOrderQuantity;
	}

	@JsonIgnore
	public void setMultipleOrderQuantity(
		UnsafeSupplier<Integer, Exception>
			multipleOrderQuantityUnsafeSupplier) {

		try {
			multipleOrderQuantity = multipleOrderQuantityUnsafeSupplier.get();
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
	protected Integer multipleOrderQuantity;

	@Schema
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
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
	protected String name;

	@Schema
	@Valid
	public ProductConfiguration getProductConfiguration() {
		return productConfiguration;
	}

	public void setProductConfiguration(
		ProductConfiguration productConfiguration) {

		this.productConfiguration = productConfiguration;
	}

	@JsonIgnore
	public void setProductConfiguration(
		UnsafeSupplier<ProductConfiguration, Exception>
			productConfigurationUnsafeSupplier) {

		try {
			productConfiguration = productConfigurationUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected ProductConfiguration productConfiguration;

	@Schema
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@JsonIgnore
	public void setProductId(
		UnsafeSupplier<Long, Exception> productIdUnsafeSupplier) {

		try {
			productId = productIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long productId;

	@Schema
	@Valid
	public ProductOption[] getProductOptions() {
		return productOptions;
	}

	public void setProductOptions(ProductOption[] productOptions) {
		this.productOptions = productOptions;
	}

	@JsonIgnore
	public void setProductOptions(
		UnsafeSupplier<ProductOption[], Exception>
			productOptionsUnsafeSupplier) {

		try {
			productOptions = productOptionsUnsafeSupplier.get();
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
	protected ProductOption[] productOptions;

	@Schema
	@Valid
	public ProductSpecification[] getProductSpecifications() {
		return productSpecifications;
	}

	public void setProductSpecifications(
		ProductSpecification[] productSpecifications) {

		this.productSpecifications = productSpecifications;
	}

	@JsonIgnore
	public void setProductSpecifications(
		UnsafeSupplier<ProductSpecification[], Exception>
			productSpecificationsUnsafeSupplier) {

		try {
			productSpecifications = productSpecificationsUnsafeSupplier.get();
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
	protected ProductSpecification[] productSpecifications;

	@Schema(example = "simple")
	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	@JsonIgnore
	public void setProductType(
		UnsafeSupplier<String, Exception> productTypeUnsafeSupplier) {

		try {
			productType = productTypeUnsafeSupplier.get();
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
	protected String productType;

	@Schema
	@Valid
	public RelatedProduct[] getRelatedProducts() {
		return relatedProducts;
	}

	public void setRelatedProducts(RelatedProduct[] relatedProducts) {
		this.relatedProducts = relatedProducts;
	}

	@JsonIgnore
	public void setRelatedProducts(
		UnsafeSupplier<RelatedProduct[], Exception>
			relatedProductsUnsafeSupplier) {

		try {
			relatedProducts = relatedProductsUnsafeSupplier.get();
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
	protected RelatedProduct[] relatedProducts;

	@Schema
	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	@JsonIgnore
	public void setShortDescription(
		UnsafeSupplier<String, Exception> shortDescriptionUnsafeSupplier) {

		try {
			shortDescription = shortDescriptionUnsafeSupplier.get();
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
	protected String shortDescription;

	@Schema
	@Valid
	public Sku[] getSkus() {
		return skus;
	}

	public void setSkus(Sku[] skus) {
		this.skus = skus;
	}

	@JsonIgnore
	public void setSkus(UnsafeSupplier<Sku[], Exception> skusUnsafeSupplier) {
		try {
			skus = skusUnsafeSupplier.get();
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
	protected Sku[] skus;

	@Schema
	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	@JsonIgnore
	public void setSlug(UnsafeSupplier<String, Exception> slugUnsafeSupplier) {
		try {
			slug = slugUnsafeSupplier.get();
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
	protected String slug;

	@Schema(example = "[tag1, tag2, tag3]")
	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	@JsonIgnore
	public void setTags(
		UnsafeSupplier<String[], Exception> tagsUnsafeSupplier) {

		try {
			tags = tagsUnsafeSupplier.get();
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
	protected String[] tags;

	@Schema
	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	@JsonIgnore
	public void setUrlImage(
		UnsafeSupplier<String, Exception> urlImageUnsafeSupplier) {

		try {
			urlImage = urlImageUnsafeSupplier.get();
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
	protected String urlImage;

	@Schema(
		example = "{en_US=product-url-us, hr_HR=product-url-hr, hu_HU=product-url-hu}"
	)
	@Valid
	public Map<String, String> getUrls() {
		return urls;
	}

	public void setUrls(Map<String, String> urls) {
		this.urls = urls;
	}

	@JsonIgnore
	public void setUrls(
		UnsafeSupplier<Map<String, String>, Exception> urlsUnsafeSupplier) {

		try {
			urls = urlsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Map<String, String> urls;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Product)) {
			return false;
		}

		Product product = (Product)object;

		return Objects.equals(toString(), product.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (attachments != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"attachments\": ");

			sb.append("[");

			for (int i = 0; i < attachments.length; i++) {
				sb.append(String.valueOf(attachments[i]));

				if ((i + 1) < attachments.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (categories != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"categories\": ");

			sb.append("[");

			for (int i = 0; i < categories.length; i++) {
				sb.append(String.valueOf(categories[i]));

				if ((i + 1) < categories.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (createDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"createDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(createDate));

			sb.append("\"");
		}

		if (description != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(description));

			sb.append("\"");
		}

		if (expando != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expando\": ");

			sb.append(_toJSON(expando));
		}

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
		}

		if (images != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"images\": ");

			sb.append("[");

			for (int i = 0; i < images.length; i++) {
				sb.append(String.valueOf(images[i]));

				if ((i + 1) < images.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (metaDescription != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"metaDescription\": ");

			sb.append("\"");

			sb.append(_escape(metaDescription));

			sb.append("\"");
		}

		if (metaKeyword != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"metaKeyword\": ");

			sb.append("\"");

			sb.append(_escape(metaKeyword));

			sb.append("\"");
		}

		if (metaTitle != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"metaTitle\": ");

			sb.append("\"");

			sb.append(_escape(metaTitle));

			sb.append("\"");
		}

		if (modifiedDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modifiedDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(modifiedDate));

			sb.append("\"");
		}

		if (multipleOrderQuantity != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"multipleOrderQuantity\": ");

			sb.append(multipleOrderQuantity);
		}

		if (name != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(name));

			sb.append("\"");
		}

		if (productConfiguration != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productConfiguration\": ");

			sb.append(String.valueOf(productConfiguration));
		}

		if (productId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productId\": ");

			sb.append(productId);
		}

		if (productOptions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productOptions\": ");

			sb.append("[");

			for (int i = 0; i < productOptions.length; i++) {
				sb.append(String.valueOf(productOptions[i]));

				if ((i + 1) < productOptions.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (productSpecifications != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productSpecifications\": ");

			sb.append("[");

			for (int i = 0; i < productSpecifications.length; i++) {
				sb.append(String.valueOf(productSpecifications[i]));

				if ((i + 1) < productSpecifications.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (productType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productType\": ");

			sb.append("\"");

			sb.append(_escape(productType));

			sb.append("\"");
		}

		if (relatedProducts != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"relatedProducts\": ");

			sb.append("[");

			for (int i = 0; i < relatedProducts.length; i++) {
				sb.append(String.valueOf(relatedProducts[i]));

				if ((i + 1) < relatedProducts.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (shortDescription != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shortDescription\": ");

			sb.append("\"");

			sb.append(_escape(shortDescription));

			sb.append("\"");
		}

		if (skus != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skus\": ");

			sb.append("[");

			for (int i = 0; i < skus.length; i++) {
				sb.append(String.valueOf(skus[i]));

				if ((i + 1) < skus.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (slug != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"slug\": ");

			sb.append("\"");

			sb.append(_escape(slug));

			sb.append("\"");
		}

		if (tags != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"tags\": ");

			sb.append("[");

			for (int i = 0; i < tags.length; i++) {
				sb.append("\"");

				sb.append(_escape(tags[i]));

				sb.append("\"");

				if ((i + 1) < tags.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (urlImage != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"urlImage\": ");

			sb.append("\"");

			sb.append(_escape(urlImage));

			sb.append("\"");
		}

		if (urls != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"urls\": ");

			sb.append(_toJSON(urls));
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
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
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

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
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

}
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

package com.liferay.headless.commerce.admin.pricing.client.dto.v2_0;

import com.liferay.headless.commerce.admin.pricing.client.function.UnsafeSupplier;
import com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0.CategorySerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public class Category implements Cloneable {

	public static Category toDTO(String json) {
		return CategorySerDes.toDTO(json);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setPath(UnsafeSupplier<String, Exception> pathUnsafeSupplier) {
		try {
			path = pathUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String path;

	public String getVocabulary() {
		return vocabulary;
	}

	public void setVocabulary(String vocabulary) {
		this.vocabulary = vocabulary;
	}

	public void setVocabulary(
		UnsafeSupplier<String, Exception> vocabularyUnsafeSupplier) {

		try {
			vocabulary = vocabularyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String vocabulary;

	@Override
	public Category clone() throws CloneNotSupportedException {
		return (Category)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Category)) {
			return false;
		}

		Category category = (Category)object;

		return Objects.equals(toString(), category.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CategorySerDes.toJSON(this);
	}

}
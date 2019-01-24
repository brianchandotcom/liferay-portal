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

package com.liferay.portal.kernel.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base implementation for all model classes. This class should never need
 * to be used directly.
 *
 * @author Brian Wing Shun Chan
 */
@ProviderType
public abstract class BaseModelImpl<T> implements BaseModel<T> {

	public BaseModelImpl() {
	}

	@Override
	public abstract Object clone();

	@Override
	public Map<String, Function<T, Object>> getAttributeGetterFunctions() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, BiConsumer<T, Object>> getAttributeSetterBiConsumers() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<>();

		Map<String, Function<T, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<T, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<T, Object> attributeGetterFunction = entry.getValue();

			attributes.put(
				attributeName, attributeGetterFunction.apply((T)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public boolean isCachedModel() {
		return _cachedModel;
	}

	@Override
	public boolean isEscapedModel() {
		return _ESCAPED_MODEL;
	}

	@Override
	public boolean isNew() {
		return _new;
	}

	@Override
	public void resetOriginalValues() {
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cachedModel = cachedModel;
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		ExpandoBridge thisExpandoBridge = getExpandoBridge();

		ExpandoBridge baseModelExpandoBridge = baseModel.getExpandoBridge();

		thisExpandoBridge.setAttributes(baseModelExpandoBridge.getAttributes());
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		ExpandoBridge thisExpandoBridge = getExpandoBridge();

		thisExpandoBridge.setAttributes(expandoBridge.getAttributes());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<T, Object>> attributeSetterBiConsumers =
			getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<T, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept((T)this, entry.getValue());
			}
		}
	}

	@Override
	public void setNew(boolean n) {
		_new = n;
	}

	@Override
	public CacheModel<T> toCacheModel() {
		throw new UnsupportedOperationException();
	}

	@Override
	public T toEscapedModel() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		Map<String, Function<T, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<T, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<T, Object> attributeGetterFunction = entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((T)this));
			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public T toUnescapedModel() {
		return (T)this;
	}

	@Override
	public String toXmlString() {
		Map<String, Function<T, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<T, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<T, Object> attributeGetterFunction = entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((T)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	protected Locale getLocale(String languageId) {
		Locale locale = null;

		if (languageId != null) {
			locale = LocaleUtil.fromLanguageId(languageId);
		}

		if (locale == null) {
			locale = LocaleUtil.getMostRelevantLocale();
		}

		return locale;
	}

	private static final boolean _ESCAPED_MODEL = false;

	private boolean _cachedModel;
	private boolean _new;

}
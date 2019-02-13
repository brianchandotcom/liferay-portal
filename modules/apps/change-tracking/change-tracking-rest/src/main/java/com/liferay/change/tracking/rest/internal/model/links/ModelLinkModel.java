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

package com.liferay.change.tracking.rest.internal.model.links;

import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Máté Thurzó
 */
public class ModelLinkModel {

	@XmlElement
	public String getHref() {
		return _href;
	}

	@XmlElement
	public String getRel() {
		return _rel;
	}

	@XmlElement
	public String getType() {
		return _type;
	}

	public static class Builder {

		public Builder() {
			_modelLinkModels = new ArrayList<>();
		}

		public Builder addModelLinkModel(String href, String rel, String type) {
			if (!Validator.isUrl(href, true)) {
				throw new IllegalArgumentException(
					"Href should be a valid URL");
			}

			if (!_httpMethods.contains(type)) {
				throw new IllegalArgumentException(
					"Type should be a valid HTTP method");
			}

			if (Validator.isNull(rel)) {
				throw new IllegalArgumentException(
					"Link relationship cannot be empty");
			}

			ModelLinkModel modelLinkModel = new ModelLinkModel();

			modelLinkModel._href = href;
			modelLinkModel._rel = rel;
			modelLinkModel._type = type;

			_modelLinkModels.add(modelLinkModel);

			return this;
		}

		public List<ModelLinkModel> build() {
			return _modelLinkModels;
		}

		private final List<ModelLinkModel> _modelLinkModels;

	}

	private ModelLinkModel() {
	}

	private static final Set<String> _httpMethods = SetUtil.fromArray(
		new String[] {"DELETE", "GET", "POST", "PUT"});

	private String _href;
	private String _rel;
	private String _type;

}
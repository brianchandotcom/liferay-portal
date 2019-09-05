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

package com.liferay.portal.relationship;

import com.liferay.portal.kernel.model.ClassedModel;

import java.util.Collection;

/**
 * @author Máté Thurzó
 */
public class RelationshipManagerUtil {

	public static <T extends ClassedModel> Collection<? extends ClassedModel>
		getInboundRelatedModels(Class<T> modelClass, long primKey) {

		return _relationshipManager.getInboundRelatedModels(
			modelClass, primKey);
	}

	public static <T extends ClassedModel> Collection<? extends ClassedModel>
		getInboundRelatedModels(
			Class<T> modelClass, long primKey, Degree degree) {

		return _relationshipManager.getInboundRelatedModels(
			modelClass, primKey, degree);
	}

	public static <T extends ClassedModel> Collection<? extends ClassedModel>
		getOutboundRelatedModels(Class<T> modelClass, long primKey) {

		return _relationshipManager.getOutboundRelatedModels(
			modelClass, primKey);
	}

	public static <T extends ClassedModel> Collection<? extends ClassedModel>
		getOutboundRelatedModels(
			Class<T> modelClass, long primKey, Degree degree) {

		return _relationshipManager.getOutboundRelatedModels(
			modelClass, primKey);
	}

	public static <T extends ClassedModel> Collection<? extends ClassedModel>
		getRelatedModels(Class<T> modelClass, long primKey) {

		return _relationshipManager.getRelatedModels(modelClass, primKey);
	}

	public static <T extends ClassedModel> Collection<? extends ClassedModel>
		getRelatedModels(Class<T> modelClass, long primKey, Degree degree) {

		return _relationshipManager.getRelatedModels(
			modelClass, primKey, degree);
	}

	public static void setRelationshipManager(
		RelationshipManager relationshipManager) {

		if (_relationshipManager != null) {
			relationshipManager = _relationshipManager;

			return;
		}

		_relationshipManager = relationshipManager;
	}

	private static RelationshipManager _relationshipManager;

}
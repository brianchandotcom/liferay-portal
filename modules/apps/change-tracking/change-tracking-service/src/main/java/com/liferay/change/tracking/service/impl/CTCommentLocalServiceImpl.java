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

package com.liferay.change.tracking.service.impl;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTComment;
import com.liferay.change.tracking.service.base.CTCommentLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Preston Crary
 */
@Component(
	property = "model.class.name=com.liferay.change.tracking.model.CTComment",
	service = AopService.class
)
public class CTCommentLocalServiceImpl extends CTCommentLocalServiceBaseImpl {

	@Override
	public CTComment addCTComment(
			long userId, long ctCollectionId, long ctEntryId, String value)
		throws PortalException {

		CTCollection ctCollection = ctCollectionPersistence.findByPrimaryKey(
			ctCollectionId);

		CTComment ctComment = ctCommentPersistence.create(
			counterLocalService.increment(CTComment.class.getName()));

		ctComment.setCompanyId(ctCollection.getCompanyId());
		ctComment.setUserId(userId);
		ctComment.setCtCollectionId(ctCollectionId);
		ctComment.setCtEntryId(ctEntryId);
		ctComment.setValue(value);

		return ctCommentPersistence.update(ctComment);
	}

	@Override
	public CTComment deleteCTComment(long ctCommentId) {
		CTComment ctComment = ctCommentPersistence.fetchByPrimaryKey(
			ctCommentId);

		if (ctComment != null) {
			ctComment = ctCommentPersistence.remove(ctComment);
		}

		return ctComment;
	}

	@Override
	public Map<Long, List<CTComment>> getCTCollectionCTComments(
		long ctCollectionId) {

		Map<Long, List<CTComment>> collectionCommentsMap = new HashMap<>();

		for (CTComment ctComment :
				ctCommentPersistence.findByCtCollectionId(ctCollectionId)) {

			List<CTComment> ctComments = collectionCommentsMap.computeIfAbsent(
				ctComment.getCtEntryId(), key -> new ArrayList<>());

			ctComments.add(ctComment);
		}

		return collectionCommentsMap;
	}

	@Override
	public List<CTComment> getCTEntryCTComments(long ctEntryId) {
		return ctCommentPersistence.findByCtEntryId(ctEntryId);
	}

	@Override
	public CTComment updateCTComment(long ctCommentId, String value)
		throws PortalException {

		CTComment ctComment = ctCommentPersistence.findByPrimaryKey(
			ctCommentId);

		ctComment.setValue(value);

		return ctCommentPersistence.update(ctComment);
	}

}
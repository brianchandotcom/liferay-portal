/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBArticleTable;
import com.liferay.knowledge.base.model.KBComment;
import com.liferay.knowledge.base.model.KBCommentTable;
import com.liferay.knowledge.base.service.persistence.KBCommentPersistence;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.social.kernel.model.SocialActivitySetTable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = TableReferenceDefinition.class)
public class KBCommentTableReferenceDefinition
	implements TableReferenceDefinition<KBCommentTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<KBCommentTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				SocialActivitySetTable.INSTANCE
			).innerJoinON(
				KBCommentTable.INSTANCE,
				KBCommentTable.INSTANCE.kbCommentId.eq(
					SocialActivitySetTable.INSTANCE.classPK)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					SocialActivitySetTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(KBComment.class.getName())
				)
			));
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<KBCommentTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.classNameReference(
			KBCommentTable.INSTANCE.classPK,
			KBArticleTable.INSTANCE.resourcePrimKey, KBArticle.class);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kbCommentPersistence;
	}

	@Override
	public KBCommentTable getTable() {
		return KBCommentTable.INSTANCE;
	}

	@Reference
	private KBCommentPersistence _kbCommentPersistence;

}
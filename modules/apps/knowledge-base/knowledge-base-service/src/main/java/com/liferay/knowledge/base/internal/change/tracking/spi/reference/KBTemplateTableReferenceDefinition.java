/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.model.KBTemplateTable;
import com.liferay.knowledge.base.service.persistence.KBTemplatePersistence;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.social.kernel.model.SocialActivitySetTable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = TableReferenceDefinition.class)
public class KBTemplateTableReferenceDefinition
	implements TableReferenceDefinition<KBTemplateTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<KBTemplateTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				SocialActivitySetTable.INSTANCE
			).innerJoinON(
				KBTemplateTable.INSTANCE,
				KBTemplateTable.INSTANCE.kbTemplateId.eq(
					SocialActivitySetTable.INSTANCE.classPK)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					SocialActivitySetTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(KBTemplate.class.getName())
				)
			)
		).resourcePermissionReference(
			KBTemplateTable.INSTANCE.kbTemplateId, KBTemplate.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<KBTemplateTable>
			parentTableReferenceInfoBuilder) {
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kbTemplatePersistence;
	}

	@Override
	public KBTemplateTable getTable() {
		return KBTemplateTable.INSTANCE;
	}

	@Reference
	private KBTemplatePersistence _kbTemplatePersistence;

}
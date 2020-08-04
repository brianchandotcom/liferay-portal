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

package com.liferay.social.activity.internal.change.tracking.spi.reference;

import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.spi.expression.Scalar;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.social.kernel.model.SocialActivitySettingTable;
import com.liferay.social.kernel.service.persistence.SocialActivitySettingPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class SocialActivitySettingTableReferenceDefinition
	implements TableReferenceDefinition<SocialActivitySettingTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<SocialActivitySettingTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<SocialActivitySettingTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			SocialActivitySettingTable.INSTANCE
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				AssetEntryTable.INSTANCE
			).innerJoinON(
				SocialActivitySettingTable.INSTANCE,
				SocialActivitySettingTable.INSTANCE.groupId.eq(
					AssetEntryTable.INSTANCE.groupId
				).and(
					SocialActivitySettingTable.INSTANCE.classNameId.eq(
						AssetEntryTable.INSTANCE.classNameId)
				).and(
					SocialActivitySettingTable.INSTANCE.name.eq(
						DSLFunctionFactoryUtil.concat(
							new Scalar<>("_LFR_CLASS_PK_"),
							DSLFunctionFactoryUtil.castText(
								AssetEntryTable.INSTANCE.classPK)))
				)
			)
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _socialActivitySettingPersistence;
	}

	@Override
	public SocialActivitySettingTable getTable() {
		return SocialActivitySettingTable.INSTANCE;
	}

	@Reference
	private SocialActivitySettingPersistence _socialActivitySettingPersistence;

}